package at3;

import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

public class DomParser {
    static HashMap<Integer, ActivityStatement> vertexes = new HashMap<>();
    static ArrayList<Integer> vertex = new ArrayList<Integer>();

    // перезадем индексы xml формата, чтобы было удобно отслеживать
    public static int getIndex(Integer vertexBig) {
        for (int i = 0; i < vertex.size(); i++) {
            if (Objects.equals(vertexBig, vertex.get(i)))
                return i;
        }
        return 0;
    }

    public static SimpleDirectedGraph<ActivityStatement, DefaultEdge> getGraph() throws Exception {
        DocumentBuilderFactory factory =
                DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        // File file = new File("C:\\Users\\Ganik\\IdeaProjects\\p02\\src\\test\\resources\\1r.xml");
        File file = new File("C:\\Users\\Ganik\\IdeaProjects\\p02\\src\\test\\resources\\Tick.xml");
        Document doc = builder.parse(new FileInputStream(file));
        Element root = doc.getDocumentElement();

        NodeList pseudoState = root.getElementsByTagName("UML:PseudoState");
        for (int i = 0; i < pseudoState.getLength(); i++) {

            Element k = (Element) pseudoState.item(i);
            NodeList elementsByTagName = k.getElementsByTagName("UML:TaggedValue");
            for (int j = 0; j < elementsByTagName.getLength(); j++) {
                if ((Objects.equals(elementsByTagName.item(j).getAttributes().getNamedItem("tag").getTextContent(), "ea_localid"))) {
                    ActivityStatement activityStatement = new ActivityStatement("", Integer.parseInt(elementsByTagName.item(j).getAttributes().getNamedItem("value").getTextContent()));
                    vertexes.put(i, activityStatement);
                    vertex.add(Integer.parseInt(elementsByTagName.item(j).getAttributes().getNamedItem("value").getTextContent()));
                }
            }
            NodeList constraints = k.getElementsByTagName("UML:Constraint");
            if (constraints != null && constraints.getLength() > 0) {
                for (int c = 0; c < constraints.getLength(); c++) {
                    vertexes.get(i).constraints.add(constraints.item(c).getAttributes().getNamedItem("name").getTextContent().replaceAll(" ", ""));
                }
            }
        }

        int size = vertexes.size();
        NodeList actionState = root.getElementsByTagName("UML:ActionState");
        for (int i = 0; i < actionState.getLength(); i++) {

            Element k = (Element) actionState.item(i);
            NodeList elementsByTagName = k.getElementsByTagName("UML:TaggedValue");
            for (int j = 0; j < elementsByTagName.getLength(); j++) {
                if ((Objects.equals(elementsByTagName.item(j).getAttributes().getNamedItem("tag").getTextContent(), "ea_localid"))) {
                    ActivityStatement activityStatement = new ActivityStatement("", Integer.parseInt(elementsByTagName.item(j).getAttributes().getNamedItem("value").getTextContent()));
                    vertexes.put(size + i, activityStatement);
                    vertex.add(Integer.parseInt(elementsByTagName.item(j).getAttributes().getNamedItem("value").getTextContent()));
                }
            }
            NodeList constraints = k.getElementsByTagName("UML:Constraint");
            if (constraints != null && constraints.getLength() > 0) {
                for (int c = 0; c < constraints.getLength(); c++) {
                    vertexes.get(size + i).constraints.add(constraints.item(c).getAttributes().getNamedItem("name").getTextContent().replaceAll(" ", ""));
                }
            }
        }


        int number = actionState.getLength() + pseudoState.getLength();// количество вершин

        SimpleDirectedGraph<ActivityStatement, DefaultEdge> g = new SimpleDirectedGraph<ActivityStatement, DefaultEdge>(DefaultEdge.class);
        NodeList trans = root.getElementsByTagName("UML:Transition");
        for (int i = 0; i < trans.getLength(); i++) {
            Element k = (Element) trans.item(i);
            NodeList elementsByTagName = k.getElementsByTagName("UML:TaggedValue");
            for (int j = 0; j < elementsByTagName.getLength(); j++) {
                if ((Objects.equals(elementsByTagName.item(j).getAttributes().getNamedItem("tag").getTextContent(), "ea_sourceID"))) {
                    ActivityStatement v1 = vertexes.get(getIndex(Integer.parseInt(elementsByTagName.item(j).getAttributes().getNamedItem("value").getTextContent())));
                    ActivityStatement v2 = vertexes.get(getIndex(Integer.parseInt(elementsByTagName.item(j + 1).getAttributes().getNamedItem("value").getTextContent())));
                    g.addVertex(v1);
                    g.addVertex(v2);
                    g.addEdge(v1, v2);
                    break;
                }
            }
        }
        return g;
    }
}


