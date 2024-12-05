import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class ScheduleGenerator {
    static String[] hari = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
    static String[] jams = {"08:00", "10:00", "12:00", "14:00", "16:00"};

    public static List<Jadwal> generateSchedule() {
        List<Jadwal> bestSchedule = new ArrayList<>();
        HashSet<String> used = new HashSet<>();
        Random random = new Random();

        for (MataKuliah matkul : MataKuliahSelector.selectedMatkul) {
            String day = hari[random.nextInt(hari.length)];
            String jam = jams[random.nextInt(jams.length)];
            Dosen dosen = DataInitializer.dosens.get(random.nextInt(DataInitializer.dosens.size()));
            Ruang ruang = DataInitializer.ruangs.get(random.nextInt(DataInitializer.ruangs.size()));
            String uniqueKey = day + jam;

            while (used.contains(uniqueKey)) {
                jam = jams[random.nextInt(jams.length)];
                uniqueKey = day + jam;
            }

            used.add(uniqueKey);
            bestSchedule.add(new Jadwal(matkul.nama, jam, day, dosen.nama, ruang.nama));
        }

        return bestSchedule;
    }

    // Genetic Algorithm implementation
    public static List<Jadwal> geneticAlgorithm() {
        List<List<Jadwal>> population = generateInitialPopulation();
        int generations = 100;
        for (int i = 0; i < generations; i++) {
            population = evolvePopulation(population);
        }
        return selectBestSchedule(population);
    }

    private static List<List<Jadwal>> generateInitialPopulation() {
        List<List<Jadwal>> initialPopulation = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            initialPopulation.add(generateSchedule());
        }
        return initialPopulation;
    }

    // Evolve the population by applying crossover and mutation
    private static List<List<Jadwal>> evolvePopulation(List<List<Jadwal>> population) {
        List<List<Jadwal>> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i += 2) {
            List<Jadwal> parent1 = population.get(i);
            List<Jadwal> parent2 = (i + 1 < population.size()) ? population.get(i + 1) : parent1;

            List<Jadwal> offspring = crossover(parent1, parent2);
            mutate(offspring);
            newPopulation.add(offspring);
        }
        return newPopulation;
    }

    // Crossover operation: combine two parents into offspring, avoid conflicts
    private static List<Jadwal> crossover(List<Jadwal> parent1, List<Jadwal> parent2) {
        List<Jadwal> offspring = new ArrayList<>(parent1.subList(0, parent1.size() / 2));
        for (Jadwal jadwal : parent2.subList(parent2.size() / 2, parent2.size())) {
            // Make sure there is no conflict in the offspring
            if (!isTimeConflict(offspring, jadwal.hari, jadwal.jam)) {
                offspring.add(jadwal);
            } else {
                // If conflict, try assigning a different time
                String newJam;
                Random random = new Random();
                do {
                    newJam = jams[random.nextInt(jams.length)];
                } while (isTimeConflict(offspring, jadwal.hari, newJam));  // Avoid conflict
                offspring.add(new Jadwal(jadwal.matkul, newJam, jadwal.hari, jadwal.dosen, jadwal.ruang));
            }
        }
        return offspring;
    }

    // Mutation operation: randomly change a schedule's time, but avoid conflicts
    private static void mutate(List<Jadwal> schedule) {
        if (schedule.isEmpty()) return;
        Random random = new Random();
        int index = random.nextInt(schedule.size());
        Jadwal selectedJadwal = schedule.get(index);

        // Find a new time that does not cause a conflict
        String newJam;
        do {
            newJam = jams[random.nextInt(jams.length)];
        } while (isTimeConflict(schedule, selectedJadwal.hari, newJam, index));  // Avoid conflict

        schedule.get(index).jam = newJam;
    }

    // Helper function to check if there is a time conflict for a given day and time
    private static boolean isTimeConflict(List<Jadwal> schedule, String hari, String jam) {
        for (Jadwal jadwal : schedule) {
            if (jadwal.hari.equals(hari) && jadwal.jam.equals(jam)) {
                return true;  // Conflict: same day and time
            }
        }
        return false;  // No conflict
    }


    // Select the best schedule from the population (simplified)
    private static List<Jadwal> selectBestSchedule(List<List<Jadwal>> population) {
        return population.get(0);  // Simplified: return the first one
    }


    // Simulated Annealing implementation
    public static List<Jadwal> simulatedAnnealing() {
        List<Jadwal> currentSchedule = generateSchedule();
        List<Jadwal> bestSchedule = new ArrayList<>(currentSchedule);
        double temperature = 1000;
        double coolingRate = 0.003;
        Random random = new Random();

        while (temperature > 1) {
            List<Jadwal> newSchedule = new ArrayList<>(currentSchedule);
            int index = random.nextInt(newSchedule.size());
            Jadwal selectedJadwal = newSchedule.get(index);

            // Cari jam baru yang tidak tabrakan dengan jadwal lain pada hari yang sama
            String newJam;
            do {
                newJam = jams[random.nextInt(jams.length)];
            } while (isTimeConflict(newSchedule, selectedJadwal.hari, newJam, index)); // Cek konflik

            // Update jam hanya jika tidak ada konflik
            newSchedule.get(index).jam = newJam;

            // Penerimaan solusi baru jika lebih baik atau berdasarkan probabilitas
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

    // Metode untuk memeriksa konflik waktu
    private static boolean isTimeConflict(List<Jadwal> schedule, String hari, String jam, int excludeIndex) {
        for (int i = 0; i < schedule.size(); i++) {
            if (i == excludeIndex) continue; // Jangan periksa jadwal yang sedang diubah
            Jadwal jadwal = schedule.get(i);
            if (jadwal.hari.equals(hari) && jadwal.jam.equals(jam)) {
                return true; // Ada konflik jika hari dan jam sama
            }
        }
        return false; // Tidak ada konflik
    }

    // Tabu Search implementation
    public static List<Jadwal> tabuSearch() {
        List<Jadwal> currentSchedule = generateSchedule();
        List<Jadwal> bestSchedule = new ArrayList<>(currentSchedule);
        List<List<Jadwal>> tabuList = new ArrayList<>();
        int maxTabuSize = 10;
        int maxIterations = 100;

        for (int i = 0; i < maxIterations; i++) {
            List<Jadwal> neighbor = generateNeighbor(currentSchedule);

            // Jika tidak ada di tabu list dan fitnes lebih baik, terima neighbor
            if (!tabuList.contains(neighbor) && calculateFitness(neighbor) > calculateFitness(bestSchedule)) {
                currentSchedule = neighbor;
                if (calculateFitness(currentSchedule) > calculateFitness(bestSchedule)) {
                    bestSchedule = currentSchedule;
                }
            }

            // Tambahkan ke tabu list
            tabuList.add(new ArrayList<>(currentSchedule));
            if (tabuList.size() > maxTabuSize) {
                tabuList.remove(0);  // Batasi ukuran tabu list
            }
        }

        return bestSchedule;
    }
    // Generate a neighboring solution by changing a random schedule entry
    private static List<Jadwal> generateNeighbor(List<Jadwal> schedule) {
        Random random = new Random();
        List<Jadwal> neighbor = new ArrayList<>(schedule);
        int index = random.nextInt(neighbor.size());
        Jadwal selectedJadwal = neighbor.get(index);

        // Cari jam baru yang tidak menyebabkan konflik
        String newJam;
        do {
            newJam = jams[random.nextInt(jams.length)];
        } while (isTimeConflict(neighbor, selectedJadwal.hari, newJam, index));  // Cek konflik jam

        // Update jadwal dengan jam baru yang tidak konflik
        neighbor.get(index).jam = newJam;

        return neighbor;
    }
    // Fitness function (could be more complex)
    private static int calculateFitness(List<Jadwal> schedule) {
        return schedule.size();  // Bisa disesuaikan untuk fungsi fitness yang lebih kompleks
    }

    @FunctionalInterface
    public interface ScheduleGeneratorFunction {
        List<Jadwal> generate();
    }
}
