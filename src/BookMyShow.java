import java.util.*;

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
}

class BookingRequestQueue {
    private Queue<Reservation> queue;

    public BookingRequestQueue() {
        queue = new LinkedList<>();
    }

    public void addRequest(Reservation r) {
        queue.offer(r);
    }

    public Reservation getNextRequest() {
        return queue.poll();
    }

    public boolean hasRequests() {
        return !queue.isEmpty();
    }
}

class RoomInventory {
    private Map<String, Integer> inventory;

    public RoomInventory() {
        inventory = new HashMap<>();
        inventory.put("Single Room", 2);
        inventory.put("Double Room", 2);
        inventory.put("Suite Room", 1);
    }

    public int getAvailability(String roomType) {
        return inventory.getOrDefault(roomType, 0);
    }

    public void decrement(String roomType) {
        inventory.put(roomType, inventory.get(roomType) - 1);
    }

    public void displayInventory() {
        for (Map.Entry<String, Integer> e : inventory.entrySet()) {
            System.out.println(e.getKey() + " available: " + e.getValue());
        }
    }
}

class RoomAllocationService {
    private RoomInventory inventory;
    private Map<String, Set<String>> allocatedRooms;
    private int roomCounter;

    public RoomAllocationService(RoomInventory inventory) {
        this.inventory = inventory;
        allocatedRooms = new HashMap<>();
        roomCounter = 1;
    }

    public void processReservation(Reservation r) {
        String roomType = r.getRoomType();

        if (inventory.getAvailability(roomType) <= 0) {
            System.out.println("Reservation failed for " + r.getGuestName() + " (" + roomType + " unavailable)");
            return;
        }

        String roomId = generateRoomId(roomType);

        allocatedRooms.putIfAbsent(roomType, new HashSet<>());
        Set<String> set = allocatedRooms.get(roomType);

        if (set.contains(roomId)) {
            System.out.println("Duplicate room allocation prevented");
            return;
        }

        set.add(roomId);
        inventory.decrement(roomType);

        System.out.println("Reservation confirmed: " + r.getGuestName() + " -> " + roomType + " | Room ID: " + roomId);
    }

    private String generateRoomId(String roomType) {
        String prefix = roomType.split(" ")[0].toUpperCase();
        return prefix + "-" + (roomCounter++);
    }

    public void displayAllocatedRooms() {
        for (Map.Entry<String, Set<String>> e : allocatedRooms.entrySet()) {
            System.out.println(e.getKey() + " allocated rooms: " + e.getValue());
        }
    }
}

public class BookMyShow {
    public static void main(String[] args) {

        System.out.println("Book My Stay Application v6.1");

        RoomInventory inventory = new RoomInventory();
        BookingRequestQueue queue = new BookingRequestQueue();
        RoomAllocationService allocationService = new RoomAllocationService(inventory);

        queue.addRequest(new Reservation("Alice", "Single Room"));
        queue.addRequest(new Reservation("Bob", "Double Room"));
        queue.addRequest(new Reservation("Charlie", "Single Room"));
        queue.addRequest(new Reservation("David", "Suite Room"));
        queue.addRequest(new Reservation("Eva", "Suite Room"));

        while (queue.hasRequests()) {
            Reservation r = queue.getNextRequest();
            allocationService.processReservation(r);
        }

        System.out.println();
        allocationService.displayAllocatedRooms();

        System.out.println();
        inventory.displayInventory();
    }
}