/*
 * ServidorDatos.java
 *
 * Created on 8 de julio de 2002, 21:27
 */
package ListDatos;


import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JRelacionesDef;
import ListDatos.estructuraBD.JRelacionesDefs;
import java.util.Comparator;
import utiles.*;

/**Utilidades del JListDatos, union de JListDatos, ...*/
public final class JUtilTabla {

    public static final int mclCombinarDiferencia = 3;

    /**
     * Anadimos todos los campos del poListO al poListD, se usara el prefijo de 
     * la tabla en el nombre de los nuevos campos
     * @param poListO
     * @param poListD
     * @throws java.lang.CloneNotSupportedException
     */
    public static void addFields(JListDatos poListO,  JListDatos poListD) throws CloneNotSupportedException{
         for(int i = 0 ; i < poListO.getFields().size(); i++){
             poListD.getFields().addField(poListO.getFields(i).Clone());
             poListD.getFields(poListD.getFields().size()-1).setNombre(
                     poListO.msTabla + "_" +
                     poListO.getFields(i).getNombre()
                     );
         }
    }
    /**
     * cargamos los field de poResult con los datos de los field de poTabla, los nokmbres de los campos
     * de poResult seran nombreTabla_nombreCampo
     * @param poResult
     * @param poTabla
     * @throws ListDatos.ECampoError
     */
    public static void cargarFields(JListDatos poResult, JListDatos poTabla) throws ECampoError{
        for(int i = 0 ; i < poTabla.getFields().size(); i++){
            poResult.getFields().get(poTabla.msTabla + "_" + poTabla.getFields(i).getNombre()).setValue(
                    poTabla.getFields(i).getString()
                    );
        }
    }
    /**
     * Antes de mandar una instancia de IServerEjecutar al IServerServidorDatos.ejecutarServer
     * se clona pq cuando se manda al servidor no esta en memoria local,
     * para igual el comportamiento de cuando se manda al servidor o
     * cuando esta en local
     *
     * @param poOrigen
     * @param poDestino
     * @throws ListDatos.ECampoError
     */
    public static void clonarEjecutarServer(JListDatos poOrigen, JListDatos poDestino) throws ECampoError{
        poDestino.clear();
        poOrigen.update(false, false);//de esta manera si la fila a guardar es igual a la ya existente no se marca como actualizado
        if(poOrigen.moveFirst()){
            do{
                poDestino.addNew();
                poDestino.getFields().cargar(poOrigen.getFields().moFilaDatos());
                poDestino.update(false);
                poDestino.moFila().setTipoModif( poOrigen.moFila().getTipoModif());
            }while(poOrigen.moveNext());
        }
        IListaElementos loBorrados = poOrigen.getListBorrados();
        for(int i = 0; i < loBorrados.size(); i++){
            poDestino.addNew();
            poDestino.getFields().cargar((IFilaDatos)loBorrados.get(i));
            poDestino.update(false);
            poDestino.moFila().setTipoModif(JListDatos.mclNada);
            poDestino.borrar(false);

        }
    }

