package io.jenkins.plugins;

import hudson.model.Action;

public class EnergyAction implements Action {
    private final long startTime;
    private final long startConsumption;

    public EnergyAction(long startTime, long startConsumption) {
        this.startTime = startTime;
        this.startConsumption = startConsumption;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStartConsumption() {
        return startConsumption;
    }

    @Override
    public String getIconFileName() {return null;}

    @Override
    public String getDisplayName() { return "Energy Monitoring"; }

    @Override
    public String getUrlName() { return null; }
}
