/*
 * CampoModificacionMasiva.java
 *
 * Created on 05/05/2017
 */
package utilesGUIx.modificacionMasiva;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rtenor
 */
public class CampoModificacionMasiva {

    public static final int mclTipoTextField = 0;
    public static final int mclTipoTextArea = 1;
    public static final int mclTipoCheckBox = 2;
    public static final int mclTipoComboBox = 3;

    private JFieldDef moField;
    private int mlTipo;
    private JListDatos moListaValores;
    private int[] moDescripciones;
    private int[] moCodigos;
    
    private boolean mbConBlanco;

    private String msCodigoAtributo;

    public CampoModificacionMasiva(JFieldDef poField, int plTipo) {
        this(poField, plTipo, null);
    }

    public CampoModificacionMasiva(JFieldDef poField, int plTipo, JListDatos poListaValores, int[] poDescripciones, int[] poCodigos, boolean pbConBlanco) {
        this(poField, plTipo, poListaValores, poDescripciones, poCodigos, pbConBlanco, null);
    }

    public CampoModificacionMasiva(JFieldDef poField, int plTipo, String psCodigoAtributo) {
        moField = poField;
        mlTipo = plTipo;
        msCodigoAtributo = psCodigoAtributo;
    }
    
    public CampoModificacionMasiva(JFieldDef poField, int plTipo, JListDatos poListaValores, int[] poDescripciones, int[] poCodigos, boolean pbConBlanco, String psCodigoAtributo) {
        moField = poField;
        mlTipo = plTipo;
        moListaValores = poListaValores;
        moDescripciones = poDescripciones;
        moCodigos = poCodigos;
        mbConBlanco = pbConBlanco;
        msCodigoAtributo = psCodigoAtributo;
    }

    public JFieldDef getField() {
        return moField;
    }

    public void setField(JFieldDef poField) {
        this.moField = poField;
    }

    public int getTipo() {
        return mlTipo;
    }

    public void setTipo(int plTipo) {
        this.mlTipo = plTipo;
    }

    public JListDatos getListaValores() {
        return moListaValores;
    }

    public void setListaValores(JListDatos poListaValores) {
        this.moListaValores = poListaValores;
    }

    public int[] getDescripciones() {
        return moDescripciones;
    }

    public void setDescripciones(int[] poDescripciones) {
        this.moDescripciones = poDescripciones;
    }

    public int[] getCodigos() {
        return moCodigos;
    }

    public void setCodigos(int[] poCodigos) {
        this.moCodigos = poCodigos;
    }

    public String getCodigoAtributo() {
        return msCodigoAtributo;
    }

    public void setCodigoAtributo(String psCodigoAtributo) {
        this.msCodigoAtributo = psCodigoAtributo;
    }

    public boolean getConBlanco() {
        return mbConBlanco;
    }

    public void setConBlanco(boolean pbConBlanco) {
        this.mbConBlanco = pbConBlanco;
    }
    

    public static List<CampoModificacionMasiva> getListaCampos(JListDatos poList){
        List<CampoModificacionMasiva> loCampos = new ArrayList<CampoModificacionMasiva>();
        for(JFieldDef loCampo : poList.getFields().getListaCampos()){
            switch(loCampo.getTipo()){
                case JListDatos.mclTipoBoolean:
                    loCampos.add(new CampoModificacionMasiva(loCampo, mclTipoCheckBox));
                    break;
                case JListDatos.mclTipoFecha:
                    loCampos.add(new CampoModificacionMasiva(loCampo, mclTipoTextField));
                    break;
                case JListDatos.mclTipoNumeroDoble:
                case JListDatos.mclTipoNumero:
                    loCampos.add(new CampoModificacionMasiva(loCampo, mclTipoTextField));
                    break;
                default:
                    if(loCampo.getTipo()==JListDatos.mclTipoCadena && loCampo.getTamano()>256){
                        loCampos.add(new CampoModificacionMasiva(loCampo, mclTipoTextArea));
                    } else {
                        loCampos.add(new CampoModificacionMasiva(loCampo, mclTipoTextField));
                    }
            }
        }
        return loCampos;
            
    }    

}
