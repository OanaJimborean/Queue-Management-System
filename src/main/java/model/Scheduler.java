package model;
import java.util.ArrayList;
import strategy.*;

public class Scheduler {
    private ArrayList<ServiceQueue> queueList;
    private int nrClients;  //maximum nr of clients
    private int nrQueues;   //maximum tasks per server
    private Strategies strategy;

    public ArrayList<ServiceQueue> getServers() {
        return queueList;
    }

    public int getNrQueues() {
        return nrQueues;
    }

    public int getNrClients() {
        return nrClients;
    }

    public Strategies getStrategy() {
        return strategy;
    }

    public Scheduler(int nrQueues, int nrClients){
        //we add queues at the queue list
        this.nrQueues = nrQueues;
        this.nrClients = nrClients;
        queueList = new ArrayList<ServiceQueue>(nrQueues);
        for(int i = 0; i < nrQueues; i++){
            queueList.add(new ServiceQueue());
        }
    }

    public void changeStrategy(SelectionPolicy policy){
        //we set the strategy for distributing clients to queues
        if(policy == SelectionPolicy.SHORTEST_QUEUE){
            strategy = new ConcreteStrategyQueue();
        }
        if(policy == SelectionPolicy.SHORTEST_TIME){
            strategy = new ConcreteStrategyTime();
        }
    }


    public void dispatchTask(Client client){
        strategy.addClient(queueList, client);

    }


    public String printQ() {
        //printing the queues
        StringBuilder result = new StringBuilder(new String("")); //creating a modifiable string
        int count = 1;
        for(ServiceQueue q : queueList) {
            result.append("Queue ").append(count).append(":");
            for(Client cl : q.getTasks()) {
                result.append("c[").append(cl.getId()).append(";").append(cl.gettArrival()).append(";").append(cl.gettService()).append("] ");
            }
            result.append("Busy time : ").append(q.getWaitingT().intValue()).append("\n");
            count++;
        }
        result.append("\n");
        return result.toString();
    }

}




