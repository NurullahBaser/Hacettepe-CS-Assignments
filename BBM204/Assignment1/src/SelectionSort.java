public class SelectionSort extends Sorts {
    @Override
    public void sort(int[] arr) {
        int n = arr.length;

        for(int i = 0; i < n-1; i++) {
            int index = i;
            for(int j = i+1; j < n; j++) {
                if (arr[j] < arr[index]) index = j;
            }
            swap(arr,i,index);
        }
    }
}