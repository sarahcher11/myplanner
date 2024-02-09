package TP.Controllers;

import TP.Noyau.MyDesktopPlanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Screen;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class SideBarController implements Initializable {

    @FXML
    private AnchorPane parent;

    @FXML
    private Button calendrier;

    @FXML
    private Button compte;

    @FXML
    private Button logout;

    @FXML
    private Button projet;

    @FXML
    private Button statistique;

    @FXML
    private Button tache;

    @FXML
    private VBox side;



    public void showPage(String fxmlFile) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
            Parent page = loader.load();
            // Remove any existing main content from the parent
            parent.getChildren().remove(0);
            parent.getChildren().add(0,page);
            side.toFront();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void calendarPage() {

        calendrier.setOnMouseClicked(MouseEvent->{
            showPage("../Calendrier.fxml");
        });

    }
    public void tasksPage() {

        tache.setOnMouseClicked(MouseEvent->{
            showPage("../Tasks.fxml");
        });

    }
    public void profilPage() {

        compte.setOnMouseClicked(MouseEvent->{
            showPage("../Profil.fxml");
        });

    }
    public void statistiquePage() {

        statistique.setOnMouseClicked(MouseEvent->{
            showPage("../Statistique.fxml");
        });
    }
    public void projectsPage() {

        projet.setOnMouseClicked(MouseEvent->{
            showPage("../Projects.fxml");
        });

    }
    @FXML
    public void logoutPage()
    {
        logout.setOnMouseClicked(MouseEvent->{
            try {
                LoginController.getDesktopPlanner().serializeDesktopPlanner();
                LoginController.getDesktopPlanner().deserializeDesktopPlanner(); // Deserialize the object
                System.out.println(LoginController.getUser().getPseudo());
                Parent page=FXMLLoader.load(this.getClass().getResource("../loginPageF.fxml"));
                Scene scene=new Scene(page);
                Stage stage=(Stage) logout.getScene().getWindow();
                stage.setResizable(false);
                stage.setScene(scene);
                Rectangle2D screenBounds = Screen.getPrimary().getVisualBounds();
                double centerX = screenBounds.getMinX() + screenBounds.getWidth() / 2;
                double centerY = screenBounds.getMinY() + screenBounds.getHeight() / 2;
                double stageWidth = stage.getWidth();
                double stageHeight = stage.getHeight();
                stage.setX(centerX - stageWidth / 2);
                stage.setY(centerY - stageHeight / 2);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        showPage("../Calendrier.fxml");


    }



}
