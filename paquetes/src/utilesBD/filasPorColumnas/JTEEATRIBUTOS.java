/*
 * JTEEATRIBUTOS.java
 *
 * Created on 6 de agosto de 2008, 16:47
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package utilesBD.filasPorColumnas;

import ListDatos.ECampoError;
import ListDatos.IFilaDatos;
import ListDatos.IResultado;
import ListDatos.IServerServidorDatos;
import ListDatos.JActualizarConj;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import ListDatos.JResultado;
import ListDatos.estructuraBD.JFieldDef;
import java.io.Serializable;
import utiles.IListaElementos;

public class JTEEATRIBUTOS implements Cloneable, Serializable {
    private static final long serialVersionUID = 1L;
    
    private JListDatos moList;
    private JTEEATRIBUTOSParam moParam;
    private transient IServerServidorDatos moServer = null;

    
    public JTEEATRIBUTOS() {
    }
    /** Creates a new instance of JTEEATRIBUTOS */
    public JTEEATRIBUTOS(IServerServidorDatos poServer) {
        moServer=poServer;
    }

    public JTEEATRIBUTOS(IServerServidorDatos poServer,final JTEEATRIBUTOSParam poParam) {
        moServer=poServer;
        setParametros(poParam);
    }

    public void refrescar(boolean pbPasarACache, boolean pbLimpiarCache) throws Exception {
        //creamos el moList
        moList = new JListDatos();
        //
        //anadimos las columnas, campos clave
        //
        for(int i = 0 ; i < moParam.malPosiCodigos.length; i++){
            JFieldDef loField = moParam.moAtrib.getFields(moParam.malPosiCodigos[i]).Clone();
            loField.setPrincipalSN(true);
            moList.getFields().addField(loField);
        }
        //
        //anadimos las columnas, atributos def
        //
        if(moParam.moAtribDef.moveFirst()){
            do{
                int lTamano = 0;
                int lTipo = JListDatos.mclTipoCadena;
                if(moParam.mlPosiTamanoDef>=0){
                    lTamano = moParam.moAtribDef.getFields(moParam.mlPosiTamanoDef).getInteger();
                }
                if(moParam.mlPosiTipoDef>=0){
                    lTipo = moParam.moAtribDef.getFields(moParam.mlPosiTipoDef).getInteger();
                }

                moList.getFields().addField(new JFieldDef(
                        lTipo,
                        moParam.moAtribDef.getFields(moParam.mlPosiCodAtribDef).getString(),
                        moParam.moAtribDef.getFields(moParam.mlPosiNombreDef).getString(),
                        false,
                        lTamano
                        ));
            }while(moParam.moAtribDef.moveNext());
        }
        //
        //rellenamos los datos
        //
        
        //ordenamos por eficiencia
        moParam.moAtrib.ordenar(moParam.malPosiCodigos);
        //recorremos los atributos y los ponemos en las columnas correspondiente
        if(moParam.moAtrib.moveFirst()){
            String lsClave=null;
            StringBuilder lsAux = new StringBuilder();
            do{
                //borramos el StringBuilder
                if(lsAux.length()>0){
                    lsAux=new StringBuilder();
                }
                //componemos la clave
                for(int i = 0 ; i < moParam.malPosiCodigos.length; i++){
                    lsAux.append(
                            moParam.moAtrib.getFields(moParam.malPosiCodigos[i]).getString()
                            );
                    lsAux.append(JFilaDatosDefecto.mcsSeparacion1);
                }
                //si la clave compuesta es <> q la anterior
                //anadimos registro
                if(lsClave==null ||
                   !lsClave.equals(lsAux.toString())){
                    //si no es la primera vez
                    if(lsClave!=null){
                        moList.update(false);
                    }

                    lsClave=lsAux.toString();
                    moList.addNew();
                    //rellenamos los campos clave
                    for(int i = 0 ; i < moParam.malPosiCodigos.length; i++){
                        JFieldDef loField = moParam.moAtrib.getFields(moParam.malPosiCodigos[i]);
                        moList.getFields(i).setValue(loField.getString());
                    }
                }
                //establecemos el atributo en la columna correcta
                JFieldDef loCampo = moList.getFields().get(
                        moParam.moAtrib.getFields(moParam.mlPosiCodAtrib).getString()
                        );
                if(loCampo!=null){
                    loCampo.setValue(
                            moParam.moAtrib.getFields(moParam.mlPosiValor).getString()
                            );
                }
            }while(moParam.moAtrib.moveNext());
            moList.update(false);
        }

        
        if(moList.moveFirst()){
            do{
                moList.moFila().setTipoModif(JListDatos.mclNada);
            }while(moList.moveNext());
        }
    }

    public void setParametros(final JTEEATRIBUTOSParam poParam){
        moParam = poParam;
    }
    public JTEEATRIBUTOSParam getParametros(){
        return moParam;
    }
    public JListDatos getList() {
        return moList;
    }
    public void setList(JListDatos poList) {
        moList=poList;
    }

    public void addFilaPorClave(IFilaDatos poFila) throws Exception {
        
    }

    public boolean getPasarCache() {
        return false;
    }
    
    public IResultado guardar() throws Exception {
        boolean lbCambios = false;
        //
        //borramos los datos del param original q se han borrado
        //
        //recogemos los campos de los codigos
        int[] lalCodigos = moParam.getCodigosAtributosCampos();
        String[] lasValores = new String[lalCodigos.length];
        IListaElementos loList =  moList.getListBorrados();
        for(int lfila = 0 ; lfila < loList.size(); lfila++){
            IFilaDatos loFila = (IFilaDatos) loList.get(lfila);
            //recogemos los valores de los codigos menos del atributo en cuestion
            for(int i = 0 ; i < moParam.malPosiCodigos.length; i++){
                lasValores[i] = loFila.msCampo(i);
            }
            //para cada atributo
            for(int i = moParam.malPosiCodigos.length; i < moList.getFields().size(); i++){
                //si existe el nombre, esto lo hacemos asi por si ponemos campos ficticios
                if(!(moList.getFields(i).getNombre()==null || 
                     moList.getFields(i).getNombre().equals(""))){
                    //ponemos el codigo atributo
                    lasValores[lasValores.length-1] = moList.getFields(i).getNombre();
                    //buscamos en la tabla de atributos real
                    //si esta lo borramos
                    if(moParam.moAtrib.buscar(
                            JListDatos.mclTIgual,
                            lalCodigos,
                            lasValores)){
                          moParam.moAtrib.borrar(false);
                          lbCambios=true;
                    }
                }
            }
        }
        //
        //Si esta en param original y no en atributos borrar
        //
        if(moParam.moAtrib.moveFirst()){
            String[] lasValoresAux = new String[moParam.malPosiCodigos.length];
            boolean lbRetroceder = false;
            do{
                //retrocedemos uno en el caso de q en el anterior bucle se haya borrado uno
                if(lbRetroceder){
                    lbRetroceder=false;
                    moParam.moAtrib.movePrevious();
                }

                //valores de claves las claves primarias
                for(int i = 0 ; i < moParam.malPosiCodigos.length; i++){
                    lasValoresAux[i] = moParam.moAtrib.getFields(moParam.malPosiCodigos[i]).getString();
                }
                //si el param original no es encontrado en la lista de JTEEATRIBUTOS es q se ha borrado
                //de JTEEATRIBUTOS por lo q hay q borrarlo del original
                if(!moList.buscar(
                        JListDatos.mclTIgual,
                        moParam.malPosiCodigos,
                        lasValoresAux
                        )){
                    moParam.moAtrib.borrar(false);
                    lbCambios=true;
                    //si borramos debemos retroceder uno, por el movenext q sigue
                    lbRetroceder=true;
                }
            //si solo queda uno despues de borrar el movenext siempre falso, por lo q 
            //si es retroceder (ha habido un borrado en el ult. bucle y movelast (Existe al menos uno y es el ultimo))
            //hay q repetir el bucle
            }while(moParam.moAtrib.moveNext() || (lbRetroceder && moParam.moAtrib.moveLast()));
        }
        //
        //recorremos la lista de datos, y vemos los editados y anadidos
        //
        if(moList.moveFirst()){
            do{
                //si algun dato ha cambiado
                if(moList.moFila().getTipoModif() != JListDatos.mclNada){
                    lbCambios = true;
                    //recogemos los valores de los codigos menos del atributo en cuestion
                    for(int i = 0 ; i < moParam.malPosiCodigos.length; i++){
                        lasValores[i] = moList.getFields(i).getString();
                    }
                    //para cada atributo
                    for(int i = moParam.malPosiCodigos.length; i < moList.getFields().size(); i++){
                        //si existe el nombre, esto lo hacemos asi por si ponemos campos ficticios
                        if(!(moList.getFields(i).getNombre()==null || 
                             moList.getFields(i).getNombre().equals(""))){
                            
                            //ponemos el codigo atributo
                            lasValores[lasValores.length-1] = moList.getFields(i).getNombre();
                            //buscamos en la tabla de atributos real
                            //si no esta anadimos
                            boolean lbEncon = false;
                            if(!moParam.moAtrib.buscar(
                                    JListDatos.mclTIgual,
                                    lalCodigos,
                                    lasValores)){
                                  moParam.moAtrib.addNew();
                                  for(int lPosiValor = 0 ; lPosiValor < moParam.malPosiCodigos.length; lPosiValor++){
                                      moParam.moAtrib.getFields(moParam.malPosiCodigos[lPosiValor]).setValue(lasValores[lPosiValor]);
                                  }                              
                                  moParam.moAtrib.getFields(moParam.mlPosiCodAtrib).setValue(moList.getFields(i).getNombre());
                            }else{
                                lbEncon = true;
                            }
                            //establecemos el valor
                            moParam.moAtrib.getFields(moParam.mlPosiValor).setValue(moList.getFields(i).getString());
                            //si el atributo es vacio no se guarda o se borra si ya existia
                            if(moParam.moAtrib.getFields(moParam.mlPosiValor).isVacio()){
                                if(lbEncon){
                                    moParam.moAtrib.borrar(false);
                                }else{
                                    moParam.moAtrib.cancel();
                                }
                            }else{
                                moParam.moAtrib.update(false);
                            }
                        }
                    }
                }
            }while(moList.moveNext());
            //eliminamos los atributos a nulo
            if(moParam.moAtrib.moveFirst()){
                do{
                    if(moParam.moAtrib.getFields(moParam.mlPosiValor).isVacio()){
                        moParam.moAtrib.borrar(false);
                        moParam.moAtrib.movePrevious();
                    }
                }while(moParam.moAtrib.moveNext());
            }
        }
        IResultado loResult;
        if(lbCambios){
            //creamos la lista de actualizaciones y ejecutmaos
            JActualizarConj loAct = new JActualizarConj("","","");
            loAct.crearUpdateAPartirList(moParam.moAtrib);
            loResult = moServer.ejecutarServer(loAct);
            if(loResult.getBien()){
                //si todo bien quitamos las marcas de edicion de la tabla real 
                //y de la virtual
                if(moParam.moAtrib.moveFirst()){
                    do{
                        moParam.moAtrib.moFila().setTipoModif(JListDatos.mclNada);
                    }while(moParam.moAtrib.moveNext());
                }
                if(moList.moveFirst()){
                    do{
                        moList.moFila().setTipoModif(JListDatos.mclNada);
                    }while(moList.moveNext());
                }
            }
        }else{
            loResult = new JResultado("", true);
        }
        return loResult;
    }
    public void setServidor(final IServerServidorDatos poServer){
        moServer = poServer;
    }
    public Object clone(){
        JTEEATRIBUTOS loAtrib = null;
        try {
            loAtrib = (JTEEATRIBUTOS) super.clone();
            loAtrib.moList = moList.Clone();
        } catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return loAtrib;
    }
    public JFieldDef getField(String psNombre){
        return moList.getFields().get(psNombre);
    }
    public JFieldDef getField(int plIndex){
        return moList.getFields().get(plIndex);
    }
    public void clear() throws ECampoError{
        moList.clear();
        moList.getFields().limpiar();
        moParam=null;
    }
    public void replicar(JTEEATRIBUTOS poAtrib) throws Exception{
        clear();
        if(poAtrib.getList().moveFirst()){
            do{
                getList().add(poAtrib.getList().moFila());
            }while(poAtrib.getList().moveNext());
        }
        getList().moveFirst();
        moParam = (JTEEATRIBUTOSParam) poAtrib.moParam.clone();
        
    }
}
