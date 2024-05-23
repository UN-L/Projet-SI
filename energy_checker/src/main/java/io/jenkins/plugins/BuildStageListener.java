package io.jenkins.plugins;

import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionListener;
import org.jenkinsci.plugins.workflow.graph.FlowNode;

@Extension
public class BuildStageListener extends FlowExecutionListener {

    @Override
    public void onRunning(FlowExecution execution) {
        System.out.println("Pipeline is running");
        double startRunning = System.currentTimeMillis();
        while (true) {
            double currentRunning = System.currentTimeMillis() - startRunning;
            try {
                TaskListener listener = execution.getOwner().getListener();
                listener.getLogger().println("Running " + currentRunning/1000 + " seconds");
            } catch (Exception e) {
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
            }
        }

    }

    @Override
    public void onCompleted(FlowExecution execution) {
        System.out.println("Pipeline completed");
    }

    @Override
    public void onResumed(FlowExecution execution) {
        System.out.println("Pipeline resumed");
    }
}

