
public class Main {
    public static int memoryAvailable = 2048;
    public static Algorithms algType;

    public synchronized static void addMemory(int amt){
        memoryAvailable+=amt;
    }
    public synchronized static int getMemory(){
        return memoryAvailable;
    }

    public static void main(String[] args) {
        Thread loadThread = new Thread(new Loader());
        Thread readyThread = new Thread(new Ready());

        loadThread.start();
        try {
            loadThread.join();
        } catch (InterruptedException e) {
            System.err.println("could not join load thread: "+e);
        }



        readyThread.start();

        StringBuilder chart = new StringBuilder();
        StringBuilder processID = new StringBuilder();
        StringBuilder burstTimeEnd = new StringBuilder("0");
        int totalBurstTime = 0;
        switch (algType) {
            case FCFS:
                while (!Ready.readyQ.isEmpty()) {
                    PCB pcb = new PCB(Ready.readyQ.poll());
                    totalBurstTime+=pcb.burstTime;

                    chart.append("+").append("-".repeat(pcb.burstTime));
                    processID.append("|").append(" ".repeat(pcb.burstTime));
                    burstTimeEnd.append(" ".repeat(pcb.burstTime)).append(totalBurstTime);
                }
                break;
            case RR:
                int quantum = 7;
                while (!Ready.readyQ.isEmpty()){
                    PCB pcb = new PCB(Ready.readyQ.poll());
                    
                    
                    pcb.burstTime -=quantum;
                    int bursted;
                    if (pcb.burstTime<=0) {
                        bursted = pcb.burstTime;
                        pcb.burstTime = 0;
                        
                    } else {
                        Ready.readyQ.add(pcb); //adding it again if there is still burst time for the process
                        bursted = quantum;
                    }
                    totalBurstTime+=bursted;
                    
                    chart.append("+").append("-".repeat(bursted));
                    processID.append("|").append(" ".repeat(bursted));
                    burstTimeEnd.append(" ".repeat(bursted)).append(totalBurstTime);
                    
                }
                break;
            case PS:
                while(!Ready.readyQ.isEmpty()){
                    Ready.readyQ.stream()
                        .sorted((p1, p2) -> Integer.compare(p1.priority, p2.priority))
                        .forEach(Ready.readyQ::add); // ths to sort the heap for its priority
                    PCB pcb = new PCB(Ready.readyQ.poll());
                    pcb.burstTime -= 1;
                    if (pcb.burstTime <= 0) {
                    } else {
                        Ready.readyQ.add(pcb);
                    }
            
                    totalBurstTime+=1;
                    chart.append("+").append("-".repeat(pcb.burstTime));
                    processID.append("|").append(" ".repeat(pcb.burstTime));
                    burstTimeEnd.append(" ".repeat(pcb.burstTime)).append(totalBurstTime);
                }
                break;
        }
        
        try{
            readyThread.join();
        } catch (InterruptedException e) {
            System.err.println("could not join ready thread: "+e);
        }
        
    }
}
