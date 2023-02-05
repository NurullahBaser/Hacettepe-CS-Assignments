import javafx.application.Application;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import java.io.File;

public class SelectFilmScene extends Application implements Scenes{

    public static Film selectedFilm;

    HBox settingsBox;
    Button settingButton;

    HBox stringPane;
    Text welcomeText;

    HBox filmSelect;
    ChoiceBox<String> choiceBox;
    Button okButton;

    HBox edits;
    Button addFilm;
    Button removeFilm;
    Button editUsers;

    HBox logoutBox;
    Button logout;

    VBox filmSelectPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),480,350);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setScene(scene);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setResizable(false);
        primaryStage.show();
    }

    @Override
    public VBox createPane(Stage primaryStage) {
        String settingIcon = new File("extra\\setting_icon.png").toURI().toString(); //TODO SRC
        ImageView settingView = new ImageView(settingIcon);
        settingView.setFitWidth(20);
        settingView.setFitHeight(20);

        settingsBox = new HBox();
        settingsBox.setAlignment(Pos.TOP_RIGHT);
        settingButton = new Button("Settings");
        settingButton.setGraphic(settingView);
        settingButton.setAlignment(Pos.TOP_RIGHT);
        settingsBox.getChildren().add(settingButton);
        settingButton.setOnAction(event -> SceneManager.openUserSettingScene(primaryStage));

        stringPane = new HBox();
        welcomeText = new Text(textCreator());
        welcomeText.setTextAlignment(TextAlignment.CENTER);
        stringPane.getChildren().add(welcomeText);
        stringPane.setAlignment(Pos.CENTER);

        filmSelect = new HBox(15);
        choiceBox = new ChoiceBox<>();
        choiceBox.setPrefWidth(300);
        try {
            choiceBox.getItems().addAll(DataManager.films.keySet());
            choiceBox.getSelectionModel().selectFirst();
        }
        catch (Exception ignored){}

        okButton = new Button("OK");
        filmSelect.getChildren().addAll(choiceBox,okButton);
        okButton.setOnAction(event -> check(primaryStage));

        edits = new HBox(15);
        if (LoginScene.selectedUser.isAdmin()) {
            addFilm = new Button("Add Film");
            removeFilm = new Button("Remove Film");
            editUsers = new Button("Edit Users");
            edits.getChildren().addAll(addFilm,removeFilm,editUsers);
            edits.setAlignment(Pos.CENTER);
            removeFilm.setOnAction(event -> SceneManager.openRemoveFilmScene(primaryStage));
            addFilm.setOnAction(event -> SceneManager.openAddFilmScene(primaryStage));
            editUsers.setOnAction(event -> SceneManager.openEditUsersScene(primaryStage));
        }

        logoutBox = new HBox();
        logoutBox.setAlignment(Pos.CENTER);
        logout = new Button("LOG OUT");
        logoutBox.getChildren().add(logout);
        logoutBox.setAlignment(Pos.CENTER_RIGHT);
        logout.setOnAction(event -> SceneManager.openLoginScene(primaryStage));

        filmSelectPane = new VBox(settingsBox,stringPane,filmSelect,edits,logoutBox);
        filmSelectPane.setSpacing(15);
        filmSelectPane.setAlignment(Pos.CENTER);
        filmSelectPane.setPadding(new Insets(20,25,20,25));
        return filmSelectPane;
    }

    /**
     * This function generates a welcome text according to the user's information.
     * @return string of user's information.
     */
    private String textCreator() {
        String textString = "Welcome "+ LoginScene.selectedUser.getName();
        if(LoginScene.selectedUser.isAdmin()&& LoginScene.selectedUser.isClubMember()) {
            textString += "(Admin - Club Member)!\nYou can either select film below or do edits.";
        }
        else if (LoginScene.selectedUser.isAdmin()) {
            textString += "(Admin)!\nYou can either select film below or do edits.";
        }
        else if (LoginScene.selectedUser.isClubMember()) {
            textString += "(Club Member)!\nSelect a film and then click OK to continue.";
        }
        else {
            textString += "!\nSelect a film and then click OK to continue.";
        }
        return textString;
    }

    /**
     * When the ok button is pressed, it examines the necessary conditions and performs the function of the button if no error is given.
     * @param primaryStage is used if a new scene is to be opened.
     */
    private void check(Stage primaryStage) {
        if (!(DataManager.films.get(choiceBox.getValue()) == null)) {
            selectedFilm = DataManager.films.get(choiceBox.getValue());
            SceneManager.openFilmScene(primaryStage);
        }
    }
}
