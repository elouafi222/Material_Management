package com.example.planificationmobile2.traitementPourProjet;
import android.os.Build;

import  java.time.*;
import java.time.format.TextStyle;
import java.time.temporal.ChronoUnit;
import java.util.Locale;

public class projet {
    private String idProjet;
    private String nomProjet;
    private String descriptionProjet;
    private LocalDateTime dateDebut;
    private LocalDateTime dateFin;
    private String chefProjet;
    private String etatProjet;

    public projet(String idProjet, String nomProjet, String descriptionProjet,LocalDateTime dateDebut,LocalDateTime dateFin, String chefProjet, String etatProjet){
        this.idProjet=idProjet;
        this.chefProjet=chefProjet;
        this.descriptionProjet=descriptionProjet;
        this.dateDebut=dateDebut;
        this.nomProjet=nomProjet;
        this.etatProjet=etatProjet;
        this.dateFin=dateFin;
    }

    public  String getIdProjet(){
        return this.idProjet;
    }

    public String getNom(){
       return this.nomProjet;
    }
    public  String getDescriptionProjet(){
        return this.descriptionProjet;
    }

    public  String getChefProjet(){
        return  this.chefProjet;
    }

    public LocalDateTime  getDateDebut(){
        return this.dateDebut;
    }

    public  LocalDateTime getDateFin(){return this.dateFin;}
    public String getEtatProjet(){
        return this.etatProjet;
    }

    public String toString(){
        return this.nomProjet+"::"+this.etatProjet+"::"+this.descriptionProjet+"::"+this.dateDebut+"::"+this.chefProjet;
    }

    public boolean equals(projet o){
        return this.idProjet.equals(o.idProjet);
    }

    /**
     * methode permet de recuperer le nom jour de debut d'un projet
     * @return
     */

    public String recuperJourDatedb(){
        // Récupérer le jour de la semaine au format texte en français
        String dayOfWeekText = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dayOfWeekText = dateDebut.getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.FRENCH);
        }
        String dayOfWeekAbbreviation = dayOfWeekText.substring(0, 3).toUpperCase();
        return dayOfWeekAbbreviation;
    }

    /**
     * methode permet de recuperer le jour de debut d'un projet
     * @return
     */

    public String recuperJourDatedb2(){
        // Récupérer le jour de la semaine au format texte en français
        String dayOfWeekText = null;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dayOfWeekText = ""+dateDebut.getDayOfMonth();
        }
        return dayOfWeekText;
    }

    /**
     * mathode permet de recuperer le moi de debut d'un projet
     * @return
     */

    public String recuperMoiDatedb() {
        String moisdb = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Month mois = dateDebut.getMonth();
            moisdb = mois.getDisplayName(TextStyle.FULL, Locale.FRENCH).substring(0, 3).toUpperCase();
        }
        return moisdb;
    }

    /**
     * methode permet de recuperer l'anne de debut d'un projet
     * @return
     */
    public String recuperAnneeDatedb(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return String.valueOf(this.dateDebut.getYear()).substring(2);
        }
        return "problem";
    }

    /**
     * methode permet de recuperer la duree restant sur la fin d'un projet
     * @return
     */
    public String dureRestant(){
        String dureeRest="";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime dateAujourdhuit =LocalDateTime.now();
            Period period = Period.between(dateAujourdhuit.toLocalDate(),dateFin.toLocalDate());

            if(period.isNegative()){
                long heurePasse=ChronoUnit.HOURS.between(dateFin,dateAujourdhuit);
                if(heurePasse>-24)
                    return heurePasse+"h";
                ////////// pour les joure passe
                return ChronoUnit.DAYS.between(dateFin,dateAujourdhuit)+"j";
            } else if (period.isZero()) {

                return "Aujourd'hui";

            } else if (period.getMonths()>0) {
                return "+"+ period.getMonths()+" j";
            } else if (period.getDays()>0) {
                return "+"+period.getDays()+" h";
            }else{
                Duration duration = Duration.between(LocalDateTime.now(),dateFin);
                long heuresRestantes = duration.toHours();
                long minutesRestantes = duration.toMinutes() % 60;
                if(heuresRestantes<1)
                    return "+"+heuresRestantes+" h";
                return "+"+minutesRestantes+" min";
            }
        }

        return  dureeRest;
    }

}
