public class GoToJail extends Others{

    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {
        thisPlayer.setLocation(11);
        thisPlayer.setJailTime(3);
        Print.printProperty(thisPlayer,otherPlayer,"went to","jail");
        thisPlayer.setIsInJail(true);
    }
}
