package io.jenkins.plugins;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Extension
public class ListenerRunEnergyMonitoring extends RunListener<Run<?, ?>> {

    @Override
    public void onInitialize(@NonNull Run<?, ?> run) {
        run.addAction(new VariablesConsumptionAction(System.currentTimeMillis(), ScriptGetEnergeticValues.lectureConsommation()));
    }

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        super.onStarted(run, listener);
        listener.getLogger().println("Début de la surveillance de la consommation d'énergie. (onStarted)");
        listener.getLogger().println("Temps à onInitialize() : " + run.getAction(VariablesConsumptionAction.class).getStartTime());
        listener.getLogger().println("Temps actuel : "+ System.currentTimeMillis());
    }

    @Override
    public void onCompleted(Run<?, ?> run, @NonNull TaskListener listener) {
        listener.getLogger().println("onCompleted() launched");
        VariablesConsumptionAction action = run.getAction(VariablesConsumptionAction.class);
        if (action != null) {
            double startTime = action.getStartTime();
            double startConsumption = action.getStartConsumption();
            double endTime = System.currentTimeMillis();
            listener.getLogger().println("lauching lectureConsommation()");
            double endConsumption = ScriptGetEnergeticValues.lectureConsommation();
            listener.getLogger().println("end of lectureConsommation()");
            listener.getLogger().println("startTime = " + startTime);
            listener.getLogger().println("startConsumption = " + startConsumption);
            listener.getLogger().println("endTime = " + endTime);
            listener.getLogger().println("endConsumption = " + endConsumption);
            ValuesEnergetic energeticUsages = ScriptGetEnergeticValues.setEnergeticValues((endTime-startTime)/1000,startConsumption,endConsumption);
            listener.getLogger().println("Consommation d'énergie pendant le build : " + energeticUsages.energy + " Joules");
            listener.getLogger().println("Puissance mobilisé lors du build : " + energeticUsages.power + " Watts");
            action.setEnergyConsumed(energeticUsages.energy);
            action.setPowerUsed(energeticUsages.power);
            double previousBuildConsumption = getPreviousBuildEnergyConsumed(run);
            listener.getLogger().println("previousBuildConsumption = " + previousBuildConsumption);

            List<Double> previousEnergies = getPreviousBuildsEnergy(run);
            List<Double> previousPowers = getPreviousBuildsPower(run);
            VariablesConsumptionsPreviousBuild historyConsumptions = new VariablesConsumptionsPreviousBuild(previousEnergies, previousPowers);
            run.addAction(historyConsumptions);
            historyConsumptions.addEnergyConsumption(energeticUsages.energy);
            historyConsumptions.addWattUsage(energeticUsages.power);
            listener.getLogger().println("History of Energy Consumptions Updated: " + historyConsumptions.getEnergyConsumptions());
            listener.getLogger().println("History of Power Usages Updated: " + historyConsumptions.getPowerUsages());


            listener.getLogger().println("Launching chart");
            run.addAction(new DisplayChart());
        }
    }

    @Override
    public void onFinalized(@NonNull Run<?, ?> run) {}

    public double getPreviousBuildEnergyConsumed(Run<?, ?> currentRun) {
        Run<?, ?> previousBuild = currentRun.getPreviousBuild();
        if (previousBuild != null) {
            VariablesConsumptionAction action = previousBuild.getAction(VariablesConsumptionAction.class);
            if (action != null) {
                return action.getEnergyConsumed();
            }
        }
        return 0;
    }

    public List<Double> getPreviousBuildsEnergy(Run<?, ?> currentRun) {
        Run<?, ?> previousBuild = currentRun.getPreviousBuild();
        if (previousBuild != null) {
            VariablesConsumptionsPreviousBuild action = previousBuild.getAction(VariablesConsumptionsPreviousBuild.class);
            if (action != null) {
                return action.getEnergyConsumptions();
            }
        }
        return Collections.emptyList();
    }
    public List<Double> getPreviousBuildsPower(Run<?, ?> currentRun) {
        Run<?, ?> previousBuild = currentRun.getPreviousBuild();
        if (previousBuild != null) {
            VariablesConsumptionsPreviousBuild action = previousBuild.getAction(VariablesConsumptionsPreviousBuild.class);
            if (action != null) {
                return action.getPowerUsages();
            }
        }
        return Collections.emptyList();
    }
}
