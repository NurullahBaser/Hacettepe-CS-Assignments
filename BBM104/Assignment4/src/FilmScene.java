import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.geometry.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Scene;
import java.io.File;
import javafx.util.Duration;

public class FilmScene extends Application implements Scenes {

    public static Hall selectedHall;

    HBox textBox;
    Text text;

    HBox trailerBox;
    VBox settings;
    Button playPauseButton;
    Button backwardButton;
    Button forwardButton;
    Button returnButton;
    Slider volumeBar;
    double oldVolume;
    Button muteButton;

    HBox timeBox;
    Slider timeSlider;
    Text timeText;

    HBox buttonBox;
    Button backButton;
    Button addHallButton;
    Button removeHallButton;
    ChoiceBox<String> selectHallButton;
    Button okButton;

    VBox filmPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),950,650);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        textBox = new HBox();
        text = new Text(SelectFilmScene.selectedFilm.getName() + " (" + SelectFilmScene.selectedFilm.getDuration() + " minutes)");
        textBox.setAlignment(Pos.CENTER);
        textBox.getChildren().add(text);

        trailerBox = new HBox(20);
        String media = new File("assets\\trailers\\" + SelectFilmScene.selectedFilm.getPath()).toURI().toString(); //todo src
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(media));
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setFitHeight(405);
        mediaView.setFitWidth(720);

        settings = new VBox(10);
        playPauseButton = new Button("\u25B6");
        playPauseButton.setMaxWidth(50);
        backwardButton = new Button("\u23EA");
        backwardButton.setMaxWidth(50);
        forwardButton = new Button("\u23E9");
        forwardButton.setMaxWidth(50);
        returnButton = new Button("\u23EE");
        returnButton.setMaxWidth(50);
        volumeBar = new Slider();
        volumeBar.setOrientation(Orientation.VERTICAL);
        volumeBar.setValue(50);
        muteButton = new Button("\uD83D\uDD0A");
        muteButton.setMaxWidth(50);
        settings.getChildren().addAll(playPauseButton,backwardButton,forwardButton,returnButton,volumeBar,muteButton);
        settings.setAlignment(Pos.CENTER);
        trailerBox.getChildren().addAll(mediaView,settings);
        trailerBox.setAlignment(Pos.CENTER);

        timeBox = new HBox(10);
        timeBox.setPadding(new Insets(0,0,0,35));
        timeText = new Text("");
        timeSlider = new Slider();
        timeSlider.setPrefSize(720,10);
        timeBox.getChildren().addAll(timeSlider,timeText);
        timeBox.setAlignment(Pos.CENTER_LEFT);

        timeSlider.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (timeSlider.isValueChanging()) {
                    mediaPlayer.seek(mediaPlayer.getTotalDuration().multiply(timeSlider.getValue()/100));
                }
            }
        });

        mediaPlayer.currentTimeProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                timeSlider.setValue(mediaPlayer.getCurrentTime().toSeconds()/mediaPlayer.getTotalDuration().toSeconds()*100);
                timeText.setText(findTime(mediaPlayer));
            }
        });

        buttonBox = new HBox(10);
        backButton = new Button("\u25C0 Back");
        buttonBox.getChildren().add(backButton);
        if (LoginScene.selectedUser.isAdmin()) {
            addHallButton = new Button("Add Hall");
            removeHallButton = new Button("Remove Hall");
            buttonBox.getChildren().addAll(addHallButton,removeHallButton);

            addHallButton.setOnAction(event -> addHallFunction(mediaPlayer,primaryStage));
            removeHallButton.setOnAction(event -> removeHallFunction(mediaPlayer,primaryStage));
        }
        selectHallButton = new ChoiceBox<>();
        try {
            selectHallButton.getItems().addAll(SelectFilmScene.selectedFilm.filmHalls.keySet());
            selectHallButton.getSelectionModel().selectFirst();
            selectHallButton.setPrefWidth(150);
        }
        catch (Exception ignored){}

        okButton = new Button("OK");
        buttonBox.getChildren().addAll(selectHallButton,okButton);
        buttonBox.setAlignment(Pos.CENTER);

        playPauseButton.setOnAction(event -> playPauseFunction(mediaPlayer));
        backwardButton.setOnAction(event -> backwardFunction(mediaPlayer));
        forwardButton.setOnAction(event -> forwardFunction(mediaPlayer));
        returnButton.setOnAction(event -> returnFunction(mediaPlayer));
        volumeBar.setOnMouseReleased(event -> volumeFunction(mediaPlayer));

        volumeBar.valueProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable observable) {
                if (volumeBar.isValueChanging()) {
                    mediaPlayer.setVolume(volumeBar.getValue()/100);
                    if (muteButton.getText().equals("\uD83D\uDD08")) {
                        muteButton.setText("\uD83D\uDD0A");
                    }
                    if (volumeBar.getValue() == 0) {
                        muteButton.setText("\uD83D\uDD08");
                    }
                }
            }
        });
        muteButton.setOnAction(event -> muteButtonFunction(mediaPlayer));

        backButton.setOnAction(event -> backFunction(mediaPlayer,primaryStage));
        okButton.setOnAction(event -> check(mediaPlayer,primaryStage));

        filmPane = new VBox(15);
        filmPane.getChildren().addAll(textBox,trailerBox,timeBox,buttonBox);
        filmPane.setAlignment(Pos.CENTER);
        return filmPane;
    }

    /**
     * This function stops or starts the media being played.
     * @param mediaPlayer is what the movie trailer is playing on.
     */
    private void playPauseFunction(MediaPlayer mediaPlayer) {
        if (playPauseButton.getText().equals("\u25B6")) {
            mediaPlayer.play();
            playPauseButton.setText("\u23F8");
        }
        else if (playPauseButton.getText().equals("\u23F8")) {
            mediaPlayer.pause();
            playPauseButton.setText("\u25B6");
        }
    }

    /**
     * This function rewinds the played media for 5 seconds.
     * @param mediaPlayer is what the movie trailer is playing on.
     */
    private void backwardFunction(MediaPlayer mediaPlayer) {
        Duration duration = Duration.seconds((mediaPlayer.getCurrentTime().toSeconds() - 5));
        timeSlider.setValue((mediaPlayer.getCurrentTime().toSeconds()-5)/mediaPlayer.getTotalDuration().toSeconds()*100);
        mediaPlayer.seek(duration);
    }

    /**
     * This function fast-forward the played media for 5 seconds.
     * @param mediaPlayer is what the movie trailer is playing on.
     */
    private void forwardFunction(MediaPlayer mediaPlayer){
        Duration duration = (Duration.seconds((mediaPlayer.getCurrentTime().toSeconds() + 5)));
        timeSlider.setValue((mediaPlayer.getCurrentTime().toSeconds()+5)/mediaPlayer.getTotalDuration().toSeconds()*100);
        mediaPlayer.seek(duration);
    }

    /**
     * This function takes the played media to the beginning.
     * @param mediaPlayer is what the movie trailer is playing on.
     */
    private void returnFunction(MediaPlayer mediaPlayer) {
        mediaPlayer.seek(Duration.ZERO);
        timeSlider.setValue(0);
    }

    /**
     * This function adjusts the volume of the media according to the value of the volume slider.
     * @param mediaPlayer is what the movie trailer is playing on.
     */
    private void volumeFunction(MediaPlayer mediaPlayer) {
        mediaPlayer.setVolume(volumeBar.getValue()/100);
        if (volumeBar.getValue() !=0) {
            muteButton.setText("\uD83D\uDD0A");
        }
        else if(volumeBar.getValue() == 0) {
            muteButton.setText("\uD83D\uDD08");
        }
    }

    /**
     * This function opens the scene of adding a hall.
     * @param mediaPlayer is what the movie trailer is playing on.
     * @param primaryStage is used if a new scene is to be opened.
     */
    private void addHallFunction(MediaPlayer mediaPlayer, Stage primaryStage) {
        mediaPlayer.pause();
        SceneManager.openAddHallScene(primaryStage);
    }

    /**
     * This function opens the scene of removing a hall.
     * @param mediaPlayer is what the movie trailer is playing on.
     * @param primaryStage is used if a new scene is to be opened.
     */
    private void removeHallFunction(MediaPlayer mediaPlayer, Stage primaryStage) {
        mediaPlayer.pause();
        SceneManager.openRemoveHallScene(primaryStage);
    }

    /**
     * This function opens the previous scene.
     * @param mediaPlayer is what the movie trailer is playing on.
     * @param primaryStage is used if a new scene is to be opened.
     */
    private void backFunction(MediaPlayer mediaPlayer, Stage primaryStage) {
        mediaPlayer.pause();
        SceneManager.openFilmSelectScene(primaryStage);
    }

    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     * @param mediaPlayer is what the movie trailer is playing on.
     * @param primaryStage is used if a new scene is to be opened.
     */
    private void check(MediaPlayer mediaPlayer, Stage primaryStage) {
        try {
            if (selectHallButton.getValue() == null) {
                throw new Exception();
            }
            mediaPlayer.pause();
            selectedHall = DataManager.halls.get(selectHallButton.getValue());
            SceneManager.openSelectSeatScene(primaryStage);
        }
        catch (Exception ignored) {}

    }

    /**
     * This function calculates the instantaneous duration and total duration of the media.
     * @param mediaPlayer is what the movie trailer is playing on.
     * @return string of the instantaneous duration and total duration of the media.
     */
    private String findTime(MediaPlayer mediaPlayer) {
        int currentTime = (int) mediaPlayer.getCurrentTime().toSeconds();
        int totalTime = (int) mediaPlayer.getTotalDuration().toSeconds();
        String output = "";
        output += (currentTime/60>=10) ? (currentTime/60):("0"+currentTime/60);
        output += ":";
        output += (currentTime%60>=10) ? (currentTime%60):("0"+currentTime%60);
        output += "/";
        output += (totalTime/60>=10) ? (totalTime/60):("0"+totalTime/60);
        output += ":";
        output += (totalTime%60>=10) ? (totalTime%60):("0"+totalTime%60);
        return output;
    }

    /**
     * This function turns off or turns on the media volume.
     * @param mediaPlayer is what the movie trailer is playing on.
     */
    private void muteButtonFunction(MediaPlayer mediaPlayer) {
        if (muteButton.getText().equals("\uD83D\uDD0A")) {
            oldVolume = volumeBar.getValue();
            volumeBar.setValue(0);
            mediaPlayer.setVolume(0);
            muteButton.setText("\uD83D\uDD08");
        }
        else if (muteButton.getText().equals("\uD83D\uDD08")) {
            volumeBar.setValue(oldVolume);
            mediaPlayer.setVolume( oldVolume/100);
            muteButton.setText("\uD83D\uDD0A");
        }
    }
}