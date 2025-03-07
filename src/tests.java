import java.util.Arrays;

public class tests {
    public static void main(String[] args) {


        String s = "1 2 3 4 5";  // A string containing space-separated numbers

        // Using Streams to convert the string into an integer array
        int[] arr = Arrays.stream(s.split(" "))
                                .mapToInt(Integer::parseInt)
                                .toArray();

        System.out.println(arr[0] + 1);
        
    }
}
