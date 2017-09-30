//Este informe se utiliza para imprimir directamente desde las pantallas de los buscadores

package impresionXML.tools;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import impresionXML.listDatos.JListDatosXML;
import impresionXML.listDatos.JListDatosXMLMaestro;
import java.awt.Font;
import java.awt.Frame;
import utiles.CadenaLarga;
import utiles.JConversiones;
import utilesGUIx.formsGenericos.IPanelGenerico;
import utilesGUIx.formsGenericos.JTablaConfigColumna;
import utilesGUIx.formsGenericos.JTablaConfigTablaConfig;

public class JInfGeneral {
    public static final double mcnCoef = 0.03;
    
    public JInfGeneral() {
        super();
    }
    
    private boolean getOrdenCorrecto(final JTablaConfigTablaConfig poConfig){
        int lOrdenAnt = -1;
        for(int i = 0; i < poConfig.size(); i++ ){
            if (poConfig.getColumna(i).getOrden() < lOrdenAnt){
                return false;
            }
            lOrdenAnt=poConfig.getColumna(i).getOrden();
        }
        return true;
    }
    
    private JTablaConfigColumna getColumna(final JTablaConfigTablaConfig poConfig, final int plColumnaOrder){
        int i = getColumnaIndiceconfig(poConfig, plColumnaOrder);
        if(i>=0){
            return poConfig.getColumna(i);
        }else{
            return null;
        }
    }
    private int getColumnaIndice(final JTablaConfigTablaConfig poConfig, final int plColumnaOrder){
        return (int)JConversiones.cdbl(poConfig.getColumna(
                getColumnaIndiceconfig(poConfig, plColumnaOrder)
                ).getNombre());
    }
    
    private int getColumnaIndiceconfig(final JTablaConfigTablaConfig poConfig, final int plColumnaOrder){
        for(int i = 0; i < poConfig.size(); i++ ){
            if (poConfig.getColumna(i).getOrden() == plColumnaOrder){
                return i;
            }
        }
        return -1;
    }
    public JListDatos getListOrdenado(final JListDatos poList, final JTablaConfigTablaConfig poConfig) throws CloneNotSupportedException, ECampoError{
        JListDatos loListNuevo = new JListDatos();
        
        for(int i = 0 ; i < poList.getFields().count(); i++){
            int lColumn = getColumnaIndice(poConfig, i);
            if(lColumn>=0){
                loListNuevo.getFields().addField(poList.getFields(lColumn).Clone());
            }
            
        }
        
        if(poList.moveFirst()){
            do{
                loListNuevo.addNew();
                for(int i = 0 ; i < poList.getFields().count(); i++){
                    int lColumn = getColumnaIndice(poConfig, i);
                    if(lColumn>=0){
                        loListNuevo.getFields(i).setValue(
                                poList.getFields(lColumn).getString()
                                );
                    }

                }
                loListNuevo.update(false);
                
            }while(poList.moveNext());
        }
        
        return loListNuevo;
    }

    //Dado un listDatos pasado desde el controlador genero el listado
    public void generarListado(final JListDatos poList, IPanelGenerico poPanel, String psTitulo) throws Exception {
        Font loFont0 = new Font("Arial",1,18);
        Font loFont2 = new Font("Arial",0,10);
        JListDatosXMLMaestro loListXML = new JListDatosXMLMaestro();
        JListDatosXML loList;
        JListDatos loListDatos = poList;
        JTablaConfigTablaConfig loConfig = poPanel.getTablaConfig().getConfigTablaConcreta();
        
        //si el orden es cambiado se regenera el list datos con los campos en el orden correcto
        if(!getOrdenCorrecto(loConfig)){
            loListDatos = getListOrdenado(loListDatos,loConfig);
        }
        //Informe General
        loList = loListXML.addListDatos(loListDatos);
        loList.getFormato().setPagina(29.7,21,0.5,0.5,0.5,0.5);
        loList.getFormato().setFuenteTitulo(loFont0);
        loList.getFormato().setTitulo(psTitulo);
        
        //Formateamos los campos
        double ldTotalLong=0;
        for(int i=0;i<poList.getFields().count();i++) {
            JTablaConfigColumna loColumn = getColumna(loConfig, i);
            if(loColumn!=null){
                loList.getFormato().setLong(
                    i, 
                    loColumn.getLong() * mcnCoef);
                ldTotalLong+=loColumn.getLong() * mcnCoef;
            }
        }
        if(ldTotalLong>20){
            loList.getFormato().setPagina(21,29.7,0.5,0.5,0.5,0.5);
        }
        
        JFormIMP loForm = new JFormIMP(new Frame(), false);
        loForm.setDatos(new CadenaLarga(loListXML.getXSLFOtoString()));
        loForm.setVisible(true);
    }
}



    

