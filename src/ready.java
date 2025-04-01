import java.util.LinkedList;
import java.util.Queue;



public class ready implements Runnable{
    //loading jobs to ready queue
    //checks available space in memory to load the next job (from job queue to ready queue)
    //loading jobs only if enough memory 

    int memory = 512;
    public static Queue<PCB> readyQ; //to take a job from jobQueue wich has list of pcb
    int i = 0;
    int j = 1;
    
    public ready(){
        this.readyQ = new LinkedList<>();
    
    }

    public void run(){
        System.out.println("starting the thread");

        while(true){
            loader.flag[j] = true;
            loader.turn = i;
            while(loader.flag[i] && loader.turn == i)System.out.println("waiting for i ");
          
            if(loader.jobQ.peek() != null){
                PCB pcb = new PCB(loader.jobQ.peek());
                if (pcb.memRequired <= this.memory){
                   
                    loader.jobQ.poll();//this is the critical task
                    
                    readyQ.add(pcb);
                }
            }



            loader.flag[j] = false;

            if(loader.jobQ.isEmpty()){
                System.out.println("job queue is empty");
                break;
            }
        }
    }

}
