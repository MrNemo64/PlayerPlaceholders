package me.nemo_64.playerplaceholders.api;

import com.google.gson.JsonElement;

import java.util.Optional;

public interface PlayerPlaceholderDataEntry<T> {

    Optional<T> getValue();

    boolean setValue(T value);

    Optional<T> getDefault();

    JsonElement serialize();

}
