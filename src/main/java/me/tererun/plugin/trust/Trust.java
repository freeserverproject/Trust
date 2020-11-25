package me.tererun.plugin.trust;

import me.tererun.plugin.trust.api.TrustAPI;
import me.tererun.plugin.trust.database.DatabaseController;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class Trust extends JavaPlugin {

    private static Plugin plugin;
    private TrustAPI trustAPI;
    private static DatabaseController databaseController;
    public static String prefix = "§3Trust§f> ";

    @Override
    public void onEnable() {
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
}
