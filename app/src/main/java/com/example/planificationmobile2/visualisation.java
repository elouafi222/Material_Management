package com.example.planificationmobile2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.services.events.TimeStamp;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.datepicker.DayViewDecorator;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.shrikanthravi.collapsiblecalendarview.data.Day;
import com.shrikanthravi.collapsiblecalendarview.widget.CollapsibleCalendar;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class visualisation extends AppCompatActivity {

    List<Task> list;
    List<Task> Templist;
    private String urlTache;

    private CollapsibleCalendar collapsibleCalendar;
    private RecyclerView recyclerView;

    private FloatingActionButton fab;

    MyAdabterRecycleTask myAdabterRecycleTask;

    JSONArray Mytache;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_visualisation);

        list = new ArrayList<>();
        Templist = new ArrayList<>();
       // urlTache = "http://20.55.44.15:8000/getTaches";
        urlTache="http://20.55.44.15:8000/getTachesdate";

        recyclerView = findViewById(R.id.recyclerView);

        collapsibleCalendar = findViewById(R.id.calendarView);



        myAdabterRecycleTask = new MyAdabterRecycleTask(getApplicationContext(),Templist);

        // button + to create new task
        fab = findViewById(R.id.creetache);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent nouveau = new Intent(getApplicationContext(),tache.class);
                startActivity(nouveau);
                finish();
            }
        });


        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(myAdabterRecycleTask);
        // make json object with value of form and send it to the server flask

         
        user user1 = user.gestInstance();

        SharedPreferences sharedPreferences = this.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String session_key = sharedPreferences.getString("session_key", "");

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("session_id", session_key);
            jsonObject.put("username", user1.getNom());
            jsonObject.put("password", user1.getPassword());
        } catch (JSONException e) {
           e.printStackTrace();
        }
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

        // Define a listener to be notified when the request is completed
        RequestCompletedListener requestCompletedListener = new RequestCompletedListener() {
            @Override
            public void onRequestCompleted() throws JSONException, ParseException {
                // The request has completed, do something here

                DatePicker datePicker = new DatePicker(getApplicationContext());
                for (int i = 0; i < Mytache.length(); i++) {
                    JSONObject tache = Mytache.getJSONObject(i);

                    SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss z", Locale.US);
                    Date parsedDate = dateFormat.parse(tache.getString("DATE_CREATION"));
                    Calendar date = Calendar.getInstance();
                    date.setTime(parsedDate);

                    // ajouter le tache on list de Task pour afficher dans le recycler view
                    String Description = tache.getString("DECRIPTION") +
                            "\n Date de fin : " + tache.getString("DATE_ECHEANCE") +
                            "\n De Project : " + tache.getString("nomproj") +
                            "\n Pour User : " + tache.getString("nompersonne");

                    list.add(new Task(tache.getString("NOMT"),Description , date));
                    collapsibleCalendar.addEventTag(date.get(Calendar.YEAR), date.get(Calendar.MONTH), date.get(Calendar.DAY_OF_MONTH), Color.parseColor("#FF4081"));



                    if ( ( date.get(Calendar.DAY_OF_MONTH) == datePicker.getDayOfMonth() )
                            && ( date.get(Calendar.MONTH) == datePicker.getMonth() )
                            && ( date.get(Calendar.YEAR) == datePicker.getYear() ) ){
                        Task task=new Task(tache.getString("NOMT"), tache.getString("DECRIPTION"), date);
                        System.out.println(task);
                        Templist.add(task);
                        // sort with dueDate of Task using getDueDate
                        Collections.sort(Templist);

                        myAdabterRecycleTask.notifyDataSetChanged();
                    }


                }
                // show list of task in current day
                //recyclerView.setAdapter(new MyAdabterRecycleTask(getApplicationContext() ,Templist));

            }
        };

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, urlTache, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String status = response.getString("status");
                    if (status.equals("success")) {

                        // ajouet le tache on list de Task
                        Mytache = response.getJSONArray("taches");

                        // Notify the listener that the request has completed
                        requestCompletedListener.onRequestCompleted();


                    } else {
                        Toast.makeText(getApplicationContext(), "error de server de la base donner", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (ParseException e) {
                    e.printStackTrace();
                }


            }


        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erreur de connexion : "+error.getMessage(), Toast.LENGTH_SHORT).show();
            }

        });




        requestQueue.add(jsonObjectRequest);




         /*
        // test list

        for (int i = 0; i < 10; i++) {
            list.add(new Task("task"+i,"Description"+i,Calendar.getInstance() ));
            Templist.add(new Task("task"+i,"Description"+i,Calendar.getInstance() ));
            collapsibleCalendar.addEventTag(Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH), Color.parseColor("#FF4081"));

        }
        Calendar calendar = Calendar.getInstance();
        calendar.getInstance().set(2023,3,12,6,30);
        for (int i = 10; i < 20; i++) {
            list.add(new Task("task"+i,"Description"+i,calendar )); // month start from 0 to 11
            collapsibleCalendar.addEventTag(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH), Color.parseColor("#FF4081"));

        }

        recyclerView.setAdapter(new MyAdabterRecycleTask(getApplicationContext() ,Templist));

         */

        collapsibleCalendar.setCalendarListener(new CollapsibleCalendar.CalendarListener() {
            /**
             *
             */
            @Override
            public void onDayChanged() {

            }

            /**
             *
             */
            @Override
            public void onClickListener() {

            }

            @Override
            public void onDaySelect() {
                collapsibleCalendar.getSelectedItem();
                Day day = collapsibleCalendar.getSelectedDay();
                Log.i(getClass().getName(), "Selected Day: "
                        + day.getYear() + "/" + (day.getMonth() + 1) + "/" + day.getDay());
                // add task below calendar recycler view
                Templist.clear(); // clear list

                for (int i = 0; i < list.size(); i++) {
                    Task tache = list.get(i);
                    Calendar date = tache.getDueDate();
                    if( ( day.getDay() == date.get(Calendar.DAY_OF_MONTH) ) && ( day.getMonth() == date.get(Calendar.MONTH) ) && ( day.getYear() == date.get(Calendar.YEAR) ) ) {
                        Templist.add(new Task(tache.getTitle(), tache.getDescription(), date));
                        Collections.sort(Templist);
                        myAdabterRecycleTask.notifyDataSetChanged();
                    }

                }

                // show list of task in current day
                System.out.println("---------------------------templist----------------------------------------");

                //recyclerView.setAdapter(new MyAdabterRecycleTask(getApplicationContext() ,Templist));
            }



            @Override
            public void onItemClick(View view) {

            }

            @Override
            public void onDataUpdate() {

            }

            @Override
            public void onMonthChange() {

            }

            @Override
            public void onWeekChange(int i) {

            }
        });
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
                user.removeUser();
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

// Define the RequestCompletedListener interface
interface RequestCompletedListener {
    void onRequestCompleted() throws JSONException, ParseException;
}