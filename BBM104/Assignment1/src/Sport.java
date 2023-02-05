public class Sport extends  InheritanceClass {
    String nameOfSport;
    int calorieBurned;
    
    public Sport(String sportId,String nameOfSport , int calorieBurned) {
        this.ID = sportId;
        this.nameOfSport = nameOfSport;
        this.calorieBurned= calorieBurned;
    }
}
