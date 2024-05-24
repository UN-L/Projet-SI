package io.jenkins.plugins;

import hudson.model.Action;
import java.util.List;

public class VariablesStagesOfABuild implements Action {
    private List<String> stagesNames;
    private List<ValuesEnergetic> stagesEnergeticValues;
    private List<Double> stagesDurations;

    public VariablesStagesOfABuild(
            List<String> stagesNames, List<Double> stagesDurations, List<ValuesEnergetic> stagesEnergeticValues) {
        this.stagesNames = stagesNames;
        this.stagesDurations = stagesDurations;
        this.stagesEnergeticValues = stagesEnergeticValues;
    }

    public List<String> getStagesNames() {
        return stagesNames;
    }

    public List<Double> getStagesDurations() {
        return stagesDurations;
    }

    public List<ValuesEnergetic> getStagesEnergeticValues() {
        return stagesEnergeticValues;
    }

    public void addValuesOfBuild(String stageName, double stageDuration, ValuesEnergetic valuesEnergetic) {
        stagesNames.add(stageName);
        stagesDurations.add(stageDuration);
        stagesEnergeticValues.add(valuesEnergetic);
    }

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return null;
    }

    @Override
    public String getUrlName() {
        return null;
    }
}
