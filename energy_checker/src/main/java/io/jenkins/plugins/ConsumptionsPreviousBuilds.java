package io.jenkins.plugins;

import hudson.model.Action;
import java.util.ArrayList;
import java.util.List;

public class ConsumptionsPreviousBuilds implements Action {
    private List<Double> energyConsumptions;

    public ConsumptionsPreviousBuilds(List<Double> energyConsumptions) {
        this.energyConsumptions = (energyConsumptions != null) ? new ArrayList<>(energyConsumptions) : new ArrayList<>();
    }

    public synchronized void addEnergyConsumption(double energyConsumed) {
        this.energyConsumptions.add(energyConsumed);
    }

    public List<Double> getEnergyConsumptions() {
        return new ArrayList<>(energyConsumptions);
    }

    @Override
    public String getIconFileName() {
        return null;  // No icon is needed
    }

    @Override
    public String getDisplayName() {
        return "Energy Consumption History";
    }

    @Override
    public String getUrlName() {
        return "energy-consumption-history";
    }
}
