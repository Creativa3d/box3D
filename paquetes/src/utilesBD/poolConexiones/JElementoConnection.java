/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesBD.poolConexiones;

import java.sql.Connection;

class JElementoConnection {
    private Connection moConex;
    private long mlCreada;
    private long mlServicio;    //fecha del ultimo servicio (uso)
    private String mslast_sql;    
    public JElementoConnection (Connection poConex, long plahora){
        moConex=poConex;
        mlCreada=plahora;
    }
    public Connection getConex(){
        return moConex;
    }

    /**
     * @return the mlAhora
     */
    public long getCreada() {
        return mlCreada;
    }

    /**
     * @return the mlServicio
     */
    public long getServicio() {
        return mlServicio;
    }

    /**
     * @param mlServicio the mlServicio to set
     */
    public void setServicio(long mlServicio) {
        this.mlServicio = mlServicio;
    }

    /**
     * @return the last_sql
     */
    public String getLast_sql() {
        return mslast_sql;
    }

    /**
     * @param last_sql the last_sql to set
     */
    public void setLast_sql(String last_sql) {
        this.mslast_sql = last_sql;
    }
    
}