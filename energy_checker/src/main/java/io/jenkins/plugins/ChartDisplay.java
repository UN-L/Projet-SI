package io.jenkins.plugins;

import hudson.model.Run;
import jenkins.model.RunAction2;

public class ChartDisplay implements RunAction2 {

    private transient Run run;
    private long dataToDisplay;

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
        if (action != null) {
            setDataToDisplay(action.getEnergyConsumed());
        }
    }

    @Override
    public void onLoad(Run<?, ?> run) {
        this.run = run;
    }

    public Run getRun() {
        return run;
    }

    public void setDataToDisplay(long dataToDisplay) {
        this.dataToDisplay = dataToDisplay;
    }

    public String getGraphDataAsJson() {
        return "[" + dataToDisplay + "]";
    }
}
