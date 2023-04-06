public abstract class Searches {

    public double[] randomInputTimes = new double[10];
    public double[] sortedInputTimes = new double[10];
    int randomIndex = 0;
    int sortedIndex = 0;

    public abstract int search(int[] arr, int target);

    public void manager(int[] arr, InputType inputType, String name) {
        long totalTime = 0;

        for(int i = 0; i < 1000; i++) {
            int index = (int)(Math.random() * ((arr.length)-1));
            long startTime = System.nanoTime();
            search(arr,arr[index]);
            long finishTime = System.nanoTime();
            totalTime += finishTime - startTime;
        }

        switch (inputType) {
            case random:
                System.out.println(name + " " + arr.length + " " + "random" + " " + ((double)totalTime/1000));
                randomInputTimes[randomIndex++] = ((double)totalTime/1000);
                break;
            case sorted:
                System.out.println(name + " " + arr.length + " " + "sorted" + " " + ((double)totalTime/1000));
                sortedInputTimes[sortedIndex++] = ((double)totalTime/1000);
                break;
        }
    }
}