import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import java.io.File;

public class AddFilmScene extends Application implements Scenes {

    HBox textBox;
    Text welcomeText;

    GridPane gridPane;
    TextField nameField;
    TextField trailerField;
    TextField durationField;
    Button backButton;
    Button okButton;

    HBox errorBox;
    Text errorText;

    VBox addFilmPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),500,290);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        textBox = new HBox();
        welcomeText = new Text("Please give name, relative path of the trailer and duration of the film.");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        textBox.getChildren().add(welcomeText);
        textBox.setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Text("Name:"),0,0);
        gridPane.add(new Text("Trailer (Path):"),0,1);
        gridPane.add(new Text("Duration (m):"),0,2);

        nameField = new TextField();
        nameField.setPromptText("Name");
        trailerField = new TextField();
        trailerField.setPromptText("Trailer Path");
        durationField = new TextField();
        durationField.setPromptText("Duration");

        backButton = new Button("\u25C0 Back");
        okButton = new Button("OK");

        okButton.setOnAction(event -> check());
        backButton.setOnAction(event -> SceneManager.openFilmSelectScene(primaryStage));

        gridPane.add(nameField,1,0);
        gridPane.add(trailerField,1,1);
        gridPane.add(durationField,1,2);
        gridPane.add(backButton,0,3);
        gridPane.add(okButton,1,3);
        GridPane.setHalignment(backButton, HPos.LEFT);
        GridPane.setHalignment(okButton,HPos.RIGHT);

        errorText = new Text("");
        errorBox = new HBox(errorText);
        errorBox.setAlignment(Pos.CENTER);

        addFilmPane = new VBox(textBox,gridPane,errorBox);
        addFilmPane.setSpacing(10);
        addFilmPane.setPadding(new Insets(20,20,20,20));
        return addFilmPane;
    }

    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     */
    private void check(){
        String name = nameField.getText();
        String path = trailerField.getText();
        String duration = durationField.getText();
        String media = new File("assets\\effects\\error.mp3").toURI().toString(); //todo src
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(media));
        try {
            if (name.length() == 0) {
                errorText.setText("ERROR: Film name could not be empty!");
                mediaPlayer.play();
            }
            else if (path.length() == 0) {
                errorText.setText("ERROR: Trailer path could not be empty!");
                mediaPlayer.play();
            }
            else if (duration.length() == 0) {
                errorText.setText("ERROR: Duration could not be empty!");
                mediaPlayer.play();
            }
            else if (DataManager.films.containsKey(name)) {
                errorText.setText("ERROR; This film already exists!");
                mediaPlayer.play();
            }
            else if (Integer.parseInt(duration) <= 0) {
                errorText.setText("ERROR: Duration has to be a positive integer!");
                mediaPlayer.play();
            }
            else {
                try {
                    String trailerPath = new File("assets\\trailers\\"+path).toURI().toString(); //todo src
                    Media trailer = new Media(trailerPath);
                    Film film = new Film(name,path,Integer.parseInt(duration));
                    DataManager.films.put(name,film);
                    errorText.setText("SUCCESS: Film added successfully!");
                }
                catch (Exception e) {
                    errorText.setText("ERROR: There is no such a trailer!");
                    mediaPlayer.play();
                }
            }
        }
        catch (Exception e) {
            errorText.setText("ERROR: Duration has to be a positive integer!");
            mediaPlayer.play();
        }
        deletion();
    }

    /**
     * This function clears the entries of all fields.
     */
    private void deletion() {
        nameField.setText("");
        trailerField.setText("");
        durationField.setText("");
    }
}
