package at3;

import org.jgrapht.Graph;
import org.jgrapht.alg.util.Pair;
import org.jgrapht.graph.DefaultEdge;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class CodeCoveragePaths {
    public static <V, E> List<List<V>> findStatementCoveragePaths(Graph<V, E> graph) {
        Objects.requireNonNull(graph);
        return new PathFinder<>(graph).getPaths();
    }

    public static <V, E> List<List<V>> findBranchCoveragePaths(Graph<V, E> graph) {
        Objects.requireNonNull(graph);
        Graph<Pair<V, V>, DefaultEdge> edgeGraph = GraphUtils.buildEdgeGraph(graph);
        List<List<Pair<V, V>>> paths = new PathFinder<>(edgeGraph).getPaths();
        return paths.stream().map(CodeCoveragePaths::mergeEdges).collect(Collectors.toList());
    }

    private static <V> List<V> mergeEdges(List<Pair<V, V>> edgePath) {
        if (edgePath == null || edgePath.isEmpty())
            return Collections.emptyList();

        List<V> path = new ArrayList<>();
        edgePath.forEach(e -> path.add(e.getFirst()));

        path.add(edgePath.get(edgePath.size() - 1).getSecond());

        return path;
    }
}
