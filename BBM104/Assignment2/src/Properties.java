public abstract class Properties extends Square{

    private String name;
    private int cost;
    private int rent;
    private int owner;


    public Properties(String name, int cost) {
        this.name = name;
        this.cost = cost;
    }


    public String getName() {
        return name;
    }
    public int getCost() {
        return cost;
    }
    public int getRent() {
        return rent;
    }
    public int getOwner() {
        return owner;
    }
    public void setRent(int rent) {
        this.rent = rent;
    }
    public void setOwner(int owner) {
        this.owner = owner;
    }
}