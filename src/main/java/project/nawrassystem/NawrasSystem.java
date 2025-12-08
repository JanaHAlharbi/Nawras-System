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
            System.out.println("\n===== Nawras Permit System =====");
            System.out.println("1. Request Permit");
            System.out.println("2. Modify Permit (Only Active)");
            System.out.println("3. View Permit Details");
            System.out.println("4. List Permit History");
            System.out.println("5. Exit");
            System.out.print("Choose option: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1 -> requestPermit();
                case 2 -> modifyPermit();
                case 3 -> viewPermitDetails();
                case 4 -> showPermitHistory();
                case 5 -> {
                    System.out.println("Thank you!");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    static void requestPermit() {

        // NAME
        String name;
        while (true) {
            System.out.print("Enter Applicant Name: ");
            name = scanner.nextLine().trim();

            if (name.isEmpty()) {
                System.out.println("❌ Name cannot be empty.");
                continue;
            }

            if (name.matches("[a-zA-Z\\s]+")) {
                break;
            } else {
                System.out.println("❌ Invalid name. Use English letters only.");
            }
        }

        // ID
        String applicantId;
        while (true) {
            System.out.print("Enter Applicant ID (10 digits): ");
            applicantId = scanner.nextLine().trim();

            if (applicantId.matches("\\d{10}")) {
                break;
            } else {
                System.out.println("❌ Invalid ID. Enter 10-digit number.");
            }
        }

        Applicant applicant = new Applicant(name, applicantId);

        // DATE
        LocalDate date = null;
        while (true) {
            try {
                System.out.print("Enter Trip Date (YYYY-MM-DD): ");
                date = LocalDate.parse(scanner.nextLine());
                break;
            } catch (Exception e) {
                System.out.println("❌ Invalid date format.");
            }
        }

        int id = permits.size() + 1;
        Permit newPermit = new Permit(id, applicant, date);
        permits.add(newPermit);

        fileHandler.savePermits(permits);

        System.out.println("✔️ Permit created! ID: " + id);
    }

    private static void modifyPermit() {
        System.out.print("Enter Permit ID to modify: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Permit permit : permits) {
            if (permit.getPermitId() == id) {
                if (permit.getStatus().equals("Expired")) {
                    System.out.println("Cannot modify expired permits.");
                } else {
                    try {
                        System.out.print("Enter new date: ");
                        LocalDate newDate = LocalDate.parse(scanner.nextLine());
                        permit.setTripDate(newDate);
                        fileHandler.savePermits(permits);
                        System.out.println("Updated.");
                    } catch (Exception e) {
                        System.out.println("Invalid date.");
                    }
                }
                return;
            }
        }
        System.out.println("Not found.");
    }

    static void viewPermitDetails() {
        System.out.print("Enter Permit ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        for (Permit permit : permits) {
            if (permit.getPermitId() == id) {
                System.out.println("\n=== Permit Details ===");
                System.out.println("Permit ID: " + permit.getPermitId());
                System.out.println("Name: " + permit.getApplicant().getName());
                System.out.println("Applicant ID: " + permit.getApplicant().getId());
                System.out.println("Trip Date: " + permit.getTripDate());
                System.out.println("Status: " + permit.getStatus());
                return;
            }
        }
        System.out.println("Not found.");
    }

    private static void showPermitHistory() {
        System.out.println("\n----- Permit History -----");
        if (permits.isEmpty()) {
            System.out.println("No permits.");
        } else {
            for (Permit permit : permits) {
                System.out.println(permit);
            }
        }
    }
}