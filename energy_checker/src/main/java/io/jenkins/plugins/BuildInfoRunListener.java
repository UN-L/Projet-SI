package io.jenkins.plugins;

import hudson.Extension;
import hudson.model.Run;
import hudson.model.TaskListener;
import hudson.model.listeners.RunListener;
import hudson.model.Result;
import hudson.model.ParametersAction;
import hudson.model.ParameterValue;
import java.util.List;

@Extension
public class BuildInfoRunListener extends RunListener<Run<?, ?>> {

    @Override
    public void onCompleted(Run<?, ?> run, TaskListener listener) {
        // Build ID and URL
        String buildId = run.getId();
        String buildUrl = run.getUrl();
        listener.getLogger().println("Build ID: " + buildId);
        listener.getLogger().println("Build URL: " + buildUrl);

        // Build status
        Result result = run.getResult();
        listener.getLogger().println("Build result: " + (result != null ? result.toString() : "N/A"));

        // Build duration
        long duration = run.getDuration();
        listener.getLogger().println("Build duration: " + duration + " ms");

        listener.getLogger().println("Build time: " + run.getTimestamp());

        // Build parameters (for parameterized builds)
        ParametersAction parametersAction = run.getAction(ParametersAction.class);
        if (parametersAction != null) {
            for (ParameterValue parameter : parametersAction.getParameters()) {
                String paramName = parameter.getName();
                String paramValue = parameter.getValue().toString();
                listener.getLogger().println("Parameter: " + paramName + " = " + paramValue);
            }
        }



    }
}

