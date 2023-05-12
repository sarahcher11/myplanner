package TP;

import TP.Creneau;
import TP.Journee;
import TP.Periode;

import java.util.ArrayList;
import java.util.SortedSet;

public class Planning {
    private ArrayList<Journee> journees;
    private Periode periode;
    private SortedSet<Tache> taches ;

    public Planning(ArrayList<Journee> journees, Periode periode) {
        this.journees = journees;
        this.periode = periode;
    }

    public void initialiserJournees(SortedSet<Creneau> creneaux)
    {
        Journee jour;
        for(int i=0;i<journees.size();i++)
        {
            jour=new Journee(periode.getDateDebut().plusDays(i),creneaux);
            journees.add(jour);
        }
    }

    public ArrayList<Creneau> recupCreneauxLibres()
    {
        ArrayList<Creneau> creneauxLibres=new ArrayList<Creneau>();
        ArrayList<Creneau> creneaux1;
        if(this.periode.equals(null))
        {
            for(Journee journee: journees)
            {
                if(journeeInPeriode(journee))
                {
                    creneaux1=journee.recupCreneauxLibres();
                    creneauxLibres.addAll(creneaux1);
                }

            }
        }
        else
        {
            for(Journee journee: journees)
            {
                    creneaux1=journee.recupCreneauxLibres();
                    creneauxLibres.addAll(creneaux1);
            }
        }

        return creneauxLibres;
    }


    public boolean journeeInPeriode(Journee journee)
    {
        if((journee.getDateDuJour().isBefore(periode.getDateFin()))
                && (journee.getDateDuJour().isAfter(periode.getDateDebut())) )
        {
            return true;
        }
        return false;

    }

    public ArrayList<Journee> getJournees() {
        return journees;
    }

    public void setJournees(ArrayList<Journee> journees) {
        this.journees = journees;
    }

    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }



}
