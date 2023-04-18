package me.nemo_64.playerplaceholders.data.entries;

import com.google.gson.JsonElement;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataGenerator;
import org.bukkit.ChatColor;

import java.util.Optional;
import java.util.UUID;
import java.util.stream.Stream;

public class ChatColorPlayerPlaceholderDataEntryGenerator implements PlayerPlaceholderDataGenerator<ChatColorPlayerPlaceholderDataEntry> {

    @Override
    public Optional<? extends ChatColorPlayerPlaceholderDataEntry> parse(UUID playerUUID, JsonElement element) {
        if(element.isJsonNull())
            return Optional.of(new ChatColorPlayerPlaceholderDataEntry());
        if(element.isJsonPrimitive() && element.getAsJsonPrimitive().isString()) {
            String color = element.getAsJsonPrimitive().getAsString();
            return Stream.of(ChatColor.values())
                    .filter((c) -> c.name().equals(color))
                    .findFirst()
                    .map(ChatColorPlayerPlaceholderDataEntry::new);
        }
        return Optional.empty();
    }

    @Override
    public Optional<? extends ChatColorPlayerPlaceholderDataEntry> createFor(UUID playerUUID) {
        return Optional.of(new ChatColorPlayerPlaceholderDataEntry());
    }

    @Override
    public String identifier() {
        return ChatColorPlayerPlaceholderDataEntryGenerator.class.getName();
    }
}
