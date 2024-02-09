package TP.Controllers;

import TP.Noyau.Project;
import TP.Noyau.Tache;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;

public class ProjectsController implements Initializable {



    @FXML
    private Button addButton;

    @FXML
    private VBox myProjects;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Set<Project> projects = LoginController.getUser().getProjects();

        for (Project project : projects) {
            Label label = new Label();
            label.setText(project.getNomProjet() + ": " + project.getDescription());
            myProjects.getChildren().add(label);
        }
    }

    @FXML
    public void addNew()
    {
        addButton.setOnMouseClicked(MouseEvent->{

            Parent root= null;
            try {
                root = FXMLLoader.load(this.getClass().getResource("../AjouterProjet.fxml"));
                Scene scene=new Scene(root);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }



}
