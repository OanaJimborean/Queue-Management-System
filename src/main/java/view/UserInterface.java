package view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class UserInterface extends JFrame {
    String[] option = {"Maximum number of clients", "Interval between arriving time"};


    private int numberQueues;
    private int numberClients;
    private int simInterval;
    private int minService;
    private int maxService;
    private int minArrival;
    private int optSelected = 1;
    private int cnt = 0;
    boolean buttonPressed;

    private int maxArrival;

    private JTextField minArrivalTime = new JTextField(30);
    private JTextField maxArrivalTime = new JTextField(30);
    private JTextField minServiceTime = new JTextField(30);
    private JTextField maxServiceTime = new JTextField(30);
    private JTextField nrOfClients = new JTextField(30);
    private JTextField nrOfQueues = new JTextField(30);
    private JTextField simulInterval = new JTextField(30);
    private JComboBox<String> optionsList = new JComboBox<String>(option);
    private JButton okButton = new JButton("OK");

    public void setValues() {
        //we update integer attributes for values added through mutating methods
        if (this.isButtonPressed()) {
            try {
                this.setNumberQueues(Integer.parseInt(nrOfQueues.getText()));
                this.setNumberClients(Integer.parseInt(nrOfClients.getText()));
                this.setMinServ(Integer.parseInt(minServiceTime.getText()));
                this.setMaxServ(Integer.parseInt(maxServiceTime.getText()));
                this.setMinArrival(Integer.parseInt(minArrivalTime.getText()));
                this.setMaxArrival(Integer.parseInt(maxArrivalTime.getText()));
                this.setSimInterval(Integer.parseInt(simulInterval.getText()));

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Some data has not been entered correctly", "Error", JOptionPane.ERROR_MESSAGE);
                System.out.println(e.getMessage());
            }

            this.setButtonPressed(false);
        }
    }


    public UserInterface() {

        this.setSize(450, 700);
        this.setTitle("User Interface");

        buttonPressed = false;
        JPanel content = new JPanel();
        content.setLayout(new GridLayout(11, 0));

        JPanel clients = new JPanel();
        clients.setLayout(new FlowLayout());
        JLabel clientsLabel = new JLabel("Number of clients:");
        clients.add(clientsLabel);
        clients.add(nrOfClients);
        content.add(clients);

        JPanel queues = new JPanel();
        queues.setLayout(new FlowLayout());
        JLabel queuesLabel = new JLabel("Number of queues:");
        queues.add(queuesLabel);
        queues.add(nrOfQueues);
        content.add(queues);


        JPanel symInterval = new JPanel();
        symInterval.setLayout(new FlowLayout());
        JLabel symLabel = new JLabel("Simulation Interval:");
        symInterval.add(symLabel);
        symInterval.add(simulInterval);
        content.add(symInterval);


        JPanel arrivingTimeLabel = new JPanel();
        JLabel arrivingTime = new JLabel("Minimum and maximum arrival time");
        arrivingTimeLabel.add(arrivingTime);
        content.add(arrivingTimeLabel);

        JPanel minArrivingTimePanel = new JPanel();
        minArrivingTimePanel.setLayout(new FlowLayout());
        JLabel minArrTimeLabel = new JLabel("Minimum:");
        minArrivingTimePanel.add(minArrTimeLabel);
        minArrivingTimePanel.add(minArrivalTime);
        content.add(minArrivingTimePanel);

        JPanel maxArrivingTimePanel = new JPanel();
        maxArrivingTimePanel.setLayout(new FlowLayout());
        JLabel maxArrTimeLabel = new JLabel("Maximum:");
        maxArrivingTimePanel.add(maxArrTimeLabel);
        maxArrivingTimePanel.add(maxArrivalTime);
        content.add(maxArrivingTimePanel);

        JPanel serviceTimePanel = new JPanel();
        JLabel serviceTime = new JLabel("Minimum and maximum service time");
        serviceTimePanel.add(serviceTime);
        content.add(serviceTimePanel);

        JPanel minServiceTimePanel = new JPanel();
        minServiceTimePanel.setLayout(new FlowLayout());
        JLabel minServiceTimeLabel = new JLabel("Minimum:");
        minServiceTimePanel.add(minServiceTimeLabel);
        minServiceTimePanel.add(minServiceTime);
        content.add(minServiceTimePanel);

        JPanel maxServiceTimePanel = new JPanel();
        maxServiceTimePanel.setLayout(new FlowLayout());
        JLabel maxServiceTimeLabel = new JLabel("Maximum:");
        maxServiceTimePanel.add(maxServiceTimeLabel);
        maxServiceTimePanel.add(maxServiceTime);
        content.add(maxServiceTimePanel);


        JPanel optionPanel = new JPanel();
        optionPanel.setLayout(new FlowLayout());
        optionPanel.add(new JLabel("Choose the option:"));
        optionPanel.add(optionsList);

        content.add(optionPanel);

        content.add(okButton);

        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                buttonPressed = true;
                cnt = 1;
                setValues();
            }
        });

        optionsList.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent arg0) {
                //Auto-generated method stub
                String s = (String) optionsList.getSelectedItem();
                assert s != null;
                if(s.equals("Maximum number of clients"))
                    setOptionSelected(1);
                else
                    setOptionSelected(2);
            }

        });
        this.setContentPane(content);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }



    public int getOptionSelected() {
        return optSelected;
    }

    public void setOptionSelected(int optionSelected) {
        this.optSelected = optionSelected;
    }

    public void printValues(){
        System.out.println("Queues: " + numberQueues + ", Clients:" + numberClients + ", Simulation: " + simInterval + " Option:" + optSelected);
    }

    public int getCnt() {
        return cnt;
    }

    public boolean isButtonPressed() {
        return buttonPressed;
    }

    public void setButtonPressed(boolean buttonPressed) {
        this.buttonPressed = buttonPressed;
    }

    public int getNumberQueues() {
        return numberQueues;
    }

    public void setNumberQueues(int nrQueues) {
        this.numberQueues = nrQueues;
    }

    public int getNumberClients() {
        return numberClients;
    }

    public void setNumberClients(int nrClients) {
        this.numberClients = nrClients;
    }

    public int getSimInterval() {
        return simInterval;
    }

    public void setSimInterval(int simInterval) {
        this.simInterval = simInterval;
    }

    public int getMinServ() {
        return minService;
    }

    public void setMinServ(int minServ) {
        this.minService = minServ;
    }

    public int getMaxServ() {
        return maxService;
    }

    public void setMaxServ(int maxServ) {
        this.maxService = maxServ;
    }

    public int getMinArrival() {
        return minArrival;
    }

    public void setMinArrival(int minArrival) {
        this.minArrival = minArrival;
    }

    public int getMaxArrival() {
        return maxArrival;
    }

    public void setMaxArrival(int maxArrival) {
        this.maxArrival = maxArrival;
    }


}