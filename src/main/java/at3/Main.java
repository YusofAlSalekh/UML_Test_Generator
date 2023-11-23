package at3;

import net.objecthunter.exp4j.Expression;
import net.objecthunter.exp4j.ExpressionBuilder;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleDirectedGraph;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static final String UNIT_TEST_TEMPLATE = "@Test\n" +
            "    public void testPath$i() {\n" +

            "        int result = callMyFunction(new String[]{$params});\n" +
            "        assertEquals($result, result);\n" +
            "    }";

    static HashMap<String, Integer> results = new HashMap<>();

    public static void main(String[] args) throws Exception {
        try (
                FileWriter writer =
                        new FileWriter("C:\\Users\\Ganik\\IdeaProjects\\p02\\src\\main\\java\\at3\\Test cases")) {

            System.out.println("write");
            String path = requestWord();
            File file = new File(path);
            int n = 0;

            Scanner scan = new Scanner(file);

            n = scan.nextInt();
            Table[] table = new Table[n];
            for (int i = 0; i < n; i++) {
                table[i] = new Table();
                table[i].from = scan.nextInt();
                table[i].to = scan.nextInt();
                table[i].value = scan.next();
            }

            SimpleDirectedGraph<ActivityStatement, DefaultEdge> graph = DomParser.getGraph();

            int stage1 = -1;
            int stage2 = -1;
            int index = 0;

            //List<List<ActividityStaement>> paths2 = CodeCoveragePaths.findStatementCoveragePaths(graph);
            // создаем все пути
            List<List<ActivityStatement>> paths2 = CodeCoveragePaths.findBranchCoveragePaths(graph);
            for (List<ActivityStatement> list : paths2) {                                       //for each идем по путям
                ArrayList<String> decisionPath = new ArrayList<String>();
                results.clear();                                                                //очищаем для нового пути
                for (ActivityStatement as : list) {
                    if (stage1 == -1) {
                        stage1 = as.id;
                    } else {
                        stage2 = as.id;
                        for (int i = 0; i < n; i++) {
                            if (table[i].from == stage1 && table[i].to == stage2) {
                                decisionPath.add("\"" + table[i].value + "\"");
                            }
                        }
                        stage1 = stage2;
                    }
                    for (String constraint : as.constraints) {
                        String[] constraintSpit = constraint.split("=");
                        if (!results.containsKey(constraintSpit[0])) {
                            results.put(constraintSpit[0], Integer.parseInt(constraintSpit[1]));
                        } else {
                            Integer prevResult = results.get(constraintSpit[0]);

                            Expression expression = new ExpressionBuilder(constraintSpit[1])
                                    .variables(constraintSpit[0])
                                    .build()
                                    .setVariable(constraintSpit[0], prevResult);
                            double result = expression.evaluate();
                            results.put(constraintSpit[0], (int) result);
                        }
                    }
                }
                index++;
                writer.write(UNIT_TEST_TEMPLATE.replace("$i", String.valueOf(index))
                        .replace("$params", decisionPath.toString()
                                .replace("[", "")
                                .replace("]", ""))
                        .replace("$result", results.get("result").toString()) + "\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String requestWord() {
        Scanner scanner = new Scanner(System.in);
        return scanner.nextLine();
    }
}
