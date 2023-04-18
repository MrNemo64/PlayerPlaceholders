package me.nemo_64.playerplaceholders.api;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface PlayerPlaceholderManager {
    CompletableFuture<Boolean> save(UUID playerUUID);

    Optional<PlayerPlaceholderData> getLoadedPlayerData(UUID playerUUID);

    CompletableFuture<PlayerPlaceholderData> getPlayerData(UUID playerUUID);

    boolean isLoaded(UUID playerUUID);
}
