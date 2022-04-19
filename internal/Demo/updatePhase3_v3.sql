

-- advanced search a
-- find all TRAINS that pass through a specific station at a specific day/time combination

-- Reformatted from the following to enable access from java program:

-- DROP VIEW search_station_day;
-- CREATE VIEW search_station_day AS
--    SELECT DISTINCT train_id, route_id
--    FROM route_schedules NATURAL JOIN routeinclude
--    WHERE route_schedules.day = 'Tuesday' AND route_schedules.time = '00:01:00'
--        AND route_schedules.routeid = routeinclude.route_id
--        AND routeinclude.station_id = '4';
--  SELECT * FROM search_station_day;

CREATE OR REPLACE FUNCTION advancedSearchA(
    day_entered VARCHAR,
    time_entered TIME,
    st_id_entered INTEGER
) returns SETOF record
language plpgsql AS $$
BEGIN

    RETURN QUERY
    SELECT DISTINCT train_id, route_id
        FROM route_schedules NATURAL JOIN routeinclude
        WHERE route_schedules.day = $1
        AND route_schedules.time = $2
        AND route_schedules.routeid = routeinclude.route_id
        AND routeinclude.station_id = $3;

END;
$$;

 --SELECT * from advancedSearchA('Tuesday'::varchar, '09:36:00'::time, 2)
 --AS f(train_id int, route_id int);


----------------------------------------------------------

-- advanced search b
-- find the routes that travel more than one railline

CREATE OR REPLACE FUNCTION advancedSearchB()
returns SETOF record
language plpgsql AS $$
BEGIN

CREATE OR REPLACE VIEW route_railline AS
    SELECT line_id, station_a AS station
    FROM lineinclude
    UNION
    SELECT line_id, station_b AS station
    FROM lineinclude;

CREATE OR REPLACE VIEW search_routes_over_multiple_lines AS
    SELECT route_id
    FROM (
        SELECT route_id, COUNT(DISTINCT line_id) AS count
        FROM (
            SELECT line_id, route_id
            FROM route_railline LEFT OUTER JOIN routeinclude ON route_railline.station = routeinclude.station_id
            ORDER BY line_id ASC, route_id ASC) B
        GROUP BY route_id
        HAVING COUNT(DISTINCT line_id) > 1
        ORDER BY route_id ASC, count ASC) A;

    RETURN QUERY
    SELECT * FROM search_routes_over_multiple_lines;

END;
$$;


-- SELECT * from advancedSearchB() AS f(route_id int);

-- advanced search c
-- rank the trains that are scheduled for more than one route
-- ranking seems weird, but is correct: there are 290 trains that are scheduled for 6
-- routes, so the remaining trains with 5 scheduled routes are ranked 291st, the rest are scheduled for 1

CREATE OR REPLACE FUNCTION advancedSearchC()
returns SETOF record
language plpgsql AS $$
BEGIN

    CREATE OR REPLACE VIEW search_train_route_rank AS
    SELECT train_id, RANK() OVER (ORDER BY count DESC) AS rank
    FROM (
        SELECT train_id, COUNT(routeid) as count
        FROM route_schedules
        GROUP BY train_id) A
    ORDER BY rank ASC, train_id ASC;

    RETURN QUERY
    SELECT * FROM search_train_route_rank;

END;
$$;

-- SELECT * from advancedSearchC() AS f(train_id int, rank bigint);

-- advanced search d
-- find the routes that pass through the same stations but dont have the same stops
-- takes 3s on my device, need to check logic
CREATE OR REPLACE FUNCTION advancedSearchD()
returns SETOF record
language plpgsql AS $$
BEGIN

    CREATE OR REPLACE VIEW search_same_stations_dif_stops AS
    SELECT DISTINCT a.route_id
    FROM routeinclude a JOIN routeinclude b ON a.route_id = b.route_id
    WHERE a.station_id = b.station_id AND EXISTS (
        SELECT DISTINCT c.route_id
        FROM routeinclude c JOIN routeinclude b ON a.route_id = b.route_id
        WHERE c.stop != b.stop AND a.route_id = c.route_id)
    ORDER BY a.route_id ASC;

    RETURN QUERY
    SELECT * FROM search_same_stations_dif_stops;


