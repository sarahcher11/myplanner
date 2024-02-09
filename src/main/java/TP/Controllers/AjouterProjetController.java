package TP.Controllers;

import TP.Noyau.Project;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;

public class AjouterProjetController {
    @FXML
    private Button Add;

    @FXML
    private TextField description;

    @FXML
    private TextField nom;


    @FXML
    public void ajouterProjet()
    {
        Add.setOnMouseClicked(MouseEvent->{
            String nomProjet=nom.getText();
            String descriptionProjet= description.getText();
            Project projet=new Project(nomProjet,descriptionProjet);
            LoginController.getUser().getProjects().add(projet);
            Stage stage=(Stage) Add.getScene().getWindow();
            stage.close();
        });
    }
}
