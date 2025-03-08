import java.util.concurrent.ConcurrentLinkedQueue;



public class thread2 implements Runnable{
    //loading jobs to ready queue
    //checks available space in memory to load the next job (from job queue to ready queue)
    //loading jobs only if enough memory 

    int memory = 512;
    ConcurrentLinkedQueue<PCB> readyQ; //to take a job from jobQueue wich has list of pcb
    ConcurrentLinkedQueue<PCB> jobQ;
    
    public thread2(ConcurrentLinkedQueue<PCB> jobQ, ConcurrentLinkedQueue<PCB> readyQ){
        this.readyQ = readyQ;
        this.jobQ = jobQ;
    }

    public void run(){
        while(!jobQ.isEmpty()){
            PCB pcb = new PCB(jobQ.peek());
            if (pcb.memRequired <= this.memory){
                jobQ.poll();
                readyQ.add(pcb);
            }

        }
    }

}
