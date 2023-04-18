package me.playerplaceholders.data;

import com.google.gson.JsonObject;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderData;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderManager;
import me.playerplaceholders.PlayerPlaceholdersPlugin;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;

public class PlayerPlaceholderManagerImpl implements PlayerPlaceholderManager {

    private final Map<UUID, CompletableFuture<Boolean>> ongoingSaveOperations = new ConcurrentHashMap<>();
    private final Map<UUID, CompletableFuture<PlayerPlaceholderDataImpl>> ongoingLoadOperations = new ConcurrentHashMap<>();
    private final Map<UUID, PlayerPlaceholderDataImpl> loadedPlayers = new ConcurrentHashMap<>();
    private final PlayerPlaceholdersPlugin plugin;

    public PlayerPlaceholderManagerImpl(PlayerPlaceholdersPlugin plugin) {
        this.plugin = plugin;
    }

    @java.lang.Override
    public CompletableFuture<Boolean> save(UUID playerUUID) {
        if(! loadedPlayers.containsKey(playerUUID))
            return CompletableFuture.completedFuture(false);
        if(ongoingSaveOperations.containsKey(playerUUID))
            return ongoingSaveOperations.get(playerUUID);
        CompletableFuture<Boolean> saveOperation = CompletableFuture.supplyAsync(() -> {
            JsonObject data = loadedPlayers.get(playerUUID).serialize();
            try(BufferedWriter writer = plugin.getPlayerDataFileAccessor().openToWrite(playerUUID)) {
                writer.write(data.toString());
            } catch (IOException e) {
                throw new CompletionException(e);
            } finally {
                ongoingSaveOperations.remove(playerUUID);
            }
            return true;
        });
        ongoingSaveOperations.put(playerUUID, saveOperation);
        return saveOperation;
    }

    @Override
    public Optional<PlayerPlaceholderData> getLoadedPlayerData(UUID playerUUID) {
        return Optional.ofNullable(loadedPlayers.get(playerUUID));
    }

    @Override
    public CompletableFuture<PlayerPlaceholderData> getPlayerData(UUID playerUUID) {
        if(isLoaded(playerUUID))
            return CompletableFuture.completedFuture(loadedPlayers.get(playerUUID));
        return CompletableFuture.supplyAsync(() -> null); // TODO
    }

    @Override
    public boolean isLoaded(UUID playerUUID) {
        return loadedPlayers.containsKey(playerUUID);
    }

}
