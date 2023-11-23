package at3;

import org.junit.jupiter.api.Test;

import java.util.List;

import static at3.GraphSamples.aDAGWithCycle;
import static at3.GraphSamples.aDAGWithShallowCycle;
import static org.junit.jupiter.api.Assertions.*;

class CodeCoveragePathsTest {
    @Test
    public void statementCoverageLongCycle() {
        List<List<Integer>> paths = CodeCoveragePaths.findStatementCoveragePaths(aDAGWithCycle());
        assertEquals("[[0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 6, 1, 2, 3, 4, 5]]", paths.toString());
    }

    @Test
    public void branchCoverageLongCycle() {
        List<List<Integer>> paths = CodeCoveragePaths.findBranchCoveragePaths(aDAGWithCycle());
        assertEquals("[[0, 1, 2, 3, 4, 5], [0, 1, 2, 3, 4, 6, 1, 2, 3, 4, 5]]", paths.toString());
    }

    @Test
    public void statementCoverageShallowCycle() {
        List<List<Integer>> paths = CodeCoveragePaths.findStatementCoveragePaths(aDAGWithShallowCycle());
        assertEquals("[[0, 1, 2, 3, 4]]", paths.toString());
    }

    @Test
    public void branchCoverageShallowCycle() {
        List<List<Integer>> paths = CodeCoveragePaths.findBranchCoveragePaths(aDAGWithShallowCycle());
        assertEquals("[[0, 1, 2, 3, 4], [0, 1, 2, 3, 1, 2, 3, 4]]", paths.toString());
    }
}
