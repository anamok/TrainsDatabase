package dbpack;
import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.util.Properties;

public class AddReservation extends JFrame implements ActionListener {
    JPanel reservationCenter = new JPanel(new GridLayout(0,1));
    JButton backRButton = new JButton("Back");
    JButton addRButton = new JButton("Add Reservation");
    JButton getPrice = new JButton("Get Price");
    
    JPanel guiPush;
    Statement st;

    JTextField reservationNumber;
    JTextField customer_id;
    JTextField price;
    JTextField totalPaid;
    JTextField balance;
    JTextField startA;
    JTextField endB;
    JTextField route;
    JTextField trainNumber;
    JTextField day;
    JTextField time;
    JTextField ticket;

    String passwordp = "";
    String userp = "";

    Connection conn;

    public AddReservation(JPanel gui, JPanel center, String password, String user){
        passwordp = password;
        userp = user;

        guiPush = gui;

        center.setVisible(false);

        reservationCenter.setBackground(Color.BLACK);
        reservationCenter.setBorder(new EmptyBorder(0, 20, 0, 20));
        gui.add(reservationCenter, BorderLayout.CENTER);

        JPanel squish = new JPanel();
        squish.setBackground(Color.BLACK);

        JLabel headerMessage = new JLabel("Reservation Entry");
        headerMessage.setFont(new Font("Courier New", Font.BOLD, 40));
        headerMessage.setForeground(Color.WHITE);
        headerMessage.setVerticalAlignment(SwingConstants.BOTTOM);

        JPanel topMessage = new JPanel(new GridLayout(0,1));
        topMessage.add(squish);
        topMessage.add(headerMessage);
        topMessage.setBackground(Color.BLACK);
        
        JLabel advise = new JLabel("Please enter the following...");
        advise.setFont(new Font("Courier New", Font.BOLD, 20));
        advise.setForeground(Color.WHITE);

        advise.setVerticalAlignment(SwingConstants.TOP);

        topMessage.add(advise);

        reservationCenter.add(topMessage);

        buttonStyle(addRButton);
        buttonStyle(backRButton);
        buttonStyle(getPrice);
        addActionEvent();

        JLabel sStation = new JLabel("Start Station(as ID): ", SwingConstants.RIGHT);
        JLabel dStation = new JLabel("End Station(as ID): ", SwingConstants.RIGHT);
        JLabel rtNum = new JLabel("Route: ", SwingConstants.RIGHT);
        JLabel dy = new JLabel("Day: ", SwingConstants.RIGHT);
        JLabel tm = new JLabel("Time(as 00:00): ", SwingConstants.RIGHT);
        JLabel tn = new JLabel("Train: ", SwingConstants.RIGHT);
        JLabel pricetxt = new JLabel("Price: ", SwingConstants.RIGHT);
        JLabel customerID = new JLabel("Customer ID: ", SwingConstants.RIGHT);
        JLabel amtPaid = new JLabel("Amount Paid: ", SwingConstants.RIGHT);
        JLabel remBalance = new JLabel("Remaining Balance: ", SwingConstants.RIGHT);
        JLabel resNumber = new JLabel("Reservation Number: ", SwingConstants.RIGHT);
        JLabel ticketNumber = new JLabel("Ticket Number: ", SwingConstants.RIGHT);

        textStyle(sStation);
        textStyle(dStation);
        textStyle(rtNum);
        textStyle(dy);
        textStyle(tm);
        textStyle(tn);
        textStyle(pricetxt);
        textStyle(customerID);
        textStyle(amtPaid);
        textStyle(remBalance);
        textStyle(resNumber);
        textStyle(ticketNumber);

        reservationNumber = new JTextField("xx");
        reservationNumber.setEditable(false);
        customer_id = new JTextField();
        price = new JTextField("xx");
        price.setEditable(false);
        totalPaid = new JTextField();
        balance = new JTextField("xx");
        balance.setEditable(false);
        startA = new JTextField();
        endB = new JTextField();
        route = new JTextField();
        trainNumber = new JTextField("xx");
        trainNumber.setEditable(false);
        day = new JTextField();
        time = new JTextField();
        ticket = new JTextField("xx");
        ticket.setEditable(false);


        formStyle(reservationNumber);
        formStyle(customer_id);
        formStyle(price);
        formStyle(totalPaid);
        formStyle(balance);
        formStyle(startA);
        formStyle(endB);
        formStyle(route);
        formStyle(trainNumber);
        formStyle(day);
        formStyle(time);
        formStyle(ticket);

        JPanel squish2 = new JPanel();
        squish2.setBackground(Color.BLACK);
        JPanel squish3 = new JPanel();
        squish3.setBackground(Color.BLACK);

        JPanel formPanel = new JPanel(new GridLayout(0,2));

        JPanel squish4 = new JPanel();
        squish4.setBackground(Color.BLACK);
        JPanel squish5 = new JPanel();
        squish5.setBackground(Color.BLACK);
        JPanel squish6 = new JPanel();
        squish6.setBackground(Color.BLACK);
    
        formPanel.add(sStation);
        formPanel.add(startA);
        formPanel.add(dStation);
        formPanel.add(endB);
        formPanel.add(dy);
        formPanel.add(day);
        formPanel.add(tm);
        formPanel.add(time);
        formPanel.add(rtNum);
        formPanel.add(route);
        formPanel.add(tn);
        formPanel.add(trainNumber);
        formPanel.add(pricetxt);
        formPanel.add(price);
        formPanel.add(squish4);
        formPanel.add(getPrice);
        formPanel.add(squish5);
        formPanel.add(squish6);

        formPanel.setBackground(Color.BLACK);

        reservationCenter.add(formPanel);

        //Reservation entry
        JPanel formPanel2 = new JPanel(new GridLayout(0,2));
        formPanel2.setBackground(Color.BLACK);

        JPanel squish7 = new JPanel();
        squish7.setBackground(Color.BLACK);
        JPanel squish8 = new JPanel();
        squish8.setBackground(Color.BLACK);
        JPanel squish9 = new JPanel();
        squish9.setBackground(Color.BLACK);
        JPanel squish10 = new JPanel();
        squish10.setBackground(Color.BLACK);

        formPanel2.add(customerID);
        formPanel2.add(customer_id);
        formPanel2.add(amtPaid);
        formPanel2.add(totalPaid);
        formPanel2.add(remBalance);
        formPanel2.add(balance);
        formPanel2.add(resNumber);
        formPanel2.add(reservationNumber);
        formPanel2.add(ticketNumber);
        formPanel2.add(ticket);
        formPanel2.add(backRButton);
        formPanel2.add(addRButton);
        formPanel2.add(squish7);
        formPanel2.add(squish8);
        formPanel2.add(squish9);
        formPanel2.add(squish10);

        reservationCenter.add(formPanel2);
        reservationCenter.add(squish3);
    }

