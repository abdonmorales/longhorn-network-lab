import java.util.*;

/**
 * This class will be used to create a {@code UniversityStudent} object in other class when
 * processing the data of the text file(s).
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public class UniversityStudent extends Student {
    /** Store the assigned roommate. */
    private UniversityStudent roommate;
    /** The ArrayList of friends, in the form of Strings which are their names. */
    private ArrayList<String> friends;
    /** This is a map that stores the chat histories between this student and other students. */
    private Map<String, List<String>> chatHistories;

    /**
     * A method that calculates a student's connection strength between student A and Student B.
     *
     * @param other student B
     * @return connectionStrenght
     */
    @Override
    public int calculateConnectionStrength(Student other) {
        int connectionStrength = 0;
        
        // Check if the other student is an instance of UniversityStudent
        if (other instanceof UniversityStudent) {
            UniversityStudent otherStudent = (UniversityStudent) other;

            // Check if other student is the assigned roommate, add +4 to the strength.
            if (this.roommate != null && this.roommate.equals(otherStudent)) {
                connectionStrength += 4;
            }

            // For each shared internship, add +3 to the strength.
            for (String internship : this.previousInternships) {
                if (otherStudent.previousInternships.contains(internship)) {
                    connectionStrength += 3;
                }
            }

            // If both share the same major, add +2 to the strength.
            if (this.major.equals(otherStudent.major)) {
                connectionStrength += 2;
            }

            // If both share the same year, add +1 to the strength.
            if (this.year == otherStudent.year) {
                connectionStrength += 1;
            }
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
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.year = year;
        this.major = major;
        this.gpa = gpa;
        this.roommatePreferences = roommatePrefs;
        this.previousInternships = previousInterns;
        this.roommate = null;
        this.friends = new ArrayList<>();
        this.chatHistories = new HashMap<>();
    }

    /**
     * This method sets/assigns the roommate for this student
     *
     * @param roommate, the new roommate for this student.
     */
    public void setRoommate(UniversityStudent roommate) {
        this.roommate = roommate;
    }

    /**
     * This method gets the roommate of this student
     * @return roommate
     */
    public UniversityStudent getRoommate() {
        return roommate;
    }

    /**
     * This method adds a friend to the list of friends for this student
     * @param friend, the name of the friend to be added
     */
    public void addFriend(String friend) {
        this.friends.add(friend);
    }

    /**
     * This method gets the list of friends for this student
     * @return friends, the ArrayList of friends for this student
     */
    public ArrayList<String> getFriends() {
        return friends;
    }

    /**
     * This method removes a friend from the list of friends for this student.
     * <p><b>NOTE: This method is not called, but I implemented it if I ever 
     * want to expand the functionality of the program in the future.</b></p>
     * @param friend the name of the friend to be removed
     */
    public void removeFriend(String friend) {
        this.friends.remove(friend);
    }

    /**
     * This method gets the chat history for this student and the other student they are chatting
     * with.
     * @param otherChatter, the name of the other student
     * @return chatHistory, the list of chat messages between this student and the other student
     */
    public List<String> getChatHistory(String otherChatter) {
        return chatHistories.computeIfAbsent(otherChatter, k -> new ArrayList<>());
    }

    /**
     * This method adds a chat message to the chat history for this student and the other student
     * they are chatting with.
     * @param otherChatter, the name of the other student
     * @param message, the message to be added to the chat history
     */
    public void addChatMessage(String otherChatter, String message) {
        getChatHistory(otherChatter).add(message);
    }

    /**
     * This method gets the chat histories for this student and other students that they have
     * chatted with.
     * <p><b>NOTE: This method is not called, but I implemented it if I ever
     * want to expand the functionality of the program in the future.</b></p>
     * @return chatHistories, the map of chat histories for this student
     */
    public Map<String, List<String>> getChatHistories() {
        return chatHistories;
    }
    /**
     * Implements and overrides the toString method from the Object class. In our case, we modify
     * the toString in our own object for the purpose to display the data of the object rather
     * than the object's memory address
     *
     * @return returns the String representation of the object {@code UniversityStudent}.
     */
    @Override
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

