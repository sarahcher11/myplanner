package TP;

import java.util.HashMap;
import java.util.Objects;
import java.util.Set;

public class User {
    private String pseudo;
    private Periode periode;
    private Set<Tache> taches;
    private Set<Project> projects;
    private Planning planning;
    private HashMap<Badge,Integer> felicitations;
    private int nbTacheMinimal;


    public User(String pseudo)
    {
        this.pseudo=pseudo;
    }


    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.pseudo, other.pseudo)) {
            return false;
        }
        return true;
    }

    public boolean seConnecter(String username)
    {
        if(this.pseudo.equals(username))
        {
            return true;
        }
        else{
            return false;
        }
    }

    public boolean planifier(User user, Tache tache, Creneau creneau)
    {
        if(tache instanceof TacheSimple tacheSimple )
        {
            if(creneau.compatibiliteDuree(tache)==1|| creneau.compatibiliteDuree(tache)==2)
            {
                creneau.setTache(tache);
                return true;
            }
            else
            {
                return false;
            }
        }
        return false;

    }
    @Override
    public int hashCode() {
        return pseudo.hashCode();
    }

    public String getPseudo() {
        return pseudo;
    }



    public Set<Tache> getTaches() {
        return taches;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Planning getPlanning() {
        return planning;
    }

    public Planning getCalendrier() {
        return planning;
    }
}
