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
public void testModifyPermitUpdated() {
    // Prepare list
    NawrasSystem.permits.clear();
    Permit p = new Permit(1, "Sara", LocalDate.now().plusDays(5)); // Active
    NawrasSystem.permits.add(p);

    // Fake user input: ID and new date
    String input = "1\n2030-01-01\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    // Capture output
    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    // Call method
    NawrasSystem.modifyPermit();

    // Verify output
    String output = out.toString();
    assertTrue(output.contains("Permit updated successfully!"));

}
@Test
public void testModifyPermitExpired() {
    NawrasSystem.permits.clear();
    Permit p = new Permit(2, "Ali", LocalDate.of(2020, 1, 1)); // Expired
    NawrasSystem.permits.add(p);

    String input = "2\n";
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    NawrasSystem.modifyPermit();

    String output = out.toString();
    assertTrue(output.contains("Cannot modify expired permits. View-only in history."));
}
@Test
public void testModifyPermitNotFound() {
    NawrasSystem.permits.clear(); // clear permits

    String input = "99\n"; // ID does not exist yet
    System.setIn(new ByteArrayInputStream(input.getBytes()));

    ByteArrayOutputStream out = new ByteArrayOutputStream();
    System.setOut(new PrintStream(out));

    NawrasSystem.modifyPermit();

    String output = out.toString();
    assertTrue(output.contains("Permit not found."));
}

}