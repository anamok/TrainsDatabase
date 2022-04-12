# CS1555_CostaExpress
CS1555 DBMS Group Project

Contributors:<br>
&nbsp;&nbsp;&nbsp;Qinnuo Li <br>
&nbsp;&nbsp;&nbsp;Anastasia Mokhon <br>
&nbsp;&nbsp;&nbsp;Samantha Lavrinc <br>

## Phase 1 - Database Design 
due 3/18/2022

  • Conceptual Design Using ER Diagram (not EER) <br>
  • Specification of the Relational Schema in PostgreSQL (i.e., DDL)
  
### Updates:

3-10 - Sam - *Finished a rough draft of the ER diagram. For textual reference please review the following:* <br>
3-12 - Emma - *Refactored the ER diagram (both diagram and textual representation); added participation and cardinality; created SQL script for table creations.* <br>
3-13 - Sam - *Made minor adjustments to ER diagram and updated README with my thoughts; Will need to save image of diagram to designated folder(as opposed to the code format required for editing in draw.io).* <br>
3-16 - Emma - *Exported draw.io as PDF, pushed a git commit, created a zip file, and sent an email for submission purposes.* <br>
4-12 - Anastasia - *Submitted a rough draft of my part of the advanced searches and ran through the testing process.* <br>

**Entities:** <br>
note: there are a few areas I'm not quite sure how to represent textually, like algorithm screated using data from other entity attributes. That's something we'll need to look into.

- STATION(<ins>station_no</ins>, hours, address)
- DISTANCE(<ins>stationA-stationB</ins>(stationA, stationB), miles) <br>
<br>*Sam: while DISTANCE wasn't explicitly stated to be used as an entity, I thought it might be easier to work with... Unless the goal is to implement an algorithm to find the shortest distance between station nodes using something like Dijkstra's?? Let me know what you think about that possibility after reading through the pdf, but it sounds rough.*<br>
<br>*Emma: I like the idea of making DISTANCE an entiy for this application. I think it simplifies the implementation of STATION as it doesn't have to have a million rows recording the distance between one station and all others. And I think in our case it's perfectly okay to store duplicate distances (stationA, stationB) and (stationB, stationA), as we don't need to do much manipulation for distances, we can just read in whatever value we need from this table.*<br>
<br>*Emma: I changed the primary key for DISTANCE from `station_AB` to `stationA-stationB` to be more representative of how the key is going to be formatted.*<br>
<br> 

- RAILLINE(<ins>rail_ID</ins>, {station_dis(stationA, stationB, distance)}, speed_limit)<br>
<br>*Emma: I changed `speed` to `speed_limit`. No other major changes.*<br>

- ROUTE(<ins>route_ID</ins>, {station_open(station, open)}, depart_station, dest_station)<br>
<br>*Emma: I added `depart_station` and `dest_station` as two attributes.*<br>

- SCHEDULE(<ins>RDT</ins>(railline, day, time), route. train_no)<br>
<br>*Emma: I added `railline` in the primary key and moved `route` out to be an attribute of its own after our discussion in the afternoon.*<br>
<br>*Sam: After considering your thoughts from our discussion last night, I agree that train_no should be part of the schedule. This is partially because of some relationship changes I made, but also it will be easier to obtain the train number if we don't have to backrack through multiple relationships when it comes time to code functionality into the DB*<br>

- TRAIN(<ins>train_ID</ins>, seats, price_mi, top_speed)
- RESERVATION(<ins>reserv_no</ins>, price, {payments}, balance)<br>
<br>*Sam: RESERVATION wasn't explicitly stated to be used as an entity, but I think it makes sense based on the future operations required in the project. (See DESCRIPTION OF REQ. OPERATIONS in the project pdf)* <br>
<br>*Emma: Smart move!*<br>

- PASSENGER(<ins>customer_ID</ins>, first_name, last_name, email, {phone_no}, address)
- CLOCK(<ins>p_date</ins>)
- TICKET(<ins>ticket_no</ins>) <br>
<br>*Emma: I added another entity TICKET to differentiate RESERVATION and TICKET*

<br>


**Relationships:**

feel free to change any of the verbage.. I'm sure it could be more clear.
I also still need to add cardinality and participation, but I'm just including the following results from some relationship models I mapped.

