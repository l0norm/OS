import java.util.concurrent.ConcurrentLinkedQueue;



public class ready implements Runnable{
    //loading jobs to ready queue
    //checks available space in memory to load the next job (from job queue to ready queue)
    //loading jobs only if enough memory 

    int memory = 512;
    public static ConcurrentLinkedQueue<PCB> readyQ; //to take a job from jobQueue wich has list of pcb
    int i = 0;
    int j = 1;
    
    public ready(){
        this.readyQ = new ConcurrentLinkedQueue<>();
    
    }

    public void run(){
        System.out.println("starting the thread");

        while(true){
            read.flag[j] = true;
            read.turn = i;

          
            if(read.jobQ.peek() != null){
                PCB pcb = new PCB(read.jobQ.peek());
                if (pcb.memRequired <= this.memory){
                    while(read.flag[i] && read.turn == i)System.out.println("waiting for i ");
                    read.jobQ.poll();//this is the critical task
                    
                    readyQ.add(pcb);
                }
            }



            read.flag[j] = false;
        }
    }

}
