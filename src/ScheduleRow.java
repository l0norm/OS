public class ScheduleRow {
    int processID;
    int startTime;
    int endTime;
    int burstTime;

    int waitingTime;
    int turnaroundTime;

    public ScheduleRow(int processID, int startTime, int endTime, int burstTime) {
        this.processID = processID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.burstTime = burstTime;
    }

}
