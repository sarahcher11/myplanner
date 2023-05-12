package TP;

import TP.*;

import java.time.LocalDate;
import java.time.LocalTime;

public class TacheSimple extends Tache {

    private int periodecite;

    private EtatDeRealisation etatDeRealisation;



    public TacheSimple(String nom, LocalTime duree, Priorite priorite, LocalDate dayOfDeadline, LocalTime timeOfDeadline, Categorie categorie) {
        super(nom, duree, priorite, dayOfDeadline,timeOfDeadline, categorie);
        this.etatDeRealisation=EtatDeRealisation.unscheduled;
    }
    public void setPeriodecite(int periodecite) {
        this.periodecite = periodecite;
    }

    public void evaluer(User user, Creneau creneau)
    {
      if(user.planifier(user,this,creneau))
      {
          etatDeRealisation=EtatDeRealisation.inProgress;
      }
      else
      {
          etatDeRealisation=EtatDeRealisation.unscheduled;
      }
    }





    public EtatDeRealisation getEtatDeRealisation() {
        return etatDeRealisation;
    }

    public void setEtatDeRealisation(EtatDeRealisation etatDeRealisation) {
        this.etatDeRealisation = etatDeRealisation;
    }
}
