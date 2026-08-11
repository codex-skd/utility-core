package com.skd.utilitycore.qol.bridging.mixin;

import com.skd.utilitycore.qol.bridging.BridgingConfig;
import com.skd.utilitycore.qol.bridging.BridgingKeyMappings;
import com.skd.utilitycore.qol.bridging.building.Bridge;
import com.skd.utilitycore.qol.bridging.compat.type.SpecialBridgingItemHandler;
import com.skd.utilitycore.qol.bridging.compat.SpecialHandlers;
import com.skd.utilitycore.qol.bridging.raytrace.BridgingResult;
import com.skd.utilitycore.qol.bridging.util.GameSupport;
import com.skd.utilitycore.qol.bridging.util.InfoStrings;
import com.skd.utilitycore.qol.bridging.raytrace.BridgingStateTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.BlockPos;
           
import com.skd.utilitycore.qol.bridging.building.Bridge;
           
import com.skd.utilitycore.qol.bridging.compat.type.SpecialBridgingItemHandler;
           
import com.skd.utilitycore.qol.bridging.compat.SpecialHandlers;
           
import com.skd.utilitycore.qol.bridging.raytrace.BridgingResult;
           
import com.skd.utilitycore.qol.bridging.util.GameSupport;
           
import com.skd.utilitycore.qol.bridging.util.InfoStrings;
           
import com.skd.utilitycore.qol.bridging.raytrace.BridgingStateTracker;
           
import net.minecraft.core.Direction;
           
import com.skd.utilitycore.qol.bridging.building.Bridge;
           
import com.skd.utilitycore.qol.bridging.compat.type.SpecialBridgingItemHandler;
           
import com.skd.utilitycore.qol.bridging.compat.SpecialHandlers;
           
import com.skd.utilitycore.qol.bridging.raytrace.BridgingResult;
           
import com.skd.utilitycore.qol.bridging.util.GameSupport;
           
import com.skd.utilitycore.qol.bridging.util.InfoStrings;
           
import com.skd.utilitycore.qol.bridging.raytrace.BridgingStateTracker;
           
import net.minecraft.network.chat.Component;
           
import net.minecraft.util.Mth;
           
import net.minecraft.world.InteractionHand;
           
import net.minecraft.world.InteractionResult;
           
import net.minecraft.world.item.ItemStack;
           
import net.minecraft.world.phys.BlockHitResult;
           
import net.minecraft.world.phys.HitResult;
           
import org.jetbrains.annotations.NotNull;
           
import org.jetbrains.annotations.Nullable;
           
import org.spongepowered.asm.mixin.Mixin;
           
import org.spongepowered.asm.mixin.Shadow;
           
import org.spongepowered.asm.mixin.Unique;
           
import org.spongepowered.asm.mixin.injection.At;
           
import org.spongepowered.asm.mixin.injection.Inject;
           
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(Minecraft.class)
public abstract class MinecraftClientMixin {

    @Shadow @Nullable public MultiPlayerGameMode gameMode;
    @Shadow @Nullable public LocalPlayer player;
    @Shadow @Nullable public HitResult hitResult;

    @Shadow private int rightClickDelay;

    @Shadow @Nullable public ClientLevel level;

    @Inject(at = @At("TAIL"), method = "tick()V")
    public void onTick(CallbackInfo ci) {

        if(this.player != null && this.player.onGround()) {
            BridgingStateTracker.lastKnownYFrac = Mth.frac(this.player.getY());
        }

        if(BridgingKeyMappings.TOGGLE_BRIDGING.consumeClick()) {
            BridgingConfig.ENABLE_BRIDGING_ASSIST.set(!BridgingConfig.ENABLE_BRIDGING_ASSIST.get());

            Component stateMsg = BridgingConfig.ENABLE_BRIDGING_ASSIST.get()
                    ? InfoStrings.ON
                    : InfoStrings.OFF;
            Component text = InfoStrings.TOGGLE_BRIDGING.copy().append(stateMsg);
            Minecraft.getInstance().gui.hud.setOverlayMessage(text, false);
        }

        BridgingStateTracker.tick(this.player);
    }


