package com.example.planificationmobile2.traitementpourGraph;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.planificationmobile2.user;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.TreeMap;

public class graph {
     private TreeMap<String,BarEntry> entries;
    int[] colors ;

    public graph(){
         entries = new TreeMap<>();
         colors = new int[] {Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.CYAN};
     }

    public void afficherGraph(Context context, String url,BarChart barChart,String reponce){

        /* récupérer la valeur de la clé et l'envoyer dans la requête */
        /*
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String session_key = sharedPreferences.getString("session_key", "");

         */

        JSONArray donnerenvoyer= new JSONArray();
        JSONObject postData = new JSONObject();
        user username = user.gestInstance();
        try {
            postData.put("username",username.getNom());
            postData.put("password",username.getPassword());
            postData.put("query",reponce);
            donnerenvoyer.put(postData);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }

        /// envoyer la requête vers le serveur
        JsonArrayRequest jonrequest =new JsonArrayRequest(Request.Method.POST, url,donnerenvoyer, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
            //    TreeMap<String,BarEntry> entries = new TreeMap<>();
                for(int i=0;i<response.length();i++){
                    try {
                        JSONObject obj = response.getJSONObject(i);
                        entries.put(obj.getString("nomemp"),new BarEntry(i,obj.getInt("score")));
                    }catch (JSONException e){
                        e.printStackTrace();
                    }
                }
                ArrayList<String> labels = new ArrayList<>(entries.keySet()); // nom des personnes
                ArrayList<BarEntry> lesEntree = new ArrayList<>(entries.values());

                String titre="";
                if(reponce.equals("difference"))
                    titre="Graphique de productivité";
                else
                    titre="Graphique de score";

                BarDataSet barDataSet = new BarDataSet(lesEntree, titre);
                barDataSet.setLabel(titre);
                barDataSet.setColors(colors);

                BarData barData = new BarData(barDataSet);
                barChart.setData(barData);

                barChart.setFitBars(true);
                barChart.getDescription().setEnabled(false);
                barChart.getDescription().setText(titre);
                barChart.getXAxis().setValueFormatter(new IndexAxisValueFormatter(labels));
                barChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
                barChart.getXAxis().setGranularity(1f);
                barChart.getAxisRight().setEnabled(false);
                barChart.getLegend().setEnabled(false);
                barChart.animateY(1000);
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
}
