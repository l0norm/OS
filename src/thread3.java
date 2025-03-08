import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class thread3 implements Runnable{
    //scheduling algorithms 
    //user chose with one (3 types -FCFS -RR -priority scheduling)
    String algType;
    LinkedList<PCB> FCFS;
    LinkedList<PCB> RR;
    LinkedList<PCB> priorityScheduling;

    ConcurrentLinkedQueue<PCB> readyQ; //to take a job from jobQueue wich has list of pcb


    public thread3(String type, ConcurrentLinkedQueue<PCB> readyQ){
        this.algType = type;
        this.readyQ = readyQ;
    }


    public void run(){
        if (this.algType.equals("FCFS")){
            StringBuilder chart = new StringBuilder();
            StringBuilder processID = new StringBuilder();
            StringBuilder burstTimeEnd = new StringBuilder("0");

            int burstTime = 0;
            while(!readyQ.isEmpty()){
                PCB pcb = new PCB(readyQ.peek());
                burstTime+=pcb.burstTime;
                chart.append("+").append("-".repeat(pcb.burstTime));
                processID.append("|").append(" ".repeat(pcb.burstTime));
                burstTimeEnd.append(" ".repeat(pcb.burstTime)).append(burstTime);


            }
        }else if(this.algType.equals("RR")){




        }else if(algType=="priorityScheduling"){

        }
    }
}
