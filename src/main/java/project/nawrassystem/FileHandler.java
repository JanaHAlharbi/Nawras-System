package project.nawrassystem;

import java.io.*;
import java.util.ArrayList;

public class FileHandler {

    private final String fileName;

    public FileHandler(String fileName) {
        this.fileName = fileName;
    }

    public ArrayList<Permit> loadPermits() {
        ArrayList<Permit> permits = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                permits.add(Permit.fromString(line));
            }
        } catch (IOException ignored) {}

        return permits;
    }

    public void savePermits(ArrayList<Permit> permits) {
        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            for (Permit permit : permits) {
                out.println(permit.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving file.");
        }
    }
}
