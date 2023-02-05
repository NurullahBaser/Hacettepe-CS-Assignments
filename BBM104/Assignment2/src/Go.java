public class Go extends Others{

    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {
        Print.printProperty(thisPlayer,otherPlayer,"is in","GO Square");
    }
}
