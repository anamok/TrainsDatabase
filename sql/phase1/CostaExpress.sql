DROP TABLE IF EXISTS Station, Distance, Railline, Route, Schedule,
    Train, Reservation, Passenger, Clock, Ticket CASCADE;

CREATE TABLE Station (
    station_no INTEGER,
    hours VARCHAR(32),
    address VARCHAR(32),

    CONSTRAINT Station_PK PRIMARY KEY(station_no)
);

CREATE TABLE Distance (
    station_a INTEGER, -- use its station_no
    station_b INTEGER, -- use its station_no
    miles INTEGER,

    CONSTRAINT Distance_PK PRIMARY KEY(station_a, station_b),
    CONSTRAINT Distance_FK_A FOREIGN KEY(station_a) REFERENCES Station(station_no) -- added fk from distance to station
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT Distance_FK_B FOREIGN KEY (station_b) REFERENCES Station(station_no) -- added fk from distance to station
        ON UPDATE CASCADE ON DELETE CASCADE

);

CREATE TABLE Railline (
    rail_id INTEGER,
    station_a INTEGER,
    station_b INTEGER,
    speed_limit INTEGER,

    CONSTRAINT Railline_PK PRIMARY KEY(rail_id),
    CONSTRAINT Railline_FK_Distance FOREIGN KEY(station_a, station_b) REFERENCES Distance(station_a, station_b)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Route (
    route_id INTEGER,
    depart_station INTEGER,
    dest_station INTEGER,
    station INTEGER,
    open BOOL,

    CONSTRAINT Route_PK PRIMARY KEY(route_id),
    CONSTRAINT Route_FK_DepartStation FOREIGN KEY(depart_station) REFERENCES Station(station_no)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT Route_FK_DestStation FOREIGN KEY(dest_station) REFERENCES Station(station_no)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT Route_FK_Station FOREIGN KEY(station) REFERENCES Station(station_no)
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Train ( -- moved train creation above schedule bc schedule now requires a train number
    train_id INTEGER, 
    seats INTEGER,
    price_mi MONEY,
    top_speed INTEGER,

    CONSTRAINT Train_PK PRIMARY KEY(train_id)
);

CREATE TABLE Schedule (
    rail_id INTEGER,
    day VARCHAR(32),
    time VARCHAR(32),
    route_id INTEGER,
    train_no INTEGER, -- added this

    CONSTRAINT Schedule_PK PRIMARY KEY(rail_id, day, time),
    CONSTRAINT Schedule_FK_Route FOREIGN KEY(route_id) REFERENCES Route(route_id)
        ON UPDATE CASCADE ON DELETE CASCADE,
    CONSTRAINT Schedule_FK_Train FOREIGN KEY(train_no) REFERENCES Train(train_id) -- new fk for train number
        ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Reservation (
    reserv_no INTEGER,
    price MONEY,
    payments MONEY,
    balance MONEY,

    CONSTRAINT Reservation_PK PRIMARY KEY(reserv_no)
);

CREATE TABLE Passenger (
    customer_id INTEGER,
    first_name VARCHAR(32),
    last_name VARCHAR(32),
    email VARCHAR(32),
    phone_no VARCHAR(32),
    address VARCHAR(32),

    CONSTRAINT Passenger_PK PRIMARY KEY(customer_id)
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
