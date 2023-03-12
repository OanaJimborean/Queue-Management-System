package model;

public class Client implements Comparable<Client>{
    private int id;         //client id
    private int tArrival;  //arival time
    private int tService; //processing time
    private int tFinish; //finish time

    public Client(int tArrival, int tService){
        this.tArrival = tArrival;
        this.tService = tService;
        this.tFinish = 0;
        this.id = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int gettArrival() {
        return tArrival;
    }

    public int gettService() {
        return tService;
    }

    public int gettFinish() {
        return tFinish;
    }

    public void settFinish(int tFinish) {
        this.tFinish = tFinish;
    }

    public void calculateFinishT(int periodOfWaiting) {  //period of waiting of a queue
        //we calculate and set a client's finish time
        int tFinish = this.gettArrival() + this.gettService()  + periodOfWaiting;
        this.settFinish(tFinish);
    }

    public String toString() {
        return "The client has the arrival time " + tArrival + " and the service time " + tService;
    }

    public int compareTo(Client arg) {
        if (this.gettArrival() == arg.gettArrival())
            return 0;
        if (this.gettArrival() < arg.gettArrival())
            return 1;
        if (this.gettArrival() > arg.gettArrival())
            return -1;
        return 0;
    }
}
