class Room {
    String name;
    String type;
    int capacity;
    boolean status;
    boolean available;

    public Room(String name, String type, int capacity, boolean status, boolean available) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.status = status;
        this.available = available;
    }
}
