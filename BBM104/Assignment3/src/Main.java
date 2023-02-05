import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        MatrixStuff.creatMatrix(args[0]); // to create matrix
        GameManager.leaderBoardReader(); // to read leaderboard.txt
        GameManager.runGame(args[1]); // to run game
    }
}