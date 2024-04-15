package io.jenkins.plugins;

import hudson.model.Run;
import jenkins.model.RunAction2;

public class ChartDisplay implements RunAction2 {

    private transient Run run;

    @Override
    public String getIconFileName() {
        return null;
    }

    @Override
    public String getDisplayName() {
        return "Chart Display";
    }

    @Override
    public String getUrlName() {
        return "chart";
    }

    @Override
    public void onAttached(Run<?, ?> run) {
        this.run = run;
    }

    @Override
    public void onLoad(Run<?, ?> run) {
        this.run = run;
    }

    public Run getRun() {
        return run;
    }
}
