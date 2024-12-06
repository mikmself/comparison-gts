/**
 * Kelas Course merepresentasikan sebuah mata kuliah dengan atribut-atribut dasar seperti nama, jumlah kredit, tipe, kode, dan semester.
 */
class Course {
    // Nama mata kuliah
    String name;

    // Jumlah kredit mata kuliah
    int credits;

    // Tipe mata kuliah (misalnya: wajib, pilihan)
    String type;

    // Kode mata kuliah
    String code;

    // Semester di mana mata kuliah ditawarkan
    String semester;

    /**
     * Konstruktor untuk membuat objek Course baru.
     *
     * @param name Nama mata kuliah
     * @param credits Jumlah kredit mata kuliah
     * @param type Tipe mata kuliah (misalnya: wajib, pilihan)
     * @param code Kode mata kuliah
     * @param semester Semester di mana mata kuliah ditawarkan
     */
    public Course(String name, int credits, String type, String code, String semester) {
        this.name = name;
        this.credits = credits;
        this.type = type;
        this.code = code;
        this.semester = semester;
    }
}