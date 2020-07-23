import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.ListIterator;

public class SudokuPaneController {

    private ArrayList<Result> results;
    private ArrayList<ArrayList<Pole>> solutions;
    private ArrayList<Pole> currentSolution;
    private Result current;
    private ListIterator<Result> iterator;
    private ListIterator<ArrayList<Pole>> solutionIterator;

    @FXML private Button btnNextSolution, btnPrevSolution, btnPrevSudoku, btnNextSudoku;
    @FXML private Label labelId, labelCurrSol, labelCurr, labelFSDur, labelFSvisited, labelFSBT, labelDur, labelVisited, labelBT, labelFoundSolutions;
    @FXML private TextField txtF11, txtF21, txtF31, txtF41, txtF51, txtF61, txtF71, txtF81, txtF91,
                            txtF12, txtF22, txtF32, txtF42, txtF52, txtF62, txtF72, txtF82, txtF92,
                            txtF13, txtF23, txtF33, txtF43, txtF53, txtF63, txtF73, txtF83, txtF93,
                            txtF14, txtF24, txtF34, txtF44, txtF54, txtF64, txtF74, txtF84, txtF94,
                            txtF15, txtF25, txtF35, txtF45, txtF55, txtF65, txtF75, txtF85, txtF95,
                            txtF16, txtF26, txtF36, txtF46, txtF56, txtF66, txtF76, txtF86, txtF96,
                            txtF17, txtF27, txtF37, txtF47, txtF57, txtF67, txtF77, txtF87, txtF97,
                            txtF18, txtF28, txtF38, txtF48, txtF58, txtF68, txtF78, txtF88, txtF98,
                            txtF19, txtF29, txtF39, txtF49, txtF59, txtF69, txtF79, txtF89, txtF99;
    private TextField[][] fields;

    public SudokuPaneController(ArrayList<Result> results) {
        this.results = results;
    }

    @FXML
    private void initialize() {
        fields = new TextField[][] {{txtF11, txtF21, txtF31, txtF41, txtF51, txtF61, txtF71, txtF81, txtF91},
                                    {txtF12, txtF22, txtF32, txtF42, txtF52, txtF62, txtF72, txtF82, txtF92},
                                    {txtF13, txtF23, txtF33, txtF43, txtF53, txtF63, txtF73, txtF83, txtF93},
                                    {txtF14, txtF24, txtF34, txtF44, txtF54, txtF64, txtF74, txtF84, txtF94},
                                    {txtF15, txtF25, txtF35, txtF45, txtF55, txtF65, txtF75, txtF85, txtF95},
                                    {txtF16, txtF26, txtF36, txtF46, txtF56, txtF66, txtF76, txtF86, txtF96},
                                    {txtF17, txtF27, txtF37, txtF47, txtF57, txtF67, txtF77, txtF87, txtF97},
                                    {txtF18, txtF28, txtF38, txtF48, txtF58, txtF68, txtF78, txtF88, txtF98},
                                    {txtF19, txtF29, txtF39, txtF49, txtF59, txtF69, txtF79, txtF89, txtF99}};
        iterator = results.listIterator();
        if(results.isEmpty())
            System.out.println("Brak rozwiązań");
        else{
            current = iterator.next();
            solutions = current.getSudoku().getSolution();
            solutionIterator =  solutions.listIterator();
            currentSolution = solutionIterator.next();
            setInformation(current);
            fillSudokuPane(currentSolution);
        }
    }

    @FXML
    private void selectNextSolution(ActionEvent event){
        if(solutionIterator.hasNext()) {
            currentSolution = solutionIterator.next();
            labelCurrSol.setText((solutions.indexOf(currentSolution)+1) + "/" + current.getNumOfSolutions());
            fillSudokuPane(currentSolution);
        }
    }

    @FXML
    private void selectPrevSolution(ActionEvent event){
        if(solutionIterator.hasPrevious()) {
            currentSolution = solutionIterator.previous();
            labelCurrSol.setText((solutions.indexOf(currentSolution)+1) + "/" + current.getNumOfSolutions());
            fillSudokuPane(currentSolution);
        }
    }

    @FXML
    private void selectNextSudoku(ActionEvent event){
        if(iterator.hasNext()){
            current = iterator.next();
            solutions = current.getSudoku().getSolution();
            solutionIterator = solutions.listIterator();
            setInformation(current);
            if(solutionIterator.hasNext())
                fillSudokuPane(solutionIterator.next());
            if(current.getNumOfSolutions()==0)
                fillSudokuPane(current.getSudoku().getPola());
        }
    }

    @FXML
    private void selectPrevSudoku(ActionEvent event){
        if(iterator.hasPrevious()){
            current = iterator.previous();
            solutions = current.getSudoku().getSolution();
            solutionIterator = solutions.listIterator();
            setInformation(current);
            if(solutionIterator.hasNext())
                fillSudokuPane(solutionIterator.next());
            if(current.getNumOfSolutions()==0)
                fillSudokuPane(current.getSudoku().getPola());
        }
    }

    private void fillSudokuPane(ArrayList<Pole> pola){
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), new EventHandler<ActionEvent>() {
            int i=0;
            @Override
            public void handle(ActionEvent event) {
                if (pola.get(i).getInitial())
                    fields[pola.get(i).getY()][pola.get(i).getX()].setFont(Font.font("Segoe UI", FontWeight.BOLD, 20));
                else {
                    fields[pola.get(i).getY()][pola.get(i).getX()].setFont(Font.font("Segoe UI", FontWeight.NORMAL, 20));
                }
                fields[pola.get(i).getY()][pola.get(i).getX()].setStyle("-fx-text-fill: black");
                fields[pola.get(i).getY()][pola.get(i).getX()].setText(String.valueOf(pola.get(i).getCurrentValue()));
                i++;
            }
        }));
        timeline.setCycleCount(pola.size());
        timeline.play();
    }

    private void clearSudokuPane(){
        for (TextField[] tf : fields){
            for(TextField t : tf){
                t.setText("");
            }
        }
    }

    private void setInformation(Result current){
        labelCurr.setText((results.indexOf(current)+1) + "/" + results.size());
        labelCurrSol.setText("1/" + current.getNumOfSolutions());
        labelId.setText("ID: " + current.getSudoku().getId());
        labelFSDur.setText("Czas do znalezienia pierwszego rozwiązania: " + current.getFirstSolutionDurationInMs() +" ms");
        labelFSvisited.setText("Liczba odwiedzonych węzłów drzewa do znalezienia pierwszego rozwiązania: " + current.getVisitedTreeNodesToFirstSolution());
        labelFSBT.setText("Liczba nawrotów wykonanych do znalezienia pierwszego rozwiązania: " + current.getCountBackTrackingsToFirstSolution());
        labelDur.setText("Całkowity czas działania metody: " + current.getDurationInMs()+" ms");
        labelVisited.setText("Całkowita liczba odwiedzonych węzłów drzewa: " + current.getVisitedTreeNodes());
        labelBT.setText("Całkowita liczba nawrotów: " +current.getCountBackTrackings());
        labelFoundSolutions.setText("Znalezionych rozwiązań: " + current.getNumOfSolutions());
    }

    private void fillCell(int X, int Y, String value){
        fields[Y][X].setText(value);
    }

    private void clearCell(int X, int Y){
        fields[Y-1][X-1].setText("");
    }
}