    /**
     * NOUSAR: todavia sin implementar, poListaTablasConDatosRecuperados
     * @param poListaTablas
     * @param poRelaciones
     * @return
     * @throws java.lang.Exception
     */
    public static JListDatos crearJListDatos(
            IListaElementos poListaTablas,
            IListaElementos poRelaciones) throws Exception {
        JListDatos loResult = new JListDatos();
        //anadimos los campos de todas las tablas al JListDatos resultado
        for(int i = 0 ; i < poListaTablas.size();i++ ){
            JListDatos loList = (JListDatos) poListaTablas.get(i);
            addFields(loList, loResult);
        }
        //manda la primera tabla
        JListDatos loTablaMaestra =  (JListDatos) poListaTablas.get(0);
        if(loTablaMaestra.moveFirst()){
            do{
                loResult.addNew();
                //cargamos los valores de la primera tabla
                cargarFields(loResult, loTablaMaestra);
                //buscamos los datos relacionados del resto tablas a traves de relaciones
                for(int i = 0 ; i < poListaTablas.size();i++ ){
                    //recuperamos la tabla
                    JListDatos loList = (JListDatos) poListaTablas.get(i);
                    //recuperamos las relaciones de la tabla
                    JRelacionesDefs loRelaciones = getRelacionesDeTabla(poRelaciones, loList.msTabla);
                    //si existen relaciones
                    if(loRelaciones!=null){
                        //para cada relacion
                        for(int ii = 0; ii < loRelaciones.getListaRelaciones().size(); ii++){
                            JRelacionesDef loRelacion = loRelaciones.getRelacion(ii);
                            //recupermaos la tabla relacionada
                            JListDatos loListRelacionada = getListDatosDeTabla(poListaTablas, loRelacion.getTablaRelacionada());
                            //buscamos por los campos relacionados en la tabla relacionada
                            if(loListRelacionada.buscar(
                                    JListDatos.mclTIgual,
                                    getListaCamposRelacionados(loListRelacionada, loRelacion),
                                    getListaCamposValores(loList, loRelacion))){
                                //si existe datos por la relacion rellenamos los fields
                                cargarFields(loResult, loListRelacionada);
                            }

                        }
                    }
                    
                }
                loResult.update(false);

            }while(loTablaMaestra.moveNext());
        }
        return loResult;
    }
    private static JListDatos getListDatosDeTabla(
            IListaElementos poTablas,
            String psTabla){
        JListDatos loResult = null;
        for(int i = 0 ; i < poTablas.size() && loResult==null;i++ ){
            JListDatos loList = (JListDatos) poTablas.get(i);
            if(loList.msTabla.equalsIgnoreCase(psTabla) ){
                loResult = loList;
            }

        }
        return loResult;

    }

    private static int[] getListaCamposRelacionados(JListDatos loListRelacionada, JRelacionesDef loRelacion) {
        throw new RuntimeException("Not yet implemented");
    }

    private static String[] getListaCamposValores(JListDatos loList, JRelacionesDef loRelacion) {
        throw new RuntimeException("Not yet implemented");
    }
    private static JRelacionesDefs getRelacionesDeTabla(
            IListaElementos poRelaciones,
            String psTabla){
        JRelacionesDefs loResult = null;
        for(int i = 0 ; i < poRelaciones.size() && loResult==null;i++ ){
            JRelacionesDefs loRelaciones = (JRelacionesDefs) poRelaciones.get(i);
            if(loRelaciones.getTabla().equalsIgnoreCase(psTabla) ){
                loResult = loRelaciones;
            }
        }
        return loResult;
    }

    /**
     * Une 2 JListDatos, anadiendo todas las filas del segundo al primero
     * @param poList1 JListDatos a anadir filas
     * @param poList2 JListDatos fuente de filas
     */
    public static void unionAddAlPrimero(JListDatos poList1, JListDatos poList2, boolean pbClonarFilas) {
        poList1.eventosGestAnular();
        for(int i = 0 ; i < poList2.size(); i++){
            if(pbClonarFilas){
                poList1.add((IFilaDatos) ((IFilaDatos)poList2.get(i)).clone());
            }else{
                poList1.add(poList2.get(i));
            }
        }
        //rehacemos los punteros
        poList1.rehacerOrdenYFiltro();
        poList1.eventosGestActivar();
    }

    /**
     * Une 2 JListDatos
     * @param poList1 JListDatos a unir
     * @param poList2 JListDatos a unir
     * @return La union de los JListDatos Anteriores
     * @throws java.lang.CloneNotSupportedException
     */
    public static JListDatos moUnion(JListDatos poList1, JListDatos poList2) throws CloneNotSupportedException{
        JListDatos loResult = new JListDatos(poList1, false);
        unionAddAlPrimero(loResult, poList1, false);
        unionAddAlPrimero(loResult, poList2, false);
        return loResult;
    }

