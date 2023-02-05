public abstract class NormalJewels extends Jewels {

    public int checkPop(int x, int y, int dx, int dy , Jewels jewel) {
        /*
        This method checks NormalJewels and calls popJewels if check is true.
         */
        try {
            if (MatrixStuff.matrix.get(x+dx)[y+dy].name.equals(jewel.name) && MatrixStuff.matrix.get(x+2*dx)[y+2*dy].name.equals(jewel.name)) {
                return popJewels(x,y,dx,dy,jewel);
            }
        }
        catch (IndexOutOfBoundsException e) {
            return 0;
        }
        return 0;
    }
}
