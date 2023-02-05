public class Chance extends Square{

    public static int index;

    @Override
    public void doSomething(Player thisPlayer, Player otherPlayer) {
        if (ListJsonReader.changeArray[index].equals("Advance to Go (Collect $200)")) {
            thisPlayer.setLocation(1);
            thisPlayer.changeMoney(200);
            Bank.money -= 200;
            Print.printProperty(thisPlayer,otherPlayer,"draw",ListJsonReader.changeArray[index]);
        }
        else if (ListJsonReader.changeArray[index].equals("Advance to Leicester Square")){
            if (thisPlayer.getLocation() > 27) {
                thisPlayer.changeMoney(200);
                Bank.money -= 200;
            }
            thisPlayer.setLocation(27);
            landForChance(thisPlayer,otherPlayer,"Advance to Leicester Square");
        }
        else if (ListJsonReader.changeArray[index].equals("Go back 3 spaces")) {
            if (thisPlayer.getLocation() == 8 && thisPlayer.getMoney() >= 100) {
                thisPlayer.setLocation(thisPlayer.getLocation()-3);
                thisPlayer.changeMoney(-100);
                Bank.money += 100;
                Print.printChance(thisPlayer,otherPlayer,"draw","Go back 3 spaces","paid","Tax");
            }
            else if (thisPlayer.getLocation() == 23) {
                thisPlayer.setLocation(thisPlayer.getLocation()-3);
                landForChance(thisPlayer,otherPlayer,"Go back 3 spaces");
            }
            else if (thisPlayer.getLocation() == 37) {
                thisPlayer.setLocation(thisPlayer.getLocation()-3);
                chestForChance(thisPlayer,otherPlayer);
            }
            else {// FOR BANKRUPT
                Main.gameIsOver = true;
                Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
            }
        }
        else if (ListJsonReader.changeArray[index].equals("Pay poor tax of $15") && thisPlayer.getMoney() >= 15) {
            thisPlayer.changeMoney(-15);
            Bank.money += 15;
            Print.printProperty(thisPlayer,otherPlayer,"draw",ListJsonReader.changeArray[index]);
        }
        else if (ListJsonReader.changeArray[index].equals("Your building loan matures - collect $150")) {
            thisPlayer.changeMoney(150);
            Bank.money -= 150;
            Print.printProperty(thisPlayer,otherPlayer,"draw",ListJsonReader.changeArray[index]);
        }
        else if (ListJsonReader.changeArray[index].equals("You have won a crossword competition - collect $100 ")) {
            thisPlayer.changeMoney(100);
            Bank.money -= 100;
            Print.printProperty(thisPlayer,otherPlayer,"draw",ListJsonReader.changeArray[index]);
        }
        else { // FOR BANKRUPT
            Main.gameIsOver = true;
            Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
        }
        index += 1;
        index = index%6;
    }

    private void landForChance(Player thisPlayer, Player otherPlayer, String propertyName) {
        // This method makes it possible to go somewhere else when you withdraw a chance card.
        // It also allows you to write the output side by side.
        Square[] squares = PropertyJsonReader.squares;
        int thisPlayerID = Integer.parseInt(thisPlayer.getName().substring(7));
        if (((Land) squares[thisPlayer.getLocation()]).getOwner() == 0) { // FOR BUY
            if (thisPlayer.getMoney() >= ((Land) squares[thisPlayer.getLocation()]).getCost()) {
                thisPlayer.changeMoney(-((Land) squares[thisPlayer.getLocation()]).getCost());
                Bank.money += ((Land) squares[thisPlayer.getLocation()]).getCost();
                ((Land) squares[thisPlayer.getLocation()]).setOwner(thisPlayerID);
                Print.printChance(thisPlayer,otherPlayer,"draw",propertyName,"bought",((Land) squares[thisPlayer.getLocation()]).getName());
                thisPlayer.getLands().add(((Land) squares[thisPlayer.getLocation()]).getName());
            }
            else { // FOR BANKRUPT
                Main.gameIsOver = true;
                Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
            }
        }
        else if (((Land) squares[thisPlayer.getLocation()]).getOwner() != thisPlayerID && thisPlayer.getMoney() >= ((Land) squares[thisPlayer.getLocation()]).getRent()) { // FOR RENT
            thisPlayer.changeMoney(-((Land) squares[thisPlayer.getLocation()]).getRent());
            otherPlayer.changeMoney(((Land) squares[thisPlayer.getLocation()]).getRent());
            Print.printChance(thisPlayer,otherPlayer,"draw",propertyName,"paid rent for",((Land) squares[thisPlayer.getLocation()]).getName());
        }
        else if (((Land) squares[thisPlayer.getLocation()]).getOwner() == thisPlayerID) { // FOR HAS
            Print.printChance(thisPlayer,otherPlayer,"draw",propertyName,"has",((Land) squares[thisPlayer.getLocation()]).getName());
        }
        else { // FOR BANKRUPT
            Main.gameIsOver = true;
            Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
        }
    }

    private void chestForChance(Player thisPlayer, Player otherPlayer) {
        // This method makes it possible to draw community card when you withdraw a chance card.
        // It also allows you to write the output side by side.
        if (ListJsonReader.communityChestArray[CommunityChest.index].equals("Advance to Go (Collect $200)")) {
            thisPlayer.setLocation(1);
            thisPlayer.changeMoney(200);
            Bank.money -= 200;
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("Bank error in your favor - collect $75")) {
            thisPlayer.changeMoney(75);
            Bank.money -= 75;
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("Doctor's fees - Pay $50") && thisPlayer.getMoney() >= 50) {
            thisPlayer.changeMoney(-50);
            Bank.money += 50;
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("It is your birthday Collect $10 from each player")) {
            thisPlayer.changeMoney(10);
            if (otherPlayer.getMoney() >= 10) {
                otherPlayer.changeMoney(-10);
            }
            else { // FOR BANKRUPT
                Main.gameIsOver = true;
                Print.printProperty(otherPlayer,thisPlayer,"goes","bankrupt");
            }
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("Grand Opera Night - collect $50 from every player for opening night seats")) {
            thisPlayer.changeMoney(50);
            if (otherPlayer.getMoney() >= 50) {
                otherPlayer.changeMoney(-50);
            }
            else { // FOR BANKRUPT
                Main.gameIsOver = true;
                Print.printProperty(otherPlayer,thisPlayer,"goes","bankrupt");
            }
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("Income Tax refund - collect $20")) {
            thisPlayer.changeMoney(20);
            Bank.money -= 20;
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("Life Insurance Matures - collect $100")) {
            thisPlayer.changeMoney(100);
            Bank.money -= 100;
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("Pay Hospital Fees of $100") && thisPlayer.getMoney() >= 100) {
            thisPlayer.changeMoney(-100);
            Bank.money += 100;
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("Pay School Fees of $50") && thisPlayer.getMoney() >= 50) {
            thisPlayer.changeMoney(-50);
            Bank.money += 50;
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("You inherit $100")) {
            thisPlayer.changeMoney(100);
            Bank.money -= 100;
        }
        else if (ListJsonReader.communityChestArray[CommunityChest.index].equals("From sale of stock you get $50")) {
            thisPlayer.changeMoney(50);
            Bank.money -= 50;
        }
        else { // FOR BANKRUPT
            Main.gameIsOver = true;
            Print.printProperty(thisPlayer,otherPlayer,"goes","bankrupt");
        }
        if (!Main.gameIsOver) {
            Print.printChance(thisPlayer,otherPlayer,"draw", "Go back 3 spaces","draw",ListJsonReader.communityChestArray[CommunityChest.index]);
            CommunityChest.index += 1;
            CommunityChest.index = CommunityChest.index%11;
        }
    }
}