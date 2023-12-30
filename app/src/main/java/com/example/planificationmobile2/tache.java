package com.example.planificationmobile2;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;

import com.android.volley.toolbox.Volley;
import com.example.planificationmobile2.traitementPourProjet.DataRecievedListener;
import com.example.planificationmobile2.traitementPourProjet.afficherProjet;
import com.example.planificationmobile2.traitementPourProjet.DataRecievedListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class tache extends AppCompatActivity {

    static private String urlProjet = "http://20.55.44.15:8000/afficherProjetDeTache";
    static private String urlPersonne = "http://20.55.44.15:8000/affichePersonner";
    static private String urlMatreriel = "http://20.55.44.15:8000/afficheMateriel";
    static private String urlTache = "http://20.55.44.15:8000/ajouterTache";


   // static private String urlProjet = "http://localhost:5000/afficherProjetDeTache";
   // static private String urlPersonne = "http://localhost:5000/affichePersonner/";
  //  static private String urlMatreriel = "http://localhost:5000/afficheMateriel/";
  //  static private String urlTache = "http://localhost:5000/ajouterTache";


    String[] personnes ;

    boolean[] selectedPersonne;
    ArrayList<Integer> PersonneArray = new ArrayList<>();
    String[] projets ;

    String[] materiels;

    boolean[] selectedMateriel;
    ArrayList<Integer> MaterielArray = new ArrayList<>();

    private Calendar selectedDate;
    private Calendar selectedTime;

    TextInputLayout nomTache;
    TextInputLayout descriptionTache;

    AutoCompleteTextView listprojet ;
    AutoCompleteTextView dateEstimation;

    AutoCompleteTextView listPersonne;

    AutoCompleteTextView listMateriel;

    Button btnAjouterTache,btnAnnulerTache;

    ArrayAdapter<String> adapter;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tache);

        nomTache = findViewById(R.id.tacheName);
        descriptionTache = findViewById(R.id.tacheDescription);

        listPersonne = findViewById(R.id.list_personne_tache);

        selectedDate = Calendar.getInstance();
        selectedTime = Calendar.getInstance();

        listprojet = findViewById(R.id.listProjet);


        dateEstimation = findViewById(R.id.dateestimation);

        listMateriel = findViewById(R.id.list_materiel);

        btnAjouterTache = findViewById(R.id.creertacheform);
        btnAnnulerTache = findViewById(R.id.annulertacheform);



        btnAnnulerTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent annulerActiviter = new Intent(getApplicationContext(),visualisation.class);
                startActivity(annulerActiviter);
                finish();
            }
        });


        // test validation de la tache then send json to server flask

        btnAjouterTache.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!validateNameTache() | !validateDescription() | !validateDate() | !validateProjet() | !validateMateriel() | !validatePersonne()) {
                    return;
                }
                String nomTache = tache.this.nomTache.getEditText().getText().toString();
                String descriptionTache = tache.this.descriptionTache.getEditText().getText().toString();
                String dateEstimation = tache.this.dateEstimation.getText().toString();
                String listPersonne = tache.this.listPersonne.getText().toString();
                String listMateriel = tache.this.listMateriel.getText().toString(); // sous forme de string separer par des virgules
                String listprojet = tache.this.listprojet.getText().toString();

                Toast.makeText(tache.this,"Tache " + nomTache + " " + descriptionTache + " " + dateEstimation + " " + listPersonne + " " + listMateriel + " " + listprojet, Toast.LENGTH_SHORT).show();
                System.out.println("-------------"+dateEstimation);

                // send json to server flask

                user user1 = user.gestInstance();

                JSONObject jsonTache = new JSONObject();
                {
                    try {
                        jsonTache.put("username",user1.getNom());
                        jsonTache.put("password",user1.getPassword());
                        jsonTache.put("nomTache", nomTache);
                        jsonTache.put("descriptionTache", descriptionTache);
                        jsonTache.put("dateEstimation", dateEstimation);
                        jsonTache.put("listPersonne", listPersonne);
                        jsonTache.put("listMateriel", listMateriel);
                        jsonTache.put("listprojet", listprojet);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                JsonObjectRequest jonrequest = new JsonObjectRequest(Request.Method.POST, urlTache, jsonTache, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            String message = response.getString("message");
                            System.out.println("-------------------------------------------------------------"+message);
                            if (message.equals("success")) {
                                Toast.makeText(tache.this, "Tache ajouter avec succes", Toast.LENGTH_SHORT).show();
                                Intent myActiviter = new Intent(getApplicationContext(),visualisation.class);
                                startActivity(myActiviter);
                                finish();
                            } else if (message.equals("tacheExiste")) {
                                Toast.makeText(getApplicationContext(), "ce nom de projet existe choisir un nouveau nom", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "Erreur lors de la création du la tache", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(tache.this, "Erreur lors de l'ajout de la tache", Toast.LENGTH_SHORT).show();
                    }
                });

                // Instantiate the RequestQueue.
                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());

                // Add the request to the RequestQueue.
                queue.add(jonrequest);

                
            }
        });





        // test
        // list du projet

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        
            SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
            String session_key = sharedPreferences.getString("session_key", "");

            user user1 = user.gestInstance();

            JSONObject jsonprojet = new JSONObject();
            
                try {
                    jsonprojet.put("session_key", session_key);
                    jsonprojet.put("username",user1.getNom());
                    jsonprojet.put("password",user1.getPassword());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            

            JsonObjectRequest jonrequest = new JsonObjectRequest(Request.Method.POST, urlProjet, jsonprojet, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        String message = response.getString("message");
                        if (message.equals("success")) {
                            // add list name projet to projets
                            projets = new String[response.getJSONArray("projets").length()];
                            for (int i = 0; i < response.getJSONArray("projets").length(); i++) {
                                JSONObject projet = response.getJSONArray("projets").getJSONObject(i);
                                String name = projet.getString("NOMPROJ");
                                projets[i] = name;
                            }

                            adapter = new ArrayAdapter<String>(getApplicationContext(), R.layout.listprojet, projets);

                            listprojet.setAdapter(adapter);

                        } else {
                            Toast.makeText(getApplicationContext(), "Erreur a la base donner", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(), "Erreur de projet", Toast.LENGTH_SHORT).show();
                }

            });

            requestQueue.add(jonrequest);
        

        listprojet.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            // code in {...} will be executed when the user clicks on an item from the list
            /* 
            {
                projets = new String[20];
                for (int i = 0; i < 20;i++)
                    projets[i] = "Projet " + i;
                adapter = new ArrayAdapter<>(getApplicationContext(), R.layout.listprojet, projets);
                listprojet.setAdapter(adapter);
            }
            */
            

            
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String var_projet = adapterView.getItemAtPosition(i).toString();
                Toast.makeText(tache.this,"item " + var_projet, Toast.LENGTH_SHORT).show();
            }
        });
        
        //----------------- list du checkbox Personne
        //AutoCompleteTextView listPersonne;
        //String[] personnes ;
        //boolean[] selectedPersonne;
        //ArrayList<Integer> PersonneArray = new ArrayList<>();
        /* 
        // test
        personnes = new String[20];
        for (int i = 0; i < 20;i++)
            personnes[i] = "Personne " + i;

        */

        /*
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String session_key = sharedPreferences.getString("session_key", "");

         */
