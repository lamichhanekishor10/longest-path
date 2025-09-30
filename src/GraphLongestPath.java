import java.util.*;

/*
 * Representing a node in the graph for keeping an ID and list of outgoing edges.
 * Using this to build connections and navigate through the graph.
 */
class Node {
    long id;
    List<Link> outgoing;

    Node(long id) {
        this.id = id;
        this.outgoing = new ArrayList<>();
    }

    @Override
    public String toString() {
        return String.valueOf(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Node)) return false;
        return id == ((Node) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}

/*
 * Representing a one-way connection between two nodes.
 * Using this to link nodes and form directed edges.
 */
class Link {
    Node from;
    Node to;

    Link(Node from, Node to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public String toString() {
        return from.id + " -> " + to.id;
    }
}

/*
 * Creating a custom exception for handling cycles.
 * This is helpful for stopping the algorithm when the graph is not acyclic.
 */
class GraphCycleException extends RuntimeException {
    GraphCycleException(String message) {
        super(message);
    }
}

/*
 * Building a program for finding the longest path in a DAG by using DFS and memoization.
 * Storing already calculated results to avoid repeating work.
 * Also checking for cycles so that we can ensure the graph is valid for this problem.
 */
public class GraphLongestPath {

    // Keeping a cache to remember results for each node, so that we don’t compute twice.
    private static final Map<Node, Long> memo = new HashMap<>();

    /*
     * Finding the longest path starting from a given node.
     * Calling a recursive helper for walking through the graph.
     */
    public static long computeLongestPath(Node start) {
        if (start == null) {
            throw new IllegalArgumentException("Start node cannot be null.");
        }
        return dfs(start, new HashSet<>());
    }

    /*
     * Using DFS to explore all paths from the current node.
     * Keeping track of nodes currently being visited to detect cycles.
     * Returning the maximum path length found.
     */
    private static long dfs(Node current, Set<Node> activePath) {
        // Checking if this node is already in the current path to catch cycles.
        if (activePath.contains(current)) {
            throw new GraphCycleException("Cycle found at node " + current.id);
        }

        // Returning cached result if already processed earlier.
        if (memo.containsKey(current)) {
            return memo.get(current);
        }

        // Marking this node as currently being visited.
        activePath.add(current);

        // Starting with 0 as default path length when no outgoing edges exist.
        long maxLength = 0;

        // Going through all outgoing edges to find the longest possible route.
        for (Link edge : current.outgoing) {
            Node next = edge.to;
            if (next != null) {
                // Adding 1 for the edge and continuing the search from the neighbor.
                long candidate = 1 + dfs(next, activePath);
                // Updating the max length found so far.
                maxLength = Math.max(maxLength, candidate);
            }
        }

        // Removing the node from the active path when backtracking.
        activePath.remove(current);

        // Saving the result for future lookups.
        memo.put(current, maxLength);
        return maxLength;
    }

    /*
     * Clearing the cache before running on another graph.
     * Doing this to avoid mixing results from previous runs.
     */
    public static void reset() {
        memo.clear();
    }

    /*
     * Building a small sample DAG for testing the algorithm.
     * Adding edges between nodes to create different paths.
     *
     * Graph:
     *  10 -> 20 -> 30
     *   \     \
     *   40     50 -> 60
     */
    private static List<Node> buildSampleGraph() {
        Node n10 = new Node(10);
        Node n20 = new Node(20);
        Node n30 = new Node(30);
        Node n40 = new Node(40);
        Node n50 = new Node(50);
        Node n60 = new Node(60);

        // Adding edges to build the DAG.
        n10.outgoing.add(new Link(n10, n20));
        n10.outgoing.add(new Link(n10, n40));
        n20.outgoing.add(new Link(n20, n30));
        n20.outgoing.add(new Link(n20, n50));
        n50.outgoing.add(new Link(n50, n60));

        return Arrays.asList(n10, n20, n30, n40, n50, n60);
    }

    /*
     * Printing the longest path length for each node in the graph.
     * Doing this to easily verify results for all starting points.
     */
    private static void printAllPaths(List<Node> nodes) {
        reset(); // Clearing cache before running
        for (Node n : nodes) {
            try {
                long length = computeLongestPath(n);
                System.out.println("Longest path from node " + n.id + ": " + length);
            } catch (GraphCycleException e) {
                System.err.println("Error: " + e.getMessage());
            }
        }
    }

    /*
     * Main method for running the program.
     * Building a sample graph and computing longest paths.
     * Accepting a node ID as an optional argument to focus on one node.
     * Running without arguments to show results for all nodes.
     */
    public static void main(String[] args) {
        // Creating a sample graph for testing
        List<Node> graph = buildSampleGraph();

        // Checking if the user passed a specific node ID
        if (args.length > 0) {
            try {
                // Parsing the node ID from arguments
                long id = Long.parseLong(args[0]);

                // Finding the matching node in the graph
                Node start = graph.stream()
                        .filter(n -> n.id == id)
                        .findFirst()
                        .orElse(null);

                // Stopping if the node is not found
                if (start == null) {
                    System.err.println("No node found with id " + id);
                    return;
                }

                // Resetting cache before calculation
                reset();

                // Calculating the longest path from the given node
                long result = computeLongestPath(start);
                System.out.println("Longest path from node " + id + ": " + result);

            } catch (NumberFormatException e) {
                System.err.println("Please provide a valid numeric id.");
            }
        } else {
            // Running for all nodes if no argument provided
            printAllPaths(graph);
        }
    }
}
