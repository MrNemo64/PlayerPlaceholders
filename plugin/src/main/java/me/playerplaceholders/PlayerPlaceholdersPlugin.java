package me.playerplaceholders;

import me.nemo_64.playerplaceholders.api.PlayerPlaceholders;
import me.playerplaceholders.data.PlayerDataFileAccessor;
import me.playerplaceholders.data.PlayerPlaceholderDataEntriesRegistryImpl;
import me.playerplaceholders.data.PlayerPlaceholderManagerImpl;
import me.playerplaceholders.data.entries.ChatColorPlayerPlaceholderDataEntryGenerator;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class PlayerPlaceholdersPlugin extends JavaPlugin implements PlayerPlaceholders {

    private PlayerPlaceholderManagerImpl playerPlaceholderManager;
    private PlayerPlaceholderDataEntriesRegistryImpl generatorsRegistry;
    private PlayerDataFileAccessor playerDataFileAccessor;

    @Override
    public void onEnable() {
        playerDataFileAccessor = new PlayerDataFileAccessor(this);
        playerPlaceholderManager = new PlayerPlaceholderManagerImpl(this);
        generatorsRegistry = new PlayerPlaceholderDataEntriesRegistryImpl();

        // Register plugin provided placeholders
        generatorsRegistry.registerGenerator(new ChatColorPlayerPlaceholderDataEntryGenerator());

        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") == null)  {
            getLogger().info("Could not find PlaceholderAPI, placeholders will not work but you may still use the plugin.");
        } else {

        }
    }

    @Override
    public PlayerPlaceholderManagerImpl getPlayerPlaceholderManager() {
        if(playerPlaceholderManager == null)
            throw new IllegalStateException("Plugin is not yet initialized");
        return playerPlaceholderManager;
    }

    @Override
    public PlayerPlaceholderDataEntriesRegistryImpl getGeneratorsRegistry() {
        if(generatorsRegistry == null)
            throw new IllegalStateException("Plugin is not yet initialized");
        return generatorsRegistry;
    }

    public PlayerDataFileAccessor getPlayerDataFileAccessor() {
        if(playerDataFileAccessor == null)
            throw new IllegalStateException("Plugin is not yet initialized");
        return playerDataFileAccessor;
    }
}
