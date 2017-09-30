/*
 * JGraficos.java
 *
 * Created on 3 de enero de 2006, 20:05
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package generadorGraficosXY;

import java.sql.Connection;

import ListDatos.JServerServidorDatos;
import utiles.*;

public class JGraficos {
    
    public JGraficoPropiedades moPropiedades=new JGraficoPropiedades();
    public IListaElementos moGraficos=null;

    /** Creates a new instance of JGraficos */
    public JGraficos() {
    }
    
    public void refrescarDatos(Connection poConex) throws Exception {
        //lista de graficos
        moGraficos = new JListaElementos();
        
        //servidor datos
        JServerServidorDatos loServer = new JServerServidorDatos();
        
        loServer.setTipo (loServer.mclTipoBD);
        loServer.setConec (poConex);
        
        //recuperar datos
        JGraficoCConsulta loConsulta = new JGraficoCConsulta(loServer);
        loConsulta.recuperarTodosNormalSinCache();
        
        loConsulta.moList.ordenar(new int[]{loConsulta.lPosiCodigo, loConsulta.lPosiGrupo, loConsulta.lPosiFecha});
        
        //procesar datos
        if(loConsulta.moList.moveFirst()){
            JGrafico loGraf = null;
            JGraficoSerie loSerie = null;
            do{
                if((loGraf == null)||(loGraf.msCodigo.compareTo(loConsulta.getCodigo().getString())!=0)){
                    loGraf = new JGrafico();
                    moGraficos.add(loGraf);
                    loSerie = new JGraficoSerie();
                    loGraf.moSeries.add(loSerie);

                    loGraf.msCodigo = loConsulta.getCodigo().getString();
                    loSerie.msGrupo = loConsulta.getGrupo().getString();
                }
                if(loSerie.msGrupo.compareTo(loConsulta.getGrupo().getString())!=0){
                    loSerie = new JGraficoSerie();
                    loGraf.moSeries.add(loSerie);
                    loSerie.msGrupo = loConsulta.getGrupo().getString();
                }
                loSerie.moDatos.moList.addNew();
                loSerie.moDatos.moList.getFields().cargar(loConsulta.moList.moFila());
                loSerie.moDatos.moList.update(false);
            }while(loConsulta.moList.moveNext());
        }
        
    }
    
}
