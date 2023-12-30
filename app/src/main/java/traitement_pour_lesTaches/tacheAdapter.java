package traitement_pour_lesTaches;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

import com.example.planificationmobile2.MainActivityClient;
import com.example.planificationmobile2.R;
import com.example.planificationmobile2.tacheActivity;
import com.example.planificationmobile2.traitementPourProjet.popupprojet;
import com.example.planificationmobile2.traitementPourProjet.projet;
import com.example.planificationmobile2.user;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class tacheAdapter extends BaseAdapter {
    private LayoutInflater inflater;
    private ArrayList<tache> lestache;

    private Context context;

    public tacheAdapter(Context context,ArrayList<tache> lestache){
        this.context=context;
        this.lestache=lestache;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return this.lestache.size();
    }

    @Override
    public tache getItem(int position) {
        return this.lestache.get(position);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view= this.inflater.inflate(R.layout.adaptertache,null);
        tache tacheCourant = getItem(i);
        /**
         * recuperation des item depuit la vue
         */
        TextView nomtache =view.findViewById(R.id.nomtache2);
        TextView descriptionTache = view.findViewById(R.id.descriptiontache);
        TextView dureeRestant =view.findViewById(R.id.durerestanttache);
        TextView statusTache = view.findViewById(R.id.statuttache);
        ImageView validerTache= view.findViewById(R.id.validerTache);

        /**
         * ajouter le contunue des tache dans les items
         */
        nomtache.setText(tacheCourant.getNomTache());
        String etat=tacheCourant.getEtat();
        descriptionTache.setText(tacheCourant.getDescription());
        statusTache.setText(etat);
        String periode =tacheCourant.dureeRestante();
        dureeRestant.setText(periode);
        if(periode.contains("+"))
            dureeRestant.setBackgroundColor(Color.GREEN);
        else if (periode.equals("Aujourd'hui")) {
            dureeRestant.setBackgroundColor(Color.YELLOW);
        }else
            dureeRestant.setBackgroundColor(Color.RED);

        ///////// ajouter la couleur pour le status
        System.out.println("-----------------------"+etat);
        if(etat.equals("en cours de execution"))
            statusTache.setBackgroundColor(Color.YELLOW);
        else if (etat.equals("terminee")) {
            statusTache.setBackgroundColor(Color.GREEN);

        } else if (etat.equals("non terminee")) {
            statusTache.setBackgroundColor(Color.RED);
        }

        /**
         * gerer l'evenement de clik sur la tache lui meme
         */
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popuptache popuptache1 = new popuptache(context);
                popuptache1.buils(tacheCourant.getNomTache(),tacheCourant.getNomEmp(),tacheCourant.getNomchef(),tacheCourant.getNomTache(),tacheCourant.getDescription(),tacheCourant.dureeRestante(),tacheCourant.getNomprojet(),tacheCourant.getIdtache());

            }
        });

        /**
         * gerer l'evenement de clik sur le boutant valider tache
         */
        user usercourant =user.gestInstance();
        if(usercourant.getVerifierchef()==1){
            validerTache.setOnClickListener(v -> {
                FragmentManager fragmentManager = ((AppCompatActivity)context).getSupportFragmentManager();
                RatingDialog.showRateAppDialogNormal(fragmentManager, context, tacheCourant.getIdtache());
            });
        }


        return view;
    }

    public void updateTaskList() {
        notifyDataSetChanged();
    }





}
