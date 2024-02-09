package TP.Controllers;

import TP.Noyau.*;
import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class TacheAutomatiqueController implements Initializable {
    @FXML
    private Button ajouterTask;

    @FXML
    private ChoiceBox<String> categoryChoice;

    @FXML
    private TextField deadhourPicker;

    @FXML
    private DatePicker deadlinePicker;

    @FXML
    private TextField deadminutePicker;

    @FXML
    private TextField hourPicker;

    @FXML
    private JFXCheckBox isDecomposable;
    private boolean isDecomposableBool;

    @FXML
    private TextField minutePicker;

    @FXML
    private TextField nameTask;


    @FXML
    private ChoiceBox<Priorite> priorityChoice;





    public void addButton()
    {
        String nomTache=nameTask.getText();
        LocalTime duree=null;
        if(!hourPicker.getText().equals("") && !minutePicker.getText().equals(""))
        {
            duree=LocalTime.of(Integer.parseInt(hourPicker.getText()),Integer.parseInt(minutePicker.getText()));

        }
        Priorite priorite=priorityChoice.getValue();
        String selectCategory = categoryChoice.getValue();

        Categorie categorie=LoginController.getUser().getCategories().get(selectCategory);

        // Access the isBlocked variable to get its value
        LocalDate deadline=deadlinePicker.getValue();
        LocalTime heureDeadline=null;
        if(!deadhourPicker.getText().equals("") && !deadminutePicker.getText().equals(""))
        {
            heureDeadline=LocalTime.of(Integer.parseInt(deadhourPicker.getText()),Integer.parseInt(deadminutePicker.getText()));

        }
        LocalTime finalDuree = duree;
        LocalTime finalHeureDeadline = heureDeadline;
        ajouterTask.setOnMouseClicked(MouseEvent->{


            if(categorie!=null && priorite!=null && nomTache!=null && hourPicker.getText()!=null
                    && minutePicker.getText()!=null && deadlinePicker.getValue()!=null && deadhourPicker.getText()!=null
                    && deadminutePicker!=null)
            {
                if(!isDecomposableBool)
                {
                    TacheSimple tacheSimple=new TacheSimple(nomTache, finalDuree,priorite,deadline, finalHeureDeadline,categorie);
                    tacheSimple.setEtatDeRealisation(EtatDeRealisation.notRealized);
                    Creneau creneau=LoginController.getUser().getPlanningCourant().planifier(tacheSimple);
                    if(creneau!=null)
                    {

                    // Afficher une alerte avec les détails du créneau
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Confirmation");
                    alert.setHeaderText("Slot's details");
                    alert.setContentText("Do you like the slot choosed!\n\n" +
                            "Start: " + creneau.getHeureDebut() + "\n" +
                            "End: " + creneau.getHeureFin() + "\n" +
                            "Date: " + creneau.getJournee().getDateDuJour());

                    // Ajouter des boutons de confirmation et d'annulation
                    ButtonType confirmerButton = new ButtonType("OK");
                    ButtonType annulerButton = new ButtonType("Cancel");

                    // Définir les boutons de l'alerte
                    alert.getButtonTypes().setAll(confirmerButton, annulerButton);

                    // Attendre la réponse de l'utilisateur
                    Optional<ButtonType> result = alert.showAndWait();

                    // Vérifier si l'utilisateur a confirmé ou annulé
                    if (result.isPresent() && result.get() == confirmerButton) {

                        LoginController.getUser().getPlanningCourant().planifierValidee(tacheSimple,creneau);


                    } else {
                        // L'utilisateur a annulé, ne rien faire
                        // ...
                    }
                    }


                }else
                {
                    TacheDecomposable tacheDecomposable=new TacheDecomposable(nomTache, finalDuree,priorite,deadline, finalHeureDeadline,categorie);
                    tacheDecomposable.setEtatDeRealisation(EtatDeRealisation.notRealized);
                    LoginController.getUser().getPlanningCourant().planifier(tacheDecomposable);
                }

                Stage stage=(Stage) ajouterTask.getScene().getWindow();
                stage.close();
            }else
            {
                showAlert("You must fill all the information");
            }

        });
    }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        ObservableList<Priorite> priorities = FXCollections.observableArrayList(Priorite.High, Priorite.Low, Priorite.Medium);
        // Set the items for the priorityChoice ChoiceBox
        priorityChoice.setItems(priorities);
        // Retrieve the user's categories
        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
        // Define the updateCategoryOptions method
        Runnable updateCategoryOptions = () -> {
            // Retrieve the user's categories
            HashMap<String, Categorie> categories = LoginController.getUser().getCategories();

            // Clear the categoryOptions list
            categoryOptions.clear();

            // Extract the category names and add them to the categoryOptions list
            for (Categorie categorie : categories.values()) {
                categoryOptions.add(categorie.getNomCategorie());
            }

            // Set the items for the categoryChoice ChoiceBox
            categoryChoice.setItems(categoryOptions);
        };

        // Call the updateCategoryOptions() method initially to populate the categoryOptions list
        updateCategoryOptions.run();
        isDecomposable.setOnAction(event -> {
            // Update the isBlocked variable based on the checkbox's checked state
            isDecomposableBool = isDecomposable.isSelected();
        });

    }
}