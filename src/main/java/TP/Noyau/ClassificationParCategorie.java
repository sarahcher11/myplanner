package TP.Noyau;

import java.awt.*;
import java.util.Set;

public class ClassificationParCategorie {
    private String nomCategorie;
    private Set<Tache> taches;
    private Color color;

    public ClassificationParCategorie(String nomCategorie, Set<Tache> taches, Color color) {
        this.nomCategorie = nomCategorie;
        this.taches = taches;
        this.color = color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public void ajouterTache(Tache tache)
    {
        if(tache.getCategorie().getNomCategorie().equals(this.nomCategorie) && !taches.contains(tache))
        {
            this.taches.add(tache);
        }
    }
}
