package TP.Controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ProfilController implements Initializable {


    private LocalDate dateDeCreation;

    @FXML
    private Label pseudoUser;
    @FXML
    private Label dateDebut;
    @FXML
    private Label dateFin;
    @FXML
    private Label nbplanning;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        pseudoUser.setText(LoginController.getUser().getPseudo());
        dateDebut.setText(LoginController.getUser().getPlanningCourant().getPeriode().getDateDebut().toString());
        dateFin.setText(LoginController.getUser().getPlanningCourant().getPeriode().getDateFin().toString());
        nbplanning.setText(String.valueOf(LoginController.getUser().getLesPlannings().size()));


    }
}
