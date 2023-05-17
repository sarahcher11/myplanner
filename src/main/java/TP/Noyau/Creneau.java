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
     * La durée minimal du créneau utilisé pour affecter une tâche
     * Lorsque on affecte une tache à un créneau,
     * et que la durée restante du créneau est supérieure à la durée minimale donc ce créneau sera décomposé
     */
    private long dureeMin;
    /**
     * La jour de ce créneau libre
     */
    private Tache tache;


    private Journee journee;
    /**
     * Cette methode est le constructeur du créneau
     * Elle sert à instancier un objet de type crénau en initialisant ses attribut
     * @param heureDebut l'heure début de ce créneau
     * @param heureFin l'heure de fin de ce créneau
     * @param dureeMin la durée minimal pour que ce créneau puisse être décomposé
     */
    public Creneau(LocalTime heureDebut, LocalTime heureFin, long dureeMin) throws HeureDebutException {
        if(heureDebut.isAfter(heureFin))
        {
          throw new HeureDebutException();
        }
        else{
            this.heureDebut = heureDebut;
            this.heureFin = heureFin;
            this.dureeMin = dureeMin;
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
        if(compatibiliteDuree(tache)==1 && dureeRestante(tache)>=dureeMin)
        {
            try{
                this.tache=tache;
                Creneau newCreneau=new Creneau(this.heureDebut.plusMinutes(tache.dureeEnMinute()),this.heureFin,this.dureeMin);
                this.heureFin=this.heureDebut.plusMinutes(tache.dureeEnMinute());
                System.out.println("Le nouveau creneau cree ");
                newCreneau.toString();
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
        return heureDebut.equals(creneau.heureDebut) && heureFin.equals(creneau.heureFin);
    }


    public boolean deadlineRespectee(Tache tache)
    {

        if(  (this.journee.getDateDuJour().isAfter(tache.getDayOfDeadline())
        && (this.heureDebut.isBefore(tache.getTimeOfDeadline()))))
        {
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "TP.Noyau.Creneau{" +
                "heureDebut=" + heureDebut +
                ", heureFin=" + heureFin +
                '}';
    }


    public void tacheRealisee()
    {
        if(this.tache!=null)
        {
            this.tache.setEtatDeRealisation(EtatDeRealisation.completed);
            this.journee.setNbTacheRealisees(this.journee.getNbTacheRealisees()+1);
        }
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

    public long getDureeMin() {
        return dureeMin;
    }

    public Tache getTache() {
        return tache;
    }

    public void setTache(Tache tache) {
        this.tache = tache;
    }


    public void setJournee(Journee journee) {
        this.journee = journee;
    }
}
