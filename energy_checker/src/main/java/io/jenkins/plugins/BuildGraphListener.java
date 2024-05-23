package io.jenkins.plugins;

import hudson.Extension;
import hudson.model.TaskListener;
import org.jenkinsci.plugins.workflow.flow.GraphListener;
import org.jenkinsci.plugins.workflow.graph.FlowNode;

import java.util.logging.Logger;

@Extension
public class BuildGraphListener implements GraphListener {

    private static final Logger LOGGER = Logger.getLogger(BuildGraphListener.class.getName());

    double startOfClass = System.currentTimeMillis();

    public BuildGraphListener() {
        LOGGER.info("BuildGraphListener created");
        System.out.println("BuildGraphListener created");
    }

    @Override
    public void onNewHead(FlowNode node) {
        if (node != null) {
            String stageName = node.getDisplayName();
            //long startTime = node.getExecution().getOwner().getStartTimeMillis();
            double startTime = System.currentTimeMillis()-startOfClass;
            LOGGER.info("Stage '" + stageName + "' started at: " + startTime);
            System.out.println("Stage '" + stageName + "' started at: " + startTime);
            try {
                TaskListener listener = node.getExecution().getOwner().getListener();
                listener.getLogger().println("Stage '" + stageName + "' started at: " + startTime/1000 + " seconds");
            } catch (Exception e) {}

        }
    }
}
