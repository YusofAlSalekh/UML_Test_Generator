package at3;

import org.jgrapht.Graph;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;

import static at3.GraphSamples.aDAG2;
import static at3.GraphSamples.aDAGWithCycle;
import static at3.GraphUtils.*;
import static org.junit.jupiter.api.Assertions.*;

class GraphUtilsTest {
    @Test
    public void findFirstVertex() {
        assertEquals(0, findStartVertex(aDAG2()));
    }

    @Test
    public void findFirstVertexInCopy() {
        assertEquals(0, findStartVertex(copyGraph(aDAG2())));
    }

    @Test
    public void findFirstVertexInReversedGraph() {
        assertEquals(5, findStartVertex(reverseGraph(aDAG2())));
    }

    @Test
    public void edgeGraph() {
        Graph<Pair<Integer, Integer>, DefaultEdge> g = buildEdgeGraph(aDAGWithCycle());
        assertEquals(7, g.vertexSet().size());
        assertEquals(7, g.edgeSet().size());
    }
}
