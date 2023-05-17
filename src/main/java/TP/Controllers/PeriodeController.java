package TP.Controllers;
import TP.Creneau;
import TP.DateDebutException;
import TP.Journee;
import TP.Periode;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.UnaryOperator;
public class PeriodeController {

    @FXML
    private DatePicker datedebut;
    @FXML
    private DatePicker datefin;

    @FXML
    private Button enter;


    @FXML
    private Label date;

    @FXML
    private Button addnew;

    @FXML
    private ChoiceBox choice;

    @FXML
    private Button next;


    @FXML
    private Spinner hour1;
    @FXML
    private Spinner minute1;

    @FXML
    private Spinner hour2;
    @FXML
    private Spinner minute2;

    @FXML
    private Button AddCreneau;

    @FXML
    private Button cancelCreneau;
    private Periode periode;

    private ArrayList<Journee> journees;


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
                        periode=new Periode(day,endDay);
                        Parent root= FXMLLoader.load(this.getClass().getResource("../TacheParJour.fxml"));
                        date=(Label) root.lookup("#date");
                        date.setText(periode.getDateDebut().toString());
                        Scene scene=new Scene(root);
                        Stage stage=(Stage) enter.getScene().getWindow();
                        stage.setScene(scene);
                        stage.show();
                    } catch (DateDebutException e) {
                        showAlert("You can not choose this dates");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        });
    }



    @FXML
    public void ajouterCreneau()
    {

        addnew.setOnMouseClicked(MouseEvent->{

            try {
                Parent root=FXMLLoader.load(this.getClass().getResource("../choisirHeure.fxml"));
                hour1= (Spinner) root.lookup("#hour1");
                hour1.setEditable(true);
                hour2=(Spinner) root.lookup("#hour2");
                hour2.setEditable(true);
                minute1=(Spinner) root.lookup("#minute1");
                minute1.setEditable(true);
                minute2=(Spinner) root.lookup("#minute2");
                minute2.setEditable(true);
                hour1 = new Spinner<Integer>(0, 23, 0);
                hour2= new Spinner<Integer>(0, 23, 0);

                UnaryOperator<TextFormatter.Change> hourFilter = change -> {
                    String text = change.getText();
                    if (text.matches("\\d*")) {
                        return change;
                    }
                    return null;
                };
                UnaryOperator<TextFormatter.Change> hourFilter2 = change -> {
                    String text = change.getText();
                    if (text.matches("\\d*")) {
                        return change;
                    }
                    return null;
                };
                TextFormatter<Integer> hourFormatter1 = new TextFormatter<>(hourFilter);
                TextFormatter<Integer> hourFormatter2 = new TextFormatter<>(hourFilter2);
                hour1.getEditor().setTextFormatter(hourFormatter1);
                hour2.getEditor().setTextFormatter(hourFormatter2);
                minute1 = new Spinner<Integer>(0, 59, 0);
                minute2= new Spinner<Integer>(0, 59, 0);

                UnaryOperator<TextFormatter.Change> minuteFilter = change -> {
                    String text = change.getText();
                    if (text.matches("\\d*")) {
                        return change;
                    }
                    return null;
                };
                UnaryOperator<TextFormatter.Change> minuteFilter2 = change -> {
                    String text = change.getText();
                    if (text.matches("\\d*")) {
                        return change;
                    }
                    return null;
                };
                TextFormatter<Integer> minuteFormatter1 = new TextFormatter<>(minuteFilter);
                TextFormatter<Integer> minuteFormatter2 = new TextFormatter<>(minuteFilter2);
                minute1.getEditor().setTextFormatter(minuteFormatter1);
                minute2.getEditor().setTextFormatter(minuteFormatter2);
                Stage stage=new Stage();
                Scene scene = new Scene(root); // create a new scene with the VBox container
                stage.setScene(scene); // set the scene on the stage
                stage.show(); // show the stage
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });
    }




    public void cancelCreneau()
    {
        cancelCreneau.setOnMouseClicked(MouseEvent->{
            Stage satge=(Stage) cancelCreneau.getScene().getWindow();
            satge.close();
        });
    }


    public void newCreneau()
    {
        int heure1= (int) hour1.getValue();
        int heure2=(int) hour2.getValue();
        int min1=(int) minute1.getValue();
        int min2=(int)minute2.getValue();

    }











}
