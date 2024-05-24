package io.jenkins.plugins;

import java.util.ArrayList;
import java.util.List;

public class ValuesStageData {
    private static ValuesStageData instance;
    private List<StageData> stageDataList;

    private ValuesStageData() {
        stageDataList = new ArrayList<>();
    }

    public static synchronized ValuesStageData getInstance() {
        if (instance == null) {
            instance = new ValuesStageData();
        }
        return instance;
    }

    public void addStageData(String stageName, double duration, double joulesConsumed, double wattsProvided) {
        stageDataList.add(new StageData(stageName, duration, joulesConsumed, wattsProvided));
    }

    public List<StageData> getStageDataList() {
        return new ArrayList<>(stageDataList);
    }

    public static class StageData {
        private String stageName;
        private double duration;
        private double joulesConsumed;
        private double wattsProvided;

        public StageData(String stageName, double duration, double joulesConsumed, double wattsProvided) {
            this.stageName = stageName;
            this.duration = duration;
            this.joulesConsumed = joulesConsumed;
            this.wattsProvided = wattsProvided;
        }

        // Getters for the fields
        public String getStageName() {
            return stageName;
        }

        public double getDuration() {
            return duration;
        }

        public double getJoulesConsumed() {
            return joulesConsumed;
        }

        public double getWattsProvided() {
            return wattsProvided;
        }
    }

    public String toJson() {
        StringBuilder json = new StringBuilder("[");
        for (StageData data : stageDataList) {
            if (json.length() > 1) {
                json.append(",");
            }
            json.append("{");
            json.append("\"stageName\":\"").append(data.getStageName()).append("\",");
            json.append("\"duration\":").append(data.getDuration()).append(",");
            json.append("\"joulesConsumed\":").append(data.getJoulesConsumed()).append(",");
            json.append("\"wattsProvided\":").append(data.getWattsProvided());
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }
}
