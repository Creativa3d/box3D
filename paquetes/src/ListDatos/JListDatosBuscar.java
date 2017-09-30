/*
 * JListDatosBuscar.java
 *
 * Created on 15 de diciembre de 2004, 9:08
 */

package ListDatos;
/**
 *Clase que implementa los metodos para buscar un datos dentro del JListDatos
 */
final class JListDatosBuscar implements java.io.Serializable {
    private static final long serialVersionUID = 3333337L;
    /**Constante de tipo de busqueda normal*/
    public static final int mclBuscarNormal = 0;
    /**Constante de tipo de busqueda binomial, solo para buscar por campos que estan ordenados*/
    public static final int mclBuscarBinomial = 1;
    private int mlTipo = mclBuscarNormal;

//    public JListDatosBuscar(){
//        super();
//    }
    /**
     * Establece el tipo de busqueda
     * @param plTipo tipo de busqueda
     */
    public void setTipo(final int plTipo){
        mlTipo = plTipo;
    }
    
    /**
    * Busca un registro que coincida con los criterios campos - valores
    * @param plCompara Tipo comparacion
    * @param poCampos indice de los campos
    * @param poCadenas Valores
    * @param pbAPartirActual a partir del registro actual
    * @param pbIgnorarCASE no distinge entre mayusculas y minusculas
    * @return si se ha encontrado
    */
    boolean Buscar(final JListDatos poList,final int plCompara, final int[] poCampos, final String[] poCadenas, final boolean pbAPartirActual, final boolean pbIgnorarCASE) {
        boolean lbEncontrado =false;
        switch(mlTipo){
            case mclBuscarBinomial:
                if(plCompara==JListDatos.mclTIgual){
                    lbEncontrado = BuscarBinomial(poList,poCampos, poCadenas, pbIgnorarCASE);
                }
                break;
            default:    
                lbEncontrado = BuscarNormal(poList,plCompara, poCampos, poCadenas, pbAPartirActual, pbIgnorarCASE);
        }
        return lbEncontrado;
    }
    
