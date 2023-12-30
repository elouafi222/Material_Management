package traitement_pour_lesTaches;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;

public class tache {

    private int idtache;
    private LocalDateTime date_creation;
    private LocalDateTime date_fin;
    private LocalDateTime duree_estimee;
    private String etat ;
    private String description;
    private int Score;
    private int idprojet;
    private String nomEmp;
    private String nomTache;
    private String nomprojet;
    private String nomchef;

    public  tache(String nomTache,int idtache,LocalDateTime date_creation,LocalDateTime date_fin,LocalDateTime duree_estimee,String etat,String description,int score,int idprojet,String nomEmp,String nomprojet,String nomchef){
        this.idtache=idtache;
        this.date_creation=date_creation;
        this.date_fin=date_fin;
        this.duree_estimee=duree_estimee;
        this.etat=etat;
        this.description=description;
        this.Score=score;
        this.idprojet=idprojet;
        this.nomEmp=nomEmp;
        this.nomTache=nomTache;
        this.nomprojet=nomprojet;
        this.nomchef=nomchef;
    }



    //////// les geters

    public int getIdtache() {
        return idtache;
    }

    public LocalDateTime getDate_creation() {
        return date_creation;
    }

    public LocalDateTime getDate_fin() {
        return date_fin;
    }

    public LocalDateTime getDuree_estimee() {
        return duree_estimee;
    }

    public String getEtat() {
        return etat;
    }

    public String getDescription() {
        return description;
    }

    public int getScore() {
        return Score;
    }

    public int getIdprojet() {
        return idprojet;
    }

    public String getNomEmp() {
        return nomEmp;
    }

    public String getNomTache(){return  this.nomTache;}

    public String getNomprojet(){
        return this.nomprojet;
    }

    public String getNomchef(){return this.nomchef;}

    /**
     * methode permet de recuperer la duree restant sur la fin d'un projet
     * @return
     */
    public String dureeRestante() {
        String dureeRest = "";

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            LocalDateTime dateAujourdhui = LocalDateTime.now();
            Duration duration = Duration.between(dateAujourdhui, duree_estimee);

            if (duration.isNegative()) {
                // si la durée est passée
                long heuresPassees = duration.toHours();
                if (heuresPassees < -24) {
                    // si la durée passée est supérieure à 24 heures
                    return "-" + (-heuresPassees) + "h";
                } else {
                    // si la durée passée est inférieure à 24 heures
                    return "-" + (-duration.toMinutes()) + "min";
                }
            } else if (duration.isZero()) {
                // si la durée est aujourd'hui
                return "Aujourd'hui";
            } else {
                // si la durée est future
                long heuresRestantes = duration.toHours();
                long minutesRestantes = duration.toMinutes() % 60;
                if (heuresRestantes > 24) {
                    // si la durée restante est supérieure à 24 heures
                    return "+" + (heuresRestantes / 24) + "j";
                } else if (heuresRestantes > 0) {
                    // si la durée restante est supérieure à 0 heures
                    return "+" + heuresRestantes + "h";
                } else {
                    // si la durée restante est inférieure à 1 heure
                    return "+" + minutesRestantes + "min";
                }
            }
        }

        return dureeRest;
    }



}
