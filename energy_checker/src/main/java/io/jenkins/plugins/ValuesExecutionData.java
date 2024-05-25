package io.jenkins.plugins;

import java.util.ArrayList;
import java.util.List;

public class ValuesExecutionData {
    private static ValuesExecutionData instance;
    private List<PartData> partDataList;

    private ValuesExecutionData() {
        partDataList = new ArrayList<>();
    }

    public static synchronized ValuesExecutionData getInstance() {
        if (instance == null) {
            instance = new ValuesExecutionData();
        }
        return instance;
    }

    public void addPartData(double duration, double joulesConsumed, double wattsProvided) {
        partDataList.add(new PartData(duration, joulesConsumed, wattsProvided));
    }

    public List<PartData> getPartDataList() {
        return new ArrayList<>(partDataList);
    }

    public static class PartData {
        private double duration;
        private double joulesConsumed;
        private double wattsProvided;

        public PartData(double duration, double joulesConsumed, double wattsProvided) {
            this.duration = duration;
            this.joulesConsumed = joulesConsumed;
            this.wattsProvided = wattsProvided;
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
        for (PartData data : partDataList) {
            if (json.length() > 1) {
                json.append(",");
            }
            json.append("{");
            json.append("\"duration\":").append(data.getDuration()).append(",");
            json.append("\"joulesConsumed\":").append(data.getJoulesConsumed()).append(",");
            json.append("\"wattsProvided\":").append(data.getWattsProvided());
            json.append("}");
        }
        json.append("]");
        return json.toString();
    }
}
