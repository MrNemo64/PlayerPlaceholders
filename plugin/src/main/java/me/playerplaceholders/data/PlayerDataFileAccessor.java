package me.playerplaceholders.data;

import me.playerplaceholders.PlayerPlaceholdersPlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class PlayerDataFileAccessor {

    private final Path playerDataFolder;

    public PlayerDataFileAccessor(PlayerPlaceholdersPlugin plugin) {
        this.playerDataFolder = plugin.getDataFolder().toPath().resolve("player_data");
    }

    public BufferedWriter openToWrite(UUID playerUUID) throws IOException {
        Path file = resolvePlayer(playerUUID);
        return Files.newBufferedWriter(file, StandardCharsets.UTF_8);
    }

    public BufferedReader openToRead(UUID playerUUID) throws IOException {
        Path file = resolvePlayer(playerUUID);
        return Files.newBufferedReader(file, StandardCharsets.UTF_8);
    }

    public Path resolvePlayer(UUID playerUUID) throws IOException {
        Path file = getPlayerDataFolder().resolve(playerUUID.toString() + ".json");
        if(! Files.exists(file)) {
            if(!Files.exists(file.getParent()))
                Files.createDirectories(file.getParent());
            Files.createFile(file);
        }
        return file;
    }

    public Path getPlayerDataFolder() {
        return playerDataFolder;
    }
}
