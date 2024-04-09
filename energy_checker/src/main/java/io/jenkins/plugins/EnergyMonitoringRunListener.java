package io.jenkins.plugins;

import edu.umd.cs.findbugs.annotations.NonNull;
import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;

@Extension
public class EnergyMonitoringRunListener extends RunListener<Run<?, ?>> {

    @Override
    public void onInitialize(@NonNull Run<?, ?> run) {}

    @Override
    public void onFinalized(@NonNull Run<?, ?> run) {}

    @Override
    public void onStarted(Run<?, ?> run, TaskListener listener) {
        listener.getLogger().println("Début de la surveillance de la consommation d'énergie. (Started)");
    }

    @Override
    public void onCompleted(Run<?, ?> run, @NonNull TaskListener listener) {
        // Logique à exécuter à la fin du run
    }
}
