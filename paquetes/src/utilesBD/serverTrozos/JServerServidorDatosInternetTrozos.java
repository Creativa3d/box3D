/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesBD.serverTrozos;

import ListDatos.IFilaDatos;
import utiles.red.IOpenConnection;
import ListDatos.JFilaCrearDefecto;
import ListDatos.JFilaDatosDefecto;
import ListDatos.JListDatos;
import utiles.red.JOpenConnectionDefault;
import ListDatos.JSelect;
import ListDatos.JServerServidorDatos;
import ListDatos.JServerServidorDatosBD;
import ListDatos.JServerServidorDatosInternet;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.zip.GZIPInputStream;

public class JServerServidorDatosInternetTrozos extends JServerServidorDatosInternet  implements IServerServidorDatosTrozos{
    private static IOpenConnection msoOpenConnection = new JOpenConnectionDefault();
    private int mlMin;
    private int mlMax;
    
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosInternetTrozos(JServerServidorDatosInternet poBD) {
        super(poBD.getURLBase1(), poBD.getNombreSelect(), poBD.getNombreGuardar());
        setTipo(poBD.getTipo());
        setIDSession(poBD.getIDSession());
        setLogin(poBD.getLogin());
        setConstrucEstruc(poBD.getConstrucEstruc());
        getParametros().setSoloLectura(poBD.getParametros().isSoloLectura());
        setSelect(poBD.getSelect());
    }


    public void setIntervaloDatos(int plMin, int plMax){
        mlMin = plMin;
        mlMax = plMax;
    }

    protected void recuperarInformacion(final JListDatos v, final JSelect poSelect, final String psTabla) throws Exception{
        //introduccion de campos a la select de sistema
        poSelect.setPassWord(msPassWord);
        poSelect.setPermisos(msPermisos);
        poSelect.setUsuario(msUsuario);
        //recuperacion de datos fisica
        switch(getTipo()){
          case JServerServidorDatos.mclTipoInternet:
            recuperarDatosInternet(v, poSelect,psTabla, false, false);
            break;
//          case JServerServidorDatos.mclTipoFichero:
//            recuperarDatosFichero(v, poSelect ,psTabla);
//            break;
          case JServerServidorDatos.mclTipoInternetComprimido:
          case JServerServidorDatos.mclTipoInternetComprimido_I_O:
            recuperarDatosInternet(v, poSelect,psTabla, true, false);
            break;
          case JServerServidorDatos.mclTipoInternetTexto:
            recuperarDatosInternet(v, poSelect,psTabla, false, true);
            break;
          default:
              throw new Exception(this.getClass().getName()+"(mlTipo)->Tipo de servidor incorrecto");

        }

    }
    private JListDatos  recuperarDatosInternet(final JListDatos v,final JSelect poSelect,final String psTabla,final boolean pbComprimido,final boolean pbSoloTexto)throws Exception  {
        JListDatos loCon = null;
        try{
            loCon = recuperarDatosInternetReal(v,poSelect,psTabla,pbComprimido,pbSoloTexto);
        }catch(Exception e){
            try{
                if(autentificar()){
                    loCon = recuperarDatosInternetReal(v,poSelect,psTabla,pbComprimido,pbSoloTexto);
                }else{
                    throw e;
                }
            }catch(Exception e1){
                throw e;
            }
        }
        return loCon;
    }

