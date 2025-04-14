import java.util.*;

/**
 * This class implements the Gale-Shapely algorithm for sorting.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923w
 */
public class GaleShapley {

    /**
     * In this method, we use the Gale-Shapely algorithm to sort the students and assign them
     * roommates.
     * @param students the list of students.
     */
    public static void assignRoommates(List<UniversityStudent> students) {
        // Map to hold final student pairings.
        Map<UniversityStudent, UniversityStudent> studentPair = new HashMap<>();

        // Keep track of the index of the next proposed roommate for each student.
        Map<UniversityStudent, Integer> indexProposed = new HashMap<>();

        // A Hashmap to look for students by their names in Strings.
        Map<String, UniversityStudent> studentLookupMap = new HashMap<>();
        //Build the student lookup map
        for (UniversityStudent student : students) {
            studentLookupMap.put(student.name, student);
            indexProposed.put(student, 0);
        }

        // Queue of student students who are not yet paired.
        Queue<UniversityStudent> unPairedStudent = new LinkedList<>(students);

        // Gale-Shapley algorithm applied on student pairing based on roommate preferences.
        while (!unPairedStudent.isEmpty()) {
            UniversityStudent proposer = unPairedStudent.poll();
            int index = indexProposed.get(proposer);
            if (index >= proposer.roommatePreferences.size()) {
                continue;
            }
            String candidateName = proposer.roommatePreferences.get(index);
            indexProposed.put(proposer, index + 1);
            UniversityStudent candidate = studentLookupMap.get(candidateName);

            /*
             * If the candidate is not found in the lookup map, 
             * continue to the next iteration.
             */
            if (candidate == null) {
                unPairedStudent.add(proposer);
                continue;
            }

            // Gale-Shapely algorithm logic:
            if (!studentPair.containsKey(candidate)) {
                studentPair.put(proposer, candidate);
                studentPair.put(candidate, proposer);

            } else {
                UniversityStudent currentRoommate = studentPair.get(candidate);
                if () {
                    studentPair.remove(candidate);
                    studentPair.remove(currentRoommate);
                    unPairedStudent.add(currentRoommate);

                    studentPair.put(candidate, proposer);
                    studentPair.put(proposer, candidate);
                } else  {
                    unPairedStudent.add(proposer);
                }
            }
        }

        // Print the final roommate assignments.
        printFinalRoomateAssignment(studentPair);
    }

    /**
     * This method prints the final roommate assignments.
     * 
     * @param studentPairFinal the map of final student pairings.
     */
    private static void printFinalRoomateAssignment(Map<UniversityStudent, UniversityStudent> studentPairFinal) {
        System.out.println("Roommate Assignments:");

        // To prevent duplicates we will use a Set<> for printing out the final roommate assignments.
        Set<UniversityStudent> printed = new HashSet<>();
        for (UniversityStudent student : studentPairFinal.keySet()) {
            if (!printed.contains(student)) {
                UniversityStudent partner = studentPairFinal.get(student);
                System.out.println(student.name + "is roommates with " + partner.name);
                printed.add(student);
                printed.add(partner);
            }
        }

    }
}
