package com.skd.utilitycore.admin.orphans;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.logging.LogUtils;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.permissions.Permissions;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import org.slf4j.Logger;

import java.util.concurrent.CompletableFuture;

@EventBusSubscriber(modid = "utility_core_admin")
public class OrphanBlockCommand {

    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        dispatcher.register(Commands.literal("utilitycore")
                .requires(source -> source.permissions().hasPermission(Permissions.COMMANDS_GAMEMASTER))
                .then(Commands.literal("cleanorphans")
                        .executes(ctx -> execute(ctx, false))
                        .then(Commands.literal("dryrun")
                                .executes(ctx -> execute(ctx, true))
                        )
                )
        );
    }

    private static int execute(CommandContext<CommandSourceStack> ctx, boolean dryRun) {
        CommandSourceStack source = ctx.getSource();
        MinecraftServer server = source.getServer();

        source.sendSuccess(() -> Component.literal(
                dryRun
                        ? "[OrphanBlockCleaner] Starting DRY-RUN scan for orphaned blocks... This will take a while."
                        : "[OrphanBlockCleaner] Starting orphan block cleanup... This will take a while. Ensure you have a world backup!"),
                false);

        OrphanBlockCleaner cleaner = new OrphanBlockCleaner(server, dryRun);
        CompletableFuture<OrphanBlockCleaner.CleanupReport> future = cleaner.run();

        future.thenAcceptAsync(report -> {
            StringBuilder msg = new StringBuilder();
            msg.append(dryRun ? "[OrphanBlockCleaner] DRY-RUN complete. " : "[OrphanBlockCleaner] Cleanup complete. ");
            msg.append("Regions scanned: ").append(report.regionsScanned).append(", ");
            msg.append("Regions modified: ").append(report.regionsModified).append(", ");
            msg.append("Chunk sections modified: ").append(report.sectionsModified).append(", ");
            msg.append("Palette entries rewritten: ").append(report.paletteEntriesRewritten).append(", ");
            msg.append("Blocks replaced in loaded chunks: ").append(report.blocksReplaced).append(", ");
            msg.append("Block entities removed: ").append(report.blockEntitiesRemoved);

            if (!report.rewrittenByBlockId.isEmpty()) {
                msg.append("\nBreakdown by block ID:");
                for (var entry : report.rewrittenByBlockId.entrySet()) {
                    msg.append("\n  ").append(entry.getKey()).append(": ").append(entry.getValue());
                }
            }

            source.sendSuccess(() -> Component.literal(msg.toString()), false);
            LOGGER.info("{}", msg);
        }, server::execute).exceptionallyAsync(throwable -> {
            LOGGER.error("[OrphanBlockCleaner] Cleanup failed: {}", throwable.getMessage(), throwable);
            source.sendFailure(Component.literal("[OrphanBlockCleaner] Cleanup failed: " + throwable.getMessage()));
            return null;
        }, server::execute);

        return 1;
    }
}
