package TP;

import TP.Creneau;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

/**
 * Cette classe représente l'outil ou le systeme du logiciel elle permet de gérer les utilisateur de cette app
 */
public class MyDesktopPlanner implements Serializable {

    /**
     * Le nom de cette application qui doit être fixe
     */
    private final String nomApplication;
    /**
     * L'ensemble des utilisateur de l'application
     * Deux ustilisaters sont considérés similaires s'ils ont le même pseudo
     */
    private static HashMap<String, User> users;


    /**
     * Cette méthode est un constructeur qui permet d'instancier
     * @param nomApplication le nom de l'application
     * @param users l'ensemble des utilisateur de l'app
     */
    public MyDesktopPlanner(String nomApplication, HashMap<String, User> users) {
        this.nomApplication = nomApplication;
        this.users = users;
    }


    public static User trouverUtilisateur(String pseudo)
    { if(users.containsKey(pseudo))
    {
        return users.get(pseudo);

    }
    else
    {
        return null;
    }
    }


    /**
     * Cette méthode permet de creer un compte avec un nom d'utilisateur si ce dernier n'exite pas déjà dans le systeme
     * @param username  le pseudo avec lequel on veut creer le compte
     * @return cette méthode retourne vraie si la l'utilisateur existe déjà dans le systeme
     */

    public boolean creerCompte(String username)
    {

        if(!users.containsKey(username))
        {
            User user=new User(username);
            users.put(username,user);
        }
        return false;

    }

    public void planifier(User user, Tache tache)
    {
        ArrayList<Creneau> creneaux=user.getPlanning().recupCreneauxLibres();


    }




    public static void setUsers(HashMap<String, User> users) {
        MyDesktopPlanner.users = users;
    }



    public static HashMap<String, User> getUsers() {
        return users;
    }

    public String getNomApplication() {
        return nomApplication;
    }
}