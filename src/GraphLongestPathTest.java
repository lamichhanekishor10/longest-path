import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/*
 * Writing tests to check the longest path logic for different cases.
 * Trying both normal DAGs and invalid graphs to see how it behaves.
 */
public class GraphLongestPathTest {

    private Node n1, n2, n3, n4, n5;
    private List<Node> graph;

    /*
     * Setting up a simple DAG before each test.
     * Using nodes with edges to build a small structure.
     *
     * Layout:
     *  1 -> 2 -> 3
     *   \     \
     *    4 ->  5
     */
    @BeforeEach
    void setup() {
        n1 = new Node(1);
        n2 = new Node(2);
        n3 = new Node(3);
        n4 = new Node(4);
        n5 = new Node(5);

        n1.outgoing.add(new Link(n1, n2));
        n1.outgoing.add(new Link(n1, n4));
        n2.outgoing.add(new Link(n2, n3));
        n4.outgoing.add(new Link(n4, n5));
        n3.outgoing.add(new Link(n3, n5));

        graph = Arrays.asList(n1, n2, n3, n4, n5);
        GraphLongestPath.reset(); // Clearing cache for fresh start
    }

    /*
     * Checking if the program is giving correct longest paths for each node.
     */
    @Test
    void testLongestPathForAllNodes() {
        assertEquals(3, GraphLongestPath.computeLongestPath(n1)); // 1 -> 2 -> 3 -> 5
        assertEquals(2, GraphLongestPath.computeLongestPath(n2)); // 2 -> 3 -> 5
        assertEquals(1, GraphLongestPath.computeLongestPath(n3)); // 3 -> 5
        assertEquals(1, GraphLongestPath.computeLongestPath(n4)); // 4 -> 5
        assertEquals(0, GraphLongestPath.computeLongestPath(n5)); // no outgoing edges
    }

    /*
     * Testing a node that has no outgoing edges to confirm path length = 0.
     */
    @Test
    void testNodeWithNoOutgoingEdges() {
        Node single = new Node(99);
        assertEquals(0, GraphLongestPath.computeLongestPath(single));
    }

    /*
     * Adding a cycle to the graph to check if it throws the cycle exception.
     */
    @Test
    void testCycleDetection() {
        // Adding a cycle: 3 -> 1
        n3.outgoing.add(new Link(n3, n1));
        assertThrows(GraphCycleException.class, () -> GraphLongestPath.computeLongestPath(n1));
    }

    /*
     * Passing a null node to confirm it throws an illegal argument error.
     */
    @Test
    void testNullInput() {
        assertThrows(IllegalArgumentException.class, () -> GraphLongestPath.computeLongestPath(null));
    }

    /*
     * Checking if memoization is working by calling the same node twice.
     * The second call should be quick since it uses cached data.
     */
    @Test
    void testMemoization() {
        long first = GraphLongestPath.computeLongestPath(n1);
        long second = GraphLongestPath.computeLongestPath(n1);
        assertEquals(first, second); // Should be same as it’s cached
    }
}
