import java.util.ArrayList;

public class Hall {
    private String name;
    private int row;
    private int column;
    private int price;
    public String shownFilm;
    public ArrayList<Seat> seats = new ArrayList<Seat>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getColumn() {
        return column;
    }

    public void setColumn(int column) {
        this.column = column;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public Hall(String name, int row, int column, int price, String shownFilm) {
        this.name = name;
        this.row = row;
        this.column = column;
        this.price = price;
        this.shownFilm = shownFilm;
    }
}
