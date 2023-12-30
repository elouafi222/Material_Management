package com.example.planificationmobile2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.planificationmobile2.MainActivity;
import com.example.planificationmobile2.R;
import com.example.planificationmobile2.monPlanning;
import com.example.planificationmobile2.traitementpourGraph.graph;
import com.example.planificationmobile2.visualisation;
import com.github.mikephil.charting.charts.BarChart;
import com.example.planificationmobile2.traitementpourGraph.graph;

public class analyser extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    //private static String url="http://192.168.1.103:5000/afficherGraph1";
   // private static String url="http://192.168.42.122:5000/afficherGraph1";
    private static String url="http://20.55.44.15:8000/afficherGraph1";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analyser);

        Spinner spinner = findViewById(R.id.spinneranalyse);
        ArrayAdapter<CharSequence> adapter =ArrayAdapter.createFromResource(this, R.array.choisirgrapht, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        String choix=  adapterView.getItemAtPosition(i).toString();

        BarChart barChart = findViewById(R.id.chart);
        graph graphaff =new graph();
        graphaff.afficherGraph(this,url,barChart,choix);

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

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
                Intent nouveau2 = new Intent(getApplicationContext(), monPlanning.class);
                startActivity(nouveau2);
                finish();
                return true;
            case R.id.itemNouveau:
                Intent nouveau3 = new Intent(getApplicationContext(), com.example.planificationmobile2.nouveau.class);
                startActivity(nouveau3);
                finish();
                return true;
            case R.id.itemvisualisation:
                Intent nouveau4 = new Intent(getApplicationContext(), visualisation.class);
                startActivity(nouveau4);
                finish();
                return true;
            case R.id.itemDeconnection:
                Intent nouveau5 = new Intent(getApplicationContext(), MainActivity.class);
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