class Sum {
    private long[] sum;

    public Sum(){
        sum = new long[4];
    }

    public void setSum(int id, long sum){
        this.sum[id] = sum;
    }
    public long getSum(){
        long result = 0;
        for(int i = 0;i<4;i++){
            result += sum[i];
        }
        return result;
    }



}

