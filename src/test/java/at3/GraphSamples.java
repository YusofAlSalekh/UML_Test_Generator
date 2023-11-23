package at3;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.jgrapht.graph.builder.GraphBuilder;

public class GraphSamples {
    public static SimpleDirectedGraph<Integer, DefaultEdge> aDAG() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = new SimpleDirectedGraph<>(DefaultEdge.class);

        return new GraphBuilder<>(g)
                .addEdgeChain(0, 1, 2, 3, 4)
                .addEdge(1, 3)
                .build();
    }

    public static SimpleDirectedGraph<Integer, DefaultEdge> aDAG2() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = new SimpleDirectedGraph<>(DefaultEdge.class);

        return new GraphBuilder<>(g)
                .addEdgeChain(0, 1, 2, 3, 4, 5)
                .addEdgeChain(1, 6, 4)
                .build();
    }

    public static SimpleDirectedGraph<Integer, DefaultEdge> aDAGWithCycle() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = new SimpleDirectedGraph<>(DefaultEdge.class);

        return new GraphBuilder<>(g)
                .addEdgeChain(0, 1, 2, 3, 4, 5)
                .addEdgeChain(4, 6, 1)
                .build();
    }

    public static SimpleDirectedGraph<Integer, DefaultEdge> aDAGWithCycle2() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = new SimpleDirectedGraph<>(DefaultEdge.class);

        return new GraphBuilder<>(g)
                .addEdgeChain(0, 1, 2, 3, 4)
                .addEdgeChain(3, 5, 1)
                .build();
    }

    public static SimpleDirectedGraph<Integer, DefaultEdge> aDAGWithShallowCycle() {
        SimpleDirectedGraph<Integer, DefaultEdge> g = new SimpleDirectedGraph<>(DefaultEdge.class);

        return new GraphBuilder<>(g)
                .addEdgeChain(0, 1, 2, 3, 4)
                .addEdgeChain(3, 1)
                .build();
    }
}
