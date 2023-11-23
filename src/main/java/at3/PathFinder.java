package at3;

import org.jgrapht.Graph;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class PathFinder<V, E> {
    protected final Graph<V, E> graph;

    private final DAGLongestPaths<V, E> forwardPaths;
    private final DAGLongestPaths<V, E> backwardPaths;

    public PathFinder(Graph<V, E> graph) {
        this.graph = Objects.requireNonNull(graph, "Graph is null");
        if (graph.vertexSet().isEmpty())
            throw new IllegalArgumentException("Graph must not be empty.");

        forwardPaths = new DAGLongestPaths<>(GraphUtils.removeBackEdges(GraphUtils.copyGraph(graph)));
        backwardPaths = new DAGLongestPaths<>(GraphUtils.removeBackEdges(GraphUtils.reverseGraph(graph)));
    }

    public List<List<V>> getPaths() {
        return getLeaves().stream()
                .map(this::concatenatePathsAtLeaf)
                .collect(Collectors.toList());
    }

    List<V> getLeaves() {
        return forwardPaths.getLeaves();
    }

    List<V> concatenatePathsAtLeaf(V leaf) {
        List<V> tail = new ArrayList<>(getBackwardPath(leaf));
        Collections.reverse(tail);
        tail.remove(0); // remove the leaf

        List<V> head = new ArrayList<>(getForwardPath(leaf));
        head.addAll(tail);

        return head;
    }

    List<V> getForwardPath(V leaf) {
        return forwardPaths.getPath(leaf).getVertexList();
    }

    List<V> getBackwardPath(V leaf) {
        return backwardPaths.getPath(leaf).getVertexList();
    }
}
