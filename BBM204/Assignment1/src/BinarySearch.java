public class BinarySearch extends Searches{
    @Override
    public int search(int[] arr, int target) {
        int low = 0;
        int high = arr.length - 1;

        while(low <= high) {
            int mid = low + (high-low)/2;

            if(arr[mid] == target) return mid;
            if(arr[mid] > target) high = mid-1;
            else if(arr[mid] < target) low = mid+1;
        }
        return -1;
    }
}
