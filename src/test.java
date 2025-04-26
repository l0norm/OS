import java.util.HashMap;

public class test {

    public static void main(String[] args) {
        // Test case 1: Create a PCB with valid parameters
        HashMap<String, Integer> params1 = new HashMap<>();

        params1.put("processID", 1);
        params1.put("burstTime", 10);
        
        // System.out.println(params1.get("processID"));
        params1.replace("processID", 2);
        System.out.println(params1.get("processID"));
        


    }
}
