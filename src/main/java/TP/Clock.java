package TP;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Clock extends Application {



        @Override
        public void start(Stage stage) throws Exception {
            Parent root= FXMLLoader.load(getClass().getResource("IntroduireTache.fxml"));
            Scene scene=new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Hour Minute Picker");
            stage.show();
        }

        public static void main(String[] args) {
            launch(args);
        }
    }