    private JListDatos  recuperarDatosInternetReal(final JListDatos v,final JSelect poSelect,final String psTabla,final boolean pbComprimido,final boolean pbSoloTexto)throws Exception  {
        //conectamos con url
        URLConnection connection=null;
        int lSizePagina = mlMax - mlMin;
        if(mlMin<mlMax){
            poSelect.setMETAPagina_size(lSizePagina);
            poSelect.setMETAPagina_actual(mlMin /(mlMax - mlMin));
         }
        if(pbSoloTexto){
          String psSelect = poSelect.msSQL(getSelect());
//          choiceEdu1.addItem(psSelect + ' ' + msURLBase1 + msNombreSelect);
          utiles.JDepuracion.anadirTexto(utiles.JDepuracion.mclINFORMACION,this.getClass().getName(),psSelect + ' ' + getURLBase1() + getNombreSelect());
          URL url = new URL(getURLBase1() + getNombreSelect() +
                            "?select=" + URLEncoder.encode(psSelect) +
                            (pbComprimido ? "&comprimido=si" : "&comprimido=no"));
          connection = msoOpenConnection.getConnection(url);
          //para evitar paginas estaticas
          connection.setUseCaches(false);
          //forzamos a que se ejecute el script
          connection.setDoOutput(true);
          //le enviamos los parametros
          PrintWriter out = new PrintWriter(
              connection.getOutputStream());
          out.close();
        }else{
          poSelect.setComprimido(pbComprimido);
          connection = enviarObjeto(getNombreSelect(), poSelect);
        }
        //creamos el buffer de lectura
        BufferedReader in=null;
        try{
            try{
                //1º intentamos con codificacion ISO-8859
                if (pbComprimido) {
                  in = (new BufferedReader(new InputStreamReader(
                          new GZIPInputStream(connection.getInputStream()), "ISO-8859-1")
                      ));
                } else {
                  in = (new BufferedReader(new InputStreamReader(
                          connection.getInputStream(), "ISO-8859-1")));
                }
            }catch(Throwable e){
                //si pega casque intentamos SIN codificacion
                if (pbComprimido) {
                  in = (new BufferedReader(new InputStreamReader(
                          new GZIPInputStream(connection.getInputStream()))
                      ));
                } else {
                  in = (new BufferedReader(new InputStreamReader(
                          connection.getInputStream())));
                }
            }

        //Ojo: Falta leer definicion de los campos
        //recorremos el buffer y rellenamos un vector de filas de datos
        //la primera linea siempre es chr(10), se hace asi porque si no casca
        //cuando sea el resultado vacio
        //los servidores antiguos si la primera linea no es "" devuelven en la 1º linea el error
        //FUTURO: los servidores modernos devuelven un xml, q contiene varios elementos, el error si lo hubiera y el numero de registros
            String inputLine = in.readLine();
            

            if(inputLine !=null && !inputLine.equals("")) {
//                try{
//                    SAXBuilder loBuid = new SAXBuilder();
//                    Document loDoc = loBuid.build(new CadenaLarga(inputLine));
//                    Element loError =Element.simpleXPath(loDoc.getRootElement(), "error");
//                    if(loError!=null){
//                        throw new Exception(loError.getValue());
//                    }
//                    Element loNum =Element.simpleXPath(loDoc.getRootElement(), "nregistros");
//                    if(loNum!=null){
//                        int lNumeroReg=-1;
//                        if(JConversiones.isNumeric(loNum.getValue())){
//                            lNumeroReg=(int)JConversiones.cdbl(loNum.getValue());
//                        }
//                        if(lNumeroReg>50){
//                            v.getJList().setIncrementoTamano(lNumeroReg);
//                        }
//                    }
//
//                }catch(Exception e){
                    throw new Exception(inputLine);
//                }
            }
            if(mlMin<mlMax){
                v.ensureCapacity(lSizePagina + 1);
            }
            boolean lbTroceo = mlMin<mlMax;
            int lFila = 0;
            inputLine = in.readLine();
            while ( inputLine != null && (!lbTroceo || (lFila<lSizePagina))) {
                //destransparentamos los cambios de linea
                if((inputLine.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea10)>0)||
                   (inputLine.indexOf(JFilaDatosDefecto.mccTransparentacionCambioLinea13)>0)){
                    inputLine = inputLine.replace(JFilaDatosDefecto.mccTransparentacionCambioLinea10, (char)10).replace(JFilaDatosDefecto.mccTransparentacionCambioLinea13,(char)13);
                }
                IFilaDatos loFila = JFilaCrearDefecto.getFilaCrear().getFilaDatos(psTabla);
                String[] lasArray = JFilaDatosDefecto.moArrayDatos(inputLine, JFilaDatosDefecto.mccSeparacion1);
                if(getParametros().isEliminarEspaciosDerechaSiempre()){
                    for(int i = 0 ; i < lasArray.length;i++){
                        lasArray[i] = JServerServidorDatosBD.rTrim(lasArray[i]);
                    }
                }
                loFila.setArray(lasArray);

                v.add(loFila);

                lFila++;
                inputLine = in.readLine();
            }
            return v;
        }finally{
            if(in!=null){
                in.close();
            }
        }
    }

}
