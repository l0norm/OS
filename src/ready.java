import java.util.LinkedList;
import java.util.Queue;



public class ready implements Runnable{
    //loading jobs to ready queue
    //checks available space in memory to load the next job (from job queue to ready queue)
    //loading jobs only if enough memory 

    int memory = 512;
    public static ConcurrentLinkedQueue<PCB> readyQ; //to take a job from jobQueue wich has list of pcb

    
    public ready(){
        this.readyQ = new LinkedList<>();
    
    }

    public void run(){
        System.out.println("starting the thread");

        while(true){

            if(read.jobQ.peek() != null){
                PCB pcb = new PCB(read.jobQ.peek());
                if (pcb.memRequired <= this.memory){
                    read.jobQ.poll();
                    readyQ.add(pcb);
                }
            }
            else{
                System.out.println("job queue is empty");
                break;
            }
        }
    }

}
