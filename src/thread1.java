import java.io.*;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;


public class thread1 implements Runnable {
    // reading from job.txt file 
    //creates PCB and put them in the job queue


    //the inputs will be [processID : burst time : priority ; memory required ]

    
    public static ConcurrentLinkedQueue<PCB> jobQ;
    public thread1(){
        jobQ = new ConcurrentLinkedQueue<>();

    }

    public void run(){

        File obj = new File("null");
        Scanner reader = null;
        try{

            reader = new Scanner(obj);
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                int splitted[] = Arrays.stream(data.split(" "))
                                        .mapToInt(Integer::parseInt)
                                        .toArray();

                PCB pcb = new PCB(splitted[0], splitted[1], splitted[2], splitted[3]);
                jobQ.add(pcb);
            }

        }catch(Exception e){
            System.out.println("error: " + e.getMessage());
        }finally{
            if(reader!=null){
                reader.close();
            }
        }

    }
    


}