    @Inject(at = @At("HEAD"), method = "startUseItem()V", cancellable = true)
    public void onItemUse(CallbackInfo info) {
        if(!BridgingConfig.ENABLE_BRIDGING_ASSIST.get()) return;
        if(this.player == null) return;
        if(this.gameMode == null) return;
        if(this.player.isHandsBusy() || this.gameMode.isDestroying()) return;

        // Should only bridge if all other options to interact are exhausted
        if(this.hitResult != null && this.hitResult.getType() != HitResult.Type.MISS) return;

        boolean passesCrouchTest = !BridgingConfig.ONLY_BRIDGE_WHEN_CROUCHED.get() ||
                                    this.player.isCrouching();

        if(!passesCrouchTest)
            return;

        BridgingResult result = BridgingStateTracker.getLastTickTarget();

        if (result == null) return;

        for(InteractionHand hand : InteractionHand.values()) {
            ItemStack itemStack = this.player.getItemInHand(hand);
            boolean isPlaceableStack = GameSupport.isStackPlaceable(itemStack);

            // If you're holding a bow, trident, or other placeable in the main hand,
            // it needs to be tested with a useItem(...) call.
            if(!isPlaceableStack) {
                InteractionResult usage = this.gameMode.useItem(this.player, hand);

                if(usage.consumesAction())
                    return;

                continue;
            }

            BlockPos pos = result.blockPos();
            Direction dir = result.direction().getOpposite(); // Fixes placing on vertical axes -- doesn't affect most horizontal blocks for some reason.

            InteractionResult blockPlaceResult = null;
            int originalStackSize = itemStack.getCount();

            // Compatibility Api - allow custom handling of blocks.
            Optional<SpecialBridgingItemHandler> optHandler = SpecialHandlers.getSpecialItemHandler(itemStack);
            boolean canBePlaced, canBePlacedInWorld;

            if(optHandler.isPresent()) {
                SpecialBridgingItemHandler handler = optHandler.get();

                canBePlaced = handler.canBePlaced(itemStack);
                canBePlacedInWorld = handler.canBePlacedInWorld(itemStack, this.player, this.level, pos, dir);

                if(canBePlaced && canBePlacedInWorld) {
                    blockPlaceResult = optHandler.get().place();
                } else continue;

            } else {
                canBePlaced = GameSupport.passesDefaultPlacementCheck(itemStack);
                canBePlacedInWorld = this.player.mayUseItemAt(pos, dir, itemStack);
            }

            // No custom handling of blocks? Do it the default way.
            if(blockPlaceResult == null) {
                if (!(canBePlaced && canBePlacedInWorld))
                    continue;

                BlockHitResult blockHitResult = Bridge.getDefaultPlaceAssistTarget(itemStack, level, dir, pos);
                blockPlaceResult = this.gameMode.useItemOn(this.player, hand, blockHitResult);
            }

            if (!(blockPlaceResult instanceof InteractionResult.Success successResult)) continue;

            // if successful place occurred, cancel all future behaviour for
            // item placement as this takes over instead. Stops off-hand
            // shields from firing constantly.
            this.rightClickDelay = Math.max(0, BridgingConfig.DELAY_POST_BRIDGING.get());
            info.cancel();

            if(successResult.swingSource() != InteractionResult.SwingSource.CLIENT)
                return;

            this.player.swing(hand);
            boolean stackSizeChanged = itemStack.getCount() != originalStackSize || this.player.hasInfiniteMaterials();

            if (stackSizeChanged && !itemStack.isEmpty()) {
                Minecraft.getInstance().gameRenderer.itemInHandRenderer.itemUsed(hand);
            }

            return;
        }
    }

    @Unique
    @NotNull
    private BlockHitResult bridgingmod$getFinalPlaceAssistTarget(ItemStack heldItem, Direction dir, BlockPos pos, SpecialBridgingItemHandler specialHandler) {
        // Where is the placement action coming from?
        // This is used by the game to determine the state used for directional blocks.

        if(specialHandler != null) {
            BlockHitResult customPlaceAssistTarget = specialHandler.generatePlacementTarget(heldItem, this.player, this.level, dir, pos);

            if(customPlaceAssistTarget != null) {
                return customPlaceAssistTarget;
            }
        }

        return Bridge.getDefaultPlaceAssistTarget(heldItem, this.level, dir, pos);
    }

}