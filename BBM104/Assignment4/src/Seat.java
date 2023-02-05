public class Seat {
    private int row_index;
    private int column_index;
    private User owner;
    private int price;

    public Seat(int row_index, int column_index, User owner, int price) {
        this.row_index = row_index;
        this.column_index = column_index;
        this.owner = owner;
        this.price = price;
    }

    public int getRow_index() {
        return row_index;
    }

    public void setRow_index(int row_index) {
        this.row_index = row_index;
    }

    public int getColumn_index() {
        return column_index;
    }

    public void setColumn_index(int column_index) {
        this.column_index = column_index;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
