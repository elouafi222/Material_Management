package com.example.planificationmobile2.traitementPourProjet;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.planificationmobile2.R;
import com.example.planificationmobile2.home;
import com.example.planificationmobile2.tacheActivity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import traitement_pour_lesTaches.projetRf;

public class projetAdapeter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<projet> lesprojets;

    private Context context;

    public projetAdapeter(Context context,ArrayList<projet> lesprojets){
        this.context=context;
        this.lesprojets=lesprojets;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.lesprojets.size();
    }

    @Override
    public projet getItem(int position) {
        return this.lesprojets.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= this.inflater.inflate(R.layout.adapetprojet,null);
        projet projetcourant = getItem(i);
        String nom=projetcourant.getNom();
        String description =projetcourant.getDescriptionProjet();
        String etatprojet= projetcourant.getEtatProjet();
        String  dateFin ="";
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            dateFin = ""+projetcourant.getDateFin().toLocalDate();
        }

        /// injecter les informations dans le textview
        TextView nomprojet= view.findViewById(R.id.nomprojet);
        nomprojet.setText(nom);

        TextView staturprojet =view.findViewById(R.id.statutprojet);
        staturprojet.setText(etatprojet);
        //// modificationd de la couleur de status selon l'etat actuelle

        if(etatprojet.equals("en cours de execution"))
            staturprojet.setBackgroundColor(Color.YELLOW);
        else if (etatprojet.equals("terminee")) {
            staturprojet.setBackgroundColor(Color.GREEN);

        } else if (etatprojet.equals("non terminee")) {
            staturprojet.setBackgroundColor(Color.RED);
        }

        /////// pour les dates
        TextView jourdb =view.findViewById(R.id.dayprojet);
        jourdb.setText(projetcourant.recuperJourDatedb());

        TextView jour2 =view.findViewById(R.id.daynbrprojet);
        jour2.setText(projetcourant.recuperJourDatedb2());

        TextView mois =view.findViewById(R.id.month);
        mois.setText(projetcourant.recuperMoiDatedb());

        TextView anne =view.findViewById(R.id.datefinProjet);
        anne.setText(projetcourant.recuperAnneeDatedb());



        ////////////// description ///////////

        TextView itemDescription =view.findViewById(R.id.description);
        itemDescription.setText(description);


        // ImageView informationprojet = view.findViewById(R.id.informationprojet);
        ImageView planningprojet = view.findViewById(R.id.planningprojet);


        String finalDateFin = dateFin;
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupprojet popupprojet = new popupprojet(context);
                popupprojet.buils(nom,description,etatprojet, finalDateFin);

            }
        });

        planningprojet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                projetRf projetrfcourant= projetRf.getInstance();
                projetrfcourant.setIdproj(projetcourant.getIdProjet());
                //nomproj.setText(projetrfcourant.getNomProj());

                Intent nouveau = new Intent(context.getApplicationContext(),tacheActivity.class);
                nouveau.putExtra("nomproj",nom);
                context.startActivity(nouveau);
                ((Activity)context).finish();
            }
        });

        return view;
    }
}





/**
 *
 *
 * package com.example.planificationmobile2.traitementPourProjet;
 *
 * import android.content.Context;
 * import android.graphics.Color;
 * import android.view.LayoutInflater;
 * import android.view.View;
 * import android.view.ViewGroup;
 * import android.widget.BaseAdapter;
 * import android.widget.ImageView;
 * import android.widget.TextView;
 * import android.widget.Toast;
 *
 * import com.example.planificationmobile2.R;
 *
 * import java.time.LocalDate;
 * import java.time.LocalDateTime;
 * import java.util.ArrayList;
 *
 * public class projetAdapeter extends BaseAdapter {
 *     private LayoutInflater inflater;
 *     private ArrayList<projet> lesprojets;
 *
 *     private Context context;
 *
 *     public projetAdapeter(Context context,ArrayList<projet> lesprojets){
 *         this.context=context;
 *         this.lesprojets=lesprojets;
 *         this.inflater = LayoutInflater.from(context);
 *     }
 *
 *     @Override
 *     public int getCount() {
 *         return this.lesprojets.size();
 *     }
 *
 *     @Override
 *     public projet getItem(int position) {
 *         return this.lesprojets.get(position);
 *     }
 *
 *     @Override
 *     public long getItemId(int i) {
 *         return 0;
 *     }
 *
 *     @Override
 *     public View getView(int i, View view, ViewGroup viewGroup) {
 *         view= this.inflater.inflate(R.layout.adapetprojet,null);
 *         projet projetcourant = getItem(i);
 *         String nom=projetcourant.getNom();
 *         String description =projetcourant.getDescriptionProjet();
 *         String etatprojet= projetcourant.getEtatProjet();
 *         String  dateFin = projetcourant.dureRestant();
 *
 *         /// injecter les informations dans le textview
 *
 *         TextView nomprojet= view.findViewById(R.id.nomprojet);
 *         nomprojet.setText(nom);
 *
 *         TextView staturprojet =view.findViewById(R.id.statutprojet);
 *         staturprojet.setText(etatprojet);
 *         staturprojet.setBackgroundColor(Color.YELLOW);
 *         //// modificationd de la valeur de status selon l'etat actuelle
 *         if(dateFin.equals("en cours de execution"))
 *             staturprojet.setBackgroundColor(Color.YELLOW);
 *         else if (dateFin.equals("terminee")) {
 *             staturprojet.setBackgroundColor(Color.GREEN);
 *
 *         } else if (dateFin.equals("non terminee")) {
 *             staturprojet.setBackgroundColor(Color.RED);
 *         }
 *
 *
 *         ///////////////// modification de la valeur de la date fin selon la valeur actuelle
 *
 *         TextView datefinprojet =view.findViewById(R.id.datefinProjet);
 *         datefinprojet.setText(dateFin);
 *
 *         if(dateFin.equals("Aujourd'hui"))
 *             datefinprojet.setBackgroundColor(Color.parseColor("#FFA500"));
 *         else if (dateFin.contains("+")) {
 *             datefinprojet.setBackgroundColor(Color.GREEN);
 *         } else  {
 *             datefinprojet.setBackgroundColor(Color.RED);
 *         }
 *
 *         // ImageView informationprojet = view.findViewById(R.id.informationprojet);
 *         ImageView planningprojet = view.findViewById(R.id.planningprojet);
 *
 *
 *         view.setOnClickListener(new View.OnClickListener() {
 *             @Override
 *             public void onClick(View view) {
 *               popupprojet popupprojet = new popupprojet(context);
 *               popupprojet.buils(nom,description,etatprojet,dateFin);
 *
 *             }
 *         });
 *
 *         planningprojet.setOnClickListener(new View.OnClickListener() {
 *             @Override
 *             public void onClick(View view) {
 *                 Toast.makeText(context, "Affichage planning projet", Toast.LENGTH_SHORT).show();
 *             }
 *         });
 *
 *         return view;
 *     }
 * }
 */
