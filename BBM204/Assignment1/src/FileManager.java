import java.io.BufferedReader;
import java.io.FileReader;

public class FileManager {

    public static int[] readCSVFile(String fileName,int size){
        try {
            int[] arr = new int[size];
            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            br.readLine();
            for(int i = 0; i < size; i++) {
                line = br.readLine();
                if(line != null) {
                    String[] cols = line.split(",");
                    arr[i] = Integer.parseInt(cols[6]);
                }
            }
            return arr;
        } catch (Exception e) {
            System.out.println("ERROR");
        }
        return null;
    }
}
