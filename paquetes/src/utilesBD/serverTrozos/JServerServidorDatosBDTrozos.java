/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesBD.serverTrozos;

import ListDatos.IFilaDatos;
import ListDatos.JFilaCrearDefecto;
import ListDatos.JListDatos;
import ListDatos.JServerServidorDatosBD;
import ListDatos.estructuraBD.JTableDefs;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Statement;
import java.util.Calendar;
import utiles.JCadenas;
import utiles.JDateEdu;
import utiles.JDepuracion;

public class JServerServidorDatosBDTrozos extends JServerServidorDatosBD implements IServerServidorDatosTrozos{
    private int mlMin;
    private int mlMax;
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosBDTrozos(JServerServidorDatosBD poBD) {
        super();
        setConec(poBD.getConec());
        setSelect(poBD.getSelect());
        setConstrucEstruc(poBD.getConstrucEstruc());
        
        getParametros().setSoloLectura(poBD.getParamBD().isSoloLectura());

    }

    @Override
    public JTableDefs getTableDefs() throws Exception {
        return super.getTableDefs(); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Constructor
     * @param psDriver driver
     * @param psURL cadena conexión
     * @param psUsu usuario
     * @param psPassword pasword
     */
    public JServerServidorDatosBDTrozos(String psDriver, String psURL, String psUsu, String psPassword) throws Throwable{
        super();
          Class.forName(psDriver);
          if (JCadenas.isVacio(psUsu) && JCadenas.isVacio(psPassword)){
            moConec = DriverManager.getConnection(psURL);
          }else{
            moConec = DriverManager.getConnection(psURL, psUsu, psPassword);
          }
    }
    /**
     * Constructor
     * @param psDriver driver
     * @param psURL cadena conexión
     * @param psUsu usuario
     * @param psPassword pasword
     * @param psTipoBDStandar BD Definidas en JSelectMotor
     */
    public JServerServidorDatosBDTrozos(final String psDriver, final String psURL, final String psUsu, final String psPassword, final String psTipoBDStandar) throws Throwable{
        this(psDriver, psURL, psUsu, psPassword);
        setTipoBDStandar(psTipoBDStandar);
    }

    public void setIntervaloDatos(int plMin, int plMax){
        mlMin = plMin;
        mlMax = plMax;
    }

    public JListDatos recuperarDatosBD(final JListDatos v,final String psSELECT, final String psTabla)throws Exception  {
        Statement stmt = null;
        ResultSet rs = null;
        try{
            stmt = moConec.createStatement();
            rs = stmt.executeQuery(psSELECT);
            ResultSetMetaData rsmd = rs.getMetaData();
            int numberOfColumns = rsmd.getColumnCount();
            JDateEdu loDateEdu = new JDateEdu();
            Calendar c = Calendar.getInstance();
            String[] lasCampos = new String[numberOfColumns];
            int lFila = 0;
            if(mlMin<mlMax){
                v.ensureCapacity(mlMax - mlMin + 1);
            }
            boolean lbTroceo = mlMin<mlMax;
            while (rs.next() && (!lbTroceo || (lFila<mlMax))) {
                if(!lbTroceo || (lFila>=mlMin && lFila<mlMax) ){
                    for(int i = 0 ; i < numberOfColumns ; i++){
                        lasCampos[i]=getCampo(rs,i,loDateEdu, c, rsmd);
                    }
                    IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                    loFila.setArray(lasCampos);
                    v.add(loFila);
                }
                lFila++;
            }
        } finally {
            try{
                if(rs!=null){
                    rs.close();
                }
                if(stmt!=null){
                    stmt.close();
                }
            }catch(Throwable e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
        return v;
    }


}