*old:*
- *has <RAILLINE, STATION, DISTANCE> 
Each RAILLINE has many stations with many distances. 1:N:M. RAILLINE being total participation, since it cannot exist without STATION and DISTANCE; DISTANCE is also total participation, as it cannot exist without STATION. However, STATION is partial participation because it can exist without DISTANCE (to any other station) and RAILLINE.*<br>
<br> *Sam: After reviewing the verbage, the total participation of DISTANCE confused me a bit. Ex. DISTANCE can exist without RAILLINE, but cannot exist without a station, so I separated the two, but with RAILLINE connected to DISTANCE rather than STATION since it uses multiple station_dis to create each railline.*<br>

<br>***new:***
- ***has <DISTANCE, STATION> N:2 <br>
DISTANCE cannot exist without STATION(FP). STATION can exist without DISTANCE(PP)***

- ***consists_of <RAILLINE, DISTANCE> N:M <br>
RAILLINE cannot exist without DISTANCE(FP). DISTANCE can exist without RAILLINE(PP)*** 
<br>

*old:*
- requires <RESERVATION, SCHEDULE, ROUTE, TRAIN> 1:1:1:1
Each RESERVATION requires a specific schedule, route and train. RESERVATION being partial participation while other three being total participation, as required information for RESERVATION. <br>
<br> *Sam: Again after reviewing the verbage, the total participation of SCHEDULE, TRAIN and ROUTE confused me. To clarify, I updated the diagram and separated some of the relationships. Ex: TRAINS exist without a route, schedule or reservation.*<br>

<br>***new:***
- ***requires <SCHEDULE, ROUTE, TRAIN> 1:1:1 <br>
TRAINS exist without SCHEDULES OR ROUTES(PP). ROUTES exist without TRAINS or SCHEDULES(PP). SCHEDULES cannot exist without a ROUTE and a TRAIN(FP).***

- ***references <RESERVATION, SCHEDULE> N:M <br>
SCHEDULES can exist without RESERVATIONS(PP). RESERVATIONS cannot exist without SCHEDULES(FP).*** 
<br>

- books <PASSENGER, RESERVATION> 1:N <br>
Each passenger books a reservation. RESERVATIONS cannot exist without a PASSENGER(FP). PASSENGER(s) can exist without a RESERVATION. <br>
<br>*Emma: Can one passenger book several reservation? I'm thinking one passenger can book N reservation while one reservation only belongs to one passenger.*<br>
<br>*Sam: Yes, I believe that makes the most sense. Passengers should be able to book several reservations but each reservation should only belong to one passenger. I've also updated the Participation with consideration to people who call in, speak to an agent, but don't complete their booking. Thus, a Passenger should be able to exist without having an active Reservation(PP).*

- issues <RESERVATION, TICKET>
1:1. TICKET being total participation, but RESERVATION is partial participation.

- traverses <ROUTE, RAILLINE> N:M
Each RAILLINE can belong to multiple ROUTES and each ROUTE can traverse multiple RAILLINES.RAILLINE being partial particiation, as it can exist without being part of a ROUTE; Route being total participation, as it cannot exist without RAILLINE.
<br>

---
Current Relationships without the comments for clarity:

- has <DISTANCE, STATION> N:2 <br>
DISTANCE cannot exist without STATION(FP). STATION can exist without DISTANCE(PP)

- consists_of <RAILLINE, DISTANCE> N:M <br>
RAILLINE cannot exist without DISTANCE(FP). DISTANCE can exist without RAILLINE(PP)

- requires <SCHEDULE, ROUTE, TRAIN> 1:1:1 <br>
TRAINS exist without SCHEDULES OR ROUTES(PP). ROUTES exist without TRAINS or SCHEDULES(PP). SCHEDULES cannot exist without a ROUTE and a TRAIN(FP).

- references <RESERVATION, SCHEDULE> N:M <br>
SCHEDULES can exist without RESERVATIONS(PP). RESERVATIONS cannot exist without SCHEDULES(FP).

- books <PASSENGER, RESERVATION> 1:N <br>
Each passenger books a reservation. RESERVATIONS cannot exist without a PASSENGER(FP). PASSENGER(s) can exist without a RESERVATION.

- issues <RESERVATION, TICKET>
1:1. TICKET being total participation, but RESERVATION is partial participation.

- traverses <ROUTE, RAILLINE> N:M
Each RAILLINE can belong to multiple ROUTES and each ROUTE can traverse multiple RAILLINES.RAILLINE being partial particiation, as it can exist without being part of a ROUTE; Route being total participation, as it cannot exist without RAILLINE.
<br>
  
