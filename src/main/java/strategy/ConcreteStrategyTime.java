package strategy;
import model.Client;
import model.ServiceQueue;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class ConcreteStrategyTime implements Strategies {

    public void addClient(ArrayList<ServiceQueue> queueList, Client client) {
        //we add a client to a queue, according to the principle of the shortest time
        if (queueList.size() < 1) {
            return;
        }
        AtomicInteger minTime = queueList.get(0).getWaitingT();
        ServiceQueue whichQueue = queueList.get(0);
        for (ServiceQueue q : queueList) {
            if (q.getWaitingT().intValue() < minTime.intValue()) {
                minTime = q.getWaitingT();
                whichQueue = q;
            }
        }
        int waitingPer = whichQueue.getWaitingT().intValue();
        client.calculateFinishT(waitingPer);
        whichQueue.addClient(client);
    }
}
