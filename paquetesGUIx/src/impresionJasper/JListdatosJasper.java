package impresionJasper;
import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRField;
import utiles.FechaMalException;
import utiles.JConversiones;

/*
 * JListdatosJasper.java
 *
 * Created on 4 de septiembre de 2007, 14:13
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

/**
 *
 * @author chema
 */
public class JListdatosJasper implements JRDataSource {
    protected JListDatos moList;
    protected boolean mbPrimeraVez=true; 
    
    boolean mbUltimo = false;
    /** Creates a new instance of JListdatosJasper */
    public JListdatosJasper(final JListDatos poList) {
        moList = poList;
        moList.moveFirst();
    }

    public boolean next() throws JRException {
        boolean lbResult = false;
        if(mbPrimeraVez){
            lbResult = moList.moveFirst();
            mbPrimeraVez=false;
        }else{
            lbResult = moList.moveNext();
        }
        mbUltimo=!lbResult;
//        System.out.println("Maestro "+moList.moFila().toString() + " ultimo = " + String.valueOf(mbUltimo));
        return lbResult;
    }
    
    public JFieldDef getCampo(final String psName){
        JFieldDef loCampo = null;
        if(JConversiones.isNumeric(psName)){
            loCampo = moList.getFields(Integer.valueOf(psName).intValue());
        } else {
            loCampo = moList.getFields().get(psName);
        }
        return loCampo;
    }

    public Object getFieldValue(final JRField jRField) throws JRException {
        int lCampo = -1;
        if(JConversiones.isNumeric(jRField.getName())){
            lCampo = (int)JConversiones.cdbl(jRField.getName());
        } else{
            int i;
            String lsNombre = jRField.getName().toUpperCase();
            for(i=0;(i<moList.getFields().size())&&(lCampo<0);i++){
              if(((JFieldDef)moList.getFields().get(i)).getNombre().toUpperCase().compareTo(lsNombre) == 0) {
                  lCampo = i;
              }
            }
        }
        if(lCampo<0){
            throw new JRException("El campo " + jRField.getName() + " no existe");
        }
        Object loReturn = null;
        if(moList.getFields().get(lCampo).isVacio()){
            if(moList.getFields().get(lCampo).getTipo() == JListDatos.mclTipoCadena) {
                loReturn="";
            }
        }else{
            loReturn = moList.getFields().get(lCampo).getValue();
            if(moList.getFields().get(lCampo).getTipo() == JListDatos.mclTipoFecha) {
                try {
                    loReturn = moList.getFields().get(lCampo).getDateEdu().moDate();
                } catch (FechaMalException ex) {
                    loReturn = null;
                    ex.printStackTrace();
                }
            }
        }
        
        return loReturn;
    }
    
    public JListDatos getList() {
        return moList;
    }

    public void setList(final JListDatos poList) {
        moList = poList;
        moList.moveFirst();
    }
}