    private static void formStyle(JTextField b){
        b.setBackground(Color.BLACK);
        b.setFont(new Font("Courier New", Font.BOLD, 18));
        b.setForeground(Color.WHITE);
    }

    private static void buttonStyle(JButton b){
        b.setForeground(Color.WHITE);
        b.setBackground(Color.BLACK);
        b.setBorder(new LineBorder(Color.WHITE));
        b.setFont(new Font("Courier New", Font.BOLD, 20));
    }

    private static void textStyle(JLabel b){
        b.setForeground(Color.WHITE);
        //b.setBackground(Color.BLACK);
        b.setFont(new Font("Courier New", Font.BOLD, 20));
    }


    public void addActionEvent(){
        getPrice.addActionListener(this);
        addRButton.addActionListener(this);
        backRButton.addActionListener(this);
        
    }

    // Necessary for connection to db
    public void connectionDB() {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException b) {
            System.out.println(b.toString());
        }
        
        String url = "jdbc:postgresql://localhost:5432/";
        Properties props = new Properties();

        //System.out.println("user: " + userp + " pass: " + passwordp);

        if ((userp.equals("")) || (userp.equals(" "))) {
            //System.out.println("yes");
            props.setProperty("user", "postgres");
        } else {
            //System.out.println("no");
            props.setProperty("user", userp);
        }

        props.setProperty("password", passwordp); 
        
