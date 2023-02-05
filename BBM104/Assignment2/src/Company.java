public class Company extends Properties{

    public Company(String name, int cost) {
        super(name, cost);

    }

    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {
        int thisPlayerID = Integer.parseInt(thisPlayer.getName().substring(7)); //substring(7) because player's id is the seventh index of player name.
        if (getOwner() == 0){ //FOR BUY
            if (thisPlayer.getMoney() >= getCost()) {
                thisPlayer.changeMoney(-getCost());
                Bank.money += getCost();
                setOwner(thisPlayerID);
                Print.printProperty(thisPlayer,otherPlayer,"bought",getName());
                thisPlayer.getLands().add(getName());
            }
            else { // FOR BANKRUPT
                Main.gameIsOver = true;
                Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
            }
        }
        else if (getOwner() != thisPlayerID && thisPlayer.getMoney() >= (4*thisPlayer.getDice())) { // FOR RENT
            thisPlayer.changeMoney(-(4*thisPlayer.getDice()));
            otherPlayer.changeMoney(4*thisPlayer.getDice());
            Print.printProperty(thisPlayer,otherPlayer,"paid rent for",getName());
        }
        else if (getOwner() == thisPlayerID) { // FOR HAS
            Print.printProperty(thisPlayer,otherPlayer,"has",getName());
        }
        else { // FOR BANKRUPT
            Main.gameIsOver = true;
            Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
        }
    }
}
