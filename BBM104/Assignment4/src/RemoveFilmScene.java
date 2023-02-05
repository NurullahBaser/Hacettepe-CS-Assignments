import javafx.application.Application;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class RemoveFilmScene extends Application implements Scenes {

    HBox textBox;
    Text welcomeText;

    HBox selectBox;
    ChoiceBox<String> choiceBox;

    HBox buttons;
    Button backButton;
    Button okButton;

    VBox removeFilmPane;


    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),500,250);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setScene(scene);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage){
        textBox = new HBox();
        welcomeText = new Text("Select the film that you desire to remove and then click OK.");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        textBox.getChildren().add(welcomeText);
        textBox.setAlignment(Pos.CENTER);

        selectBox = new HBox();
        choiceBox = new ChoiceBox<>();
        choiceBox.setPrefWidth(300);
        try {
            choiceBox.getItems().addAll(DataManager.films.keySet());
            choiceBox.getSelectionModel().selectFirst();
        } catch (Exception ignored){}
        selectBox.getChildren().add(choiceBox);
        selectBox.setAlignment(Pos.CENTER);

        buttons = new HBox(15);
        backButton = new Button("\u25C0 Back");
        okButton = new Button("OK");
        buttons.getChildren().addAll(backButton,okButton);
        buttons.setAlignment(Pos.CENTER);

        okButton.setOnAction(event -> check());
        backButton.setOnAction(event -> SceneManager.openFilmSelectScene(primaryStage));

        removeFilmPane = new VBox(textBox,selectBox,buttons);
        removeFilmPane.setSpacing(15);
        removeFilmPane.setAlignment(Pos.CENTER);
        removeFilmPane.setPadding(new Insets(20,50,20,50));
        return removeFilmPane;
    }

    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     */
    private void check(){
        String chosenFilm = choiceBox.getValue();
        DataManager.films.remove(chosenFilm);
        try {
            choiceBox.getItems().remove(chosenFilm);
            choiceBox.getSelectionModel().selectFirst();
        } catch (Exception ignored){}
    }


}
