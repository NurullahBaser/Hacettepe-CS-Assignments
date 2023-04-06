import org.knowm.xchart.*;
import org.knowm.xchart.style.Styler;

import java.io.IOException;

public class Chart {
                        //SELECTION - QUICK - BUCKET - JAVA
    public static void chartForSorts(Sorts[] arr) throws IOException {

        double[] inputAxis = {500,1000,2000,4000,8000,16000,32000,64000,128000,250000};

        double[][] allAlgorithms = new double[4][10];
        String[] sortName = {"Selection Sort", "Quick Sort", "Bucket Sort", "Java Built-in Sort"};

        //FOR RANDOM INPUT FOR ALL ALGORITHMS
        for(int i = 0; i < arr.length; i++) {
            allAlgorithms[i] = arr[i].randomInputTimes;
        }
        showAndSaveChart("Sort Algorithms Random Input Test",sortName,inputAxis,allAlgorithms,"Time in Milliseconds");


        //FOR SORTED INPUT FOR ALL ALGORITHMS
        for(int i = 0; i < arr.length; i++) {
            allAlgorithms[i] = arr[i].sortedInputTimes;
        }
        showAndSaveChart("Sort Algorithms Sorted Input Test",sortName,inputAxis,allAlgorithms,"Time in Milliseconds");

        //FOR REVERSELY SORTED INPUT FOR ALL ALGORITHMS
        for(int i = 0; i < arr.length; i++) {
            allAlgorithms[i] = arr[i].reverselySortedInputTimes;
        }
        showAndSaveChart("Sort Algorithms Reversely Sorted Input Test",sortName,inputAxis,allAlgorithms,"Time in Milliseconds");


        double[][] allInputType = new double[3][10];
        String[] inputType = {"Random Input", "Sorted Input", "Reversely Sorted Input"};

        //FOR SELECTION SORT ALGORITHMS
        allInputType[0] = arr[0].randomInputTimes;
        allInputType[1] = arr[0].sortedInputTimes;
        allInputType[2] = arr[0].reverselySortedInputTimes;
        showAndSaveChart("Selection Sort Test", inputType,inputAxis,allInputType,"Time in Milliseconds");

        //FOR QUICK SORT ALGORITHMS
        allInputType[0] = arr[1].randomInputTimes;
        allInputType[1] = arr[1].sortedInputTimes;
        allInputType[2] = arr[1].reverselySortedInputTimes;
        showAndSaveChart("QuickSort Sort Test", inputType,inputAxis,allInputType,"Time in Milliseconds");

        //FOR BUCKET SORT ALGORITHMS
        allInputType[0] = arr[2].randomInputTimes;
        allInputType[1] = arr[2].sortedInputTimes;
        allInputType[2] = arr[2].reverselySortedInputTimes;
        showAndSaveChart("Bucket Sort Test", inputType,inputAxis,allInputType,"Time in Milliseconds");
    }

    public static void chartForSearch(LinearSearch linearSearch, BinarySearch binarySearch) throws IOException {

        double[] inputAxis = {500, 1000, 2000, 4000, 8000, 16000, 32000, 64000, 128000, 250000};
        double[][] outputAxis = new double[3][10];
        String[] searchName = {"Linear Search Random", "Linear Search Sorted", "Binary Search"};

        outputAxis[0] = linearSearch.randomInputTimes;
        outputAxis[1] = linearSearch.sortedInputTimes;
        outputAxis[2] = binarySearch.sortedInputTimes;
        showAndSaveChart("Search Algorithms",searchName,inputAxis,outputAxis,"Time in Nanoseconds");
    }

    public static void showAndSaveChart(String title, String[] data, double[] xAxis, double[][] yAxis, String time) throws IOException {
        // Create Chart
        XYChart chart = new XYChartBuilder().width(800).height(600).title(title)
                .yAxisTitle(time).xAxisTitle("Input Size").build();

        // Customize Chart
        chart.getStyler().setLegendPosition(Styler.LegendPosition.InsideNE);
        chart.getStyler().setDefaultSeriesRenderStyle(XYSeries.XYSeriesRenderStyle.Line);

        // Add a plot for a sorting algorithm
        for(int i = 0; i < data.length; i++) {
            chart.addSeries(data[i], xAxis, yAxis[i]);
        }


        // Save the chart as PNG
        BitmapEncoder.saveBitmap(chart, title + ".png", BitmapEncoder.BitmapFormat.PNG);

        // Show the chart
        new SwingWrapper(chart).displayChart();
    }
}
