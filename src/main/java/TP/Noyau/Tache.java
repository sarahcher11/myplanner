package TP.Noyau;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

public abstract class Tache implements Serializable {

    protected String nom;
    protected LocalTime duree;
    protected Priorite priorite;
    protected LocalDate dayOfDeadline;
    protected LocalTime timeOfDeadline;
    protected Categorie categorie;
    protected EtatDeRealisation etatDeRealisation;


    public Tache(String nom, LocalTime duree, Priorite priorite, LocalDate deadline, LocalTime timeOfDeadline, Categorie categorie) {
        this.nom = nom;
        this.duree = duree;
        this.priorite = priorite;
        this.dayOfDeadline = deadline;
        this.timeOfDeadline=timeOfDeadline;
        this.categorie = categorie;
    }
    public Tache()
    { }



    public long dureeEnMinute() {
        Duration duration = Duration.between(LocalTime.MIN, duree);
        return duration.toMinutes();
    }


    public boolean Notifier()
    {
        if( (this.timeOfDeadline.equals(LocalTime.now())) && (this.dayOfDeadline.equals(LocalDate.now())))
        {
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tache tache)) return false;
        return Objects.equals(nom, tache.nom) && Objects.equals(duree, tache.duree) && Objects.equals(dayOfDeadline, tache.dayOfDeadline) && Objects.equals(timeOfDeadline, tache.timeOfDeadline);
    }



    public void setEtatDeRealisation(EtatDeRealisation etatDeRealisation) {
        this.etatDeRealisation = etatDeRealisation;
    }


    public String getNom() {
        return nom;
    }

    public LocalTime getDuree() {
        return duree;
    }

    public Priorite getPriorite() {
        return priorite;
    }

    public LocalDate getDayOfDeadline() {
        return dayOfDeadline;
    }

    public LocalTime getTimeOfDeadline() {
        return timeOfDeadline;
    }

    public EtatDeRealisation getEtatDeRealisation() {
        return etatDeRealisation;
    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setPriorite(Priorite priorite) {
        this.priorite = priorite;
    }
    // JavaFX properties
    public ObjectProperty<String> nomProperty() {
        return new SimpleObjectProperty<>(nom);
    }

    public ObjectProperty<LocalTime> dureeProperty() {
        return new SimpleObjectProperty<>(duree);
    }

    public ObjectProperty<Priorite> prioriteProperty() {
        return new SimpleObjectProperty<>(priorite);
    }

    public ObjectProperty<LocalDate> dayOfDeadlineProperty() {
        return new SimpleObjectProperty<>(dayOfDeadline);
    }

    public ObjectProperty<LocalTime> timeOfDeadlineProperty() {
        return new SimpleObjectProperty<>(timeOfDeadline);
    }

    public ObjectProperty<Categorie> categorieProperty() {
        return new SimpleObjectProperty<>(categorie);
    }

    public ObjectProperty<EtatDeRealisation> etatDeRealisationProperty() {
        return new SimpleObjectProperty<>(etatDeRealisation);
    }
}
