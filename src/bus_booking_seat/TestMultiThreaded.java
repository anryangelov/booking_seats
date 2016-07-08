package bus_booking_seat;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;

public class TestMultiThreaded implements Runnable {

	DB db;
	int from;
	int to;
	ConcurrentHashMap<Integer, Integer> bookedSeats; // we put obtained seat  

	public TestMultiThreaded(DB db, int from, int to,
			ConcurrentHashMap<Integer, Integer> bookedSeats) {
		this.db = db;
		this.from = from;
		this.to = to;
		this.bookedSeats = bookedSeats;
	}

	public void run() {	
		try {
			for ( int i = from; i < to; i++) {
				int seat = db.bookSeat("name" + i);
				bookedSeats.put(seat, 1);
			}
		} catch (NoAvailableSeats e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws InterruptedException {
		int seats = 1_000_000;
		int step = 100;
		DB db = new DB(seats);
		ConcurrentHashMap<Integer, Integer> obtainedSeats =  new ConcurrentHashMap<Integer, Integer>();
		
		ArrayList<Thread> threads = new ArrayList<>();

		for (int i = 1; i < seats; i += step) {
			Thread t = new Thread(new TestMultiThreaded(db, i, i + step, obtainedSeats));
			t.start();
			threads.add(t);
		}
		for (Thread t : threads) {
			t.join();
		}

		HashSet<String> uniqNames = new HashSet<>();

		for (String seat : db.seats) { // get all names booked seat from our db
			uniqNames.add(seat);
		}

		System.out.println("seats in Bus: " + seats);
		System.out.println("obtained uniq seats: " + obtainedSeats.size());
		System.out.println("booked seats: " + uniqNames.size());
		

		if (uniqNames.size() == seats && obtainedSeats.size() == seats) {
			// usually first condition is enough
			System.out.println("PASS");
		} else {
			System.out.println("FAIL");
		}
		
		/*Integer max = 0;
		for (Entry<Integer, Integer> entry : bookedSeats.entrySet()) {
			if (max < entry.getKey()) {
				max = entry.getKey();
			}
		}
		System.out.println(max);
		*/
	}
}
