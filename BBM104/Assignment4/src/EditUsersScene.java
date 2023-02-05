import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;


public class EditUsersScene extends Application implements Scenes {

    HBox tableBox;
    TableView tableView;
    TableColumn<User, String> firstNameColumn;
    TableColumn<User, Boolean> clubMember;
    TableColumn<User, Boolean> admin;
    ObservableList<User> observableList;

    HBox buttonsBox;
    Button backButton;
    Button clubMemberButton;
    Button adminButton;

    VBox editUsersPane;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Scene scene = new Scene(createPane(primaryStage),650,520);
        primaryStage.getIcons().add(DataManager.logoImage);
        primaryStage.setTitle(DataManager.properties.getProperty("title"));
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

    }

    @Override
    public VBox createPane(Stage primaryStage) {
        tableBox = new HBox();
        tableView = new TableView();

        firstNameColumn = new TableColumn<>("Username");
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        firstNameColumn.setMinWidth(250);

        clubMember = new TableColumn<>("Club Member");
        clubMember.setCellValueFactory(new PropertyValueFactory<>("clubMember"));
        clubMember.setMinWidth(150);

        admin = new TableColumn<>("Admin");
        admin.setCellValueFactory(new PropertyValueFactory<>("admin"));
        admin.setMinWidth(150);

        tableView.getColumns().addAll(firstNameColumn,clubMember,admin);
        tableView.setPlaceholder(new Text("No user available in the database!"));
        tableBox.getChildren().add(tableView);
        tableBox.setAlignment(Pos.CENTER);

        observableList = FXCollections.observableArrayList();
        DataManager.users.forEach((k, v) ->{
            if (!k.equals(LoginScene.selectedUser.getName())) {
                observableList.add(v);
            }
        });
        tableView.setItems(observableList);
        tableView.getSelectionModel().selectFirst();

        buttonsBox = new HBox(10);
        buttonsBox.setAlignment(Pos.CENTER);
        backButton = new Button("\u25C0 Back");
        clubMemberButton = new Button("Promote/Demote Club Member");
        adminButton = new Button("Promote/Demote Admin");
        buttonsBox.getChildren().addAll(backButton,clubMemberButton,adminButton);

        clubMemberButton.setOnAction(event -> DPClubMember());
        adminButton.setOnAction(event -> DPAdmin());
        backButton.setOnAction(event -> SceneManager.openFilmSelectScene(primaryStage));

        editUsersPane = new VBox(15);
        editUsersPane.getChildren().addAll(tableBox,buttonsBox);
        return editUsersPane;
    }

    /**
     * This function changes the club member information of the selected person.
     */
    private void DPClubMember() {
        try {
            TablePosition position = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
            DataManager.users.get(observableList.get(position.getRow()).getName()).setClubMember(!observableList.get(position.getRow()).isClubMember());
            tableView.refresh();
        }
        catch (Exception ignored){}
    }

    /**
     * This function changes the admin information of the selected person.
     */
    private void DPAdmin() {
        try {
            TablePosition position = (TablePosition) tableView.getSelectionModel().getSelectedCells().get(0);
            DataManager.users.get(observableList.get(position.getRow()).getName()).setAdmin(!observableList.get(position.getRow()).isAdmin());
            tableView.refresh();
        }
        catch (Exception ignored) {}
    }
}
