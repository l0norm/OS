import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class Loader implements Runnable{
    public static Queue<PCB> waitingQ = new LinkedList<>();

    public void run() {
        File jobs = new File("50_tasks_adjusted.txt");

        try (BufferedReader reader = new BufferedReader(new FileReader(jobs))) {
            String line;
            while ((line=reader.readLine()) != null){
                int splitted[] = Arrays.stream(line.split("[:;]"))
                                        .mapToInt(Integer::parseInt)
                                        .toArray();
                waitingQ.add(SystemCalls.CreateProcess(splitted[0], splitted[1], splitted[2], splitted[3]));
            }
        } catch (Exception e){
            System.out.println("error: " + e.getMessage());
        } 
    }

}