
public class SystemCalls {
    public static PCB CreateProcess(int processID, int burstTime, int priority, int memRequired, State state) {
        return new PCB(processID, burstTime, priority, memRequired, state);        
    }
    public static PCB CreateProcess(PCB pcb) {
        return new PCB(pcb);        
    }
    public static PCB GetProcessInfo(int processID) {
        return Main.pcbMap.get(processID);
    }
    public static void SetProcessState(State s, PCB pcb){
        pcb.state=s;
    }
}
