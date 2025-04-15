import java.util.*;

/**
 * This class attempts to find the shortest path (or strongest connection) to a student who interned
 * at a specific company.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">abdonmorales@my.utexas.edu</a>, am226923
 */
public class ReferralPathFinder {
    /** */
    private StudentGraph graph;

    /**
     * The constructor method of the class.
     * @param graph the graph of students.
     */
    public ReferralPathFinder(StudentGraph graph) {
        // Constructor method
        this.graph = graph;
    }

    /**
     * This method attempts to find the shortest referral path to student B, who interned at some
     * company X.
     *
     * @param start the student A we want to connect to Student B
     * @param targetCompany the company that Student A wants to intern.
     * @return {@code List<UniversityStudent>}
     */
    public List<UniversityStudent> findReferralPath(UniversityStudent start, String targetCompany) {
        Map<UniversityStudent, Double> distances = new HashMap<>();
        Map<UniversityStudent, UniversityStudent> previous = new HashMap<>();
        Set<UniversityStudent> visited = new HashSet<>();

        //
        for (UniversityStudent student : graph.getAllNodes()) {
            distances.put(student, Double.POSITIVE_INFINITY);
            previous.put(student, null);
        }

        distances.put(start, 0.0);

        PriorityQueue<UniversityStudent> pq = new PriorityQueue<>(Comparator.comparingDouble(distances::get));
        pq.add(start);
        
        //
        while (!pq.isEmpty()) {
            UniversityStudent student = pq.poll();

            //
            if (visited.contains(student)) {
                continue;
            }
            visited.add(student);

            for (String intership : student.previousInternships) {

                //
                if (intership.equalsIgnoreCase(targetCompany)) {
                    List<UniversityStudent> path = new ArrayList<>();
                    UniversityStudent current = student;

                    //
                    while (current != null) {
                        path.add(student);
                        current = previous.get(current);
                    }
                    
                    Collections.reverse(path);
                    return path;
                }
            }

            //
            for (StudentGraph.Edge edge : graph.getNeighbors(student)) {
                UniversityStudent neighbor  = edge.neighbor;
                
                //
                if (visited.contains(neighbor)) {continue;}

                double newDistance = distances.get(student) + (1.0 / edge.weight);
                //
                if (newDistance < distances.get(neighbor)) {
                    distances.put(neighbor, newDistance);
                    previous.put(neighbor, student);
                    pq.add(neighbor);
                }
            }
        }
        return new ArrayList<>();
    }
}
