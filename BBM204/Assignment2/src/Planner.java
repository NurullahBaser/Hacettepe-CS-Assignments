import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

public class Planner {

    public final Task[] taskArray;
    public final Integer[] compatibility;
    public final Double[] maxWeight;
    public final ArrayList<Task> planDynamic;
    public final ArrayList<Task> planGreedy;

    public Planner(Task[] taskArray) {

        // Should be instantiated with a Task array
        // All the properties of this class should be initialized here

        this.taskArray = taskArray;
        this.compatibility = new Integer[taskArray.length];
        maxWeight = new Double[taskArray.length];

        this.planDynamic = new ArrayList<>();
        this.planGreedy = new ArrayList<>();
    }

    /**
     * @param index of the {@link Task}
     * @return Returns the index of the last compatible {@link Task},
     * returns -1 if there are no compatible {@link Task}s.
     */
    public int binarySearch(int index) {
        int low = 0;
        int high = index - 1;

        while(low <= high) {
            int mid = low + (high-low)/2;

            if(!LocalTime.parse(taskArray[mid].getFinishTime()).isAfter(LocalTime.parse(taskArray[index].getStartTime()))) {
                if(!LocalTime.parse(taskArray[mid + 1].getFinishTime()).isAfter(LocalTime.parse(taskArray[index].getStartTime()))) low = mid + 1;
                else return mid;
            }
            else high = mid - 1;
        }
        return -1;
    }


    /**
     * {@link #compatibility} must be filled after calling this method
     */
    public void calculateCompatibility() {
        for(int i = 0; i < compatibility.length; i++) compatibility[i] = binarySearch(i);
    }


    /**
     * Uses {@link #taskArray} property
     * This function is for generating a plan using the dynamic programming approach.
     * @return Returns a list of planned tasks.
     */
    public ArrayList<Task> planDynamic() {
        System.out.println("Calculating max array\n---------------------");
        calculateCompatibility();
        calculateMaxWeight(taskArray.length-1);
        System.out.println("\nCalculating the dynamic solution\n--------------------------------");
        solveDynamic(taskArray.length-1);
        System.out.println();
        printTasks("Dynamic Schedule\n----------------",planDynamic);
        return planDynamic;
    }


    /**
     * {@link #planDynamic} must be filled after calling this method
     */
    public void solveDynamic(int i) {
        if(i<0) return;

        System.out.println("Called solveDynamic(" + i + ")");

        boolean isSafe = compatibility[i] != -1;

        if(isSafe && maxWeight[i] > maxWeight[i-1]){
            solveDynamic(compatibility[i]);
            planDynamic.add(taskArray[i]);
        }else if (!isSafe) {
            planDynamic.add(taskArray[i]);
        }else solveDynamic(i-1);
    }

    /**
     * {@link #maxWeight} must be filled after calling this method
     */
    /* This function calculates maximum weights and prints out whether it has been called before or not  */
    public Double calculateMaxWeight(int i) {
        System.out.println("Called calculateMaxWeight(" + i + ")");
        if (i < 0) {
            return 0d;
        }

        if(maxWeight[i] != null && i != 0) {
            return maxWeight[i];
        }


        maxWeight[i] = Math.max(taskArray[i].getWeight()+calculateMaxWeight(compatibility[i]),calculateMaxWeight(i-1));
        return maxWeight[i];
    }

    /**
     * {@link #planGreedy} must be filled after calling this method
     * Uses {@link #taskArray} property
     *
     * @return Returns a list of scheduled assignments
     */

    /*
     * This function is for generating a plan using the greedy approach.
     * */
    public ArrayList<Task> planGreedy() {
        String finishTime = taskArray[0].getFinishTime();
        planGreedy.add(taskArray[0]);
        for (int i = 1; i < taskArray.length; i++) {
            if(!LocalTime.parse(finishTime).isAfter(LocalTime.parse(taskArray[i].getStartTime()))) {
                finishTime = taskArray[i].getFinishTime();
                planGreedy.add(taskArray[i]);
            }
        }
        printTasks("Greedy Schedule\n---------------",planGreedy);
        return planGreedy;
    }

    /**
     * @param title of the given arrayList
     * @param arrayList is the list of elements to be used
     * This function prints the start time and name of each element in the given arrayList.
     */
    public void printTasks(String title, ArrayList<Task> arrayList) {
        System.out.println(title);
        for(Task task : arrayList) {
            System.out.println("At " + task.getStartTime() + ", " + task.getName() + ".");
        }
    }
}
