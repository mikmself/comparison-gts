import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DataInitializer {
    static List<Dosen> dosens = new ArrayList<>();
    static List<Ruang> ruangs = new ArrayList<>();
    static List<MataKuliah> matkuls = new ArrayList<>();
    public static void createDummyData() {
        String[] dosenNames = {
                "Abdul Azis M.Kom.", "Abdul Jahir M.Kom.", "Adam Prayogo Kuncoro M.Kom.", "Ade Nurhopipah S.Si., M.Cs.",
                "Dr. Ade Tuti Turistiati MIRHRM.", "Adinna Islah Perwita , M. I. Kom.", "Adita Miranti M.I.Kom.",
                "Aditya Riza Dharmawan S.H., M.H", "Agung Prasetyo M.Kom.", "Agus Pramono M.T.", "Akto Hariawan S.Kom., M.Si.",
                "Dr. Alex Nanang Agus Sifa S.Fil.I., M.Pd.", "Alfian Muhazir S.Sos., M.A.", "Ali Nur Ikhsan M.Kom.",
                "Ambar Winarni", "Andi Dwi Riyanto M.Kom.", "Andik Wijanarko M.T.", "Anisa Nur Andina S.E., M.Si.",
                "Antonius Ary Setyawan", "Anugerah Bagus Wijaya M.Kom.", "Argiyan Dwi Pritama S.Kom., M.MSI.",
                "Dr. Arief Adhy Kurniawan S.E., M.Si.", "Asep Suryanto S.Kom.", "Aulia Hamdi M.Kom."
        };
        String[] jabatan = {"Lektor", "Lektor Kepala", "Asisten Ahli", "Guru Besar", "Dosen"};
        String[] fakultas = {"Fakultas Ilmu Komputer", "Fakultas Teknik", "Fakultas Ekonomi", "Fakultas Seni", "Fakultas Hukum"};
        String[] emails = {"andi@mail.com", "budi@mail.com", "citra@mail.com", "dewi@mail.com", "eko@mail.com"};
        for (int i = 0; i < dosenNames.length; i++) {
            String email = emails[i % emails.length];
            dosens.add(new Dosen(dosenNames[i], "NIP" + (1000 + i), jabatan[i % jabatan.length], fakultas[i % fakultas.length], email));
        }
        String[] jenisRuang = {"kelas", "lab", "auditorium"};
        for (int i = 1; i <= 10; i++) {
            ruangs.add(new Ruang("Ruang " + i, jenisRuang[i % jenisRuang.length], 30 + new Random().nextInt(20), true, false));
        }
        String[] matkulNames = {
                "Pendidikan Pancasila dan Kewarganegaraan", "Pendidikan Agama Islam", "Sikap Mental Amikom",
                "Kalkulus Dasar", "Sistem Basis Data", "Arsitektur dan Organisasi Komputer", "Technopreneurship",
                "Pendidikan Anti Korupsi", "Bahasa Indonesia", "Pengantar Ilmu komputer", "Pengantar Multimedia",
                "Pengantar Sistem Cerdas", "Pemrograman Berorientasi Objek", "Aljabar Linier dan Matrik",
                "Matematika Diskret", "Algoritma dan Struktur Data", "Bahasa Inggris", "Bahasa Pemrograman Python",
                "Sistem Operasi", "Jaringan Komputer", "Kalkulus Lanjut", "Statistik Probabilitas", "Pemodelan 2 Dimensi",
                "Pembelajaran Mesin", "Logika Digital dan Sistem Digital", "Bahasa Inggris Lanjut", "Mikroprosessor",
                "Teori Graf dan Otomata", "Pemrograman Logik dan Semantik", "Cloud Computing", "Pemrograman Mobile"
        };
        String[] matkulCodes = {
                "NSIFW001", "NSIFW002", "USIFW001", "PSIFW001", "PSIFW003", "PSIFW004", "USIFW002", "USIFW003", "NSIFW008",
                "FSIFW001", "USIFW004", "PSIFW005", "PSIFW006", "FSIFW002", "FSIFW003", "PSIFW008", "USIFW005", "PSIFW009",
                "FSIFW004", "PSIFW010", "PSIFW002", "PSIFW012", "PSIFW013", "PSIFW031", "PSIFW014", "FSIFW005", "PSIFW020",
                "PSIFW021", "PSIFW024", "PSIFW022", "PSIFW027"
        };
        int[] sksArray = {
                3, 3, 1, 2, 4, 3, 2, 2, 3, 3, 3, 2, 3, 3, 3, 3, 2, 4, 3, 3, 2, 3, 3, 3, 2, 3, 3, 3, 2, 4
        };
        for (int i = 0; i < matkulNames.length; i++) {
            if (i >= matkulNames.length || i >= matkulCodes.length || i >= sksArray.length) {
                System.out.println("Array length mismatch at index: " + i);
                break;
            }
            int sks = sksArray[i];
            String jenis = (sks == 2) ? "teori" : "teori & praktek";
            String semester = "Semester " + (1 + (i % 8));
            matkuls.add(new MataKuliah(matkulNames[i], sks, jenis, matkulCodes[i], semester));
        }
    }
}
