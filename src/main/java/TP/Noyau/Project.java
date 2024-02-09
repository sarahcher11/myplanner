package TP.Noyau;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;

/**
 * Cette classe représente un project qui est cataractérisé par son nom et l'ensemble des taches
 */
public class Project implements Serializable {

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

    public Project(String nomProjet, String description) {
        this.nomProjet = nomProjet;
        this.description = description;
        this.taches = new TreeMap<>();
        this.etatDeRealisation=EtatDeRealisation.notRealized;
    }
    public void setEtatRealisation() {
        Map<EtatDeRealisation, Integer> occurences = new HashMap<>();

        for (Tache tache : taches.values()) {
            EtatDeRealisation etatTache = tache.getEtatDeRealisation();
            occurences.put(etatTache, occurences.getOrDefault(etatTache, 0) + 1);
        }

        if (occurences.containsKey(EtatDeRealisation.inProgress)) {
            etatDeRealisation = EtatDeRealisation.inProgress;
        } else if (occurences.containsKey(EtatDeRealisation.completed)) {
            if (occurences.containsKey(EtatDeRealisation.delayed) || occurences.containsKey(EtatDeRealisation.cancelled)) {
                etatDeRealisation = EtatDeRealisation.completed;
            } else if (occurences.size() == 1) {
                // Toutes les tâches ont le même état
                etatDeRealisation = occurences.keySet().iterator().next();
            } else {
                etatDeRealisation = EtatDeRealisation.unscheduled;
            }
        }

        // Autres méthodes et constructeurs de la classe Projet
    }

    public EtatDeRealisation getEtatDeRealisationProjet() {
        return etatDeRealisation;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Project project)) return false;
        return Objects.equals(nomProjet, project.nomProjet);
    }

    public EtatDeRealisation getEtatDeRealisation() {
        return etatDeRealisation;
    }



    @Override
    public int hashCode() {
        return this.nomProjet.hashCode();
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
