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


/* # 4 on pdf
   Thoughts on update reservation:
   The only two things that would really need updated would be to either remove the reservation
   completely or update the balance of the reservation. If anything other than the payment 
   needs to be updated, a new reservation should instead be generated.. Assuming the balance reaches
   0, a new ticket should be generated here. 

   We can make this easier by requiring the reservation id here. In the 'Search Database' part of the 
   program, we can get the reservation ID.. here, we're only updating or deleting. 

   the Update Reservation class is created when the user presses the 'Edit Customer' button on the Agent Screen
*/

//JFrame is the window, Action Listener is listening for the button 'press'
public class UpdateReservation extends JFrame implements ActionListener {

    // the following is just a blank square in the CENTER of the screen.
    JPanel resEditCenter = new JPanel(new GridLayout(0,1));

    JPanel guiPush; // panel created in Costa Express that covers the ENTIRE screen which
                    // is passed through all of the classes. We add the Center panel to this later.
    Statement st;

    JButton getRButton = new JButton("Get Reservation");
    JButton saveRButton = new JButton("Save");
    JButton backRButton = new JButton("Back");
    JButton deleteRButton = new JButton("Delete");

    // text fields that the agent needs to 'enter' into the system,
    // if the agent enters one combination, we can populate the remaining fields
    JTextField inputReservationNumber; //Reserv_no
    JTextField inputPayment;

    //text fields returned by the search
    JTextField returnedCustomerID; // will need to return this as the customer name
    JTextField returnedReservationBAL; 
    JTextField returnedDay;
    JTextField returnedTime;
    JTextField returnedPrice;
    JTextField returnedTicket;
    JTextField returnedFname;
    JTextField returnedLname;

    // demo could be more secure.. it's not great to pass passwords through each
    // class like this
    String passwordp = "";
    String userp = "";

    Connection conn; //db connection

