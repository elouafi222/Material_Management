package com.example.planificationmobile2.traitementPourProjet;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.planificationmobile2.R;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Stack;

public class popupprojet extends Dialog {
    private TextView nomprojet;
    private TextView descriptionprojet;
    private TextView statueprojet;
    private TextView dateFin;
    private Button butonok;

    public popupprojet(Context activity){
        super(activity, androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popus_afficherprojet);
        this.nomprojet=findViewById(R.id.popusNomprojet);
        this.descriptionprojet=findViewById(R.id.popusdescriptionprojetreponce);
        this.dateFin=findViewById(R.id.datefinproejtvaleur);

    }

   public void buils(String nom, String descriptionprojet, String statueprojet, String dateFin) {
        show();
       setnomprojet(nom);
       setDescriptionprojet(descriptionprojet);
      // setStatueprojet(statueprojet);
       setDateFin(""+dateFin);
   }

    public void setnomprojet(String nom){
        this.nomprojet.setText(nom);
    }

    public void setDescriptionprojet(String descriptionprojet){
        this.descriptionprojet.setText(descriptionprojet);
    }

    public void setStatueprojet(String statueprojet){
        this.statueprojet.setText(statueprojet);
    }
    public void setDateFin(String dateFin){
        this.dateFin.setText(dateFin);
    }

    public Button getButonok(){
        return  this.butonok;
    }
}
