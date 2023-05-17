package TP.Noyau;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.SortedSet;

public class Journee implements Comparable<Journee>{

    // dans journee on choisi un creneau libre, ce creneau sera affecté pour tache precise
    // cette tache sera ajoutée dans taches(de la même journee) et le créneau sera enlevée de creneaux Libres

    /**
     * La date du jour
     */
    private LocalDate dateDuJour;
    /**
     * Les créneaux libres de la journée
     */
    private SortedSet<Creneau> creneaux;

    /**
     * L'ensemble des tâches à réaliser
     */

    private HashMap<String, Tache> taches;
    /**
     * Le nombre de tâches réalisées pour cette journée
     */
    private int nbTacheRealisees;
    public Journee(LocalDate dateDuJour, SortedSet<Creneau> creneaux) {
        this.dateDuJour = dateDuJour;
        this.creneaux = creneaux;
    }
     public void supprimerCreneau(Creneau creneau)
     {
         if(creneaux.contains(creneau))
         {
             creneaux.remove(creneau);
         }
     }
     public void ajouterCreneau(Creneau creneau)
     {
         if (!creneaux.contains(creneau))
         {
             creneaux.add(creneau);
         }
     }
     public ArrayList<Creneau> recupCreneauxLibres()
     {
         ArrayList<Creneau> creneauxLibres=new ArrayList<Creneau>();
         for(Creneau creneau: this.creneaux)
         {

             if(creneau.getTache()==null)
             {
                 creneauxLibres.add(creneau);
             }
         }
         return creneauxLibres;
     }
    public LocalDate getDateDuJour() {
        return dateDuJour;
    }

    public SortedSet<Creneau> getCreneaux() {
        return creneaux;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Journee journee)) return false;
        return dateDuJour.equals(journee.dateDuJour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(dateDuJour);
    }






    public void afficherCreneaux()
    {
        for(Creneau creneau: creneaux)
        {
            System.out.println(creneau.toString());
        }
    }


    public void setNbTacheRealisees(int nbTacheRealisees) {
        this.nbTacheRealisees = nbTacheRealisees;
    }

    public int getNbTacheRealisees() {
        return nbTacheRealisees;
    }


    @Override
    public int compareTo(Journee o) {
        return this.dateDuJour.compareTo(o.dateDuJour);
    }
}
