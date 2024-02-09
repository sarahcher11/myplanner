package TP.Noyau;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.Objects;

public class TacheSimple extends Tache implements Comparable<TacheSimple>, Serializable {

    private int periodecite;

    private Creneau creneau;

    public TacheSimple(String nom,Priorite priorite)
    {
        this.nom=nom;
        this.priorite=priorite;
    }

    public TacheSimple(String nom,LocalTime duree,LocalDate dayOfDeadLine,LocalTime timeOfDeadLine)
    {
       this.nom=nom;
       this.duree=duree;
       this.dayOfDeadline=dayOfDeadLine;
       this.timeOfDeadline=timeOfDeadLine;

    }
    public TacheSimple(String nom, LocalTime duree, Priorite priorite, LocalDate dayOfDeadline, LocalTime timeOfDeadline, Categorie categorie) {
        super(nom, duree, priorite, dayOfDeadline,timeOfDeadline, categorie);

    }
    public void setPeriodecite(int periodecite) {
        this.periodecite = periodecite;
    }

  /*  public void evaluer(User user, Creneau creneau)
    {
      if(user.planifier(user,this,creneau))
      {
          etatDeRealisation=EtatDeRealisation.inProgress;
      }
      else
      {
          etatDeRealisation=EtatDeRealisation.unscheduled;
      }

    }*/




    public EtatDeRealisation getEtatDeRealisation() {
        return etatDeRealisation;
    }

    public void setEtatDeRealisation(EtatDeRealisation etatDeRealisation) {
        this.etatDeRealisation = etatDeRealisation;
    }

    public Creneau getCreneau() {
        return creneau;
    }

    public void setCreneau(Creneau creneau) {
        this.creneau = creneau;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Tache tache)) return false;
        return Objects.equals(nom, tache.nom) && Objects.equals(duree, tache.duree) && Objects.equals(dayOfDeadline, tache.dayOfDeadline) && Objects.equals(timeOfDeadline, tache.timeOfDeadline);
    }
    @Override
    public int compareTo(TacheSimple other) {
        return Comparator.comparing(Tache::getPriorite, Comparator.comparingInt(Priorite::ordinal))
                .compare(this, other);
    }


}
