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
    private static List<Jadwal> geneticAlgorithm() {
        List<List<Jadwal>> population = generateInitialPopulation();
        int generations = 100;
        for (int i = 0; i < generations; i++) {
            population = evolvePopulation(population); // List<List<Jadwal>>
        }
        return selectBestSchedule(population); // Returns List<Jadwal>
    }
    private static List<List<Jadwal>> generateInitialPopulation() {
        List<List<Jadwal>> initialPopulation = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            initialPopulation.add(generateSchedule()); // Generate a schedule
        }
        return initialPopulation;
    }
    private static List<List<Jadwal>> evolvePopulation(List<List<Jadwal>> population) {
        List<List<Jadwal>> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i += 2) {
            List<Jadwal> parent1 = population.get(i);
            List<Jadwal> parent2;

            if (i + 1 < population.size()) {
                parent2 = population.get(i + 1); // Normal pairing
            } else {
                parent2 = parent1; // If no pair, use parent1 as parent2
            }

            List<Jadwal> offspring = crossover(parent1, parent2);  // Perform crossover
            mutate(offspring); // Perform mutation
            newPopulation.add(offspring);
        }
        return newPopulation;
    }

    private static List<Jadwal> crossover(List<Jadwal> parent1, List<Jadwal> parent2) {
        // Combine parts of parent1 and parent2 to create offspring
        // This is a simplistic crossover operation
        List<Jadwal> offspring = new ArrayList<>(parent1.subList(0, parent1.size() / 2));
        offspring.addAll(parent2.subList(parent2.size() / 2, parent2.size()));
        return offspring;
    }

    private static void mutate(List<Jadwal> schedule) {
        if (schedule.isEmpty()) return; // Pastikan daftar tidak kosong
        Random random = new Random();
        int index = random.nextInt(schedule.size());
        schedule.get(index).jam = jams[random.nextInt(jams.length)];
    }

    private static List<Jadwal> selectBestSchedule(List<List<Jadwal>> population) {
        // For simplicity, select the first schedule as the best
        return population.get(0);  // Return the best schedule, which is a List<Jadwal>
    }
    private static List<Jadwal> simulatedAnnealing() {
        List<Jadwal> currentSchedule = generateSchedule();
        List<Jadwal> bestSchedule = new ArrayList<>(currentSchedule);
        double temperature = 1000;
        double coolingRate = 0.003;

        while (temperature > 1) {
            List<Jadwal> newSchedule = new ArrayList<>(currentSchedule);
            Random random = new Random();
            int index = random.nextInt(newSchedule.size());
            newSchedule.get(index).jam = jams[random.nextInt(jams.length)];

            if (acceptanceProbability(currentSchedule, newSchedule, temperature) > Math.random()) {
                currentSchedule = newSchedule;
            }

            if (calculateFitness(newSchedule) > calculateFitness(bestSchedule)) {
                bestSchedule = newSchedule;
            }

            temperature *= 1 - coolingRate;
        }

        return bestSchedule;
    }

    private static double acceptanceProbability(List<Jadwal> current, List<Jadwal> newSchedule, double temperature) {
        int currentFitness = calculateFitness(current);
        int newFitness = calculateFitness(newSchedule);
        if (newFitness > currentFitness) {
            return 1.0;
        }
        return Math.exp((newFitness - currentFitness) / temperature);
    }

    private static int calculateFitness(List<Jadwal> schedule) {
        // Simplified fitness function: higher is better
        return schedule.size(); // Here we just return size for simplicity
    }
    private static List<Jadwal> tabuSearch() {
        List<Jadwal> currentSchedule = generateSchedule();
        List<Jadwal> bestSchedule = new ArrayList<>(currentSchedule);
        List<List<Jadwal>> tabuList = new ArrayList<>();
        int maxTabuSize = 10;
        int maxIterations = 100;

        for (int i = 0; i < maxIterations; i++) {
            List<Jadwal> neighbor = generateNeighbor(currentSchedule);

            if (!tabuList.contains(neighbor) && calculateFitness(neighbor) > calculateFitness(bestSchedule)) {
                currentSchedule = neighbor;
                if (calculateFitness(currentSchedule) > calculateFitness(bestSchedule)) {
                    bestSchedule = currentSchedule;
                }
            }

            tabuList.add(new ArrayList<>(currentSchedule));
            if (tabuList.size() > maxTabuSize) {
                tabuList.remove(0); // Keep tabu list size limited
            }
        }

        return bestSchedule;
    }

    private static List<Jadwal> generateNeighbor(List<Jadwal> schedule) {
        // Generate a neighboring solution by making a slight change
        Random random = new Random();
        List<Jadwal> neighbor = new ArrayList<>(schedule);
        int index = random.nextInt(neighbor.size());
        neighbor.get(index).jam = jams[random.nextInt(jams.length)];
        return neighbor;
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
