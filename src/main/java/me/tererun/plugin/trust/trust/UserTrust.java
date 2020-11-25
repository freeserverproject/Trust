package me.tererun.plugin.trust.trust;

import java.util.UUID;

public class UserTrust {
    private UUID user;
    private double point;

    public UserTrust(UUID user, double point) {
        this.user = user;
        this.point = point;
    }

    public UUID getUser() {
        return user;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        double resultPoint = 0;
        if (point >= 0) {
            resultPoint = point;
        }
        this.point = resultPoint;
    }

    public void addPoint(double point) {
        this.setPoint(this.getPoint() + point);
    }
}
