package me.nemo_64.playerplaceholders.api.util;

import me.nemo_64.playerplaceholders.api.PlayerPlaceholderDataKey;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class PlayerPlaceholderDataKeyMap<T> implements Map<PlayerPlaceholderDataKey, T> {

    private final Map<String, T> identifyByIdentifier;
    private final Map<String, T> identifyByPlaceholder;
    private final Map<PlayerPlaceholderDataKey, T> identifyByKey;

    public PlayerPlaceholderDataKeyMap() {
        this(HashMap::new, HashMap::new, HashMap::new);
    }

    public PlayerPlaceholderDataKeyMap(Supplier<Map<String, T>> identifyByIdentitySupplier,
                                       Supplier<Map<String, T>> identifyByPlaceholderSupplier,
                                       Supplier<Map<PlayerPlaceholderDataKey, T>> identifyByKeySupplier) {
        this.identifyByIdentifier = identifyByIdentitySupplier.get();
        this.identifyByPlaceholder = identifyByPlaceholderSupplier.get();
        this.identifyByKey = identifyByKeySupplier.get();
    }

    @Override
    public int size() {
        return identifyByKey.size();
    }

    @Override
    public boolean isEmpty() {
        return identifyByKey.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        if (! (key instanceof PlayerPlaceholderDataKey))
            throw new IllegalArgumentException("key is not a type of PlayerPlaceholderDataKey");
        return identifyByKey.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return identifyByKey.containsValue(value);
    }

    @Override
    public T get(Object key) {
        if (! (key instanceof PlayerPlaceholderDataKey))
            throw new IllegalArgumentException("key is not a type of PlayerPlaceholderDataKey");
        return identifyByKey.get(key);
    }

    @Override
    public T put(PlayerPlaceholderDataKey key, T value) {
        identifyByKey.put(key, value);
        identifyByPlaceholder.put(key.placeholder(), value);
        return identifyByIdentifier.put(key.identifier(), value);
    }

    @Override
    public T remove(Object key) {
        if (! (key instanceof PlayerPlaceholderDataKey))
            throw new IllegalArgumentException("key is not a type of PlayerPlaceholderDataKey");
        return identifyByKey.remove(key);
    }

    @Override
    public void putAll(Map<? extends PlayerPlaceholderDataKey, ? extends T> m) {
        m.forEach(this::put);
    }

    @Override
    public void clear() {
        identifyByIdentifier.clear();
        identifyByPlaceholder.clear();
        identifyByKey.clear();
    }

    @Override
    public Set<PlayerPlaceholderDataKey> keySet() {
        return identifyByKey.keySet();
    }

    @Override
    public Collection<T> values() {
        return identifyByKey.values();
    }

    @Override
    public Set<Entry<PlayerPlaceholderDataKey, T>> entrySet() {
        return identifyByKey.entrySet();
    }
}
