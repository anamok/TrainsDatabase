

CREATE OR REPLACE PROCEDURE exportData()
language plpgsql AS $$
begin

COPY Customers TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\Customers.txt' DELIMITER '|' ; --need to change the file path depending on your system
COPY Clock TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\Clock.txt' DELIMITER '|' ;
COPY lineinclude TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\LineInclude.txt' DELIMITER '|' ;
COPY railroad_lines TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\railroad_lines.txt' DELIMITER '|' ;
COPY reservations TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\reservations.txt' DELIMITER '|' ;
COPY route_schedules TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\route_schedules.txt' DELIMITER '|' ;
COPY routeinclude TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\routeinclude.txt' DELIMITER '|' ;
COPY routes TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\routes.txt' DELIMITER '|' ;
COPY stations TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\stations.txt' DELIMITER '|' ;
COPY stop_seatcount TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\stop_seatcount.txt' DELIMITER '|' ;
COPY tickets TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\tickets.txt' DELIMITER '|' ;
COPY trains TO 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\trains.txt' DELIMITER '|' ;

end;
$$;

CALL exportData();

-- to import data:
--COPY tickets FROM 'C:\Users\Sam\Desktop\CS1555_CostaExpress\export\tickets.txt' ( DELIMITER'|');

CREATE OR REPLACE PROCEDURE deleteData()

language plpgsql AS $$
begin

DELETE FROM Customers;
DELETE FROM Clock;
DELETE FROM lineinclude;
DELETE FROM railroad_lines;
DELETE FROM reservations;
DELETE FROM route_schedules;
DELETE FROM routeinclude;
DELETE FROM routes;
DELETE FROM stations;
DELETE FROM stop_seatcount;
DELETE FROM tickets;
DELETE FROM trains;


END;
$$;

-- CALL deleteData();

-- SELECT * FROM Customers;
-- SELECT * FROM Clock;
-- SELECT * FROM lineinclude;
-- SELECT * FROM railroad_lines;
-- SELECT * FROM reservations;
-- SELECT * FROM route_schedules;
-- SELECT * FROM routeinclude;
-- SELECT * FROM routes;
-- SELECT * FROM stations;
-- SELECT * FROM stop_seatcount;
-- SELECT * FROM tickets;
-- SELECT * FROM trains;