package io.jenkins.plugins;

import hudson.model.Action;
import java.util.ArrayList;
import java.util.List;

public class VariablesConsumptionsPreviousBuild implements Action {
    private List<Double> energyConsumptions;
    private List<Double> powerUsages;

    public VariablesConsumptionsPreviousBuild(List<Double> energyConsumptions, List<Double> powerUsages) {
        this.energyConsumptions = (energyConsumptions != null) ? new ArrayList<>(energyConsumptions) : new ArrayList<>();
        this.powerUsages = (powerUsages != null) ? new ArrayList<>(powerUsages) : new ArrayList<>();
    }

    public synchronized void addEnergyConsumption(double energyConsumed) {
        this.energyConsumptions.add(energyConsumed);
    }
    public synchronized void addWattUsage(double wattConsumed) {this.powerUsages.add(wattConsumed);}

    public List<Double> getEnergyConsumptions() {
        return new ArrayList<>(energyConsumptions);
    }
    public List<Double> getPowerUsages() {return new ArrayList<>(powerUsages);}

    @Override
    public String getIconFileName() {
        return null;  // No icon is needed
    }

    @Override
    public String getDisplayName() {
        return "Consumption History";
    }

    @Override
    public String getUrlName() {
        return "consumption-history";
    }
}
