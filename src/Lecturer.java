/**
 * Kelas Lecturer merepresentasikan seorang dosen dengan atribut-atribut dasar seperti nama dan NIP.
 */
class Lecturer {
    // Nama dosen
    String name;

    // Nomor Induk Pegawai (NIP) dosen
    String nip;

    /**
     * Konstruktor untuk membuat objek Lecturer baru.
     *
     * @param name Nama dosen
     * @param nip Nomor Induk Pegawai (NIP) dosen
     */
    public Lecturer(String name, String nip) {
        this.name = name;
        this.nip = nip;
    }
}