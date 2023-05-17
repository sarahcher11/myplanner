package TP.Controllers;

import TP.Noyau.DateDebutException;
import TP.Noyau.Periode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.time.LocalDate;

public class PeriodeController {

    @FXML
    private DatePicker datedebut;


    @FXML
    private DatePicker datefin;

    @FXML
    private Button enter;


    void showAlert(String message) {
        Stage stage = new Stage();
        stage.initStyle(StageStyle.UNDECORATED);
        Label label = new Label(message);
        label.setFont(Font.font("Calibri", 16));
        label.setTextFill(Color.WHITE);
        StackPane stackPane = new StackPane(label);
        stackPane.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(5), Insets.EMPTY)));
        stackPane.setPadding(new Insets (10));

        Scene scene = new Scene(stackPane);
        scene.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode() == KeyCode.ENTER) {
                stage.close();
            }
        });
        stage.setScene(scene);
        stage.show();
        // Close the alert after 2 seconds
        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(2), evt -> stage.close()));
        timeline.play();
    }

    public void specifierPeriode()
    {
        enter.setOnMouseClicked(MouseEvent->{
            if(datefin.getValue()==null)
            {
                showAlert("Please specify the date of end of this periode");
            }
            else
            {
                LocalDate day=datedebut.getValue();
                if(day==null)
                {
                    datedebut.setValue(LocalDate.now());
                    day=LocalDate.now();
                }
                else
                {
                    LocalDate endDay=datefin.getValue();
                    try {
                        Periode periode=new Periode(day,endDay);
                        System.out.println(day);
                    } catch (DateDebutException e) {
                        showAlert("You can not choose this dates");
                    }
                }

            }
        });
    }



}
