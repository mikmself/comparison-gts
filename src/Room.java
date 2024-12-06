/**
 * Kelas Room merepresentasikan sebuah ruangan dengan atribut-atribut dasar seperti nama, tipe, kapasitas, status, dan ketersediaan.
 */
class Room {
    // Nama ruangan
    String name;

    // Tipe ruangan (misalnya: kelas, lab)
    String type;

    // Kapasitas ruangan
    int capacity;

    // Status ruangan (misalnya: aktif, tidak aktif)
    boolean status;

    // Ketersediaan ruangan
    boolean available;

    /**
     * Konstruktor untuk membuat objek Room baru.
     *
     * @param name Nama ruangan
     * @param type Tipe ruangan (misalnya: kelas, lab)
     * @param capacity Kapasitas ruangan
     * @param status Status ruangan (misalnya: aktif, tidak aktif)
     * @param available Ketersediaan ruangan
     */
    public Room(String name, String type, int capacity, boolean status, boolean available) {
        this.name = name;
        this.type = type;
        this.capacity = capacity;
        this.status = status;
        this.available = available;
    }
}