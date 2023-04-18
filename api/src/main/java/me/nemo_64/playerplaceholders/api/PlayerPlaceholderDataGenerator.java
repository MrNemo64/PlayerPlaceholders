package me.nemo_64.playerplaceholders.api;

import com.google.gson.JsonElement;

import java.util.Optional;
import java.util.UUID;

public interface PlayerPlaceholderDataGenerator<T extends PlayerPlaceholderDataEntry<?>> {

    Optional<? extends T> parse(UUID playerUUID, JsonElement element);

    Optional<? extends T> createFor(UUID playerUUID);

    String identifier();

}