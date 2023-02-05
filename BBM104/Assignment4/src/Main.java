import javafx.application.Application;
import javafx.stage.Stage;
import java.io.IOException;


public class Main extends Application {
    public static void main(String[] args) throws IOException {
        DataManager.backUpReader();
        DataManager.propertiesReader();
        launch(args);
        DataManager.backUpWriter();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        SceneManager.openLoginScene(primaryStage);
    }
}
