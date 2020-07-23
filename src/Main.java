import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.ArrayList;

public class Main extends Application {

	private Parent rootNode;
	private ArrayList<Result> resultsContainer;

	public Main() {
		super();
	}

    public static void main(String[] args){
		launch(args);
    }

	@Override
	public void init() throws Exception {
		String path = "data/Sudoku.csv";
		Loader ld = new Loader();
		ArrayList<Sudoku> sudokuContainer = ld.readSudokuFile(path);
		resultsContainer = new ArrayList<>();

		SudokuSolver ss = new SudokuSolver();
		for (Sudoku s : sudokuContainer) {
			Result rs = ss.run(s, Constant.valueOrder.ORIGINAL, Constant.variableOrder.MostRestrictedVariable, Constant.technic.BACKTRACK_WITH_FC);
			System.out.println(rs.toString());
			resultsContainer.add(rs);
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SudokuPaneController spc = new SudokuPaneController(resultsContainer);
		FXMLLoader loader = new FXMLLoader(getClass().getResource("resources/sudoku_pane.fxml"));
		loader.setController(spc);
		rootNode = loader.load();
		Scene scene = new Scene(rootNode, 650, 650);
		primaryStage.setScene(scene);
		primaryStage.setResizable(false);
		primaryStage.centerOnScreen();
		primaryStage.show();
	}
}