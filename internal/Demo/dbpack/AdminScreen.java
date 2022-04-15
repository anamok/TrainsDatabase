package dbpack;
import dbpack.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.*;

public class AdminScreen extends JFrame implements ActionListener {
    
    JPanel adminCenter = new JPanel(new GridLayout(0,1));
    JPanel topMessage = new JPanel(new GridLayout(0,1));

    JButton importButton = new JButton("Import"); //create insert statement with a form
    JButton exportButton = new JButton("Export"); //create update statement 
    JButton InitButton = new JButton("Initiate Tables"); //search/view data for customers, trips or reservations
    JButton delTabButton = new JButton("Delete Tables"); //extra button
    JButton delDataButton = new JButton("Delete Data"); //extra button
    JButton exitButton = new JButton("Logout"); //Log out of program

    JPanel guiPush;
    JPanel centerPush;

    String passwordp;
    String userp = "";

    public AdminScreen(JPanel gui, JPanel center, String password, String user) {

        passwordp = password;
        userp = user;

        center.setVisible(false);

        adminCenter.setBackground(Color.BLACK);
        adminCenter.setBorder(new EmptyBorder(30, 20, 50, 20));
        gui.add(adminCenter, BorderLayout.CENTER);

        JLabel welcomeMessage = new JLabel("Welcome!", SwingConstants.CENTER);
        welcomeMessage.setFont(new Font("Courier New", Font.BOLD, 40));
        welcomeMessage.setForeground(Color.WHITE);

        welcomeMessage.setVerticalAlignment(SwingConstants.BOTTOM);

        //adminCenter.add(welcomeMessage);
        topMessage.add(welcomeMessage);
        topMessage.setBackground(Color.BLACK);

        JLabel advise = new JLabel("Please select a menu option...", SwingConstants.CENTER);
        advise.setFont(new Font("Courier New", Font.BOLD, 20));
        advise.setForeground(Color.WHITE);

        advise.setVerticalAlignment(SwingConstants.TOP);

        topMessage.add(advise);

        adminCenter.add(topMessage);

        JPanel adminButtonPanel = new JPanel(new GridLayout(0,1));
        adminButtonPanel.setBackground(Color.BLACK);
        adminButtonPanel.setBorder(new EmptyBorder(0, 250, 0, 250));

        buttonStyle(importButton);
        buttonStyle(exportButton);
        buttonStyle(InitButton);
        buttonStyle(delTabButton);
        buttonStyle(delDataButton);
        buttonStyle(exitButton);

        adminButtonPanel.add(importButton);
        adminButtonPanel.add(exportButton);
        adminButtonPanel.add(InitButton);
        adminButtonPanel.add(delTabButton);
        adminButtonPanel.add(delDataButton);
        adminButtonPanel.add(exitButton);

        adminCenter.add(adminButtonPanel);

        JPanel squish = new JPanel();
        squish.setBackground(Color.BLACK);
    //   JPanel squish2 = new JPanel();

        adminCenter.add(squish);
     // adminCenter.add(squish2);

        guiPush = gui;
        centerPush = adminCenter;

        addActionEvent();

    }

    private static void buttonStyle(JButton b){
        b.setForeground(Color.WHITE);
        b.setBackground(Color.BLACK);
        b.setBorder(new LineBorder(Color.WHITE));
        b.setFont(new Font("Courier New", Font.BOLD, 20));
    }

    public void addActionEvent(){
        importButton.addActionListener(this);
        exportButton.addActionListener(this);
        InitButton.addActionListener(this);
        delTabButton.addActionListener(this);
        delDataButton.addActionListener(this);
        exitButton.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        //String inputRes = null;

        if (source == importButton){
            //new customer
           // AddCustomer newCustomer = new AddCustomer(guiPush, centerPush, passwordp, userp);
        } else if (source == exportButton){

        } else if (source == InitButton){
            //
        } else if (source == delTabButton){
            //new reservation
            

        } else if (source == delDataButton){
            //
        } else if (source == exitButton){
            // return to login 
            AgentLogin agentP = new AgentLogin(guiPush, centerPush);

        } 


    }
}
