import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public interface Scenes {

    /**
     * This function defines all the elements necessary for the scene and creates their properties.
     * @param primaryStage is used if a new scene is to be opened.
     * @return a box containing all the elements.
     */

    VBox createPane(Stage primaryStage);
}
