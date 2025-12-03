package project.nawrassystem;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class NawrasSystemTest {

    private static final String FILE_NAME = "permits.txt";

    @org.junit.jupiter.api.BeforeAll
    public static void setUpClass() throws Exception {
    }

    @org.junit.jupiter.api.AfterAll
    public static void tearDownClass() throws Exception {
    }

    @org.junit.jupiter.api.BeforeEach
    public void setUp() throws Exception {
    }

    @org.junit.jupiter.api.AfterEach
    public void tearDown() throws Exception {
    }


    @Test
    public void testRequestPermit() {
        // 1.Preparation
        String inputs = "Jana\n2025-12-30\n";
        ByteArrayInputStream in = new ByteArrayInputStream(inputs.getBytes());
        NawrasSystem.scanner = new Scanner(in);

        // 2.Calling
        NawrasSystem.requestPermit();

        // 3.Assertion
        ArrayList<Permit> list = NawrasSystem.permits;
        assertEquals(1, list.size());
        
        Permit p = list.get(0);
        assertEquals("Jana", p.getApplicantName());
        assertNotNull(p);
    }

    @Test
    public void testSavePermits() throws Exception {
        // 1.Setup
        ArrayList<Permit> list = NawrasSystem.permits;
        list.add(new Permit(100, "SaverTest", LocalDate.of(2025, 10, 10)));

        // 2.Calling
        NawrasSystem.savePermits();

        // 3.Assertion
        File file = new File(FILE_NAME);
        assertTrue(file.exists()); 
        
        String content = new String(Files.readAllBytes(file.toPath()));
        assertTrue(content.contains("SaverTest"));
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