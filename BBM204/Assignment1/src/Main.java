import java.io.IOException;

class Main {
    public static void main(String args[]) throws IOException {
        GameManager gameManager = new GameManager();
        gameManager.startGame();
        gameManager.runGame(args);
        gameManager.setGraphs();
    }
}
