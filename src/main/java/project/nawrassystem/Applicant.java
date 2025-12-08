package project.nawrassystem;

public class Applicant {
    private String name;
    private String id;

    public Applicant(String name, String id) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    @Override
    public String toString() {
        return name + "," + id;
    }

    public static Applicant fromString(String data) {
        String[] parts = data.split(",");
        return new Applicant(parts[0], parts[1]);
    }
}
