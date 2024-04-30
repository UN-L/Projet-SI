package io.jenkins.plugins;

import hudson.model.Action;

public class EnergyVariablesAction implements Action {
    private final long startTime;
    private final long startConsumption;
    private long energyConsumed;

    public EnergyVariablesAction(long startTime, long startConsumption) {
        this.startTime = startTime;
        this.startConsumption = startConsumption;
    }

    public long getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(long energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public long getStartTime() {
        return startTime;
    }

    public long getStartConsumption() {
        return startConsumption;
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Energy Monitoring";
    }

    @Override
    public String getUrlName() {
        return null;
    }
}
