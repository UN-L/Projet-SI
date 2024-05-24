package io.jenkins.plugins;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class ScriptGetEnergeticValues {

    public static double calculatePowerUsed(double startTime, double endTime, double startConsumption, double endConsumption) {
        double consumption = endConsumption - startConsumption;
        double duration = (endTime - startTime) / 1000;
        return consumption / duration / 1000000;
    }

    public static double calculatePowerUsed(double duration, double startConsumption, double endConsumption) {
        double consumption = endConsumption - startConsumption;
        return consumption / duration / 1000000;
    }

    public static double lectureConsommation() {
        // String chemin = System.getProperty("user.home") + "/fichier_pour_lecture_jenkins/consumption.txt";
        String chemin = "/sys/devices/virtual/powercap/intel-rapl/intel-rapl:0/energy_uj";
        try (BufferedReader lecteur = new BufferedReader(new FileReader(chemin))) {
            String ligne = lecteur.readLine();
            if (ligne != null) {
                return Double.parseDouble(ligne)/1000000;
            }
        } catch (IOException e) {
            System.err.println("Erreur de lecture du fichier : " + e.getMessage());
        }
        return 0;
    }

    public static ValuesEnergetic setEnergeticValues(double duration, double startEnergy, double endEnergy) {
        double energy = endEnergy - startEnergy;
        double power = energy / duration;
        return new ValuesEnergetic<>(energy, power);
    }
}
