
public class PCB {
    int processID;
    int burstTime;
    int priority;
    int memRequired;
    State state;

    int processWaitingTime;
    int processTurnaroundTime;

    public PCB(PCB pcb){
        pcb.processID = processID;
        pcb.burstTime = burstTime;
        pcb.priority = priority;
        pcb.memRequired = memRequired;
    }
    
    public PCB(int processID, int burstTime, int priority, int memRequired){
        this.processID = processID;
        this.burstTime = burstTime;
        this.priority = priority;
        this.memRequired = memRequired;
    }
}
