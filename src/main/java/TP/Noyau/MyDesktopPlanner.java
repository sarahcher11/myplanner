package TP.Noyau;

import java.io.*;
import java.util.HashMap;

/**
 * Cette classe représente l'outil ou le systeme du logiciel elle permet de gérer les utilisateur de cette app
 */
public class MyDesktopPlanner implements Serializable {
    private static final long serialVersionUID = 7145059108766265683L;
    private static String serializationPath="planner.ser";

    /**
     * L'ensemble des utilisateur de l'application
     * Deux ustilisaters sont considérés similaires s'ils ont le même pseudo
     */
    private  HashMap<String, User> users =new HashMap<>();


    /**
     * Cette méthode est un constructeur qui permet d'instancier
     * @param users l'ensemble des utilisateur de l'app
     */



    public MyDesktopPlanner(HashMap<String, User> users) {

        this.users = users;
    }

    public MyDesktopPlanner()
    {

    }


    public  User trouverUtilisateur(String pseudo)
    {
        deserializeDesktopPlanner();
    if(users.containsKey(pseudo))
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

    public  boolean  creerCompte(String username)
    {

        if(!users.containsKey(username))
        {
            User user=new User(username);
            users.put(username,user);
            serializeDesktopPlanner();
        }
        return false;

    }

    public void serializeDesktopPlanner() {
        try {
            FileOutputStream fileOut = new FileOutputStream(serializationPath);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            System.out.println(users.values());
            out.writeObject(users); // Serialize the entire HashMap, including User and Planning objects
            out.close();
            fileOut.close();
            System.out.println("MyDesktopPlanner object serialized successfully.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void deserializeDesktopPlanner() {
        try {
            File serializedFile = new File(serializationPath);
            if (serializedFile.exists()) {
                FileInputStream fileIn = new FileInputStream(serializedFile);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                users = (HashMap<String, User>) in.readObject(); // Deserialize the entire HashMap
                in.close();
                fileIn.close();
                System.out.println("MyDesktopPlanner object deserialized successfully.");
            } else {
                // The file doesn't exist, handle the case accordingly
                // For example, you can initialize the `users` HashMap to an empty HashMap
                users = new HashMap<>();
                System.out.println("No serialized file found. Initializing with an empty user list.");
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }



    public  void setUsers(HashMap<String, User> users) {
        this.users = users;
    }

    public static String getSerializationPath() {
        return serializationPath;
    }

    public  HashMap<String, User> getUsers() {
        return users;
    }

}