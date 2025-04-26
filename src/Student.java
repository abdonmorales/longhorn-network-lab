import java.util.*;

/**
 * This class includes the student's metadata variables that will be set during processing of the
 * text files. These variables will be useful when building out our {@code UniversityStudent} class.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public abstract class Student {
    /** The name of the student. */
    protected String name;
    /** The age of the student. */
    protected int age;
    /** The gender of the student. */
    protected String gender;
    /** The student's academic year */
    protected int year;
    /** The student's major */
    protected String major;
    /** The student's grade point average */
    protected double gpa;
    /** The student's list of roommate preferences */
    protected List<String> roommatePreferences;
    /** The student's list of previous internship(s) */
    protected List<String> previousInternships;

    /**
     * An abstract method to calculate the connection strength using the ... algorithm
     *
     * @param other Student B.
     * @return connectionStrength.
     */
    public abstract int calculateConnectionStrength(Student other);
}
