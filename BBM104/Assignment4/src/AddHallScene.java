import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import java.io.File;

public class AddHallScene extends Application implements Scenes {

    HBox textBox;
    Text welcomeText;

    GridPane gridPane;
    ChoiceBox<Integer> rowBox;
    ChoiceBox<Integer> columnBox;
    TextField nameField;
    TextField priceField;
    Button backButton;
    Button okButton;

    HBox errorBox;
    Text errorText;

    VBox addHallPane;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),425,340);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        textBox = new HBox();
        welcomeText = new Text(SelectFilmScene.selectedFilm.getName() + " (" + SelectFilmScene.selectedFilm.getDuration()+ " minutes)");
        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().add(welcomeText);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Text("Row:"),0,0);
        gridPane.add(new Text("Column:"),0,1);
        gridPane.add(new Text("Name:"),0,2);
        gridPane.add(new Text("Price:"),0,3);

        rowBox = new ChoiceBox<>();
        rowBox.getItems().addAll(3,4,5,6,7,8,9,10);
        rowBox.setPrefWidth(50);
        rowBox.getSelectionModel().selectFirst();

        columnBox = new ChoiceBox<>();
        columnBox.getItems().addAll(3,4,5,6,7,8,9,10);
        columnBox.setPrefWidth(50);
        columnBox.getSelectionModel().selectFirst();

        nameField = new TextField();
        nameField.setPromptText("Name");
        nameField.setPrefWidth(125);

        priceField = new TextField();
        priceField.setPromptText("Price");
        priceField.setPrefWidth(125);

        backButton = new Button("\u25C0 Back");
        okButton = new Button("OK");

        gridPane.add(rowBox,1,0);
        gridPane.add(columnBox,1,1);
        gridPane.add(nameField,1,2);
        gridPane.add(priceField,1,3);
        gridPane.add(backButton,0,4);
        gridPane.add(okButton,1,4);
        GridPane.setHalignment(okButton, HPos.RIGHT);
        GridPane.setHalignment(rowBox, HPos.CENTER);
        GridPane.setHalignment(columnBox, HPos.CENTER);

        backButton.setOnAction(event -> SceneManager.openFilmScene(primaryStage));
        okButton.setOnAction(event -> check());

        errorBox = new HBox();
        errorText = new Text("");
        errorBox.setAlignment(Pos.CENTER);
        errorBox.getChildren().add(errorText);

        addHallPane = new VBox(15);
        addHallPane.getChildren().addAll(textBox,gridPane,errorBox);
        addHallPane.setAlignment(Pos.CENTER);
        return addHallPane;
    }


    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     */
    private void check() {
        String media = new File("assets\\effects\\error.mp3").toURI().toString(); //todo src
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(media));
        String hallName = nameField.getText();
        String price = priceField.getText();
        try {
            if (hallName.length() == 0) {
                errorText.setText("ERROR: Hall name could not be empty!");
                mediaPlayer.play();
            }
            else if (price.length() == 0) {
                errorText.setText("ERROR: Price could not be empty!");
                mediaPlayer.play();
            }
            else if (DataManager.halls.containsKey(hallName)) {
                errorText.setText("ERROR: Hall name does exist!");
                mediaPlayer.play();
            }
            else if (Integer.parseInt(price) < 0) {
                errorText.setText("ERROR: Price has to be a positive integer!");
                mediaPlayer.play();
            }
            else {
                errorText.setText("SUCCESS: Hall added successfully");
                Hall newHall = new Hall(hallName, rowBox.getValue(), columnBox.getValue(),Integer.parseInt(price),SelectFilmScene.selectedFilm.getName());
                SelectFilmScene.selectedFilm.filmHalls.put(hallName,newHall);
                DataManager.halls.put(hallName, newHall);
                for (int row = 0; row< rowBox.getValue() ; row++) {
                    for (int column = 0; column < columnBox.getValue() ; column++) {
                        Seat newSeat = new Seat(row,column,null, 0);
                        newHall.seats.add(newSeat);
                    }
                }
            }
        }
        catch (Exception e) {
            errorText.setText("ERROR: Price has to be a positive integer!");
            mediaPlayer.play();
        }
        deletion();
    }

    /**
     * This function clears the entries of all fields.
     */
    private void deletion(){
        rowBox.getSelectionModel().selectFirst();
        columnBox.getSelectionModel().selectFirst();
        nameField.setText("");
        priceField.setText("");
    }
}
