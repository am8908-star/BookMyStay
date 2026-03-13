
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
        System.out.println("Beds : " + beds);
        System.out.println("Size : " + size + " sq.ft");
        System.out.println("Price : $" + price);
    }

    public abstract String getRoomType();
}