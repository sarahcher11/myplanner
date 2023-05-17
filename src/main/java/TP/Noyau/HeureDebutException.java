package TP.Noyau;

public class HeureDebutException extends Exception{

    public HeureDebutException(){
        super("l'heure début est antérieur à l'heure de fin");
    }

}
