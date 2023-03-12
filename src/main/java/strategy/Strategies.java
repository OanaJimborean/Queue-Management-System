package strategy;
import java.util.ArrayList;

import model.Client;
import model.ServiceQueue;

public interface Strategies {
    void addClient(ArrayList<ServiceQueue> queueList, Client client);

}