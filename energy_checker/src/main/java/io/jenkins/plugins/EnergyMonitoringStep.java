package io.jenkins.plugins;

import hudson.Extension;
import hudson.FilePath;
import hudson.Launcher;
import hudson.EnvVars;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.tasks.Builder;
import hudson.tasks.BuildStepDescriptor;
import jenkins.tasks.SimpleBuildStep;
import org.jenkinsci.Symbol;
import org.jenkinsci.plugins.workflow.steps.Step;
import org.jenkinsci.plugins.workflow.steps.StepContextParameter;
import org.jenkinsci.plugins.workflow.steps.StepDescriptor;
import org.jenkinsci.plugins.workflow.steps.StepExecution;
import org.kohsuke.stapler.DataBoundConstructor;

import javax.inject.Inject;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class EnergyMonitoringStep extends Step implements SimpleBuildStep {
    
    private final Step buildStep; // L'étape de build que l'utilisateur veut exécuter

    // Constructeur
    public EnergyMonitoringStep(Step buildStep) {
        this.buildStep = buildStep;
    }

    @Override
    public void perform(Run<?, ?> run, FilePath workspace, EnvVars env, Launcher launcher, TaskListener listener) throws InterruptedException, IOException {
        // Enregistrer l'état initial
        long valeurAvant = lireConsommation();

        // Exécuter l'étape de build réelle
        buildStep.perform(run, workspace, env, launcher, listener);

        // Enregistrer l'état final
        long valeurApres = lireConsommation();

        // Calculer et afficher les métriques
        long difference = valeurApres - valeurAvant;
        listener.getLogger().println("Consommation d'énergie pendant le build : " + difference + " uJ");
    }

    private long lireConsommation() {
        // Implémentez la logique pour lire la consommation d'énergie depuis le système
        return 0; // Exemple simplifié
    }

    @Symbol("monitorEnergy")
    @Extension
    public static final class DescriptorImpl extends StepDescriptor {
        @Override
        public Set<? extends Class<?>> getRequiredContext() {
            return Collections.unmodifiableSet(new HashSet<>(Arrays.asList(FilePath.class, Run.class, EnvVars.class, Launcher.class, TaskListener.class)));
        }

        @Override
        public String getFunctionName() {
            return "monitorEnergy";
        }
    }
}
