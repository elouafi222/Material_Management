package com.example.planificationmobile2.traitementPourProjet;

import android.content.Context;
import android.os.Build;
import android.widget.ListView;
import android.widget.Toast;
import android.content.SharedPreferences;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.planificationmobile2.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class afficherProjet {
    private  ArrayList<projet> ensembleProjet;
    private DataRecievedListener listener;


    public afficherProjet(DataRecievedListener listener) {
        this.ensembleProjet= new ArrayList<>();
        this.listener=listener;

    }


    public void RecuperLesDonneJson(Context context, String url){

        // recuperer la valeur de la cle et onvoyee dans la requete

        /*
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String session_key = sharedPreferences.getString("session_key", "");
        System.out.println(session_key);
        */
        user usercourant = user.gestInstance();

        JSONArray donnerenvoyer= new JSONArray();
        JSONObject postData = new JSONObject();
        try {
            postData.put("username",usercourant.getNom());
            postData.put("password",usercourant.getPassword());
            donnerenvoyer.put(postData);
            System.out.println("-------------------------------"+usercourant.getNom()+"---------------------------"+usercourant.getPassword());
        } catch (JSONException e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        /// envoier la requete vers le serveur

        JsonArrayRequest jonrequest =new JsonArrayRequest(Request.Method.POST, url,donnerenvoyer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        //  ensembleProjet.add(new projet(obj.getString("idprojet"),obj.getString("nomprojet"),obj.getString("description"),obj.getString("dateDebut"),obj.getString("chefProjet"),obj.getString("etatProjet")));
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                            ensembleProjet.add(new projet(obj.getString("IDPROJ"),obj.getString("NOMPROJ"),obj.getString("DESCRIPTION"),LocalDateTime.parse(obj.getString("DATEDEB"),formatter),LocalDateTime.parse(obj.getString("DateFin"),formatter),obj.getString("PN"),obj.getString("ETATPROJ")));
                        }
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                listener.onDataRecieved();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.getMessage();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jonrequest);

    }

    public void afficherEnsembleProjet(Context context,ListView toutProjet ) {
        if (ensembleProjet.size() > 0) {
            toutProjet.setAdapter(new projetAdapeter(context, ensembleProjet));
        } else {
            // Affichage d'un message si la liste est vide
            Toast.makeText(context, "Aucun projet n'a été trouvé", Toast.LENGTH_SHORT).show();
        }



    }

}

