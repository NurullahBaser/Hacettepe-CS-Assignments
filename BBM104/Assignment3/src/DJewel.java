public class DJewel extends NormalJewels {

    public DJewel(String name, int point) {
        this.name = name;
        this.point = point;
    }

    @Override
    public int pop(int x, int y) {
        int score = checkPop(x, y, -1, -1, this);
        if (score == 0) {
            score = checkPop(x, y, 1, 1, this);
        }
        if (score == 0) {
            score = checkPop(x, y, -1, 1, this);
        }
        if (score == 0) {
            score = checkPop(x, y, 1, -1, this);
        }
        return score;
    }
}
