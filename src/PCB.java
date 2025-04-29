public class PCB {
    int processID;
    int burstTime;
    int priority;
    int memRequired;
    State state;

    int processRemainingBurstTime;
    int processStarvation = 0;
    int processDegree = 0;

    int arrivalTime;
    int exitTime;

    public PCB(PCB pcb) {
        this.processID = pcb.processID;
        this.burstTime = pcb.burstTime;
        this.priority = pcb.priority;
        this.memRequired = pcb.memRequired;
        this.state = pcb.state;

        this.processRemainingBurstTime = pcb.processRemainingBurstTime;
        this.processStarvation = pcb.processStarvation;
        this.processDegree = pcb.processDegree;

        this.arrivalTime = pcb.arrivalTime;
        this.exitTime = pcb.exitTime;
    }

    public PCB(int processID, int burstTime, int priority, int memRequired, State state) {
        this.processID = processID;
        this.burstTime = burstTime;
        this.priority = priority;
        this.memRequired = memRequired;
        this.state = state;
        this.processRemainingBurstTime = burstTime; 
    }


}