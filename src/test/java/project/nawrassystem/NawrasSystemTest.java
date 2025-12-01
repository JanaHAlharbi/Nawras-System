package project.nawrassystem;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class NawrasSystemTest {

    private static final String FILE_NAME = "permits.txt";

    @Before
    public void setUp() {
        NawrasSystem.permits = new ArrayList<>();
    }

    @After
    public void tearDown() {
        File file = new File(FILE_NAME);
        if (file.exists()) {
            file.delete();
        }
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
}