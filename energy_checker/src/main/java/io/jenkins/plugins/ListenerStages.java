package io.jenkins.plugins;

import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.flow.GraphListener;
import org.jenkinsci.plugins.workflow.graph.FlowNode;

@Extension
public class ListenerStages implements GraphListener {


    double timeStartOfClass = System.currentTimeMillis();
    double joulesStartOfClass = ScriptGetEnergeticValues.lectureConsommation();
    boolean firstNode = true;
    double startTimePrevious;
    String stageNamePrevious;
    double joulesConsumedPrevious;

    public ListenerStages() {
        System.out.println("BuildGraphListener created");
    }

    @Override
    public void onNewHead(FlowNode node) {
        if (node != null) {
            String stageName = node.getDisplayName();
            //long startTime = node.getExecution().getOwner().getStartTimeMillis();
            double startTime = (System.currentTimeMillis()- timeStartOfClass)/1000;
            double joulesConsumed = ScriptGetEnergeticValues.lectureConsommation() - joulesStartOfClass;
            if (!firstNode) {
                double duration = startTime - startTimePrevious;
                double deltaJoules = joulesConsumed - joulesConsumedPrevious;
                double wattProvidedPrevious = deltaJoules / duration;
                try {
                    TaskListener listener = node.getExecution().getOwner().getListener();
                    listener.getLogger().println("Stage '" + stageNamePrevious + "' lasted for " + String.format("%.3f", duration) + " seconds, provided " + wattProvidedPrevious + " watts and consumed " + deltaJoules + " joules");
                } catch (Exception e) {}

            }
            System.out.println("Stage '" + stageName + "' started at: " + startTime);
            try {
                TaskListener listener = node.getExecution().getOwner().getListener();
                listener.getLogger().println("Stage '" + stageName + "' started at: " + String.format("%.3f", startTime) + " seconds");
            } catch (Exception e) {}
            startTimePrevious = startTime;
            stageNamePrevious = stageName;
            joulesConsumedPrevious = joulesConsumed;
            firstNode = false;
        }
    }
}