  /**
   * Busca un registro que coincida con los criterios campos - valores
   * @param plCompara Tipo comparacion
   * @param poCampos indice de los campos
   * @param poCadenas Valores
   * @param pbAPartirActual a partir del registro actual
   * @param pbIgnorarCASE no distinge entre mayusculas y minusculas
   * @return si se ha encontrado
   */
    private boolean BuscarNormal(
            final JListDatos poList,final int plCompara, final int[] poCampos, final String[] poCadenas,
            final boolean pbAPartirActual, final boolean pbIgnorarCASE) {
    boolean lbEncontrado = false;
    int i;
    IFilaDatos loFilaDatos;
    IFilaDatos loFilaDatosCom;
    int[] poTiposCampos = new int[poCampos.length];
    for (i = 0; i < poCampos.length; i++) {
      poTiposCampos[i] = poList.getFields().get(poCampos[i]).getTipo();
    }
    if ( (poCampos.length == poCadenas.length) &&
        (poCampos.length == poTiposCampos.length)) {

      //creamos el objeto que nos comparara filas
      JOrdenacion loOrden = new JOrdenacion(poCampos, poTiposCampos, true, pbIgnorarCASE);

      //
      //creamos la fila a comparar
      //
      loFilaDatosCom = moFilaAComparar(poCampos, poCadenas);

      //
      //realizamos el filtrado
      //
      if (pbAPartirActual) {
        i = poList.getIndex();
      }
      else {
        i = 0;
      }
      //moOrdenacion.malCampos.toString()==poCampos.toString()
      for (; (i < poList.size()) && (!lbEncontrado); i++) {
        loFilaDatos = (IFilaDatos) poList.get(i);
        lbEncontrado = false;
        switch (plCompara) {
          case JListDatos.mclTIgual:
            lbEncontrado = (loOrden.compare(loFilaDatos, loFilaDatosCom) ==
                            JOrdenacion.mclIgual);
            break;
          case JListDatos.mclTMayor:
            lbEncontrado = (loOrden.compare(loFilaDatos, loFilaDatosCom) ==
                            JOrdenacion.mclMayor);
            break;
          case JListDatos.mclTMenor:
            lbEncontrado = (loOrden.compare(loFilaDatos, loFilaDatosCom) ==
                            JOrdenacion.mclMenor);
            break;
          case JListDatos.mclTMayorIgual:
            lbEncontrado = (loOrden.compare(loFilaDatos, loFilaDatosCom) ==
                            JOrdenacion.mclIgual) ||
                (loOrden.compare(loFilaDatos, loFilaDatosCom) ==
                 JOrdenacion.mclMayor);
            break;
          case JListDatos.mclTMenorIgual:
            lbEncontrado = (loOrden.compare(loFilaDatos, loFilaDatosCom) ==
                            JOrdenacion.mclIgual) ||
                (loOrden.compare(loFilaDatos, loFilaDatosCom) ==
                 JOrdenacion.mclMenor);
            break;
          case JListDatos.mclTDistinto:
            lbEncontrado = (loOrden.compare(loFilaDatos, loFilaDatosCom) !=
                            JOrdenacion.mclIgual);
            break;
          case JListDatos.mclTLike:
            lbEncontrado = (loFilaDatos.msCampo(poCampos[0]).indexOf(loFilaDatosCom.msCampo(poCampos[0])) >=0);
             break;
          default:
             lbEncontrado = false;
        }
        if (lbEncontrado) {
          poList.setIndex(i);
        }
      }
    }
    loFilaDatos=null;
    loFilaDatosCom=null;
    return lbEncontrado;
  }
  /**
    * Busqueda Binomial, exige que en los campos a buscar esten ordenados
    * @param poCampos Lista de los campos
    * @param poFilaDatos Fila datos
    * @return si se ha encontrado
    */
   private boolean BuscarBinomial(
           final JListDatos poList,final int[] poCampos, final String[] psValores, 
           final boolean pbIgnoreCASE) {
        int lMed = poList.size()/2;
        int lSup = poList.size()-1;
        int lMin = 0;
        int lCompara = 0;
        boolean lbEncontrado = false;
        //realizamos la busqueda binomial
        if (lSup >= 0 ){
            int[] loTiposCampos = new int[poCampos.length];
            //creamos la fila de datos y los tipos de campos a comparar
            for (int i = 0; i < poCampos.length; i++) {
                loTiposCampos[i] = poList.getFields().get(poCampos[i]).getTipo();
            }
            IFilaDatos loFilaDatos=moFilaAComparar(poCampos, psValores);
            //creamos el objeto de comparacion
            JOrdenacion loOrden = new JOrdenacion(poCampos, loTiposCampos, true, pbIgnoreCASE);
            //busqueda binomial para mas de 3 elementos
            while (((lSup-lMin)>3)&&(!lbEncontrado)){
                lCompara=loOrden.compare(loFilaDatos,(IFilaDatos) poList.get(lMed));
                if (lCompara==loOrden.mclIgual) {
                    poList.setIndex(lMed);
                    lbEncontrado=true;
                }else {
                    if (lCompara==loOrden.mclMayor){
                        lMin = lMed;
                    }else{
                        lSup = lMed;
                    }
                 }
                 lMed = lMin + ((lSup-lMin)/2);
            }
            //busqueda secuencial para 3 o menos elementos
            int i=lMin;
            while ((i<=lSup)&&(!lbEncontrado)){
                if (loOrden.compare(loFilaDatos,(IFilaDatos)poList.get(i))==JOrdenacion.mclIgual){
                    poList.setIndex(i);
                    lbEncontrado=true;
                }
                i++;
            }
            loFilaDatos=null;
            loTiposCampos=null;
            loOrden=null;
        }
        return lbEncontrado;
   }
   public static IFilaDatos moFilaAComparar(final int[] poCampos, final String[] poCadenas){
      //
      //creamos la fila a comparar
      //
      int i;
      int lMax = 0;
      for (i = 0; i < poCampos.length; i++) {
        if (poCampos[i] > lMax) {
          lMax = poCampos[i];

        }
      }
      String[] lsCadenas = new String[lMax + 1];

      for (i = 0; i < poCampos.length; i++) {
          if(poCadenas[i]==null){
            lsCadenas[poCampos[i]] = "";
          }else{
            lsCadenas[poCampos[i]] = poCadenas[i];
          }
      }
//      StringBuilder lsCadena = new StringBuilder();
//      for (i = 0; i <= lMax; i++) {
//        if (lsCadenas[i] == null) {
//          lsCadena.append(JFilaDatosDefecto.mcsSeparacion1);
//        }
//        else {
//          lsCadena.append(lsCadenas[i]);
//          lsCadena.append(JFilaDatosDefecto.mcsSeparacion1);
//        }
//      }
//      lsCadenas=null;
      return new JFilaDatosDefecto(lsCadenas);
   }
    
}
