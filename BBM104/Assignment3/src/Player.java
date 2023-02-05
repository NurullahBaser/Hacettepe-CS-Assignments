public class Player implements Comparable<Player> {

    private int score;
    private String name;

    public Player(String name , int score) {
        this.name = name;
        this.score = score;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Player o) {
        if(this.score>o.getScore()){ return -1;}
        if(this.score<o.getScore()){ return 1;}
        return 0;
    }
}