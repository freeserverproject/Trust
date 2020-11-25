package me.tererun.plugin.trust.event;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import java.util.UUID;

public class UserTrustEvent extends Event {
    private static final HandlerList handlers = new HandlerList();
    private UUID user;

    public UserTrustEvent(UUID user) {
        this.user = user;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
