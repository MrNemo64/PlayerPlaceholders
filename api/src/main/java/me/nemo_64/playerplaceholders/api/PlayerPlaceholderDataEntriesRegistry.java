package me.nemo_64.playerplaceholders.api;

import java.util.Map;
import java.util.Optional;

public interface PlayerPlaceholderDataEntriesRegistry {
    boolean registerGenerator(PlayerPlaceholderDataGenerator<?> generator);

    void forceRegisterGenerator(PlayerPlaceholderDataGenerator<?> generator);

    Optional<PlayerPlaceholderDataGenerator<?>> getGenerator(String identifier);

    Map<String, PlayerPlaceholderDataGenerator<?>> getGenerators();
}
