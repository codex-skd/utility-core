package com.skd.utilitycore.fixes.common.attachment;

import com.skd.utilitycore.fixes.common.attachment.PlayerRecipeData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, "utility_core_fixes");

    public static final Supplier<AttachmentType<PlayerRecipeData>> PLAYER_RECIPE_DATA =
            ATTACHMENT_TYPES.register("player_recipe_data",
                    () -> AttachmentType.builder(PlayerRecipeData::new).build());
}