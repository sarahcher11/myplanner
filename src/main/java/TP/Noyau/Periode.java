package TP.Noyau;

import TP.Noyau.DateDebutException;

import java.time.LocalDate;

/**
 * Cette classe caractérise une période donnée qui sétale entre une début et une date fin
 */
public class Periode {

    /**
     * La date du début de la période
     */
    private LocalDate dateDebut;

    /**
     * La date de fin de la période
     */
    private LocalDate dateFin;

    private double moyenneDeRendementJournalier;


    /**
     * Cette méthode est un contructeur de cette classe
     * Ce constructeur intialise les attributs de la classe la date de début et la date de fin
     * @param dateDebut la date du début de la période qui ne doit pas être avant la date de fin
     * @param dateFin  la date de fin de la période
     * @throws DateDebutException une exception qui sera déclenché si la date du début est antérieure a la date de fin
     */
    public Periode(LocalDate dateDebut, LocalDate dateFin)throws DateDebutException {
        if(dateDebut.isBefore(LocalDate.now()) || dateDebut.isAfter(dateFin) ) throw new DateDebutException(dateDebut);
        else {
            this.dateDebut = dateDebut;
            this.dateFin = dateFin;

        }

    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }
}
