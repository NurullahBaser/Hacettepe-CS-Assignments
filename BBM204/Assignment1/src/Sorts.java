import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public abstract class Sorts {

    public double[] randomInputTimes = new double[10];
    public double[] sortedInputTimes = new double[10];
    public double[] reverselySortedInputTimes = new double[10];
    int randomIndex = 0;
    int sortedIndex = 0;
    int reverselyIndex = 0;

    protected void swap(int[] arr, int i , int j) {
        arr[j] ^= arr[i];
        arr[i] ^= arr[j];
        arr[j] ^= arr[i];
    }

    public abstract void sort(int[] arr);

    public void manager(int[] arr,InputType inputType,String name) {
        long totalTime = 0;

        for(int i = 0; i < 10 ; i++) {
            int[] copyArr = Arrays.copyOf(arr,arr.length);

            long startTime = System.nanoTime();
            sort(copyArr);
            long finishTime = System.nanoTime();

            totalTime += finishTime - startTime;
        }
        totalTime = TimeUnit.NANOSECONDS.toMillis(totalTime);

        switch (inputType) {
            case random:
                System.out.println(name + " " + arr.length + " " + "random" + " " + ((double)totalTime/10));
                randomInputTimes[randomIndex++] = ((double)totalTime/10);
                break;
            case sorted:
                System.out.println(name + " " + arr.length + " " + "sorted" + " " + ((double)totalTime/10));
                sortedInputTimes[sortedIndex++] = ((double)totalTime/10);
                break;
            case reverselySorted:
                System.out.println(name + " " + arr.length + " " + "reversely" + " " + ((double)totalTime/10));
                reverselySortedInputTimes[reverselyIndex++] = ((double)totalTime/10);
                break;
        }
    }
}