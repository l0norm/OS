import java.util.LinkedList;
import java.util.Queue;
import java.util.stream.Collectors;

public class Ready implements Runnable{
    public static Queue<PCB> readyQ = new LinkedList<>();
    private static final Object mutex = new Object();
    public static void addToReadyQ(PCB pcb) {
        synchronized (mutex) {
            readyQ.add(pcb);
        }
    }

    public static PCB pollFromReadyQ() {
        synchronized (mutex) {
            return readyQ.poll();
        }
    }

    public static boolean isReadyQEmpty() {
        synchronized (mutex) {
            return readyQ.isEmpty();
        }
    }

    public static int getReadyQSize() {
        synchronized (mutex) {
            return readyQ.size();
        }
    }
    public static void sortReadyQ() {
        synchronized (mutex) {
            readyQ = Ready.readyQ.stream()
                    .sorted((p1, p2) -> Integer.compare(p2.priority, p1.priority))
                    .collect(Collectors.toCollection(LinkedList::new)); // Replace readyQ with the sorted list
         
        }
    }
    public void run(){
        PCB pcb;
        while((pcb = Loader.waitingQ.peek()) != null){
            if (pcb.memRequired < Main.getMemory()) {
                Main.addMemory(-pcb.memRequired);
                Loader.waitingQ.poll();
                synchronized (mutex) {
                    readyQ.add(pcb);
                    pcb.processDegreeTime = readyQ.size();
                    pcb.arrivalTime = Main.getBurstTime();
                }
            }

        }
    }
    public static void processStarvationincrement(){
        synchronized (mutex) {
            for (PCB pcb : readyQ) {
                pcb.processStarvation++;
            }
        }

    }

}
