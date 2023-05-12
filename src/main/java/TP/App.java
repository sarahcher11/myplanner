package TP;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static javafx.application.Application.launch;

public class App extends Application{
    private static Stage stg;

    public void changeScene(String fxml) throws Exception{
        Parent pane=FXMLLoader.load(getClass().getResource(fxml));
        stg.getScene().setRoot(pane);
    }

    public static void main(String[] args) {
            launch();
        try{

            Creneau creneau=new Creneau(LocalTime.of(12,00),LocalTime.of(14,00),30);
            Categorie categorie1=new Categorie("School");
            SortedSet<Creneau> creneaux=new TreeSet<Creneau>();
            creneaux.add(creneau);
            Journee journee=new Journee(LocalDate.of(2023,06,27),creneaux) ;
            creneau.setJournee(journee);
            Tache tache1=new TacheSimple("tache1",LocalTime.of(1,00), Priorite.High,LocalDate.of(2023,07,10),LocalTime.of(14,00),categorie1);
            creneau.decomposer(tache1);
            System.out.println("Le creneau de la tache "+ creneau.toString());
            ArrayList<Creneau> creneauLibres=new ArrayList<Creneau>();
            creneauLibres=journee.recupCreneauxLibres();
            System.out.println(creneauLibres.size());
            for(Creneau creneau19: creneauLibres)
            {

                System.out.println(creneau19.toString());
            }

            System.out.println(creneau.getTache().getNom());




            TacheDecomposable tacheDecomposable=new TacheDecomposable("tache2",LocalTime.of(1,00), Priorite.High,LocalDate.of(2023,07,10),LocalTime.of(14,00),categorie1);
           /* TP.Creneau creneau2=new TP.Creneau(LocalTime.of(17,00),LocalTime.of(18,00),30);
            TP.Creneau creneau3=new TP.Creneau(LocalTime.of(10,00),LocalTime.of(12,00),30);
            SortedSet<TP.Creneau> creneaux=new TreeSet<TP.Creneau>();
            creneaux.add(creneau);
            creneaux.add(creneau2);
            creneaux.add(creneau3);
            System.out.println(creneaux.size());
            for (TP.Creneau creneau10: creneaux) {

                System.out.println(creneau10.toString());
            }

            TP.Journee journee=new TP.Journee(LocalDate.of(2023,06,27),creneaux) ;
            journee.ajouterCreneau(creneau);
            journee.ajouterCreneau(creneau2);
            journee.ajouterCreneau(creneau3);

            TP.Creneau creneau1=new TP.Creneau(LocalTime.of(12,00),LocalTime.of(14,00),30);

             TP.Categorie categorie1=new TP.Categorie("School");
             TP.Categorie categorie2=new TP.Categorie("work");
             TP.Categorie categorie3=new TP.Categorie("Sport");
             TP.Categorie categorie4=new TP.Categorie("wlach");
            TP.Tache tache1=new TP.TacheSimple("tache1",LocalTime.of(1,00),TP.Priorite.High,LocalDate.of(2023,07,10),LocalTime.of(14,00),categorie1);
            TP.Tache tache2=new TP.TacheSimple("tache2",LocalTime.of(1,00),TP.Priorite.Low,LocalDate.of(2023,07,11),LocalTime.of(14,00),categorie1);
            TP.Tache tache3=new TP.TacheSimple("tache3",LocalTime.of(1,00),TP.Priorite.High,LocalDate.of(2023,07,13),LocalTime.of(14,00),categorie2);
            TP.Tache tache4=new TP.TacheSimple("tache4",LocalTime.of(1,00),TP.Priorite.High,LocalDate.of(2023,07,14),LocalTime.of(14,00),categorie3);


            TP.Periode periode=new TP.Periode(LocalDate.of(2023,05,07),LocalDate.of(2023,12,12));

            TP.Journee journee1=new TP.Journee(LocalDate.of(2023,05,10),creneaux);
            TP.Journee journee2=new TP.Journee(LocalDate.of(2023,05,11),creneaux);
            TP.Journee journee3=new TP.Journee(LocalDate.of(2023,05,12),creneaux);
            TP.Journee journee4=new TP.Journee(LocalDate.of(2023,05,13),creneaux);
            TP.Journee journee5=new TP.Journee(LocalDate.of(2023,05,14),creneaux);
            TP.Journee journee7=new TP.Journee(LocalDate.of(2023,05,15),creneaux);




            if(journee.getCreneaux().contains(creneau3))
            {
                System.out.println("Oui je l'ai trouvé");
            }
            else
            {
                System.out.println("NON");
            }*/

        }
        catch (HeureDebutException e){
            e.printStackTrace();

        }







      /* LocalDate date = LocalDate.now();
        int month = date.getMonthValue();
        int year = date.getYear();

        System.out.println("       " + date.getMonth() + "  " + year);
        System.out.println("Lun Mar Mer Jeu Ven Sam Dim");

        LocalDate firstDayOfMonth = LocalDate.of(year, month, 1);
        int dayOfWeek = firstDayOfMonth.getDayOfWeek().getValue();

        for (int i = 1; i < dayOfWeek; i++) {
            System.out.print("    ");
        }

        while (firstDayOfMonth.getMonthValue() == month) {
            System.out.printf(" %2d ", firstDayOfMonth.getDayOfMonth());

            if (firstDayOfMonth.getDayOfWeek().getValue() == 7) {
                System.out.println();
            }
            firstDayOfMonth = firstDayOfMonth.plusDays(1);
        }*/

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root =FXMLLoader.load(this.getClass().getResource("loginPageF.fxml"));
        Scene scene=new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setResizable(false);
        User user1=new User("SSSSSSSSSSS");
        User user2=new User("gggggg");
        User user3=new User("Kenza");
        User user4=new User("hhhhhh");
        HashMap<String,User> users=new HashMap<String,User>();
        users.put("Kenza",user3);
        users.put("gggggg",user2);
        users.put("SSSSSSSSSSS",user1);
        users.put("hhhhhh",user4);
        MyDesktopPlanner myDesktopPlanner=new MyDesktopPlanner("mon app",users);
        MyDesktopPlanner.setUsers(users);




    }
}