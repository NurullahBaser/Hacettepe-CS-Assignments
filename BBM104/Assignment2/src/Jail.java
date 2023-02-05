public class Jail extends Others{

    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {
        thisPlayer.setJailTime(3);
        Print.printProperty(thisPlayer,otherPlayer,"went to","jail");
        thisPlayer.setIsInJail(true);
    }
}
