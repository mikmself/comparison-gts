import java.util.List;

/**
 * Kelas `SchedulePrinter` menyediakan metode untuk mencetak jadwal dan waktu eksekusi dari berbagai algoritma.
 */
public class SchedulePrinter {
    /**
     * Mencetak tabel waktu eksekusi dari algoritma Genetic Algorithm, Simulated Annealing, dan Tabu Search dan menghitung rata-ratanya.
     */
    public static void printTableOfExecutionTimes(int times) {
        double totalDuration1 = 0;
        double totalDuration2 = 0;
        double totalDuration3 = 0;

        System.out.println("Execution Times, without print schedule :");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-40s | %-40s | %-40s |%n", "Genetic Algorithm", "Simulated Annealing", "Tabu Search");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        for (int i = 0; i < times; i++) {
            long startTime = System.nanoTime();
            ScheduleGenerator.geneticAlgorithm();
            long endTime = System.nanoTime();
            double duration1 = (endTime - startTime) / 1_000_000.0;
            totalDuration1 += duration1;

            startTime = System.nanoTime();
            ScheduleGenerator.simulatedAnnealing();
            endTime = System.nanoTime();
            double duration2 = (endTime - startTime) / 1_000_000.0;
            totalDuration2 += duration2;

            startTime = System.nanoTime();
            ScheduleGenerator.tabuSearch();
            endTime = System.nanoTime();
            double duration3 = (endTime - startTime) / 1_000_000.0;
            totalDuration3 += duration3;

            System.out.printf("| %-40.3f | %-40.3f | %-40.3f |%n", duration1, duration2, duration3);
        }

        double averageDuration1 = totalDuration1 / times;
        double averageDuration2 = totalDuration2 / times;
        double averageDuration3 = totalDuration3 / times;

        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-40s | %-40s | %-40s |%n", "Average Execution Time", "Average Execution Time", "Average Execution Time");
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
        System.out.printf("| %-40.3f | %-40.3f | %-40.3f |%n", averageDuration1, averageDuration2, averageDuration3);
        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
    }

    /**
     * Menghasilkan dan mencetak jadwal menggunakan algoritma yang diberikan.
     *
     * @param algorithmName Nama algoritma.
     * @param generator Fungsi generator jadwal.
     */
    public static void generateAndPrintSchedule(String algorithmName, ScheduleGenerator.ScheduleGeneratorFunction generator) {
        long startTime = System.nanoTime();
        List<Schedule> schedule = generator.generate();
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.println("\nSchedule from " + algorithmName + ":");
        System.out.printf("Execution Time: %.3f ms%n", duration);
        printSchedule(schedule);
    }

    /**
     * Mencetak jadwal yang diberikan.
     *
     * @param schedule Daftar jadwal.
     */
    private static void printSchedule(List<Schedule> schedule) {
        String[] daysOrder = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        String[] timeOrder = {"08:00", "10:00", "12:00", "14:00", "16:00"};

        schedule.sort((s1, s2) -> {
            int dayIndex1 = findIndex(daysOrder, s1.day);
            int dayIndex2 = findIndex(daysOrder, s2.day);
            if (dayIndex1 == dayIndex2) {
                int timeIndex1 = findIndex(timeOrder, s1.time);
                int timeIndex2 = findIndex(timeOrder, s2.time);
                return Integer.compare(timeIndex1, timeIndex2);
            }
            return Integer.compare(dayIndex1, dayIndex2);
        });

        for (String day : daysOrder) {
            boolean dayPrinted = false;
            for (Schedule s : schedule) {
                if (s.day.equalsIgnoreCase(day)) {
                    if (!dayPrinted) {
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
                        System.out.println(day);
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
                        dayPrinted = true;
                    }
                    System.out.printf("         | %-40s | %-10s | %-40s | %-15s%n", s.subject, s.time, s.lecturer, s.room);
                    System.out.println("         |----------------------------------------------------------------------------------------------------------------------");
                }
            }
        }
    }

    /**
     * Mencari indeks dari nilai dalam array.
     *
     * @param array Array yang akan dicari.
     * @param value Nilai yang akan dicari.
     * @return Indeks dari nilai dalam array, atau -1 jika tidak ditemukan.
     */
    private static int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(value)) {
                return i;
            }
        }
        return -1;
    }
}