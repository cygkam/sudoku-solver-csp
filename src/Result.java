
public class Result {
    private int visitedTreeNodesToFirstSolution;
    private int countBackTrackingsToFirstSolution;
    private int visitedTreeNodes;
    private int countBackTrackings;
    private Sudoku sudoku;
    private double durationInMs;
    private double firstSolutionDurationInMs;
    private int numOfSolutions;
    private Constant.valueOrder valOrder;
    private Constant.variableOrder varOrder;
    private Constant.technic technic;

    public Result(Constant.technic technic,Constant.variableOrder varOrder, Constant.valueOrder valOrder,int visitedTreeNodesToFirstSolution, int countBackTrackingsToFirstSolution, int visitedTreeNodes, int countBackTrackings, Sudoku sudoku, double durationInMs, double firstSolutionDurationInMs, int numOfSolutions) {
        this.visitedTreeNodesToFirstSolution = visitedTreeNodesToFirstSolution;
        this.countBackTrackingsToFirstSolution = countBackTrackingsToFirstSolution;
        this.visitedTreeNodes = visitedTreeNodes;
        this.countBackTrackings = countBackTrackings;
        this.sudoku = sudoku;
        this.durationInMs = durationInMs;
        this.firstSolutionDurationInMs = firstSolutionDurationInMs;
        this.numOfSolutions = numOfSolutions;
        this.technic = technic;
        this.valOrder = valOrder;
        this.varOrder = varOrder;
    }

    public int getVisitedTreeNodesToFirstSolution() {
        return visitedTreeNodesToFirstSolution;
    }

    public void setVisitedTreeNodesToFirstSolution(int visitedTreeNodesToFirstSolution) {
        this.visitedTreeNodesToFirstSolution = visitedTreeNodesToFirstSolution;
    }

    public int getCountBackTrackingsToFirstSolution() {
        return countBackTrackingsToFirstSolution;
    }

    public void setCountBackTrackingsToFirstSolution(int countBackTrackingsToFirstSolution) {
        this.countBackTrackingsToFirstSolution = countBackTrackingsToFirstSolution;
    }

    public int getVisitedTreeNodes() {
        return visitedTreeNodes;
    }

    public void setVisitedTreeNodes(int visitedTreeNodes) {
        this.visitedTreeNodes = visitedTreeNodes;
    }

    public int getCountBackTrackings() {
        return countBackTrackings;
    }

    public void setCountBackTrackings(int countBackTrackings) {
        this.countBackTrackings = countBackTrackings;
    }

    public Sudoku getSudoku() {
        return sudoku;
    }

    public void setSudoku(Sudoku sudoku) {
        this.sudoku = sudoku;
    }

    public double getDurationInMs() {
        return durationInMs;
    }

    public void setDurationInMs(double durationInMs) {
        this.durationInMs = durationInMs;
    }

    public double getFirstSolutionDurationInMs() {
        return firstSolutionDurationInMs;
    }

    public void setFirstSolutionDurationInMs(double firstSolutionDurationInMs) {
        this.firstSolutionDurationInMs = firstSolutionDurationInMs;
    }

    public int getNumOfSolutions() {
        return numOfSolutions;
    }

    public void setNumOfSolutions(int numOfSolutions) {
        this.numOfSolutions = numOfSolutions;
    }


    @Override
    public String toString() {
        return sudoku.getId() + ";" + firstSolutionDurationInMs + ";" + visitedTreeNodesToFirstSolution + ";"
                + countBackTrackingsToFirstSolution  +";"+ durationInMs +";"+ visitedTreeNodes + ";"
                +  countBackTrackings +";"+  numOfSolutions +";"+  technic +";"+  valOrder +";"+  varOrder;
    }

}
