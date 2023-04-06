public class LinearSearch extends Searches{
    @Override
    public int search(int[] arr, int target) {
        for(int i = 0; i <  arr.length-1; i++)
            if(arr[i] == target)
                return i;
        return -1;
    }
}
