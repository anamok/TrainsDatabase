package dbpack;
import dbpack.*;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.*;


public class AgentScreen extends JFrame implements ActionListener {

    JPanel agentcenter = new JPanel(new GridLayout(0,1));
    JPanel topMessage = new JPanel(new GridLayout(0,1));

    JButton addButton = new JButton("Add Customer"); //create insert statement with a form
    JButton editButton = new JButton("Edit Customer"); //create update statement 
    JButton searchButton = new JButton("Search Database"); //search/view data for customers, trips or reservations
    JButton addReservationButton = new JButton("Add Reservation"); //extra button
    JButton tmpButton2 = new JButton("RESERVED"); //extra button
    JButton exitButton = new JButton("Logout"); //Log out of program

    JPanel guiPush;
    JPanel centerPush;

    String passwordp;
    String userp = "";

    public AgentScreen(JPanel gui, JPanel center, String password, String user) {

        passwordp = password;
        userp = user;

        center.setVisible(false);


        agentcenter.setBackground(Color.BLACK);
        agentcenter.setBorder(new EmptyBorder(30, 20, 50, 20));
        gui.add(agentcenter, BorderLayout.CENTER);

        JLabel welcomeMessage = new JLabel("Welcome!");
        welcomeMessage.setFont(new Font("Courier", Font.BOLD, 40));
        welcomeMessage.setForeground(Color.WHITE);

        welcomeMessage.setVerticalAlignment(SwingConstants.BOTTOM);

        //agentcenter.add(welcomeMessage);
        topMessage.add(welcomeMessage);
        topMessage.setBackground(Color.BLACK);

        JLabel advise = new JLabel("Please select a menu option...");
        advise.setFont(new Font("Courier", Font.BOLD, 20));
        advise.setForeground(Color.WHITE);

        advise.setVerticalAlignment(SwingConstants.TOP);

        topMessage.add(advise);

        agentcenter.add(topMessage);

        JPanel agentButtonPanel = new JPanel(new GridLayout(0,1));

        buttonStyle(addButton);
        buttonStyle(editButton);
        buttonStyle(searchButton);
        buttonStyle(addReservationButton);
        buttonStyle(tmpButton2);
        buttonStyle(exitButton);

        agentButtonPanel.add(addButton);
        agentButtonPanel.add(editButton);
        agentButtonPanel.add(searchButton);
        agentButtonPanel.add(addReservationButton);
        agentButtonPanel.add(tmpButton2);
        agentButtonPanel.add(exitButton);

        agentcenter.add(agentButtonPanel);

        JPanel squish = new JPanel();
        squish.setBackground(Color.BLACK);
    //   JPanel squish2 = new JPanel();

        agentcenter.add(squish);
     // agentcenter.add(squish2);

        guiPush = gui;
        centerPush = agentcenter;

        addActionEvent();

    }

    private static void buttonStyle(JButton b){
        b.setForeground(Color.WHITE);
        b.setBackground(Color.BLACK);
        b.setBorder(new LineBorder(Color.WHITE));
        b.setFont(new Font("Courier", Font.BOLD, 20));
    }

    public void addActionEvent(){
        addButton.addActionListener(this);
        editButton.addActionListener(this);
        searchButton.addActionListener(this);
        addReservationButton.addActionListener(this);
        tmpButton2.addActionListener(this);
        exitButton.addActionListener(this);
    }


    public void actionPerformed(ActionEvent e){
        Object source = e.getSource();
        //String inputRes = null;

        if (source == addButton){
            //new customer
            AddCustomer newCustomer = new AddCustomer(guiPush, centerPush, passwordp, userp);
        } else if (source == editButton){

        } else if (source == searchButton){
            //
        } else if (source == addReservationButton){
            //new reservation
            AddReservation newReservation = new AddReservation(guiPush, centerPush, passwordp, userp);

        } else if (source == tmpButton2){
            //
        } else if (source == exitButton){
            // return to login 
            AgentLogin agentP = new AgentLogin(guiPush, centerPush);

        } 


    }

}
