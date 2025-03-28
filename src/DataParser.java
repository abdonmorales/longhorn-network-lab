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
     * @throws IOException throws an IOException when the file being loaded to the scanner is missing.
     */
    public static List<UniversityStudent> parseStudents(String filename) throws IOException {
        Scanner sc = new Scanner(new File(filename));
        // Check if the file is empty, before doing the actual processing.
        // if (!sc.hasNextLine()) {}
        List<UniversityStudent> students = new ArrayList<>();

        // Setting up expected student metadata.
        String name = null;
        int age = -1;
        String gender = "";
        int year = -1;
        String major = "";
        double gpa = -1.0;
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

            if(line.startsWith("Student:")) {
                // Do a prelim check and then reset. To add create a new student.
                if (name != null && !name.isEmpty()) {
                    studentValidation(name, age, gender, year, major, gpa, roommatePreferences,
                            previousInternships);
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
            // Check if the length of the tokens array that we gather are less than 2
            if (split.length < 2) {
                sc.close();
                String field = line.split(" ")[0].trim();
                throw new IllegalArgumentException("Incorrect format in line: '" + line +
                        "'. Expected format '" + field + ": <value>'.");
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
                        throw new NumberFormatException("Invalid number format for age: '" + data +
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
                        throw new NumberFormatException("Invalid number format for year: '" + data +
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
                        throw new NumberFormatException("Invalid number format for GPA: '" + data +
                                "' in student entry for " + name + ".");
                    }
                    break;
                case "RoommatePreferences":
                    String[] RoommatePrefs = data.split(",");
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
            // TODO: Ask about passing through scanner to gracefully exit.
            studentValidation(name, age, gender, year, major, gpa, roommatePreferences,
                    previousInternships);
            UniversityStudent student = new UniversityStudent(name, age, gender, year, major, gpa, roommatePreferences,
                    previousInternships);
            students.add(student);
        }
        sc.close();
        return students;
    }

    /**
     *
     * The purpose of the studentValidation method is to make sure there is not
     * a missing field by looking through the data after processing.
     *
     * @param name, the student name
     * @param age, the age of the student
     * @param gender, the gender of the student
     * @param year, the year (classification) of the student
     * @param major, the student's major
     * @param gpa, the student's GPA
     * @param roommatePreferences, the List of String(s) containing the student's roommate preferences.
     * @param previousInternships, the List of String(s) containing the student's previous internships.
     */
    private static void studentValidation(String name, int age, String gender, int year, String major, double gpa,
                                          List<String> roommatePreferences, List<String> previousInternships) {
        // Check name is an empty field
        if (name == null || name.isEmpty()) {
            throw new IllegalArgumentException("Missing required field 'Name' in student entry for " + name + ".");
        }

        // Check if age is an empty field by presetting the age as -1.
        // EDGE: Case, what if their age is actually zero.
        if (age <= -1) {
            throw new IllegalArgumentException("Missing required field 'Age' in student entry for " + name + ".");
        }

        // Check if gender is an empty field
        if (gender == null || gender.isEmpty()) {
            throw new IllegalArgumentException("Missing required field 'Gender' in student entry for " + name + ".");
        }

        // Check if year is an empty field by using the preset of -1. I might change this to zero as it makes more
        // logical sense.
        if (year <= -1) {
            throw new IllegalArgumentException("Missing required field 'Year' in student entry for " + name + ".");
        }

        // Check if major is an empty field.
        if (major == null || major.isEmpty()) {
            throw new IllegalArgumentException("Missing required field 'Major' in student entry for " + name + ".");
        }

        // Check if the gpa is an empty field (set it as -1.0 for easier detection).
        if (gpa <= -1.0) {
            throw new IllegalArgumentException("Missing required field 'GPA' in student entry for " + name + ".");
        }

        // Check if ArrayList<>() roommatePreferences is empty
        if (roommatePreferences == null || roommatePreferences.isEmpty()) {
            throw new IllegalArgumentException("Missing required field 'RoommatePreferences' in student entry for "
                    + name + ".");
        }

        // Check if ArrayList<>() previousInternships is empty
        if (previousInternships == null || previousInternships.isEmpty()) {
            throw new IllegalArgumentException("Missing required field 'previousInternships' in student entry for "
                    + name + ".");
        }
    }

}