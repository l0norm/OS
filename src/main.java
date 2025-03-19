import java.io.File;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;

public class main {
    public static void main(String[] args) {

    // reading from job.txt file 
    //creates PCB and put them in the job queue


    //the inputs will be [processID : burst time : priority ; memory required ]



        ConcurrentLinkedQueue<PCB> jobQ = new ConcurrentLinkedQueue<>();




        File obj = new File("null");
        Scanner reader = null;
        try{

            reader = new Scanner(obj);




            
            while(reader.hasNextLine()){
                String data = reader.nextLine();
                int splitted[] = Arrays.stream(data.split(":;"))
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
