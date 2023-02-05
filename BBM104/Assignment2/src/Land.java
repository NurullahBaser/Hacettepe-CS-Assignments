public class Land extends Properties{

    public Land(String name, int cost) {
        super(name, cost);

        if (cost >= 0 && cost <= 2000) setRent((int) (cost*0.4));
        else if (cost >= 2001 && cost <=3000) setRent((int) (cost*0.3));
        else setRent((int) (cost*0.35));

    }
    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {
        int thisPlayerID = Integer.parseInt(thisPlayer.getName().substring(7)); //substring(7) because player's id is the seventh index of player name.
        if(getOwner() == 0 ) { // FOR BUY
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
        else if (getOwner() != thisPlayerID && thisPlayer.getMoney() >= getRent()) { // FOR RENT
            thisPlayer.changeMoney(-getRent());
            otherPlayer.changeMoney(getRent());
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

