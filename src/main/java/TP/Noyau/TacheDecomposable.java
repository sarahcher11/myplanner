package TP.Noyau;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

public class TacheDecomposable extends Tache implements  Serializable {

    private long dureeRestante;
    Map<TacheSimple, Creneau> sousTaches;

    private EtatDeRealisation etatDeRealisation;

    public TacheDecomposable(String nom,LocalTime duree,LocalDate dayOfDeadline,LocalTime heureOfDeadline)
    {
        this.nom=nom;
        this.duree=duree;
        this.dayOfDeadline=dayOfDeadline;
        this.timeOfDeadline=heureOfDeadline;
        this.sousTaches=new TreeMap<>();
    }

    public TacheDecomposable(String nom, LocalTime duree, Priorite priorite, LocalDate dayOfDeadline, LocalTime heureOfDeadline, Categorie categorie) {
        super(nom, duree, priorite,dayOfDeadline,heureOfDeadline, categorie);
        TreeMap<TacheSimple, Creneau> sousTaches=new TreeMap<TacheSimple, Creneau>();
        this.sousTaches = sousTaches;
    }
    public void miseAjourDureeRestante(Creneau creneau)
    {
        dureeRestante=dureeRestante- creneau.calculerDureeCreneau();
    }



    public TacheSimple decomposer(Creneau creneau) {
        if (dureeRestante > 0) {
            String nomSousTache = this.nom + (sousTaches.size() + 1);
            System.out.println("-------------");
            System.out.println(nomSousTache);
            System.out.println(creneau.getHeureDebut());
            TacheSimple sousTache = new TacheSimple(nomSousTache, creneau.convertMinuteLocalTime(), priorite, dayOfDeadline, timeOfDeadline, categorie);
            System.out.println(creneau.getHeureDebut());
            this.sousTaches.put(sousTache, creneau);
            creneau.decomposer(sousTache);
            sousTache.setCreneau(creneau);
            creneau.setTache(sousTache);
            miseAjourDureeRestante(creneau);
            System.out.println(dureeRestante);
            return sousTache;
        }
        return null;
    }


    public void evaluer()
    {
        int completed=0 ;
        int inProgress=0;
        Set<TacheSimple> tacheSimpleSet=this.sousTaches.keySet();
        for(TacheSimple sousTache: tacheSimpleSet)
        {
            if(sousTache.getEtatDeRealisation().equals(EtatDeRealisation.completed))
            {
                completed++;
            }
            if(sousTache.getEtatDeRealisation().equals(EtatDeRealisation.inProgress))
            {
                inProgress++;
            }
        }
        if(completed==tacheSimpleSet.size()+1)
        {
            this.etatDeRealisation=EtatDeRealisation.completed;
        }
        if(inProgress>=1)
        {
            this.etatDeRealisation=EtatDeRealisation.inProgress;
        }
        super.setEtatDeRealisation(this.etatDeRealisation);

    }
    public Map<TacheSimple, Creneau> getSousTaches() {
        return sousTaches;
    }
    public void ajouterTache(TacheSimple soustache,Creneau creneau)
    {
        if(!sousTaches.containsKey(soustache))
        {
            sousTaches.put(soustache,creneau);
        }
    }

    public void setDureeRestante(long dureeRestante) {
        this.dureeRestante = dureeRestante;
    }

    public long getDureeRestante() {
        return dureeRestante;
    }

    @Override
    public EtatDeRealisation getEtatDeRealisation() {
        return etatDeRealisation;
    }
}
