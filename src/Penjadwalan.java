public class Penjadwalan {
    public static void main(String[] args) {
        DataInitializer.createDummyData();
        MataKuliahSelector.selectCourses();
        JadwalPrinter.generateAndPrintSchedule("Genetic Algorithm", ScheduleGenerator::geneticAlgorithm);
        JadwalPrinter.generateAndPrintSchedule("Simulated Annealing", ScheduleGenerator::simulatedAnnealing);
        JadwalPrinter.generateAndPrintSchedule("Tabu Search", ScheduleGenerator::tabuSearch);
    }
}


