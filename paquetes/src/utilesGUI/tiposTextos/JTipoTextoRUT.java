/*
 * JTipoTextoEstandar.java
 *
 * Created on 16 de noviembre de 2006, 9:56
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */
package utilesGUI.tiposTextos;

import utiles.JCadenas;
import utiles.JRUT;

public class JTipoTextoRUT extends JTipoTextoBaseAbstract {


    /** Creates a new instance of JTipoTextoEstandar */
    public JTipoTextoRUT() {
        super();
    }

    @Override
    public boolean isTipoCorrecto(final String psTexto) {
        boolean lbCorrecto = true;
        String lsTexto = psTexto.trim();
        if (lsTexto != null && !lsTexto.equals("")) {
            String lsLetra = JRUT.getDigitoVerificador(lsTexto);
            if (lsLetra != null) {
                lsTexto = lsTexto + lsLetra;
            }
            try {
                lbCorrecto = JRUT.isRUTOK(lsTexto);
            } catch (Exception e) {
                lbCorrecto = false;
            }
        }
        return lbCorrecto;
    }

    @Override
    public String getTextoModificado(final String psTexto) {
        String lsTexto = psTexto;

        if (lsTexto != null && !lsTexto.equals("")) {
            
            lsTexto = lsTexto.toUpperCase().replace(".", "").replace("-", "").replace(" ", "");
            String lsLetra = JRUT.getDigitoVerificador(lsTexto);
            if (lsLetra != null) {
                lsTexto = lsTexto + lsLetra;
            }
                
        }
        return lsTexto;
    }

    @Override
    public String getTextoError(final String psTexto) {
        String lsTextoError = null;
        String lsTexto = psTexto.trim();

        if (!isTipoCorrecto(lsTexto)) {
            lsTextoError = "RUT incorrecto (" + psTexto + ")";
        }
        return lsTextoError;
    }

    @Override
    public String getTextFormateado() {
        String retValue = "";
        if (JCadenas.isVacio(msTexto)) {
            return "";
        } else {
            retValue = JRUT.getRUTFormateado(msTexto);

        }
        return retValue;
    }

    @Override
    public void getTecla(final String psTexto, final KeyEventCZ poEvent) {
        poEvent.setKeyChar(Character.toUpperCase(poEvent.getKeyChar()));
    }
}
