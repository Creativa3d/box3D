package impresionJasper;

import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import net.sf.jasperreports.engine.JRException;

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
public class JListdatosJasperDetalle extends JListdatosJasper {
    private JListDatos moMaestro;
    private int[] malCamposMaestro;
    private int[] malCampos;


    private String msClaveMaestro=null;
    /** Creates a new instance of JListdatosJasper */
    public JListdatosJasperDetalle(final JListDatos poList, final JListDatos poMaestro, final int[] palCamposMaestro, final int[] palCampos) {
        super(poList);
        moMaestro = poMaestro;
        malCamposMaestro = palCamposMaestro;
        malCampos = palCampos;

    }
    public String getClaveMaestro(){
        String lsValores = "";
        for (int i = 0; i < malCamposMaestro.length; i++) {
            lsValores += moMaestro.getFields(malCamposMaestro[i]).getString() + "-";
        }
        return lsValores;
    }
    public boolean next() throws JRException {

        if (msClaveMaestro == null || !getClaveMaestro().equals(msClaveMaestro)) {
//            System.out.println("Detalle FILTRAR "+moList.msTabla+ " "+msClaveMaestro);

            msClaveMaestro = getClaveMaestro();
            moList.filtrarNulo();
            moList.getFiltro().Clear();

            String[] lasValores = new String[malCamposMaestro.length];
            for(int i = 0 ; i < lasValores.length; i++){
                lasValores[i] = moMaestro.getFields(malCamposMaestro[i]).getString();
            }
            moList.getFiltro().addCondicion(
                    JListDatosFiltroConj.mclAND,
                    JListDatos.mclTIgual,
                    malCampos,
                    lasValores
            );
            moList.filtrar();
            mbPrimeraVez=true;

            
        }
//        System.out.println("Detalle "+moList.msTabla + " " +msClaveMaestro + " primeravez=" + String.valueOf(mbPrimeraVez));

        
        
        boolean lbResult = false;
        if(mbPrimeraVez){
            lbResult = moList.moveFirst();
            mbPrimeraVez=false;
        }else{
            lbResult = moList.moveNext();
        }
        return lbResult;
    }

}
