package com.skd.utilitycore.attachment;

import com.skd.utilitycore.UtilityCore;
import com.skd.utilitycore.polymorph.PlayerRecipeData;
import net.neoforged.neoforge.attachment.AttachmentType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public class ModAttachments {

    public static final DeferredRegister<AttachmentType<?>> ATTACHMENT_TYPES =
            DeferredRegister.create(NeoForgeRegistries.Keys.ATTACHMENT_TYPES, UtilityCore.MODID);

    public static final Supplier<AttachmentType<PlayerRecipeData>> PLAYER_RECIPE_DATA =
            ATTACHMENT_TYPES.register("player_recipe_data",
                    () -> AttachmentType.builder(PlayerRecipeData::new).build());
}
