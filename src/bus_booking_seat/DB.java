/*
 * with synchronization;
 */

package bus_booking_seat;

public class DB {

	String [] seats;
	int lastTakenSeat;
	int busSize;
	
	public DB(int busSize) {
		this.busSize = busSize;
		seats = new String[busSize];
		lastTakenSeat = -1;
	}
	
	public int bookSeat(String name) throws NoAvailableSeats{
		int index = getIndex();
		if (index >= busSize) {
			throw new NoAvailableSeats("All seats are taken");
		}
		seats[index] = name;
		return index + 1;
	}
	
	private synchronized int getIndex() {
		++lastTakenSeat;
		return lastTakenSeat;
	}
	
	public Boolean hasAvailableSeat() {
		return lastTakenSeat < busSize - 1;
	}
	
	public static void main(String[] args) {
		
		DB db = new DB(3);
		try {
			System.out.println(db.bookSeat("Ivan"));
			System.out.println(db.bookSeat("Miro"));
			System.out.println(db.bookSeat("Miro"));
			System.out.println(db.bookSeat("Petia"));
		} catch (NoAvailableSeats e) {
			System.out.println(e.getMessage());
		}
	}
}
