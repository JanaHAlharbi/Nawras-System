package project.nawrassystem;
import java.time.LocalDate;

public class Permit {
    private int permitId;
    private String applicantName;
    private LocalDate tripDate;
    private String status;

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
    public void setStatus( String newStatus){
        this.status = newStatus;
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
