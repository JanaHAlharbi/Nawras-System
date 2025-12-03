package project.nawrassystem;
import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.File;
import java.time.LocalDate;
import java.util.Scanner;

public class NawrasSystemTest {

    private static final String FILE_NAME = "permits.txt";


    @Test
    public void testRequestPermitLogic() {

        NawrasSystem.permits.clear();   

        String expectedName = "Jana";
        LocalDate expectedDate = LocalDate.parse("2025-12-10");
        int expectedId = 1;

        int id = NawrasSystem.permits.size() + 1;      // should be 1
        Permit newPermit = new Permit(id, expectedName, expectedDate);
        NawrasSystem.permits.add(newPermit);

        Permit actual = NawrasSystem.permits.get(0);

        assertEquals(expectedId, actual.getPermitId());
        assertEquals(expectedName, actual.getApplicantName());
        assertEquals(expectedDate, actual.getTripDate());
    }


    @Test
    public void testModifyPermitActive() {

        NawrasSystem.permits.clear();
        Permit p = new Permit(1, "Jana", LocalDate.parse("2025-12-10"));
        NawrasSystem.permits.add(p);

        LocalDate expectedNewDate = LocalDate.parse("2025-12-20");

        int searchId = 1;
        for (Permit permit : NawrasSystem.permits) {
            if (permit.getPermitId() == searchId) {
                if (!permit.getStatus().equals("Expired")) {
                    permit.setTripDate(expectedNewDate);  
                }
            }
        }

        LocalDate actualDate = NawrasSystem.permits.get(0).getTripDate();

        assertEquals(expectedNewDate, actualDate);
    }
    @Test
    public void testModifyPermitExpired() {

        NawrasSystem.permits.clear();
        Permit p = new Permit(1, "Jana", LocalDate.parse("2025-12-10"));
        p.setStatus("Expired");  // force expired status
        NawrasSystem.permits.add(p);

        LocalDate expectedOldDate = p.getTripDate();


        int searchId = 1;
        LocalDate newDateAttempt = LocalDate.parse("2025-12-20");  // user tries to change

        for (Permit permit : NawrasSystem.permits) {
            if (permit.getPermitId() == searchId) {
                if (!permit.getStatus().equals("Expired")) {
                    permit.setTripDate(newDateAttempt);
                }
            }
        }

        LocalDate actualDate = NawrasSystem.permits.get(0).getTripDate();

        assertEquals(expectedOldDate, actualDate);
    }
    @Test
    public void testModifyPermitNotFound() {

        NawrasSystem.permits.clear();
        Permit p = new Permit(1, "Jana", LocalDate.parse("2025-12-10"));
        NawrasSystem.permits.add(p);

        int searchId = 99;  // an ID that does NOT exist

        Permit expected = null;

        Permit actual = null;
        for (Permit permit : NawrasSystem.permits) {
            if (permit.getPermitId() == searchId) {
                actual = permit;
                break;
            }
        }

        assertEquals(expected, actual); 
    }

    @Test
    public void testViewPermitDetailsFound(){
        Permit p = new Permit(1, "Jana", LocalDate.parse("2025-12-10"));
        NawrasSystem.permits.clear();
        NawrasSystem.permits.add(p);

        int searchId = 1;  

        int expectedId = 1;
        String expectedName = "Jana";
        LocalDate expectedDate = LocalDate.parse("2025-12-10");
        String expectedStatus = "Active";


        Permit actual = null;
        for (Permit permit : NawrasSystem.permits) {
            if (permit.getPermitId() == searchId) {
                actual = permit;
                break;
            }
        }

        assertNotNull(actual);                              
        assertEquals(expectedId, actual.getPermitId());
        assertEquals(expectedName, actual.getApplicantName());
        assertEquals(expectedDate, actual.getTripDate());
        assertEquals(expectedStatus, actual.getStatus());
    }
    @Test
    public void testViewPermitDetailsNotFound() {
        NawrasSystem.permits.clear();  
        Permit p = new Permit(1, "Jana", LocalDate.parse("2025-12-10"));
        NawrasSystem.permits.add(p);

        int searchId = 99; 

        Permit expected = null;  

        Permit actual = null;
        for (Permit permit : NawrasSystem.permits) {
            if (permit.getPermitId() == searchId) {
                actual = permit;
                break;
            }
        }

        assertEquals(expected, actual);   
    }

    @Test
    public void testShowPermitHistoryEmpty() {
        NawrasSystem.permits.clear();

        boolean expectedEmpty = true;

        boolean actualEmpty = NawrasSystem.permits.isEmpty();

        assertTrue(actualEmpty);
    }

    @Test
    public void testShowPermitHistoryNotEmpty() {
        NawrasSystem.permits.clear();
        NawrasSystem.permits.add(new Permit(1, "Jana", LocalDate.parse("2025-12-10")));

        boolean expectedEmpty = false;   

        boolean actualEmpty = NawrasSystem.permits.isEmpty();

        assertFalse(actualEmpty);
    }

    }