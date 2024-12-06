import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class ScheduleGenerator {
    static String[] days = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
    static String[] times = {"08:00", "10:00", "12:00", "14:00", "16:00"};

    public static List<Schedule> generateSchedule() {
        List<Schedule> bestSchedule = new ArrayList<>();
        HashSet<String> used = new HashSet<>();
        Random random = new Random();

        for (Course course : CourseSelector.selectedCourses) {
            String day = days[random.nextInt(days.length)];
            String time = times[random.nextInt(times.length)];
            Lecturer lecturer = DataInitializer.lecturers.get(random.nextInt(DataInitializer.lecturers.size()));
            Room room = DataInitializer.rooms.get(random.nextInt(DataInitializer.rooms.size()));
            String uniqueKey = day + time;

            while (used.contains(uniqueKey)) {
                time = times[random.nextInt(times.length)];
                uniqueKey = day + time;
            }
            used.add(uniqueKey);
            bestSchedule.add(new Schedule(course.name, time, day, lecturer.name, room.name));
        }

        return bestSchedule;
    }

    public static List<Schedule> geneticAlgorithm() {
        List<List<Schedule>> population = generateInitialPopulation();
        int generations = 100;
        for (int i = 0; i < generations; i++) {
            population = evolvePopulation(population);
        }
        return selectBestSchedule(population);
    }

    private static List<List<Schedule>> generateInitialPopulation() {
        List<List<Schedule>> initialPopulation = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            initialPopulation.add(generateSchedule());
        }
        return initialPopulation;
    }

    private static List<List<Schedule>> evolvePopulation(List<List<Schedule>> population) {
        List<List<Schedule>> newPopulation = new ArrayList<>();
        for (int i = 0; i < population.size(); i += 2) {
            List<Schedule> parent1 = population.get(i);
            List<Schedule> parent2 = (i + 1 < population.size()) ? population.get(i + 1) : parent1;

            List<Schedule> offspring = crossover(parent1, parent2);
            mutate(offspring);
            newPopulation.add(offspring);
        }
        return newPopulation;
    }

    private static List<Schedule> crossover(List<Schedule> parent1, List<Schedule> parent2) {
        List<Schedule> offspring = new ArrayList<>(parent1.subList(0, parent1.size() / 2));
        for (Schedule schedule : parent2.subList(parent2.size() / 2, parent2.size())) {
            if (!isTimeConflict(offspring, schedule.day, schedule.time)) {
                offspring.add(schedule);
            } else {
                String newTime;
                Random random = new Random();
                do {
                    newTime = times[random.nextInt(times.length)];
                } while (isTimeConflict(offspring, schedule.day, newTime));
                offspring.add(new Schedule(schedule.subject, newTime, schedule.day, schedule.lecturer, schedule.room));
            }
        }
        return offspring;
    }

    private static void mutate(List<Schedule> schedule) {
        if (schedule.isEmpty()) return;
        Random random = new Random();
        int index = random.nextInt(schedule.size());
        Schedule selectedSchedule = schedule.get(index);
        String newTime;
        do {
            newTime = times[random.nextInt(times.length)];
        } while (isTimeConflict(schedule, selectedSchedule.day, newTime, index));

        schedule.get(index).time = newTime;
    }

    private static boolean isTimeConflict(List<Schedule> schedule, String day, String time) {
        for (Schedule scheduleItem : schedule) {
            if (scheduleItem.day.equals(day) && scheduleItem.time.equals(time)) {
                return true;
            }
        }
        return false;
    }

    private static List<Schedule> selectBestSchedule(List<List<Schedule>> population) {
        return population.get(0);
    }

    public static List<Schedule> simulatedAnnealing() {
        List<Schedule> currentSchedule = generateSchedule();
        List<Schedule> bestSchedule = new ArrayList<>(currentSchedule);
        double temperature = 1000;
        double coolingRate = 0.003;
        Random random = new Random();

        while (temperature > 1) {
            List<Schedule> newSchedule = new ArrayList<>(currentSchedule);
            int index = random.nextInt(newSchedule.size());
            Schedule selectedSchedule = newSchedule.get(index);
            String newTime;
            do {
                newTime = times[random.nextInt(times.length)];
            } while (isTimeConflict(newSchedule, selectedSchedule.day, newTime, index));

            newSchedule.get(index).time = newTime;
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

    private static double acceptanceProbability(List<Schedule> current, List<Schedule> newSchedule, double temperature) {
        int currentFitness = calculateFitness(current);
        int newFitness = calculateFitness(newSchedule);
        if (newFitness > currentFitness) {
            return 1.0;
        }
        return Math.exp((newFitness - currentFitness) / temperature);
    }

    private static boolean isTimeConflict(List<Schedule> schedule, String day, String time, int excludeIndex) {
        for (int i = 0; i < schedule.size(); i++) {
            if (i == excludeIndex) continue;
            Schedule scheduleItem = schedule.get(i);
            if (scheduleItem.day.equals(day) && scheduleItem.time.equals(time)) {
                return true;
            }
        }
        return false;
    }

    public static List<Schedule> tabuSearch() {
        List<Schedule> currentSchedule = generateSchedule();
        List<Schedule> bestSchedule = new ArrayList<>(currentSchedule);
        List<List<Schedule>> tabuList = new ArrayList<>();
        int maxTabuSize = 10;
        int maxIterations = 100;

        for (int i = 0; i < maxIterations; i++) {
            List<Schedule> neighbor = generateNeighbor(currentSchedule);
            if (!tabuList.contains(neighbor) && calculateFitness(neighbor) > calculateFitness(bestSchedule)) {
                currentSchedule = neighbor;
                if (calculateFitness(currentSchedule) > calculateFitness(bestSchedule)) {
                    bestSchedule = currentSchedule;
                }
            }
            tabuList.add(new ArrayList<>(currentSchedule));
            if (tabuList.size() > maxTabuSize) {
                tabuList.remove(0);
            }
        }
        return bestSchedule;
    }

    private static List<Schedule> generateNeighbor(List<Schedule> schedule) {
        Random random = new Random();
        List<Schedule> neighbor = new ArrayList<>(schedule);
        int index = random.nextInt(neighbor.size());
        Schedule selectedSchedule = neighbor.get(index);
        String newTime;
        do {
            newTime = times[random.nextInt(times.length)];
        } while (isTimeConflict(neighbor, selectedSchedule.day, newTime, index));
        neighbor.get(index).time = newTime;
        return neighbor;
    }

    private static int calculateFitness(List<Schedule> schedule) {
        return schedule.size();
    }

    @FunctionalInterface
    public interface ScheduleGeneratorFunction {
        List<Schedule> generate();
    }
}
