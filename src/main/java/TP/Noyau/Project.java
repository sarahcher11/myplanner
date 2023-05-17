package TP.Noyau;

import java.util.TreeMap;

/**
 * Cette classe représente un project qui est cataractérisé par son nom et l'ensemble des taches
 */
public class Project {

    /**
     * Le nom de ce projet
     */
    private String nomProjet;
    /**
     * La description du projet en quoi ça consiste
     */
    private String description;
    /**
     * L'ensembles des taches de ce project
     */
    private TreeMap<String, Tache> taches;


    /**
     *
     */
    private EtatDeRealisation etatDeRealisation;

    public Project(String nomProjet, String description, TreeMap<String,Tache> taches, EtatDeRealisation etatDeRealisation) {
        this.nomProjet = nomProjet;
        this.description = description;
        this.taches = taches;
        this.etatDeRealisation=EtatDeRealisation.notRealized;
    }


    /**
     * Cette méthode permet d'ajouter une tache à ce projet
     * @param tache la tache qu'on veut ajouter à ce projet et il ne faut pas que celle ci soit existante déjà
     */
    public void ajouterTache(Tache tache)
    {
        if(!taches.containsKey(tache.getNom()))
        {
            this.taches.put(tache.getNom(),tache);
        }
    }


    public TreeMap<String,Tache> getTaches() {
        return taches;
    }

    public String getNomProjet() {
        return nomProjet;
    }

    public String getDescription() {
        return description;
    }

}
