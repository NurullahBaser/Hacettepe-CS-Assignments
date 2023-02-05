import java.io.*;
import java.util.*;

public class GameManager {

    public static int totalScore;
    public static ArrayList<Player> players = new ArrayList<Player>();

    public static void runGame(String text) throws IOException {
        /*
        This method does all of game's operation.
        Reading command.txt, does its operation, writing monitoring.txt
         */
        File command = new File(text);
        Scanner sc = new Scanner(command);
        File output = new File("monitoring.txt");
        PrintWriter writer = new PrintWriter(output);

        writer.println("Game grid:\n");
        MatrixStuff.showMatrix(writer);

        while (sc.hasNext()) {
            /*
            This while statement reads the command.txt and does its operations.
             */
            try {
                writer.printf("Select coordinate or enter E to end the game: ");
                String[] com = sc.nextLine().split(" ");

                if (com[0].equals("E")) { // For exit command.
                    writer.println(com[0] + "\n");
                    writer.printf("Total score: %d points\n\n", totalScore);
                    String playerName = sc.nextLine(); //For taking name
                    writer.printf("Enter name: " + playerName + "\n\n");
                    Player newPlayer = new Player(playerName,totalScore);
                    players.add(newPlayer);
                    changeLeaderBoard();
                    printRank(writer, newPlayer);
                }

                else { //For pop commands.
                    int score = 0;
                    writer.printf(com[0] + " " + com[1] + "\n\n");
                    Jewels jewel = MatrixStuff.matrix.get(Integer.parseInt(com[0]))[Integer.parseInt(com[1])];
                    if (jewel == null) {
                        throw new IndexOutOfBoundsException(); // If we chose an empty space in the matrix then throw an Index Exception.
                    }
                    else { //This line is the main line in the code. It calls the pop method and allows the game's operations to start.
                        score = jewel.pop(Integer.parseInt(com[0]), Integer.parseInt(com[1]));
                    }
                    MatrixStuff.showMatrix(writer);
                    writer.printf("Score: %d points\n\n", score);
                }
            } catch (IndexOutOfBoundsException e) {
                /*
                If the chosen matrix is empty, or we chose a coordinate that is not in the matrix then print this statement.
                 */
                writer.println("Please enter a valid coordinate\n");
            }
        }
        writer.close();
    }

    public static void leaderBoardReader() throws FileNotFoundException {
        /*
        This method reads the leaderboard.txt and creates players with names and scores in this text file.
         */
        File command = new File("leaderboard.txt");
        Scanner sc = new Scanner(command);
        while (sc.hasNext()) {
            String[] arr = sc.nextLine().split(" ");
            players.add(new Player(arr[0],Integer.parseInt(arr[1])));
        }
    }

    public static void printRank(PrintWriter writer, Player newPlayer){
        /*
        This method for last print in the game.
         */
        Collections.sort(players);
        int rank = Collections.binarySearch(players,newPlayer);

        if (rank == 0) {
            /*
            If the player is the first in the ranking print this statement.
             */
            writer.printf("Your rank is 1/%d, your score is %d points higher than %s\n\nGood bye!"
            ,players.size(),totalScore-players.get(1).getScore(),players.get(1).getName());
        }
        else if (rank == players.size()-1) {
            /*
            If the player is the last in the ranking print this statement.
             */
            writer.printf("Your rank is %d/%d, your score is %d points lower than %s\n\nGood bye!"
            ,players.size(),players.size(),players.get(players.size()-2).getScore()-totalScore,players.get(players.size()-2).getName());
        }
        else {
            /*
            If the player is in the middle of the ranking print this statement.
             */
            writer.printf("Your rank is %d/%d, your score is %d points lower than %s and %d points higher than %s\n\nGood bye!"
            ,(rank+1),players.size(),players.get(rank-1).getScore()-totalScore, players.get(rank-1).getName(),totalScore-players.get(rank+1).getScore(), players.get(rank+1).getName());
        }
    }

    public static void changeLeaderBoard() throws IOException {
        /*
        This method writes leaderboard.txt with new player.
         */
        File board = new File("leaderboard.txt");
        FileWriter writer = new FileWriter(board);
        for(Player player : players) {
            writer.write(player.getName() + " " + player.getScore() + "\n");
        }
        writer.close();
    }
}


