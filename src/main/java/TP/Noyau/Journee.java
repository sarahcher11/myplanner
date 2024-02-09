package TP.Noyau;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Journee implements Comparable<Journee>, Serializable {

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


   private boolean felicitation;



    public void affecterTache(Tache tache, Creneau creneau) {
        creneau.setTache(tache); // Assign the task to the creneau
        creneau.decomposer(tache);
        taches.put(tache.getNom(), tache); // Add the task to the map of tasks for this journée
    }


    public Journee(LocalDate dateDuJour, SortedSet<Creneau> creneaux) {
        this.dateDuJour = dateDuJour;
        this.creneaux = creneaux;
         taches=new HashMap<>();
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

             creneaux.add(creneau);

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


    public ArrayList<Creneau> recupCreneauxLibresCompa(Tache tache)
    {
        ArrayList<Creneau> creneauxLibres=new ArrayList<>();
        for(Creneau creneau: this.creneaux)
        {

            if(creneau.getTache()==null)
            {
                if( (tache instanceof TacheSimple) &&(creneau.deadlineRespectee(tache)) && (creneau.compatibiliteDuree(tache)!=0))
                {
                    creneauxLibres.add(creneau);
                }
                if((tache instanceof TacheDecomposable) && creneau.deadlineRespectee(tache))
                {
                    creneauxLibres.add(creneau);
                }

            }
        }
        return creneauxLibres;
    }

    public Creneau rechercherCreneau(String creneau)
    {
        for(Creneau creneau1 :this.creneaux)
        {
            if(creneau1.toString().equals(creneau))
            {
                return creneau1;
            }
        }
        return null;
    }
    public LocalDate getDateDuJour() {
        return dateDuJour;
    }

    public SortedSet<Creneau> getCreneaux() {
        return creneaux;
    }

    public Creneau getCreneau(Creneau creneau) {
        if (creneaux.contains(creneau)) {
            return creneau;
        } else {
            return null;
        }
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




    public void setTaches(HashMap<String, Tache> taches) {
        this.taches = taches;
    }

    public HashMap<String, Tache> getTaches() {
        return taches;
    }



    public int nbTachesRealisees()
    {
        int i=0;
        for(Creneau creneau: creneaux)
        {
            if (creneau.getTache()!=null)
            {
                if(creneau.getTache().getEtatDeRealisation().equals(EtatDeRealisation.completed))
                {
                    i++;
                }
            }
        }
        return i;
    }

    @Override
    public String toString()
    {
        return dateDuJour.toString();
    }
    @Override
    public int compareTo(Journee o) {
        return this.dateDuJour.compareTo(o.dateDuJour);
    }
    public boolean isFelicitation() {
        return felicitation;}

    public void setFelicitation(boolean felicitation) {
        this.felicitation = felicitation;
    }
}
