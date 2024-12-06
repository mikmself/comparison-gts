import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CourseSelector {
    static List<Course> selectedCourses = new ArrayList<>();

    public static void selectCourses() {
        Scanner scanner = new Scanner(System.in);
        int totalCredits = 0;
        System.out.println("Course List:");
        System.out.printf("%-10s%-50s%-10s%n", "Code", "Course", "Credits");
        System.out.println("---------------------------------------------------------------");
        for (Course course : DataInitializer.courses) {
            System.out.printf("%-10s%-52s%-10d%n", course.code, course.name, course.credits);
        }

        while (totalCredits < 24) {
            System.out.print("Enter the course code you want to select (or 'done' to finish): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("done")) {
                break;
            }
            Course selected = findCourse(input);
            if (selected != null) {
                if (totalCredits + selected.credits <= 24) {
                    selectedCourses.add(selected);
                    totalCredits += selected.credits;
                    System.out.println("Course " + selected.name + " added. Total credits: " + totalCredits);
                } else {
                    System.out.println("Selected credits exceed the 24-credit limit! Please select again.");
                }
            } else {
                System.out.println("Course not found.");
            }
            printSelectedCourses();
            if (totalCredits >= 20) {
                handleRemoveOption(scanner, totalCredits);
                if (askToGenerateSchedule(scanner, totalCredits)) break;
            }
        }
    }

    private static Course findCourse(String code) {
        for (Course course : DataInitializer.courses) {
            if (course.code.equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private static void printSelectedCourses() {
        System.out.println("\nSelected Courses:");
        for (Course course : selectedCourses) {
            System.out.println(course.code + " - " + course.name + " | Credits: " + course.credits);
        }
    }

    private static void handleRemoveOption(Scanner scanner, int totalCredits) {
        System.out.print("Do you want to remove a course? (y/n): ");
        String removeInput = scanner.nextLine();
        if (removeInput.equalsIgnoreCase("y")) {
            System.out.print("Enter the course code to remove: ");
            String removeCode = scanner.nextLine();
            Course toRemove = findCourseInSelected(removeCode);
            if (toRemove != null) {
                selectedCourses.remove(toRemove);
                totalCredits -= toRemove.credits;
                System.out.println("Course " + toRemove.name + " removed. Total credits: " + totalCredits);
            } else {
                System.out.println("Course not found in the selected list.");
            }
        }
    }

    private static Course findCourseInSelected(String code) {
        for (Course course : selectedCourses) {
            if (course.code.equalsIgnoreCase(code)) {
                return course;
            }
        }
        return null;
    }

    private static boolean askToGenerateSchedule(Scanner scanner, int totalCredits) {
        System.out.print("Total credits exceed 20, do you want to generate the schedule now? (y/n): ");
        String generateInput = scanner.nextLine();
        return generateInput.equalsIgnoreCase("y");
    }
}
