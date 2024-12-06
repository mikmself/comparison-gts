import java.util.Scanner;

public class Scheduling {
    public static void main(String[] args) {
        DataInitializer.createDummyData();
        CourseSelector.selectCourses();
        System.out.print("input how many times you want to run the algorithm : ");
        Scanner scanner = new Scanner(System.in);
        int times = scanner.nextInt();
        for (int i = 0; i < times; i++) {
            SchedulePrinter.generateAndPrintSchedule("Genetic Algorithm", ScheduleGenerator::geneticAlgorithm);
            SchedulePrinter.generateAndPrintSchedule("Simulated Annealing", ScheduleGenerator::simulatedAnnealing);
            SchedulePrinter.generateAndPrintSchedule("Tabu Search", ScheduleGenerator::tabuSearch);
        }
        SchedulePrinter.printTableOfExecutionTimes();
    }
}
