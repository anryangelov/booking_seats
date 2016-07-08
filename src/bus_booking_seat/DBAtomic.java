/*
 * without locks instead atomic operations
 */
package bus_booking_seat;

import java.util.concurrent.atomic.AtomicInteger;

public class DBAtomic {

	String [] seats;
	AtomicInteger lastTakenSeat;
	int busSize;
	
	public DBAtomic(int busSize) {
		this.busSize = busSize;
		seats = new String[busSize];
		lastTakenSeat = new AtomicInteger(-1);
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
		return lastTakenSeat.incrementAndGet();
	}
	
	public Boolean hasAvailableSeat() {
		return lastTakenSeat.get() < busSize - 1;
	}
	
}
