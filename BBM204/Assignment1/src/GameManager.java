import java.io.IOException;
import java.util.Arrays;

public class GameManager {

    int[] arrSizes = {500,1000,2000,4000,8000,16000,32000,64000,128000,250000};

    int[][] arrays;

    SelectionSort selectionSort;
    QuickSort quickSort;
    BucketSort bucketSort;
    JavaSort javaSort ;
    LinearSearch linearSearch;
    BinarySearch binarySearch;

    public void startGame() {
        selectionSort = new SelectionSort();
        quickSort = new QuickSort();
        bucketSort = new BucketSort();
        javaSort = new JavaSort();
        linearSearch = new LinearSearch();
        binarySearch = new BinarySearch();
        arrays = new int[arrSizes.length][];

    }

    public void runGame(String[] args) {
        for(int i = 0; i < arrSizes.length; i++) {
            arrays[i] = FileManager.readCSVFile(args[0], arrSizes[i]);

            //Random Input For Selection, Quick, Bucket and Java Builtin Sort Algorithms
            selectionSort.manager(arrays[i],InputType.random,"selection");
            quickSort.manager(arrays[i],InputType.random,"quick");
            bucketSort.manager(arrays[i],InputType.random,"bucket");
            javaSort.manager(arrays[i],InputType.random,"java");
            linearSearch.manager(arrays[i],InputType.random,"linear");

            Arrays.sort(arrays[i]); // Sort Array for second part

            //Sorted Input For Selection, Quick, Bucket and Java Builtin Sort Algorithms
            selectionSort.manager(arrays[i],InputType.sorted,"selection");
            quickSort.manager(arrays[i],InputType.sorted,"quick");
            bucketSort.manager(arrays[i],InputType.sorted,"bucket");
            javaSort.manager(arrays[i],InputType.sorted,"java");
            linearSearch.manager(arrays[i],InputType.sorted,"linear");
            binarySearch.manager(arrays[i],InputType.sorted, "binary");

            reverselySort(arrays[i]); // Reversely Sort Array for third part

            //Reversely Sorted Input For Selection, Quick, Bucket and Java Builtin Sort Algorithms
            selectionSort.manager(arrays[i],InputType.reverselySorted,"selection");
            quickSort.manager(arrays[i],InputType.reverselySorted,"quick");
            bucketSort.manager(arrays[i],InputType.reverselySorted,"bucket");
            javaSort.manager(arrays[i],InputType.reverselySorted,"java");
        }
    }

    public void setGraphs() throws IOException {
        Sorts[] arr = {selectionSort,quickSort,bucketSort,javaSort};
        Chart.chartForSorts(arr);
        Chart.chartForSearch(linearSearch,binarySearch);
    }

    public void reverselySort(int[] arr){
        Arrays.sort(arr);
        for (int i = 0; i < arr.length / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[arr.length - 1 - i];
            arr[arr.length - 1 - i] = temp;
        }
    }
}
