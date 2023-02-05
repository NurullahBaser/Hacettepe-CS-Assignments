import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class MatrixStuff {
    public static ArrayList<Jewels[]> matrix = new ArrayList<>();

    public static void creatMatrix(String text) {
        /*
        To create matrix.
        This method reads the gameGrid.txt and creates a matrix with jewel objects.
         */
        try {
            File command = new File(text);
            Scanner sc = new Scanner(command);
            while (sc.hasNext()) {
                String[] arr = (sc.nextLine().replace("\n"," ").split(" "));
                Jewels[] jarr = new Jewels[arr.length];
                for (int i = 0 ; i < arr.length ; i++) {
                    switch (arr[i]) {
                        case "T":
                            jarr[i] = new TJewel("T", 15);
                            break;
                        case "D":
                            jarr[i] = new DJewel("D", 30);
                            break;
                        case "S":
                            jarr[i] = new SJewel("S", 15);
                            break;
                        case "W":
                            jarr[i] = new WJewel("W", 10);
                            break;
                        case "/":
                            jarr[i] = new SlashJewel("/", 20);
                            break;
                        case "-":
                            jarr[i] = new MinusJewel("-", 20);
                            break;
                        case "+":
                            jarr[i] = new PlusJewel("+", 20);
                            break;
                        case "\\":
                            jarr[i] = new BackSlashJewel("\\", 20);
                            break;
                        case "|":
                            jarr[i] = new VerticalJewel("|", 20);
                            break;
                    }
                }
                matrix.add(jarr);
            }
        }
        catch (FileNotFoundException e) {
            //do not anything
        }
    }

    public static void showMatrix(PrintWriter writer) {
        /*
        This method prints the matrix on the monitoring.txt.
        If the matrix[x](y) is null print " ".
         */
        for (Jewels[] arr : matrix) {
            for (int i = 0; i < matrix.get(0).length; i++) {
                if (arr[i] == null) {
                    writer.printf(" " + " ");
                }
                else {
                    writer.printf(arr[i].name + " ");
                }
            }
            writer.println();
        }
        writer.println();
    }

    public static void gravityMatrix() {
        /*
        This method allows the jewels to fall down.
         */
        boolean over = false;
        while (!over) {
            over = true;
            for (int i = 1 ; i < matrix.size() ; i ++) {
                for(int j = 0 ; j < matrix.get(0).length ; j ++) {
                    if (matrix.get(i)[j] == (null) && !(matrix.get(i-1)[j] == (null))) {
                        matrix.get(i)[j] = matrix.get(i-1)[j];
                        matrix.get(i-1)[j] = null;
                        over = false;
                    }
                }
            }
        }
    }
}
