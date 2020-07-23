import java.util.*;

public class SudokuSolver {

    private Constraint con = new Constraint();
    private ArrayList<ArrayList<Pole>> polaToReturn;
    private boolean firstSolution;
    private long startTime;
    private long firstSolutionTime;
    private long endTime;
    private int visitedTreeNodesToFirstSolution;
    private int countBackTrackingsToFirstSolution;
    private int visitedTreeNodes;
    private int countBackTrackings;

    public SudokuSolver() {
        polaToReturn = new ArrayList<>();
    }

    public Result run(Sudoku sudoku, Constant.valueOrder valOrder, Constant.variableOrder varOrder, Constant.technic technic){
        visitedTreeNodesToFirstSolution =0;
        countBackTrackingsToFirstSolution=0;
        visitedTreeNodes = 0;
        countBackTrackings = 0;
        polaToReturn.clear();
        firstSolution=false;
        startTime = System.nanoTime();
        switch (technic){
            case BACKTRACK:{
                solveSudoku(sudoku.getPola(),0, valOrder, varOrder);
                break;
            }
            case BACKTRACK_WITH_FC:{
                solveSudokuFC(sudoku.getPola(),0, valOrder, varOrder);
                break;
            }
        }
        endTime = System.nanoTime();
        long duration = (endTime - startTime);
        double durationInMs =  ((double) duration)/Constant.NANOSECONDS;
        long firstSolutionDuration = (firstSolutionTime - startTime);
        double firstSolutionDurationInMs =  ((double) firstSolutionDuration)/Constant.NANOSECONDS;
        sudoku.setSolution((ArrayList<ArrayList<Pole>>) polaToReturn.clone());

        if(!firstSolution){
            firstSolutionDurationInMs =0;
        }

        return new Result(technic,varOrder, valOrder,visitedTreeNodesToFirstSolution, countBackTrackingsToFirstSolution, visitedTreeNodes, countBackTrackings, sudoku, durationInMs, firstSolutionDurationInMs, polaToReturn.size());
    }


    public Boolean solveSudoku(ArrayList<Pole> pola, int iter, Constant.valueOrder valOrder, Constant.variableOrder varOrder) {
        Pole p = null;
        Boolean found = false;
        switch(varOrder){
            case MostRestrictiveVariable: {
                p = findMostRestrictiveVariable(pola);
                if(p==null)
                    found = true;
                break;
            }
            case LeastRestrictiveVariable: {
                p = findLeastRestrictiveVariable(pola);
                if(p==null)
                    found = true;
                break;
            }
            case MostRestrictedVariable: {
                p = findMostRestrictedVariable(pola);
                if(p==null)
                    found = true;
                break;
            }
            case LeastRestrictedVariable: {
                p = findLeastRestrictedVariable(pola);
                if(p==null)
                    found = true;
                break;
            }
            case ORIGINAL: {
                if(iter == Constant.BT_END_VALUE){
                    found = true;
                    break;
                }
                p = pola.get(iter);
                break;
            }
        }
        if(found){
                if(!firstSolution){
                    firstSolution =true;
                    countBackTrackingsToFirstSolution=countBackTrackings;
                    visitedTreeNodesToFirstSolution=visitedTreeNodes;
                    firstSolutionTime = System.nanoTime();
                }
                polaToReturn.add(clone(pola));
                return false;
            }else{
                visitedTreeNodes++;
                if( p.getCurrentValue() != 0 ){
                    return solveSudoku(pola, iter+1, valOrder, varOrder);
                }else{
                    switch(valOrder){
                        case RANDOM: {
                            Collections.shuffle(p.getDomain());
                            break;
                        }
                        case ORIGINAL: {
                            break;
                        }
                    }
                    for(int k : p.getDomain()){
                        if(con.checkConstraints(k,p.getX(), p.getY(), pola)){
                            p.setCurrentValue(k);
                            if(solveSudoku(pola, iter+1, valOrder, varOrder)){
                                return true;
                            }else {
                                p.setCurrentValue(0);
                            }
                        }
                    }
                }
            }
            countBackTrackings++;
            return false;
    }


