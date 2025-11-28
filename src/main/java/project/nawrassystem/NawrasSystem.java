/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package project.nawrassystem;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.Scanner;
/**
 *
 * @author janaalharbi
 */
public class NawrasSystem {

    public static void main(String[] args) {
       
    }


     private static void requestPermit() {
        System.out.print("Enter Applicant Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Trip Date (YYYY-MM-DD): ");
        LocalDate date = LocalDate.parse(scanner.nextLine());

        int id = permits.size() + 1;
        Permit newPermit = new Permit(id, name, date);
        permits.add(newPermit);

        savePermits();
        System.out.println("Permit created successfully! ID: " + id);
    }

    private static void savePermits() {
        try (PrintWriter out = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Permit permit : permits) {
                out.println(permit);
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
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

 private static void viewPermitDetails() {
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

   

   
}


