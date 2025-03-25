import java.io.*;
import java.util.*;

/**
 * This class implements a parser to read data from text files containing student metadata
 * such as name, year, gender, GPA, major, roommate preference, and if any, previous internships.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public class DataParser {

    /**
     * This method parses the student metadata from a textfile into an arrayList.
     *
     * @param filename the filename of the text file containing the student metadata.
     * @return {@code List<UniversityStudent>}
     * @throws IOException
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        Scanner sc = new Scanner(new File(filename));
        // Check if the file is empty, before doing the actual processing.
        // if (!sc.hasNextLine()) {}
        List<UniversityStudent> students = new ArrayList<>();

        // Setting up expected student metadata.
        String name = null;
        int age = 0;
        String gender = "";
        int year = 0;
        String major = "";
        double gpa = 0.0;
        List<String> roommatePreferences = new ArrayList<>();
        List<String> previousInternships = new ArrayList<>();

        // Going line by line of the textfile, for the information that I need.
        while (sc.hasNextLine()) {
            String line = sc.nextLine();

            // College student metadata
            // Implement the reading line mechanism here.
            if (line.isEmpty()) {
                continue;
            }
            if(line.startsWith("Student: ")) {
                // Do a prelim check and then reset. To add create a new student.
                if (name != null && !name.isEmpty()) {
                    UniversityStudent student = new UniversityStudent(name, age, gender, year, major, gpa,
                            roommatePreferences, previousInternships);
                    students.add(student);

                    // This is the reset needed to create the next student if the file contains multiple students.
                    name = null;
                    age = 0;
                    gender = "";
                    year = 0;
                    major = "";
                    gpa = 0.0;
                    roommatePreferences = new ArrayList<>();
                    previousInternships = new ArrayList<>();
                }
                continue;
            }
            String[] split = line.split(":", 2);
            if (split.length < 2) {
                sc.close();
                throw new IllegalArgumentException("Parsing error: Incorrect format in line: '" + line + "'. " +
                        "Expected format 'Name: <value>'.");
            }

            String category = split[0].trim();
            String data = split[1].trim();

            // Add the data by check through each category. Error handling will be implemented later.
            switch (category) {
                case "Name":
                    name = data;
                    break;
                case "Age":
                    try {
                        age = Integer.parseInt(data);
                    } catch (NumberFormatException e) {
                        sc.close();
                        System.out.println("Number format error: Invalid number format for age: '" + data +
                                "' in student entry for " + name + ".");
                    }
                    break;
                case "Gender":
                    gender = data;
                    break;
                case "Year":
                    try {
                        year = Integer.parseInt(data);
                    } catch (NumberFormatException e) {
                        sc.close();
                        System.out.println("Number format error: Invalid number format for year: '" + data +
                                "' in student entry for " + name + ".");
                    }
                    break;
                case "Major":
                    major = data;
                    break;
                case "GPA":
                    try {
                        gpa = Double.parseDouble(data);
                    } catch (NumberFormatException e) {
                        sc.close();
                        System.out.println("Number format error: Invalid number format for GPA: '" + data +
                                "' in student entry for " + name + ".");
                    }
                    break;
                case "RoommatePreferences":
                    String RoommatePrefs[] = data.split(",");
                    // For fun.
                    for (String mate: RoommatePrefs) {
                        roommatePreferences.add(mate.trim());
                    }
                    break;
                case "PreviousInternships":
                    String[] internships = data.split(",");
                    for (String interned: internships) {
                        previousInternships.add(interned.trim());
                    }
                    break;
                default:
                    break;
            }
        }

        // Do a final check that we have the last student.
        if (name != null && !name.isEmpty()) {
            UniversityStudent student = new UniversityStudent(name, age, gender, year, major, gpa, roommatePreferences,
                    previousInternships);
            students.add(student);
        }
        sc.close();
        return students;
    }

}
