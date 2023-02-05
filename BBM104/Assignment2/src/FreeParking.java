public class FreeParking extends Others{

    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {
        thisPlayer.setJailTime(1); //I used the same variable as jail because it's logically the same thing.
        Print.printProperty(thisPlayer,otherPlayer,"is in","Free Parking");
        thisPlayer.setIsInPark(true);
    }
}
