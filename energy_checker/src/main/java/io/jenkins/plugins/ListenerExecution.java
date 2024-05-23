package io.jenkins.plugins;

import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionListener;

@Extension
public class ListenerExecution extends FlowExecutionListener {

    private volatile boolean isRunning = false;

    @Override
    public void onRunning(FlowExecution execution) {
        System.out.println("Pipeline is running");
        double startRunning = System.currentTimeMillis();
        while (isRunning) {
            double currentRunning = System.currentTimeMillis() - startRunning;
            try {
                TaskListener listener = execution.getOwner().getListener();
                listener.getLogger().println("Running " + currentRunning/1000 + " seconds");
            } catch (Exception e) {
            }
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                break;
            }
        }

    }

    @Override
    public void onCompleted(FlowExecution execution) {
        System.out.println("Pipeline completed");
        isRunning = false;
    }

    @Override
    public void onResumed(FlowExecution execution) {
        System.out.println("Pipeline resumed");
    }
}

