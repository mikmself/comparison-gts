import java.util.List;

public class SchedulePrinter {
    public static void generateAndPrintSchedule(String algorithmName, ScheduleGenerator.ScheduleGeneratorFunction generator) {
        long startTime = System.nanoTime();
        List<Schedule> schedule = generator.generate();
        long endTime = System.nanoTime();
        double duration = (endTime - startTime) / 1_000_000.0;
        System.out.println("\nSchedule from " + algorithmName + ":");
        System.out.printf("Execution Time: %.3f ms%n", duration);
        printSchedule(schedule);
    }

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

    private static int findIndex(String[] array, String value) {
        for (int i = 0; i < array.length; i++) {
            if (array[i].equalsIgnoreCase(value)) {
                return i;
            }
        }
        return -1;
    }
}
