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
        
        // Check if the other student is an instance of UniversityStudent.
        if(!(other instanceof UniversityStudent)) {return connectionStrength;}
        
        UniversityStudent otherStudent = (UniversityStudent) other;

        // Add +1 if they share the same major.
        if (this.major != null && this.major.equals(otherStudent.major)) {
            connectionStrength += 1;
        }

        // Add +1 if they share the same age.
        if (this.age == otherStudent.age) {
            connectionStrength += 1;
        }
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

    /**
     * Implements and overrides the toString method from the Object class. In our case, we modify
     * the toString in our own object for the purpose to display the data of the object rather
     * than the object's memory address
     *
     * @return returns the String representation of the object {@code UniversityStudent}.
     */
    public String toString() {
        String out = this.getClass().getSimpleName() + "{";
        out += "name='" + name + "', age=" + age + ", gender='" + gender + "', year=" + year
                + ", major='" + major + "', GPA=" + gpa;
        // Check if ArrayList are empty, if they are empty display "[None]"
        if (roommatePreferences.contains("None")) {
            out += ", roommatePreferences=[]";
        } else  {
            out += ", roommatePreferences=" + roommatePreferences;
        }

        // Check if ArrayList are empty, if they are empty display "[None]"
        if (previousInternships.contains("None")) {
            out += ", previousInternships=[]}";
        } else  {
            out += ", previousInternships=" + previousInternships + "}";
        }
        return out;
    }
}

