package impresionJasper;
import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import net.sf.jasperreports.engine.JRException;


public class JListdatosJasperDetalleSincr extends JListdatosJasper {
    private JListDatos moMaestro;
    private int[] malCamposMaestro;
    private int[] malCampos;
        
    
    private String msClaveMaestro=null;
    private boolean mbPrimerRegistro=true;
    private JListDatos moListaClaves;
    private int mlRetraso;
    private final JListdatosJasper moJasperMaestro;
    /** Creates a new instance of JListdatosJasper */
    public JListdatosJasperDetalleSincr(JListdatosJasper poJasperMaestro ,final JListDatos poList, final JListDatos poMaestro, final int[] palCamposMaestro, final int[] palCampos) {
        super(poList);
        moMaestro = poMaestro;
        moJasperMaestro=poJasperMaestro;
        try {
            moListaClaves=new JListDatos(moMaestro, false);
        } catch (CloneNotSupportedException ex) {
        }
        malCamposMaestro = palCamposMaestro;
        malCampos = palCampos;
        
    }
    public String getClaveMaestro(JListDatos poMaestro){
        String lsValores = "";
        for(int i =0;i<malCamposMaestro.length;i++){
            lsValores += poMaestro.getFields(malCamposMaestro[i]).getString()+"-";
        }
        return lsValores;
    }
    public boolean next() throws JRException {
        //cuando hay grupos en el informe, puede haber algun registro de retraso, aqui se ve cuantos
        if(mbPrimerRegistro){
            mlRetraso = moMaestro.getIndex();
            mbPrimerRegistro=false;
        }
        //se coje el registro quitando el retraso, salvo si es el ultimo registro del maqestro q entonces si se coje el ultimo
        if(moJasperMaestro.mbUltimo){
            try {
                moListaClaves.getFields().cargar((IFilaDatos) moMaestro.get(moMaestro.size()-1));
            } catch (ECampoError ex) {
            }
        }else{
            try {
                moListaClaves.getFields().cargar((IFilaDatos) moMaestro.get(moMaestro.getIndex()-mlRetraso));
            } catch (ECampoError ex) {
            }
        }
        
        //si cambia la clave se filtra el detalle
        if(msClaveMaestro == null || !getClaveMaestro(moListaClaves).equals(msClaveMaestro)){
            msClaveMaestro = getClaveMaestro(moListaClaves);
            filtrarDetalle(moListaClaves);
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
        //para que se repita si se pone el el pie de pagina y un registro tiene varias paginas
        if(!lbResult){
            msClaveMaestro = null;
        }
        return lbResult;
    }
    
    private void filtrarDetalle(JListDatos poMaestro){
//        System.out.println("Detalle FILTRAR "+moList.msTabla+ " "+msClaveMaestro);
        moList.filtrarNulo();
        moList.getFiltro().Clear();

        String[] lasValores = new String[malCamposMaestro.length];
        for(int i = 0 ; i < lasValores.length; i++){
            lasValores[i] = poMaestro.getFields(malCamposMaestro[i]).getString();
        }
        moList.getFiltro().addCondicion(
                JListDatosFiltroConj.mclAND,
                JListDatos.mclTIgual,
                malCampos,
                lasValores
                );
        moList.filtrar();

    }

}
