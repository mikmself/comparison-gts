public class Scheduling {
    public static void main(String[] args) {
        DataInitializer.createDummyData();
        CourseSelector.selectCourses();
        SchedulePrinter.generateAndPrintSchedule("Genetic Algorithm", ScheduleGenerator::geneticAlgorithm);
        SchedulePrinter.generateAndPrintSchedule("Simulated Annealing", ScheduleGenerator::simulatedAnnealing);
        SchedulePrinter.generateAndPrintSchedule("Tabu Search", ScheduleGenerator::tabuSearch);
    }
}
