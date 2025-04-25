
public class PCB {
    int processID;
    int burstTime;
    int priority;
    int memRequired;
    State state;

    int processWaitingTime;
    int processTurnaroundTime;

    public PCB(PCB pcb){
        this.processID = pcb.processID;
        this.burstTime = pcb.burstTime;
        this.priority = pcb.priority;
        this.memRequired = pcb.memRequired;
    }
    
    public PCB(int processID, int burstTime, int priority, int memRequired){
        this.processID = processID;
        this.burstTime = burstTime;
        this.priority = priority;
        this.memRequired = memRequired;
    }
}
