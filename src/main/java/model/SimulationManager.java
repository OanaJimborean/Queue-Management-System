package model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

import javax.swing.JOptionPane;

import strategy.SelectionPolicy;
import view.UserInterface;
import view.QueueInterface;

public class SimulationManager implements Runnable {
    public int timeLimit = 15;
    public int maxTimeProcess = 10;
    public int minTimeProcess = 1;
    public int nrOfServers = 3;
    public int nrOfClients = 100;
    public int minArrTime = 0;
    public int maxArrTime = 2;
    public int option = 2;  //1-for generation without taking into account the arrival interval of customers
                            //2-otherwise
    public SelectionPolicy selPolicy = SelectionPolicy.SHORTEST_TIME;

    private Scheduler scheduler;
    private ArrayList<Client> generatedTasks;
    private QueueInterface frame;
    private UserInterface userInterface;

    public SimulationManager(UserInterface data) {
        this.setUserInterface(data);
        while (userInterface.getCnt() == 0) {

            System.out.println(userInterface.getCnt());
        }
        data.printValues();

        option = userInterface.getOptionSelected();
        timeLimit = userInterface.getSimInterval();
        nrOfServers = userInterface.getNumberQueues();
        nrOfClients = userInterface.getNumberClients();
        minTimeProcess = userInterface.getMinServ();
        maxTimeProcess = userInterface.getMaxServ();
        minArrTime = userInterface.getMinArrival();
        maxArrTime = userInterface.getMaxArrival();
        scheduler = new Scheduler(nrOfServers, 100);
        scheduler.changeStrategy(selPolicy);
        frame = new QueueInterface(nrOfServers);
        generateNRandomTasks(nrOfClients);

    }

    private void generateNRandomTasks(int nrC) {
        generatedTasks = new ArrayList<Client>(nrC);
        int j = 0;
        int randomProcessTime = -1;
        int randomArrTime = -1;
        if (option == 1) {
            System.out.println("There are generated " + nrC + " clients");
            for (int i = 0; i < nrC; i++) {
                boolean ok = true;
                while (ok) {
                    //we do not take in account the previous arrival time or the arrival interval between costumers
                    randomProcessTime = (int) (Math.random() * 10); //returns a random nr between 0 and 10
                    if (j == 0)
                        randomArrTime = 0;
                    else
                        randomArrTime = (int) (Math.random() * 100);  //generates a random nr between 0 and 100

                    System.out.println("random process : " + randomProcessTime);
                    System.out.println("random time: " + randomArrTime);

                    if (randomProcessTime < minTimeProcess || randomProcessTime > maxTimeProcess
                            || randomArrTime + randomProcessTime >= timeLimit)
                        ok = true;
                    else {
                        ok = false;
                    }
                }
                Client newClient = new Client(randomArrTime, randomProcessTime);
                generatedTasks.add(newClient);
                System.out.println("There were generated " + (j + 1) + " clients");
                j++;
            }
        } else if (option == 2) {
            int prevArrivalTime = 0;
            boolean ok = true;
            int nr = (int) (Math.random() * 1000); //generating maximum 1000 nr of clients
            if (nr > nrC || nr == 0)
                nr = nrC;
            System.out.println("There will be generated maximum " + nr + " numbers");
            while (nr > 0) {
                ok = true;
                while (ok) {
                    System.out.println("previous: " + prevArrivalTime);
                    if (j == 0)
                        randomArrTime = 0;
                    else
                        randomArrTime = (int) (prevArrivalTime + Math.random() * 100); //generates a random nr between 0 and 100

                    randomProcessTime = (int) (Math.random() * 10);//generates a random nr between 0 and 10

                    System.out.println("random process : " + randomProcessTime);
                    System.out.println("random time: " + randomArrTime);

                    //the customer list will be ordered in ascending order of arrival times
                    if (randomProcessTime < minTimeProcess || maxTimeProcess < randomProcessTime
                            || randomArrTime - prevArrivalTime < minArrTime
                            || randomArrTime - prevArrivalTime > maxArrTime) {
                        ok = true;
                        if (j == 0) {
                            if (randomProcessTime < minTimeProcess || maxTimeProcess < randomProcessTime)
                                ok = true;
                            else {
                                ok = false;
                            }
                        }
                    } else {
                        ok = false;
                        prevArrivalTime = randomArrTime;
                    }
                    System.out.println(ok);

                }
                System.out.println(randomArrTime + " and " + randomProcessTime);
                Client newClient = new Client(randomArrTime, randomProcessTime);
                generatedTasks.add(newClient);
                System.out.println("There were generated " + (j + 1) + " clients");
                j++;
                nr--;
            }
        }
        Collections.sort(generatedTasks);

    }