    public Boolean solveSudokuFC(ArrayList<Pole> pola, int iter, Constant.valueOrder valOrder, Constant.variableOrder varOrder) {
        Pole p = null;
        Boolean found = false;
        switch(varOrder) {
            case MostRestrictiveVariable: {
                p = findMostRestrictiveVariable(pola);
                if (p == null)
                    found = true;
                break;
            }
            case LeastRestrictiveVariable: {
                p = findLeastRestrictiveVariable(pola);
                if (p == null)
                    found = true;
                break;
            }
            case MostRestrictedVariable: {
                p = findMostRestrictedVariable(pola);
                if (p == null)
                    found = true;
                break;
            }
            case LeastRestrictedVariable: {
                p = findLeastRestrictedVariable(pola);
                if (p == null)
                    found = true;
                break;
            }
            case ORIGINAL: {
                if(iter == Constant.BT_END_VALUE){
                    found = true;
                    break;
                }
                p = pola.get(iter);
                break;
            }
        }
        if(found){
            if(!firstSolution){
                firstSolution =true;
                countBackTrackingsToFirstSolution=countBackTrackings;
                visitedTreeNodesToFirstSolution=visitedTreeNodes;
                firstSolutionTime = System.nanoTime();
            }
            polaToReturn.add(clone(pola));
            return false;
        }else{
            visitedTreeNodes++;
            ArrayList<Pole> polaBackup = clone(pola);
            switch(valOrder){
                case RANDOM: {
                    Collections.shuffle(p.getDomain());
                    break;
                }
                case ORIGINAL: {
                    break;
                }
            }
            if(!p.getInitial()) {
                for (int k : p.getDomain()) {
                    if (con.checkConstraints(k,  p.getX(),  p.getY(), pola)) {
                        pola =clone(polaBackup);
                        switch(varOrder){
                            case MostRestrictiveVariable: {
                                p = findMostRestrictiveVariable(pola);
                                break;
                            }
                            case LeastRestrictiveVariable: {
                                p = findLeastRestrictiveVariable(pola);
                                break;
                            }
                            case MostRestrictedVariable: {
                                p = findMostRestrictedVariable(pola);
                                break;
                            }
                            case LeastRestrictedVariable: {
                                p = findLeastRestrictedVariable(pola);
                                break;
                            }
                            case ORIGINAL: {
                                p = pola.get(iter);
                                break;
                            }
                        }
                        p.setCurrentValue(k);
                        if (reduceDomains( p, pola))
                            solveSudokuFC(pola, iter + 1, valOrder, varOrder);
                    }
                }
                countBackTrackings++;
                return false;
            }else{
                solveSudokuFC(pola, iter + 1, valOrder, varOrder);
                return false;
            }
        }
    }

    private Pole findMostRestrictedVariable(ArrayList<Pole> pola){
        int max =10;
        Pole maxPole = null;
        for( int k=0; k<pola.size(); k++){
            if(!pola.get(k).getInitial())
                if(pola.get(k).getCurrentValue()==0){
                   if (pola.get(k).getDomain().size()<max)
                        max = pola.get(k).getDomain().size();
                        maxPole = pola.get(k);
                }
        }
        return maxPole;
    }

    private Pole findLeastRestrictedVariable(ArrayList<Pole> pola){
        int max =0;
        Pole maxPole = null;
        for( int k=0; k<pola.size(); k++){
            if(!pola.get(k).getInitial())
                if(pola.get(k).getCurrentValue()==0){
                    if (pola.get(k).getDomain().size()>max)
                        max = pola.get(k).getDomain().size();
                    maxPole = pola.get(k);
                }
        }
        return maxPole;
    }

    private Pole findRandomVariable(ArrayList<Pole> pola){
        ArrayList<Pole> polaTemp;
        polaTemp = clone(pola);
        polaTemp.removeIf(Pole::getInitial);
        polaTemp.removeIf(pole -> pole.getCurrentValue()!=0);
        Random rand = new Random();
        return polaTemp.get(rand.nextInt(polaTemp.size()));
    }

    private Pole findMostRestrictiveVariable(ArrayList<Pole> pola){
        int max =0;
        Pole maxPole = null;
        for( int k=0; k<pola.size(); k++){
            if(!pola.get(k).getInitial())
                if(pola.get(k).getCurrentValue()==0){

                    if (countPoles(pola.get(k),pola ) > max)
                        max = pola.get(k).getDomain().size();
                    maxPole = pola.get(k);
                }
        }
        return maxPole;
    }

    private Pole findLeastRestrictiveVariable(ArrayList<Pole> pola){
        int max = 81;
        Pole maxPole = null;
        for( int k=0; k<pola.size(); k++){
            if(!pola.get(k).getInitial())
                if(pola.get(k).getCurrentValue()==0){

                    if (countPoles(pola.get(k),pola ) < max)
                        max = pola.get(k).getDomain().size();
                    maxPole = pola.get(k);
                }
        }
        return maxPole;
    }


