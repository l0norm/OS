

public class Driver {
    public static Sum sumy = new Sum();

    public static void main(String[] args){
       

        long startTime = System.nanoTime();

        // myThreads[] thread = new myThreads[4];

        // for(int i = 0;i<4;i++){
        //     thread[i] = new myThreads(i);
        //     thread[i].start();
        // }

        // for(int i = 0 ;i<4; i++){
        //     try {
        //         thread[i].join();
        //     } catch (Exception e) {
        //     }
        // }









        long sum = 0;
        for(int i = 1; i<=100000000;i++){
            sum+=i;
        }
        long TotalSum = sum;

        long endTime = System.nanoTime();
        long duration = endTime - startTime;

        System.out.println("multithread : " + TotalSum);
        System.out.println("time : " + duration);


        

    }
}
