package at3;

import org.jgrapht.graph.DefaultEdge;
import org.junit.jupiter.api.Test;

import java.util.List;

import static at3.GraphSamples.*;
import static org.junit.jupiter.api.Assertions.*;

class PathFinderTest {
    @Test
    public void getLeaves() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG());
        assertEquals("[4]", finder.getLeaves().toString());
    }

    @Test
    public void getForwardPath() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG());
        assertEquals("[0, 1, 2, 3, 4]", finder.getForwardPath(4).toString());
    }

    @Test
    public void getBackwardPath() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG());
        assertEquals("[4]", finder.getBackwardPath(4).toString());
    }

    @Test
    public void concatenatePaths() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG());
        assertEquals("[0, 1, 2, 3, 4]", finder.concatenatePathsAtLeaf(4).toString());
    }

    @Test
    public void findPaths() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG());
        List<List<Integer>> paths = finder.getPaths();
        assertEquals("[[0, 1, 2, 3, 4]]", paths.toString());
    }

    @Test
    public void getLeaves2() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG2());
        assertEquals("[5, 6]", finder.getLeaves().toString());
    }

    @Test
    public void getForwardPath2_5() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG2());
        assertEquals("[0, 1, 2, 3, 4, 5]", finder.getForwardPath(5).toString());
    }

    @Test
    public void getBackwardPath2_5() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG2());
        assertEquals("[5]", finder.getBackwardPath(5).toString());
    }

    @Test
    public void concatenatePaths2_5() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG2());
        assertEquals("[0, 1, 2, 3, 4, 5]", finder.concatenatePathsAtLeaf(5).toString());
    }

    @Test
    public void getForwardPath2_6() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG2());
        assertEquals("[0, 1, 6]", finder.getForwardPath(6).toString());
    }

    @Test
    public void getBackwardPath2_6() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG2());
        assertEquals("[5, 4, 6]", finder.getBackwardPath(6).toString());
    }

    @Test
    public void concatenatePaths2_6() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG2());
        assertEquals("[0, 1, 6, 4, 5]", finder.concatenatePathsAtLeaf(6).toString());
    }

    @Test
    public void findPaths2() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAG2());
        List<List<Integer>> paths = finder.getPaths();
        assertEquals("[[0, 1, 2, 3, 4, 5], [0, 1, 6, 4, 5]]", paths.toString());
    }

    @Test
    public void findPathsWithCycle() {
        PathFinder<Integer, DefaultEdge> finder = new PathFinder<>(aDAGWithCycle());
        List<List<Integer>> paths = finder.getPaths();
        assertEquals("[[0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 6, 1, 2, 3, 4, 5]]", paths.toString());
    }
}
