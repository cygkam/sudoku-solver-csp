import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Loader {

    public ArrayList<Sudoku> readSudokuFile(String nameFile) {
        ArrayList<Sudoku> readSudokus = new ArrayList<>();

        File myObj = new File(nameFile);
        try {
            Scanner scanner = new Scanner(myObj);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                readSudokus.add(getSudokuFromLine(scanner.nextLine()));
            }
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return readSudokus;
    }

    private Sudoku getSudokuFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try {
            Scanner rowScanner = new Scanner(line);
            rowScanner.useDelimiter(";");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return new Sudoku(Integer.parseInt(values.get(0)), Double.parseDouble(values.get(1)),values.get(2));
    }
}
