package com.andrew121410.fabric.utils;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.LinkedList;

public class TpsHelper {

    private static DecimalFormat decimalFormat = new DecimalFormat("#,###.##");

    private transient long lastPoll = System.nanoTime();
    private final LinkedList<Double> history = new LinkedList<>();

    public TpsHelper() {
        history.add(20d);
        decimalFormat.setRoundingMode(RoundingMode.HALF_UP);
    }

    public void tick(net.minecraft.server.MinecraftServer server) {
        run();
    }

    public void run() {
        final long startTime = System.nanoTime();
        long timeSpent = (startTime - lastPoll) / 1000;
        if (timeSpent == 0) timeSpent = 1;
        if (history.size() > 10) history.remove();
        double tps = 1000000.0 / timeSpent;
        if (tps <= 21) history.add(tps);
        lastPoll = startTime;
    }

    public double getAverageTPS() {
        double avg = 0;
        for (Double f : history) if (f != null) avg += f;
        return avg / history.size();
    }

    public String getFancyTPS() {
        return decimalFormat.format(getAverageTPS());
    }
}