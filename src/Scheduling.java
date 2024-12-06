import java.util.Scanner;

/**
 * Kelas `Scheduling` adalah kelas utama yang menginisialisasi data, memungkinkan pengguna untuk memilih mata kuliah,
 * dan menjalankan algoritma untuk menghasilkan jadwal.
 */
public class Scheduling {
    public static void main(String[] args) {
        // Menginisialisasi data dummy
        DataInitializer.createDummyData();

        // Memilih mata kuliah
        CourseSelector.selectCourses();

        // Meminta input dari pengguna untuk berapa kali menjalankan algoritma
        System.out.print("input how many times you want to run the algorithm : ");
        Scanner scanner = new Scanner(System.in);
        int times = scanner.nextInt();

        // Menjalankan algoritma sebanyak input pengguna
        for (int i = 0; i < times; i++) {
            SchedulePrinter.generateAndPrintSchedule("Genetic Algorithm", ScheduleGenerator::geneticAlgorithm);
            SchedulePrinter.generateAndPrintSchedule("Simulated Annealing", ScheduleGenerator::simulatedAnnealing);
            SchedulePrinter.generateAndPrintSchedule("Tabu Search", ScheduleGenerator::tabuSearch);
        }

        // Mencetak tabel waktu eksekusi
        SchedulePrinter.printTableOfExecutionTimes();
    }
}