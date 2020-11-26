package me.tererun.plugin.trust;

import me.tererun.plugin.trust.api.TrustAPI;
import me.tererun.plugin.trust.database.DatabaseController;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Trust extends JavaPlugin {

    private static Plugin plugin;
    private static FileConfiguration config;

    private TrustAPI trustAPI;
    private static DatabaseController databaseController;

    public static String prefix = "§3Trust§f> ";
    public static double defaultScore;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        config = getConfig();
        defaultScore = config.getDouble("DefaultScore");
        plugin = this;
        databaseController = new DatabaseController("userdata.db", "trust");
        trustAPI = new TrustAPI();
    }

    public static Plugin getPlugin() {
        return plugin;
    }

    public TrustAPI getTrustAPI() {
        return trustAPI;
    }

    public static DatabaseController getDatabaseController() {
        return databaseController;
    }

    public static FileConfiguration getFileConfigration() {
        return config;
    }
}
