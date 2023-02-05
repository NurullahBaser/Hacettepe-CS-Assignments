import java.util.ArrayList;

public class Player {

    private int money = 15_000;
    private String name;
    private int location = 1;
    private int numberOfTheRailroads;
    private int jailTime;
    private int dice;
    private boolean isInPark;
    private boolean isInJail;
    private ArrayList<String> lands = new ArrayList<>(); //To keep player's lands


    public Player(String name) { this.name = name;}


    public int getMoney() { return money;}
    public String getName() { return name;}
    public int getLocation() { return location;}
    public void setLocation(int location) { this.location = location;}
    public int getDice() { return dice;}
    public int getNumberOfTheRailroads() { return numberOfTheRailroads;}
    public void setNumberOfTheRailroads(int numberOfTheRailroads) { this.numberOfTheRailroads = numberOfTheRailroads;}
    public void setJailTime(int jailTime) { this.jailTime = jailTime;}
    public void setIsInJail(boolean isIn) { this.isInJail = isIn;}
    public void setIsInPark(boolean isIn) { this.isInPark = isIn;}
    public ArrayList<String> getLands() {return lands;}

    public void changeMoney(int m) { money += m;}

    public void playerMove(int dice, Player otherPlayer) {
        Square[] squares = PropertyJsonReader.squares;
        this.dice = dice;
        if (jailTime == 0) {
            isInJail = false; isInPark = false;
            location += dice;

            if (location > 40) { //This if statement gives to player 200 money
                location = location % 40;
                money += 200;
                Bank.money -= 200;
            }
            squares[location].doSomething(this,otherPlayer); // This is the main line of my code. This command performs the operation on whichever square it came to.
        }
        else {
            jailTime -= 1;
            if (isInPark) {
                Print.printProperty(this,otherPlayer,"wait for","Free Parking");
            }
            else if (isInJail) {
                Print.printProperty(this,otherPlayer,"in jail","(count="+(3-jailTime)+")");
            }
        }
    }
}
