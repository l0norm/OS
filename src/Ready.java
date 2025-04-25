import java.util.LinkedList;
import java.util.Queue;

public class Ready implements Runnable{
    public static Queue<PCB> readyQ = new LinkedList<>();

    public void run(){
        PCB pcb;
        while((pcb = Loader.jobQ.peek()) != null){
                if (pcb.memRequired <= Main.memoryAvailable){
                    Main.addMemory(-pcb.memRequired);
                    Loader.jobQ.poll();
                    readyQ.add(pcb);
                }
           
        }
    }

}
