package com.example.planificationmobile2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class connection {
    

     private static String url ="http://20.55.44.15:8000/";
     // private static String url = "http://192.168.1.103:5000/authentification";
    public static void get_connection(Context context, String username ,String password){

        if(username.trim().isEmpty() || password.trim().isEmpty()){
            Toast.makeText(context, "saisir un username ou un password", Toast.LENGTH_SHORT).show();
        }else {
            JSONObject postData = new JSONObject();
            try {
                postData.put("username", username);
                postData.put("password", password);
            } catch (
                    JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                String status = response.getString("status");
                                //String session_key = response.getString("session_id");
                                int ischef =response.getInt("ischef");
                                System.out.println("---------------------------"+ischef);
                                if (!status.isEmpty()) {
                                    // Connexion réussie
                                    Toast.makeText(context, "Connexion réussie ", Toast.LENGTH_SHORT).show();
                                    // Stocker les informations de l'utilisateur
                                    user u1= user.gestInstance();
                                    u1.setNom(username);
                                    u1.setPassword(password);
                                    u1.setVerifierchef(ischef);

                                    // Récupérer la clé de session et la stocker dans les SharedPreferences
                               /*
                                SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.putString("session_key", session_key);
                                editor.apply();
                                */


                                    // Changer d'activité
                                    if(ischef==1){
                                        Intent nouveau = new Intent(context.getApplicationContext(),home.class);
                                        context.startActivity(nouveau);
                                        ((Activity)context).finish();
                                    }else{
                                        Intent nouveau = new Intent(context.getApplicationContext(),MainActivityClient.class);
                                        context.startActivity(nouveau);
                                        ((Activity)context).finish();
                                    }
                                } else {
                                    // Connexion échouée

                                    Toast.makeText(context,"connection echoué", Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    // Erreur de connexion
                    Toast.makeText(context,"erreur dans la connection", Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);

        }


    }
}

