package TP.Noyau;
import TP.Noyau.Categorie;
import TP.Noyau.EtatDeRealisation;
import TP.Noyau.Priorite;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;

public abstract class Tache implements Serializable {

    protected String nom;
    protected LocalTime duree;
    protected Priorite priorite;
    protected LocalDate dayOfDeadline;
    protected LocalTime timeOfDeadline;
    protected Categorie categorie;
    protected EtatDeRealisation etatDeRealisation;



    public Tache(String nom, LocalTime duree, Priorite priorite, LocalDate deadline,LocalTime timeOfDeadline, Categorie categorie) {
        this.nom = nom;
        this.duree = duree;
        this.priorite = priorite;
        this.dayOfDeadline = deadline;
        this.timeOfDeadline=timeOfDeadline;
        this.categorie = categorie;
    }



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



}
