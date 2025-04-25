public class SystemCalls {
    public static PCB CreateProcess(int processID, int burstTime, int priority, int memRequired) {
        return new PCB(processID, burstTime, priority, memRequired);
    }
}
