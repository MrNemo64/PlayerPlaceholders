package me.nemo_64.playerplaceholders.data;

import me.nemo_64.playerplaceholders.PlayerPlaceholdersPlugin;

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

    public boolean hasFile(UUID playerUUID) {
        Path file = resolvePlayer(playerUUID);
        return Files.exists(file) && Files.isRegularFile(file);
    }

    public BufferedWriter openToWrite(UUID playerUUID) throws IOException {
        Path file = resolvePlayer(playerUUID);
        if(! Files.exists(file))
            createPlayerFile(file);
        return Files.newBufferedWriter(file, StandardCharsets.UTF_8);
    }

    public BufferedReader openToRead(UUID playerUUID) throws IOException {
        Path file = resolvePlayer(playerUUID);
        if(! Files.exists(file))
            createPlayerFile(file);
        return Files.newBufferedReader(file, StandardCharsets.UTF_8);
    }

    public Path resolvePlayer(UUID playerUUID) {
        return getPlayerDataFolder().resolve(playerUUID.toString() + ".json");
    }

    private void createPlayerFile(Path file) throws IOException {
        if(!Files.exists(file.getParent()))
            Files.createDirectories(file.getParent());
        Files.createFile(file);
    }

    public Path getPlayerDataFolder() {
        return playerDataFolder;
    }
}
