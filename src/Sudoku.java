import java.util.ArrayList;
import java.util.Arrays;

public class Sudoku {

    private int id;
    private double difficulty;
    private String puzzle;
    private ArrayList<ArrayList<Pole>> solutions;
    private ArrayList<Pole> pola;

    public Sudoku(int id, double difficulty, String puzzle){
        this.id = id;
        this.difficulty = difficulty;
        this.puzzle = puzzle;
        solutions = new ArrayList<>();
        pola = new ArrayList<>();
        parsePuzzle();
        reduceDomains();
    }

    private void parsePuzzle(){
        if(puzzle.toCharArray().length==81){
            int i=0;
            for(char k : puzzle.toCharArray()){
                if( k <='9' && k>='1'){
                    pola.add(new Pole(new ArrayList<Integer>(Arrays.asList(Character.getNumericValue(k))), (i%Constant.SUDOKU_DIM), (i/Constant.SUDOKU_DIM), Character.getNumericValue(k)));
                }else if(k=='.') {
                    pola.add(new Pole((i%Constant.SUDOKU_DIM), (i/Constant.SUDOKU_DIM)));
                }else{
                    System.out.println("Błędna wartość pola: '" + k  +"' w sudoku: " + id);
                }
                i++;
            }
        }else{
            System.out.println("Błędna ilość znaków w sudoku: " + id);
        }
    }

    private void reduceDomains(){
        for(Pole p : pola){
            if(!p.getInitial()){
                reduceDomainsVertically(p);
                reduceDomainsHorizontally(p);
                reduceDomainsSquare(p);
            }
        }
    }

    private void reduceDomainsVertically(Pole p){
        for(int j=0; j<Constant.SUDOKU_DIM; j++){
            if(j!=p.getY()){
                if(pola.get(j*Constant.SUDOKU_DIM+p.getX()).getInitial()){
                    int finalTemp = pola.get(j*Constant.SUDOKU_DIM+p.getX()).getCurrentValue();
                    p.getDomain().removeIf(k -> k== finalTemp);
                }
            }
        }
    }

    private void reduceDomainsHorizontally(Pole p){
        for(int j=0; j<Constant.SUDOKU_DIM; j++){
            if(j!=p.getX()){
                if(pola.get(j+Constant.SUDOKU_DIM*p.getY()).getInitial()){
                    int finalTemp = pola.get(j+Constant.SUDOKU_DIM*p.getY()).getCurrentValue();
                    p.getDomain().removeIf(k -> k== finalTemp);
                }
            }
        }
    }

    private void reduceDomainsSquare(Pole p){
            int x = ((p.getY())/Constant.SQUARE_DIM)*Constant.SQUARE_DIM;
            int y = ((p.getX())/Constant.SQUARE_DIM)*Constant.SQUARE_DIM;
             for (int j = x; j < x + Constant.SQUARE_DIM; j++) {
                 for (int i = y; i < y + Constant.SQUARE_DIM; i++) {
                     if (pola.get(j*Constant.SUDOKU_DIM+i).getInitial()) {
                         int finalTemp = pola.get(j*Constant.SUDOKU_DIM+i).getCurrentValue();
                         p.getDomain().removeIf(k -> k == finalTemp);
                     }
                 }
             }
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public double getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(double difficulty) {
        this.difficulty = difficulty;
    }

    public String getPuzzle() {
        return puzzle;
    }

    public void setPuzzle(String puzzle) {
        this.puzzle = puzzle;
    }

    public ArrayList<ArrayList<Pole>> getSolution() {
        return solutions;
    }

    public void setSolution(ArrayList<ArrayList<Pole>> solutions) {
        this.solutions = solutions;
    }

    public ArrayList<Pole> getPola() {
        return pola;
    }

    public void setPola(ArrayList<Pole> pola) {
        this.pola = pola;
    }

    @Override
    public String toString() {
        return "Sudoku{" +
                "id=" + id +
                ", difficulty=" + difficulty +
                ", puzzle='" + puzzle + '\'' +
                ", pola=" + pola +
                '}';
    }
}
