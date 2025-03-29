import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * This class creates the relationships between students as a weighted graph. Each student in a node
 * , and the connection strength between two students is an edge with a corresponding weight. In
 * addition, this component is critical for both pod formation and referral path finding.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public class StudentGraph {
    // Implement later next week. Before the hard deadline for Stage 1.
    private Map<UniversityStudent, Map<UniversityStudent, Integer>> adjacencyList;

    /**
     * This is the constructor method for the StudentGraph class. This initializes the
     * graph structure and add all students to the graph
     * 
     * @param students, the {@code ArrayList<>()} of (University) students.
     */
    public StudentGraph(List<UniversityStudent> students) {
        adjacencyList = new HashMap<>();
        for (UniversityStudent student : students) {
            adjacencyList.put(student, new HashMap<>());
        }
    }

    /**
     * This method prints out the student graph.
     */
    public void displayGraph() {}

    /**
     * This method's purpose is to add a weighted edge between two students 
     * and to ensure the graph is undirected by adding the edge in both directions.
     */
    public void addEdge() {}

    /**
     * The purpose of this method is for it to be useful for traversal 
     * algorithms like Prim's Algorithm and Dijkstra's Algorithm.
     */
    public void getNeighbors() {}

    /**
     * The purpose of this method is for it to be useful for initializing 
     * traversal algorithms.
     */
    public void getAllNodes() {}
}
