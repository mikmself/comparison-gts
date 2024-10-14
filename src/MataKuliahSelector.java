import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MataKuliahSelector {
    static List<MataKuliah> selectedMatkul = new ArrayList<>();

    public static void selectCourses() {
        Scanner scanner = new Scanner(System.in);
        int totalSKS = 0;

        // Tampilkan daftar mata kuliah dalam bentuk tabel
        System.out.println("Daftar Mata Kuliah:");
        System.out.printf("%-10s%-30s%-10s%n", "Kode", "Mata Kuliah", "SKS");
        System.out.println("----------------------------------------------------");

        for (MataKuliah matkul : DataInitializer.matkuls) {
            System.out.printf("%-10s%-30s%-10d%n", matkul.kode, matkul.nama, matkul.sks);
        }

        while (totalSKS < 24) {
            System.out.print("Masukkan kode mata kuliah yang ingin dipilih (atau 'selesai' untuk mengakhiri): ");
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("selesai")) {
                break;
            }

            MataKuliah selected = findMataKuliah(input);

            if (selected != null) {
                if (totalSKS + selected.sks <= 24) {
                    selectedMatkul.add(selected);
                    totalSKS += selected.sks;
                    System.out.println("Mata kuliah " + selected.nama + " ditambahkan. Total SKS: " + totalSKS);
                } else {
                    System.out.println("Pilih SKS melebihi batas 24! Silakan pilih lagi.");
                }
            } else {
                System.out.println("Mata kuliah tidak ditemukan.");
            }

            printSelectedCourses();

            if (totalSKS >= 20) {
                handleRemoveOption(scanner, totalSKS);
                if (askToGenerateSchedule(scanner, totalSKS)) break;
            }
        }
    }

    private static MataKuliah findMataKuliah(String kode) {
        for (MataKuliah matkul : DataInitializer.matkuls) {
            if (matkul.kode.equalsIgnoreCase(kode)) {
                return matkul;
            }
        }
        return null;
    }

    private static void printSelectedCourses() {
        System.out.println("\nMata Kuliah yang Dipilih:");
        for (MataKuliah matkul : selectedMatkul) {
            System.out.println(matkul.kode + " - " + matkul.nama + " | SKS: " + matkul.sks);
        }
    }

    private static void handleRemoveOption(Scanner scanner, int totalSKS) {
        System.out.print("Ingin menghapus mata kuliah? (y/n): ");
        String removeInput = scanner.nextLine();
        if (removeInput.equalsIgnoreCase("y")) {
            System.out.print("Masukkan kode mata kuliah yang ingin dihapus: ");
            String removeCode = scanner.nextLine();
            MataKuliah toRemove = findMataKuliahInSelected(removeCode);
            if (toRemove != null) {
                selectedMatkul.remove(toRemove);
                totalSKS -= toRemove.sks;
                System.out.println("Mata kuliah " + toRemove.nama + " dihapus. Total SKS: " + totalSKS);
            } else {
                System.out.println("Mata kuliah tidak ditemukan dalam daftar yang dipilih.");
            }
        }
    }

    private static MataKuliah findMataKuliahInSelected(String kode) {
        for (MataKuliah matkul : selectedMatkul) {
            if (matkul.kode.equalsIgnoreCase(kode)) {
                return matkul;
            }
        }
        return null;
    }

    private static boolean askToGenerateSchedule(Scanner scanner, int totalSKS) {
        System.out.print("Total SKS lebih dari 20, ingin langsung menghasilkan jadwal? (y/n): ");
        String generateInput = scanner.nextLine();
        return generateInput.equalsIgnoreCase("y");
    }
}
