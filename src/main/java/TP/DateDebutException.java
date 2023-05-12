package TP;

import java.time.LocalDate;

public class DateDebutException extends Exception{
    public DateDebutException(LocalDate dateDebut)
    {
        super("La date saisie [\""+ dateDebut+"\"] est antérieure à la date d'aujourd'hui. Vous n'êtes pas autorisés à entrer cette date");
    }
}
