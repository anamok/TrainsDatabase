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
3-12 - Emma - *Refactored the ER diagram (both diagram and textual representation); added participation and cardinality; created SQL script for table creations.*

**Entities:** <br>
note: there are a few areas I'm not quite sure how to represent textually, like algorithm screated using data from other entity attributes. That's something we'll need to look into.

- STATION(<ins>station_no</ins>, hours, address)
- DISTANCE(<ins>stationA-stationB</ins>(stationA, stationB), miles) <br>
*Sam: while DISTANCE wasn't explicitly stated to be used as an entity, I thought it might be easier to work with... Unless the goal is to implement an algorithm to find the shortest distance between station nodes using something like Dijkstra's?? Let me know what you think about that possibility after reading through the pdf, but it sounds rough.*<br>
*Emma: I like the idea of making DISTANCE an entiy for this application. I think it simplifies the implementation of STATION as it doesn't have to have a million rows recording the distance between one station and all others. And I think in our case it's perfectly okay to store duplicate distances (stationA, stationB) and (stationB, stationA), as we don't need to do much manipulation for distances, we can just read in whatever value we need from this table.*<br>
*Emma: I changed the primary key for DISTANCE from `station_AB` to `stationA-stationB` to be more representative of how the key is going to be formatted.*<br>
- RAILLINE(<ins>rail_ID</ins>, {station_dis(stationA, stationB, distance)}, speed_limit)<br>
*Emma: I changed `speed` to `speed_limit`. No other major changes.*<br>
- ROUTE(<ins>route_ID</ins>, {station_open(station, open)}, depart_station, dest_station)<br>
*Emma: I added `depart_station` and `dest_station` as two attributes.*<br>
- SCHEDULE(<ins>RDT</ins>(route, day, time))<br>
*Emma: I added `railline` in the primary key and moved `route` out to be an attribute of its own after our discussion in the afternoon.*<br>
- TRAIN(<ins>train_ID</ins>, seats, price_mi, top_speed)
- RESERVATION(<ins>reserv_no</ins>, price, {payments}, balance)<br>
*Sam: RESERVATION wasn't explicitly stated to be used as an entity, but I think it makes sense based on the future operations required in the project. (See DESCRIPTION OF REQ. OPERATIONS in the project pdf)* <br>
*Emma: Smart move!*<br>
- PASSENGER(<ins>customer_ID</ins>, first_name, last_name, email, {phone_no}, address)
- CLOCK(<ins>p_date</ins>)
- TICKET(<ins>ticket_no</ins>) <br>
*Emma: I added another entity TICKET to differentiate RESERVATION and TICKET*


**Relationships:**

feel free to change any of the verbage.. I'm sure it could be more clear.
I also still need to add cardinality and participation, but I'm just including the following results from some relationship models I mapped.

- has <RAILLINE, STATION, DISTANCE> 
Each RAILLINE has many stations with many distances. 1:N:N. RAILLINE being total participation, since it cannot exist without STATION and DISTANCE; DISTANCE is also total participation, as it cannot exist without STATION. However, STATION is partial participation because it can exist without DISTANCE (to any other station) and RAILLINE.
- traverses <RAILLINE, ROUTE> 
Each RAILLINE can belong to multiple ROUTES and each ROUTE can traverse multiple RAILLINES. N:N. RAILLINE being partial particiation, as it can exist without being part of a ROUTE; Route being total participation, as it cannot exist without RAILLINE.
- requires <RESERVATION, SCHEDULE, ROUTE, TRAIN> 
Each RESERVATION requires a specific schedule, route and train. 1:1:1:1. RESERVATION being partial participation while other three being total participation, as required information for RESERVATION.
- books <PASSENGER, RESERVATION>
Each passenger books a reservation. PASSENGER and RESERVATION both being total participation. <br>
*Emma: Can one passenger book several reservation? I'm thinking one passenger can book N reservation while one reservation only belongs to one passenger.*<br>
- issues <RESERVATION, TICKET>
1:1. TICKET being total participation, but RESERVATION is partial participation.
  
