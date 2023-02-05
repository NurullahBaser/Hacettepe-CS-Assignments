public abstract class Jewels {
    public String name;
    public int point;

    public abstract int pop(int x, int y); //
    /*
     It will call check pop in given direction.
     If the check return 0 (there is no match) then call the other check with other direction.
     */

    public abstract int checkPop(int x , int y , int dx , int dy , Jewels jewel);
    /*
    It checks the matrix and if the given coordinate in the matrix will pop
    then it will call the popJewels.
     */


    protected int popJewels(int x , int y , int dx , int dy , Jewels jewel) {
        /*
        It calculates the score and pop the jewels.
        Popped jewels will be null
        Finally method calls the gravityMatrix.
         */
        int score = 0;

        score += jewel.point;
        score += MatrixStuff.matrix.get(x+dx)[y+dy].point;
        score += MatrixStuff.matrix.get(x+2*dx)[y+2*dy].point;
        GameManager.totalScore += score;

        MatrixStuff.matrix.get(x)[y] = null;
        MatrixStuff.matrix.get(x+dx)[y+dy] = null;
        MatrixStuff.matrix.get(x+2*dx)[y+2*dy] = null;
        MatrixStuff.gravityMatrix();

        return score;
    }
}