    @SuppressWarnings("deprecation")
    public void run() {
        int nrClient = 0;
        // TODO Auto-generated method stub
        int currentTime = 0;
        int averageWaitingTime = 0;
        int averageProcessingTime = 0;
        int peakHour = -1;
        int maxPeakHour = Integer.MIN_VALUE;
        while (currentTime < timeLimit) {
            for (Client customer : generatedTasks) {
                //at each moment of time, if the customers have the arrival time equal to the current time
                if (customer.gettArrival() == currentTime) {
                    scheduler.dispatchTask(customer);
                    averageProcessingTime += customer.gettService();
                    customer.setId(nrClient + 1);
                    int poz = -1;

                    for (ServiceQueue a : scheduler.getServers()) {
                        if (a.getTasks().contains(customer)) {
                            //to the queues
                            //send tasks to queue by calling the dispatch method from scheduler
                            poz = scheduler.getServers().indexOf(a);
                            averageWaitingTime += a.getWaitingT().intValue() - customer.gettService();
                            // customer.calculateFinishTime(a.getWaitingPeriod().intValue());
                        }
                    }
                    System.out.println("#" + nrClient + ":" + customer.toString() + " assigned to the queue " + (poz + 1)
                            + " finishes at time " + customer.gettFinish());
                    frame.getLogger().append("C" + customer.getId() + " having arriving time " + customer.gettArrival()
                                    + " and process time " + customer.gettService()
                                    + " has been assigned to the queue " + (poz + 1) + "\n");
                    nrClient++;

                }
                if (customer.gettFinish() == currentTime) {
                    if (currentTime != 0)
                        frame.getLogger().append(
                                "C" + customer.getId() + " left the queue at the moment t=" + currentTime + "\n");
                }
            }

            System.out.println("Current time:" + currentTime);
            frame.getLogger().append("Current time:" + currentTime+"\n");
            frame.getCurrentTime().setText("Current Time:" + currentTime);
            int cnt = 0;
            int peakHr = 0;
            for (ServiceQueue q : scheduler.getServers()) {
                String s = new String("");
                for (Client clt : q.getTasks()) {
                    s += "c" + clt.getId();
                   // frame.getqTextFields().customer.getId;

                }
                peakHr += q.getTasks().size();

                frame.getqTextFields()[cnt].setText(s);
                frame.getBusyLabels()[cnt].setText("Busy for " + q.getWaitingT());
                cnt++;
            }
            if (peakHr > maxPeakHour) {
                maxPeakHour = peakHr;
                peakHour = currentTime;
            }
            System.out.println(scheduler.printQ());

            currentTime++;
            for (ServiceQueue queue : scheduler.getServers()) {
                if (queue.getWaitingT().intValue() > 0) {
                    queue.setWaitingT(new AtomicInteger(queue.getWaitingT().decrementAndGet()));
                }
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        for (ServiceQueue q : scheduler.getServers()) {
            q.getThreadQ().stop();
        }
        JOptionPane.showMessageDialog(null,
                "Average Waiting time: " + (averageWaitingTime / (float) nrClient) + "\n" + "Average Service time: "
                        + (averageProcessingTime / (float) nrClient) + "\n" + "Peak Hour: " + peakHour + " with "
                        + maxPeakHour + " clients\n");
    }

    public static void main(String[] args) {
        UserInterface view = new UserInterface();
        SimulationManager sim = new SimulationManager(view);
        Thread t = new Thread(sim);
        t.start();

        try{
            PrintStream myconsole = new PrintStream(new File("D:\\AN2_SEMESTRUL2\\PT_Lab\\PT2022_30422_Jimborean_Oana_Assignment_2\\output.txt"));
            System.setOut(myconsole);
        }
        catch(FileNotFoundException fx){
            System.out.println(fx);
        }

    }

    public void setUserInterface(UserInterface userInterface) {
        this.userInterface = userInterface;
    }
}
