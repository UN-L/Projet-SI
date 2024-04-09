package io.jenkins.plugins;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

@Extension
public class EnergyMonitoringRunListener extends RunListener<Run<?, ?>> {

    @Override
    public void onInitialize(@NonNull Run<?, ?> run) {}

    @Override
    public void onFinalized(@NonNull Run<?, ?> run) {}

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        listener.getLogger().println("Début de la surveillance de la consommation d'énergie. (onStarted)");
        run.addAction(new EnergyAction(System.currentTimeMillis(),lectureConsommation()));
    }

    @Override
    public void onCompleted(Run<?, ?> run, @NonNull TaskListener listener) {
        listener.getLogger().println("onCompleted() launched");
        EnergyAction action = run.getAction(EnergyAction.class);
        if (action != null) {
            long startTime = action.getStartTime();
            long startConsumption = action.getStartConsumption();
            long endTime = System.currentTimeMillis();
            long endConsumption = lectureConsommation();
            long energyConsumed = calculateEnergyConsumption(startTime, endTime, startConsumption, endConsumption);
            listener.getLogger().println("Consommation d'énergie pendant le build : " + energyConsumed + " Watts.");
        }
    }

    private long calculateEnergyConsumption(long startTime, long endTime, long startConsumption, long endConsumption) {
        long consumption = endConsumption - startConsumption;
        long duration = (endTime - startTime)/1000;
        return consumption / duration / 1000000;
    }

    private long lectureConsommation() {
        //String chemin = System.getProperty("user.home") + "/fichier_pour_lecture_jenkins/consumption.txt";
        String chemin = "/sys/devices/virtual/powercap/intel-rapl/intel-rapl:0/energy_uj";
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            String ligne = lecteur.readLine();
            if (ligne != null) {
                return Long.parseLong(ligne);
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return 0;
    }
}