    private static ArrayList<Pole> clone(ArrayList<Pole> list) {
        ArrayList<Pole> clone = new ArrayList<Pole>(list.size());
        for (Pole item : list) clone.add(new Pole(item));
        return clone;
    }

    private Boolean reduceDomains(Pole p, ArrayList<Pole> pola){
        return reduceDomainsHorizontally(p, pola) && reduceDomainsSquare(p, pola) &&  reduceDomainsVertically(p, pola);
    }

    private Boolean reduceDomainsVertically(Pole p, ArrayList<Pole> pola){
        for(int j=0; j<Constant.SUDOKU_DIM; j++){
            if(j!=p.getY()){
                if(!pola.get(j*Constant.SUDOKU_DIM+p.getX()).getInitial() && pola.get(j*Constant.SUDOKU_DIM+p.getX()).getCurrentValue()==0){
                    int finalTemp = p.getCurrentValue();
                    pola.get(j*Constant.SUDOKU_DIM+p.getX()).getDomain().removeIf(k -> k== finalTemp);
                    if( pola.get(j*Constant.SUDOKU_DIM+p.getX()).getDomain().isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Boolean reduceDomainsHorizontally(Pole p, ArrayList<Pole> pola){
        for(int j=0; j<Constant.SUDOKU_DIM; j++){
            if(j!=p.getX()){
                if(!pola.get(j+Constant.SUDOKU_DIM*(p.getY())).getInitial() && pola.get(j+Constant.SUDOKU_DIM*(p.getY())).getCurrentValue()==0){
                    int finalTemp = p.getCurrentValue();
                    pola.get((j+Constant.SUDOKU_DIM*(p.getY()))).getDomain().removeIf(k -> k== finalTemp);
                    if(pola.get((j+Constant.SUDOKU_DIM*(p.getY()))).getDomain().isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private Boolean reduceDomainsSquare(Pole p, ArrayList<Pole> pola){
        int x = ((p.getY())/Constant.SQUARE_DIM)*Constant.SQUARE_DIM;
        int y = ((p.getX())/Constant.SQUARE_DIM)*Constant.SQUARE_DIM;
        for (int j = x; j < x + Constant.SQUARE_DIM; j++) {
            for (int i = y; i < y + Constant.SQUARE_DIM; i++) {
                if (!pola.get(j*Constant.SUDOKU_DIM+i).getInitial() && pola.get(j*Constant.SUDOKU_DIM+i).getCurrentValue()==0) {
                    int finalTemp = p.getCurrentValue();
                    pola.get(j*Constant.SUDOKU_DIM+i).getDomain().removeIf(k -> k== finalTemp);
                    if(pola.get(j*Constant.SUDOKU_DIM+i).getDomain().isEmpty()){
                        return false;
                    }
                }
            }
        }
        return true;
    }

    private int countPoles(Pole p, ArrayList<Pole> pola){
        return countPolesVertically(p, pola) + countPolesHorizontally(p, pola) + countPolesSquare(p, pola);
    }

    private int countPolesVertically(Pole p, ArrayList<Pole> pola){
        int counter =0;
        for(int j=0; j<Constant.SUDOKU_DIM; j++){
            if(j!=p.getY()){
                if(!pola.get(j*Constant.SUDOKU_DIM+p.getX()).getInitial() && pola.get(j*Constant.SUDOKU_DIM+p.getX()).getCurrentValue()==0){
                    counter++;
                }
            }
        }
        return counter;
    }

    private int countPolesHorizontally(Pole p, ArrayList<Pole> pola){
        int counter =0;
        for(int j=0; j<Constant.SUDOKU_DIM; j++){
            if(j!=p.getX()){
                if(!pola.get(j+Constant.SUDOKU_DIM*(p.getY())).getInitial() && pola.get(j+Constant.SUDOKU_DIM*(p.getY())).getCurrentValue()==0){
                    counter++;
                }
            }
        }
        return counter;
    }

    private int countPolesSquare(Pole p, ArrayList<Pole> pola){
        int counter =0;
        int x = ((p.getY())/Constant.SQUARE_DIM)*Constant.SQUARE_DIM;
        int y = ((p.getX())/Constant.SQUARE_DIM)*Constant.SQUARE_DIM;
        for (int j = x; j < x + Constant.SQUARE_DIM; j++) {
            for (int i = y; i < y + Constant.SQUARE_DIM; i++) {
                if (!pola.get(j*Constant.SUDOKU_DIM+i).getInitial() && pola.get(j*Constant.SUDOKU_DIM+i).getCurrentValue()==0) {
                    counter++;
                }
            }
        }
        return counter;
    }



}
