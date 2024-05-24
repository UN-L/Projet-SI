package io.jenkins.plugins;

import hudson.model.Run;
import jenkins.model.RunAction2;

import java.util.List;

public class DisplayStagesValues implements RunAction2 {

    private transient Run run;

    @Override
    public String getIconFileName() {
        return "/plugin/energy_checker/images/chart-histogram.png";
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

    public String getStageDataAsJson() {
        List<ValuesStageData.StageData> stageDataList = ValuesStageData.getInstance().getStageDataList();
        return stageDataList.toString();
    }
}