    public UpdateReservation(JPanel gui, JPanel center, String password, String user) {
        // gui = ENTIRE screen
        // center = CENTER screen of the class that called EditCustomer 
        
        passwordp = password;
        userp = user;

        guiPush = gui; //to pass the ENTIRE screen on to the next class

        center.setVisible(false);   // makes the old center screen(agentscreen) invisible before we
                                    // add the new editcenter screen

        resEditCenter.setBackground(Color.BLACK); //sets bg color of the new edit center screen
        resEditCenter.setBorder(new EmptyBorder(0, 20, 130, 20)); //it has a border because formatting is hard
        gui.add(resEditCenter, BorderLayout.CENTER);// add the new center screen to the entire screen, in the center
                            
        // create top 'banner' message
        JLabel headerMessage = new JLabel("Edit Reservation", SwingConstants.CENTER);
        headerMessage.setFont(new Font("Courier New", Font.BOLD, 40));
        headerMessage.setForeground(Color.WHITE);
        headerMessage.setVerticalAlignment(SwingConstants.BOTTOM);
        
        JLabel advise = new JLabel("Please enter the Reservation ID number", SwingConstants.CENTER);
        advise.setFont(new Font("Courier New", Font.BOLD, 20));
        advise.setForeground(Color.WHITE);
        advise.setVerticalAlignment(SwingConstants.CENTER);

        // create panel to attach the banner message
        JPanel topMessage = new JPanel(new GridLayout(0,1));
        topMessage.setBackground(Color.BLACK);

        // create buffer panel
        JPanel squish = new JPanel();
        squish.setBackground(Color.BLACK);

        // add these to a panel.. we're doing this so many times because
        // I don't know how to use most of the layout options :)
        //topMessage.add(squish);
        topMessage.add(headerMessage);
        topMessage.add(advise);

        resEditCenter.add(topMessage); //add newly created 'banner' to the center of the screen

        // make the buttons *pretty*
        buttonStyle(getRButton);
        buttonStyle(saveRButton);
        buttonStyle(backRButton);
        buttonStyle(deleteRButton);
        addActionEvent(); // initalize button listeners or else they wont do anything when you click them

        // un-editable text labels that let the agent know which corresponding form field to fill in
        JLabel resn = new JLabel ("Reservation Number: ", SwingConstants.RIGHT);
        JLabel fn = new JLabel("First Name: ", SwingConstants.RIGHT); //return will display these
        JLabel ln = new JLabel("Last Name: ", SwingConstants.RIGHT); // return will display these
        JLabel prc = new JLabel("Price: ", SwingConstants.RIGHT); 
        JLabel bal = new JLabel("Balance: ", SwingConstants.RIGHT);
        JLabel day = new JLabel("Day: ", SwingConstants.RIGHT);
        JLabel time = new JLabel("Time: ", SwingConstants.RIGHT);
        JLabel tick = new JLabel("Ticket Number: ", SwingConstants.RIGHT);
        JLabel pymt = new JLabel("Payment: ", SwingConstants.RIGHT);

        // make them *pretty*
        textStyle(resn);
        textStyle(ln);
        textStyle(fn);
        textStyle(prc);
        textStyle(bal);
        textStyle(day);
        textStyle(time);
        textStyle(pymt);
        textStyle(tick);

        // initialize text fields
        inputReservationNumber = new JTextField(); 
        inputPayment = new JTextField();
        returnedCustomerID = new JTextField("xx");
        returnedReservationBAL = new JTextField("xx");
        returnedDay = new JTextField("xx");
        returnedTime = new JTextField("xx"); 
        returnedPrice = new JTextField("xx");
        returnedTicket = new JTextField("xx");
        returnedFname = new JTextField("xx");
        returnedLname = new JTextField("xx");

        // make them *pretty*
        formStyle(inputReservationNumber);
        formStyle(returnedCustomerID);
        formStyle(returnedReservationBAL);
        formStyle(returnedDay);
        formStyle(returnedTime);
        formStyle(returnedPrice);
        formStyle(returnedTicket);
        formStyle(inputPayment);
        formStyle(returnedFname);
        formStyle(returnedLname);

        //look at the formPanel.add section to see how these are used
        JPanel squish4 = new JPanel();
        squish4.setBackground(Color.BLACK);
        JPanel squish5 = new JPanel();
        squish5.setBackground(Color.BLACK);

        // make new panel just for form input
        // GridLayout specifies a layout that adds input in the
        // sequence:
        // row1colA, row1colB
        // row2colA, row2colB
        // row3colA, ...etc
        JPanel formPanel = new JPanel(new GridLayout(0,2));
        formPanel.setBackground(Color.BLACK);
        formPanel.setBorder(new EmptyBorder(0, 250, 0, 250));

        // add all of the objects to the form panel
        formPanel.add(resn); // row1colA : "Reservation Number: "
        formPanel.add(inputReservationNumber); //row1colB : [*enter res# here*]
        formPanel.add(pymt); //row2colA : "Payment: "
        formPanel.add(inputPayment); //row2colB : [*enter input payment here*]
        formPanel.add(deleteRButton); //row3colA : delete Reservation button
        formPanel.add(getRButton); //row3colB : get Reservation button
        formPanel.add(squish4); // row4colA: EMPTY
        formPanel.add(squish5); // row4colB: EMPTY
        formPanel.add(fn); //row5colA : "First Name: "
        formPanel.add(returnedFname); //row5colB : [*enter fname here*] ...etc
        formPanel.add(ln);
        formPanel.add(returnedLname);
        formPanel.add(day); 
        formPanel.add(returnedDay); 
        formPanel.add(time);
        formPanel.add(returnedTime);
        formPanel.add(prc);
        formPanel.add(returnedPrice);
        formPanel.add(bal);
        formPanel.add(returnedReservationBAL);
        formPanel.add(tick);
        formPanel.add(returnedTicket);
        formPanel.add(backRButton);
        formPanel.add(saveRButton);


        //add the form panel to the center of the screen
        resEditCenter.add(formPanel);

        // add another squish for centering because these alignment options
        // are beyond me :)
        JPanel squish22 = new JPanel();
        squish22.setBackground(Color.BLACK);

        //resEditCenter.add(squish22);

    }

