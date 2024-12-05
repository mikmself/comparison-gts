import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataInitializer {
    static List<Dosen> dosens = new ArrayList<>();
    static List<Ruang> ruangs = new ArrayList<>();
    static List<MataKuliah> matkuls = new ArrayList<>();

    public static void createDummyData() {
        String[] dosenNames = {"Dr. Andi", "Dr. Budi", "Dr. Citra", "Dr. Dewi", "Dr. Eko"};
        String[] jabatan = {"Lektor", "Lektor Kepala", "Asisten Ahli", "Guru Besar", "Dosen"};
        String[] fakultas = {"Fakultas Ilmu Komputer", "Fakultas Teknik", "Fakultas Ekonomi", "Fakultas Seni", "Fakultas Hukum"};
        String[] emails = {"andi@mail.com", "budi@mail.com", "citra@mail.com", "dewi@mail.com", "eko@mail.com"};

        for (int i = 0; i < dosenNames.length; i++) {
            dosens.add(new Dosen(dosenNames[i], "NIP" + (1000 + i), jabatan[i % jabatan.length], fakultas[i % fakultas.length], emails[i]));
        }

        String[] jenisRuang = {"kelas", "lab", "auditorium"};
        for (int i = 1; i <= 10; i++) {
            ruangs.add(new Ruang("Ruang " + i, jenisRuang[i % jenisRuang.length], 30 + new Random().nextInt(20), true, false));
        }

        // Perbanyak jumlah mata kuliah menjadi 20
        String[] matkulNames = {
                "Matematika", "Fisika", "Kimia", "Biologi", "Ekonomi",
                "Sosiologi", "Antropologi", "Sejarah", "Geografi", "Psikologi",
                "Ilmu Komunikasi", "Statistika", "Pemrograman", "Jaringan Komputer", "Kalkulus",
                "Algoritma", "Sistem Informasi", "Kewirausahaan", "Pemasaran", "Teknik Mesin"
        };
        int[] sksArray = {2, 3};

        for (int i = 0; i < matkulNames.length; i++) {
            int sks = sksArray[new Random().nextInt(sksArray.length)];
            String jenis = (sks == 2) ? "teori" : "teori & praktek";
            String kode = "MK" + (100 + i);
            String semester = "Semester " + (1 + (i % 8));
            matkuls.add(new MataKuliah(matkulNames[i], sks, jenis, kode, semester));
        }
    }
}
