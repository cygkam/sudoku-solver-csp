import java.util.ArrayList;

public class Constraint {

    public Boolean checkConstraints(int k ,int x, int y, ArrayList<Pole> pola){
        return checkHorizontalConstraint(k,x,y,pola) && checkVerticalConstraint(k,x,y,pola) && checkSquareConstraint(k,x,y,pola);
    }

    public Boolean checkHorizontalConstraint(int k, int x, int y, ArrayList<Pole> pola){
        for(int j=0; j<Constant.SUDOKU_DIM; j++){
            if(j!=x){
                if(pola.get(j+ Constant.SUDOKU_DIM*(y)).getCurrentValue() == k)
                    return false;
            }
        }
        return true;
    }

    public Boolean checkVerticalConstraint(int k, int x, int y, ArrayList<Pole> pola){
        for(int j=0; j<Constant.SUDOKU_DIM; j++){
            if(j!=y){
                if(pola.get(j*Constant.SUDOKU_DIM+x).getCurrentValue()==k)
                    return false;
            }
        }
        return true;
    }

    public Boolean checkSquareConstraint(int k, int x, int y, ArrayList<Pole> pola){
        int c = ((y)/Constant.SQUARE_DIM)*Constant.SQUARE_DIM;
        int r = ((x)/Constant.SQUARE_DIM)*Constant.SQUARE_DIM;
        for (int j = c; j < c + Constant.SQUARE_DIM; j++) {
            for (int i = r; i < r + Constant.SQUARE_DIM; i++) {
                if(pola.get(j*Constant.SUDOKU_DIM + i).getCurrentValue() == k)
                    return false;
            }
        }
        return true;
    }
}
