package io.jenkins.plugins;

import hudson.model.Run;
import jenkins.model.RunAction2;

import java.util.ArrayList;
import java.util.List;

public class ChartDisplay implements RunAction2 {

    private transient Run run;
    private double dataToDisplay;
    private List<Double> dataToDisplayAll;

    @Override
    public String getIconFileName() {
        return "/plugin/energy_checker/images/chart-histogram.png";
    }

    @Override
    public String getDisplayName() {
        return "Chart Display";
    }

    @Override
    public String getUrlName() {
        return "chart";
    }

    @Override
    public void onAttached(Run<?, ?> run) {
        this.run = run;
        EnergyVariablesAction action = run.getAction(EnergyVariablesAction.class);
        ConsumptionsPreviousBuilds consumptions = run.getAction(ConsumptionsPreviousBuilds.class);
        if (action != null) {
            setDataToDisplay(action.getEnergyConsumed());
        }
        if (consumptions != null) {
            setDataToDisplayAll(consumptions.getEnergyConsumptions());
        }
    }

    @Override
    public void onLoad(Run<?, ?> run) {
        this.run = run;
    }

    public Run getRun() {
        return run;
    }

    public void setDataToDisplay(double dataToDisplay) {
        this.dataToDisplay = dataToDisplay;
    }

    public void setDataToDisplayAll(List<Double> dataToDisplayAll) {
        this.dataToDisplayAll = dataToDisplayAll;
    }


    public String getGraphDataAsJsonCurrent() {
        return "[" + dataToDisplay + "]";
    }

    public String getGraphDataAsJsonAll() {
        return dataToDisplayAll != null ? dataToDisplayAll.toString() : "[]";
    }

    public String getLabelsAsJson() {
        int dataLength = dataToDisplayAll.size();
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < dataLength; i++) {
            if (i == dataLength - 1) {
                labels.add("'CurrentBuild'");
            } else if (i == dataLength - 2) {
                labels.add("'PreviousBuild'");
            } else {
                labels.add("'CurrentBuild-" + (dataLength - i - 1)+"'");
            }
        }
        return labels.toString();
    }
}
