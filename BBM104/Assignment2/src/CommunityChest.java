public class CommunityChest extends Square{

    public static int index;

    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {

        if (ListJsonReader.communityChestArray[index].equals("Advance to Go (Collect $200)")) {
            thisPlayer.setLocation(1);
            thisPlayer.changeMoney(200);
            Bank.money -= 200;
        }
        else if (ListJsonReader.communityChestArray[index].equals("Bank error in your favor - collect $75")) {
            thisPlayer.changeMoney(75);
            Bank.money -= 75;
        }
        else if (ListJsonReader.communityChestArray[index].equals("Doctor's fees - Pay $50") && thisPlayer.getMoney() >= 50) {
            thisPlayer.changeMoney(-50);
            Bank.money += 50;
        }
        else if (ListJsonReader.communityChestArray[index].equals("It is your birthday Collect $10 from each player")) {
            thisPlayer.changeMoney(10);
            if (otherPlayer.getMoney() >= 10) {
                otherPlayer.changeMoney(-10);
            }
            else { // FOR BANKRUPT
                Main.gameIsOver = true;
                Print.printProperty(otherPlayer,thisPlayer,"goes","bankrupt");
            }
        }
        else if (ListJsonReader.communityChestArray[index].equals("Grand Opera Night - collect $50 from every player for opening night seats")) {
            thisPlayer.changeMoney(50);
            if (otherPlayer.getMoney() >= 50) {
                otherPlayer.changeMoney(-50);
            }
            else { // FOR BANKRUPT
                Main.gameIsOver = true;
                Print.printProperty(otherPlayer,thisPlayer,"goes","bankrupt");
            }
        }
        else if (ListJsonReader.communityChestArray[index].equals("Income Tax refund - collect $20")) {
            thisPlayer.changeMoney(20);
            Bank.money -= 20;
        }
        else if (ListJsonReader.communityChestArray[index].equals("Life Insurance Matures - collect $100")) {
            thisPlayer.changeMoney(100);
            Bank.money -= 100;
        }
        else if (ListJsonReader.communityChestArray[index].equals("Pay Hospital Fees of $100")&& thisPlayer.getMoney() >= 100) {
            thisPlayer.changeMoney(-100);
            Bank.money += 100;
        }
        else if (ListJsonReader.communityChestArray[index].equals("Pay School Fees of $50")&& thisPlayer.getMoney() >= 50) {
            thisPlayer.changeMoney(-50);
            Bank.money += 50;
        }
        else if (ListJsonReader.communityChestArray[index].equals("You inherit $100")) {
            thisPlayer.changeMoney(100);
            Bank.money -= 100;
        }
        else if (ListJsonReader.communityChestArray[index].equals("From sale of stock you get $50")) {
            thisPlayer.changeMoney(50);
            Bank.money -= 50;
        }
        else { // FOR BANKRUPT
            Main.gameIsOver = true;
            Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
        }
        if (!Main.gameIsOver) {
            Print.printProperty(thisPlayer,otherPlayer,"draw",ListJsonReader.communityChestArray[index]);
            index += 1;
            index = index%11;
        }
    }
}
