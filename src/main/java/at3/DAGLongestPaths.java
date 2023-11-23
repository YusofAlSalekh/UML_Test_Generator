package at3;

import org.jgrapht.Graph;
import org.jgrapht.GraphPath;
import org.jgrapht.Graphs;
import org.jgrapht.alg.interfaces.ShortestPathAlgorithm;
import org.jgrapht.alg.shortestpath.TreeSingleSourcePathsImpl;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.traverse.TopologicalOrderIterator;

import java.util.*;
import java.util.stream.Collectors;

public class DAGLongestPaths<V, E> {
    protected final Graph<V, E> graph;

    private final Map<V, Pair<Double, E>> distanceAndPredecessorMap = new HashMap<>();
    private final List<V> verticesInTopologicalOrder = new ArrayList<>();
    private final ShortestPathAlgorithm.SingleSourcePaths<V, E> paths;
    private final Graph<V, E> spanningTree;

    public DAGLongestPaths(Graph<V, E> graph) {
        this.graph = Objects.requireNonNull(graph, "Graph is null");
        if (graph.vertexSet().isEmpty())
            throw new IllegalArgumentException("Graph must not be empty.");

        paths = findPaths();
        spanningTree = buildSpanningTree();
    }

    public GraphPath<V, E> getPath(V sink) {
        return paths.getPath(sink);
    }

    public List<V> getLeaves() {
        return spanningTree.vertexSet().stream()
                .filter(v -> spanningTree.outDegreeOf(v) == 0)
                .collect(Collectors.toList());
    }

    private ShortestPathAlgorithm.SingleSourcePaths<V, E> findPaths() {
        TopologicalOrderIterator<V, E> it = new TopologicalOrderIterator<>(graph);

        while (it.hasNext()) {
            V u = it.next();
            verticesInTopologicalOrder.add(u);

            for (E e : graph.incomingEdgesOf(u)) {
                Pair<Double, E> du = distanceAndPredecessorMap.getOrDefault(u, Pair.of(0., null));

                V v = Graphs.getOppositeVertex(graph, e, u);
                Pair<Double, E> dv = distanceAndPredecessorMap.getOrDefault(v, Pair.of(0., null));

                if (du.getFirst() < dv.getFirst() + 1) {
                    distanceAndPredecessorMap.put(u, Pair.of(dv.getFirst() + 1, e));
                }
            }
        }
        return new TreeSingleSourcePathsImpl<>(graph, verticesInTopologicalOrder.get(0), distanceAndPredecessorMap);
    }

    private Graph<V, E> buildSpanningTree() {
        SimpleDirectedGraph<V, E> tree = new SimpleDirectedGraph<>(
                graph.getVertexSupplier(),
                graph.getEdgeSupplier(),
                graph.getType().isWeighted());
        List<E> edges = distanceAndPredecessorMap.values().stream()
                .map(Pair::getSecond)
                .collect(Collectors.toList());
        Graphs.addAllEdges(tree, graph, edges);
        return tree;
    }
}
