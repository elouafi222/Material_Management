package traitement_pour_lesTaches;

import android.app.Activity;
import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.planificationmobile2.MainActivityClient;
import com.example.planificationmobile2.R;
import com.example.planificationmobile2.home;
import com.example.planificationmobile2.user;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class RatingDialog extends DialogFragment implements RatingBar.OnRatingBarChangeListener {


    public static final String PREF_KEY_SHOW_AGAIN = "show_rate_dialog_again";
    public static final String PREF_KEY_NEVER_SHOW = "never_show_rate_dialog";
    private static final String PREF_KEY_APP_LAUNCH_COUNT = "app_launch_count";
    public static String EMAIL_FEEDBACK = "";

    public static String url = "http://20.55.44.15:8000/ratetache";
    public static int score=0;

    private static int id_tache;

    public static void showRateAppDialog(FragmentManager fragmentManager, Context context,int id_tacheval) {
        try {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
            boolean showAgain = prefs.getBoolean(PREF_KEY_SHOW_AGAIN, true);
            boolean neverShow = prefs.getBoolean(PREF_KEY_NEVER_SHOW, false);
            if (neverShow || !showAgain) {
                return;
            }
            RatingDialog.showRateAppDialogNormal(fragmentManager, context,id_tacheval);
        } catch (Exception e) {
            Utils.getErrors(e);
        }
    }

    public static void showRateAppDialogNormal(FragmentManager fragmentManager, Context context,int id_tacheval) {
        try {
            RatingDialog ratingDialog = new RatingDialog();
            ratingDialog.show(fragmentManager, "RatingDialog");
            id_tache=id_tacheval;
        } catch (Exception e) {
            Utils.getErrors(e);
        }
    }



    public static void rateTache(Context context,int score){
        /*
        SharedPreferences sharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        String session_key = sharedPreferences.getString("session_key", "");
         */
        JSONObject postData = new JSONObject();
        user usercourant =user.gestInstance();

        try {
            postData.put("username",usercourant.getNom());
            postData.put("password",usercourant.getPassword());
            postData.put("id_tache",id_tache);
            postData.put("scoreval",score);
        } catch (
                JSONException e) {
            throw new RuntimeException(e);
        }

        /// envoier la requete vers le serveur

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, postData,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            System.out.println(response.getString("----------------------------------------etat"));
                        } catch (JSONException e) {
                            Toast.makeText(context,e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Erreur de connexion
                Toast.makeText(context,error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);

    }


    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(requireActivity());
        View dialogView = onCreateView(getLayoutInflater(), null, savedInstanceState);
        builder.setCancelable(false).setView(dialogView).setBackground(new ColorDrawable(Color.TRANSPARENT));
        return builder.create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.popuptache, container, false);


        TextView neverButton = view.findViewById(R.id.bt_maybeLater);
        neverButton.setOnClickListener(v -> {
            SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(requireContext());
            prefs.edit().putBoolean(PREF_KEY_NEVER_SHOW, true).apply();
            dismiss();
        });

        TextView rateButton = view.findViewById(R.id.bt_ratingSend);
        rateButton.setOnClickListener(v -> Toast.makeText(getActivity(), R.string.select_5_star_rating, Toast.LENGTH_SHORT).show());

        String title = "Évaluez cette tache de 1 à 5 étoiles";
        TextView tv_title = view.findViewById(R.id.tv_title);
        tv_title.setText(title);


        RatingBar ratingBar = view.findViewById(R.id.bt_ratingBar);
        ratingBar.setOnRatingBarChangeListener(this);

        return view;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        try {
            rateTache(getActivity(),(int)rating); // appel de la méthode rateTache avec un score de 5
            dismiss();
        } catch (Exception e) {
            Utils.getErrors(e);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (getDialog() != null) {
            getDialog().setDismissMessage(null);
        }
    }
}
