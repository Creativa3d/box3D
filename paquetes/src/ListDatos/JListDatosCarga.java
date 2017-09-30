/**
 * <p>Title: </p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2003-2005</p>
 * <p>Company: </p>
 * @author sin atribuir
 * @version 1.0 
 */

package ListDatos;

import utiles.JDepuracion;

/**
 *Carga un JListDatos de forma asincrona
 */
public final class JListDatosCarga extends Thread {
  // constructor
  private final JListDatos mov;
  private final JSelect moSelect;
  private final String msTabla;
  private final boolean mbPasarACache;
  private final boolean mbCacheRefrescar;
  private final int mlOpciones;
  private final IServerServidorDatos moServidor;
  /**
   * Constructor
   * @param str nombre del trhead
   * @param poServidor servidor de datos
   * @param v JListDatos a cargar
   * @param poSelect select del JListDatos
   * @param psTabla tabla base
   * @param pbPasarACache si pasa a cache
   * @param plOpciones  opciones
   * @param pbCacheRefrescar si refresca la cache
   */
  public JListDatosCarga(String str, IServerServidorDatos poServidor,JListDatos v, JSelect poSelect,String psTabla,boolean pbPasarACache, int plOpciones, boolean pbCacheRefrescar) {
    super(str);
    moServidor = poServidor;
    mov = v;
    moSelect = poSelect;
    msTabla = psTabla ;
    mbPasarACache = pbPasarACache;
    mlOpciones = plOpciones;
    mbCacheRefrescar = pbCacheRefrescar;
  }
  // redefinicion del metodo run()
  public void run() {
    try{
      moServidor.recuperarDatos(mov, moSelect, msTabla, 
        mbPasarACache, mbCacheRefrescar, mlOpciones);
    }catch(Exception e){
      JDepuracion.anadirTexto(JDepuracion.mclWARNING, this.getClass().getName(), e);
      BusquedaEvent loBus = new BusquedaEvent(this, mov);
      loBus.mbError = true;
      loBus.moError = e;
      mov.recuperacionDatosTerminada(loBus);
    }
  }
}