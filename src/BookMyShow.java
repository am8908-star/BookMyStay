import java.util.Map;

class RoomSearchService {

    private RoomInventory inventory;

    public RoomSearchService(RoomInventory inventory) {
        this.inventory = inventory;
    }

    /**
     * Displays available rooms and their details
     */
    public void searchAvailableRooms() {

        System.out.println("\nAvailable Rooms:");

        Map<String, Integer> data = inventory.getInventory();

        for (Map.Entry<String, Integer> entry : data.entrySet()) {

            String roomType = entry.getKey();
            int available = entry.getValue();

            // Defensive check: show only rooms with availability > 0
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