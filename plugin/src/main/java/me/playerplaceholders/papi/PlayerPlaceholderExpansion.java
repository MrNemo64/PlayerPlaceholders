package me.playerplaceholders.papi;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import me.playerplaceholders.PlayerPlaceholdersPlugin;
import org.bukkit.OfflinePlayer;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class PlayerPlaceholderExpansion extends PlaceholderExpansion {

    private final PlayerPlaceholdersPlugin plugin;

    public PlayerPlaceholderExpansion(PlayerPlaceholdersPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public @Nullable String onRequest(OfflinePlayer player, @NotNull String params) {
        if(player == null)
            return null;
        return "";
    }

    @Override
    public @NotNull String getIdentifier() {
        return plugin.getConfig().getString("papi.identifier", "player-placeholder");
    }

    @Override
    public @NotNull String getAuthor() {
        return String.join(", ", plugin.getDescription().getAuthors());
    }

    @Override
    public @NotNull String getVersion() {
        return plugin.getDescription().getVersion();
    }

    @Override
    public @NotNull String getName() {
        return plugin.getDescription().getName();
    }
}
