import javafx.application.Application;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;

public class UserSettingScene extends Application implements Scenes{

    HBox usernameBox;
    Text usernameText;

    GridPane gridPane;
    Text admin;
    Text clubMember;
    Button changeUsernameButton;
    Button changePasswordButton;
    Button backButton;

    VBox userSettingPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),480,350);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        usernameBox = new HBox();
        usernameText = new Text("Welcome " + LoginScene.selectedUser.getName() + "!");
        usernameText.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        usernameBox.getChildren().add(usernameText);
        usernameBox.setAlignment(Pos.CENTER);

        gridPane = new GridPane();
        gridPane.setVgap(20);
        gridPane.setHgap(35);
        gridPane.setAlignment(Pos.CENTER);
        admin = new Text();
        admin.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        clubMember = new Text();
        clubMember.setFont(Font.font("Verdana", FontWeight.BOLD,15));
        admin.setTextAlignment(TextAlignment.CENTER);
        clubMember.setTextAlignment(TextAlignment.CENTER);
        if (LoginScene.selectedUser.isAdmin()) {
            admin.setText("Admin \u2714");
            admin.setFill(Color.GREEN);
        }
        else {
            admin.setText("Admin \u274C");
            admin.setFill(Color.RED);
        }
        if (LoginScene.selectedUser.isClubMember()) {
            clubMember.setText("Club Member \u2714");
            clubMember.setFill(Color.GREEN);

        }
        else {
            clubMember.setText("Club Member \u274C");
            clubMember.setFill(Color.RED);
        }

        changeUsernameButton = new Button("Change Username");
        changePasswordButton = new Button("Change Password");
        changeUsernameButton.setAlignment(Pos.CENTER);
        changePasswordButton.setAlignment(Pos.CENTER);
        backButton = new Button("\u25C0 Back");
        backButton.setAlignment(Pos.CENTER_LEFT);

        changeUsernameButton.setOnAction(event -> SceneManager.openChangeUsernameScene(primaryStage));
        changePasswordButton.setOnAction(event -> SceneManager.openChangePasswordScene(primaryStage));
        backButton.setOnAction(event -> SceneManager.openFilmSelectScene(primaryStage));

        gridPane.add(admin,0,0);
        gridPane.add(clubMember,0,1);
        gridPane.add(changeUsernameButton,0,2);
        gridPane.add(changePasswordButton,1,2);
        gridPane.add(backButton,0,3);

        userSettingPane = new VBox(usernameBox,gridPane);
        userSettingPane.setSpacing(30);
        userSettingPane.setPadding(new Insets(15,30,15,30));
        userSettingPane.setAlignment(Pos.CENTER);
        return userSettingPane;
    }
}
