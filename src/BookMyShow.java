import java.util.*;

class Reservation {
    private String guestName;
    private String roomType;

    public Reservation(String guestName, String roomType) {
        this.guestName = guestName;
        this.roomType = roomType;
    }
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
    }

    class AddOnService {
        private String serviceName;
        private double cost;

        public AddOnService(String serviceName, double cost) {
            this.serviceName = serviceName;
            this.cost = cost;
        }

        public String getServiceName() {
            return serviceName;
        }

        public double getCost() {
            return cost;
        }
    }

    class AddOnServiceManager {
        private Map<String, List<AddOnService>> serviceMap;

        public AddOnServiceManager() {
            serviceMap = new HashMap<>();
        }

        public void addService(String reservationId, AddOnService service) {
            serviceMap.putIfAbsent(reservationId, new ArrayList<>());
            serviceMap.get(reservationId).add(service);
        }

        public double calculateTotalCost(String reservationId) {
            double total = 0;
            List<AddOnService> services = serviceMap.get(reservationId);

            if (services != null) {
                for (AddOnService s : services) {
                    total += s.getCost();
                }
            }

            return total;
        }

        public void displayServices(String reservationId) {
            List<AddOnService> services = serviceMap.get(reservationId);

            if (services == null || services.isEmpty()) {
                System.out.println("No add-on services selected.");
                return;
            }

            System.out.println("Services for Reservation " + reservationId + ":");
            for (AddOnService s : services) {
                System.out.println(s.getServiceName() + " - $" + s.getCost());
            }

            System.out.println("Total Add-On Cost: $" + calculateTotalCost(reservationId));
        }
    }

    public class UseCase7AddOnServiceSelection {
        public static void main(String[] args) {

            System.out.println("Book My Stay Application v7.1");

            Reservation r1 = new Reservation("R-101", "Alice", "Single Room");

            AddOnService wifi = new AddOnService("WiFi", 10);
            AddOnService breakfast = new AddOnService("Breakfast", 20);
            AddOnService spa = new AddOnService("Spa", 50);

            AddOnServiceManager manager = new AddOnServiceManager();

            manager.addService(r1.getReservationId(), wifi);
            manager.addService(r1.getReservationId(), breakfast);
            manager.addService(r1.getReservationId(), spa);

            manager.displayServices(r1.getReservationId());
        }
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