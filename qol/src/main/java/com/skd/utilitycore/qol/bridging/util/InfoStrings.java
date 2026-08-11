package com.skd.utilitycore.qol.bridging.util;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;

public class InfoStrings {

    public static final Component TOGGLE_BRIDGING = Component.translatable("notif.utility_core_qol.toggle_bridging").withStyle(ChatFormatting.GOLD).append(": ");

    public static final Component ON = Component.translatable("notif.utility_core_qol.action.enabled").withStyle(ChatFormatting.GREEN);
    public static final Component OFF = Component.translatable("notif.utility_core_qol.action.disabled").withStyle(ChatFormatting.RED);

}
