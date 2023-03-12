package strategy;
import java.util.ArrayList;
import model.ServiceQueue;
import model.Client;

public class ConcreteStrategyQueue implements Strategies {

    public void addClient(ArrayList<ServiceQueue> queueList, Client client) {
        //we add a client to a queue, according to the principle of the shortest queue
        if(queueList.size() < 1) {
            return ;
        }
        int minQSize = queueList.get(0).getTasks().size();
        ServiceQueue whichQueue = queueList.get(0);
        for(ServiceQueue q : queueList){
            if(q.getTasks().size() < minQSize){
                minQSize = q.getTasks().size();
                whichQueue = q;
            }
        }
        int waitingPer = whichQueue.getWaitingT().intValue();
        client.calculateFinishT(waitingPer);
        whichQueue.addClient(client);

    }

}