import java.util.HashMap;
import java.util.LinkedList;
import java.util.stream.Collectors;
public class Main {
    public static int memoryAvailable = 2048;
    public static Algorithms algType=Algorithms.RR;
    

    public synchronized static void addMemory(int amt){
        memoryAvailable+=amt;
    }
    public synchronized static int getMemory(){
        return memoryAvailable;
    }

    public static void main(String[] args) {
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
        LinkedList<ScheduleRow> ScheduleTable = new LinkedList<>();
        HashMap<Integer, PCB> processMap = new HashMap<>();
        int totalBurstTime = 0;
        
        System.out.println("Starting scheduling:");
        switch (algType) {
            case FCFS:
                while (!Ready.readyQ.isEmpty()) {
                    PCB pcb = new PCB(Ready.readyQ.poll());
                    int startTime = totalBurstTime;
                    int endTime = startTime + pcb.burstTime;

                    totalBurstTime+=pcb.burstTime;
                    pcb.processTurnaroundTime=totalBurstTime;
                    pcb.processWaitingTime = pcb.processTurnaroundTime - pcb.burstTime;

                    Main.addMemory(pcb.memRequired);
                    processMap.put(pcb.processID, pcb);
                    ScheduleTable.add(new ScheduleRow(pcb.processID, startTime, endTime, pcb.burstTime));

                    String processSpaces = "P" + pcb.processID;
                    String burstSpaces = String.valueOf(totalBurstTime);
                    chart.append("+").append("-".repeat(pcb.burstTime*3));
                    processIDs.append("|").append(processSpaces).append(" ".repeat(pcb.burstTime*3 - processSpaces.length()));
                    burstTimeEnd.append(" ".repeat(pcb.burstTime*3 - burstSpaces.length() +1)).append(totalBurstTime);


                }
                break;

            case RR:
                int quantum = 4;
                while (!Ready.readyQ.isEmpty()){
                    PCB pcb = new PCB(Ready.readyQ.poll());
                    int startTime = totalBurstTime;
                    int endTime = startTime + pcb.burstTime;
                    int bursted = Math.min(pcb.processRemainingBurstTime, quantum);
                    pcb.processRemainingBurstTime -= bursted;
                    totalBurstTime += bursted;

                    if (pcb.processRemainingBurstTime > 0) {
                        Ready.readyQ.add(pcb); // Add back to the queue if not finished
                    } else {
                        Main.addMemory(pcb.memRequired);
                        pcb.processTurnaroundTime = totalBurstTime;
                        pcb.processWaitingTime = pcb.processTurnaroundTime - pcb.burstTime;
                        processMap.put(pcb.processID, pcb);
                    }

                    ScheduleTable.add(new ScheduleRow(pcb.processID, startTime, endTime, bursted));

                    String processSpaces = "P" + pcb.processID;
                    String burstSpaces = String.valueOf(totalBurstTime);
                    chart.append("+").append("-".repeat(pcb.burstTime*3));
                    processIDs.append("|").append(processSpaces).append(" ".repeat(pcb.burstTime*3 - processSpaces.length()));
                    burstTimeEnd.append(" ".repeat(pcb.burstTime*3 - burstSpaces.length() +1)).append(totalBurstTime);
                }
                break;
            case PS:
                while(!Ready.readyQ.isEmpty()){                
                    Ready.readyQ = Ready.readyQ.stream()
                    .sorted((p1, p2) -> Integer.compare(p1.priority, p2.priority))
                    .collect(Collectors.toCollection(LinkedList::new)); // Replace readyQ with the sorted list
                
                    for (PCB p : Ready.readyQ) {
                        if (p.processStarvation > Ready.readyQ.size()) {
                            p.priority = 0;

                        }
                    }
                    Ready.readyQ = Ready.readyQ.stream()
                    .sorted((p1, p2) -> Integer.compare(p1.priority, p2.priority))
                    .collect(Collectors.toCollection(LinkedList::new));

                    PCB pcb = new PCB(Ready.readyQ.poll());
                    Ready.processStarvationincrement();
                    
                    int startTime = totalBurstTime;
                    int endTime = startTime + pcb.burstTime;
                    totalBurstTime += pcb.burstTime;
                    pcb.processTurnaroundTime = totalBurstTime;
                    pcb.processWaitingTime = pcb.processTurnaroundTime - pcb.burstTime;

                    Main.addMemory(pcb.memRequired);
                    processMap.put(pcb.processID, pcb);
                    ScheduleTable.add(new ScheduleRow(pcb.processID, startTime, endTime, pcb.burstTime));


                    String processSpaces = "P" + pcb.processID;
                    String burstSpaces = String.valueOf(totalBurstTime);
                    chart.append("+").append("-".repeat(pcb.burstTime*3));
                    processIDs.append("|").append(processSpaces).append(" ".repeat(pcb.burstTime*3 - processSpaces.length()));
                    burstTimeEnd.append(" ".repeat(pcb.burstTime*3 - burstSpaces.length() +1)).append(totalBurstTime);
                }
                break;
        }

   
        
        
        try{
            readyThread.join();
        } catch (InterruptedException e) {
            System.err.println("Could not join ready thread: "+e);
        }

        System.out.println("Process ID  | Start Time | End Time | Burst Time ");
        System.out.println("---------------------------------------------------------------");
        for (ScheduleRow row : ScheduleTable) {
            System.out.printf("%-12d | %-10d | %-8d | %-9d%n", row.processID, row.startTime, row.endTime, row.burstTime);
        }
        System.out.println("---------------------------------------------------------------");
        System.out.println("Process ID   |waiting time| TurnAroundTime" );
        for (PCB pcb : processMap.values()) {
            System.out.printf("%-12d | %-10d | %-18d%n", pcb.processID, pcb.processWaitingTime, pcb.processTurnaroundTime);
        }
        System.out.println("---------------------------------------------------------------");
        double totalWaitingTime = 0, totalTurnaroundTime = 0;
        for (PCB pcb : processMap.values()) {
            totalWaitingTime += pcb.processWaitingTime;
            totalTurnaroundTime += pcb.processTurnaroundTime;
        }
        System.out.printf("Average waiting time: %.2f%n", totalWaitingTime / processMap.size());
        System.out.printf("Average turnaround time: %.2f%n", totalTurnaroundTime / processMap.size());

        processIDs.append("|");
        int rowLength = 50 * 3; 
        int length = chart.length();

        for (int i = 0; i < length; i += rowLength) {
            int end = Math.min(i + rowLength, length);

      
            System.out.println(chart.substring(i, end));
            System.out.println(processIDs.substring(i, end));
            System.out.println(burstTimeEnd.substring(i, end));
        }

        
        // System.out.println(chart.append("+"));
        // System.out.println(processIDs.append("|"));
        // System.out.println(burstTimeEnd);

    }


}
