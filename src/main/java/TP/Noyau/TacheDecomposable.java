package TP.Noyau;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;
import java.util.TreeMap;

public class TacheDecomposable extends Tache implements Decomposable {

    private long dureeRestante=dureeEnMinute();
    TreeMap<TacheSimple, Creneau> sousTaches;

    private EtatDeRealisation etatDeRealisation;

    public TacheDecomposable(String nom, LocalTime duree, Priorite priorite, LocalDate dayOfDeadline, LocalTime heureOfDeadline, Categorie categorie) {
        super(nom, duree, priorite,dayOfDeadline,heureOfDeadline, categorie);
        TreeMap<TacheSimple, Creneau> sousTaches=new TreeMap<TacheSimple, Creneau>();
        this.sousTaches = sousTaches;
    }
    private void miseAjourDureeRestante(Creneau creneau)
    {
        dureeRestante=dureeRestante- creneau.calculerDureeCreneau();
    }
    public boolean decomposer(Object object)
    {
        if(object instanceof Creneau)
        {
            Creneau creneau=(Creneau) object;

            if(dureeRestante>0)
            {
                String nomSousTache=this.nom+ (sousTaches.size()+1);
                TacheSimple sousTache=new TacheSimple(nomSousTache,creneau.convertMinuteLocalTime(),priorite,dayOfDeadline,timeOfDeadline,categorie);
                sousTaches.put(sousTache,creneau);
                return true;
            }
        }

        return false;

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
    }
    public TreeMap<TacheSimple, Creneau> getSousTaches() {
        return sousTaches;
    }
    public void ajouterTache(TacheSimple soustache,Creneau creneau)
    {
        if(!sousTaches.containsKey(soustache))
        {
            sousTaches.put(soustache,creneau);
        }
    }

    public long getDureeRestante() {
        return dureeRestante;
    }

    @Override
    public EtatDeRealisation getEtatDeRealisation() {
        return etatDeRealisation;
    }
}