END
$$;

-- SELECT * from advancedSearchD() AS f(route_id int);

-- advanced search e
-- find any stations through which all trains pass through
CREATE OR REPLACE FUNCTION advancedSearchE()
returns SETOF record
language plpgsql AS $$
BEGIN

    CREATE OR REPLACE VIEW trains_and_stations AS
    SELECT station_id, train_id
    FROM Route_Schedules, RouteInclude;

    CREATE OR REPLACE VIEW all_trains AS
    SELECT train_id FROM Trains;

    CREATE OR REPLACE VIEW stations_passed_by_all_trains AS
    SELECT * FROM trains_and_stations AS x
    WHERE NOT EXISTS (
        (SELECT y.train_id FROM all_trains AS y)
        EXCEPT
        (SELECT z.train_id FROM trains_and_stations AS z
            WHERE z.station_id = x.station_id)
        );

    RETURN QUERY
    SELECT DISTINCT station_id FROM stations_passed_by_all_trains;

END;
$$;

--SELECT * from advancedSearchE() AS f(station_id int);


-- advanced search f
-- find all trains that do not stop at a specific station

-- (this is kind of weird one... i've tried to more than 10 random stations, and with
-- each station there will always be 350 DISTINCT trains stopped at it, therefore the
-- set difference always returns an empty set)

CREATE OR REPLACE FUNCTION advancedSearchF(
    -- need to add station input integer
)
returns SETOF record
language plpgsql AS $$
BEGIN
    CREATE OR REPLACE VIEW trains_do_stop AS
    SELECT DISTINCT train_id
    FROM Route_Schedules, RouteInclude
    WHERE station_id = 25
      AND stop = TRUE;
    -- all_trains - trains_do_stop

    RETURN QUERY
    (SELECT train_id FROM Trains)
    EXCEPT
    (SELECT DISTINCT train_id FROM trains_do_stop);

END;
$$;

-- SELECT * from advancedSearchF() AS f(train_id int);

-- advanced search g
-- find routes where they stop at least xx% (where xx is a number from 10-90) of the
-- stations from which they pass (eg route passing through 10 stops will stop at 5 will
-- be returned as a result of a 50% search

-- CREATE OR REPLACE FUNCTION advancedSearchG()
-- returns SETOF record
-- language plpgsql AS $$
-- BEGIN


    -- not yet implemented


-- END;
-- $$;

-- advanced search h
-- display the schedule of a route
-- for a specific route, list the days of departure, departure hours and trains that run it

CREATE OR REPLACE FUNCTION advancedSearchH(
    rtID INTEGER
) returns SETOF record
language plpgsql AS
$$
BEGIN

    RETURN QUERY
    SELECT * FROM route_schedules
    WHERE routeid = $1;

END;
$$;

--SELECT * from advancedSearchh(590) AS f(routeid int, day varchar, time time without time zone, train_id int);

-- advanced search i
-- find the availability of a route at every stop on a specific day and time
CREATE OR REPLACE FUNCTION advancedSearchI(
    rtID INTEGER,
    dayrequested VARCHAR,
    timerequested TIME
) returns SETOF record
language plpgsql AS
$$
BEGIN

    RETURN QUERY
    SELECT route_id, station_id, day, time
    FROM Route_Schedules, RouteInclude
    WHERE Route_Schedules.routeid = $1
      AND RouteInclude.route_id = $1
      AND RouteInclude.stop = TRUE
      AND route_schedules.day = $2
      AND route_schedules.time = $3;

END;
$$;

-- SELECT * from advancedSearchI(376, 'Monday'::varchar, '11:22:00'::time)
-- AS f(routeid int, stationid int, day varchar, rettime time);