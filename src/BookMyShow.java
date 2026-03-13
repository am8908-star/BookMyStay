import java.util.LinkedList;
import java.util.Queue;

class Reservation {

    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void displayReservation() {
        System.out.println("Guest: " + guestName + " | Requested Room: " + roomType);
    }
}


class BookingRequestQueue {

    private Queue<Reservation> requestQueue;

    public BookingRequestQueue() {
        requestQueue = new LinkedList<>();
    }

    public void addRequest(Reservation reservation) {

        requestQueue.offer(reservation);

        System.out.println("Booking request added for: "
                + reservation.getGuestName()
                + " (" + reservation.getRoomType() + ")");
    }

    public void displayQueue() {

        System.out.println("\nCurrent Booking Request Queue:");

        if (requestQueue.isEmpty()) {
            System.out.println("No pending booking requests.");
            return;
        }

        for (Reservation r : requestQueue) {
            r.displayReservation();
        }
    }

    public Reservation peekNextRequest() {
        return requestQueue.peek();
    }
}


public class BookMyShow {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println("       Hotel Booking v5.1        ");
        System.out.println("=================================");

        // Initialize booking queue
        BookingRequestQueue bookingQueue = new BookingRequestQueue();

        // Simulated guest booking requests
        Reservation r1 = new Reservation("Alice", "Single Room");
        Reservation r2 = new Reservation("Bob", "Double Room");
        Reservation r3 = new Reservation("Charlie", "Suite Room");

        // Guests submit booking requests
        bookingQueue.addRequest(r1);
        bookingQueue.addRequest(r2);
        bookingQueue.addRequest(r3);

        // Display queue
        bookingQueue.displayQueue();

        // Show next request (FIFO)
        System.out.println("\nNext request to be processed:");
        Reservation next = bookingQueue.peekNextRequest();

        if (next != null) {
            next.displayReservation();
        }

        System.out.println("\nNote: Inventory not modified at this stage.");
        System.out.println("Requests are waiting for allocation processing.");
    }
}