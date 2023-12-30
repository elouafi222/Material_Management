package com.example.planificationmobile2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ListView;

import com.example.planificationmobile2.traitementPourProjet.DataRecievedListener;
import com.example.planificationmobile2.traitementPourProjet.afficherProjet;
import com.example.planificationmobile2.traitementPourProjet.DataRecievedListener;

public class monPlanning extends AppCompatActivity implements DataRecievedListener {
    private afficherProjet lesprojet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setTheme(R.style.PlanningTheme);
        setContentView(R.layout.activity_mon_planning);

        //String url = "http://192.168.1.103:5000/afficherProjet";
        //String url = "http://192.168.42.122:5000/afficherProjet";
        String url ="http://20.55.44.15:8000/afficherProjet";
        //  user u1 = user.gestInstance();

        lesprojet = new afficherProjet(this);
        lesprojet.RecuperLesDonneJson(this,url);


    }

    @Override
    public void onDataRecieved() {

        ListView listprojet = findViewById(R.id.projet);
        lesprojet.afficherEnsembleProjet(this, listprojet);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.exemple, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.itemAnalyse:
                Intent nouveau = new Intent(getApplicationContext(),analyser.class);
                startActivity(nouveau);
                finish();
                return true;
            case R.id.itemMonPlanning:
                Intent nouveau2 = new Intent(getApplicationContext(),monPlanning.class);
                startActivity(nouveau2);
                finish();
                return true;
            case R.id.itemNouveau:
                Intent nouveau3 = new Intent(getApplicationContext(), com.example.planificationmobile2.nouveau.class);
                startActivity(nouveau3);
                finish();
                return true;
            case R.id.itemvisualisation:
                Intent nouveau4 = new Intent(getApplicationContext(),visualisation.class);
                startActivity(nouveau4);
                finish();
                return true;
            case R.id.itemDeconnection:
                Intent nouveau5 = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(nouveau5);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    // onKeyBack is for back button in android
    @Override
    public void onBackPressed() {  // back button pressed in android device in previous activity
        Intent nouveau = new Intent(getApplicationContext(),home.class);
        startActivity(nouveau);
        finish();

    }


}


/*
    JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(url, new Response.Listener<JSONArray>() {
        @Override
        public void onResponse(JSONArray response) {
            try {
                JSONObject jsonObject = response.getJSONObject(0);
                int userId = jsonObject.getInt("colonne1");
                String id = jsonObject.getString("colonne2");
                String title = jsonObject.getString("colonne3");
                tv.setText(userId+"\n"+id+"\n"+title+"\n");
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }, new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            tv.setText("error "+error.getMessage());
            System.out.println(error.getMessage());
        }
    });

    //String url = "http://10.0.2.2:5000/materiel";

 */