package model;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.LinkedList;

public class ServiceQueue implements Runnable{
    private BlockingQueue<Client> tasks; //A thread trying to enqueue an element in a full queue is blocked
                                        // until some other thread makes space in the queue, either by dequeuing one or more elements or clearing the queue completely
    private AtomicInteger waitingT; //atomic variable access is more efficient than accessing these variables through synchronized code
    public Thread threadQ; //the work thread that will be executed once a new queue instance is created



    public AtomicInteger getWaitingT() {
        return waitingT;
    }

    public void setWaitingT(AtomicInteger waitingT) {
        this.waitingT = waitingT;
    }

    public Thread getThreadQ() {
        return threadQ;
    }


    public ServiceQueue() {
        tasks = new ArrayBlockingQueue<Client>(1000);
        waitingT = new AtomicInteger(0);
        threadQ = new Thread(this);
        threadQ.start();
    }

    public void addClient(Client newClient) {
        //we add a client to the queue and at the same time we update its own waiting time
        tasks.add(newClient);
        waitingT.addAndGet(newClient.gettService());
    }


    public void run() {
        while (true) {
            //we retrieve the client that is at the end of the queue
            Client nextClient = this.getTasks().peek();

            if (nextClient != null) {
                try {
                    //we fall asleep the queue thread for a period equal in seconds to the processing time of the client extracted from the queue
                    Thread.sleep(nextClient.gettService() * 1000L);
                } catch (InterruptedException e) {
                    //Auto-generated catch block
                    e.printStackTrace();
                }
                //After this period has elapsed, we remove the client from the client list
                tasks.remove();
            }
        }
    }

    public LinkedList<Client> getTasks() {
        LinkedList<Client> allTasks = new LinkedList<Client>();
        for(Client client : tasks) {
            allTasks.add(client);
        }
        return allTasks;
    }

}
