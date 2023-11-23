package at3;

import org.jgrapht.Graph;
import org.jgrapht.event.EdgeTraversalEvent;
import org.jgrapht.event.TraversalListenerAdapter;
import org.jgrapht.event.VertexTraversalEvent;
import org.jgrapht.traverse.DepthFirstIterator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class BackEdgeDetector<V, E> extends TraversalListenerAdapter<V, E> {
    protected final Graph<V, E> graph;
    protected final V startVertex;

    protected List<E> backEdges;
    protected Set<V> verticesOnStack;

    public BackEdgeDetector(Graph<V, E> graph) {
        this(graph, null);
    }

    public BackEdgeDetector(Graph<V, E> graph, V startVertex) {
        this.graph = graph;
        this.startVertex = startVertex;
    }

    public List<E> findBackEdges() {
        backEdges = new ArrayList<>();
        verticesOnStack = new HashSet<>();

        DepthFirstIterator<V, E> dfit = new DepthFirstIterator<>(graph, startVertex);
        dfit.addTraversalListener(this);

        while (dfit.hasNext())
            dfit.next();

        return backEdges;
    }

    @Override
    public void edgeTraversed(EdgeTraversalEvent<E> e) {
        E edge = e.getEdge();
        V target = graph.getEdgeTarget(edge);

        if (verticesOnStack.contains(target))
            backEdges.add(edge);
    }

    @Override
    public void vertexTraversed(VertexTraversalEvent<V> e) {
        verticesOnStack.add(e.getVertex());
    }

    @Override
    public void vertexFinished(VertexTraversalEvent<V> e) {
        verticesOnStack.remove(e.getVertex());
    }
}