        try{
            conn = DriverManager.getConnection(url, props);
            st = conn.createStatement(); //SQL statement to run
        } 
        catch (SQLException c){
            System.out.println(c.toString());
        }
        
         
    }

    public void actionPerformed(ActionEvent e)  {
        Object source = e.getSource();

        //need to add db connection
        connectionDB();

        if (source == getPrice) {
            StringBuilder sb = new StringBuilder();
            sb.append("SELECT train_id FROM Route_Schedules WHERE routeid = '" + route.getText() + "' \nAND ");
            sb.append("day = '" + day.getText() + "' \nAND time = '" + time.getText() + "';");

            //System.out.println(sb);
            ResultSet resultSet = null;

            try { 
                try{
                    //st.executeUpdate(sb.toString()); //adds new customer to db
                    resultSet = st.executeQuery(sb.toString());
                } catch (NullPointerException np1){
                    System.out.println(np1);
                }
               
            } catch (SQLException e1) {
                System.out.println(e1);
            }

            String returnedID = "error"; //if id cannot be found
            
            try {
                while(resultSet.next()){
                    returnedID = resultSet.getString("train_id");
                }     
            } catch (SQLException res) {
                System.out.println(res);
            }

            trainNumber.setText(returnedID);

            StringBuilder priceSB = new StringBuilder();
            priceSB.append("SELECT getPriceFunction(" + startA.getText() + ", " + endB.getText() + ", " + returnedID + ");");
            ResultSet resultSet2 = null;

            try { 
                try{
                    //st.executeUpdate(sb.toString()); //adds new customer to db
                    resultSet2 = st.executeQuery(priceSB.toString());
                } catch (NullPointerException np1){
                    System.out.println(np1);
                }
               
            } catch (SQLException e1) {
                System.out.println(e1);
            }

            String returnedID2 = "";

            try {
                while(resultSet2.next()){
                    returnedID2 = resultSet2.getString("getpricefunction");
                }     
            } catch (SQLException res) {
                System.out.println(res);
            }

            price.setText(returnedID2);



        } else if (source == backRButton) {
            AgentScreen agentS = new AgentScreen(guiPush, reservationCenter, passwordp, userp);
        } else if (source == addRButton){
            StringBuilder resInsert = new StringBuilder();

            double remainingBal = Double.valueOf(price.getText()) - Double.valueOf(totalPaid.getText());

            BigDecimal bd = new BigDecimal(remainingBal);
            bd = bd.setScale(2, RoundingMode.CEILING);

            balance.setText(bd.toString());

            resInsert.append("INSERT INTO Reservations (customer_id, price, balance, route_num, day, time, train) VALUES ('");
            resInsert.append(customer_id.getText() + "', '" + price.getText() + "', '" + Double.toString(remainingBal) + "', '");
            resInsert.append(route.getText() + "', '" + day.getText() + "' , '" + time.getText() + "' , '" + trainNumber.getText() + "');");

            //System.out.println(resInsert.toString());

            StringBuilder returnQuery = new StringBuilder();
            returnQuery.append("SELECT reserv_no FROM Reservations WHERE customer_id = '" + customer_id.getText() + "' AND ");
            returnQuery.append("price = '" + price.getText() + "' AND balance = '" + balance.getText() + "' AND ");
            returnQuery.append("route_num = '" + route.getText() + "' AND day = '" + day.getText() + "' AND ");
            returnQuery.append("time = '" + time.getText() + "' AND train = '" + trainNumber.getText() + "';");

            System.out.println(returnQuery);
            ResultSet resultSet = null;
            
            try { 
                try{
                    st.executeUpdate(resInsert.toString()); //adds new customer to db
                    resultSet = st.executeQuery(returnQuery.toString());
                } catch (NullPointerException np1){
                    System.out.println(np1);
                }
               
            } catch (SQLException e1) {
                System.out.println(e1);
            }

            String returnedReserv = "error"; //if id cannot be found
            
            try {
                while(resultSet.next()){
                    returnedReserv = resultSet.getString("reserv_no");
                }     
            } catch (SQLException res) {
                System.out.println(res);
            }

            reservationNumber.setText(returnedReserv);

            if (remainingBal == 0) {
                //create ticket
                StringBuilder ticketGen = new StringBuilder();
                ticketGen.append("INSERT INTO Tickets (reserv_no) VALUES (" + reservationNumber.getText() + ");");

                StringBuilder getTicketNo = new StringBuilder();
                getTicketNo.append("SELECT ticket_no FROM Tickets WHERE reserv_no = '" + reservationNumber.getText() + "';");

                //System.out.println(ticketGen);
                //System.out.println(getTicketNo);

                //get ticket number
                ResultSet returnTicketNo = null;

                try { 
                    try{
                        st.executeUpdate(ticketGen.toString()); //adds new customer to db
                        returnTicketNo = st.executeQuery(getTicketNo.toString());
                    } catch (NullPointerException np1){
                        System.out.println(np1);
                    }
                   
                } catch (SQLException e1) {
                    System.out.println(e1);
                }

                String retTick = "error"; //if id cannot be found
            
                try {
                    while(returnTicketNo.next()){
                        retTick = returnTicketNo.getString("ticket_no");
                    }     
                } catch (SQLException res1) {
                    System.out.println(res1);
                }

                ticket.setText(retTick);


            }
            



        }
        
    }

}
