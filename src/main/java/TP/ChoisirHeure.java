package TP;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;

public class ChoisirHeure extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        HourMinutePicker hourMinutePicker = new HourMinutePicker(); // create the custom control
        Button button = new Button("Choisir l'heure"); // create a new button
        button.setLayoutX(150);
        button.setLayoutY(200);
        VBox vbox = new VBox(hourMinutePicker,button); // add the control to a VBox container


        button.setOnMouseClicked(MouseEvent->{
            int hour=hourMinutePicker.getHour();
            System.out.println("hour is "+ hour);
            int minute=hourMinutePicker.getMinute();
            System.out.println("minute is "+ minute);
        });
        vbox.setPrefWidth(200);
        vbox.setPrefHeight(400);


        Scene scene = new Scene(vbox); // create a new scene with the VBox container
        stage.setScene(scene); // set the scene on the stage
        stage.show(); // show the stage
    }

    public static void main(String[] args) {
        launch(args);
    }
}