    /**
     * combina las tablas para formar una union de ellas
     * @return tabla combinada
     * @param poList1 ListDatos 1
     * @param poList2 ListDatos 2
     * @param poCampos1 Lista de campos de ListDatos 1
     * @param poCampos2 Lista de campos de ListDatos 2
     * @param pbCamposOrdenados indica si los campos estan ordenados en los listDatos
     * @param plTipoCombinacion Tipo combinacion, left, inner, esta en JSelectUnionTablas
     */
    public static JListDatos moCombinar(
            JListDatos poList1, JListDatos poList2, 
            int[] poCampos1,int[] poCampos2, 
            final boolean pbCamposOrdenados, 
            final int plTipoCombinacion) throws Exception {
         return moCombinar(poList1, poList2, poCampos1, poCampos2, pbCamposOrdenados, plTipoCombinacion, true, true);
    }
    /**
     * combina las tablas para formar una union de ellas
     * @return tabla combinada
     * @param poList1 ListDatos 1
     * @param poList2 ListDatos 2
     * @param poCampos1 Lista de campos de ListDatos 1
     * @param poCampos2 Lista de campos de ListDatos 2
     * @param pbCamposOrdenados indica si los campos estan ordenados en los listDatos
     * @param plTipoCombinacion Tipo combinacion, left, inner, esta en JSelectUnionTablas
     * @param pbCampos1 si se incluyen los campos del primer JListDatos
     * @param pbCampos2 si se incluyen los campos del segundo JListDatos
     */
    public static JListDatos moCombinar(
            JListDatos poList1, JListDatos poList2,
            int[] poCampos1,int[] poCampos2,
            final boolean pbCamposOrdenados,
            final int plTipoCombinacion,
            final boolean pbCampos1, final boolean pbCampos2) throws Exception {
        JListDatos loList=null;
        JListDatos loList1Clone=null;
        JListDatos loList2Clone=null;

        try{
            String[] lsNombres;
            String[] lsNombresCaption;
            int[] lalTipos;
            //creamos los nombres, nombres Caption y tipos con la union de ambas tablas
            lsNombres = new String[
                    (pbCampos1 ? poList1.getFields().count():0)+
                    (pbCampos2 ? poList2.getFields().count():0)];
            lsNombresCaption = new String[lsNombres.length];
            lalTipos = new int[lsNombres.length];
            if(pbCampos1){
                for(int i = 0; i< poList1.getFields().count(); i++){
                  lalTipos[i] = poList1.getFields().get(i).getTipo();
                  lsNombres[i] = poList1.getFields().get(i).getNombre();
                  lsNombresCaption[i] = poList1.getFields().get(i).getCaption();
                }
            }
            if(pbCampos2){
                for(int i = 0; i< poList2.getFields().count(); i++){
                  lalTipos[i+(pbCampos1 ? poList1.getFields().count():0)] = poList2.getFields().get(i).getTipo();
                  lsNombres[i+(pbCampos1 ? poList1.getFields().count():0)] = poList2.getFields().get(i).getNombre();
                  lsNombresCaption[i+(pbCampos1 ? poList1.getFields().count():0)] = poList2.getFields().get(i).getCaption();
                }
            }
            //creamos la nueva tabla vacia
            loList = new JListDatos(null,"", lsNombres, lalTipos,poCampos1, lsNombresCaption);
            //anulamos los eventos de gestion
            loList.eventosGestAnular();
            //para que los punteros de las tablas iniciales no se muevan clonamos las originales
            //si es tipo combinacion right damos la vuelta a los JListDatos
            if(plTipoCombinacion==JSelectUnionTablas.mclRight){
                JListDatos loAuxList1 = (JListDatos)poList1.clone();
                loList1Clone = (JListDatos)poList2.clone();
                loList2Clone = loAuxList1;
                int[] loAux = poCampos1;
                poCampos1 = poCampos2;
                poCampos2 = loAux;
            }else{
                loList1Clone = (JListDatos)poList1.clone();
                loList2Clone = (JListDatos)poList2.clone();
            }
            //rellenamos la tabla
            if(poCampos1.length == poCampos2.length){
              String[] lsValores = new String[poCampos2.length];
              if(loList1Clone.moveFirst()){
                  IFilaDatos loFilaDatos;
                  IFilaDatos loFilaDatos1Vacia = new JFilaDatosDefecto();
                  for(int i = 0; i < loList1Clone.getFields().count(); i++ ){
                        loFilaDatos1Vacia.addCampo("");
                  }
                  IFilaDatos loFilaDatos2Vacia = new JFilaDatosDefecto();
                  for(int i = 0; i < loList2Clone.getFields().count(); i++ ){
                        loFilaDatos2Vacia.addCampo("");
                  }
                  do{
                      loFilaDatos=loList1Clone.moFila();
                      //campos principales del JListDatos 1
                      for(int i=0;i<poCampos1.length;i++){
                        lsValores[i]=loFilaDatos.msCampo(poCampos1[i]);
                      }
                      boolean lbEncontrado = false;
                      boolean lbPrimeraVez=true;
                      //si en el segundo ListDatos existen varias filas que cumplen la cond. del primer ListDatos
                      //tambien se anaden
                      while( (lbEncontrado && plTipoCombinacion != mclCombinarDiferencia)||lbPrimeraVez  ) {
                          lbEncontrado = false;
                          if(lbPrimeraVez){
                            if(pbCamposOrdenados){
                                lbEncontrado = (loList2Clone.buscarBinomial(poCampos2, lsValores));
                                //la busq. binomial no te asegura q te posicione en el primer elemento q coincide
                                if(lbEncontrado){
                                    IFilaDatos loFilaAComparar = moFilaAComparar(
                                                                    lsValores,
                                                                    loList2Clone.getFields().size(),
                                                                    poCampos2);
                                    Comparator<IFilaDatos> loOrdenacion = loList2Clone.crearOrdenacion(poCampos2, true);
                                    boolean lbRetroceder = true;
                                    boolean lbEsIgual = true;
                                    //mientras retroceda y sea igual continuar
                                    while(lbRetroceder && lbEsIgual){
                                        //retrocedemos y vemos si a tenido exito
                                        lbRetroceder = loList2Clone.movePrevious();
                                        //comprobamos si la fila es igual a los valores
                                        lbEsIgual = loOrdenacion.compare(loFilaAComparar, loList2Clone.moFila())==JOrdenacion.mclIgual;
                                    }
                                    //si no es igual nos movemos a la fila siguiente
                                    if(!lbEsIgual && lbRetroceder){
                                        loList2Clone.moveNext();
                                    }

                                }
                            }else{
                                lbEncontrado = (loList2Clone.buscar(JListDatos.mclTIgual, poCampos2, lsValores, false));
                            }
                          }else{
                              if(loList2Clone.moveNext()){
                                lbEncontrado = (loList2Clone.buscar(JListDatos.mclTIgual, poCampos2, lsValores, true));
                              }
                          }
                          switch(plTipoCombinacion){
                              case mclCombinarDiferencia:
                                  //se incluyen todos los q no estan en la der.
                                  if(!lbEncontrado){
                                      loList.add(
                                        getFilaCompuesta(
                                              loFilaDatos, loFilaDatos2Vacia,
                                              pbCampos1, pbCampos2
                                              )
                                              );
                                  }
                                  break;
                              case JSelectUnionTablas.mclInner:
                                  //solo si estan en los dos se anade
                                  if(lbEncontrado){
                                      loList.add(getFilaCompuesta(
                                              loFilaDatos, loList2Clone.moFila(),
                                              pbCampos1, pbCampos2
                                              ));
                                  }
                                  break;
                              case JSelectUnionTablas.mclLeft:
                                  //siempre se anade uno, aunque no exista en la derecha
                                  if(lbEncontrado){
                                      loList.add(getFilaCompuesta(
                                              loFilaDatos, loList2Clone.moFila(),
                                              pbCampos1, pbCampos2
                                              ));
                                  }else{
                                      if(lbPrimeraVez){
                                        loList.add(
                                            getFilaCompuesta(
                                              loFilaDatos, loFilaDatos2Vacia,
                                              pbCampos1, pbCampos2
                                              ));
                                      }
                                  }
                                  break;
                              case JSelectUnionTablas.mclRight:
                                  //Siempre se anade uno, aunque no exista en la izq.
                                  //recordar que se a dado la vuelta en los campos antes del bucle
                                  //con lo que se empieza alreves, es decir, q realmente
                                  //poList1 es poList2 y viceversa
                                  if(lbEncontrado){
                                      loList.add(
                                        getFilaCompuesta(
                                              loList2Clone.moFila(), loFilaDatos,
                                              pbCampos1, pbCampos2
                                              ));
                                  }else{
                                      if(lbPrimeraVez){
                                          loList.add(
                                            getFilaCompuesta(
                                                  loFilaDatos2Vacia, loFilaDatos,
                                                  pbCampos1, pbCampos2
                                                  ));
                                      }
                                  }
                                  break;
                              default:
                                  throw new Exception("Union de tablas incorrecta");
                          }
                          lbPrimeraVez=false;
                     }
                  }while(loList1Clone.moveNext());
              }
            }
            //rehacemos los punteros
            loList.rehacerOrdenYFiltro();
            //reactivamos los eventos de gestion
            loList.eventosGestActivar();
            //nos movemos al primero, para q tenga algo
            loList.moveFirst();
        }finally{
            if(loList1Clone!=null){
                loList1Clone.close();
            }
            if(loList2Clone!=null){
                loList2Clone.close();
            }
        }
        //devolvemos la tabla
        return loList;
    }
    private static IFilaDatos getFilaCompuesta (
            IFilaDatos poFilaDatos1, IFilaDatos poFilaDatos2,
            final boolean pbCampos1, final boolean pbCampos2
            ){
        JFilaDatosDefecto loFila = new JFilaDatosDefecto();
        if(pbCampos1){
            loFila.setArray(poFilaDatos1.moArrayDatos());
        }
        if(pbCampos1){
            loFila.addCampos(poFilaDatos2.moArrayDatos());
        }
        return loFila;
    }
    /**
     * Clona un JListDatos de manera indep.
     * @return tabla combinada
     * @param poList ListDatos a clonar
     * @param pbClonarFilas Tb Clona las filas 
     */
    public static JListDatos clonarFilasListDatos(
            final JListDatos poList, final boolean pbClonarFilas) throws Exception {
        //creamos la nueva tabla vacia
        JListDatos loList = new JListDatos();
        loList.replicar(poList, false);
        //anulamos los eventos de gestion
        loList.eventosGestAnular();
        //rellenamos la tabla
        if(poList.moveFirst()){
          IFilaDatos loFilaDatos;
          do{
              if(pbClonarFilas){
                loFilaDatos=(IFilaDatos)poList.moFila().clone();  
              }else{
                loFilaDatos=poList.moFila();
              }
              loList.add(loFilaDatos);
              
              
          }while(poList.moveNext());
        }
        loList.rehacerOrdenYFiltro();

        if(loList.getFiltro()!=null && loList.getEsFiltrado()){
            loList.getFiltro().addCondicionAND(loList.getFiltro());
            loList.filtrar();
        }
        
        if(loList.getOrdenacion()!=null){
            loList.ordenar(loList.getOrdenacion());
        }
        //reactivamos los eventos de gestion
        loList.eventosGestActivar();
        //devolvemos la tabla
        return loList;
    }
    public static String getListDatos2String(final JListDatos poList){
        return getListDatos2String(poList, true);
    }
    public static String getListDatos2String(final JListDatos poList, boolean pbConNombreTabla){
        StringBuilder lsCadena = new StringBuilder(poList.size() * 20);
        String lsRetornoCarro = System.getProperty("line.separator");
        //almacenamos el estado actual
        JListDatosBookMark loMemento = poList.getMemento();
        //Cabezera
        if(pbConNombreTabla){
            lsCadena.append('<');
            lsCadena.append(poList.msTabla);
            lsCadena.append('>');
            lsCadena.append(lsRetornoCarro);
        }
        for(int i = 0; i < poList.getFields().count() ; i++ ){
            lsCadena.append(poList.getFields().get(i).getCaption());
            lsCadena.append('\t');
        }
        lsCadena.append(lsRetornoCarro);
        //recorremos el ListDatos almacenando los valores en un StringBuilder
        if(poList.moveFirst()){
            do{
                for(int i = 0; i < poList.getFields().count() ; i++ ){
                    switch (poList.getFields().get(i).getTipo()) {
                        case JListDatos.mclTipoNumeroDoble:
                            lsCadena.append(poList.getFields().get(i).getString().replace('.',','));
                            break;
                        case JListDatos.mclTipoMoneda3Decimales:
                            lsCadena.append(poList.getFields().get(i).getString().replace('.',','));
                            break;
                        case JListDatos.mclTipoMoneda:
                            lsCadena.append(poList.getFields().get(i).getString().replace('.',','));
                            break;
                        case JListDatos.mclTipoPorcentual3Decimales:
                            lsCadena.append(poList.getFields().get(i).getString().replace('.',','));
                            break;
                        case JListDatos.mclTipoPorcentual:
                            lsCadena.append(poList.getFields().get(i).getString().replace('.',','));
                            break;
                        default:
                            String lsCampo = poList.getFields().get(i).getString().replace('\t', ' ');
                            lsCadena.append(lsCampo.replace(lsRetornoCarro," "));
                            break;
                    }
                    lsCadena.append('\t');
                }
                lsCadena.append(lsRetornoCarro);
            }while(poList.moveNext());
        }
        //restablecemos el estado previamente guardado
        poList.setMemento(loMemento);

        //reemplazamos los carac. raros por espacios
        for(int i = 0 ; i<lsCadena.length();i++ ){
            if(lsCadena.charAt(i)==0){
                lsCadena.setCharAt(i, ' ');
            }
        }
        return lsCadena.toString();
    }


