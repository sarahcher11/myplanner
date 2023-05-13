package TP.Controllers;

import TP.App;
import TP.MyDesktopPlanner;
import TP.User;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class SignUpController {

  @FXML
    private Button login;
  @FXML
    private Button signupbutton;
  @FXML
    private TextField info;


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
  public void CreateNewAccount()
  {
    signupbutton.setOnMouseClicked(MouseEvent->{
      if (info.getText().isEmpty())
      {
        showAlert("Vous devez entrer un pseudo");
      }
      else
      {
        String username=info.getText();
        if(MyDesktopPlanner.trouverUtilisateur(username)==null)
        {
            try {
              Parent root= FXMLLoader.load(this.getClass().getResource("../IntroducePeriode.fxml"));
              Scene scene=new Scene(root);
              Stage stage=new Stage();
              stage.setScene(scene);
              stage.show();
              Stage ancienneStage=(Stage) signupbutton.getScene().getWindow();
              ancienneStage.close();

            } catch (IOException e) {
              throw new RuntimeException(e);
            }
          }
          else {
            showAlert("Ce pseudo existe deja");
          }
        }
    });
  }


}
