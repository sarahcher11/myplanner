package TP.Noyau;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.*;

public class User implements Serializable {

    //************************************************Attributs*********************************************//
    private String pseudo;
    private Set<Tache> taches;
    private Set<Project> projects;
    private ArrayList<Planning> lesPlannings;

    private Planning planningCourant;
    private HashMap<String,Categorie> categories;
    private HashMap<Badge,Integer> felicitations;
    private int nbTacheMinimal=3;
    private int nbEncouragement;
    private LocalDate datePlusRentable;

    /**
     * La durée minimal du créneau utilisé pour affecter une tâche
     * Lorsque on affecte une tache à un créneau,
     * et que la durée restante du créneau est supérieure à la durée minimale donc ce créneau sera décomposé
     */

    private static final long dureeMin=30;


    //***********************************************Methode****************************************************//


    public User(String pseudo)
    {
        this.pseudo=pseudo;
        projects=new HashSet<>();
        taches=new HashSet<>();
        lesPlannings=new ArrayList<>();
        felicitations=new HashMap<>();
        categories=new HashMap<>();
        felicitations.put(Badge.GOOD,0);
        felicitations.put(Badge.veryGOOD,0);
        felicitations.put(Badge.EXCELLENT,0);
        Project project=new Project("NEW PROJECT","Description");
        this.projects.add(project);
    }

    public double rendement()
    {
        if(this.planningCourant.getTaches().size()!=0)
        {
            return (NbTachesRealiseesEnPlanning(this.planningCourant)/this.planningCourant.getTaches().size()) ;

        }
        else
        {
            return 0;
        }
    }




  /*  public void SetEncourPeriode()
    {

        for(int i = 0; i< planningCourant.getPeriode().getNumberOfDays(); i++)
        {
            LocalDate day= planningCourant.getPeriode().getDateDebut().plusDays(i);
            Journee journee= planningCourant.getJournees().get(day);
            if (( journee.getNbTacheRealisees())>= (this.nbTacheMinimal) )  journee.setFelicitation(true);
        }
    }

    public int Nb_Mess_Consec(){
        int nbCons = 0;
        int cpt=0;
        for(int i = 0; i< planningCourant.getPeriode().getNumberOfDays(); i++){
            LocalDate day= planningCourant.getPeriode().getDateDebut().plusDays(i);
            Journee journee= planningCourant.getJournees().get(day);
            if (journee.isFelicitation()==true){cpt++;}
            else{cpt=0;}
            if(cpt==5){
                nbCons++;
                cpt=0;
            }
        }

      return nbCons;
    }

    public void Affecter_Badge(){

          this.felicitations.put(Badge.GOOD,felicitations.get(Badge.GOOD)+Nb_Mess_Consec());
          if(this.felicitations.get(Badge.GOOD)==3){this.felicitations.put(Badge.veryGOOD,felicitations.get(Badge.veryGOOD)+1);}
          if(this.felicitations.get(Badge.veryGOOD)==3){this.felicitations.put(Badge.EXCELLENT,felicitations.get(Badge.EXCELLENT)+1);}

    }*/

    public void Set_Encour_Periode()
    {

        for(int i = 0; i< planningCourant.getPeriode().getNumberOfDays(); i++)
        {
            LocalDate day= planningCourant.getPeriode().getDateDebut().plusDays(i);
            Journee journee= planningCourant.getJournees().get(day);
            if (( journee.nbTachesRealisees())>= (this.nbTacheMinimal) ) {
                journee.setFelicitation(true);
                this.nbEncouragement++;
            }
        }
    }


    public int Nb_Mess_Consec(){
        int nbCons = 0;
        int cpt=0;
        for(int i = 0; i< planningCourant.getPeriode().getNumberOfDays(); i++){
            LocalDate day= planningCourant.getPeriode().getDateDebut().plusDays(i);
            Journee journee= planningCourant.getJournees().get(day);
            if (journee.isFelicitation()==true){cpt++;}
            else{cpt=0;}
            if(cpt==5){
                nbCons++;
                cpt=0;
            }
        }

        return nbCons;
    }

    public void Affecter_Badge(){
        if(felicitations!=null)
        {
            this.felicitations.put(Badge.GOOD,felicitations.get(Badge.GOOD)+Nb_Mess_Consec());
            if(this.felicitations.get(Badge.GOOD)==3){this.felicitations.put(Badge.veryGOOD,felicitations.get(Badge.veryGOOD)+1);}
            if(this.felicitations.get(Badge.veryGOOD)==3){this.felicitations.put(Badge.EXCELLENT,felicitations.get(Badge.EXCELLENT)+1);}

        }


    }


    public HashMap<String, EtatDeRealisation> EtatRealisationTaches(LocalDate date) {
        HashMap<String, EtatDeRealisation> taches_EtatRealisation = new HashMap<>();

        Journee journee = planningCourant.journeeInPeriode(date);
        if (journee != null) {
            Map<String, Tache> taches = journee.getTaches();
            for (Map.Entry<String, Tache> entry : taches.entrySet()) {
                String nomTache = entry.getKey();
                Tache tache = entry.getValue();
                EtatDeRealisation etatRealisation = tache.getEtatDeRealisation();

                taches_EtatRealisation.put(nomTache, etatRealisation);
            }
        }

        return taches_EtatRealisation;
    }

