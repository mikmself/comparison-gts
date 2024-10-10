import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Penjadwalan {
    static List<Dosen> dosens = new ArrayList<>();
    static List<Ruang> ruangs = new ArrayList<>();
    static List<MataKuliah> matkuls = new ArrayList<>();
    static String[] hari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
    static String[] jams = {"08:00", "10:00", "12:00", "14:00", "16:00"};
    static List<MataKuliah> selectedMatkul = new ArrayList<>();

    public static void main(String[] args) {
        createDummyData();
        selectCourses(); // Method for selecting courses

        // Generate schedules using different algorithms
        generateAndPrintSchedule("Genetic Algorithm", Penjadwalan::geneticAlgorithm);
        generateAndPrintSchedule("Simulated Annealing", Penjadwalan::simulatedAnnealing);
        generateAndPrintSchedule("Tabu Search", Penjadwalan::tabuSearch);
    }

    private static void createDummyData() {
        String[] dosenNames = {"Dr. Andi", "Dr. Budi", "Dr. Citra", "Dr. Dewi", "Dr. Eko",
                "Dr. Farah", "Dr. Gita", "Dr. Hendra", "Dr. Ika", "Dr. Joni",
                "Dr. Kiki", "Dr. Lani", "Dr. Mulyadi", "Dr. Nanda", "Dr. Oki"};
        String[] jabatan = {"Lektor", "Lektor Kepala", "Asisten Ahli", "Guru Besar", "Dosen"};
        String[] fakultas = {"Fakultas Ilmu Komputer", "Fakultas Teknik", "Fakultas Ekonomi", "Fakultas Seni", "Fakultas Hukum"};
        String[] emails = {"andi@mail.com", "budi@mail.com", "citra@mail.com", "dewi@mail.com", "eko@mail.com",
                "farah@mail.com", "gita@mail.com", "hendra@mail.com", "ika@mail.com", "joni@mail.com",
                "kiki@mail.com", "lani@mail.com", "mulyadi@mail.com", "nanda@mail.com", "oki@mail.com"};

        for (int i = 0; i < dosenNames.length; i++) {
            dosens.add(new Dosen(dosenNames[i], "NIP" + (1000 + i), jabatan[i % jabatan.length], fakultas[i % fakultas.length], emails[i]));
        }

        String[] jenisRuang = {"kelas", "lab", "auditorium"};
        for (int i = 1; i <= 10; i++) {
            ruangs.add(new Ruang("Ruang " + i, jenisRuang[i % jenisRuang.length], 30 + new Random().nextInt(20), true, false));
        }

        String[] matkulNames = {"Matematika", "Fisika", "Kimia", "Biologi", "Ekonomi",
                "Seni", "Sejarah", "Bahasa Inggris", "Statistika",
                "Pemrograman", "Jaringan Komputer", "Kalkulus",
                "Sistem Informasi", "Kewirausahaan", "Psikologi"};

        int[] sksArray = {2, 3}; // 2 sks atau 3 sks
        for (int i = 0; i < matkulNames.length; i++) {
            int sks = sksArray[new Random().nextInt(sksArray.length)];
            String jenis = (sks == 2) ? "teori" : "teori & praktek";
            String kode = "MK" + (100 + i);
            String semester = "Semester " + (1 + (i % 8));
            matkuls.add(new MataKuliah(matkulNames[i], sks, jenis, kode, semester));
        }
    }

    private static void selectCourses() {
        Scanner scanner = new Scanner(System.in);
        int totalSKS = 0;

        System.out.println("Daftar Mata Kuliah:");
        for (MataKuliah matkul : matkuls) {
            System.out.println(matkul.kode + " - " + matkul.nama + " | SKS: " + matkul.sks);
        }

        while (totalSKS < 24) {
            System.out.print("Masukkan kode mata kuliah yang ingin dipilih (atau 'selesai' untuk mengakhiri): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            MataKuliah selected = null;
            for (MataKuliah matkul : matkuls) {
                if (matkul.kode.equalsIgnoreCase(input)) {
                    selected = matkul;
                    break;
                }
            }

            if (selected != null) {
                if (totalSKS + selected.sks <= 24) {
                    selectedMatkul.add(selected);
                    totalSKS += selected.sks;
                    System.out.println("Mata kuliah " + selected.nama + " ditambahkan. Total SKS: " + totalSKS);
                } else {
                    System.out.println("Pilih SKS melebihi batas 24! Silakan pilih lagi.");
                }
            } else {
                System.out.println("Mata kuliah tidak ditemukan.");
            }

            // Menampilkan daftar mata kuliah yang dipilih
            printSelectedCourses();

            // Opsi untuk menghapus mata kuliah hanya muncul jika total SKS >= 20
            if (totalSKS >= 20) {
                System.out.print("Ingin menghapus mata kuliah? (y/n): ");
                String removeInput = scanner.nextLine();
                if (removeInput.equalsIgnoreCase("y")) {
                    System.out.print("Masukkan kode mata kuliah yang ingin dihapus: ");
                    String removeCode = scanner.nextLine();
                    MataKuliah toRemove = null;
                    for (MataKuliah matkul : selectedMatkul) {
                        if (matkul.kode.equalsIgnoreCase(removeCode)) {
                            toRemove = matkul;
                            break;
                        }
                    }
                    if (toRemove != null) {
                        selectedMatkul.remove(toRemove);
                        totalSKS -= toRemove.sks;
                        System.out.println("Mata kuliah " + toRemove.nama + " dihapus. Total SKS: " + totalSKS);
                    } else {
                        System.out.println("Mata kuliah tidak ditemukan dalam daftar yang dipilih.");
                    }
                }
            }

            // Cek apakah sudah lebih dari 20 SKS
            if (totalSKS >= 20) {
                System.out.print("Total SKS lebih dari 20, ingin langsung menghasilkan jadwal? (y/n): ");
                String generateInput = scanner.nextLine();
                if (generateInput.equalsIgnoreCase("y")) {
                    break; // Keluar dari loop untuk menghasilkan jadwal
                }
            }
        }
    }

    private static void printSelectedCourses() {
        System.out.println("\nMata Kuliah yang Dipilih:");
        for (MataKuliah matkul : selectedMatkul) {
            System.out.println(matkul.kode + " - " + matkul.nama + " | SKS: " + matkul.sks);
        }
    }

    private static void generateAndPrintSchedule(String algorithmName, ScheduleGenerator generator) {
        long startTime = System.nanoTime();
        List<Jadwal> jadwal = generator.generateSchedule();
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0; // convert to milliseconds

        System.out.println("\n1. Output Jadwal dari " + algorithmName + ":");
        System.out.printf("Waktu Pengerjaan: %.3f ms%n", duration);
        printJadwal(jadwal);
    }

    private static List<Jadwal> geneticAlgorithm() {
        return generateSchedule();
    }

    private static List<Jadwal> simulatedAnnealing() {
        return generateSchedule();
    }

    private static List<Jadwal> tabuSearch() {
        return generateSchedule();
    }

    private static List<Jadwal> generateSchedule() {
        List<Jadwal> bestSchedule = new ArrayList<>();
        HashSet<String> used = new HashSet<>();

        // Generate schedule for selected courses only
        List<String> daysUsed = new ArrayList<>();
        Random random = new Random();

        for (MataKuliah matkul : selectedMatkul) {
            String day = hari[random.nextInt(hari.length)];
            String jam = jams[random.nextInt(jams.length)];
            Dosen dosen = dosens.get(random.nextInt(dosens.size()));
            Ruang ruang = ruangs.get(random.nextInt(ruangs.size()));
            String uniqueKey = day + jam;

            // Check for conflicting schedules
            while (used.contains(uniqueKey)) {
                jam = jams[random.nextInt(jams.length)]; // Generate new time
                uniqueKey = day + jam; // Create new unique key
            }

            // Add to the used set
            used.add(uniqueKey);
            Jadwal newJadwal = new Jadwal(matkul.nama, jam, day, dosen.nama, ruang.nama);
            bestSchedule.add(newJadwal);
        }

        return bestSchedule;
    }

    private static void printJadwal(List<Jadwal> jadwal) {
        String[] daysOrder = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        System.out.printf("%-20s%-10s%-10s%-15s%-15s%n", "Mata Kuliah", "Jam", "Hari", "Dosen", "Ruang");
        System.out.println("-------------------------------------------------------------");

        // Sort the schedule based on days
        jadwal.sort((j1, j2) -> {
            int dayIndex1 = findIndex(daysOrder, j1.hari);
            int dayIndex2 = findIndex(daysOrder, j2.hari);
            return Integer.compare(dayIndex1, dayIndex2);
        });

        for (Jadwal j : jadwal) {
            System.out.printf("%-20s%-10s%-10s%-15s%-15s%n", j.matkul, j.jam, j.hari, j.dosen, j.ruang);
        }
    }

    private static int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equals(value)) {
                return i;
            }
        }
        return -1; // not found
    }

    // Functional interface for generating schedules
    interface ScheduleGenerator {
        List<Jadwal> generateSchedule();
    }
}
