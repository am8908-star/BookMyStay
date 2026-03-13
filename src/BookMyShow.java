import java.util.HashMap;
import java.util.Map;

abstract class Room {

    protected int beds;
    protected int size;
    protected double price;

    public Room(int beds, int size, double price) {
        this.beds = beds;
        this.size = size;
        this.price = price;
    }

    public void displayRoomDetails() {
        System.out.println("Beds: " + beds);
        System.out.println("Size: " + size + " sq.ft");
        System.out.println("Price: $" + price);
    }

    public abstract String getRoomType();
}

class SingleRoom extends Room {

    public SingleRoom() {
        super(1, 200, 100);
    }

    public String getRoomType() {
        return "Single Room";
    }
}


class DoubleRoom extends Room {

    public DoubleRoom() {
        super(2, 350, 180);
    }

    public String getRoomType() {
        return "Double Room";
    }
}

class SuiteRoom extends Room {

    public SuiteRoom() {
        super(3, 500, 300);
    }

    public String getRoomType() {
        return "Suite Room";
    }
}

class RoomInventory {

    private HashMap<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();

        inventory.put("Single Room", 10);
        inventory.put("Double Room", 5);
        inventory.put("Suite Room", 2);
    }

    public Map<String, Integer> getInventory() {
        return inventory;
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }
}


class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    public void searchAvailableRooms() {

        System.out.println("\nAvailable Rooms:");

        for (Map.Entry<String, Integer> entry : inventory.getInventory().entrySet()) {

            String roomType = entry.getKey();
            int available = entry.getValue();

            // Only show rooms with availability > 0
            if (available > 0) {

                Room room = createRoomObject(roomType);

                if (room != null) {
                    System.out.println("\nRoom Type: " + room.getRoomType());
                    room.displayRoomDetails();
                    System.out.println("Available Rooms: " + available);
                }
            }
        }
    }

    private Room createRoomObject(String type) {

        switch (type) {

            case "Single Room":
                return new SingleRoom();

            case "Double Room":
                return new DoubleRoom();

            case "Suite Room":
                return new SuiteRoom();

            default:
                return null;
        }
    }
}

public class BookMyShow {

    public static void main(String[] args) {

        System.out.println("=================================");
        System.out.println("     Book My Stay Application    ");
        System.out.println("       Hotel Booking v4.1        ");
        System.out.println("=================================");

        RoomInventory inventory = new RoomInventory();

        RoomSearchService searchService = new RoomSearchService(inventory);

        searchService.searchAvailableRooms();

        System.out.println("\nSearch completed. Inventory state unchanged.");
    }
}