package com.example.planificationmobile2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONObject;

public class nouveau extends AppCompatActivity {


    //String url = "http://192.168.1.105:5000/ajouterProjet";
   // String url = "http://localhost:5000/ajouterProjet"; // <<-- need to be created
    String url = "http://20.55.44.15:8000/ajouterProjet";

    TextInputLayout ProjetName , DescriptionProjet;
    DatePicker dateDebut, dateFin;

    Button btnCreerProjet, btnAnnulerProjet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar);
        setContentView(R.layout.activity_nouveau);

        ProjetName = (TextInputLayout) findViewById(R.id.ProjetName);
        DescriptionProjet = (TextInputLayout) findViewById(R.id.ProjetDescription);
        dateDebut = (DatePicker) findViewById(R.id.dateDebutPickerProjet);
        dateFin = (DatePicker) findViewById(R.id.dateFinPickerProjet);

        // set par default date debut on current date et date fin on current date + 20 days
        DatePicker currentDate = new DatePicker(this);
        dateDebut.updateDate(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth());
        dateFin.updateDate(currentDate.getYear(), currentDate.getMonth(), currentDate.getDayOfMonth() + 20);

        btnCreerProjet = (Button) findViewById(R.id.creer);
        btnAnnulerProjet = (Button) findViewById(R.id.annuler);

        btnCreerProjet.setOnClickListener(v -> creer());
        btnAnnulerProjet.setOnClickListener(v -> annuler());

        btnCreerProjet.setBackgroundColor(Color.parseColor("#007FAC"));
        btnCreerProjet.setTextColor(Color.WHITE);
        btnAnnulerProjet.setBackgroundColor(Color.parseColor("#007FAC"));
        btnAnnulerProjet.setTextColor(Color.WHITE);
    }

    /**
     * validation du form pour creer un nouveau projet
     */
    private void creer() {

        if ((validateNameProjet()==false) || (validateDescriptionProjet()==false) || (validateDebutDate()==false) || (validateFinDate()==false)) {
            System.out.println("valeur non valide ");
            return;
        }


        // make json object with value of form and send it to the server flask

        user user1 = user.gestInstance();

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String session_key = sharedPreferences.getString("session_key", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session_id", session_key);
            jsonObject.put("username", user1.getNom());
            jsonObject.put("password",user1.getPassword());
            jsonObject.put("name", ProjetName.getEditText().getText().toString().trim());
            jsonObject.put("description", DescriptionProjet.getEditText().getText().toString().trim());
            jsonObject.put("dateDeb", dateDebut.getDayOfMonth() + "/" + dateDebut.getMonth() + "/" + dateDebut.getYear() );
            jsonObject.put("dateFin", dateFin.getDayOfMonth() + "/" + dateFin.getMonth() + "/" + dateFin.getYear());
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("---------------------------------------------------------- pdkdkdkdk");

        // envoie de la requete au serveur flask

        JsonObjectRequest jonrequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("success")) {
                        Intent intent = new Intent(getApplicationContext(), monPlanning.class);
                        startActivity(intent);
                        finish();
                    } else if (message.equals("projetExiste")) {
                        Toast.makeText(getApplicationContext(), "ce nom de projet existe deja choisir un nouveau nom", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur lors de la création du projet", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            /**
             * @param error
             */
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erreur lors de la création du projet", Toast.LENGTH_SHORT).show();
            }

        });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(jonrequest);

    }


    /**
     * annuler la creation du projet
     */
    private void annuler() {
        Intent intent = new Intent(getApplicationContext(),home.class);
        startActivity(intent);
        finish();
    }

    private boolean validateNameProjet() {
        String val = ProjetName.getEditText().getText().toString().trim();
        System.out.println(val);
        if (val.isEmpty()) {
            ProjetName.setError("Le champ ne peut pas être vide");
            return false;
        }  else if (val.length() > 50 ) {
            ProjetName.setError("Nom de tache trop long");
            return false;
        }else if (val.length() < 5){
            ProjetName.setError("Nom de tache trop court");
            return false;
        } else {
            ProjetName.getEditText().setText(val);
            ProjetName.setError(null);
            ProjetName.setErrorEnabled(false);
            return true;
        }
        
    }

    private boolean validateDescriptionProjet() {
        String val = DescriptionProjet.getEditText().getText().toString().trim();
        System.out.println(val);
        if (val.isEmpty()) {
            DescriptionProjet.setError("Le champ ne peut pas être vide");
            return false;
        } else if (val.length() > 100 ) {
            DescriptionProjet.setError("Description trop long");
            return false;
        } else if (val.length() < 10) {
            DescriptionProjet.setError("Description trop court");
            return false;
        }else {
            DescriptionProjet.setError(null);
            DescriptionProjet.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateDebutDate() {

        DatePicker currentDate = new DatePicker(this);
        int currentDay = currentDate.getDayOfMonth();
        int currentMonth = currentDate.getMonth();
        int currentYear = currentDate.getYear();

        int day = dateDebut.getDayOfMonth();
        int month = dateDebut.getMonth();
        int year = dateDebut.getYear();

        if (year < currentYear) {
            Toast.makeText(getApplicationContext(), "La date de début doit être supérieur à la date actuelle", Toast.LENGTH_SHORT).show();
            return false;
        } else if (year == currentYear) {
            if (month < currentMonth) {
                Toast.makeText(getApplicationContext(), "La date de début doit être supérieur à la date actuelle", Toast.LENGTH_SHORT).show();
                return false;
            } else if (month == currentMonth) {
                if (day < currentDay) {
                    Toast.makeText(getApplicationContext(), "La date de début doit être supérieur à la date actuelle", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }

            }

        } else {
            return true;
        }
       return true; // TODO: 2021-05-03
    }

    private boolean validateFinDate() {
        if (dateFin.getYear() < dateDebut.getYear()) {
            Toast.makeText(getApplicationContext(), "La date de fin doit être supérieur à la date de début", Toast.LENGTH_SHORT).show();
            return false;
        } else if (dateFin.getYear() == dateDebut.getYear()) {
            if (dateFin.getMonth() < dateDebut.getMonth()) {
                Toast.makeText(getApplicationContext(), "La date de fin doit être supérieur à la date de début", Toast.LENGTH_SHORT).show();
                return false;
            } else if (dateFin.getMonth() == dateDebut.getMonth()) {
                if (dateFin.getDayOfMonth() < dateDebut.getDayOfMonth()) {
                    Toast.makeText(getApplicationContext(), "La date de fin doit être supérieur à la date de début", Toast.LENGTH_SHORT).show();
                    return false;
                } else {
                    return true;
                }
            }
        } else {
            return true;
        }
        return true; // TODO: 2021-05-03
    }

    // onKeyBack is for back button in android
    @Override
    public void onBackPressed() {  // back button pressed in android device in previous activity
        Intent nouveau = new Intent(getApplicationContext(),home.class);
        startActivity(nouveau);
        finish();

    }

}