## Phase 2 - Database Implementation 
due 4/12/2022

  • Data generation in DML <br>
  • Implementation of Operations (Queries, Transactions, and Reports) in DML
  
### Updates:

3-27 - Emma - *Created schema-v2.txt and sql-v2.sql to get started with our database design improvement; modified the SQL script from Phase 1 based on the data and format in the project sample data. More details please see schema-v2.txt.* <br>
3-29 - Sam - *Reviewed changes and created an updated diagram, added insertdata.java for DML generation* <br>
3-30 - Sam - *small updates* <br>
3-31 - Emma - *1) Attributes consistency: changed some naming in the SQL file and a tiny bit of code in JAVA file to match the attribute names in the tables and in the insert statement; 2) Converted the printting of insert statements to actually executing them in the JAVA file; 3) Verified that the insert statements were executed successfully and data were being populated into the tables; 4) Verified the data in the tables match with the allData.txt, that they are complete and correct for each table.* <br>

3-29 Notes/Thoughts:
After reviewing the updated sql text, I want to say you did a great job incorporating all of the new information with splitting the many-to-many relationships with consideration to the data provided! It's almost exactly what I had in mind. I do have a few questions though that I'd like to discuss.. I didn't change the sql file yet but I can if you agree to the ideas. <br>
<br>
~~- I don't know if it's nitpicky and I'm trying to work out what it would look like, but for Line_Include, would it be better to use the stationAtoB key from distance to populate? Or should we have individual stations? I have station_AB on the diagram, but I will update if it will hinder us or cause redundant information.~~ Nevermind. Writing the data generation program helped me realize using both station_A and station_B in Line_Inlcude was the better option.<br>
~~- Under Route, I agree that we do not need the depart/destination station. However, I do think that we should include the starting station of the RAILLINE which is referenced in the data as a stop with 0 distance. I honestly do not know if we'll need this information long term, but until we begin working on the operations and learn which information we will need, it might be a good idea just to have.~~ Honestly, I didn't code this either. I have a variable with this information in the java generation file, but never created an insert statement for it, if we want, we can add it.. otherwise there doesn't seem to be a good reason.<br>
- While its not a big deal, I wonder if ticket_no would be easier to include in RESERVATION. I'm thinking when the balance hits 0, we can easily generate a unique ticket number without having to reference another table. It seems less complicated than referencing the ticket table where reservationnumber = reservationnumber.
- For everything else that seemed like superfluous information that wasn't included in the data sets, I agree that we can probably remove them.

3-31 Notes/Thoughts:
For adding a starting station in Railroad_Lines to account for the 0 distance: At first sight I do agree with you! That it seemed intuitive to preserve the input data as much as possible in our table. However, after running the execution of the insert statements and inspecting how the data are organized in Distance, which LineInclude uses to keep track of the distance between station_a and station_b, I realized it's not necessary to add a 0 distance for us to capture the information completely. I recommend taking a loot at LineInclude and Distance after populating the table, and you will know what I mean! ;-P

I like the idea of linking the ticket number to a reservation! I think we will get a better feel of how this is going to help us when we implement the queries, transactions, etc.. If you spot the need for it please go ahead! So far at the data population stage, since there's no data associated with reservations and tickets, it's a bit difficult to say for sure. We will cross the bridge when we get there!

I also removed some comments (the not important ones) in the SQL file that everyone has prob read, so the SQL file looks a bit cleaner.

Some SQL commands is added at the end of the SQL file to make it easy for you to verify data population and actually get to see the data inserted into the tables.

After my modification, the JAVA file still prints out the INSERT statements it generates as before, but these INSERT statements are also being executed. Feel free to compare the console and DataGrip to confirm that the table population is working as expected!

Right now, what we have left is: <br>
1. ~~format the DML generation program to include 'these things' ('') whatever those things are called that determine the string type in VALUES. Since the generation program is a mess, I can work on this.~~ (done) <br>
2. verify the sql file properties match the DML generation. I attempted to format the attribute names in the data generation program to match the sql file, but it could be better. I was learning as I worked through the file so I discovered some methods later in the code that could have been helpful sooner. (done) <br>
3. run the updated sql file in datagrip (done) <br>
4. connect the generated strings to the call to st.executeUpdate(string); (done) <br>
5. run the program and test for bugs in the data population (done) <br>
6. Implement queries/transactions/reports <br>
