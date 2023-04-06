import java.util.ArrayList;
import java.util.Collections;

public class BucketSort extends Sorts{
    @Override
    public void sort(int[] arr) {
        int n = (int) (Math.sqrt(arr.length));

        ArrayList<Integer>[] bucket = new ArrayList[n];

        for(int i = 0; i < n; i++) {
            bucket[i] = new ArrayList<>();
        }

        int max = arr[0];
        for(int element : arr) {
               max = Math.max(max,element);
        }

        for (int element : arr) {
            int bucketIndex = hash(element,max,n);
            bucket[bucketIndex].add(element);
        }

        for(int i = 0; i < n; i++) {
            Collections.sort(bucket[i]);
        }

        int index = 0;
        for(int i = 0; i < n; i++) {
            for (int j = 0, size = bucket[i].size(); j < size; j++) {
                arr[index++] = bucket[i].get(j);
            }
        }
    }

    private int hash(int i, int max, int n) {
        return (int) ((double) i / max * (n-1));
    }

}
