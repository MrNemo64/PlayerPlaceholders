package me.playerplaceholders.data;

import com.google.gson.JsonObject;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderData;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataEntry;

import java.util.Map;

public class PlayerPlaceholderDataImpl implements PlayerPlaceholderData {

    private final Map<String, PlayerPlaceholderDataEntry<?>> entries;

    public PlayerPlaceholderDataImpl(Map<String, PlayerPlaceholderDataEntry<?>> entries) {
        this.entries = entries;
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        entries.forEach((key, value) -> obj.add(key, value.serialize()));
        return obj;
    }

}
