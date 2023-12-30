package com.example.planificationmobile2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class baseDeDonne {
    // class de base de donne oracle utilisé JBDC
    //private static baseDeDonne connection=null;
    private final static String url = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
    private  Connection conn;

    private  Statement Session; // for file session conection sql oracle

    // constructeur
    public baseDeDonne(){
        this.conn=null;
        this.Session=null;
    }

    /// cette methode permet de recuperer l'objet courant dans n'import quelle activite
    /*
    public  static baseDeDonne getInstance(){
        if(connection==null)
            connection =new baseDeDonne();
        return  connection;
    }
    */
    // methode de connexion
    public void connexion(String user, String password) throws ClassNotFoundException, SQLException {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Class.forName("oracle.jdbc.driver.OracleDriver");
                    conn = DriverManager.getConnection(url,user,password);
                    Session = conn.createStatement(); // on stock la session dans la variable Session
                } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    // methode for execute sql statement
    public int execute(String sql) throws SQLException {
        ResultSet rs = Session.executeQuery(sql);
        int cmpt=0;
        StringBuffer stringBuffer = new StringBuffer();
        while (rs.next()) {
            //stringBuffer.append(rs.getString(1) + "\n");
            cmpt++;
        }

      //  System.out.println(stringBuffer.toString());
        rs.close();
        return  cmpt;

    }

    // methode de deconnexion
    public void deconnexion() throws SQLException {
        // code de deconnexion
        this.conn.close();

        Session=null;

    }
}
