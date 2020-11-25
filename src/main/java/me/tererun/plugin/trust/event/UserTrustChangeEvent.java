package me.tererun.plugin.trust.event;

import org.bukkit.event.Cancellable;

import java.util.UUID;

public class UserTrustChangeEvent extends UserTrustEvent implements Cancellable {
    private double point;
    private boolean cancel;
    private String reason;

    public UserTrustChangeEvent(UUID user, double point, String reason) {
        super(user);
        this.point = point;
        this.cancel = false;
        this.reason = reason;
    }

    public double getPoint() {
        return point;
    }

    public String getReason() {
        return reason;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    @Override
    public boolean isCancelled() {
        return cancel;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
}
