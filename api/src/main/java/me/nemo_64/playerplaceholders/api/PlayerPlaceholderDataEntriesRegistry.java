package me.nemo_64.playerplaceholders.api;

import java.util.Map;
import java.util.Optional;

public interface PlayerPlaceholderDataEntriesRegistry {
    boolean registerGenerator(PlayerPlaceholderDataGenerator<?> generator);

    void forceRegisterGenerator(PlayerPlaceholderDataGenerator<?> generator);

    Optional<PlayerPlaceholderDataGenerator<?>> getGeneratorByIdentifier(String identifier);
    Optional<PlayerPlaceholderDataGenerator<?>> getGeneratorByPlaceholder(String placeholder);

    Map<String, PlayerPlaceholderDataGenerator<?>> getGenerators();
}
