package TP.Controllers;

import TP.Noyau.User;
import TP.Noyau.MyDesktopPlanner;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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

import java.io.IOException;

public class LoginController {

   @FXML
    private Button signup;
   @FXML
    private TextField info;
   @FXML
   private Button loginbutton;

    private static MyDesktopPlanner desktopPlanner=new MyDesktopPlanner();

    private static User user;

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

    public void seConnecter() {
        loginbutton.setOnMouseClicked(mouseEvent -> {
            if (info.getText().isEmpty()) {
                showAlert("Le pseudo est obligatoire pour s'authentifier");
            } else {
                String username = info.getText();
                System.out.println(username);
                if (username != null) {
                    try {
                        // Deserialize MyDesktopPlanner object from the file
                        user = desktopPlanner.trouverUtilisateur(username);
                        if (user != null) {
                            Parent root = FXMLLoader.load(this.getClass().getResource("../SideBar.fxml"));
                            Scene scene = new Scene(root);
                            Stage stage = new Stage();
                            stage.setScene(scene);
                            stage.show();
                            stage.setOnCloseRequest(event -> {
                                // Handle the close request here
                                desktopPlanner.serializeDesktopPlanner();
                            });
                            Stage ancienneStage = (Stage) loginbutton.getScene().getWindow();
                            ancienneStage.close();
                        } else {
                            showAlert("Votre compte n'existe pas, Veuillez vous inscrire");
                        }

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }

    public static MyDesktopPlanner getDesktopPlanner() {
        return desktopPlanner;
    }

    public static User getUser() {
        return user;
    }

    public static void setDesktopPlanner(MyDesktopPlanner desktopPlanner) {
        LoginController.desktopPlanner = desktopPlanner;
    }

    public static void setUser(User user) {
        LoginController.user = user;
    }
}
