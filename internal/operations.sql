-- INDEX
-- A - Program Logistical Operations
-- B - Passenger Service Operations
-- C - Database Administrator Operations
-- END INDEX

-- A - Program Logistical Operations

-- 1) Insert my credential into USERS table
INSERT INTO Users (username, password) VALUES ('postgres', '@LQNlqn19981024');

-- B - Passenger Service Operations

-- 1) Update Customer List

-- 1.1) add_customer()
-- Receives a list of client information, adds him to the Customer table, and
-- returns the customer_id assigned to him if successful (the customer_id will
-- be generated from the JAVA code and passed in; if customer_id already exists
-- JAVA side will generate a new one and makes another attempt until successful)
CREATE OR REPLACE FUNCTION add_customer (customer_id INTEGER, first_name VARCHAR(32),
                                         last_name VARCHAR(32), street VARCHAR(32),
                                         town VARCHAR(32), postalcode VARCHAR(32))
RETURNS INTEGER
AS $$
    BEGIN
        INSERT INTO Customers (customer_id, first_name, last_name,
                               street, town, postalcode)
        VALUES (customer_id, first_name, last_name, street, town, postalcode);
        RETURN customer_id;
    END;
$$ LANGUAGE plpgsql;

-- 1.2) edit_customer()
-- Receives a list of client information and updates the according tuple.
-- Returns TRUE if successful.
CREATE OR REPLACE FUNCTION edit_customer (customer_id INTEGER, first_name VARCHAR(32),
                                          last_name VARCHAR(32), street VARCHAR(32),
                                          town VARCHAR(32), postalcode VARCHAR(32))
RETURNS BOOLEAN
AS $$
    BEGIN
        UPDATE Customers
            SET first_name = first_name,
                last_name = last_name,
                street = street,
                town = town,
                postalcode = postalcode
        WHERE customer_id = Customers.customer_id;
        RETURN TRUE;
    END;
$$ LANGUAGE plpgsql;

-- 1.3) view_customer()
-- Receives a customer_id and returns his information.
CREATE OR REPLACE FUNCTION view_customer (customer_id INTEGER)
RETURNS TABLE (customer_id INTEGER, first_name VARCHAR(32),
               last_name VARCHAR(32), street VARCHAR(32),
               town VARCHAR(32), postalcode VARCHAR(32))
AS $$
    BEGIN
        RETURN QUERY
            SELECT * FROM Customers
            WHERE customer_id = Customers.customer_id;
    END;
$$ LANGUAGE plpgsql;

-- 2) Find Travel between Two Stations (4 ways to sort)
-- 3) Add Reservation

-- create_reservation()
CREATE OR REPLACE FUNCTION create_reservation (reserv_no INTEGER, price MONEY, payments MONEY,
                                               balance MONEY)
RETURNS BOOLEAN
AS $$
    BEGIN
        INSERT INTO Reservations (reserv_no, price, payments, balance)
        VALUES (reserv_no, price, payments, balance);
        RETURN TRUE;
    END;
$$ LANGUAGE plpgsql;

-- 4) Get Ticket

-- create_ticket()
CREATE OR REPLACE FUNCTION create_ticket (ticket_no INTEGER, reserv_no INTEGER)
RETURNS BOOLEAN
AS $$
    BEGIN
        INSERT INTO Tickets (ticket_no, reserv_no)
        VALUES (ticket_no, reserv_no);
        RETURN TRUE;
    END;
$$ LANGUAGE plpgsql;

-- 5) Advanced Searches

-- 6) Other Operations

-- C - Database Administrator Operations
-- 1) Import Database
-- 2) Export Database
-- 3) Delete Database