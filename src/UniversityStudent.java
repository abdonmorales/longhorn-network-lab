import java.util.*;

/**
 * This class will be used to create a {@code UniversityStudent} object in other class when
 * processing the data of the text file(s).
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public class UniversityStudent extends Student {
    /**
     * A method that calculates a student's connection strength between student A and Student B.
     *
     * @param other student B
     * @return connectionStrenght
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        int connectionStrength = 0;
        return connectionStrength;
    }

    /**
     *
     * This is the constructor for the {@code UniversityStudent}.class
     *
     * @param name, the name of the student
     * @param age, the age of the student
     * @param gender, the gender of the student
     * @param year, the year/grade of the student
     * @param major, the major of the student
     * @param gpa, the grade point average of the student
     * @param roommatePrefs, the roommate preferences of the student
     * @param previousInterns, the previous internship(s) of the students.
     */
    public UniversityStudent(String name, int age, String gender, int year, String major, double gpa,
                             List<String> roommatePrefs, List<String> previousInterns) {
        // Is there a more proper way to simplify this? We need to reduce overhead.
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePrefs;
        this.previousInternships = previousInterns;
    }

    // TODO: Implement additional methods in the later stages if needed.
    public String toString() {
        String out = this.getClass().getSimpleName() + "{";
        out += "name='" + name + "', age=" + age + ", gender='" + gender + "', year=" + year
                + ", major='" + major + "', GPA=" + gpa +
                ", roommatePreferences=" + roommatePreferences.toString() + ", previousInternships="
                + previousInternships.toString() + "}";
        return out;
    }
}

