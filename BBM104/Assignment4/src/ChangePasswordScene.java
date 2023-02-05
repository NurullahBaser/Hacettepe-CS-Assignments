import javafx.application.Application;
import javafx.scene.Scene;
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
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.File;

public class ChangePasswordScene extends Application implements Scenes,HasPasswordField {

    HBox textBox;
    Text welcomeText;

    GridPane gridPane;
    PasswordField oldPasswordField;
    TextField oldPasswordOpenField;
    PasswordField newPasswordField;
    TextField newPasswordOpenField;
    PasswordField newPasswordField2;
    TextField newPasswordOpenField2;
    RadioButton changePasswordButton;
    boolean passwordIsActive;
    Button backButton;
    Button okButton;

    HBox errorBox;
    Text errorText;

    VBox changePasswordPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),500,350);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        textBox = new HBox();
        welcomeText = new Text("You can change the your password.\nWrite your old password and new password.");
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        textBox.getChildren().add(welcomeText);
        textBox.setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setVgap(10);
        gridPane.setHgap(10);
        gridPane.setAlignment(Pos.CENTER);

        oldPasswordField = new PasswordField();
        oldPasswordField.setPromptText("Old Password");
        oldPasswordField.setPrefWidth(150);
        oldPasswordOpenField = new TextField();
        oldPasswordOpenField.setPromptText("Old Password");
        oldPasswordOpenField.setPrefWidth(150);

        newPasswordField = new PasswordField();
        newPasswordField.setPromptText("New Password");
        newPasswordField.setPrefWidth(150);
        newPasswordOpenField = new TextField();
        newPasswordOpenField.setPromptText("New Password");
        newPasswordOpenField.setPrefWidth(150);

        newPasswordField2 = new PasswordField();
        newPasswordField2.setPromptText("New Password Again");
        newPasswordField2.setPrefWidth(150);
        newPasswordOpenField2 = new TextField();
        newPasswordOpenField2.setPromptText("New Password Again");
        newPasswordOpenField2.setPrefWidth(150);

        backButton = new Button("\u25C0 Back");
        okButton = new Button("OK");
        changePasswordButton = new RadioButton("Show");

        gridPane.add(new Text("Old Password:"),0,0);
        gridPane.add(new Text("New Password:"),0,1);
        gridPane.add(new Text("New Password Again:"),0,2);
        gridPane.add(oldPasswordField,1,0);
        gridPane.add(newPasswordField,1,1);
        gridPane.add(newPasswordField2,1,2);
        gridPane.add(backButton,0,3);
        gridPane.add(okButton,1,3);
        gridPane.add(changePasswordButton,2,0);
        GridPane.setHalignment(okButton,HPos.RIGHT);
        gridPane.setAlignment(Pos.CENTER);

        backButton.setOnAction(event -> SceneManager.openUserSettingScene(primaryStage));
        okButton.setOnAction(event -> check());
        changePasswordButton.setOnAction(event -> changePasswordField());

        errorBox = new HBox();
        errorText = new Text("");
        errorBox.getChildren().add(errorText);
        errorBox.setAlignment(Pos.CENTER);

        changePasswordPane = new VBox(textBox,gridPane,errorBox);
        changePasswordPane.setSpacing(20);
        changePasswordPane.setPadding(new Insets(15,25,15,25));
        changePasswordPane.setAlignment(Pos.CENTER);
        return changePasswordPane;
    }


    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     */
    private void check(){
        String media = new File("assets\\effects\\error.mp3").toURI().toString(); //todo src
        MediaPlayer mediaPlayer = new MediaPlayer(new Media(media));

        String oldPassword;
        String newPassword;
        String newPassword2;

        if (!passwordIsActive) {
            oldPassword = oldPasswordField.getText();
            newPassword = newPasswordField.getText();
            newPassword2 = newPasswordField2.getText();
        }
        else {
            oldPassword = oldPasswordOpenField.getText();
            newPassword = newPasswordOpenField.getText();
            newPassword2 = newPasswordOpenField2.getText();
        }

        if (oldPassword.length() == 0) {
            errorText.setText("ERROR: Old Password cannot be empty!");
            mediaPlayer.play();
        }
        else if (newPassword.length() == 0) {
            errorText.setText("ERROR: New Password cannot be empty!");
            mediaPlayer.play();
        }
        else if (newPassword2.length() == 0) {
            errorText.setText("ERROR: New Password Again cannot be empty!");
            mediaPlayer.play();
        }
        else if (!LoginScene.selectedUser.getPassword().equals(DataManager.hashPassword(oldPassword))) {
            errorText.setText("ERROR: Password is wrong!");
            mediaPlayer.play();
        }
        else if (!newPassword.equals(newPassword2)){
            errorText.setText("ERROR: Passwords do not match!");
            mediaPlayer.play();
        }
        else {
            errorText.setText("SUCCESS: You have successfully change your password!");
            DataManager.users.get(LoginScene.selectedUser.getName()).setPassword(DataManager.hashPassword(newPassword));
        }
        deletion();
    }

    @Override
    public void changePasswordField() {
        if (!passwordIsActive) {
            oldPasswordOpenField.setText(oldPasswordField.getText());
            newPasswordOpenField.setText(newPasswordField.getText());
            newPasswordOpenField2.setText(newPasswordField2.getText());
            gridPane.getChildren().remove(oldPasswordField);
            gridPane.getChildren().remove(newPasswordField);
            gridPane.getChildren().remove(newPasswordField2);
            gridPane.add(oldPasswordOpenField,1,0);
            gridPane.add(newPasswordOpenField,1,1);
            gridPane.add(newPasswordOpenField2,1,2);
            passwordIsActive = true;
        }
        else {
            oldPasswordField.setText(oldPasswordOpenField.getText());
            newPasswordField.setText(newPasswordOpenField.getText());
            newPasswordField2.setText(newPasswordOpenField2.getText());
            gridPane.getChildren().remove(oldPasswordOpenField);
            gridPane.getChildren().remove(newPasswordOpenField);
            gridPane.getChildren().remove(newPasswordOpenField2);
            gridPane.add(oldPasswordField,1,0);
            gridPane.add(newPasswordField,1,1);
            gridPane.add(newPasswordField2,1,2);
            passwordIsActive = false;
        }
    }


    /**
     * This function clears the entries of all fields.
     */
    private void deletion(){
        oldPasswordField.setText("");
        oldPasswordOpenField.setText("");
        newPasswordField.setText("");
        newPasswordOpenField.setText("");
        newPasswordField2.setText("");
        newPasswordOpenField2.setText("");
    }
}
