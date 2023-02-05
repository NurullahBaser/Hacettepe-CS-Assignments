public abstract class MathJewels extends Jewels {

    public int checkPop(int x , int y , int dx , int dy , Jewels jewel) {
        /*
        This method checks MathJewels and calls popJewels if check is true.
         */
        try {
            if(MatrixStuff.matrix.get(x+dx)[y+dy] instanceof MathJewels && MatrixStuff.matrix.get(x+2*dx)[y+2*dy] instanceof MathJewels) {
                return popJewels(x,y,dx,dy,jewel);
            }
        }
        catch (IndexOutOfBoundsException e) {
            return 0;
        }
        return 0;
    }
}
