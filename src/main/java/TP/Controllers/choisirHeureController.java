package TP.Controllers;

import TP.Noyau.Creneau;
import TP.Noyau.HeureDebutException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import java.io.IOException;
import java.time.LocalTime;
import java.util.function.UnaryOperator;

public class choisirHeureController {
    @FXML
    private Button AddCreneau;

    @FXML
    private Button cancelCreneau;

    @FXML
    private Spinner<Integer> hour1;

    @FXML
    private Spinner<Integer> hour2;

    @FXML
    private Spinner<Integer> minute1;

    @FXML
    private Spinner<Integer> minute2;



    @FXML
   public  void cancelCreneau() {

        cancelCreneau.setOnMouseClicked(MouseEvent->{
            Stage stage=(Stage) cancelCreneau.getScene().getWindow();
            stage.close();
        });

    }

    @FXML
    public void addNewSlot() {
        Parent root = null;
        LocalTime heureDebut;
        LocalTime heureFin;
        try {
            root = FXMLLoader.load(getClass().getResource("../choisirHeure.fxml"));

            hour1 = (Spinner) root.lookup("#hour1");
            hour1.setEditable(true);
            hour2 = (Spinner) root.lookup("#hour2");
            hour2.setEditable(true);
            minute1 = (Spinner) root.lookup("#minute1");
            minute1.setEditable(true);
            minute2 = (Spinner) root.lookup("#minute2");
            minute2.setEditable(true);

// Set up SpinnerValueFactory for each spinner
            SpinnerValueFactory.IntegerSpinnerValueFactory hourFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
            SpinnerValueFactory.IntegerSpinnerValueFactory hourFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23, 0);
            SpinnerValueFactory.IntegerSpinnerValueFactory minuteFactory1 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);
            SpinnerValueFactory.IntegerSpinnerValueFactory minuteFactory2 = new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59, 0);

            hour1.setValueFactory(hourFactory1);
            hour2.setValueFactory(hourFactory2);
            minute1.setValueFactory(minuteFactory1);
            minute2.setValueFactory(minuteFactory2);
            hour1.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                int value = Integer.parseInt(newValue);
                hour1.getValueFactory().setValue(value);
            });

            hour2.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                int value = Integer.parseInt(newValue);
                hour2.getValueFactory().setValue(value);
            });

            minute1.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                int value = Integer.parseInt(newValue);
                minute1.getValueFactory().setValue(value);
            });

            minute2.getEditor().textProperty().addListener((observable, oldValue, newValue) -> {
                int value = Integer.parseInt(newValue);
                minute2.getValueFactory().setValue(value);
            });

            UnaryOperator<TextFormatter.Change> hourFilter = change -> {
                String text = change.getText();
                if (text.matches("\\d*")) {
                    return change;
                }
                return null;
            };
            UnaryOperator<TextFormatter.Change> minuteFilter = change -> {
                String text = change.getText();
                if (text.matches("\\d*")) {
                    return change;
                }
                return null;
            };
            System.out.println(minute1.getValue());

            TextFormatter<Integer> hourFormatter1 = new TextFormatter<>(hourFilter);
            TextFormatter<Integer> hourFormatter2 = new TextFormatter<>(hourFilter);
            TextFormatter<Integer> minuteFormatter1 = new TextFormatter<>(minuteFilter);
            TextFormatter<Integer> minuteFormatter2 = new TextFormatter<>(minuteFilter);

            hour1.getEditor().setTextFormatter(hourFormatter1);
            hour2.getEditor().setTextFormatter(hourFormatter2);
            minute1.getEditor().setTextFormatter(minuteFormatter1);
            minute2.getEditor().setTextFormatter(minuteFormatter2);

            heureDebut = LocalTime.of(hour1.getValue(), minute1.getValue());
            heureFin = LocalTime.of(hour2.getValue(), minute2.getValue());

            AddCreneau.setOnMouseClicked(MouseEvent -> {
                try {
                    System.out.println("heure deeeeeeeeeebut" + heureDebut);
                    System.out.println("heure fiiiiiiiiiiiiiiin" + heureFin);
                    Creneau creneau = new Creneau(heureDebut, heureFin);
                    creneau.setJournee(CalendrierController.getLastClickedJournee());
                    CalendrierController.getLastClickedJournee().ajouterCreneau(creneau);
                } catch (HeureDebutException e) {
                    throw new RuntimeException(e);
                }
            });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
