import java.util.*;

/**
 * This class includes the student's metadata variables that will be set during processing of the
 * text files. These variables will be useful when building out our {@code UniversityStudent} class.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public abstract class Student {
    protected String name;
    protected int age;
    protected String gender;
    protected int year;
    protected String major;
    protected double gpa;
    protected List<String> roommatePreferences;
    protected List<String> previousInternships;

    /**
     * An abstract method to calculate the connection strength using the ... algorithm
     *
     * @param other Student B.
     * @return connectionStrength.
     */
    public abstract int calculateConnectionStrength(Student other);
}
