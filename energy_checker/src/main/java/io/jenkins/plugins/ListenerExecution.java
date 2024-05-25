package io.jenkins.plugins;

import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.flow.FlowExecution;
import org.jenkinsci.plugins.workflow.flow.FlowExecutionListener;

@Extension
public class ListenerExecution extends FlowExecutionListener {

    private volatile boolean isRunning = false;
    double previousPartTime;
    double joulesConsumedPrevious;
    boolean firstPart = true;

    @Override
    public void onRunning(FlowExecution execution) {
        isRunning = true;
        double startRunning = System.currentTimeMillis();
        double joulesStartRunning = ScriptGetEnergeticValues.lectureConsommation();
        while (isRunning) {
            double currentPartTime = (System.currentTimeMillis() - startRunning)/1000;
            double joulesConsumed = ScriptGetEnergeticValues.lectureConsommation() - joulesStartRunning;
            if (!firstPart) {
                double duration = currentPartTime - previousPartTime;
                double deltaJoules = joulesConsumed - joulesConsumedPrevious;
                double wattProvidedPrevious = deltaJoules / duration;
                try {
                    TaskListener listener = execution.getOwner().getListener();
                    listener.getLogger()
                            .println("Running " + String.format("%.3f", duration) + " seconds, provided " + wattProvidedPrevious + " watts and consumed " + deltaJoules + " joules");
                    ValuesExecutionData.getInstance().addPartData(duration, deltaJoules, wattProvidedPrevious);
                } catch (Exception e) {e.printStackTrace();}

            }
            try {
                TaskListener listener = execution.getOwner().getListener();
                listener.getLogger().println("Part started at: " + String.format("%.3f", currentPartTime) + " seconds");
            } catch (Exception e) {
                e.printStackTrace();
            }
            previousPartTime = currentPartTime;
            joulesConsumedPrevious = joulesConsumed;
            firstPart = false;

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                break;
            }
        }
    }

    @Override
    public void onCompleted(FlowExecution execution) {
        System.out.println("Pipeline completed");
        try {
            CompletionLatch.getInstance().await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        isRunning = false;

        isRunning = false;
    }

    @Override
    public void onResumed(FlowExecution execution) {
        System.out.println("Pipeline resumed");
    }
}
