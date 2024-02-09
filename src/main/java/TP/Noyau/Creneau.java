package TP.Noyau;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalTime;

public class Creneau implements Decomposable,Comparable<Creneau>, Serializable {
   /**
    *Heure  début du créneau
  */
    private LocalTime heureDebut;
    /**
     * Heure fin du créneau
     */
    private LocalTime heureFin;
    /**
     * La jour de ce créneau libre
     */
    private Tache tache;

    private Journee journee;

    private boolean blocked;
    /**
     * Cette methode est le constructeur du créneau
     * Elle sert à instancier un objet de type crénau en initialisant ses attribut
     * @param heureDebut l'heure début de ce créneau
     * @param heureFin l'heure de fin de ce créneau
     */
    public Creneau(LocalTime heureDebut, LocalTime heureFin) throws HeureDebutException {
        if(heureDebut.isAfter(heureFin))
        {
          throw new HeureDebutException();
        }
        else{
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
            this.blocked=false;
        }

    }


    /**
     * Cette methode sert à transformer la durée qui est en LocalTime en minute
     * @return la durée en minute
     */
    public long calculerDureeCreneau()
    {
        Duration duration = Duration.between(heureDebut, heureFin);
        return duration.toMinutes(); // Convert duration to minutes
    }



    /**
     * Cette méthode donne vrai si la durée de la tache est compatible avec la durée de ce créneau
     * @param tache la tache que je veux affecter à ce créneau
     * @return retourne vrai si on peut affecter la tache à ce créneau et faux sinon
     */
    public int compatibiliteDuree(Tache tache)
    {
        int n=0;
        if(calculerDureeCreneau()>tache.dureeEnMinute())
        {
            n=1;
        }
        else
        {
            if (calculerDureeCreneau()==tache.dureeEnMinute())
            {
                n=2;
            }
        }
        return n;
    }

    /**
     * Cette méthode permet de calculer la durée restante après avoir affecté une tache
     * @param tache la tache qu'on veut affecter au créneau
     * @return elle retourne la durée restante en minute
     */

    public long dureeRestante(Tache tache)
    {
        return (calculerDureeCreneau()-tache.dureeEnMinute());
    }


    /**
     * fffdfdf
     * @param object
     * @return
     */

    public boolean decomposer(Object object)
    {
        Tache tache=(Tache) object;
        if(compatibiliteDuree(tache)==1 && dureeRestante(tache)>= User.getDureeMin())
        {
            try{
                this.tache=tache;
                Creneau newCreneau=new Creneau(this.heureDebut.plusMinutes(tache.dureeEnMinute()),this.heureFin);
                this.heureFin=this.heureDebut.plusMinutes(tache.dureeEnMinute());
                newCreneau.setJournee(this.journee);
                System.out.println("Le nouveau creneau cree ");
                System.out.println(newCreneau);
                journee.ajouterCreneau(newCreneau);
                return true;
            }
            catch (HeureDebutException e)
            {
                e.printStackTrace();
            }

        }
            return false;
    }

    public LocalTime convertMinuteLocalTime()
    {
        long minutes= calculerDureeCreneau();
        return LocalTime.ofSecondOfDay(minutes * 60);
    }


    public void annulerPlanification(Tache tache)
    {
        if(tache.equals(this.tache))
        {
            this.tache=null;
            tache.etatDeRealisation= EtatDeRealisation.cancelled;
        }
    }

    @Override
    public int compareTo(Creneau creneau) {
        return heureDebut.compareTo(creneau.heureDebut);
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Creneau creneau)) return false;
        return heureDebut.equals(((Creneau) o).getHeureDebut()) && heureFin.equals(((Creneau) o).heureFin) && this.journee.getDateDuJour().equals(((Creneau) o).getJournee().getDateDuJour());
    }


    public boolean deadlineRespectee(Tache tache)
    {

        if(  ((this.journee.getDateDuJour().isBefore(tache.getDayOfDeadline()) || journee.getCreneaux().equals(tache.getDayOfDeadline()))))
        {
            if(journee.getCreneaux().equals(tache.getDayOfDeadline()))
            {
                if(this.heureDebut.isBefore(tache.getTimeOfDeadline()))
                {
                    return true;
                }
                else
                {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return  heureDebut +
                " - " + heureFin;
    }





    public boolean egalEnTermeDuree(Tache tache)
    {
        if(this.calculerDureeCreneau()==tache.dureeEnMinute())
        {
            return true;
        }
        return false;
    }


    //*********************************************Getters & Setters*********************************//

    public LocalTime getHeureDebut() {
        return heureDebut;
    }

    public void setHeureDebut(LocalTime heureDebut) {
        this.heureDebut = heureDebut;
    }

    public LocalTime getHeureFin() {
        return heureFin;
    }

    public void setHeureFin(LocalTime heureFin) {
        this.heureFin = heureFin;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setJournee(Journee journee) {
        this.journee = journee;
    }

    public Journee getJournee() {
        return journee;
    }
}
