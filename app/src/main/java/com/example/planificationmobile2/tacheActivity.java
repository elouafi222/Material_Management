package com.example.planificationmobile2;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.planificationmobile2.traitementPourProjet.DataRecievedListener;

import traitement_pour_lesTaches.afficherTache;
import traitement_pour_lesTaches.projetRf;

public class tacheActivity extends AppCompatActivity implements DataRecievedListener {
    private afficherTache lestache;
    private TextView nomproj;
    private ImageView fleche_gauche;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activyti_tache2);

       // String url = "http://192.168.1.103:5000/affichertachechef";
        String url="http://20.55.44.15:8000/affichertachechef";

        nomproj = findViewById(R.id.nomprojTache);


        this.lestache =new afficherTache(this);
        this.lestache.RecuperLesDonneJson(this,url);

        fleche_gauche=findViewById(R.id.fleche_gauche);
        fleche_gauche.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user usercourant =user.gestInstance();
                if(usercourant.getVerifierchef()==1){
                    Intent nouveau = new Intent(getApplicationContext(),monPlanning.class);
                    startActivity(nouveau);
                    finish();
                }else{
                    Intent nouveau = new Intent(getApplicationContext(),MainActivityClient.class);
                    startActivity(nouveau);
                    finish();
                }

            }
        });


    }

    @Override
    public void onDataRecieved() {

        user usercourant =user.gestInstance();
        if(usercourant.getVerifierchef()==1){
            String nomprojet =getIntent().getStringExtra("nomproj");
            nomproj.setText(nomprojet);
        }else{
            //projetRf projetcourant =projetRf.getInstance();
            nomproj.setText("Liste des Taches ");
        }

        ListView listetache =findViewById(R.id.tache);
        this.lestache.afficherEnsembletache(this,listetache);

    }
/*
    @Override
    public void onResume() {
        super.onResume();
        // Mettre à jour la liste des tâches affichées
        onDataRecieved();


    }

 */
    // onKeyBack is for back button in android
    @Override
    public void onBackPressed() {  // back button pressed in android device in previous activity
        user usercourant =user.gestInstance();
        if(usercourant.getVerifierchef()==1){
            Intent nouveau = new Intent(getApplicationContext(),monPlanning.class);
            startActivity(nouveau);
            finish();
        }else{
            Intent nouveau = new Intent(getApplicationContext(),MainActivityClient.class);
            startActivity(nouveau);
            finish();
        }


    }
}

