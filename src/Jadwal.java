class Jadwal {
    String matkul;
    String jam;
    String hari;
    String dosen;
    String ruang;

    public Jadwal(String matkul, String jam, String hari, String dosen, String ruang) {
        this.matkul = matkul;
        this.jam = jam;
        this.hari = hari;
        this.dosen = dosen;
        this.ruang = ruang;
    }
    @Override
    public String toString() {
        return "Jadwal{" +
                "matkul='" + matkul + '\'' +
                ", jam='" + jam + '\'' +
                ", hari='" + hari + '\'' +
                ", dosen='" + dosen + '\'' +
                ", ruang='" + ruang + '\'' +
                '}';
    }
}