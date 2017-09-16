package com.example.piano.health;

import com.codahale.metrics.health.HealthCheck;

/**
 * Created by adel on 9/15/17.
 */
public class PropsAvailableHealthCheck extends HealthCheck {

    private final String check;

    public PropsAvailableHealthCheck(String check) {
        this.check = check;
    }

    @Override
    protected Result check() throws Exception {
        if (check != null && check.equals("WORKS")) {
            return Result.healthy();
        }
        return Result.unhealthy("check is not WORKS");
    }
}
