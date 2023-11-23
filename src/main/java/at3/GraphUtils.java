package at3;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.util.List;
import java.util.Objects;

public class GraphUtils {
    public static <V, E> Graph<Pair<V, V>, DefaultEdge> buildEdgeGraph(Graph<V, E> graph) {
        Objects.requireNonNull(graph);

        Graph<Pair<V, V>, DefaultEdge> edgeGraph = new SimpleDirectedGraph<>(DefaultEdge.class);

        for (V v : graph.vertexSet()) {
            for (E incoming : graph.incomingEdgesOf(v)) {
                for (E outgoing : graph.outgoingEdgesOf(v)) {
                    V in = graph.getEdgeSource(incoming);
                    V out = graph.getEdgeTarget(outgoing);
                    Graphs.addEdgeWithVertices(edgeGraph, Pair.of(in, v), Pair.of(v, out));
                }
            }
        }
        return edgeGraph;
    }

    public static <V, E> Graph<V, E> removeBackEdges(Graph<V, E> graph) {
        V startVertex = findStartVertex(graph);
        List<E> backEdges = new BackEdgeDetector<>(graph, startVertex).findBackEdges();
        graph.removeAllEdges(backEdges);
        return graph;
    }

    public static <V, E> V findStartVertex(Graph<V, E> graph) {
        return graph.vertexSet().stream().filter(v -> graph.inDegreeOf(v) == 0).findAny().get();
    }

    public static <V, E> Graph<V, E> copyGraph(Graph<V, E> graph) {
        Graph<V, E> clone = buildEmptyGraph(graph);
        Graphs.addGraph(clone, graph);
        return clone;
    }

    public static <V, E> Graph<V, E> reverseGraph(Graph<V, E> graph) {
        SimpleDirectedGraph<V, E> reversed = buildEmptyGraph(graph);
        Graphs.addGraphReversed(reversed, graph);
        return reversed;
    }

    public static <V, E> SimpleDirectedGraph<V, E> buildEmptyGraph(Graph<V, E> graph) {
        return new SimpleDirectedGraph<>(
                graph.getVertexSupplier(),
                graph.getEdgeSupplier(),
                graph.getType().isWeighted());
    }
}
