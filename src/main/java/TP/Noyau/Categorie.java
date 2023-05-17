package TP.Noyau;

import java.awt.*;
import java.util.Objects;

/**
 * Cette classe permet de créer une catégorie de taches selon le désir de l'utilisateur
 * On peut avoir plusieurs catégories de taches
 * chaque catégorie est caractérisé par sa couleur
 */
public class Categorie {

    /**
     * Le nom de la catégorie
     */
    private String nomCategorie;
    /**
     * La couleur de la catégorie
     */
    private Color couleur;

    public Categorie(String nomCategorie) {
        this.nomCategorie = nomCategorie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Categorie categorie)) return false;
        return Objects.equals(nomCategorie, categorie.nomCategorie);
    }

    @Override
    public int hashCode() {
        return this.nomCategorie.hashCode();
    }

    public Color getCouleur() {
        return couleur;
    }

    public void setCouleur(Color couleur) {
        this.couleur = couleur;
    }

    public String getNomCategorie(){
        return nomCategorie;
    }
}
