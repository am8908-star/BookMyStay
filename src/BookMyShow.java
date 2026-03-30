import java.io.*;
import java.util.*;

class Reservation implements Serializable {
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

    public String getRoomType() {
        return roomType;
    }

    public void display() {
        System.out.println(reservationId + " | " + guestName + " | " + roomType);
    }
}

class RoomInventory implements Serializable {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
    }

    public void setInventory(Map<String, Integer> data) {
        inventory = data;
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public void display() {
        System.out.println("\nInventory:");
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " : " + e.getValue());
        }
    }
}

class BookingHistory implements Serializable {
    private List<Reservation> history;

    public BookingHistory() {
        history = new ArrayList<>();
    }

    public void add(Reservation r) {
        history.add(r);
    }

    public List<Reservation> getAll() {
        return history;
    }

    public void display() {
        System.out.println("\nBooking History:");
        for (Reservation r : history) {
            r.display();
        }
    }
}

class PersistenceService {
    private static final String FILE = "hotel_data.ser";

    public void save(RoomInventory inventory, BookingHistory history) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE))) {
            out.writeObject(inventory);
            out.writeObject(history);
            System.out.println("\nData saved successfully");
        } catch (Exception e) {
            System.out.println("\nError saving data");
        }
    }

    public Object[] load() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE))) {
            RoomInventory inventory = (RoomInventory) in.readObject();
            BookingHistory history = (BookingHistory) in.readObject();
            System.out.println("\nData loaded successfully");
            return new Object[]{inventory, history};
        } catch (Exception e) {
            System.out.println("\nNo previous data found. Starting fresh.");
            return null;
        }
    }
}

public class UseCase12DataPersistenceRecovery {
    public static void main(String[] args) {

        System.out.println("Book My Stay Application v12.1");

        PersistenceService persistence = new PersistenceService();

        RoomInventory inventory;
        BookingHistory history;

        Object[] data = persistence.load();

        if (data != null) {
            inventory = (RoomInventory) data[0];
            history = (BookingHistory) data[1];
        } else {
            inventory = new RoomInventory();
            history = new BookingHistory();

            history.add(new Reservation("R-101", "Alice", "Single Room"));
            history.add(new Reservation("R-102", "Bob", "Double Room"));
        }

        inventory.display();
        history.display();

        persistence.save(inventory, history);
    }
}