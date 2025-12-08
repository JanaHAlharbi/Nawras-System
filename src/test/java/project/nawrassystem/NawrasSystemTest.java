package project.nawrassystem;
import org.junit.Test;
import static org.junit.Assert.*;
import java.time.LocalDate;

public class NawrasSystemTest {

    @Test
    public void testRequestPermit() {

        NawrasSystem.permits.clear();

        Applicant app = new Applicant("Jana", "1234567890");
        LocalDate expectedDate = LocalDate.parse("2025-12-10");
        int expectedId = 1;

        int id = NawrasSystem.permits.size() + 1;
        Permit newPermit = new Permit(id, app, expectedDate);
        NawrasSystem.permits.add(newPermit);

        Permit actual = NawrasSystem.permits.get(0);

        assertEquals(expectedId, actual.getPermitId());
        assertEquals("Jana", actual.getApplicant().getName());
        assertEquals(expectedDate, actual.getTripDate());
    }


    @Test
    public void testModifyPermitActive() {

        NawrasSystem.permits.clear();
        Applicant app = new Applicant("Jana", "1234567890");
        Permit p = new Permit(1, app, LocalDate.parse("2025-12-10"));
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
        Applicant app = new Applicant("Jana", "1234567890");
        Permit p = new Permit(1, app, LocalDate.parse("2025-12-10"));
        p.setStatus("Expired");
        NawrasSystem.permits.add(p);

        LocalDate expectedOldDate = p.getTripDate();

        int searchId = 1;
        LocalDate newDateAttempt = LocalDate.parse("2025-12-20");

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
        Applicant app = new Applicant("Jana", "1234567890");
        Permit p = new Permit(1, app, LocalDate.parse("2025-12-10"));
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
    public void testViewPermitDetailsFound(){

        Applicant app = new Applicant("Jana", "1234567890");
        Permit p = new Permit(1, app, LocalDate.parse("2025-12-10"));
        NawrasSystem.permits.clear();
        NawrasSystem.permits.add(p);

        int searchId = 1;

        Permit expected = p;
        Permit actual = null;

        for (Permit permit : NawrasSystem.permits) {
            if (permit.getPermitId() == searchId) {
                actual = permit;
                break;
            }
        }

        assertNotNull(actual);
        assertEquals(1, actual.getPermitId());
        assertEquals("Jana", actual.getApplicant().getName());
        assertEquals(LocalDate.parse("2025-12-10"), actual.getTripDate());
        assertEquals("Active", actual.getStatus());
    }


    @Test
    public void testViewPermitDetailsNotFound() {

        NawrasSystem.permits.clear();
        Applicant app = new Applicant("Jana", "1234567890");
        Permit p = new Permit(1, app, LocalDate.parse("2025-12-10"));
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
        boolean actualEmpty = NawrasSystem.permits.isEmpty();

        assertTrue(actualEmpty);
    }


    @Test
    public void testShowPermitHistoryNotEmpty() {

        NawrasSystem.permits.clear();
        Applicant app = new Applicant("Jana", "1234567890");
        NawrasSystem.permits.add(new Permit(1, app, LocalDate.parse("2025-12-10")));

        boolean actualEmpty = NawrasSystem.permits.isEmpty();

        assertFalse(actualEmpty);
    }
}
