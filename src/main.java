import java.io.File;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class main {

    public static ConcurrentLinkedQueue<PCB> jobQ = new ConcurrentLinkedQueue<>();
    public static boolean[] flag = new boolean[2];
    public static int turn;

    public static void main(String[] args) {

    // reading from job.txt file 
    //creates PCB and put them in the job queue


    //the inputs will be [processID : burst time : priority ; memory required ]

        int i = 0;
        int j = 1;
        
        String algType = "FCFS";
        LinkedList<PCB> FCFS;
        LinkedList<PCB> RR;
        LinkedList<PCB> priorityScheduling;

        Thread t1 = new Thread(new ready());
  
        t1.start();




        File obj = new File("C:\\Users\\fahad\\Desktop\\GIT\\OS\\50_tasks_adjusted.txt");
        Scanner reader = null;
        try{
            reader = new Scanner(obj);
          
            while(reader.hasNextLine()){

                flag[i] = true;
                turn = j;


                String data = reader.nextLine();
                int splitted[] = Arrays.stream(data.split("[:;]"))
                                        .mapToInt(Integer::parseInt)
                                        .toArray();
                PCB pcb = new PCB(splitted[0], splitted[1], splitted[2], splitted[3]);

                // System.out.println("entering wait");
                while(flag[j] && turn == j){
                    System.out.println("waiting for j");
                }
                // System.out.println("exiting wait");
                jobQ.add(pcb);//this is the critical task







                if (algType.equals("FCFS")){
                    StringBuilder chart = new StringBuilder();
                    StringBuilder processID = new StringBuilder();
                    StringBuilder burstTimeEnd = new StringBuilder("0");
        
                    int totalBurstTime = 0;
                    while(!ready.readyQ.isEmpty()){
                        pcb = new PCB(ready.readyQ.peek());
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
                        pcb = new PCB(ready.readyQ.poll());
        
                        
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



                flag[i] = false;
            }

        }catch(Exception e){
            System.out.println("error: " + e.getMessage());
        }finally{
            if(reader!=null){
                reader.close();
            }
        }

     

        try {
            t1.join();  // Main thread waits here until t1 finishes
        } catch (InterruptedException e) {
            e.printStackTrace();
        }





    }
}
