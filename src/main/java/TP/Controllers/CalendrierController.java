package TP.Controllers;
import TP.Noyau.Categorie;
import TP.Noyau.*;
import com.jfoenix.controls.JFXCheckBox;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class CalendrierController implements Initializable {


    @FXML
    private JFXCheckBox blockedCheck;

    @FXML
    private ChoiceBox<String> categoryChoice;

    @FXML
    private ChoiceBox<String> creneauChoice;

    @FXML
    private Button exitButton;

   @FXML
   private TextField hourPicker;
    @FXML
    private TextField minutePicker;

    @FXML
    private TextField nameTask;

    @FXML
    private Pane paneTache;

    @FXML
    private ChoiceBox<Priorite> priorityChoice;

     private LocalDate dateFocus;
     private LocalDate today;
    private Text text;
    @FXML
    private Label creneauLibre;


    private  SortedSet<Creneau> creneaux;

    @FXML
    private Text year;

    @FXML
    private Text month;


    @FXML
    private Button addButton;

    @FXML
    private FlowPane calendar;
    @FXML
    private VBox lesCategories;


    private static Journee lastClickedJournee;

    private static Creneau lastClickedCreneau;

    @FXML
    private Button ajouterTask;

    private boolean isBlocked;

    @FXML
    private DatePicker deadlinePicker;


    @FXML
    private TextField deadhourPicker;
    @FXML
    private TextField deadminutePicker;

    @FXML
    private Label tasks;


    @FXML
    private Label groupeTache;
    @FXML
    private Label uneTache;

    @FXML
    private Button newTache;

    @FXML
    private Button planifierEnsemble;
    @FXML
    private Button modifiermin;
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        dateFocus = LoginController.getUser().getPlanningCourant().getPeriode().getDateDebut();
        today = LoginController.getUser().getPlanningCourant().getPeriode().getDateDebut();
        drawCalendar();
        int i=LoginController.getUser().getPlanningCourant().recupCreneauxLibres().size();
        creneauLibre.setText(String.valueOf(i));
        displayCategories(lesCategories);
        paneTache.toFront();
        paneTache.setVisible(false);
        // Create an ObservableList with the desired options
        ObservableList<Priorite> priorities = FXCollections.observableArrayList(Priorite.High, Priorite.Low, Priorite.Medium);
        // Set the items for the priorityChoice ChoiceBox
        priorityChoice.setItems(priorities);
        // Retrieve the user's categories
        ObservableList<String> categoryOptions = FXCollections.observableArrayList();
        // Define the updateCategoryOptions method
         Runnable updateCategoryOptions = () -> {
            // Retrieve the user's categories
            HashMap<String, Categorie> categories = LoginController.getUser().getCategories();

            // Clear the categoryOptions list
            categoryOptions.clear();

            // Extract the category names and add them to the categoryOptions list
            for (Categorie categorie : categories.values()) {
                categoryOptions.add(categorie.getNomCategorie());
            }

            // Set the items for the categoryChoice ChoiceBox
            categoryChoice.setItems(categoryOptions);
            tasks.setText(String.valueOf(LoginController.getUser().getPlanningCourant().getTaches().size()));
        };

// Call the updateCategoryOptions() method initially to populate the categoryOptions list
        updateCategoryOptions.run();


        // Add an event handler to the blockedCheck checkbox
        blockedCheck.setOnAction(event -> {
            // Update the isBlocked variable based on the checkbox's checked state
            isBlocked = blockedCheck.isSelected();
        });

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

            int nb=Integer.parseInt(texteSaisi);
            LoginController.getUser().setNbTacheMinimal(nb);
            System.out.println("Texte saisi : " + texteSaisi);

            // Fermer la fenêtre
            stage.close();
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


    @FXML
    void backOneMonth(ActionEvent event) {
        dateFocus = dateFocus.minusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }
    @FXML
    void forwardOneMonth(ActionEvent event) {
        dateFocus = dateFocus.plusMonths(1);
        calendar.getChildren().clear();
        drawCalendar();
    }


    public void ajouterTaskButton()
    {
        String nomTache=nameTask.getText();
        LocalTime duree=LocalTime.of(Integer.parseInt(hourPicker.getText()),Integer.parseInt(minutePicker.getText()));
        Priorite priorite=priorityChoice.getValue();
        String selectCategory = categoryChoice.getValue();

        Categorie categorie=LoginController.getUser().getCategories().get(selectCategory);
        if (categorie==null)
        {
            categorie=new Categorie("");
        }
        // Access the isBlocked variable to get its value
        boolean isChecked = isBlocked;
        LocalDate deadline=deadlinePicker.getValue();
        LocalTime heureDeadline=LocalTime.of(Integer.parseInt(deadhourPicker.getText()),Integer.parseInt(deadminutePicker.getText()));
        TacheSimple tacheSimple=new TacheSimple(nomTache,duree,priorite,deadline,heureDeadline,categorie);
        ajouterTask.setOnMouseClicked(MouseEvent->{
            if(lastClickedCreneau.deadlineRespectee(tacheSimple) && lastClickedCreneau.compatibiliteDuree(tacheSimple)!=0)
            {
                lastClickedJournee=LoginController.getUser().getPlanningCourant().getJournees().get(lastClickedCreneau.getJournee().getDateDuJour());
                System.out.println("ici"+lastClickedJournee);
                tacheSimple.setEtatDeRealisation(EtatDeRealisation.notRealized);
                tacheSimple.setCreneau(lastClickedCreneau);
                System.out.println("la"+lastClickedJournee);
                LoginController.getUser().getPlanningCourant().planifier(tacheSimple,lastClickedJournee.getDateDuJour(),lastClickedCreneau,isChecked);
                paneTache.setVisible(false);
            }
            else
            {
                showAlert("This time slot is incompatible with your task. Please choose another one");
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

    private void drawCalendar(){
        year.setText(String.valueOf(dateFocus.getYear()));
        month.setText(String.valueOf(dateFocus.getMonth()));

        double calendarWidth = calendar.getPrefWidth();
        double calendarHeight = calendar.getPrefHeight();
        double strokeWidth = 0.25;
        double spacingH = calendar.getHgap();
        double spacingV = calendar.getVgap();
        //List of activities for a given month
        Map<Integer, List<Creneau>> calendarActivityMap = recupererLesCreneaux(dateFocus);

        int monthMaxDate = dateFocus.getMonth().maxLength();
        //Check for leap year
        if(dateFocus.getYear() % 4 != 0 && monthMaxDate == 29){
            monthMaxDate = 28;
        }

        int dateOffset = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), 1)
                .getDayOfWeek().getValue();

        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < 7; j++) {
                StackPane stackPane = new StackPane();
                Rectangle rectangle = new Rectangle();
                rectangle.setFill(Color.TRANSPARENT);
                rectangle.setStroke(Color.web("#0C5776"));
                rectangle.setStrokeWidth(strokeWidth);
                double rectangleWidth =(calendarWidth/7) - strokeWidth - spacingH;
                rectangle.setWidth(rectangleWidth);
                double rectangleHeight = (calendarHeight/6) - strokeWidth - spacingV;
                rectangle.setHeight(rectangleHeight);
                stackPane.getChildren().add(rectangle);

                int calculatedDate = (j+1)+(7*i);
                LocalDate date;
                if (calculatedDate > dateOffset && calculatedDate <= (dateOffset + monthMaxDate)) {
                    date = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), calculatedDate - dateOffset);
                    // Rest of the code...
                } else {
                    // Adjust the date to the maximum day of the month
                    int adjustedDate = Math.min(calculatedDate, monthMaxDate);
                    date = LocalDate.of(dateFocus.getYear(), dateFocus.getMonthValue(), adjustedDate);
                }
                LocalDate finalDate = date;
                rectangle.setOnMouseClicked(MouseEvent->{



                    if(LoginController.getUser().getPlanningCourant().getJournees().containsKey(finalDate))
                    {
                        System.out.println("finalDate"+ finalDate);
                        lastClickedJournee=LoginController.getUser().getPlanningCourant().getJournees().get(finalDate);
                        creneaux = lastClickedJournee.getCreneaux();
                        afficherCreneaux(creneaux);
                        //On Text clicked
                        System.out.println("saraaaaaaaah"+text.getText());

                    }
                    // Dans la condition "else"
                    else {
                        Stage addDayStage = new Stage();
                        VBox content = new VBox();
                        // Define the content of the window (layout, controls, etc.)
                        Button okButton = new Button("OK");
                        okButton.setOnAction(event -> {
                            // Retrieve the information entered by the user

                            // Add a new day to the schedule
                            Journee newJournee = new Journee(finalDate, new TreeSet<>());
                            LoginController.getUser().getPlanningCourant().getJournees().put(finalDate, newJournee);

                            // Close the window
                            addDayStage.close();
                        });

                        // Add the button to the content
                        content.getChildren().add(okButton);

                        // Set the content of the stage
                        addDayStage.setScene(new Scene(content));

                        // Show the stage
                        addDayStage.show();
                    }
                });
                if(calculatedDate > dateOffset){
                    int currentDate = calculatedDate - dateOffset;
                    if(currentDate <= monthMaxDate){
                        Text datetext = new Text(String.valueOf(currentDate));
                        double textTranslationY = - (rectangleHeight / 2) * 0.75;
                        datetext.setTranslateY(textTranslationY);
                        stackPane.getChildren().add(datetext);
                        List<Creneau> calendarActivities = calendarActivityMap.get(currentDate);
                        if(calendarActivities != null){
                            createCalendarActivity(calendarActivities, rectangleHeight, rectangleWidth, stackPane);
                        }
                    }
                    if(today.getYear() == dateFocus.getYear() && today.getMonth() == dateFocus.getMonth() && today.getDayOfMonth() == currentDate){
                        rectangle.setStroke(Color.BLUE);
                    }
                }
                calendar.getChildren().add(stackPane);
            }
        }
    }
    private void createCalendarActivity(List<Creneau> calendarActivities, double rectangleHeight, double rectangleWidth, StackPane stackPane) {
        VBox calendarActivityBox = new VBox();
        for (int k = 0; k < calendarActivities.size(); k++) {
            if(k >= 2) {
                Text moreActivities = new Text("plus");
                calendarActivityBox.getChildren().add(moreActivities);
                moreActivities.setOnMouseClicked(mouseEvent -> {
                    //On ... click print all activities for given date
                     lastClickedJournee=calendarActivities.get(0).getJournee();
                    System.out.println(lastClickedJournee);
                     creneaux = calendarActivities.get(0).getJournee().getCreneaux();
                     afficherCreneaux(creneaux);

                });
                break;
            }
            text = new Text(calendarActivities.get(k).getHeureDebut().toString() + ", " + calendarActivities.get(k).getHeureFin().toString());
            calendarActivityBox.getChildren().add(text);
            text.setOnMouseClicked(mouseEvent -> {
                System.out.println(lastClickedJournee);
                lastClickedJournee=calendarActivities.get(0).getJournee();
                creneaux = calendarActivities.get(0).getJournee().getCreneaux();
                afficherCreneaux(creneaux);
                //On Text clicked
                System.out.println("saraaaaaaaah"+text.getText());
            });
        }

        calendarActivityBox.setTranslateY((rectangleHeight / 2) * 0.20);
        calendarActivityBox.setMaxWidth(rectangleWidth * 0.8);
        calendarActivityBox.setMaxHeight(rectangleHeight * 0.65);
        calendarActivityBox.setStyle("-fx-background-color:#F8DAD0");
        stackPane.getChildren().add(calendarActivityBox);
    }


    /**
     * Cette méthode permet d'afficher des creneaux dans une stage et chaque creneau dans son rectangle
     * @param creneaux l'ensemble des creneaux à afficher ordonnés selon l'heure debut
     */

    public void afficherCreneaux(SortedSet<Creneau> creneaux) {
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle("Time slot");

        VBox creneauList = new VBox(18);
        ScrollPane scrollPane = new ScrollPane(creneauList);
        scrollPane.setFitToWidth(true);

        Button ajouterButton = new Button("Add");
        ajouterButton.setOnAction(e -> {
            // Création de la fenêtre de sélection de l'heure
            Stage heureStage = new Stage();
            heureStage.initModality(Modality.APPLICATION_MODAL);
            heureStage.setTitle("Choose a time");

            // Création des spinners pour l'heure de début
            Spinner<Integer> debutHourSpinner = new Spinner<>(0, 23, 0);
            debutHourSpinner.setEditable(true);
            debutHourSpinner.setPrefWidth(80);

            Spinner<Integer> debutMinuteSpinner = new Spinner<>(0, 59, 0);
            debutMinuteSpinner.setEditable(true);
            debutMinuteSpinner.setPrefWidth(80);

            // Création des spinners pour l'heure de fin
            Spinner<Integer> finHourSpinner = new Spinner<>(0, 23, 0);
            finHourSpinner.setEditable(true);
            finHourSpinner.setPrefWidth(80);

            Spinner<Integer> finMinuteSpinner = new Spinner<>(0, 59, 0);
            finMinuteSpinner.setEditable(true);
            finMinuteSpinner.setPrefWidth(80);

            // Création du bouton "OK"
            Button okButton = new Button("OK");
            okButton.setOnAction(event -> {
                int debutHour = debutHourSpinner.getValue();
                int debutMinute = debutMinuteSpinner.getValue();
                int finHour = finHourSpinner.getValue();
                int finMinute = finMinuteSpinner.getValue();

                LocalTime heureDebut = LocalTime.of(debutHour, debutMinute);
                LocalTime heureFin = LocalTime.of(finHour, finMinute);

                try {
                    Creneau creneau=new Creneau(heureDebut,heureFin);
                    creneau.setJournee(lastClickedJournee);
                    lastClickedJournee.getCreneaux().add(creneau);
                } catch (HeureDebutException ex) {
                    throw new RuntimeException(ex);
                }

                heureStage.close(); // Fermer la fenêtre de sélection de l'heure
            });

            // Création du bouton "Annuler"
            Button cancelButton = new Button("Cancel");
            cancelButton.setOnAction(event -> heureStage.close());

            // Création de la mise en page
            HBox debutHBox = new HBox(10, new Label("Start:"), debutHourSpinner, new Label("Minute:"), debutMinuteSpinner);
            HBox finHBox = new HBox(10, new Label("End   :"), finHourSpinner, new Label("Minute:"), finMinuteSpinner);
            HBox buttonHBox = new HBox(10, okButton, cancelButton);
            VBox vbox = new VBox(20, debutHBox, finHBox, buttonHBox);
            vbox.setPadding(new Insets(20));

            Scene heureScene = new Scene(vbox);
            heureStage.setScene(heureScene);
            heureStage.showAndWait();
        });
        BorderPane root = new BorderPane();
        root.setCenter(scrollPane);
        root.setBottom(ajouterButton);
        BorderPane.setMargin(ajouterButton, new Insets(10));

        Scene scene = new Scene(root, 300, 400);  // Définissez la taille souhaitée de la fenêtre
        stage.setScene(scene);
        stage.show();

        for (Creneau creneau : creneaux) {
            Label creneauLabel = new Label(creneau.toString());
            creneauLabel.setStyle("-fx-font-family: Calibri; -fx-font-size: 14; -fx-alignment: center-left; -fx-background-radius: 15; -fx-border-radius: 15;");
            creneauLabel.setPadding(new Insets(5));

            creneauList.getChildren().add(creneauLabel);
            if(creneau.getTache()!=null)
            {
                String couleur=toRGBCodeAWT(creneau.getTache().getCategorie().getCouleur());
                creneauLabel.setStyle("-fx-font-family: Calibri; -fx-font-size: 14; -fx-alignment: center-left; -fx-background-radius: 15; -fx-border-radius: 15;"+"-fx-background-color:"+couleur+";");
            }
            creneauLabel.setOnMouseClicked(event -> {
                // Récupérer la valeur du créneau sélectionné
                String creneauValue = creneauLabel.getText();
                System.out.println(creneauValue);
                Creneau creneau1=lastClickedJournee.rechercherCreneau(creneauValue);
                 if(creneau1!=null)
                 {
                     paneTache.setVisible(true);
                     lastClickedCreneau=creneau1;


                 }

            });



        }
    }


    /**
     * Cette méthode permet d'afficher les  creneaux du mois courant
     * @param creneaux l'ensemble de tous les creneaux de l'utilisateur
     * @return  retourner une map dont la clé c'est le jour du mois concerné et la valeur c'est la liste
     * des creneaux de la journées en question
     */

    private Map<Integer, List<Creneau>> afficherCreneauMois(List<Creneau> creneaux) {
        Map<Integer, List<Creneau>> calendarActivityMap = new HashMap<>();

        for (Creneau creneau : creneaux) {
            LocalDate creneauDate = creneau.getJournee().getDateDuJour();
            int month = creneauDate.getMonthValue();
            int year=creneauDate.getYear();

            // Check if the creneau belongs to the current month within the period
            if (month == dateFocus.getMonthValue() && year==dateFocus.getYear()) {
                int dayOfMonth = creneauDate.getDayOfMonth();
                if (!calendarActivityMap.containsKey(dayOfMonth)) {
                    calendarActivityMap.put(dayOfMonth, new ArrayList<>());
                }
                calendarActivityMap.get(dayOfMonth).add(creneau);
            }
        }

        return calendarActivityMap;
    }
    private Map<Integer, List<Creneau>> recupererLesCreneaux(LocalDate dateFocus) {
        List<Creneau> calendarActivities = new ArrayList<>();

          for(int i=0;i<LoginController.getUser().getPlanningCourant().getPeriode().getNumberOfDays();i++)
          {
              LocalDate date=LoginController.getUser().getPlanningCourant().getPeriode().getDateDebut().plusDays(i);
              Journee journee=LoginController.getUser().getPlanningCourant().getJournees().get(date);
              Set<Creneau> creneaux=journee.getCreneaux();
              for(Creneau creneau : creneaux)
              {   LocalTime heureDebut=creneau.getHeureDebut();
                  LocalTime heureFin=creneau.getHeureFin();
                  try {
                      Creneau nvCreneau=new Creneau(heureDebut,heureFin);
                      nvCreneau.setJournee(journee);
                      System.out.println("new "+ nvCreneau.getJournee().getDateDuJour());
                      calendarActivities.add(nvCreneau);
                  } catch (HeureDebutException e) {
                      throw new RuntimeException(e);
                  }
              }
          }
          return  afficherCreneauMois(calendarActivities);
    }
    public void showAddCategoryDialog(VBox categoryList) {
        Dialog<Categorie> dialog = new Dialog<>();
        dialog.setTitle("Add new category");
        TextField categoryNameField = new TextField();
        ColorPicker colorPicker = new ColorPicker(Color.WHITE);
        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setStyle("-fx-background-color: white");
        gridPane.setPadding(new Insets(20));
        gridPane.add(new Label("Name of category:"), 0, 0);
        gridPane.add(categoryNameField, 1, 0);
        gridPane.add(new Label("Color:"), 0, 1);
        gridPane.add(colorPicker, 1, 1);
        dialog.getDialogPane().setContent(gridPane);
        ButtonType addButton = new ButtonType("Ajouter", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);
        dialog.setResultConverter(buttonType -> {
            if (buttonType == addButton) {
                String categoryName = categoryNameField.getText();
                java.awt.Color awtColor = convertToAWTColor(colorPicker.getValue());
                Categorie categorie=new Categorie(categoryName, awtColor);
                return categorie;
            }
            return null;
        });
        dialog.showAndWait().ifPresent(category -> {
            if(!LoginController.getUser().getCategories().containsValue(category))
            {
                LoginController.getUser().getCategories().put(category.getNomCategorie(),category);
            }
            displayCategories(categoryList);
        });
    }
    public void buttonAjouter()
    {
        addButton.setOnAction(e -> showAddCategoryDialog(lesCategories));
    }

    public void displayCategories(VBox categoryList) {
        categoryList.getChildren().clear();

        for (Categorie category : LoginController.getUser().getCategories().values()) {
            Label nameLabel = new Label(category.getNomCategorie());
            nameLabel.setStyle("-fx-font-family: Calibri; -fx-font-size: 22; -fx-alignment: center-left; -fx-background-radius: 15; -fx-border-radius: 15;");
            nameLabel.setPrefHeight(35);
            Region colorBox = new Region();
            colorBox.setPrefSize(35, 35);
            colorBox.setStyle("-fx-background-color: " + toRGBCodeAWT(category.getCouleur()));
            HBox categoryBox = new HBox(20);
            categoryBox.getChildren().addAll(colorBox, nameLabel);

            categoryList.getChildren().add(categoryBox);
            nameLabel.setOnMouseClicked(MouseEvent->{
                AnchorPane anchorPane=new AnchorPane();
                Scene scene=new Scene(anchorPane);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();

            });
        }
    }


    public void fermerTache(ActionEvent event)
    {
        paneTache.setVisible(false);
    }

    private String toRGBCodeAWT(java.awt.Color color) {
        return String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue());
    }
    private java.awt.Color convertToAWTColor(javafx.scene.paint.Color fxColor) {
        int red = (int) (fxColor.getRed() * 255);
        int green = (int) (fxColor.getGreen() * 255);
        int blue = (int) (fxColor.getBlue() * 255);
        return new java.awt.Color(red, green, blue);
    }

    public static Journee getLastClickedJournee() {
        return lastClickedJournee;
    }

    public static void setLastClickedJournee(Journee lastClickedJournee) {
        CalendrierController.lastClickedJournee = lastClickedJournee;
    }

    public static Creneau getLastClickedCreneau() {
        return lastClickedCreneau;
    }


    public void planifierAutomatUneTache()
    {
        newTache.setOnMouseClicked(MouseEvent->{
            try {
                Parent root= FXMLLoader.load(this.getClass().getResource("../TacheAutomatique.fxml"));
                Scene scene=new Scene(root);
                Stage stage=new Stage();
                stage.setScene(scene);
                stage.show();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });
    }
    public void planifierAutomatDesTaches()
    {
        planifierEnsemble.setOnMouseClicked(MouseEvent->{
            try {
                Parent root= FXMLLoader.load(this.getClass().getResource("../TachesAutomatiques.fxml"));
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