    private static void textStyle(JLabel b){
        b.setForeground(Color.WHITE);
        //b.setBackground(Color.BLACK);
        b.setFont(new Font("Courier New", Font.BOLD, 20));
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

    // sets the program to listen for clicks on any of these buttons
    public void addActionEvent(){
        getRButton.addActionListener(this);
        saveRButton.addActionListener(this);
        backRButton.addActionListener(this);
        deleteRButton.addActionListener(this);
    }

    // Necessary for connection to db, I didn't write this, it's referenced from the recitation demos
    // try/catch statements are separated for error tracing isolation
    
    public void connectionDB() {
        try {
            Class.forName("org.postgresql.Driver");
        }
        catch (ClassNotFoundException b) {
            System.out.println(b.toString());
        }
            
        String url = "jdbc:postgresql://localhost:5432/";
        Properties props = new Properties();
    
        if ((userp.equals("")) || (userp.equals(" "))) {
            props.setProperty("user", "postgres");
        } else {
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

    /* the following is an incomplete function that takes the button push
       and then does something depending on which option was selected.
       look at AddReservation.java to see how to implement each command

       you can use inputCustomerID.getText(Int/String/whatever) and inputCustomerID.setText(String)

       for reference, these are the JTextFields that need to be set/retrieved:
       inputReservationNumber;
       inputPayment;

       returnedCustomerID; // will need to return this as the customer name
       returnedReservationBAL; 
       returnedDay;
       returnedTime;
       returnedPrice;
       returnedTicket;
       returnedFname;
       returnedLname;

    */
    
    public void actionPerformed(ActionEvent e)  {
        Object source = e.getSource();  

        if (source == getRButton) { // when agent 'clicks' this button, the program will:
            // start db connection

            // PART 1 ****
            // generate a string SELECT statement to get the reservation attributes of 
            // the inputReservation number ie. SELECT * FROM table WHERE res# = res#
            // we need the following:
            // balance, customer id(*!*), day, time, price, and ticketNumber(if one exists)
            // *!* NOTE: customer id will be returned to the program
            // *!* but does NOT need to be displayed in the program. Instead, we can
            // *!* take this string to obtain the customer's name later in PART2

            // before implementing the next part and connecting the generated string to
            // the database, make a call to System.out.println to copy and paste 
            // the output into datagrip. This ensures the formatting is correct 
            // and no SQL errors will be thrown. 

            // create a ResultSet to hold the returned attributes. 
            // execute a query with the try/catch block and 
            // resultSet = st.executeQuery(yourString);

            // loop through the resultSet, assigning the returned values that match
            // attribute names.

            // setText in the return fields for their corresponding attributes

            // PART 2 ****
            // using the attributes obtained from the previous functionality, 
            // create another SELECT statement to retrieve the customer's name using their id number
            
            // check the statement by printing the line and manually inserting into datagrip

            // create a ResultSet to hold the returned attributes. 
            // execute a query with the try/catch block and 
            // resultSet = st.executeQuery(yourString);

            // loop through the resultSet, assigning the returned values that match
            // attribute names.

            // setText in the return fields for their corresponding attributes

            // close connection

            
        } else if (source == saveRButton){    
            // start db connection, because im not sure if we need to close it from the 
            // get button but it cant hurt to restart it, I suppose

            // get the balance field(string) and cast to a double or big decimal
            // subtract the payment from the balance

            // generate an UPDATE statement based on .getText for all attributes 
            // and the newly updated balance where res# = res#

            // if the balance is now 0, generate an INSERT statement to add a new TICKET

            // check the statements using println

            // try/catch block with st.executeUpdate(yourUPDATEString);
            // AND if balance = 0, call st.executeUpdate(yourINSERTString);

            // if balance = 0, generate a SELECT statement to find the ticket number
            // from the ticket table where res# = res#

            //          - test the select statement in datagrip

            //          - create a new ResultSet to store return values

            //          - try/catch block with st.executeQuery(yourSELECTstring);

            //          - loop through the resultSet, assigning the ticketnumber field
            //          - so that 'xx' will be updated with the new ticket number
            
            // setText for new balance field and ticket number field

            // close connection

        } else if (source == deleteRButton) {
            // start db connection

            // generate a statement to delete reservation where res# = res#

            // generate a statement to delete ticket where res# = res#

            // check statements in datagrip

            // try/catch block to execute Update

            // update text fields to appropriate 'deleted' messages, if desired

            // close connection

        } else if (source == backRButton) {
            AgentScreen agentS = new AgentScreen(guiPush, resEditCenter, passwordp, userp);
        }
    }
}
