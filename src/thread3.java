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

            int totalBurstTime = 0;
            while(!readyQ.isEmpty()){
                PCB pcb = new PCB(readyQ.peek());
                totalBurstTime+=pcb.burstTime;
                chart.append("+").append("-".repeat(pcb.burstTime));
                processID.append("|").append(" ".repeat(pcb.burstTime));
                burstTimeEnd.append(" ".repeat(pcb.burstTime)).append(totalBurstTime);
            }
        }else if(this.algType.equals("RR")){
            StringBuilder chart = new StringBuilder();
            StringBuilder processID = new StringBuilder();
            StringBuilder burstTimeEnd = new StringBuilder("0");

            int quantum = 7;
            int totalBurstTime = 0;
            while(!readyQ.isEmpty()){
                PCB pcb = new PCB(readyQ.poll());

                
                pcb.burstTime -=quantum;
                int bursted;
                if(pcb.burstTime<=0){
                    bursted = pcb.burstTime;
                    pcb.burstTime = 0;

                }else{
                    readyQ.add(pcb);
                    bursted = quantum;
                }
                totalBurstTime+=bursted;

                chart.append("+").append("-".repeat(bursted));
                processID.append("|").append(" ".repeat(bursted));
                burstTimeEnd.append(" ".repeat(bursted)).append(totalBurstTime);

            }



        }else if(algType=="priorityScheduling"){
            StringBuilder chart = new StringBuilder();
            StringBuilder processID = new StringBuilder();
            StringBuilder burstTimeEnd = new StringBuilder("0");

            

        }
    }
}
