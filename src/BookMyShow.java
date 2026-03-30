import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;

    public Reservation(String reservationId, String guestName, String roomType) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getGuestName() {
        return guestName;
    }

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

class BookingHistory {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void addReservation(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAllReservations() {
        return history;
    }
}

class BookingReportService {

    public void displayAllBookings(List<Reservation> reservations) {
        System.out.println("\nBooking History:");
        for (Reservation r : reservations) {
            r.display();
        }
    }

    public void generateSummary(List<Reservation> reservations) {
        Map<String, Integer> countMap = new HashMap<>();

        for (Reservation r : reservations) {
            String type = r.getRoomType();
            countMap.put(type, countMap.getOrDefault(type, 0) + 1);
        }

        System.out.println("\nBooking Summary:");
        for (Map.Entry<String, Integer> e : countMap.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

public class UseCase8BookingHistoryReport {
    public static void main(String[] args) {

        System.out.println("Book My Stay Application v8.1");

        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("R-101", "Alice", "Single Room");
        Reservation r2 = new Reservation("R-102", "Bob", "Double Room");
        Reservation r3 = new Reservation("R-103", "Charlie", "Suite Room");
        Reservation r4 = new Reservation("R-104", "David", "Single Room");

        history.addReservation(r1);
        history.addReservation(r2);
        history.addReservation(r3);
        history.addReservation(r4);

        BookingReportService reportService = new BookingReportService();

        reportService.displayAllBookings(history.getAllReservations());
        reportService.generateSummary(history.getAllReservations());
    }
}