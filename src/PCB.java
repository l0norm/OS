public class PCB {
    int processID;
    int burstTime;
    int priority;
    int memRequired;
    State state;

    int processWaitingTime;
    int processTurnaroundTime;
    int processRemainingBurstTime;
    int processStarvation;

    public PCB(PCB pcb) {
        this.processID = pcb.processID;
        this.burstTime = pcb.burstTime;
        this.priority = pcb.priority;
        this.memRequired = pcb.memRequired;

        this.processTurnaroundTime = pcb.processTurnaroundTime;
        this.processWaitingTime = pcb.processWaitingTime;
        this.processRemainingBurstTime = pcb.processRemainingBurstTime;
        this.processStarvation = pcb.processStarvation;
    }

    public PCB(int processID, int burstTime, int priority, int memRequired) {
        this.processID = processID;
        this.burstTime = burstTime;
        this.priority = priority;
        this.memRequired = memRequired;
        this.processRemainingBurstTime = burstTime; 
        this.processStarvation = 0;
    }


}