package com.example.planificationmobile2;

public class user {

    private static user user1;
    String nom;
    String password;
    int verifierchef;

    public user(){
    }

    public static user gestInstance(){
        if(user1==null)
            user1 = new user();
        return user1;
    }

    public  void setNom(String nom){
        this.nom=nom;
    }

    public  void setPassword(String password){
        this.password=password;
    }

    public  void  setVerifierchef(int verifierchef){this.verifierchef=verifierchef;}


    public String getNom(){
        return this.nom;
    }

    public String getPassword(){
        return this.password;
    }

    /**
     * remoeve user
     */
    public static void  removeUser(){
        user1=null;
    }
    public int getVerifierchef(){return this.verifierchef;}


}
