import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Ready implements Runnable{
    private static Queue<PCB> readyQ = new LinkedList<>();
    private static final Object readyQMutex = new Object();
    
    public static void syncSort() { //sort descending
        synchronized(readyQMutex){
            readyQ = Ready.readyQ.stream()
                    .sorted((p1, p2) -> Integer.compare(p2.priority, p1.priority))
                    .collect(Collectors.toCollection(LinkedList::new)); 
        }
    }

    public static PCB syncPoll(){
        synchronized(readyQMutex){
            return readyQ.poll();
        }
    }

    public static void syncAdd(PCB pcb){
        synchronized(readyQMutex){
            readyQ.add(pcb);
        }
    }

    public static boolean syncEmpty(){
        synchronized(readyQMutex){
            return readyQ.isEmpty();
        }
    }

    public static void processStarvationIncrement(){
        synchronized(readyQMutex){
            for (PCB pcb : readyQ) {
                pcb.processStarvation++;
                if (pcb.processStarvation > pcb.processDegree) {
                    pcb.priority=9;
                    System.out.println("Process "+pcb.processID+" starved");
                }
            }
        }
    }

    public void run(){
        PCB pcb;
        while((pcb = Loader.waitingQ.peek()) != null){
            if (pcb.memRequired <= Main.getMemory()) {
                synchronized(readyQMutex){
                    Main.addMemory(-pcb.memRequired);
                    Loader.waitingQ.poll();

                    readyQ.add(pcb);

                    pcb.state = State.READY;
                    pcb.arrivalTime = Main.getTime();
                    pcb.processDegree = readyQ.size();
                }
            }
        }
    }
}