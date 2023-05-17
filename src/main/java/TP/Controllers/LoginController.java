package TP.Controllers;

import TP.App;
import TP.User;
import TP.MyDesktopPlanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;

public class LoginController {

   @FXML
    private Button signup;
   @FXML
    private TextField info;
   @FXML
   private Button loginbutton;



   @FXML
    public void CreateNewAccount()
   {

       signup.setOnMouseClicked(MouseEvent->{
           Parent root = null;
           try {
               root = FXMLLoader.load(getClass().getResource("../SignUp.fxml"));

           Scene scene = new Scene(root);
           Stage stage = (Stage) signup.getScene().getWindow();
           stage.setResizable(false);
           stage.setScene(scene);
           stage.show(); }
           catch (IOException e) {
               e.printStackTrace();
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

    public void seConnecter()
    {
        loginbutton.setOnMouseClicked(mouseEvent->{
            if (info.getText().isEmpty())
            {
                showAlert("Le pseudo est obligatoire pour s'authentifier");
            }
            else
            {
                String username=info.getText();
                System.out.println(username);
                if(username!=null)
                {
                    User user= MyDesktopPlanner.trouverUtilisateur(username);
                    System.out.println(MyDesktopPlanner.getUsers().size());
                    if(user!=null)
                    {
                        try {
                            Parent root= FXMLLoader.load(this.getClass().getResource("../IntroduireTache.fxml"));
                            Scene scene=new Scene(root);
                            Stage stage=new Stage();
                            stage.setScene(scene);
                            stage.show();
                            Stage ancienneStage=(Stage) loginbutton.getScene().getWindow();
                            ancienneStage.close();

                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    else {
                        showAlert("Votre compte n'existe pas,Veuillez vous inscrire");
                    }
                }
            }
        });
    }
}
