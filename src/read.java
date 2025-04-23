import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
public class read implements Runnable{
    // reading from job.txt file 
    //creates PCB and put them in the job queue


    //the inputs will be [processID : burst time : priority ; memory required ]

        




    //scheduling algorithms 
    //user chose with one (3 types -FCFS -RR -priority scheduling)


    public static ConcurrentLinkedQueue<PCB> jobQ = new ConcurrentLinkedQueue<>();

    public static boolean[] flag = new boolean[2];
    public static int turn;

    int i = 0;
    int j = 1;

    ConcurrentLinkedQueue<PCB> readyQ; //to take a job from jobQueue wich has list of pcb


    public read( ConcurrentLinkedQueue<PCB> readyQ){
   
        this.readyQ = readyQ;
    }


    public void run(){


        File obj = new File("C:\\Users\\fahad\\Desktop\\GIT\\OS\\50_tasks_adjusted.txt");
        Scanner reader = null;
        try{
            reader = new Scanner(obj);
          
            while(reader.hasNextLine()){

                String data = reader.nextLine();
                int splitted[] = Arrays.stream(data.split("[:;]"))
                                        .mapToInt(Integer::parseInt)
                                        .toArray();
                PCB pcb = new PCB(splitted[0], splitted[1], splitted[2], splitted[3]);
                jobQ.add(pcb);//this is the critical task
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
