package bus_booking_seat;

import java.util.Scanner;

public class CLI {

	public static void main(String[] args) {
		
		String prompt_end = "> ";
		Scanner scanner = new Scanner(System.in);
		
		DB db = new DB(5);
		
		while (db.hasAvailableSeat()) {
			System.out.print("Enter name to book ticket " + prompt_end);
			String name = scanner.nextLine();
			try {
				int seat = db.bookSeat(name);
				System.out.println(String.format("Seat %d is booked for %s", seat, name));
			} catch (NoAvailableSeats e) {
				break;
			}
		}
		System.out.println("All place are taken");
	}

}
