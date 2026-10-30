package com.skd.utilitycore.compat;

import com.mojang.logging.LogUtils;
import com.skd.utilitycore.Config;
import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.FilePackResources;
import net.minecraft.server.packs.PackLocationInfo;
import net.minecraft.server.packs.PackSelectionConfig;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.PathPackResources;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraft.util.FileUtil;
import net.neoforged.fml.loading.FMLPaths;
import net.neoforged.neoforge.event.AddPackFindersEvent;
import org.slf4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * Loads every datapack (.zip or folder) found in a folder inside the game
 * directory into every world automatically, mirroring the Global Packs mod.
 * Packs are registered with required=true, so they are always enabled without
 * having to toggle them per world. Fires for both dedicated servers and
 * single-player (integrated) servers via AddPackFindersEvent.
 */
public final class DataPackFolderLoader {

    private static final Logger LOGGER = LogUtils.getLogger();

    private DataPackFolderLoader() {
    }

    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() != PackType.SERVER_DATA) return;
        if (!Config.DATA_PACK_FOLDER_ENABLED.get()) return;

        Path folder = FMLPaths.GAMEDIR.get().resolve(Config.DATA_PACK_FOLDER_PATH.get()).normalize();
        event.addRepositorySource(consumer -> loadPacks(folder, consumer));
    }

    private static void loadPacks(Path folder, Consumer<Pack> consumer) {
        try {
            FileUtil.createDirectoriesSafe(folder);
            try (Stream<Path> stream = Files.list(folder)) {
                for (Path child : stream.toList()) {
                    Pack.ResourcesSupplier supplier;
                    if (Files.isDirectory(child)) {
                        supplier = new PathPackResources.PathResourcesSupplier(child);
                    } else {
                        String fileName = child.getFileName().toString();
                        if (!fileName.endsWith(".zip")) continue;
                        supplier = new FilePackResources.FileResourcesSupplier(child);
                    }

                    String packId = child.getFileName().toString();
                    PackLocationInfo info = new PackLocationInfo(
                            packId,
                            Component.literal(packId),
                            PackSource.BUILT_IN,
                            Optional.empty()
                    );
                    Pack pack = Pack.readMetaAndCreate(info, supplier, PackType.SERVER_DATA,
                            new PackSelectionConfig(true, Pack.Position.TOP, false));
                    if (pack != null) {
                        consumer.accept(pack);
                    }
                }
            }
        } catch (IOException e) {
            LOGGER.warn("[DataPackFolder] Failed to load datapacks from {}: {}", folder, e.getMessage());
        }
    }
}
