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

public class SignUpScene extends Application implements Scenes, HasPasswordField{

    Pane stringPane;
    Text welcomeText;

    GridPane gridPane;
    TextField usernameField;
    PasswordField passwordField;
    PasswordField passwordField2;
    TextField passwordOpenField;
    TextField passwordOpenField2;
    boolean passwordIsActive;
    RadioButton changePasswordButton;
    Button sign_up_button;
    Button login_button;

    HBox errorBox;
    Text errorText;

    VBox signUpPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),420,350);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        stringPane = new Pane();
        welcomeText = new Text("Welcome to the HUCS cinema reservation system!" +
                "\nFill the form bellow to create a new account" +
                "\nYou can go to log in page by clicking LOG IN button.");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        stringPane.getChildren().add(welcomeText);


        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(new Text("Username:"),0,0);
        gridPane.add(new Text("Password:"),0,1);
        gridPane.add(new Text("Password:"),0,2);
        usernameField = new TextField();
        usernameField.setPromptText("Username");
        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField2 = new PasswordField();
        passwordField2.setPromptText("Password Again");
        passwordOpenField = new TextField();
        passwordOpenField.setPromptText("Password");
        passwordOpenField2 = new TextField();
        passwordOpenField2.setPromptText("Password Again");
        sign_up_button = new Button("SIGN UP");
        login_button = new Button("LOG IN");
        changePasswordButton = new RadioButton("Show");
        gridPane.add(usernameField,1,0);
        gridPane.add(passwordField,1,1);
        gridPane.add(sign_up_button,1,3);
        gridPane.add(login_button,0,3);
        gridPane.add(passwordField2,1,2);
        gridPane.add(changePasswordButton,2,1);
        GridPane.setHalignment(sign_up_button, HPos.RIGHT);

        login_button.setOnAction(event -> SceneManager.openLoginScene(primaryStage));
        sign_up_button.setOnAction(event -> check());
        changePasswordButton.setOnAction(event -> changePasswordField());

        errorText = new Text("");
        errorBox = new HBox(errorText);
        errorBox.setAlignment(Pos.CENTER);


        signUpPane = new VBox(stringPane,gridPane,errorBox);
        signUpPane.setSpacing(15);
        signUpPane.setPadding(new Insets(30,30,50,30));
        return signUpPane;
    }

    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     */
    private void check(){
        String media = new File("assets\\effects\\error.mp3").toURI().toString(); //todo src
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(media));

        String username = usernameField.getText();
        String password1;
        String password2;

        if (!passwordIsActive) {
            password1 = passwordField.getText();
            password2 = passwordField2.getText();
        }
        else {
            password1 = passwordOpenField.getText();
            password2 = passwordOpenField2.getText();
        }

        if (username.length() == 0) {
            errorText.setText("ERROR: Username cannot be empty!");
            deletion();
            mediaPlayer.play();
        }
        else if (password1.length() == 0) {
            errorText.setText("ERROR: Password cannot be empty!");
            deletion();
            mediaPlayer.play();
        }
        else if (password2.length() == 0) {
            errorText.setText("ERROR: Password cannot be empty!");
            deletion();
            mediaPlayer.play();
        }
        else if (!password1.equals(password2)){
            errorText.setText("ERROR: Passwords do not match!");
            deletion();
            mediaPlayer.play();
        }
        else if (DataManager.users.containsKey(username)) {
            errorText.setText("ERROR: This username already exist!");
            deletion();
            mediaPlayer.play();
        }
        else {
            errorText.setText("SUCCESS: You have successfully registered!");
            deletion();
            DataManager.users.put(username,new User(username, DataManager.hashPassword(password1),false, false));
        }
    }

    @Override
    public void changePasswordField() {
        if (!passwordIsActive) {
            passwordOpenField.setText(passwordField.getText());
            passwordOpenField2.setText(passwordField2.getText());
            gridPane.getChildren().remove(passwordField);
            gridPane.getChildren().remove(passwordField2);
            gridPane.add(passwordOpenField,1,1);
            gridPane.add(passwordOpenField2,1,2);
            passwordIsActive = true;
        }
        else {
            passwordField.setText(passwordOpenField.getText());
            passwordField2.setText(passwordOpenField2.getText());
            gridPane.getChildren().remove(passwordOpenField);
            gridPane.getChildren().remove(passwordOpenField2);
            gridPane.add(passwordField,1,1);
            gridPane.add(passwordField2,1,2);
            passwordIsActive = false;
        }
    }

    /**
     * This function clears the entries of all fields.
     */
    private void deletion() {
        usernameField.setText("");
        passwordField.setText("");
        passwordField2.setText("");
        passwordOpenField.setText("");
        passwordOpenField2.setText("");
    }
}
