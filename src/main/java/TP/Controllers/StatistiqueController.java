package TP.Controllers;

import TP.Noyau.Badge;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class StatistiqueController implements Initializable {
    @FXML
    private Label excellent;

    @FXML
    private Label good;

    @FXML
    private Label projets;

    @FXML
    private Label taches;

    @FXML
    private Label veryGood;

    @FXML
    private Label rentable;
    @FXML
    private Button categorie;
    @FXML
    private Label journalier;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        LoginController.getUser().Affecter_Badge();
        LoginController.getUser().Affecter_Badge();
        taches.setText(String.valueOf(LoginController.getUser().NbTachesRealiseesEnPlanning(LoginController.getUser().getPlanningCourant())));
        projets.setText(String.valueOf(LoginController.getUser().NbProjetsRealiseesEnPlanning(LoginController.getUser().getPlanningCourant())));
        veryGood.setText(String.valueOf(LoginController.getUser().getBadgeNbrTypeProject(LoginController.getUser().getPlanningCourant()).get(Badge.veryGOOD)));
        good.setText(String.valueOf(LoginController.getUser().getBadgeNbrTypeProject(LoginController.getUser().getPlanningCourant()).get(Badge.GOOD)));
        excellent.setText(String.valueOf(LoginController.getUser().getBadgeNbrTypeProject(LoginController.getUser().getPlanningCourant()).get(Badge.EXCELLENT)));
        if(LoginController.getUser().Stat_DatePlusRentable()!=null)
        {
            rentable.setText(LoginController.getUser().Stat_DatePlusRentable().toString());

        }



    }
    @FXML
    private void afficherStage2() {
        // Créer une nouvelle fenêtre (stage)
        Stage stage = new Stage();

        // Créer un TextField
        TextField textField = new TextField();

        // Créer un bouton "OK"
        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            // Action à effectuer lorsque le bouton "OK" est cliqué
            String texteSaisi = textField.getText();

            int i=LoginController.getUser().DureeTachesParCategorie(texteSaisi);

            // Fermer la fenêtre
            stage.close();
            afficherResultat(i);
        });

        // Créer un conteneur pour le TextField et le bouton
        VBox vbox = new VBox(textField, okButton);
        vbox.setSpacing(10);
        vbox.setPadding(new Insets(10));

        // Créer une scène et associer le conteneur à la scène
        Scene scene = new Scene(vbox);

        // Configurer la fenêtre (stage)
        stage.setTitle("Modifier");
        stage.setScene(scene);

        // Afficher la fenêtre modale (attendre que l'utilisateur la ferme avant de continuer)
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.showAndWait();
    }
    private void afficherResultat(int i) {
        // Créer une nouvelle fenêtre (stage)
        Stage resultatStage = new Stage();

        // Créer un Label pour afficher le résultat
        Label resultatLabel = new Label("Résultat : " + i);

        // Créer un conteneur pour le Label
        VBox vbox = new VBox(resultatLabel);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        // Créer une scène et associer le conteneur à la scène
        Scene scene = new Scene(vbox);

        // Configurer la fenêtre (stage)
        resultatStage.setTitle("Résultat");
        resultatStage.setScene(scene);

        // Afficher la fenêtre
        resultatStage.show();
    }

}
