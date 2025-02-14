

class myThreads extends Thread {
    private int start;
    private int id;
  
    

   

    public myThreads(int id){
        this.start = id * 25000000;
        this.id = id;
    }
    public void run(){
        long sum = 0;
        int i = 0;
        while(i<25000000){
            sum +=(start + i + 1);
            i++;
        }
        Driver.sumy.setSum(id, sum);

    }
    
}
