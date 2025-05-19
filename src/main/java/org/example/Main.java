package org.example;

import GUI.LoginWindow;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        // Open the login window
        new LoginWindow().show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}