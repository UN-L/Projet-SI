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
public class EnergyMonitoringRunListener extends RunListener<Run<?, ?>> {

    @Override
    public void onInitialize(@NonNull Run<?, ?> run) {
        run.addAction(new EnergyVariablesAction(System.currentTimeMillis(), lectureConsommation()));
    }

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        super.onStarted(run, listener);
        listener.getLogger().println("Début de la surveillance de la consommation d'énergie. (onStarted)");
        listener.getLogger().println("Temps à onInitialize() : " + run.getAction(EnergyVariablesAction.class).getStartTime());
        listener.getLogger().println("Temps actuel : "+System.currentTimeMillis());
    }

    @Override
    public void onCompleted(Run<?, ?> run, @NonNull TaskListener listener) {
        listener.getLogger().println("onCompleted() launched");
        EnergyVariablesAction action = run.getAction(EnergyVariablesAction.class);
        if (action != null) {
            double startTime = action.getStartTime();
            double startConsumption = action.getStartConsumption();
            double endTime = System.currentTimeMillis();
            listener.getLogger().println("lauching lectureConsommation()");
            double endConsumption = lectureConsommation();
            listener.getLogger().println("end of lectureConsommation()");
            listener.getLogger().println("endConsumption = " + endConsumption);
            listener.getLogger().println("endTime = " + endTime);
            listener.getLogger().println("startTime = " + startTime);
            listener.getLogger().println("startConsumption = " + startConsumption);
            double energyConsumed = calculateEnergyConsumption(startTime, endTime, startConsumption, endConsumption);
            listener.getLogger().println("Consommation d'énergie pendant le build : " + energyConsumed + " Watts.");
            action.setEnergyConsumed(energyConsumed);
            double previousBuildConsumption = getPreviousBuildEnergyConsumed(run);
            listener.getLogger().println("previousBuildConsumption = " + previousBuildConsumption);

            List<Double> previousConsumptions = getPreviousBuildsConsumption(run);
            ConsumptionsPreviousBuilds historyConsumptions = new ConsumptionsPreviousBuilds(previousConsumptions);
            run.addAction(historyConsumptions);
            historyConsumptions.addEnergyConsumption(energyConsumed);
            listener.getLogger().println("History of Consumptions Updated: " + historyConsumptions.getEnergyConsumptions());


            listener.getLogger().println("Launching chart");
            run.addAction(new ChartDisplay());
        }
    }

    @Override
    public void onFinalized(@NonNull Run<?, ?> run) {}

    private double calculateEnergyConsumption(
            double startTime, double endTime, double startConsumption, double endConsumption) {
        double consumption = endConsumption - startConsumption;
        double duration = (endTime - startTime) / 1000;
        return consumption / duration / 1000000;
    }

    private double lectureConsommation() {
        // String chemin = System.getProperty("user.home") + "/fichier_pour_lecture_jenkins/consumption.txt";
        String chemin = "/sys/devices/virtual/powercap/intel-rapl/intel-rapl:0/energy_uj";
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            String ligne = lecteur.readLine();
            if (ligne != null) {
                return Double.parseDouble(ligne);
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return 0;
    }

    public double getPreviousBuildEnergyConsumed(Run<?, ?> currentRun) {
        Run<?, ?> previousBuild = currentRun.getPreviousBuild();
        if (previousBuild != null) {
            EnergyVariablesAction action = previousBuild.getAction(EnergyVariablesAction.class);
            if (action != null) {
                return action.getEnergyConsumed();
            }
        }
        return 0;
    }

    public List<Double> getPreviousBuildsConsumption(Run<?, ?> currentRun) {
        Run<?, ?> previousBuild = currentRun.getPreviousBuild();
        if (previousBuild != null) {
            ConsumptionsPreviousBuilds action = previousBuild.getAction(ConsumptionsPreviousBuilds.class);
            if (action != null) {
                return action.getEnergyConsumptions();
            }
        }
        return Collections.emptyList();
    }
}
