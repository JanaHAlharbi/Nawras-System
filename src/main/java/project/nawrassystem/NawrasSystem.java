package project.nawrassystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class NawrasSystem {

    private static final String FILE_NAME = "permits.txt";
    static ArrayList<Permit> permits = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in);
    static FileHandler fileHandler = new FileHandler(FILE_NAME);

    public static void main(String[] args) {
        permits = fileHandler.loadPermits();

        while (true) {
            System.out.println("\n===== NAWRAS System =====");
            System.out.println("1. Request Permit");
            System.out.println("2. Request to Modify Permit");
            System.out.println("3. View Applicant Permit Detail");
            System.out.println("4. List All Applicant Permits");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> requestPermit();
                case 2 -> requestToModifyPermit();
                case 3 -> viewApplicantPermitDetail();
                case 4 -> listAllApplicantPermits();
                case 5 -> {
                    System.out.println("Thank you for using NAWRAS!");
                    return;
                }
                default -> System.out.println("❌ Invalid choice. Try again.");
            }
        }
    }
    
    private static void requestPermit() {
        String name;
        while (true) {
            System.out.print("Enter Applicant Name: ");
            name = scanner.nextLine().trim();
            
            if (name.isEmpty()) {
                System.out.println("❌ Name cannot be empty.");
                continue;
            }
            if (name.matches("[\\p{L}\\s]+")) {
                break;
            }
            System.out.println("❌ Invalid name. Letters only.");
        }
        
        String applicantId;
        while (true) {
            System.out.print("Enter Applicant ID (10 digits): ");
            applicantId = scanner.nextLine().trim();

            if (applicantId.matches("\\d{10}")) {
                break;
            }
            System.out.println("❌ Invalid ID. Enter 10-digit number.");
        }

        LocalDate date;
        while (true) {
            try {
                System.out.print("Enter Trip Date (YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("❌ Invalid date format.");
            }
        }

        Applicant applicant = new Applicant(name, applicantId);

        int id = permits.size() + 1;
        Permit newPermit = new Permit(id, applicant, date);
        permits.add(newPermit);
        fileHandler.savePermits(permits);

        System.out.println("✔ Permit created successfully! ID: " + id);
    }

    private static void requestToModifyPermit() {
    int id = 0;

    while (true) {
        System.out.print("Enter Permit ID to modify: ");
        String input = scanner.nextLine();

        try {
            id = Integer.parseInt(input);
            break; // valid number entered
        } catch (NumberFormatException e) {
            System.out.println("❌ Invalid permit ID. Please enter a numeric value.");
        }
    }

    for (Permit permit : permits) {
        if (permit.getPermitId() == id) {

            if (permit.getStatus().equalsIgnoreCase("Expired")) {
                System.out.println("❌ Cannot modify expired permits.");
                return;
            }
            while (true) {
                System.out.print("Enter new trip date (YYYY-MM-DD): ");
                String dateInput = scanner.nextLine();

                try {
                    LocalDate newDate = LocalDate.parse(dateInput);
                    permit.setTripDate(newDate);
                    fileHandler.savePermits(permits);
                    System.out.println("✔ Modification request submitted.");
                    return;
                } catch (Exception e) {
                    System.out.println("❌ Invalid date format.");
                }
            }
        }
    }
    System.out.println("❌ Permit not found.");
}

    private static void viewApplicantPermitDetail() {
        System.out.print("Enter Permit ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Permit permit : permits) {
            if (permit.getPermitId() == id) {
                System.out.println("\n=== Permit Detail ===");
                System.out.println("Permit ID: " + permit.getPermitId());
                System.out.println("Applicant Name: " + permit.getApplicant().getName());
                System.out.println("Applicant ID: " + permit.getApplicant().getId());
                System.out.println("Trip Date: " + permit.getTripDate());
                System.out.println("Status: " + permit.getStatus());
                return;
            }
        }
        System.out.println("❌ Permit not found.");
    }

    private static void listAllApplicantPermits() {
        System.out.println("\n----- All Applicant Permits -----");

        if (permits.isEmpty()) {
            System.out.println("No permits found.");
            return;
        }

        for (Permit permit : permits) {
            System.out.println(permit);
        }
    }
}
