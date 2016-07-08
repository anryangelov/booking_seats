package bus_booking_seat;

public class NoAvailableSeats extends Exception {

	public NoAvailableSeats(String message) {
		super(message);
	}
	
}
