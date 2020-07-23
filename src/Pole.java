import java.util.ArrayList;
import java.util.Arrays;

public class Pole {

    private ArrayList<Integer> domain;
    private Boolean isInitial;
    private int X;
    private int Y;
    private int currentValue;

    public Pole(int X, int Y){
        this.X = X;
        this.Y = Y;
        isInitial = false;
        domain = new ArrayList<Integer>(Arrays.asList(1,2,3,4,5,6,7,8,9));
        this.currentValue = 0;
    }

    public Pole(ArrayList<Integer> domain, int X, int Y, int currentValue){
        this.X = X;
        this.Y = Y;
        this.currentValue = currentValue;
        isInitial = true;
        this.domain = domain;
    }

    public Pole(Pole pole){
        this.X = pole.X;
        this.Y = pole.Y;
        this.isInitial = pole.isInitial;
        this.domain = (ArrayList<Integer>) pole.domain.clone();
        this.currentValue = pole.currentValue;
    }

    public void removeValue(int toBeRemoved){
        domain.removeIf(number -> number == toBeRemoved);
    }

    @Override
    public String toString() {
        return "Pole{" +
                "domain=" + domain +
                ", isInitial=" + isInitial +
                ", X=" + X +
                ", Y=" + Y +
                ", currentValue=" + currentValue +
                '}';
    }

    public ArrayList<Integer> getDomain() {
        return domain;
    }

    public void setDomain(ArrayList<Integer> domain) {
        this.domain = domain;
    }

    public Boolean getInitial() {
        return isInitial;
    }

    public void setInitial(Boolean initial) {
        isInitial = initial;
    }

    public int getX() {
        return X;
    }

    public void setX(int x) {
        X = x;
    }

    public int getY() {
        return Y;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public void setCurrentValue(int currentValue) {
        this.currentValue = currentValue;
    }

}