//        user user1 = user.gestInstance();

        JSONObject jsonObject = new JSONObject();
        
            try {
                jsonObject.put("session_key", session_key);
                jsonObject.put("username",user1.getNom());
                jsonObject.put("password",user1.getPassword());
            } catch (Exception e) {
                e.printStackTrace();
            }
        

        JsonObjectRequest jonrequestPersonne = new JsonObjectRequest(Request.Method.POST, urlPersonne, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("success")) {
                        // add list name projet to projets
                        personnes = new String[response.getJSONArray("personnes").length()];
                        for (int i = 0; i < response.getJSONArray("personnes").length(); i++) {
                            JSONObject personne = response.getJSONArray("personnes").getJSONObject(i);
                            String name = personne.getString("NOMP");
                            personnes[i] = name;
                            System.out.println(name);
                        }
                        selectedPersonne = new boolean[personnes.length];
                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur a la base donner", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erreur de flask server", Toast.LENGTH_SHORT).show();
            }

        });
        


        //
        requestQueue.add(jonrequestPersonne);



         
 

        listPersonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inisialiaze alert dialod
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        view.getContext()
                );

                // Set title
                builder.setTitle("Select Personnes");
                // set builder non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(personnes, selectedPersonne, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        selectedPersonne[i] = b;
                        if (b){
                            // when checkbox selected
                            // add position in personne Array
                            PersonneArray.add(i);
                            Collections.sort(PersonneArray);
                        }else {
                            // when checkbox unselected
                            // remove position from personne Array
                            for (int j = 0 ; j < PersonneArray.size(); j++)
                                if (i == PersonneArray.get(j))
                                    PersonneArray.remove(j);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // inisialize string buidler
                        StringBuilder stringBuilder = new StringBuilder();

                        for(int j = 0;  j<PersonneArray.size() ; j++){
                            stringBuilder.append(personnes[PersonneArray.get(j)]);
                            // Check condition
                            if (j != PersonneArray.size()-1){
                                // when j value not equal to personne array size -1
                                // add comma
                                stringBuilder.append(", ");
                            }

                        }
                        // Set text on AutoCompleteTextView

                        listPersonne.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Tout Effacer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j= 0 ; j < selectedPersonne.length ; j++){

                            selectedPersonne[j] = false;
                            PersonneArray.clear();
                            listPersonne.setText("");
                        }
                    }
                });
                // show dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });



        //----------- list of checkbox Materiels
        //AutoCompleteTextView listMateriel;
        //String[] materiels ;
        //boolean[] selectedMateriel;
        //ArrayList<Integer> MaterielArray = new ArrayList<>();
        // test
        /* 
        materiels = new String[20];
        for (int i = 0; i < 20;i++)
            materiels[i] = "materiel " + i;

        */
        
        JsonObjectRequest jonrequestMateriel = new JsonObjectRequest(Request.Method.POST, urlMatreriel, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    String message = response.getString("message");
                    if (message.equals("success")) {
                        // add list name projet to projets
                        materiels = new String[response.getJSONArray("materiels").length()];
                        for (int i = 0; i < response.getJSONArray("materiels").length(); i++) {
                            JSONObject materiel = response.getJSONArray("materiels").getJSONObject(i);
                            String name = materiel.getString("NOMM");
                            materiels[i] = name;
                        }
                        selectedMateriel = new boolean[materiels.length];

                    } else {
                        Toast.makeText(getApplicationContext(), "Erreur a la base donner", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Erreur de flask server", Toast.LENGTH_SHORT).show();
            }

        });

        requestQueue.add(jonrequestMateriel);
        



        listMateriel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Inisialiaze alert dialod
                AlertDialog.Builder builder = new AlertDialog.Builder(
                        view.getContext()
                );

                // Set title
                builder.setTitle("Select materiels");
                // set builder non cancelable
                builder.setCancelable(false);

                builder.setMultiChoiceItems(materiels, selectedMateriel, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i, boolean b) {
                        // check condition
                        selectedMateriel[i] = b;
                        if (b){
                            // when checkbox selected
                            // add position in personne Array
                            MaterielArray.add(i);
                            Collections.sort(MaterielArray);
                        }else {
                            // when checkbox unselected
                            // remove position from personne Array
                            for (int j = 0 ; j < MaterielArray.size(); j++)
                                if (i == MaterielArray.get(j))
                                    MaterielArray.remove(j);
                        }
                    }
                });

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        // inisialize string buidler
                        StringBuilder stringBuilder = new StringBuilder();

                        for(int j = 0;  j<MaterielArray.size() ; j++){
                            stringBuilder.append(materiels[MaterielArray.get(j)]);
                            // Check condition
                            if (j != MaterielArray.size()-1){
                                // when j value not equal to personne array size -1
                                // add comma
                                stringBuilder.append(", ");
                            }

                        }
                        // Set text on AutoCompleteTextView

                        listMateriel.setText(stringBuilder.toString());
                    }
                });

                builder.setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.setNeutralButton("Tout Effacer", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        for (int j= 0 ; j < selectedMateriel.length ; j++){

                            selectedMateriel[j] = false;
                            MaterielArray.clear();
                            listMateriel.setText("");
                        }
                    }
                });
                // show dialog
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        // date estimation
        dateEstimation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Inflate the custom dialog layout
                View dialogView = LayoutInflater.from(getContext()).inflate(R.layout.date_time_picker_dialog, null);

                // Get references to the date and time pickers
                DatePicker datePicker = dialogView.findViewById(R.id.date_picker);
                TimePicker timePicker = dialogView.findViewById(R.id.time_picker);

                // Set the default date and time on the pickers
                datePicker.init(selectedDate.get(Calendar.YEAR), selectedDate.get(Calendar.MONTH), selectedDate.get(Calendar.DAY_OF_MONTH), null);
                timePicker.setIs24HourView(false);

                //timePicker.setCurrentHour(selectedTime.get(Calendar.HOUR_OF_DAY));
                timePicker.setHour(selectedTime.get(Calendar.HOUR_OF_DAY));
                //timePicker.setCurrentMinute(selectedTime.get(Calendar.MINUTE));
                timePicker.setMinute(selectedTime.get(Calendar.MINUTE));

                // Create the dialog and show it
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setView(dialogView);
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Get the selected date and time from the pickers and update the selectedDate and selectedTime variables
                        selectedDate.set(Calendar.YEAR, datePicker.getYear());
                        selectedDate.set(Calendar.MONTH, datePicker.getMonth());
                        selectedDate.set(Calendar.DAY_OF_MONTH, datePicker.getDayOfMonth());
                        //selectedTime.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                        selectedTime.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        //selectedTime.set(Calendar.MINUTE, timePicker.getCurrentMinute());
                        selectedTime.set(Calendar.MINUTE, timePicker.getMinute());

                        // Update the text view with the selected date and time

                        dateEstimation.setText(String.format("%02d/%02d/%04d %02d:%02d",
                                selectedDate.get(Calendar.DAY_OF_MONTH),
                                selectedDate.get(Calendar.MONTH) + 1,
                                selectedDate.get(Calendar.YEAR),
                                selectedTime.get(Calendar.HOUR_OF_DAY),
                                selectedTime.get(Calendar.MINUTE)));
                    }
                });
                builder.setNegativeButton("Cancel", null); // Do nothing on cancel
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });


    }

    private Context getContext() {
        return this;
    }


    // onKeyBack is for back button in android
    @Override
    public void onBackPressed() {  // back button pressed in android device in previous activity
        Intent nouveau = new Intent(getApplicationContext(),visualisation.class);
        startActivity(nouveau);
        finish();

    }


    // validation

    // name tache validation
    private boolean validateNameTache() {
        String nameTacheInput = nomTache.getEditText().getText().toString().trim();

        if (nameTacheInput.isEmpty()) {
            nomTache.setError("Champ obligatoire");
            return false;
        } else if (nameTacheInput.length() > 50 ) {
            nomTache.setError("Nom de tache trop long");
            return false;
        }else if (nameTacheInput.length() < 5){
            nomTache.setError("Nom de tache trop court");
            return false;
        }
        else {
            nomTache.getEditText().setText(nameTacheInput);
            nomTache.setError(null); // remove error icon and text
            nomTache.setErrorEnabled(false); // remove error icon and text
            return true;
        }
    }

    // description validation
    private boolean validateDescription() {
        String descriptionInput = descriptionTache.getEditText().getText().toString().trim();

        if (descriptionInput.isEmpty()) {
            descriptionTache.setError("Champ obligatoire");
            return false;
        } else if (descriptionInput.length() > 100 ) {
            descriptionTache.setError("Description trop long");
            return false;
        } else if (descriptionInput.length() < 10) {
            descriptionTache.setError("Description trop court");
            return false;
        } else {
            descriptionTache.setError(null);
            return true;
        }
    }

    // date validation
    private boolean validateDate() {
        String dateInput = dateEstimation.getText().toString().trim();

        if (dateInput.isEmpty()) {
            dateEstimation.setError("Champ obligatoire");
            return false;
        } else {
            dateEstimation.setError(null);
            return true;
        }
    }

    // materiel validation
    private boolean validateMateriel() {
        String materielInput = listMateriel.getText().toString().trim();

        if (materielInput.isEmpty()) {
            listMateriel.setError("Champ obligatoire");
            return false;
        } else {
            listMateriel.setError(null);
            return true;
        }
    }

    // personne validation
    private boolean validatePersonne() {
        String personneInput = listPersonne.getText().toString().trim();

        if (personneInput.isEmpty()) {
            listPersonne.setError("Champ obligatoire");
            return false;
        } else {
            listPersonne.setError(null);
            return true;
        }
    }

    // projet validation
    private boolean validateProjet() {
        String projetInput = listprojet.getText().toString().trim();

        if (projetInput.isEmpty()) {
            listprojet.setError("Champ obligatoire");
            return false;
        } else {
            listprojet.setError(null);
            return true;
        }
    }
}
