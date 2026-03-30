import java.util.*;

class Reservation {
    private String reservationId;
    private String guestName;
    private String roomType;
    private String roomId;
    private boolean active;

    public Reservation(String reservationId, String guestName, String roomType, String roomId) {
        this.reservationId = reservationId;
        this.guestName = guestName;
        this.roomType = roomType;
        this.roomId = roomId;
        this.active = true;
    }

    public String getReservationId() {
        return reservationId;
    }

    public String getRoomType() {
        return roomType;
    }

    public String getRoomId() {
        return roomId;
    }

    public boolean isActive() {
        return active;
    }

    public void cancel() {
        active = false;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType + " | " + roomId + " | " + (active ? "ACTIVE" : "CANCELLED"));
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 1);
        inventory.put("Double Room", 1);
        inventory.put("Suite Room", 1);
    }

    public void increment(String roomType) {
        inventory.put(roomType, inventory.getOrDefault(roomType, 0) + 1);
    }

    public void display() {
        System.out.println("\nInventory State:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingHistory {
    private Map<String, Reservation> history;

    public BookingHistory() {
        history = new HashMap<>();
    }

    public void addReservation(Reservation r) {
        history.put(r.getReservationId(), r);
    }

    public Reservation getReservation(String id) {
        return history.get(id);
    }

    public void displayAll() {
        System.out.println("\nBooking History:");
        for (Reservation r : history.values()) {
            r.display();
        }
    }
}

class CancellationService {
    private RoomInventory inventory;
    private BookingHistory history;
    private Stack<String> rollbackStack;

    public CancellationService(RoomInventory inventory, BookingHistory history) {
        this.inventory = inventory;
        this.history = history;
        rollbackStack = new Stack<>();
    }

    public void cancelReservation(String reservationId) {

        Reservation r = history.getReservation(reservationId);

        if (r == null) {
            System.out.println("Cancellation failed: Reservation not found");
            return;
        }

        if (!r.isActive()) {
            System.out.println("Cancellation failed: Already cancelled");
            return;
        }

        rollbackStack.push(r.getRoomId());
        inventory.increment(r.getRoomType());
        r.cancel();

        System.out.println("Cancelled: " + reservationId + " | Released Room: " + r.getRoomId());
    }

    public void displayRollbackStack() {
        System.out.println("\nRollback Stack: " + rollbackStack);
    }
}

public class UseCase10BookingCancellation {
    public static void main(String[] args) {

        System.out.println("Book My Stay Application v10.1");

        RoomInventory inventory = new RoomInventory();
        BookingHistory history = new BookingHistory();

        Reservation r1 = new Reservation("R-101", "Alice", "Single Room", "S-1");
        Reservation r2 = new Reservation("R-102", "Bob", "Double Room", "D-1");

        history.addReservation(r1);
        history.addReservation(r2);

        CancellationService service = new CancellationService(inventory, history);

        history.displayAll();
        inventory.display();

        service.cancelReservation("R-101");
        service.cancelReservation("R-101");
        service.cancelReservation("R-999");

        history.displayAll();
        inventory.display();
        service.displayRollbackStack();
    }
}