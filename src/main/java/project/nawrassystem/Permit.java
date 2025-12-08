package project.nawrassystem;

import java.time.LocalDate;

public class Permit {
    private int permitId;
    private Applicant applicant;
    private LocalDate tripDate;
    private String status;

    public Permit(int permitId, Applicant applicant, LocalDate tripDate) {
        this.permitId = permitId;
        this.applicant = applicant;
        this.tripDate = tripDate;
        this.status = null; 
    }

    public int getPermitId() {
        return permitId;
    }

    public Applicant getApplicant() {
        return applicant;
    }

    public LocalDate getTripDate() {
        return tripDate;
    }

    public void setTripDate(LocalDate newDate) {
        this.tripDate = newDate;
    }

    public void setStatus(String newStatus) {
        this.status = newStatus;
    }

    public String getStatus() {
        if (status != null) {
            return status;
        }
        return tripDate.isBefore(LocalDate.now()) ? "Expired" : "Active";
    }

    @Override
    public String toString() {
        // id,name,applicantId,date
        return permitId + ","
                + applicant.getName() + ","
                + applicant.getId() + ","
                + tripDate;
    }

    public static Permit fromString(String str) {
        String[] parts = str.split(",");

        if (parts.length == 3) {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            LocalDate date = LocalDate.parse(parts[2]);
            Applicant applicant = new Applicant(name, "0000000000");
            return new Permit(id, applicant, date);
        }

        if (parts.length >= 4) {
            int id = Integer.parseInt(parts[0]);
            String name = parts[1];
            String applicantId = parts[2];
            LocalDate date = LocalDate.parse(parts[3]);
            Applicant applicant = new Applicant(name, applicantId);
            return new Permit(id, applicant, date);
        }

        throw new IllegalArgumentException("Invalid permit line: " + str);
    }
}
