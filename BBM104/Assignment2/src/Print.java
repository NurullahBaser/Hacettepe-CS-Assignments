public class Print {
    public static String output = "";

    public static void printProperty(Player currentPlayer, Player otherPlayer, String process, String propertyName) { // Basic print method.
        String playerName = currentPlayer.getName();
        int dice = currentPlayer.getDice();
        int location = currentPlayer.getLocation();
        int money1;
        int money2;
        if (currentPlayer.getName().equals("Player 1")) {
            money1 = currentPlayer.getMoney();
            money2 = otherPlayer.getMoney();
        }
        else {
            money1 = otherPlayer.getMoney();
            money2 = currentPlayer.getMoney();
        }

        output += (playerName + "\t" + dice + "\t" + location + "\t" + money1 + "\t" + money2 + "\t" + playerName + " " + process + " " + propertyName + "\n");
    }

    public static void show(Player player1, Player player2) { // Show method
        String s1 = (player1.getLands().size() > 0) ? String.valueOf(player1.getLands().get(0)) : "";
        for (int i = 1 ; i < player1.getLands().size() ; i++) {
            s1 = s1 + "," + player1.getLands().get(i);
        }

        String s2 = (player2.getLands().size() > 0) ? String.valueOf(player2.getLands().get(0)) : "";
        for (int i = 1 ; i < player2.getLands().size() ; i++) {
            s2 = s2 + "," + player2.getLands().get(i);
        }
        output += ("-------------------------------------------------------------------------------------------------------------------------\n");
        output += (player1.getName() + "\t" + player1.getMoney() + "\t" + "have: " + s1 + "\n");
        output += (player2.getName() + "\t" + player2.getMoney() + "\t" + "have: " + s2 + "\n");
        output += ("Banker" + "\t" + Bank.money + "\n");
        if (player1.getMoney() > player2.getMoney()) {
            output += ("Winner" + "\t" + "Player 1" + "\n");
        }
        else if (player1.getMoney() < player2.getMoney()) {
            output += ("Winner "+ "\t" + "Player 2" + "\n");
        }
        output += ("-------------------------------------------------------------------------------------------------------------------------\n");
    }

    public static void printChance(Player currentPlayer, Player otherPlayer, String process1, String propertyName1, String process2, String propertyName2) {
        // This method is for writing 2 processes in the same line.
        String playerName = currentPlayer.getName();
        int dice = currentPlayer.getDice();
        int location = currentPlayer.getLocation();
        int money1;
        int money2;
        if (currentPlayer.getName().equals("Player 1")) {
            money1 = currentPlayer.getMoney();
            money2 = otherPlayer.getMoney();
        }
        else {
            money1 = otherPlayer.getMoney();
            money2 = currentPlayer.getMoney();
        }
        output += (playerName + "\t" + dice + "\t" + location + "\t" + money1 + "\t" + money2 + "\t" + playerName + " " + process1 + " " + propertyName1 + " " + playerName + " " + process2 + " " + propertyName2 + "\n");
    }
}


