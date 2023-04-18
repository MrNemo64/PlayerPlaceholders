package me.playerplaceholders.data.entries;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataEntry;
import org.bukkit.ChatColor;

import java.util.Optional;

public class ChatColorPlayerPlaceholderDataEntry implements PlayerPlaceholderDataEntry<ChatColor> {

    private ChatColor chatColor;

    public ChatColorPlayerPlaceholderDataEntry() {
        this(null);
    }

    public ChatColorPlayerPlaceholderDataEntry(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    @Override
    public Optional<ChatColor> getValue() {
        return Optional.ofNullable(chatColor);
    }

    @Override
    public boolean setValue(ChatColor value) {
        this.chatColor = value;
        return true;
    }

    @Override
    public Optional<ChatColor> getDefault() {
        return Optional.of(ChatColor.BLACK);
    }

    @Override
    public JsonElement serialize() {
        return getValue()
                .map(ChatColor::name)
                .map(JsonPrimitive::new)
                .map(JsonElement.class::cast)
                .orElse(JsonNull.INSTANCE);
    }

}
