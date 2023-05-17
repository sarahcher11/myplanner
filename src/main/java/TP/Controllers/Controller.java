package TP.Controllers;

import TP.Noyau.MyDesktopPlanner;
import TP.Noyau.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class Controller {
    @FXML
    private Button login;


    @FXML
    private Button cancel;

    @FXML
    private Label errorLabel;

    @FXML
    private TextField info;

  @FXML
    private Button signUp;




  public void fermerConsole()
  {
      cancel.setOnMouseClicked(mouseEvent -> {
          Stage stage = (Stage) cancel.getScene().getWindow();
          stage.close();
      });
  }



  public void seConnecter()
  {
      login.setOnMouseClicked(mouseEvent->{
          if (info.getText().isEmpty())
          {
              Alert alert=new Alert(Alert.AlertType.ERROR,"Le pseudo est obligatoire pour s'authentifier");
              alert.showAndWait();
          }
          else
          {
              String username=info.getText();
              System.out.println(username);
              if(username!=null)
              {
                  User user= MyDesktopPlanner.trouverUtilisateur(username);
                  System.out.println(MyDesktopPlanner.getUsers().size());
                  System.out.println(user);


                  if(user!=null)
                  {
                      try {
                          Parent root= FXMLLoader.load(this.getClass().getResource("../IntroduireTache.fxml"));
                          Scene scene=new Scene(root);
                          Stage stage=new Stage();
                          stage.setScene(scene);
                          stage.show();
                          Stage ancienneStage=(Stage) login.getScene().getWindow();
                          ancienneStage.close();

                      } catch (IOException e) {
                          throw new RuntimeException(e);
                      }
                  }
                  else {

                          Alert alert=new Alert(Alert.AlertType.ERROR,"Votre compte n'existe pas,Veuillez vous inscrire");
                          alert.showAndWait();

                  }


              }



          }


      });

  }
}
