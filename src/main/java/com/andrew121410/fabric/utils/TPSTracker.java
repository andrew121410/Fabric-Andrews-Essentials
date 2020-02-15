package com.andrew121410.fabric.utils;

import java.util.*;

public class TPSTracker {

    private long lastTime = System.nanoTime();
    private List<Double> history;

    public TPSTracker() {
        history = new ArrayList<>();

        Timer timer = new Timer();

        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                ok();
            }
        }, 1000, 50);
    }

    public void ok() {
        long startTime = System.nanoTime();
        long timeSpent = (startTime - lastTime) / 1000;
        if (timeSpent == 0L) {
            timeSpent = 1;
        }
        if (history.size() > 10) {
            history.remove(0);
        }
        int intervalTicks = 50;
        double tps = intervalTicks * 1_000_000.0 / timeSpent;
        if (tps <= 21) {
            history.add(tps);
        }
        lastTime = startTime;
    }

    public IntSummaryStatistics getIntSummaryStatistics() {
        return history.stream().mapToInt(Double::intValue).summaryStatistics();
    }

    public double getTps() {
        return getIntSummaryStatistics().getSum();
    }
}
