package me.nemo_64.playerplaceholders.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderData;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderManager;
import me.nemo_64.playerplaceholders.PlayerPlaceholdersPlugin;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class PlayerPlaceholderManagerImpl implements PlayerPlaceholderManager {

    private final Map<UUID, CompletableFuture<Boolean>> ongoingSaveOperations = new ConcurrentHashMap<>();
    private final Map<UUID, CompletableFuture<PlayerPlaceholderData>> ongoingLoadOperations = new ConcurrentHashMap<>();
    private final Map<UUID, PlayerPlaceholderData> loadedPlayers = new ConcurrentHashMap<>();
    private final PlayerPlaceholdersPlugin plugin;
    private final PlayerPlaceholderLoader loader;

    public PlayerPlaceholderManagerImpl(PlayerPlaceholdersPlugin plugin) {
        this.plugin = plugin;
        this.loader = new PlayerPlaceholderLoader(plugin);
    }

    @Override
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
        if(ongoingLoadOperations.containsKey(playerUUID))
            return ongoingLoadOperations.get(playerUUID);
        CompletableFuture<PlayerPlaceholderData> loadingOperation = CompletableFuture.supplyAsync(() -> {
            if(!plugin.getPlayerDataFileAccessor().hasFile(playerUUID)) {
                PlayerPlaceholderData data = createEmpty(playerUUID);
                loadedPlayers.put(playerUUID, data);
                ongoingLoadOperations.remove(playerUUID);
                return data;
            }
            PlayerPlaceholderData data = loader.load(playerUUID).orElseGet(() -> createEmpty(playerUUID));
            loadedPlayers.put(playerUUID, data);
            ongoingLoadOperations.remove(playerUUID);
            return data;
        });
        ongoingLoadOperations.put(playerUUID, loadingOperation);
        return loadingOperation;
    }

    @Override
    public boolean isLoaded(UUID playerUUID) {
        return loadedPlayers.containsKey(playerUUID);
    }

    private PlayerPlaceholderData createEmpty(UUID playerUUID) {
        return new PlayerPlaceholderDataImpl(
                plugin.getGeneratorsRegistry()
                        .getGenerators()
                        .values()
                        .stream()
                        .map(generator -> generator.createFor(playerUUID))
                        .filter(Optional::isEmpty)
                        .map(Optional::get)
                        .collect(Collectors.toList()),
                playerUUID,
                Map.of(),
                plugin.getGeneratorsRegistry());
    }

}
