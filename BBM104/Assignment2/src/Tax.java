public class Tax extends Others{

    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {
        if (thisPlayer.getMoney() >= 100) {
            thisPlayer.changeMoney(-100);
            Bank.money += 100;
            Print.printProperty(thisPlayer,otherPlayer,"paid","Tax");
        }
        else { //FOR BANKRUPT
            Main.gameIsOver = true;
            Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
        }
    }
}
