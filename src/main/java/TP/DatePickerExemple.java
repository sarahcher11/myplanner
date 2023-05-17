package TP;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;

public class DatePickerExemple extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        DatePicker datePicker = new DatePicker(); // create a new date picker
        Button button = new Button("Get selected date"); // create a new button

        // add an event handler to the button to print the selected date
        button.setOnAction(event -> {
            LocalDate selectedDate = datePicker.getValue();
            System.out.println("Selected date: " + selectedDate);
        });
        VBox vbox = new VBox(datePicker, button); // add the date picker and button to the VBox container
        vbox.setPrefHeight(200);
        Scene scene = new Scene(vbox, 400, 400); // create a new scene with the VBox container
        stage.setScene(scene); // set the scene on the stage
        stage.show(); // show the stage
    }

    public static void main(String[] args) {
        launch(args);
    }

}





