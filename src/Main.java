import java.util.HashMap;
import java.util.Scanner;

public class Main {
    private static int memoryAvailable = 2048;
    private static int time = 0;
    public static Algorithms algType=Algorithms.PS;
    public static HashMap<Integer, PCB> pcbMap = new HashMap<>();
    
    public synchronized static void addMemory(int amt){
        memoryAvailable+=amt;
    }
    public synchronized static int getMemory(){
        return memoryAvailable;
    }

    public synchronized static void addTime(int amt){
        time+=amt;
    }
    public synchronized static int getTime(){
        return time;
    }


    public static void main(String[] args) {
        System.out.println("Enter alg choice:\n1) FCFS (default)\n2) RR\n3) PS");
        try (Scanner input = new Scanner(System.in)) {
            switch (input.nextInt()) {
                case 1: algType = Algorithms.FCFS; break;
                case 2: algType = Algorithms.RR; break;
                case 3: algType = Algorithms.PS; break;
                default: algType = Algorithms.PS; break;
            }
        }
     
        
        Thread loadThread = new Thread(new Loader());
        Thread readyThread = new Thread(new Ready());

        loadThread.start();
        try {
            loadThread.join();
        } catch (InterruptedException e) {
            System.err.println("Could not join load thread: "+e);
        }
        
        readyThread.start();

        StringBuilder chart = new StringBuilder();
        StringBuilder processIDs = new StringBuilder();
        StringBuilder burstTimeEnd = new StringBuilder("0");
        
        switch (algType) {
            case FCFS:
                while (!Loader.waitingQ.isEmpty()) {
                    while (!Ready.syncEmpty()) {
                        PCB pcb = new PCB(Ready.syncPoll());
                        
                        SystemCalls.SetProcessState(State.RUNNING, pcb);
                        
                        addTime(pcb.burstTime);
                        pcb.exitTime = getTime();

                        SystemCalls.SetProcessState(State.TERMINATED, pcb);
                        
                        addMemory(pcb.memRequired);
                        pcbMap.put(pcb.processID, pcb);

                        String processSpaces = "P" + pcb.processID;
                        String burstSpaces = String.valueOf(getTime());
                        chart.append("+").append("-".repeat(pcb.burstTime*3));
                        processIDs.append("|").append(processSpaces).append(" ".repeat(pcb.burstTime*3 - processSpaces.length()));
                        burstTimeEnd.append(" ".repeat(pcb.burstTime*3 - burstSpaces.length() +1)).append(getTime());
                    }
                }
                break;
            case RR:
                int quantum = 7;
                while (!Loader.waitingQ.isEmpty()) {
                    while (!Ready.syncEmpty()){
                        PCB pcb = new PCB(Ready.syncPoll());

                        SystemCalls.SetProcessState(State.RUNNING, pcb);

                        int bursted = Math.min(pcb.processRemainingBurstTime, quantum);
                        pcb.processRemainingBurstTime -= bursted;
                        addTime(bursted);

                        if (pcb.processRemainingBurstTime > 0) {
                            SystemCalls.SetProcessState(State.READY, pcb);
                            Ready.syncAdd(pcb);
                        } else {
                            pcb.exitTime = getTime();
                            SystemCalls.SetProcessState(State.TERMINATED, pcb);
                            addMemory(pcb.memRequired);
                            pcbMap.put(pcb.processID, pcb);
                        }

                        String processSpaces = "P" + pcb.processID;
                        String burstSpaces = String.valueOf(getTime());
                        chart.append("+").append("-".repeat(pcb.burstTime*3));
                        processIDs.append("|").append(processSpaces).append(" ".repeat(pcb.burstTime*3 - processSpaces.length()));
                        burstTimeEnd.append(" ".repeat(pcb.burstTime*3 - burstSpaces.length() +1)).append(getTime());
                    }
                }
                break;
            case PS:
                while (!Loader.waitingQ.isEmpty()) {
                    while(!Ready.syncEmpty()) { 
                        Ready.syncSort();

                        PCB pcb = new PCB(Ready.syncPoll());

                        SystemCalls.SetProcessState(State.RUNNING, pcb);
                        
                        Ready.processStarvationIncrement();

                        addTime(pcb.burstTime);
                        pcb.exitTime = getTime();

                        SystemCalls.SetProcessState(State.TERMINATED, pcb);
                        
                        addMemory(pcb.memRequired);
                        pcbMap.put(pcb.processID, pcb);

                        String processSpaces = "P" + pcb.processID;
                        String burstSpaces = String.valueOf(getTime());
                        chart.append("+").append("-".repeat(pcb.burstTime*3));
                        processIDs.append("|").append(processSpaces).append(" ".repeat(pcb.burstTime*3 - processSpaces.length()));
                        burstTimeEnd.append(" ".repeat(pcb.burstTime*3 - burstSpaces.length() +1)).append(getTime());
                    }
                }
                break;
        }

        try{
            readyThread.join();
        } catch (InterruptedException e) {
            System.err.println("Could not join ready thread: "+e);
        }

        System.out.println("Process ID   |Arrival Time| Exit Time| Burst Time ");
        System.out.println("---------------------------------------------------------------");
        for (PCB pcb : pcbMap.values()) {
            System.out.printf("%-12d | %-10d | %-8d | %-9d%n", pcb.processID, pcb.arrivalTime, pcb.exitTime, pcb.burstTime);
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println(); System.out.println(); System.out.println();
        System.out.println("Process ID   | Waiting Time | Turnaround Time" );
        System.out.println("---------------------------------------------------------------");
        for (PCB pcb : pcbMap.values()) {
            System.out.printf("%-12d | %-12d | %-18d%n", pcb.processID, pcb.exitTime-pcb.arrivalTime-pcb.burstTime, pcb.exitTime-pcb.arrivalTime);
        }
        System.out.println("---------------------------------------------------------------");
        double totalWaitingTime = 0, totalTurnaroundTime = 0;
        for (PCB pcb : pcbMap.values()) {
            totalTurnaroundTime += pcb.exitTime-pcb.arrivalTime;
            totalWaitingTime += pcb.exitTime-pcb.arrivalTime-pcb.burstTime;
        }
        System.out.printf("Average waiting time: %.2f%n", totalWaitingTime / pcbMap.size());
        System.out.printf("Average turnaround time: %.2f%n", totalTurnaroundTime / pcbMap.size());

        processIDs.append("|");
        int rowLength = 51 * 3; 
        int length = chart.length();


        for (int i = 0; i < length; i += rowLength) {
            int end = Math.min(i + rowLength, length);

            System.out.println(chart.substring(i, end));
            System.out.println(processIDs.substring(i, end));
            System.out.println(burstTimeEnd.substring(i, end));
        }
    }
}