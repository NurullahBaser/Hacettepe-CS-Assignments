import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import java.io.File;

public class SelectSeatScene extends Application implements Scenes {

    HBox textBox;
    Text welcomeText;

    GridPane gridPane;

    HBox selectUserBox;
    ChoiceBox<String> selectUser;
    HBox seatStatusTextBox;
    Text seatStatusText;
    HBox seatInformationTextBox;
    Text seatInformationText;
    HBox backButtonBox;
    Button backButton;

    VBox selectSeatPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),FilmScene.selectedHall.getColumn()*75+270,FilmScene.selectedHall.getRow()*75+220);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        selectSeatPane = new VBox(10);
        selectSeatPane.setAlignment(Pos.CENTER);

        textBox = new HBox();
        welcomeText = new Text(SelectFilmScene.selectedFilm.getName() + " (" + SelectFilmScene.selectedFilm.getDuration() + " minutes) Hall: " + FilmScene.selectedHall.getName());
        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().add(welcomeText);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        String emptySeat = new File("assets\\icons\\empty_seat.png").toURI().toString(); //todo src
        String reservedSeat = new File("assets\\icons\\reserved_seat.png").toURI().toString(); //todo src
        selectSeatPane.getChildren().addAll(textBox,gridPane);

        for (Seat seat : FilmScene.selectedHall.seats) {
            ToggleButton seatButton = new ToggleButton();

            ImageView emptyView = new ImageView(emptySeat);
            emptyView.setFitWidth(50);
            emptyView.setFitHeight(50);

            ImageView reservedView = new ImageView(reservedSeat);
            reservedView.setFitWidth(50);
            reservedView.setFitHeight(50);

            seatButton.setGraphic(emptyView);

            if (seat.getOwner() != null && !LoginScene.selectedUser.isAdmin() && seat.getOwner() != LoginScene.selectedUser) {
                seatButton.setDisable(true);
                seatButton.setGraphic(reservedView);
            }
            else if (seat.getOwner() != null && seat.getOwner() == LoginScene.selectedUser && !LoginScene.selectedUser.isAdmin()) {
                seatButton.setGraphic(reservedView);
                seatButton.setSelected(true);
            }
            if (LoginScene.selectedUser.isAdmin()) {
                if (seat.getOwner() != null) {
                    seatButton.setGraphic(reservedView);
                    seatButton.setSelected(true);
                }
                seatButton.setOnMouseEntered(event -> mouseEntered(seat));
                seatButton.setOnMouseExited(event -> mouseExited());
            }
            gridPane.add(seatButton,seat.getColumn_index(),seat.getRow_index());
            seatButton.setOnMouseClicked(event -> mouseClick(seatButton,seat,emptyView,reservedView));
        }

        if (LoginScene.selectedUser.isAdmin()) {
            selectUserBox = new HBox();
            selectUser = new ChoiceBox<>();
            selectUser.getItems().addAll(DataManager.users.keySet());
            selectUser.getSelectionModel().select(LoginScene.selectedUser.getName());
            selectUserBox.getChildren().add(selectUser);
            selectUserBox.setAlignment(Pos.CENTER);
            selectSeatPane.getChildren().add(selectUserBox);

        }
        seatStatusTextBox = new HBox();
        seatStatusText = new Text("");
        seatStatusTextBox.setAlignment(Pos.CENTER);
        seatStatusTextBox.getChildren().add(seatStatusText);

        seatInformationTextBox = new HBox();
        seatInformationText = new Text("");
        seatInformationTextBox.setAlignment(Pos.CENTER);
        seatInformationTextBox.getChildren().add(seatInformationText);

        backButtonBox = new HBox();
        backButtonBox.setAlignment(Pos.CENTER_LEFT);
        backButton = new Button("\u25C0 Back");
        backButtonBox.getChildren().add(backButton);
        backButtonBox.setPadding(new Insets(0,0,0,10*FilmScene.selectedHall.getRow()));

        selectSeatPane.getChildren().addAll(seatStatusTextBox,seatInformationTextBox,backButtonBox);
        backButton.setOnAction(event -> SceneManager.openFilmScene(primaryStage));
        return selectSeatPane;
    }

    /**
     * This function creates a text when the mouse enters it.
     * @param seat is selected seat.
     */
    private void mouseEntered(Seat seat) {
        if (seat.getOwner() == null) {
            seatStatusText.setText("Not bought yet!");
        }
        else {
            seatStatusText.setText("Bought by " + seat.getOwner().getName() + " for " + seat.getPrice() + " TL!");
        }
    }

    /**
     * This function clear the text when the mouse exist it.
     */
    private void mouseExited() {
        seatStatusText.setText("");
    }

    /**
     * This function allows you to purchase or refund the selected seat when the mouse is clicked.
     * It also prints the seat location, price and information of the user who received it.
     * @param button selected button
     * @param seat selected seat
     * @param empty an image for empty seat
     * @param reserved an image for reserved seat
     */
    private void mouseClick(ToggleButton button , Seat seat, ImageView empty, ImageView reserved) {
        String infoText;
        if (!button.isSelected()) {
            seat.setOwner(null);
            seat.setPrice(0);
            button.setGraphic(empty);
            seatInformationText.setText("Seat at " + (seat.getRow_index()+1)+"-"+(seat.getColumn_index()+1) + " refunded successfully!");
        }
        else {
            int price = FilmScene.selectedHall.getPrice();

            if (LoginScene.selectedUser.isAdmin()) {
                if (DataManager.users.get(selectUser.getValue()).isClubMember()) {
                    price = (FilmScene.selectedHall.getPrice() * (100 - Integer.parseInt(DataManager.properties.getProperty("discount-percentage")))) / 100;
                }
                seat.setOwner(DataManager.users.get(selectUser.getValue()));
                infoText = "Seat at " + (seat.getRow_index()+1)+"-"+(seat.getColumn_index()+1) + " is bought for " + seat.getOwner().getName()+ " for " + price +" TL successfully!";
            }
            else {
                if (LoginScene.selectedUser.isClubMember()) {
                    price = (FilmScene.selectedHall.getPrice() * (100 - Integer.parseInt(DataManager.properties.getProperty("discount-percentage")))) / 100;
                }
                seat.setOwner(LoginScene.selectedUser);
                infoText = "Seat at " + (seat.getRow_index()+1)+"-"+(seat.getColumn_index()+1) + " is bought for " + price +" TL successfully!";
            }
            seat.setPrice(price);
            button.setGraphic(reserved);
            seatInformationText.setText(infoText);
        }
    }
}
