package TP.Noyau;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class Planning implements Comparable<Planning>, Serializable {

    /**
     * L'ensemble des journees d'un planning
     */
    private HashMap<LocalDate,Journee> journees;
    /**
     * La période de ce planning
     */
    private Periode periode;
    /**
     * Les tache à realiser dans ce planning
     */
    private ArrayList<Tache> taches ;
    /**
     * Nombre et types de badges gagnés
     */
    private HashMap<Badge,Integer> BadgeNbrType;


    private HashMap<String, Project> projects;



    public Planning( HashMap<LocalDate, Journee> journees, Periode periode) {
        this.journees = journees;
        this.periode = periode;
        this.taches=new ArrayList<>();
        this.BadgeNbrType=new HashMap<>();
        projects=new HashMap<>();

    }

    /**
     * Initilisation des journées apparteneat au planning avec un ensemble de taches
     * @param creneaux
     */

    public void initialiserJournees(SortedSet<Creneau> creneaux)
    {

        for (int i = 0; i < periode.getNumberOfDays(); i++) {
            Journee jour = new Journee(periode.getDateDebut().plusDays(i), new TreeSet<>());

            for (Creneau creneau : creneaux) {
                Creneau newCreneau = null;
                try {
                    newCreneau = new Creneau(creneau.getHeureDebut(), creneau.getHeureFin());
                    newCreneau.setJournee(jour);
                    jour.getCreneaux().add(newCreneau);
                } catch (HeureDebutException e) {
                    throw new RuntimeException(e);
                }

            }

            journees.put(periode.getDateDebut().plusDays(i), jour);
        }

    }


    /**
     * Recupérer les creneaux libres de ce planning
     * @return l'ensemble des creneaux libres
     */
    public ArrayList<Creneau> recupCreneauxLibres()
    {
        ArrayList<Creneau> creneauxLibres=new ArrayList<>();
        ArrayList<Creneau> creneaux1;
        for(int i=0;i<this.periode.getNumberOfDays();i++)
        {
            LocalDate date=this.periode.getDateDebut().plusDays(i);
            Journee journee=this.journees.get(date);

                creneaux1=journee.recupCreneauxLibres();
                creneauxLibres.addAll(creneaux1);

        }


        return creneauxLibres;
    }




    public Journee journeeInPeriode(LocalDate laDate)
    {
        if(this.journees.containsKey(laDate))
        {
            return this.journees.get(laDate);
        }
        return null;
    }


    /**
     * Planifier une tache manuellement
     * @param tache la tache à planifier
     * @param day le jour ou elle doit être planifiée
     * @param creneau le créneau concerné
     * @param blocked l'etat de ce creneau
     * @return vrai si cela a été fait
     */
    public boolean planifier( Tache tache, LocalDate day,Creneau creneau,boolean blocked)
    {
         if(tache instanceof TacheSimple)
         {
             if(this.journees.containsKey(day))
             {
                     creneau.setBlocked(blocked);
                     this.journees.get(day).affecterTache(tache,creneau);
                     this.taches.add(tache);
                     return true;
             }
             else
             {
                 tache.setEtatDeRealisation(EtatDeRealisation.unscheduled);
             }
         }
         return false;
    }


    public int nbTachesRealisees()
    {
        int i=0;
        for(Tache tache:taches)
        {
            if (tache.getEtatDeRealisation().equals(EtatDeRealisation.completed))
            {
                i++;
            }
        }
        return i;
    }
    public int nbProjetsRealisees()
    {
        int i=0;
        for(Project projet: projects.values())
        {
            if (projet.getEtatDeRealisation().equals(EtatDeRealisation.completed))
            {
                i++;
            }
        }
        return i;
    }



    public ArrayList<Creneau> recupererCreneauCompa(Tache tache)
    {
        ArrayList<Creneau> creneaux=new ArrayList<>();
        ArrayList<Creneau>  creneaux1;
        for(Journee journee:journees.values())
        {
            creneaux1=journee.recupCreneauxLibresCompa(tache);
            creneaux.addAll(creneaux1);
        }
        return creneaux;
    }


    /**
     * Planifier automatiquement une tache décomposable
     * @param tache la tache en question
     * @return elle retourne vrai si cette derniere a été planifié faux sinon
     */
    public boolean planifier(TacheDecomposable tache) {
        ArrayList<Creneau> creneaux = recupCreneauxLibres();
        tache.setDureeRestante(tache.dureeEnMinute());
        System.out.println("Le oooooooooooo"+creneaux.size());
        boolean planif = false;
        if (tache instanceof TacheDecomposable) {
            if (calculerSommeDurees(creneaux) >= tache.dureeEnMinute()) {
                for (Creneau creneau : creneaux) {
                    System.out.println("Deadline respectée : " + creneau.deadlineRespectee(tache));
                    System.out.println("Durée restante : " + tache.getDureeRestante());
                    if (creneau.deadlineRespectee(tache) && tache.getDureeRestante() > 0) {
                        // Créer une nouvelle instance de la tâche pour chaque sous-tâche
                        TacheSimple sousTache =tache.decomposer(creneau);
                        sousTache.setEtatDeRealisation(EtatDeRealisation.notRealized);
                        creneau.setTache(sousTache);
                        System.out.println("creneauuuuuuuuuuu"+ creneau.getJournee().getDateDuJour());
                        tache.miseAjourDureeRestante(creneau);
                        this.taches.add(sousTache);
                    }
                }


                this.taches.add(tache);
                planif = true;
            } else {
                tache.setEtatDeRealisation(EtatDeRealisation.unscheduled);
                this.taches.add(tache);
                planif = false;
            }
        }
        return planif;
    }
    public Creneau planifier(TacheSimple tache)
    {
        ArrayList<Creneau> creneaux=recupererCreneauCompa(tache);
        System.out.println("siiiiiiiiiiiiiiize"+creneaux.size());
        if(creneaux.size()!=0)
        {
           Creneau creneau=creneaux.get(0);

           return creneau;

        }
        else
        {
            tache.setEtatDeRealisation(EtatDeRealisation.unscheduled);
            this.taches.add(tache);
            return null;
        }
    }
    public void planifierValidee(TacheSimple tache,Creneau creneau)
    {
        creneau.setTache(tache);
        creneau.decomposer(tache);
        tache.setCreneau(creneau);
        this.taches.add(tache);

    }

    public void planifierEnsembleTaches(ArrayList<Tache> taches)
    {
        for(Tache tache: taches)
        {
            if(tache instanceof TacheDecomposable)
            {
                planifier((TacheDecomposable) tache);
                this.taches.add(tache);
            }
            else {
                if(tache instanceof TacheSimple)
                {
                    planifier((TacheSimple) tache);
                    this.taches.add(tache);
                }
            }
        }
    }


    public void replanifier()
    {
        taches.sort(Comparator.comparing(Tache::getPriorite));

    }

    public void planifierTachePeriodique(TacheSimple tache,Creneau creneau,LocalDate dateDebut,int periodicite)
    {
           Journee journee=this.journees.get(dateDebut);
           creneau.setTache(tache);
           tache.setCreneau(creneau);
           Journee journee1=journee;
           int n=0;
          while (journee1.getDateDuJour().isBefore(this.getPeriode().getDateFin()))
          {
               n=n+periodicite;
               LocalDate date=journee1.getDateDuJour().plusDays(n);
               journee1=this.journees.get(date);
          }
    }




    public double calculerSommeDurees(ArrayList<Creneau> creneaux) //Savoir la somme d'un emsemble de creneaux
    {
        double duree=0;
        for(Creneau creneau: creneaux)
        {
            duree=duree+creneau.calculerDureeCreneau();
        }
        return duree;
    }

    public ArrayList<Creneau> displayCreneau(Tache tache,Journee journee)
    {
        ArrayList<Creneau> creneaux=new ArrayList<>();
        journee.recupCreneauxLibresCompa(tache);
        return  creneaux;
    }



    //Recuperer une tache selon une catégorie donnée
    public List<Tache> recupererCategorieTache(String nomDeCatgegorie)
    {
        List<Tache> taches=new ArrayList<>();
        for(Journee journee: journees.values())
        {
            for(Tache tache: journee.getTaches().values())
            {
                if(tache.getCategorie().getNomCategorie().equals(nomDeCatgegorie))
                {
                    taches.add(tache);
                }
            }
        }
        for(Tache tache:this.taches)
        {
              if(tache.getCategorie().getNomCategorie().equals(nomDeCatgegorie))
            {
                taches.add(tache);
            }
        }
        taches.sort(Comparator.comparing(Tache::getPriorite));

      return taches;
    }




    public void setJournees(HashMap<LocalDate, Journee> journees) {
        this.journees = journees;
    }


    public Periode getPeriode() {
        return periode;
    }

    public void setPeriode(Periode periode) {
        this.periode = periode;
    }

    public void setBadgeNbrType(HashMap<Badge, Integer> badgeNbrType) {
        BadgeNbrType = badgeNbrType;
    }



    public void setProjects(HashMap<String, Project> projects) {
        this.projects = projects;
    }

    public HashMap<String, Project> getProjects() {
        return projects;
    }



    public void setTaches(ArrayList<Tache> taches) {
        this.taches = taches;
    }

    public ArrayList<Tache> getTaches() {
        return taches;
    }

    public HashMap<LocalDate, Journee> getJournees() {
        return journees;
    }

    public HashMap<Badge, Integer> getBadgeNbrType() {
        return BadgeNbrType;
    }




    @Override
    public int compareTo(Planning o) {
        return this.periode.getDateDebut().compareTo(o.getPeriode().getDateDebut());
    }
}
