import java.util.*;

/**
 * This class implements the Gale-Shapely algorithm for sorting 
 * and assigning roommate preferences.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
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

        // Queue of student students who are not yet paired and still have preferences to propose.
        Queue<UniversityStudent> unPairedStudent = new LinkedList<>(students);

        for (UniversityStudent student : students) {
            if (student.roommatePreferences.isEmpty()) {
                unPairedStudent.offer(student);
            }
        }

        // Gale-Shapley algorithm applied on student pairing based on roommate preferences.
        while (!unPairedStudent.isEmpty()) {
            UniversityStudent proposer = unPairedStudent.poll();

            // If the proposer already has a roommate, continue to the next iteration.
            if (proposer.getRoommate() != null) {
                continue;
            }

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
                if (indexProposed.get(proposer) < proposer.roommatePreferences.size()) {
                    unPairedStudent.offer(proposer);
                }
                continue;
            }

            // If the candidate does not list the proposer as a preference, reject the proposer.
            if (!candidate.roommatePreferences.contains(proposer.name)) {
                if (indexProposed.get(proposer) < proposer.roommatePreferences.size()) {
                    unPairedStudent.offer(proposer);
                }
                continue;
            }

            // Reference: https://uw-cse442-wi20.github.io/FP-cs-algorithm/
            // Core stable marriage logic:
            // If the candidate is not already paired with someone, pair them with the proposer.
            if (candidate.getRoommate() == null) {
                studentPair.put(proposer, candidate);
                studentPair.put(candidate, proposer);

                proposer.setRoommate(candidate);
                candidate.setRoommate(proposer);
            } else {
                UniversityStudent currentRoommate = candidate.getRoommate();
                int currentRoommateIndex = candidate.roommatePreferences.indexOf(currentRoommate.name);
                int newIndex = candidate.roommatePreferences.indexOf(proposer.name);

                // If the candidate prefers the proposer over their current roommate, swap them.
                if (newIndex < currentRoommateIndex) {
                    studentPair.put(candidate, proposer);
                    studentPair.put(proposer, candidate);

                    studentPair.remove(currentRoommate);
                    unPairedStudent.offer(currentRoommate);
                    currentRoommate.setRoommate(null);

                    proposer.setRoommate(candidate);
                    candidate.setRoommate(proposer);
                } else  {
                    // If the candidate rejects the proposer.
                    if (indexProposed.get(proposer) < proposer.roommatePreferences.size()) {
                        unPairedStudent.offer(proposer);
                    }
                }
            }
        }

        // Print the final roommate assignments. In able it for logging to the terminal.
        //printFinalRoommateAssignment(studentPair);
    }

    /**
     * This method prints the final roommate assignments.
     * 
     * @param studentPairFinal the map of final student pairings.
     */
    private static void printFinalRoommateAssignment(Map<UniversityStudent, UniversityStudent> studentPairFinal) {
        System.out.println("Roommate Pairings (Gale-Shapley):");

        // Iterate through the studentPairFinal map and print the roommate assignments.
        Set<UniversityStudent> printed = new HashSet<>();
        for (UniversityStudent student : studentPairFinal.keySet()) {
            UniversityStudent roommate = studentPairFinal.get(student);
            if (!printed.contains(student) && !printed.contains(roommate)) {
                System.out.println(student.name + " is paired with " + roommate.name);
                printed.add(student);
                printed.add(roommate);
            }
        }

    }
}
