import java.util.LinkedList;
import java.util.Queue;

public class Ready implements Runnable{
    public static Queue<PCB> readyQ = new LinkedList<>();

    public void run(){
        PCB pcb;
        while((pcb = Loader.waitingQ.peek()) != null && pcb.memRequired <= Main.getMemory()){
            Main.addMemory(-pcb.memRequired);
            Loader.waitingQ.poll();
            readyQ.add(pcb);
        }
    }

}