    public EtatDeRealisation getEtatRealisationProject(String nomProjet) {
        for (Project projet : projects) {
            if (projet.getNomProjet().equals(nomProjet)) {
                return projet.getEtatDeRealisationProjet();
            }
        }
        return null; // Retourner null si le projet n'est pas trouvé
    }

    public HashMap<Badge, Integer> getStatistiquesBadges() {
        HashMap<Badge, Integer> statistiques = new HashMap<>();

        for (Map.Entry<Badge, Integer> entry : felicitations.entrySet()) {
            Badge badge = entry.getKey();
            int nombre = entry.getValue();
            statistiques.put(badge, nombre);
        }

        return statistiques;
    }


    public double RendementJournalier(LocalDate date) {
        Journee journee = planningCourant.getJournees().get(date);
        if (journee != null) {
            int nbTachesCompletes = journee.nbTachesRealisees();
            int nbTachesPrevues = journee.getTaches().size();

            if (nbTachesPrevues > 0) {
                double rendement = (double) nbTachesCompletes / nbTachesPrevues;
                return rendement;
            }
        }

        return 0;
    }

    public double RendementMoyen() {
        LocalDate dateDebut = planningCourant.getPeriode().getDateDebut();
        LocalDate dateActuelle = LocalDate.now();

        int nbJoursParcourus = 0;
        double sommeRendements = 0.0;

        for (LocalDate date = dateDebut; date.isBefore(dateActuelle) || date.isEqual(dateActuelle); date = date.plusDays(1)) {
            double rendementJournalier = RendementJournalier(date);
            sommeRendements += rendementJournalier;
            nbJoursParcourus++;
        }

        if (nbJoursParcourus > 0) {
            double rendementMoyen = sommeRendements / nbJoursParcourus;
            return rendementMoyen;
        }

        return 0;
    }

    public int NbEncouragement() {
        return nbEncouragement;
    }

    public LocalDate Stat_DatePlusRentable() {
        HashMap<LocalDate, Journee> journees = planningCourant.getJournees();

        for (Map.Entry<LocalDate, Journee> entry : journees.entrySet()) {
            LocalDate date = entry.getKey();
            Journee journee = entry.getValue();

            if (datePlusRentable == null || journee.nbTachesRealisees() > journees.get(datePlusRentable).nbTachesRealisees()) {
                datePlusRentable = date;
            }
        }
        return datePlusRentable;
    }

    public int DureeTachesParCategorie(String categorie) {
        int sommeDuree = 0;

        for (Tache tache : this.planningCourant.getTaches()) {
            if (tache.getCategorie().equals(categorie)) {
                sommeDuree += tache.dureeEnMinute();
            }
        }

        return sommeDuree;
    }
    public HashMap<Badge, Integer> getBadgeNbrTypeProject(Planning planning) {
        for (Planning p : lesPlannings) {
            if (p.equals(planning)) {
                return p.getBadgeNbrType();
            }
        } return null;}

    public int NbTachesRealiseesEnPlanning(Planning planning) {
        for (Planning p : lesPlannings) {
            if (p.equals(planning)) {
                return p.nbTachesRealisees();
            }
        }

        return 0;
    }
    public int NbProjetsRealiseesEnPlanning(Planning planning) {
        for (Planning p : lesPlannings) {
            if (p.equals(planning)) {
                return p.nbProjetsRealisees();
            }
        }

        return 0;
    }
  //*************************************************Fonctions redéfinies********************************************//
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.pseudo, other.pseudo)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return pseudo.hashCode();
    }


    //*********************************************Getters & Setters****************************************************//

    public void setPlanningCourant(Planning planningCourant) {
        this.planningCourant = planningCourant;
    }

    public void setProjects(Set<Project> projects) {
        this.projects = projects;
    }

    public void setTaches(Set<Tache> taches) {
        this.taches = taches;
    }

    public String getPseudo() {
        return pseudo;
    }

    public Set<Tache> getTaches() {
        return taches;
    }

    public Set<Project> getProjects() {
        return projects;
    }

    public Planning getPlanningCourant() {
        return planningCourant;
    }

    public static long getDureeMin() {
        return dureeMin;
    }

    public Planning getCalendrier() {
        return planningCourant;
    }

    public ArrayList<Planning> getLesPlannings() {
        return lesPlannings;
    }

    public HashMap<Badge, Integer> getFelicitations() {
        return felicitations;
    }

    public void setLesPlannings(ArrayList<Planning> lesPlannings) {
        this.lesPlannings = lesPlannings;
    }

    public int getNbTacheMinimal() {
        return nbTacheMinimal;
    }

    public void setCategories(HashMap<String, Categorie> categories) {
        this.categories = categories;
    }

    public HashMap<String, Categorie> getCategories() {
        return categories;
    }

    public void setNbTacheMinimal(int nbTacheMinimal) {
        this.nbTacheMinimal = nbTacheMinimal;
    }
}


