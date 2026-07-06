package application;

import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static Stage stage;

    @Override
    public void start(Stage s) {
        stage = s;
        stage.setTitle("UET Taxila - Admission Eligibility System");
        stage.setResizable(false);
        SceneManager.show(WelcomeScene.build());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
