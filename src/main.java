import java.util.LinkedList;

public class main {

    public static String algType;
    LinkedList<PCB> FCFS;
    LinkedList<PCB> RR;
    LinkedList<PCB> priorityScheduling;



    public static void main(String[] args) {

        Thread loaderThread = new Thread(new loader(ready.readyQ));
        Thread readyThread = new Thread(new ready());

        loaderThread.start();
        readyThread.start();   

        try {
            loaderThread.join();
            readyThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }




        
        if (algType.equals("FCFS")){
            StringBuilder chart = new StringBuilder();
            StringBuilder processID = new StringBuilder();
            StringBuilder burstTimeEnd = new StringBuilder("0");

            int totalBurstTime = 0;
            while(!ready.readyQ.isEmpty()){
                PCB pcb = new PCB(ready.readyQ.peek());
                totalBurstTime+=pcb.burstTime;
                chart.append("+").append("-".repeat(pcb.burstTime));
                processID.append("|").append(" ".repeat(pcb.burstTime));
                burstTimeEnd.append(" ".repeat(pcb.burstTime)).append(totalBurstTime);
            }
        }else if(algType.equals("RR")){
            StringBuilder chart = new StringBuilder();
            StringBuilder processID = new StringBuilder();
            StringBuilder burstTimeEnd = new StringBuilder("0");

            int quantum = 7;
            int totalBurstTime = 0;
            while(!ready.readyQ.isEmpty()){
                PCB pcb = new PCB(ready.readyQ.poll());

                
                pcb.burstTime -=quantum;
                int bursted;
                if(pcb.burstTime<=0){
                    bursted = pcb.burstTime;
                    pcb.burstTime = 0;

                }else{
                    ready.readyQ.add(pcb);
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
