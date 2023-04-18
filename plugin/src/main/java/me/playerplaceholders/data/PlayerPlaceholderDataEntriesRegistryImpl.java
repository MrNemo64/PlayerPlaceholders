package me.playerplaceholders.data;

import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataEntriesRegistry;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataGenerator;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class PlayerPlaceholderDataEntriesRegistryImpl implements PlayerPlaceholderDataEntriesRegistry {

    private final Map<String, PlayerPlaceholderDataGenerator<?>> generators = new HashMap<>();

    @Override
    public boolean registerGenerator(PlayerPlaceholderDataGenerator<?> generator) {
        Objects.requireNonNull(generator, "generator");
        if(generators.containsKey(generator.identifier()))
            return false;
        generators.put(generator.identifier(), generator);
        return true;
    }

    @Override
    public void forceRegisterGenerator(PlayerPlaceholderDataGenerator<?> generator) {
        Objects.requireNonNull(generator, "generator");
        generators.put(generator.identifier(), generator);
    }

    @Override
    public Optional<PlayerPlaceholderDataGenerator<?>> getGenerator(String identifier) {
        return Optional.of(generators.get(Objects.requireNonNull(identifier, "identifier")));
    }

    @Override
    public Map<String, PlayerPlaceholderDataGenerator<?>> getGenerators() {
        return Collections.unmodifiableMap(generators);
    }
}
