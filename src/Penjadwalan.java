public class Penjadwalan {
    public static void main(String[] args) {
        // Inisialisasi data dummy
        DataInitializer.createDummyData();

        // Pemilihan mata kuliah
        MataKuliahSelector.selectCourses();

        // Gunakan ScheduleGeneratorFunction untuk method reference
        JadwalPrinter.generateAndPrintSchedule("Genetic Algorithm", ScheduleGenerator::geneticAlgorithm);
        JadwalPrinter.generateAndPrintSchedule("Simulated Annealing", ScheduleGenerator::simulatedAnnealing);
        JadwalPrinter.generateAndPrintSchedule("Tabu Search", ScheduleGenerator::tabuSearch);
    }
}


