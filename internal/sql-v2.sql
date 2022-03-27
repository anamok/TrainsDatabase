DROP TABLE IF EXISTS Station, Distance, Railline, LineInclude, Route, RouteInclude,
    Train, Schedule, Reservation, Customer, Clock, Ticket CASCADE;

CREATE TABLE Station (
    station_id INTEGER, -- nitpicking: changed from station_no to station_id
    name VARCHAR(32),
    opening_time TIME, -- decomposed 'hours' into opening and closing time
    closing_time TIME, -- format: HH:MM in 24 hrs
    stop_delay INTEGER, -- i suppose it's in minutes? need to double check
    street VARCHAR(32), -- decomposed 'address' to street, town, and postal code
    town VARCHAR(32),
    postal_code VARCHAR(32),

    CONSTRAINT Station_PK PRIMARY KEY(station_id)
);

CREATE TABLE Distance (
    station_a INTEGER, -- use its station_id
    station_b INTEGER, -- use its station_id
    miles INTEGER,

    CONSTRAINT Distance_PK PRIMARY KEY(station_a, station_b),
    CONSTRAINT Distance_FK_A FOREIGN KEY(station_a) REFERENCES Station(station_id) -- added fk from distance to station
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT Distance_FK_B FOREIGN KEY (station_b) REFERENCES Station(station_id) -- added fk from distance to station
        ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE Railline (
    rail_id INTEGER,
    speed_limit INTEGER,
    -- removed station_a and station_b because i created a new table
    -- LineInclude to try to capture the many-to-many relationship

    CONSTRAINT Railline_PK PRIMARY KEY(rail_id)
);

-- i created a new table to represent the many-to-many relationship
-- for the two lists in railroad lines: <Station list>, <Distance from previous
-- line station list>; however i'm not sure if this seems logical. please
-- let me know if you have a different idea!
CREATE TABLE LineInclude (
    rail_id INTEGER,
    station_a INTEGER,
    station_b INTEGER,

    CONSTRAINT LineInclude_PK PRIMARY KEY(rail_id, station_a, station_b),
    CONSTRAINT LineInclude_FK_A FOREIGN KEY(station_a) REFERENCES Station(station_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT LineInclude_FK_B FOREIGN KEY(station_b) REFERENCES Station(station_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT LineInclude_FK_C FOREIGN KEY(station_a, station_b) REFERENCES Distance(station_a, station_b)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Route (
    route_id INTEGER,
    depart_station INTEGER, -- do we want to keep them if they are not present in the sample data?
    dest_station INTEGER, -- do we want to keep them if they are not present in the sample data?
    -- removed station and open because i created a new table to capture
    -- the many-to-many relationship

    CONSTRAINT Route_PK PRIMARY KEY(route_id),
    CONSTRAINT Route_FK_DepartStation FOREIGN KEY(depart_station) REFERENCES Station(station_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT Route_FK_DestStation FOREIGN KEY(dest_station) REFERENCES Station(station_id)
        ON UPDATE CASCADE ON DELETE CASCADE
);

-- a new table to represent the two lists in routes: <Station list> and
-- <Stop list>
CREATE TABLE RouteInclude (
    route_id INTEGER,
    station_id INTEGER,
    stop BOOLEAN, -- true if it's a stop, false otherwise

    CONSTRAINT RouteInclude_FK_Route FOREIGN KEY(route_id) REFERENCES Route(route_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT RouteInclude_FK_Station FOREIGN KEY(station_id) REFERENCES Station(station_id)
        ON UPDATE CASCADE ON DELETE CASCADE
)

CREATE TABLE Train ( -- moved train creation above schedule bc schedule now requires a train number
    train_id INTEGER,
    train_name VARCHAR(32),
    description VARCHAR(32),
    seats INTEGER,
    top_speed INTEGER, -- in km/h
    cost_per_km INTEGER, -- changed price_mi to cost_per_km to keep units consistent,

    CONSTRAINT Train_PK PRIMARY KEY(train_id)
);

CREATE TABLE Schedule (
    rail_id INTEGER, -- not present in the sample data, should we still keep it?
    day VARCHAR(32), -- can we use TIME somehow to get the day instead of storing it as plain string...?
    time TIME,
    route_id INTEGER,
    train_id INTEGER, -- added this

    CONSTRAINT Schedule_PK PRIMARY KEY(rail_id, day, time),
    CONSTRAINT Schedule_FK_Route FOREIGN KEY(route_id) REFERENCES Route(route_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT Schedule_FK_Train FOREIGN KEY(train_id) REFERENCES Train(train_id) -- new fk for train number
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Reservation (
    reserv_no INTEGER,
    price MONEY,
    payments MONEY,
    balance MONEY,

    CONSTRAINT Reservation_PK PRIMARY KEY(reserv_no)
);

-- changed the name from 'Passenger' to 'Customer' for consistency
CREATE TABLE Customer (
    customer_id INTEGER,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    email VARCHAR(32), -- not present in the sample data
    phone_no VARCHAR(32), -- not present in the sample data
    -- decomposed 'address' to street, town, and postal_code to match the sample data
    street VARCHAR(32),
    town VARCHAR(32),
    postal_code VARCHAR(32),


    CONSTRAINT Customer_PK PRIMARY KEY(customer_id)
);

CREATE TABLE Clock (
    p_date DATE,

    CONSTRAINT Clock_PK PRIMARY KEY(p_date)
);

CREATE TABLE Ticket (
    ticket_no INTEGER,
    reserv_no INTEGER,

    CONSTRAINT Ticket_PK PRIMARY KEY(ticket_no),
    CONSTRAINT Ticket_FK_Reserv FOREIGN KEY(reserv_no) REFERENCES Reservation(reserv_no)
        ON UPDATE CASCADE ON DELETE CASCADE
);
