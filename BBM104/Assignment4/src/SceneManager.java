import javafx.stage.Stage;

public class SceneManager {

    /*
    This class contains the functions for which the scene changes are performed.
     */

    public static void openSignUpScene(Stage primaryStage) {
        try {
            new SignUpScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openLoginScene(Stage primaryStage) {
        try {
            LoginScene loginScene = new LoginScene();
            loginScene.start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openFilmSelectScene(Stage primaryStage) {
        try {
            new SelectFilmScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openRemoveFilmScene(Stage primaryStage) {
        try {
            new RemoveFilmScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void openAddFilmScene(Stage primaryStage) {
        try {
            new AddFilmScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openUserSettingScene(Stage primaryStage) {
        try {
            new UserSettingScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openChangeUsernameScene(Stage primaryStage) {
        try {
            new ChangeUsernameScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openEditUsersScene(Stage primaryStage) {
        try {
            new EditUsersScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openFilmScene(Stage primaryStage) {
        try {
            new FilmScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openRemoveHallScene(Stage primaryStage) {
        try {
            new RemoveHallScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openAddHallScene(Stage primaryStage) {
        try {
            new AddHallScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openChangePasswordScene(Stage primaryStage) {
        try {
            new ChangePasswordScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void openSelectSeatScene(Stage primaryStage) {
        try {
            new SelectSeatScene().start(primaryStage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
