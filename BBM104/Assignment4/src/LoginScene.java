import javafx.application.Application;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
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
import java.util.Timer;
import java.util.TimerTask;


public class LoginScene extends Application implements Scenes,HasPasswordField{

    public static User selectedUser;

    Pane stringPane;
    Text welcomeText;

    GridPane gridPane;
    TextField usernameField;
    PasswordField passwordField;
    TextField passwordOpenField;
    boolean passwordIsActive;
    Button sign_up_button;
    Button login_button;
    RadioButton changePasswordButton;

    HBox errorBox;
    Text errorText;

    private static int count = 0;
    private static boolean isBlocked;
    private static int blockTime = Integer.parseInt(DataManager.properties.getProperty("block-time"));

    VBox loginPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),500,320);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        stringPane = new Pane();
        welcomeText = new Text("Welcome to the HUCS Cinema Reservation System!" +
                "\nPlease enter your credentials below and click LOGIN." +
                "\nYou can create a new account by clicking SIGN UP button.");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        stringPane.getChildren().add(welcomeText);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Text("Username:"),0,0);
        gridPane.add(new Text("Password:"),0,1);
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordOpenField = new TextField();
        passwordOpenField.setPromptText("Password");
        sign_up_button = new Button("SIGN UP");
        login_button = new Button("LOG IN");
        changePasswordButton = new RadioButton("Show");
        gridPane.add(usernameField,1,0);
        gridPane.add(passwordField,1,1);
        gridPane.add(sign_up_button,0,2);
        gridPane.add(login_button,1,2);
        gridPane.add(changePasswordButton,2,1);
        GridPane.setHalignment(login_button, HPos.RIGHT);

        sign_up_button.setOnAction(event -> SceneManager.openSignUpScene(primaryStage));
        login_button.setOnAction(event -> check(primaryStage));
        changePasswordButton.setOnAction(event -> changePasswordField());

        errorText = new Text("");
        errorBox = new HBox(errorText);
        errorBox.setAlignment(Pos.CENTER);

        loginPane = new VBox(stringPane,gridPane,errorBox);
        loginPane.setAlignment(Pos.CENTER);
        loginPane.setSpacing(10);
        loginPane.setPadding(new Insets(30,30,30,30));
        return loginPane;
    }

    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     * @param primaryStage is used if a new scene is to be opened.
     */
    private void check(Stage primaryStage){
        String media = new File("assets\\effects\\error.mp3").toURI().toString(); //todo src
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(media));
        String username = usernameField.getText();
        String password;
        if (!passwordIsActive) {
            password = passwordField.getText();
        }
        else {
            password = passwordOpenField.getText();
        }

        if (count == Integer.parseInt(DataManager.properties.getProperty("maximum-error-without-getting-blocked"))-1) {
            if (!DataManager.users.containsKey(username) || !DataManager.users.get(username).getPassword().equals(DataManager.hashPassword(password))) {
                errorText.setText("ERROR: Please wait for " + Integer.parseInt(DataManager.properties.getProperty("block-time")) + " seconds to make new operation!");
                mediaPlayer.play();
                count = 0;
                isBlocked = true;
                setTimer();
            }
            else {
                count = 0;
                selectedUser = DataManager.users.get(username);
                SceneManager.openFilmSelectScene(primaryStage);
            }
        }
        else if (isBlocked) {
            errorText.setText("ERROR: Please wait until end of the " + Integer.parseInt(DataManager.properties.getProperty("block-time")) + " seconds to make new operation!");
            mediaPlayer.play();
        }
        else if (username.length() == 0) {
            errorText.setText("ERROR: Username must be entered!");
            mediaPlayer.play();
            count ++;
        }
        else if (password.length() == 0) {
            errorText.setText("ERROR: Password must be entered!");
            mediaPlayer.play();
            count ++;
        }
        else if (!DataManager.users.containsKey(username)) {
            errorText.setText("ERROR: There is no such a credential!");
            mediaPlayer.play();
            count ++;
        }
        else if (!DataManager.users.get(username).getPassword().equals(DataManager.hashPassword(password))) {
            errorText.setText("ERROR: Password is wrong!");
            mediaPlayer.play();
            count ++;
        }
        else {
            count = 0;
            selectedUser = DataManager.users.get(username);
            SceneManager.openFilmSelectScene(primaryStage);
        }
        deletion();
    }

    /**
     * When this function is run, it calculates the time as long as the time is given.
     */
    private void setTimer() {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if(blockTime > 0) {
                    blockTime--;
                }
                else {
                    timer.cancel();
                    isBlocked = false;
                    blockTime = Integer.parseInt(DataManager.properties.getProperty("block-time"));
                }
            }
        }, 0,1000);
    }

    @Override
    public void changePasswordField() {
        if (!passwordIsActive) {
            passwordOpenField.setText(passwordField.getText());
            gridPane.getChildren().remove(passwordField);
            gridPane.add(passwordOpenField,1,1);
            passwordIsActive = true;
        }
        else {
            passwordField.setText(passwordOpenField.getText());
            gridPane.getChildren().remove(passwordOpenField);
            gridPane.add(passwordField,1,1);
            passwordIsActive = false;
        }
    }

    /**
     * This function clears the entries of all fields.
     */
    private void deletion() {
        usernameField.setText("");
        passwordField.setText("");
        passwordOpenField.setText("");
    }
}
