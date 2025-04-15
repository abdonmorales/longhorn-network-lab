import java.util.*;

/**
 * This class creates the relationships between students as a weighted graph. Each student in a node
 * , and the connection strength between two students is an edge with a corresponding weight. In
 * addition, this component is critical for both pod formation and referral path finding.
 *
 * @author <a href="mailto:abdonmorales@my.utexas.edu">Abdon Morales</a>, am226923
 */
public class StudentGraph {

    /**
     * This class represents an edge in the graph. Each edge connects two students and has a weight
     * representing the connection strength between them.
     * 
     * @author Abdon Morales, am226923, <a href="mailto:abdonmorales@my.utexas.edu">abdonmorales@my.utexas.edu</a>
     */
    public static class Edge {
        /** The student that this edge connects to (the neighboring node in the graph) */
        public UniversityStudent neighbor;

        /** The weight of the edge */
        public int weight;

        /**
         * This constructor method creates an edge between two students.
         * @param neighbor the neighbor student.
         * @param weight the weight of the edge.
         */
        public Edge(UniversityStudent neighbor, int weight) {
            this.neighbor = neighbor;
            this.weight = weight;
        }

        /**
         * This method returns a string representation of the edge.
         */
        @Override
        public String toString() {

            // Might appear on the final exam.
            return "(" + neighbor.name + ", " + weight + ")";
        }
    }

    /** 
     * The adjacency list representing the graph structure.
     * Maps each student to a list of edges connecting to their neighboring students.
     */
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
        for (UniversityStudent student : getAllNodes()) {
            System.out.println(student.name + " -> " + adjacencyList.get(student));
        }
    }

    /**
     * This method's purpose is to add a weighted edge between two students 
     * and to ensure the graph is undirected by adding the edge in both directions.
     *
     * @param studentA is a {@code UniversityStudent} object and acts as a node between a weighted
     *                 edge and another node (Student B).
     * @param studentB is a {@code UniversityStudent} object and acts as a node between a weighted
     *                 edge and another node (Student A).
     * @param weight Is the weight of the edge between two nodes/students.
     */
    private void addEdge(UniversityStudent studentA, UniversityStudent studentB, int weight) {
        adjacencyList.get(studentA).add(new Edge(studentB, weight));
        adjacencyList.get(studentB).add(new Edge(studentA, weight));
    }

    /**
     * The purpose of this method is for it to be useful for traversal 
     * algorithms like Prim's Algorithm and Dijkstra's Algorithm.
     *
     * @param student the student we want to find the neighbors of.
     * @return Return the {@code List<Edge>} of neighbors of the student.
     */
    public List<Edge> getNeighbors(UniversityStudent student) {
        return adjacencyList.get(student);
    }

    /**
     * The purpose of this method is for it to be useful for initializing 
     * traversal algorithms.
     *
     * @return Return all of the nodes of the graph.
     */
    public Set<UniversityStudent> getAllNodes() {
        return adjacencyList.keySet();
    }
}
