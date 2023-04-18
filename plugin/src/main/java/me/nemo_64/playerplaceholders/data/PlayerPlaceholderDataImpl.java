package me.nemo_64.playerplaceholders.data;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderData;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataEntriesRegistry;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataEntry;
import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataGenerator;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PlayerPlaceholderDataImpl implements PlayerPlaceholderData {

    private final Map<String, JsonElement> unparsedEntries = new HashMap<>();
    private final Map<String, PlayerPlaceholderDataEntry<?>> entries;
    private final PlayerPlaceholderDataEntriesRegistry registry;
    private final UUID player;

    public PlayerPlaceholderDataImpl(Collection<PlayerPlaceholderDataEntry<?>> entries,
                                     UUID player,
                                     Map<String, JsonElement> unparsedEntries,
                                     PlayerPlaceholderDataEntriesRegistry registry) {
        this.entries = new ConcurrentHashMap<>(entries.stream().collect(Collectors.toMap(
                PlayerPlaceholderDataEntry::placeholder,
                Function.identity()
        )));
        this.unparsedEntries.putAll(unparsedEntries);
        this.registry = registry;
        this.player = player;
    }

    @Override
    public boolean addEntry(PlayerPlaceholderDataEntry<?> entry) {
        if(entry == null || entries.containsKey(entry.placeholder()))
           return false;
        entries.put(entry.placeholder(), entry);
        return true;
    }

    @Override
    public Optional<? extends PlayerPlaceholderDataEntry<?>> getEntryByPlaceholder(String placeholder) {
        if(entries.containsKey(placeholder))
            return Optional.of(entries.get(placeholder));
        Optional<PlayerPlaceholderDataGenerator<?>> oGenerator = registry.getGeneratorByPlaceholder(placeholder);
        if(oGenerator.isEmpty())
            return Optional.empty();
        PlayerPlaceholderDataGenerator<?> generator = oGenerator.get();
        if(unparsedEntries.containsKey(generator.identifier())) {
            Optional<? extends PlayerPlaceholderDataEntry<?>> data = generator.parse(getPlayer(), unparsedEntries.get(generator.identifier()));
            if(data.isEmpty())
                return Optional.empty();
            unparsedEntries.remove(generator.identifier());
            entries.put(data.get().placeholder(), data.get());
            return data;
        } else {
            Optional<? extends PlayerPlaceholderDataEntry<?>> data = generator.createFor(getPlayer());
            if(data.isEmpty())
                return Optional.empty();
            entries.put(data.get().placeholder(), data.get());
            return data;
        }
    }

    public Collection<? extends PlayerPlaceholderDataEntry<?>> entries() {
        return Collections.unmodifiableCollection(entries.values());
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        unparsedEntries.forEach(obj::add);
        entries.forEach((key, value) -> obj.add(value.identifier(), value.serialize()));
        return obj;
    }

    @Override
    public UUID getPlayer() {
        return player;
    }
}
