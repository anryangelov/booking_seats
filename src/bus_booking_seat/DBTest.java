package bus_booking_seat;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

public class DBTest {

	public int dbSize = 50; // seats
	DBAtomic db;

	@Before
	public void init() {
		db = new DBAtomic(dbSize);
	}

	@Rule
	public ExpectedException exception = ExpectedException.none();
	
	@Test
	public void testBookSeat() throws NoAvailableSeats {
		for (int i = 1; i <= dbSize; i++) {
			assertEquals(i, db.bookSeat("name" + i));
		}
		exception.expect(NoAvailableSeats.class);
		db.bookSeat("some name");
	}
	
	@Test
	public void testHasAvailableSeat() throws NoAvailableSeats{
		for (int i = 1; i <= dbSize - 1; i++) {
			db.bookSeat("some name");
			boolean res = db.hasAvailableSeat();
			assertEquals(res, true);
		}
		db.bookSeat("some name");
		assertEquals(false, db.hasAvailableSeat());
	}
	
}
