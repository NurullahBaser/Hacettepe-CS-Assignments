import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {
    public static boolean gameIsOver;

    public static void main(String[] args) throws IOException {
        new PropertyJsonReader(); // I run PropertyJsonReader (In this class, there is a static array for use later.[squares] )
        new ListJsonReader(); // I run ListJsonReader (In this class, there is 2 static array for use later. [communityChest, chance])
        File input = new File(args[0]);
        Scanner scanInput = new Scanner(input);
        File output = new File("monitoring.txt");
        FileWriter writer = new FileWriter(output);
        Player player1 = new Player("Player 1");
        Player player2 = new Player("Player 2");

        while (scanInput.hasNext() && !gameIsOver && player1.getMoney() > 0 && player2.getMoney() > 0) {
            String[] commands = scanInput.nextLine().split(";");
            if (commands[0].equals("show()")) {
                Print.show(player1,player2);
            }
            else if (commands[0].equals("Player 1")) {
                    player1.playerMove(Integer.parseInt(commands[1]),player2);
                }
            else if (commands[0].equals("Player 2")){
                    player2.playerMove(Integer.parseInt(commands[1]),player1);
            }
        }
        Print.show(player1,player2);
        writer.write(Print.output);
        writer.close();
    }
}

