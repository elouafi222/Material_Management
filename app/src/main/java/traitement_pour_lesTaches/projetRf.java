package traitement_pour_lesTaches;

import com.example.planificationmobile2.traitementPourProjet.projet;

public class projetRf {
    private  static projetRf projet1;

    private String  idproj;
    private String nomProj;

    public static projetRf getInstance(){
        if(projet1==null)
            projet1=new projetRf();
        return projet1;
    }

    public void setIdproj(String idproj){
        this.idproj=idproj;
    }

    public void setNomProj(String nomProj){this.nomProj=nomProj;}

    public String  getidproj(){
        return this.idproj;
    }

    public String getNomProj(){return  this.nomProj;}

}
