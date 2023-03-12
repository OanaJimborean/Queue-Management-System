package view;

import javax.swing.JTextField;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JFrame;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import javax.swing.ScrollPaneConstants;

import java.awt.FlowLayout;
import java.awt.GridLayout;



public class QueueInterface extends JFrame {
    private JTextArea logger = new JTextArea();
    private JScrollPane loggerScroll = new JScrollPane(logger);

    private JLabel[] qLabels;
    private JLabel[] busyLabels;
    private JLabel currentTime;
    private JTextField[] qTextFields;


    public JTextArea getLogger() {
        return logger;
    }

    public JLabel getCurrentTime() {
        return currentTime;
    }

    public JTextField[] getqTextFields() {
        return qTextFields;
    }

    public JLabel[] getBusyLabels() {
        return busyLabels;
    }


    public QueueInterface(int nrQ) {

        this.setSize(600,1000);

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(nrQ + 2, 0));
        currentTime = new JLabel("Current time: ");
        content.add(currentTime);

        qLabels = new JLabel[nrQ];
        qTextFields = new JTextField[nrQ];
        busyLabels = new JLabel[nrQ];

        for (int i = 0; i < nrQ; i++) {
            JPanel jpanel = new JPanel();
            jpanel.setLayout(new FlowLayout());
            qLabels[i] = new JLabel(new String("Q"+(i+1)));
            qTextFields[i]=new JTextField(40);
            busyLabels[i]=new JLabel(new String("Busy for:"));

            jpanel.add(qLabels[i]);
            jpanel.add(qTextFields[i]);
            jpanel.add(busyLabels[i]);

            content.add(jpanel);
        }
        loggerScroll.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        content.add(loggerScroll);
        this.setContentPane(content);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        QueueInterface view = new QueueInterface(5);
    }




}