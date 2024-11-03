import java.awt.Color;

public class ProcessExecution {
    private final String processNo;
    private final Color color;
    private final String startTime;
    private String stopTime;


    public ProcessExecution(String processNo, Color color, String startTime, String stopTime) {
        this.processNo = processNo;
        this.color = color;
        this.startTime = startTime;
        this.stopTime = stopTime;
    }

    public String getProcessNo() {
        return processNo;
    }

    public Color getColor() {
        return color;
    }

    public String getStartTime() {
        return startTime;
    }

    public String getStopTime() {
        return stopTime;
    }


    public void setStopTime(String s) {
        stopTime = s;
    }
}
