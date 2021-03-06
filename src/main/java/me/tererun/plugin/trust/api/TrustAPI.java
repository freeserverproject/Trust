package me.tererun.plugin.trust.api;

import me.tererun.plugin.trust.Trust;
import me.tererun.plugin.trust.event.UserTrustChangeEvent;
import me.tererun.plugin.trust.trust.UserTrust;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.UUID;

public class TrustAPI {
    public List<UserTrust> getAllUserTrust() {
        return Trust.getDatabaseController().loadAllData("trust", null);
    }

    public UserTrust getUserTrust(UUID uuid) {
        if (Trust.getDatabaseController().getCount("trust", uuid.toString()) == 0) {
            Trust.getDatabaseController().addData("trust", new UserTrust(uuid, Trust.defaultScore));
        }
        return Trust.getDatabaseController().loadData("trust", uuid.toString());
    }

    public void setPoint(UUID uuid, double point, String reason) {
        UserTrustChangeEvent userTrustChangeEvent = new UserTrustChangeEvent(uuid, point, reason);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().getPluginManager().callEvent(userTrustChangeEvent);
            }
        }.runTask(Trust.getPlugin());
        if (userTrustChangeEvent.isCancelled()) return;
        UserTrust userTrust = this.getUserTrust(uuid);
        userTrust.setPoint(userTrustChangeEvent.getPoint());
        Trust.getDatabaseController().addData("trust", userTrust);
    }

    public void addPoint(UUID uuid, double point, String reason) {
        UserTrust userTrust = this.getUserTrust(uuid);
        double resultPoint = userTrust.getPoint() + point;
        UserTrustChangeEvent userTrustChangeEvent = new UserTrustChangeEvent(uuid, resultPoint, reason);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getServer().getPluginManager().callEvent(userTrustChangeEvent);
            }
        }.runTask(Trust.getPlugin());
        if (userTrustChangeEvent.isCancelled()) return;
        userTrust.setPoint(userTrustChangeEvent.getPoint());
        Trust.getDatabaseController().addData("trust", userTrust);
        //userTrust変更
    }

    public double getPoint(UUID uuid) {
        UserTrust userTrust = this.getUserTrust(uuid);
        return userTrust.getPoint();
    }
}
