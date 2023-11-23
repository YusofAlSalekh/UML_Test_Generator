package at3;

import org.jgrapht.GraphPath;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DAGLongestPathsTest {

    @Test
    public void longestPaths() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = GraphSamples.aDAG();

        DAGLongestPaths<Integer, DefaultEdge> paths = new DAGLongestPaths<>(g);

        GraphPath<Integer, DefaultEdge> path = paths.getPath(4);

        assertEquals("[0, 1, 2, 3, 4]", path.getVertexList().toString());
    }

    @Test
    public void spanningTreeOneLeaf() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = GraphSamples.aDAG();

        DAGLongestPaths<Integer, DefaultEdge> paths = new DAGLongestPaths<>(g);

        List<Integer> leaves = paths.getLeaves();
        assertEquals("[4]", leaves.toString());
    }

    @Test
    public void spanningTreeTwoLeaves() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = GraphSamples.aDAG2();

        DAGLongestPaths<Integer, DefaultEdge> paths = new DAGLongestPaths<>(g);

        List<Integer> leaves = paths.getLeaves();
        assertEquals("[5, 6]", leaves.toString());
    }
}
