import java.util.List;
public class JadwalPrinter {
    public static void generateAndPrintSchedule(String algorithmName, ScheduleGenerator.ScheduleGeneratorFunction generator) {
        long startTime = System.nanoTime();
        List<Jadwal> jadwal = generator.generate();
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.println("\nJadwal dari " + algorithmName + ":");
        System.out.printf("Waktu Pengerjaan: %.3f ms%n", duration);
        printJadwal(jadwal);
    }
    private static void printJadwal(List<Jadwal> jadwal) {
        String[] daysOrder = {"Senin", "Selasa", "Rabu", "Kamis", "Jumat", "Sabtu"};
        String[] timeOrder = {"08:00", "10:00", "12:00", "14:00", "16:00"};
        jadwal.sort((j1, j2) -> {
            int dayIndex1 = findIndex(daysOrder, j1.hari);
            int dayIndex2 = findIndex(daysOrder, j2.hari);
            if (dayIndex1 == dayIndex2) {
                int timeIndex1 = findIndex(timeOrder, j1.jam);
                int timeIndex2 = findIndex(timeOrder, j2.jam);
                return Integer.compare(timeIndex1, timeIndex2);
            }
            return Integer.compare(dayIndex1, dayIndex2);
        });
        for (String day : daysOrder) {
            boolean dayPrinted = false;
            for (Jadwal j : jadwal) {
                if (j.hari.equalsIgnoreCase(day)) {
                    if (!dayPrinted) {
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
                        System.out.println(day);
                        System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
                        dayPrinted = true;
                    }
                    System.out.printf("         | %-40s | %-10s | %-40s | %-15s%n", j.matkul, j.jam, j.dosen, j.ruang);
                    System.out.println("         |----------------------------------------------------------------------------------------------------------------------");
                }
            }
        }
    }
    private static int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(value)) {
                return i;
            }
        }
        return -1;
    }
}
