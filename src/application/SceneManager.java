package application;

import javafx.scene.Scene;
import javafx.scene.layout.VBox;

public class SceneManager {

    public static void show(VBox root) {
        Scene scene = new Scene(root, 1280, 840);
        Main.stage.setScene(scene);
        Main.stage.show();
    }
}
