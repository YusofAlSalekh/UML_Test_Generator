package at3;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;
import org.testng.annotations.Test;

import java.util.List;

import static at3.GraphSamples.aDAGWithCycle2;
import static at3.GraphUtils.removeBackEdges;
import static org.junit.jupiter.api.Assertions.*;
import static org.testng.AssertJUnit.assertEquals;

class BackEdgeDetectorTest {
    @Test
    public void getBackEdges() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = aDAGWithCycle2();

        BackEdgeDetector<Integer, DefaultEdge> detector = new BackEdgeDetector<>(g);
        List<DefaultEdge> edges = detector.findBackEdges();
        System.out.println("edges: " + edges);
        assertEquals(edges.size(), 1);
    }

    @Test
    public void getBackEdgesInReversedGraph() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = aDAGWithCycle2();
        SimpleDirectedGraph<Integer, DefaultEdge> reversed = new SimpleDirectedGraph<>(DefaultEdge.class);
        Graphs.addGraphReversed(reversed, g);

        BackEdgeDetector<Integer, DefaultEdge> detector = new BackEdgeDetector<>(reversed, 4);
        List<DefaultEdge> edges = detector.findBackEdges();
        System.out.println("edges: " + edges);
        assertEquals(edges.size(), 1);
    }

    @Test
    public void graphWithRemovedBackEdgesCanBeSortedTopologically() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = aDAGWithCycle2();
        removeBackEdges(g);

        TopologicalOrderIterator<Integer, DefaultEdge> topoit = new TopologicalOrderIterator<>(g);
        while (topoit.hasNext())
            topoit.next();
    }
}
