import java.util.List;

public class JadwalPrinter {
    public static void generateAndPrintSchedule(String algorithmName, ScheduleGenerator.ScheduleGeneratorFunction generator) {
        long startTime = System.nanoTime();
        List<Jadwal> jadwal = generator.generate();
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0; // convert to milliseconds

        System.out.println("\nJadwal dari " + algorithmName + ":");
        System.out.printf("Waktu Pengerjaan: %.3f ms%n", duration);
        printJadwal(jadwal);
    }

    private static void printJadwal(List<Jadwal> jadwal) {
        System.out.printf("%-20s%-10s%-10s%-15s%-15s%n", "Mata Kuliah", "Jam", "Hari", "Dosen", "Ruang");
        System.out.println("-------------------------------------------------------------");

        for (Jadwal j : jadwal) {
            System.out.printf("%-20s%-10s%-10s%-15s%-15s%n", j.matkul, j.jam, j.hari, j.dosen, j.ruang);
        }
    }
}
