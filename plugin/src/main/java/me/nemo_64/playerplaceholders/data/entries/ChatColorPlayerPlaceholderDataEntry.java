package me.nemo_64.playerplaceholders.data.entries;

import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonPrimitive;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataEntry;
import org.bukkit.ChatColor;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Stream;

public class ChatColorPlayerPlaceholderDataEntry implements PlayerPlaceholderDataEntry<ChatColor> {

    public static final Collection<ChatColor> ALLOWED_CHAT_COLORS = Stream.of(ChatColor.values())
            .filter(ChatColor::isColor)
            .toList();
    public static final ChatColor DEFAULT_COLOR = ChatColor.BLACK;

    public static boolean isValidColor(ChatColor color) {
        return color != null && ALLOWED_CHAT_COLORS.contains(color);
    }

    private ChatColor chatColor;

    public ChatColorPlayerPlaceholderDataEntry() {
        this(DEFAULT_COLOR);
    }

    public ChatColorPlayerPlaceholderDataEntry(ChatColor chatColor) {
        this.chatColor = chatColor;
    }

    @Override
    public boolean setValue(ChatColor value) {
        if(!isValidColor(value))
            return false;
        this.chatColor = value;
        return true;
    }

    @Override
    public String onRequest(String params) {
        return getValue()
                .map(ChatColor::getChar)
                .map(c -> ChatColor.COLOR_CHAR + String.valueOf(c))
                .orElse(null);
    }

    @Override
    public Optional<ChatColor> getDefault() {
        return Optional.of(DEFAULT_COLOR);
    }

    @Override
    public Optional<ChatColor> getValue() {
        return Optional.ofNullable(chatColor);
    }

    @Override
    public String placeholder() {
        return "chat_color";
    }

    @Override
    public String identifier() {
        return ChatColorPlayerPlaceholderDataEntryGenerator.class.getName();
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
