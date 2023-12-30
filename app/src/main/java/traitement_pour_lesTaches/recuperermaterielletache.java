package traitement_pour_lesTaches;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.planificationmobile2.MainActivityClient;
import com.example.planificationmobile2.home;
import com.example.planificationmobile2.user;

import org.json.JSONException;
import org.json.JSONObject;

public class recuperermaterielletache {
    private static String url ="http://20.55.44.15:8000/recuperermaterilletache";
    // private static String url = "http://192.168.1.103:5000/authentification";
     private static String listemeterille;
    public static String recupermaterille(Context context, String username , String password,int idtache){

            JSONObject postData = new JSONObject();
            try {
                postData.put("username", username);
                postData.put("password", password);
                postData.put("idtache",idtache);
            } catch (
                    JSONException e) {
                throw new RuntimeException(e);
            }

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,

                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                listemeterille = response.getString("materiels");

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

            return listemeterille;

        }


}
