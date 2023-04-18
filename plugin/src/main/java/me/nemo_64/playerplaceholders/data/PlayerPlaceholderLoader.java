package me.nemo_64.playerplaceholders.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.nemo_64.playerplaceholders.PlayerPlaceholdersPlugin;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderData;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletionException;

public class PlayerPlaceholderLoader {

    private final PlayerPlaceholdersPlugin plugin;

    public PlayerPlaceholderLoader(PlayerPlaceholdersPlugin plugin) {
        this.plugin = plugin;
    }

    public Optional<PlayerPlaceholderData> load(UUID playerUUID) {
        try(BufferedReader reader = plugin.getPlayerDataFileAccessor().openToRead(playerUUID)) {
            JsonElement root = new JsonParser().parse(reader);
            if(!root.isJsonObject())
                return Optional.empty();
            JsonObject obj = root.getAsJsonObject();
            
        } catch (IOException e) {
            throw new CompletionException(e);
        }
    }

}
