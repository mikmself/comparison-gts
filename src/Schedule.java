/**
 * Kelas Schedule merepresentasikan sebuah jadwal dengan atribut-atribut dasar seperti mata pelajaran, waktu, hari, dosen, dan ruangan.
 */
class Schedule {
    // Mata pelajaran
    String subject;

    // Waktu
    String time;

    // Hari
    String day;

    // Dosen
    String lecturer;

    // Ruangan
    String room;

    /**
     * Konstruktor untuk membuat objek Schedule baru.
     *
     * @param subject Mata pelajaran
     * @param time Waktu
     * @param day Hari
     * @param lecturer Dosen
     * @param room Ruangan
     */
    public Schedule(String subject, String time, String day, String lecturer, String room) {
        this.subject = subject;
        this.time = time;
        this.day = day;
        this.lecturer = lecturer;
        this.room = room;
    }

    @Override
    public String toString() {
        return "Schedule{" +
                "subject='" + subject + '\'' +
                ", time='" + time + '\'' +
                ", day='" + day + '\'' +
                ", lecturer='" + lecturer + '\'' +
                ", room='" + room + '\'' +
                '}';
    }
}