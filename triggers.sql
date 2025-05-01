DELIMITER //

CREATE TRIGGER after_aircraft_insert
AFTER INSERT ON Aircrafts
FOR EACH ROW
BEGIN
    DECLARE i INT DEFAULT 1;
    DECLARE seat_no VARCHAR(255);
    
    -- Loop to insert seats based on the number of seats in the aircraft
    WHILE i <= NEW.seats DO
        SET seat_no = CONCAT(NEW.model, '-', NEW.id, '-', i);  -- Generate seat number
        INSERT INTO Seats (seat_no, status, aircraft_id) 
        VALUES (seat_no, 'Available', NEW.id);
        SET i = i + 1;
    END WHILE;
END //

DELIMITER ;
DELIMITER //

CREATE TRIGGER after_booking_update
AFTER UPDATE ON Bookings
FOR EACH ROW
BEGIN
    -- 1. Adjust booked_seats in Flights if booking is cancelled or deadline crossed
    IF OLD.status != 'Cancelled' AND (NEW.status = 'Cancelled' OR NEW.status = 'Deadline crossed') THEN
        UPDATE Flights 
        SET booked_seats = booked_seats - (
            SELECT COUNT(*) FROM Passengers WHERE booking_id = NEW.id
        )
        WHERE id = NEW.flight_id;
    END IF;

    -- 2. Update payment status based on new booking status
    IF NEW.status = 'Cancelled' AND 
       (SELECT status FROM Payments WHERE booking_id = NEW.id) IN ('Successful', 'Confirmed') THEN
        UPDATE Payments SET status = 'Refunded' WHERE booking_id = NEW.id;

    ELSEIF NEW.status = 'Approved' THEN
        UPDATE Payments SET status = 'Confirmed' WHERE booking_id = NEW.id;

    ELSEIF NEW.status = 'Waiting for approval' THEN
        UPDATE Payments SET status = 'Successful' WHERE booking_id = NEW.id;

    ELSEIF (NEW.status = 'Cancelled' OR NEW.status = 'Deadline crossed') AND 
           (SELECT status FROM Payments WHERE booking_id = NEW.id) = 'Pending' THEN
        UPDATE Payments SET status = 'Not required' WHERE booking_id = NEW.id;
    END IF;
END;
//

DELIMITER ;
DELIMITER //

CREATE TRIGGER assign_aircraft
AFTER INSERT ON Flights
FOR EACH ROW
BEGIN
    UPDATE Aircrafts 
    SET status = 'Assigned'
    WHERE id = NEW.aircraft_id;
END;
//

DELIMITER ;
DELIMITER //

CREATE TRIGGER unassign_aircraft_and_reset_seats
AFTER UPDATE ON Flights
FOR EACH ROW
BEGIN
    -- Only act if the flight's status changed to 'Cancelled' or 'Landed'
    IF NEW.status IN ('Cancelled', 'Landed') AND OLD.status != NEW.status THEN

        -- Unassign the aircraft
        UPDATE Aircrafts
        SET status = 'Unassigned'
        WHERE id = NEW.aircraft_id;

        -- Invalidate the tickets
        UPDATE Tickets
        SET status = 'Invalid'
        WHERE flight_id = NEW.id;

        -- Set all seats of the aircraft as 'Available'
        UPDATE Seats
        SET status = 'Available'
        WHERE aircraft_id = NEW.aircraft_id;

    END IF;
END;
//

DELIMITER ;
