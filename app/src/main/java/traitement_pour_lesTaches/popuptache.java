package traitement_pour_lesTaches;

import android.app.Dialog;
import android.content.Context;
import android.widget.Button;
import android.widget.TextView;

import com.example.planificationmobile2.R;
import com.example.planificationmobile2.user;


public class popuptache extends Dialog {

    private TextView nomtache;
    private TextView descptiontache;
    private TextView statueprojet;
    private TextView  emp;
    private TextView chef;
    private  TextView nomprojet;
    private TextView dateFin;
    private TextView nomdatefintache;
    private TextView nomemp;
    private TextView nomchef;
    private TextView Nomprojetques;
    private TextView nomprojetvaleur;
    private TextView nomdesmateriell;
    private TextView valeurmat;

    private Button butonok;

    public popuptache(Context activity){
        super(activity, androidx.appcompat.R.style.Theme_AppCompat_DayNight_Dialog);
        setContentView(R.layout.popuptache2);
        this.nomtache=findViewById(R.id.popusNomtache);
        this.descptiontache=findViewById(R.id.popusdescriptiontachereponce);
        this.dateFin=findViewById(R.id.datefintachevaleur);
        this.emp=findViewById(R.id.lespersonnetachevaleur);
        this.chef=findViewById(R.id.valeurchefprojettache);
        this.nomprojet=findViewById(R.id.valeurnomprojettache);

        this.nomdatefintache = findViewById(R.id.datefintachepopus);
        this.nomemp=findViewById(R.id.personnetachequestion);
        this.nomchef=findViewById(R.id.nomcheftache);
        this.Nomprojetques=findViewById(R.id.nomprojetdetache);
        this.nomprojetvaleur=findViewById(R.id.valeurnomprojettache);
        this.nomdesmateriell=findViewById(R.id.nomdumateriel);
        this.valeurmat=findViewById(R.id.listedemateriel);



    }

    public void buils(String nomtache,String emp,String chef, String nomprojet, String descriptionprojet, String dateFin,String nomprojet2,int idtache) {
        show();
        setNomtache(nomtache);
        setnomprojet(nomprojet);
        setDescriptiontache(descriptionprojet);
        // setStatueprojet(statueprojet);
        setDateFin(""+dateFin);
        setEmp(emp);
        setChef(chef);


        this.nomdatefintache.setText("Durée:");
        this.nomemp.setText("EMP :");
        this.nomchef.setText("Chef :");
        this.Nomprojetques.setText("Nom projet :");
        this.nomprojetvaleur.setText(nomprojet2);
        this.nomdesmateriell.setText("liste matérielle:");
        user usercourant =user.gestInstance();

        String resulata =recuperermaterielletache.recupermaterille(getContext(),usercourant.getNom(),usercourant.getPassword(),idtache);
        this.valeurmat.setText(resulata);
        System.out.println("------------------------------------------------"+nomprojet2);

    }

    public void setNomtache(String nomtache){ this.nomtache.setText(nomtache);}

    public void setnomprojet(String nomprojet){
        this.nomprojet.setText(nomprojet);
    }

    public void setDescriptiontache(String descriptiontache){
        this.descptiontache.setText(descriptiontache);
    }

    public void setStatueprojet(String statueprojet){
        this.statueprojet.setText(statueprojet);
    }
    public void setDateFin(String dateFin){
        this.dateFin.setText(dateFin);
    }

    public void  setEmp(String emp){this.emp.setText(emp);}
    public void setChef(String chef){this.chef.setText(chef);}


    public Button getButonok(){
        return  this.butonok;
    }
}
