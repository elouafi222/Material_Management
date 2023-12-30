package traitement_pour_lesTaches;
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
import com.example.planificationmobile2.traitementPourProjet.DataRecievedListener;
import com.example.planificationmobile2.traitementPourProjet.projet;
import com.example.planificationmobile2.traitementPourProjet.projetAdapeter;
import com.example.planificationmobile2.user;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

public class afficherTache {
    private  ArrayList<tache> ensembleTache;
    private DataRecievedListener listener;


    public afficherTache(DataRecievedListener listener) {
        this.ensembleTache= new ArrayList<>();
        this.listener=listener;

    }
    public void RecuperLesDonneJson(Context context, String url){

        // recuperer la valeur de la cle et onvoyee dans la requete

        /*
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String session_key = sharedPreferences.getString("session_key", "");
        */

        JSONArray donnerenvoyer= new JSONArray();
        JSONObject postData = new JSONObject();
        projetRf projetcourant = projetRf.getInstance();

        ///////// recuperer le user acutelle
        user usercourant = user.gestInstance();

        try {
            postData.put("username",usercourant.getNom());
            postData.put("password",usercourant.getPassword());
            postData.put("idproj",projetcourant.getidproj());
            postData.put("ischef",usercourant.getVerifierchef());
            donnerenvoyer.put(postData);
        } catch (
                JSONException e) {
            throw new RuntimeException(e);
        }


        /// envoier la requete vers le serveur

        JsonArrayRequest jonrequest =new JsonArrayRequest(Request.Method.POST, url,donnerenvoyer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss z", Locale.ENGLISH);
                            LocalDateTime datefin=null;
                            if (!obj.isNull("DATE_ECHEANCE")) {
                                datefin = LocalDateTime.parse(obj.getString("DATE_ECHEANCE"), formatter);
                            }
                            int score=0;
                            if(!obj.isNull("SCORE"))
                                score= obj.getInt("SCORE");

                            String nomtache=obj.getString("nomtache");
                            String nomproj =obj.getString("nomproj");
                            String chef=obj.getString("chef");
                            projetcourant.setNomProj(nomproj);
                            System.out.println("----------------------------------------------------"+nomtache);
                            ensembleTache.add(new tache(nomtache,obj.getInt("IDTACHE"),LocalDateTime.parse(obj.getString("DATE_CREATION"),formatter),datefin,LocalDateTime.parse(obj.getString("DUREE_ESTIMEE"),formatter),obj.getString("ETATT"),obj.getString("DECRIPTION"),score,obj.getInt("IDPROJ"),obj.getString("NOMEMP"),nomproj,chef));

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

    public void afficherEnsembletache(Context context,ListView toutProjet ) {
        if (ensembleTache.size() > 0) {
            toutProjet.setAdapter(new tacheAdapter(context, ensembleTache));
        } else {
            // Affichage d'un message si la liste est vide
            Toast.makeText(context, "Aucun projet n'a été trouvé", Toast.LENGTH_SHORT).show();
        }

    }


}


