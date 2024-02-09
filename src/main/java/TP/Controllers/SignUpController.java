package TP.Controllers;

import TP.Noyau.*;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;

public class SignUpController {

  @FXML
    private Button login;
  @FXML
    private Button signupbutton;
  @FXML
    private TextField info;

  @FXML
  private  MyDesktopPlanner desktopPlanner=new MyDesktopPlanner();


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

  @FXML
  public void seConnecter()
  {
    login.setOnMouseClicked(MouseEvent->{
      Parent root = null;
      try {
        root = FXMLLoader.load(getClass().getResource("../loginPageF.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) login.getScene().getWindow();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show(); }
      catch (IOException e) {
        e.printStackTrace();
      }
    });
  }

  @FXML
  public void CreateNewAccount() {
    signupbutton.setOnMouseClicked(MouseEvent -> {
      if (info.getText().isEmpty()) {
        showAlert("Vous devez entrer un pseudo");
      } else {
        String username = info.getText();

        try {

            desktopPlanner=new MyDesktopPlanner();

          if (desktopPlanner.trouverUtilisateur(username) == null) {
            LoginController.setDesktopPlanner(desktopPlanner);
            desktopPlanner.creerCompte(username);
            LoginController.setUser(desktopPlanner.trouverUtilisateur(username));
            Parent root = FXMLLoader.load(getClass().getResource("../IntroducePeriode.fxml"));
            Scene scene=new Scene(root);
            Stage stage=new Stage();
            stage.setScene(scene);
            stage.show();
            desktopPlanner.serializeDesktopPlanner();
            Stage ancienneStage = (Stage) signupbutton.getScene().getWindow();
            ancienneStage.close();
          } else {
            showAlert("Ce pseudo existe deja");
          }
        } catch (IOException e) {
          throw new RuntimeException(e);
        }
      }
    });
  }


 }








