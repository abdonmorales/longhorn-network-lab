import java.util.*;

/**
 * This class creates the relationships between students as a weighted graph. Each student in a node
 * , and the connection strength between two students is an edge with a corresponding weight. In
 * addition, this component is critical for both pod formation and referral path finding.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public class StudentGraph {

    public static class Edge {
        public UniversityStudent neighbor;
        public int weight;

        /**
         * 
         */
        public Edge(UniversityStudent neighbor, int weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }

        /**
         * 
         */
        @Override
        public String toString() {

            // Might appear on the final exam.
            return "(" + neighbor.name + ", " + weight + ")";
        }
    }

    // Implement later next week. Before the hard deadline for Stage 1.
    private Map<UniversityStudent, List<Edge>> adjacencyList;

    /**
     * This is the constructor method for the StudentGraph class. This initializes the
     * graph structure and add all students to the graph
     * 
     * @param students, the {@code ArrayList<>()} of (University) students.
     */
    public StudentGraph(List<UniversityStudent> students) {
        adjacencyList = new HashMap<>();

        // Initialize the nodes of the graph.
        for (UniversityStudent student : students) {
            adjacencyList.put(student, new LinkedList<>());
        }

        // Create the edges between every node [every pair of students] of the graph.
        for (int i = 0; i < students.size(); i++) {
            for (int j = i + 1; j < students.size(); j++) {
                UniversityStudent studentA = students.get(i);
                UniversityStudent studentB = students.get(j);

                int weight = studentA.calculateConnectionStrength(studentB);
                
                // If the weight is greater than 0, add the edge to the graph.
                if (weight > 0) {
                    addEdge(studentA, studentB, weight);
                }
            }
        }
    }

    /**
     * This method prints out the student graph.
     */
    public void displayGraph() {
        System.out.println("\nStudent Graph:");
        
        // Iterate through each student and their edges.
        for (UniversityStudent student : adjacencyList.keySet()) {
            System.out.println(student.name + " -> " + adjacencyList.get(student));
        }
    }

    /**
     * This method's purpose is to add a weighted edge between two students 
     * and to ensure the graph is undirected by adding the edge in both directions.
     */
    private void addEdge(UniversityStudent studentA, UniversityStudent studentB, int weight) {
        adjacencyList.get(studentA).add(new Edge(studentB, weight));
        adjacencyList.get(studentB).add(new Edge(studentA, weight));
    }

    /**
     * The purpose of this method is for it to be useful for traversal 
     * algorithms like Prim's Algorithm and Dijkstra's Algorithm.
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return adjacencyList.get(student);
    }

    /**
     * The purpose of this method is for it to be useful for initializing 
     * traversal algorithms.
     */
    public Set<UniversityStudent> getAllNodes() {
        return adjacencyList.keySet();
    }
}
