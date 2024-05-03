package io.jenkins.plugins;

import hudson.model.Action;

public class EnergyVariablesAction implements Action {
    private final double startTime;
    private final double startConsumption;
    private double energyConsumed;

    public EnergyVariablesAction(double startTime, double startConsumption) {
        this.startTime = startTime;
        this.startConsumption = startConsumption;
    }

    public double getEnergyConsumed() {
        return energyConsumed;
    }

    public void setEnergyConsumed(double energyConsumed) {
        this.energyConsumed = energyConsumed;
    }

    public double getStartTime() {
        return startTime;
    }

    public double getStartConsumption() {
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