    private static IFilaDatos moFilaAComparar(String[] pasValores, int size, int[] poCampos2) {
        String[] lasValores = new String[size];
        for(int i = 0; i < poCampos2.length; i++){
            lasValores[poCampos2[i]] = pasValores[i];
        }
        return new JFilaDatosDefecto(lasValores);
    }
    public static JFieldDef addCampo(final JSelect moSelectEstatica, final JListDatos moListDatosEstatico,final String psNombreTabla, final JFieldDef poCampo){
        return addCampo(moSelectEstatica, moListDatosEstatico,psNombreTabla, poCampo, poCampo.getPrincipalSN());
    }

    public static JFieldDef addCampo(final JSelect moSelectEstatica, final JListDatos moListDatosEstatico,final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal){
        return addCampo(moSelectEstatica, moListDatosEstatico,psNombreTabla, poCampo, pbEsPrincipal, JSelectCampo.mclFuncionNada, false);
    }

    public static JFieldDef addCampo(final JSelect moSelectEstatica,final JListDatos moListDatosEstatico,final String psNombreTabla, final JFieldDef poCampo, final boolean pbEsPrincipal, final int plFuncion, final boolean pbAddAgrupado){
        try {
            JFieldDef loCampo = poCampo.Clone();
            if(psNombreTabla!=null && !psNombreTabla.equals("")){
                loCampo.setTabla(psNombreTabla);
            }
            loCampo.setPrincipalSN(pbEsPrincipal);
            moListDatosEstatico.getFields().addField(loCampo);
            moSelectEstatica.addCampo(plFuncion, psNombreTabla, loCampo.getNombre());
            if (pbAddAgrupado) {
                moSelectEstatica.addCampoGroup(psNombreTabla, loCampo.getNombre());
            }
            return loCampo;
        } catch (CloneNotSupportedException ex) {
            JDepuracion.anadirTexto("JUtilTabla", ex);
        }
        return poCampo;
    }

