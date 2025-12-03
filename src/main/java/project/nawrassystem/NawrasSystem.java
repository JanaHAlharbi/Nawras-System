package project.nawrassystem;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class NawrasSystem {
    private static final String FILE_NAME = "permits.txt";
    static ArrayList<Permit> permits = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
       loadPermits();
        
        while (true) {
            System.out.println("\n===== Nawras Permit System =====");
            System.out.println("1. Request Permit");
            System.out.println("2. Modify Permit (Only Active)");
            System.out.println("3. View Permit Details");
            System.out.println("4. List Permit History");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume line

            switch (choice) {
                case 1:
                    requestPermit();
                    break;
                case 2:
                    modifyPermit();
                    break;
                case 3:
                    viewPermitDetails();
                    break;
                case 4:
                    showPermitHistory();
                    break;
                case 5:
                    System.out.println("Thank you for using Nawras System!");
                    return;
                default:
                    System.out.println("Invalid choice, try again.");
         }  
      }
    }

     static void requestPermit() {
        System.out.print("Enter Applicant Name: ");
        String name = scanner.nextLine();

            LocalDate date = null;
    while (true) {
        try {
            System.out.print("Enter Trip Date (YYYY-MM-DD): ");
            date = LocalDate.parse(scanner.nextLine());
            break; 
        } catch (Exception e) {
            System.out.println("❌ Invalid date format. Please try again (YYYY-MM-DD).");
        }
    }


        int id = permits.size() + 1;
        Permit newPermit = new Permit(id, name, date);
        permits.add(newPermit);

        savePermits();
        System.out.println("Permit created successfully! ID: " + id);
    }

     private static void modifyPermit() {
        System.out.print("Enter Permit ID to modify: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Permit permit : permits) {
            if (permit.getPermitId() == id) {
                if (permit.getStatus().equals("Expired")) {
                    System.out.println("Cannot modify expired permits. View-only in history.");
                } else {
                    System.out.print("Enter new trip date (YYYY-MM-DD): ");
                    LocalDate newDate = LocalDate.parse(scanner.nextLine());
                    permit.setTripDate(newDate);
                    savePermits();
                    System.out.println("Permit updated successfully!");
                }
                return;
            }
        }
        System.out.println("Permit not found.");
    }

    static void viewPermitDetails() {
        System.out.print("Enter Permit ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Permit permit : permits) {
            if (permit.getPermitId() == id) {
                System.out.println("\n=== Permit Details ===");
                System.out.println("Permit ID: " + permit.getPermitId());
                System.out.println("Applicant: " + permit.getApplicantName());
                System.out.println("Trip Date: " + permit.getTripDate());
                System.out.println("Status: " + permit.getStatus());
                System.out.println("\nShow this screen as proof (example: QR or download view)");
                return;
            }
        }
        System.out.println("Permit not found.");
    }

    private static void showPermitHistory() {
        System.out.println("\n----- Permit History -----");
        if (permits.isEmpty()) {
            System.out.println("No permits found.");
            return;
        }
        for (Permit permit : permits) {
            System.out.println(permit);
        }
    }

   private static void loadPermits() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            while ((line = br.readLine()) != null) {
                permits.add(Permit.fromString(line));
            }
        } catch (IOException ignored) {}
    }
 
   static void savePermits() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Permit permit : permits) {
                out.println(permit);
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}


