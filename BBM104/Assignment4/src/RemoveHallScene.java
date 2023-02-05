import javafx.application.Application;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;

public class RemoveHallScene extends Application implements Scenes {

    HBox textBox;
    Text welcomeText;

    HBox choiceBox;
    ChoiceBox<String> selectHall;

    HBox buttonsBox;
    Button backButton;
    Button okButton;

    VBox removeHallPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),600,250);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setScene(scene);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage){
        textBox = new HBox();
        welcomeText = new Text("Select the hall that you desire to remove from " + SelectFilmScene.selectedFilm.getName() +" and then click OK.");
        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().add(welcomeText);

        choiceBox = new HBox();
        selectHall = new ChoiceBox<>();
        selectHall.setPrefWidth(200);
        try {
            selectHall.getItems().addAll(SelectFilmScene.selectedFilm.filmHalls.keySet());
            selectHall.getSelectionModel().selectFirst();
        } catch (Exception ignored) {}
        choiceBox.setAlignment(Pos.CENTER);
        choiceBox.getChildren().add(selectHall);

        buttonsBox = new HBox(15);
        backButton = new Button("\u25C0 Back");
        okButton = new Button("OK");
        buttonsBox.setAlignment(Pos.CENTER);
        buttonsBox.getChildren().addAll(backButton,okButton);

        backButton.setOnAction(event -> SceneManager.openFilmScene(primaryStage));
        okButton.setOnAction(event -> check());

        removeHallPane = new VBox(15);
        removeHallPane.getChildren().addAll(textBox,choiceBox,buttonsBox);
        removeHallPane.setAlignment(Pos.CENTER);
        return removeHallPane;
    }

    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     */
    private void check() {
        try {
            SelectFilmScene.selectedFilm.filmHalls.remove(selectHall.getValue());
            DataManager.halls.remove(selectHall.getValue());
            try {
                selectHall.getItems().remove(selectHall.getValue());
                selectHall.getSelectionModel().selectFirst();
            }
            catch (Exception ignored) {}
        }
        catch (Exception ignored) {}
    }
}
