package me.nemo_64.playerplaceholders.api;

import com.google.gson.JsonObject;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

public interface PlayerPlaceholderData {

    Collection<? extends PlayerPlaceholderDataEntry<?>> entries();

    boolean addEntry(PlayerPlaceholderDataEntry<?> entry);

    Optional<? extends PlayerPlaceholderDataEntry<?>> getEntryByPlaceholder(String placeholder);

    JsonObject serialize();

    UUID getPlayer();

}
