package TP.Controllers;

import TP.Noyau.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;

public class TasksController implements Initializable {


    @FXML
    private Button add;
    @FXML
    private TableView<Tache> taskTable;
    @FXML
    private TableColumn<Tache, String> taskNameColumn;
    @FXML
    private TableColumn<Tache, String> deadlineColumn;
    @FXML
    private TableColumn<Tache, String> categoryColumn;
    @FXML
    private TableColumn<Tache, String> completionStatusColumn;
    @FXML
    private TableColumn<Tache, String> dateCreneau;
    @FXML
    private TableColumn<Tache, String> priorityTask;
    @FXML
    private TableColumn<Tache, EtatDeRealisation> modifierEtatColumn;


    private ObservableList<Tache> tasks = FXCollections.observableArrayList();

    public void displayTasks() {
        // Get the user's tasks
        List<Tache> tacheList = LoginController.getUser().getPlanningCourant().getTaches();

        // Clear the ObservableList before adding the new tasks

        tasks.clear();

        // Iterate over the tasks and add them to the ObservableList
        for (Tache tache : tacheList) {
            tasks.add(tache);
        }
        tasks.sort(Comparator.comparing(Tache::getPriorite));
        // Set the items in the TableView
        taskTable.setItems(tasks);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Set up the cell value factories for each column
        taskNameColumn.setCellValueFactory(new PropertyValueFactory<>("nom"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<>("dayOfDeadline"));
        categoryColumn.setCellValueFactory(cellData -> {
            Tache tache = cellData.getValue();
            Categorie categorie = tache.getCategorie();
            if (categorie != null) {
                return new SimpleStringProperty(categorie.getNomCategorie());
            }
            return new SimpleStringProperty("");
        });

        completionStatusColumn.setCellValueFactory(cellData -> {
            Tache tache = cellData.getValue();
            EtatDeRealisation etatDeRealisation = tache.getEtatDeRealisation();
            if (etatDeRealisation != null) {
                return new SimpleStringProperty(etatDeRealisation.toString());
            }
            return new SimpleStringProperty("");
        });

        priorityTask.setCellValueFactory(new PropertyValueFactory<>("priorite"));

        // Set up the cell value factory for the creneauColumn
        // Set up the cell value factory for the creneauColumn
        dateCreneau.setCellValueFactory(cellData -> {
            Tache tache = cellData.getValue();
            if (tache instanceof TacheSimple) {
                TacheSimple tacheSimple = (TacheSimple) tache;
                Creneau creneau = tacheSimple.getCreneau();
                if (creneau != null) {
                    String date = creneau.getJournee().getDateDuJour().toString();
                    String heure = creneau.getHeureDebut().toString();
                    System.out.println("Date: " + date); // Debug statement
                    System.out.println("Heure: " + heure); // Debug statement
                    return new SimpleStringProperty(date + " " + heure);
                }
            }
            return new SimpleStringProperty("");
        });
        // Définir la cellule de la colonne modifierEtat avec une ChoiceBox
        modifierEtatColumn.setCellFactory(column -> {
            TableCell<Tache, EtatDeRealisation> cell = new TableCell<>() {
                @Override
                protected void updateItem(EtatDeRealisation item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                    } else {
                        setText(item.toString());
                    }
                }
            };

            // Ajoutez un événement de clic sur la cellule
            cell.setOnMouseClicked(event -> {
                if (!cell.isEmpty()) {
                    Tache tache = cell.getTableView().getItems().get(cell.getIndex());
                    showModifierWindow(tache);
                }
            });

            return cell;
        });



        // Call the displayTasks() method
        displayTasks();
    }


    private void showModifierWindow(Tache tache) {
        Stage stage = new Stage();
        VBox vbox = new VBox();

        // Création de la ChoiceBox avec les valeurs de l'énumération EtatDeRealisation
        ChoiceBox<EtatDeRealisation> choiceBox = new ChoiceBox<>();
        choiceBox.getItems().addAll(EtatDeRealisation.values());

        // Création du bouton "OK"
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            EtatDeRealisation etat = choiceBox.getValue();
             tache.setEtatDeRealisation(etat);
            stage.close(); // Ferme la fenêtre
        });

        vbox.getChildren().addAll(choiceBox, okButton);

        Scene scene = new Scene(vbox);
        stage.setScene(scene);
        stage.show();
    }






    public void ajouterTache()
    {
          add.setOnMouseClicked(MouseEvent->{
              try {
                  Parent root= FXMLLoader.load(this.getClass().getResource("../TacheNonProgram.fxml"));
                  Scene scene=new Scene(root);
                  Stage stage=new Stage();
                  stage.setScene(scene);
                  Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                  double centerX = screenBounds.getMinX() + screenBounds.getWidth() / 2;
                  double centerY = screenBounds.getMinY() + screenBounds.getHeight() / 2;
                  double stageWidth = stage.getWidth();
                  double stageHeight = stage.getHeight();
                  stage.setX(centerX - stageWidth / 2);
                  stage.setY(centerY - stageHeight / 2);
                  stage.setResizable(false);
                  stage.show();
              } catch (IOException e) {
                  throw new RuntimeException(e);
              }
          });
    }



}
