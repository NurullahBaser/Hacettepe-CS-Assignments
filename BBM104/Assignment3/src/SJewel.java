public class SJewel extends NormalJewels {

    public SJewel(String name, int point) {
        this.name = name;
        this.point = point;
    }

    @Override
    public int pop(int x, int y) {
        int score = checkPop(x, y, 0, -1, this);
        if (score == 0) {
            score = checkPop(x, y, 0, 1, this);
        }
        return score;
    }
}
