public class WJewel extends NormalJewels {

    public WJewel(String name, int point) {
        this.name = name;
        this.point = point;
    }

    public int checkPop(int x , int y , int dx , int dy , Jewels jewel) {
        /*
        This method checks only WJewel and calls popJewels if check is true.
         */
        try {
            if ((MatrixStuff.matrix.get(x + dx)[y + dy].name.equals("W") && (MatrixStuff.matrix.get(x + 2 * dx)[y + 2 * dy] instanceof NormalJewels))
                    || ((MatrixStuff.matrix.get(x + dx)[y + dy] instanceof NormalJewels) && (MatrixStuff.matrix.get(x + 2 * dx)[y + 2 * dy].name.equals("W")))
                    || (MatrixStuff.matrix.get(x + dx)[y + dy].name.equals(MatrixStuff.matrix.get(x + 2 * dx)[y + 2 * dy].name) && (MatrixStuff.matrix.get(x + 2 * dx)[y + 2 * dy] instanceof NormalJewels))) {
                return popJewels(x, y, dx, dy, jewel);
            }
        }
        catch (IndexOutOfBoundsException e) {
            return 0;
        }
        return 0;
    }

    @Override
    public int pop(int x, int y) {
        int score = checkPop(x, y, -1, 0, this);
        if (score == 0) {
            score = checkPop(x, y, 1, 0, this);
        }
        if (score == 0) {
            score = checkPop(x, y, 0, -1, this);
        }
        if (score == 0) {
            score = checkPop(x, y, 0, 1, this);
        }
        if (score == 0) {
            score = checkPop(x, y, -1, -1, this);
        }
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
