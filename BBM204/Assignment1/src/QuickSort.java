public class QuickSort extends Sorts{
    @Override
    public void sort(int[] arr) {
        int low = 0;
        int high = arr.length - 1;
        int top = -1;

        int[] stack = new int[high-low+1];

        stack[++top] = low;
        stack[++top] = high;

        while (top >= 0) {
            high = stack[top--];
            low = stack[top--];

            int p = partition(arr,low,high);

            if (p - 1 > low) {
                stack[++top] = low;
                stack[++top] = p-1;
            }

            if (p+1 < high) {
                stack[++top] = p + 1;
                stack[++top] = high;
            }
        }
    }


    public int partition(int[] arr, int low, int high) {
        int pivot = arr[high];

        int i = low-1;
        for(int j = low; j < high; j++) {
            if (arr[j] <= pivot) {
                i++;
                swap(arr,i,j);
            }
        }
        swap(arr,i+1,high);
        return i + 1;
    }
}