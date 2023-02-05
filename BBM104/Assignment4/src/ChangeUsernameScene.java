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

public class ChangeUsernameScene extends Application implements Scenes,HasPasswordField{

    HBox textBox;
    Text welcomeText;

    GridPane gridPane;
    Text newUsernameText;
    Text passwordText;
    TextField newUsernameField;
    PasswordField passwordField;
    TextField passwordOpenField;
    boolean passwordIsActive;
    Button backButton;
    Button okButton;
    RadioButton changePasswordButton;

    HBox errorBox;
    Text errorText;

    VBox changeUsernamePane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),470,330);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage){
        textBox = new HBox();
        welcomeText = new Text("You can change the your username.\nWrite new username and your password.");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        textBox.getChildren().add(welcomeText);
        textBox.setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setVgap(7);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        newUsernameText = new Text("New Username:");
        passwordText = new Text("Password:");

        newUsernameField = new TextField();
        newUsernameField.setPromptText("New Username");
        newUsernameField.setPrefWidth(150);

        passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setPrefWidth(150);

        passwordOpenField = new TextField();
        passwordOpenField.setPromptText("Password");
        passwordOpenField.setPrefWidth(150);

        backButton = new Button("\u25C0 Back");
        okButton = new Button("OK");
        changePasswordButton = new RadioButton("Show");
        gridPane.add(newUsernameText,0,0);
        gridPane.add(passwordText,0,1);
        gridPane.add(newUsernameField,1,0);
        gridPane.add(passwordField,1,1);
        gridPane.add(backButton,0,2);
        gridPane.add(okButton,1,2);
        gridPane.add(changePasswordButton,2,1);
        GridPane.setHalignment(okButton,HPos.RIGHT);
        gridPane.setAlignment(Pos.CENTER);

        backButton.setOnAction(event -> SceneManager.openUserSettingScene(primaryStage));
        okButton.setOnAction(event -> check());
        changePasswordButton.setOnAction(event -> changePasswordField());

        errorBox = new HBox();
        errorText = new Text("");
        errorBox.getChildren().add(errorText);
        errorBox.setAlignment(Pos.CENTER);

        changeUsernamePane = new VBox(textBox,gridPane,errorBox);
        changeUsernamePane.setSpacing(20);
        changeUsernamePane.setPadding(new Insets(15,25,15,25));
        changeUsernamePane.setAlignment(Pos.CENTER);
        return changeUsernamePane;
    }

    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     */
    private void check() {
        String media = new File("assets\\effects\\error.mp3").toURI().toString(); //todo src
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(media));

        String username = newUsernameField.getText();
        String password;
        if (!passwordIsActive) {
            password = passwordField.getText();
        }
        else {
            password = passwordOpenField.getText();
        }

        if (username.length() == 0) {
            errorText.setText("ERROR: Username cannot be empty!");
            mediaPlayer.play();
        }
        else if (password.length() == 0) {
            errorText.setText("ERROR: Password cannot be empty!");
            mediaPlayer.play();
        }
        else if (DataManager.users.containsKey(username)) {
            errorText.setText("ERROR: This username already exists!");
            mediaPlayer.play();
        }
        else if (!LoginScene.selectedUser.getPassword().equals(DataManager.hashPassword(password))){
            errorText.setText("ERROR: Password is wrong!");
            mediaPlayer.play();
        }
        else {
            errorText.setText("SUCCESS: You have successfully change your username!");
            String oldUsername = LoginScene.selectedUser.getName();
            LoginScene.selectedUser.setName(username);
            DataManager.users.put(username,LoginScene.selectedUser);
            DataManager.users.remove(oldUsername);

        }
        deletion();
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
        newUsernameField.setText("");
        passwordField.setText("");
        passwordOpenField.setText("");
    }
}

