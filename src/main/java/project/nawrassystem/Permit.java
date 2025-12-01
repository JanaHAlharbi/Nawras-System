/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package project.nawrassystem;
    import java.time.LocalDate;

/**
 *
 * @author janaalharbi
 */

public class Permit {
    private int permitId;
    private String applicantName;
    private LocalDate tripDate;

    public Permit(int permitId, String applicantName, LocalDate tripDate) {
        this.permitId = permitId;
        this.applicantName = applicantName;
        this.tripDate = tripDate;
    }

    public int getPermitId() {
        return permitId;
    }

    public String getApplicantName() {
        return applicantName;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate newDate) {
        this.tripDate = newDate;
    }

    public String getStatus() {
        return tripDate.isBefore(LocalDate.now()) ? "Expired" : "Active";
    }

    @Override
    public String toString() {
        return permitId + "," + applicantName + "," + tripDate + "," + getStatus();
    }

    public static Permit fromString(String str) {
        String[] parts = str.split(",");
        return new Permit(
                Integer.parseInt(parts[0]),
                parts[1],
                LocalDate.parse(parts[2])
        );
    }

}