    public static JFieldDef addCampoFuncion(final JSelect moSelectEstatica, final JListDatos moListDatosEstatico,final String psNombreTabla, final JFieldDef poCampo, final int plFuncion){
        return addCampo(moSelectEstatica, moListDatosEstatico,psNombreTabla, poCampo, false, plFuncion, false);
    }
    public static JFieldDef addCampoYGrupo(final JSelect moSelectEstatica, final JListDatos moListDatosEstatico,final String psNombreTabla, final JFieldDef poCampo){
        return addCampo(moSelectEstatica, moListDatosEstatico,psNombreTabla, poCampo, false, JSelectCampo.mclFuncionNada, true);
    }
    /**Agrupa el JListDatos pasado por parametro
     * @param poList ListDatos a agrupar
     * @param poSelectCampos Lista de JSelectCampo
     * @return 
     * @throws java.lang.Exception
     */
    public static JListDatos agrupar(JListDatos poList, IListaElementos poSelectCampos) throws Exception {
        JListDatos loResult = new JListDatos();
        int[] lalCamposTodos=new int[poSelectCampos.size()];
        int[] lalCamposAgrup;
        int[] lalCamposAgrupDestino;
        int lAgrup=0;
        for(int i = 0 ; i < poSelectCampos.size(); i++) {
            JSelectCampo loCampo = (JSelectCampo) poSelectCampos.get(i);
            if(loCampo.getFuncion()==JSelectCampo.mclFuncionNada){
                lAgrup++;
            }
            lalCamposTodos[i]=poList.getFields().getIndiceDeCampo(loCampo.getNombre());
            JFieldDef loCampoDef = poList.getFields(loCampo.getNombre()).Clone();
            switch(loCampo.getFuncion()){
                case JSelectCampo.mclFuncionAvg:
                case JSelectCampo.mclFuncionCount:
                case JSelectCampo.mclFuncionSum:
                    loCampoDef.setTipo(JListDatos.mclTipoNumeroDoble);
                    break;
                default:
            }
            loResult.getFields().addField(loCampoDef);
        }
        lalCamposAgrup=new int[lAgrup];
        lalCamposAgrupDestino=new int[lAgrup];
        int lIndex=0;
        for(int i = 0 ; i < poSelectCampos.size(); i++) {
            JSelectCampo loCampo = (JSelectCampo) poSelectCampos.get(i);
            if(loCampo.getFuncion()==JSelectCampo.mclFuncionNada){
                lalCamposAgrup[lIndex]=poList.getFields().getIndiceDeCampo(loCampo.getNombre());
                lalCamposAgrupDestino[lIndex]=i;
                lIndex++;
            }
        }
//TODO OJO optimizacion, ordenar la lista, hay q probar la linea siguiente        
//        poList.ordenar(lalCamposAgrup);
        if(poList.moveFirst()){
            String[] lasValores = new String[lalCamposAgrup.length];
            do{
                for(int i = 0 ; i < lalCamposAgrup.length; i++) {
                    lasValores[i] = poList.getFields(lalCamposAgrup[i]).getString();
                }
//TODO OJO optimizacion, ordenar la lista, hay q probar las lineas siguientes        
//                JOrdenacion loOrden = new JOrdenacion(lalCamposAgrupDestino, poList.getFields().malTipos(), true, true);
//                IFilaDatos loFilaComparar = JListDatosBuscar.moFilaAComparar(lalCamposAgrupDestino, lasValores);
//                if(loOrden.compare(loResult.moFila(), loFilaComparar)!=loOrden.mclIgual){
                if(!loResult.buscar(JListDatos.mclTIgual, lalCamposAgrupDestino, lasValores)){
                    loResult.addNew();
                }
                for(int i = 0 ; i < poSelectCampos.size(); i++) {
                    JSelectCampo loCampo = (JSelectCampo) poSelectCampos.get(i);
                    if(loCampo.getFuncion()==JSelectCampo.mclFuncionNada){
                        loResult.getFields(i).setValue(poList.getFields(lalCamposTodos[i]).getString());
                    }else{
                        switch(loCampo.getFuncion()){
                            case JSelectCampo.mclFuncionAvg:
                                break;
                            case JSelectCampo.mclFuncionCount:
                                loResult.getFields(i).setValue(loResult.getFields(i).getInteger()+1);
                                break;
                            case JSelectCampo.mclFuncionMax:
                            case JSelectCampo.mclFuncionMin:
                                if(loResult.getFields(i).isVacio()){
                                    loResult.getFields(i).setValue(poList.getFields(lalCamposTodos[i]).getString());
                                } else {
                                    String[] las = new String[i+1];
                                    las[i]=poList.getFields(lalCamposTodos[i]).getString();
                                    JFilaDatosDefecto loA = new JFilaDatosDefecto(las);
                                    int lCom = JOrdenacion.mlcompare(loResult.moFila(), loA, new int[]{i}, new int[]{loResult.getFields(i).getTipo()}, true);
                                    if(lCom<0 && loCampo.getFuncion()==JSelectCampo.mclFuncionMax){
                                        loResult.getFields(i).setValue(poList.getFields(lalCamposTodos[i]).getString());
                                    }
                                    if(lCom>0 && loCampo.getFuncion()==JSelectCampo.mclFuncionMin){
                                        loResult.getFields(i).setValue(poList.getFields(lalCamposTodos[i]).getString());
                                    }
                                }
                                break;
                            case JSelectCampo.mclFuncionSum:
                                loResult.getFields(i).setValue(loResult.getFields(i).getDouble()+poList.getFields(lalCamposTodos[i]).getDouble());
                                break;
                            default:
                                loResult.getFields(i).setValue(poList.getFields(lalCamposTodos[i]).getString());
                        }
                    }
                }
                
                
                loResult.update(false);
                
            }while(poList.moveNext());
        }
        
        return loResult;
    }

  
    public static JListDatos getListDatosDeIndices(JListDatos poList, int[] palIndexs) throws CloneNotSupportedException{
        JListDatos loResult = new JListDatos(poList, false);
        for(int i : palIndexs){
            loResult.add((IFilaDatos) poList.get(i).clone());
        }
        
        return loResult;
    }
}
