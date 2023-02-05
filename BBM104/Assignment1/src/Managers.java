public class Managers {

    public static int gainCalorie(Food food, int portion) {
        return food.calorieCount * portion;
    }
    public static long burnCalorie(Sport sport, int minute) {
        return Math.round(sport.calorieBurned * minute / 60.0);
    }
    public static int findPosition(InheritanceClass[] array, String command){  //This method finds an index of the array for people, food and sports.
        int chosenID = 0;
        for (int i = 0; i < array.length ; i++) {
            if (array[i].ID.equals(command)) {
                chosenID = i;
                break;
            }
        }
        return chosenID;
    }
}
