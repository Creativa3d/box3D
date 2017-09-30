/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIxAvisos.avisos;

import ListDatos.IResultado;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.estructuraBD.JFieldDef;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.mail.Flags;
import javax.mail.Folder;
import javax.mail.FolderClosedException;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.search.AndTerm;
import javax.mail.search.ComparisonTerm;
import javax.mail.search.MessageIDTerm;
import javax.mail.search.ReceivedDateTerm;
import javax.mail.search.SearchTerm;
import utiles.IListaElementos;
import utiles.JCadenas;
import utiles.JDateEdu;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIxAvisos.tablasExtend.JTEEGUIXMENSAJESBD;


public class JGUIxAvisosCorreoLeer implements Cloneable {


    public static final int mclSeguridadNada = JGUIxAvisosCorreoEnviar.mclSeguridadNada;
    public static final int mclSeguridadSSL = JGUIxAvisosCorreoEnviar.mclSeguridadSSL;
    public static final int mclSeguridadstarttls = JGUIxAvisosCorreoEnviar.mclSeguridadstarttls;
    
    public static final String mcsServidorPOP3 = "POP3";
    public static final String mcsServidorIMAP = "IMAP";            
    private String msCarpetaCorreo;
    private JDateEdu moFechaDesde;
    private JDateEdu moFechaHasta;
    private transient Session moSesion;
    private transient Store moStore;
    
    private String msTipoEntrante;
    
    //datos conexion
    private String msServidor;
    private String msUsuario;
    private String msPassword;
    private int mlPuerto=0;
    private int mlSeguridad=JGUIxAvisosCorreoEnviar.mclSeguridadNada;
    private Folder moFolder;
    private final JGUIxAvisosCorreo moPadre;
    
    public JGUIxAvisosCorreoLeer(JGUIxAvisosCorreo poPadre){
        moPadre=poPadre;
    }
    
    public void inicializar(){
        
        if(moStore!=null){
            try{
                moStore.close();
                moStore=null;
            }catch(Exception e){}
        }
        moSesion=null;
        
    }
    
    @Override
    protected Object clone() throws CloneNotSupportedException {
        JGUIxAvisosCorreoLeer loLeer = (JGUIxAvisosCorreoLeer) super.clone();
        if(moFechaDesde!=null){
            loLeer.moFechaDesde = (JDateEdu) moFechaDesde.clone();
        }
        
        if(moFechaHasta!=null){
            loLeer.moFechaHasta = (JDateEdu) moFechaHasta.clone();
        }
        loLeer.inicializar();
        return loLeer;
    }
    
    public void setServidor(String psServidor){
        inicializar();
        msServidor=psServidor;
    }
    public void setUsuario(String psUsuario){
        inicializar();
        msUsuario=psUsuario;
    }
    public void setPassword(String psPassword){
        inicializar();
        msPassword=psPassword;
    }
    
    
    public void setCarpetaCorreo(String psCarpetaCorreo) {
        msCarpetaCorreo = psCarpetaCorreo;
    }

    public void setFechaDesde(JDateEdu poFechaDesde) {
        this.moFechaDesde = poFechaDesde;
    }

    public void setFechaHasta(JDateEdu poFechaHasta) {
        this.moFechaHasta = poFechaHasta;
    }
    
    public void setTipoEntrante(String psTipoEntrante){
        inicializar();
        msTipoEntrante=psTipoEntrante;
    }
    
    /**
     * @return the mlCorreoPuerto
     */
    public int getPuerto() {
        return mlPuerto;
    }

    /**
     * @param mlCorreoPuerto the mlCorreoPuerto to set
     */
    public void setPuerto(int mlCorreoPuerto) {
        inicializar();
        this.mlPuerto = mlCorreoPuerto;
    }

    /**
     * @return the mlCorreoSeguridad
     */
    public int getSeguridad() {
        return mlSeguridad;
    }

    /**
     * @param mlCorreoSeguridad the mlCorreoSeguridad to set
     */
    public void setSeguridad(int mlCorreoSeguridad) {
        this.mlSeguridad = mlCorreoSeguridad;
        inicializar();
    }
    
    public boolean isCompleto(){
        return !JCadenas.isVacio(msUsuario) && !JCadenas.isVacio(msServidor);
    }

    public void marcarRegistro(List<String> loIdsMensajesSeleccionados) throws Exception {
        if (getTipoEntrante().equalsIgnoreCase(mcsServidorIMAP)) {

            conectar();
            if (moStore != null) {
                Folder folder = (getCarpetaCorreo() != null ? moStore.getFolder(getCarpetaCorreo()) : moStore.getFolder("INBOX"));
                folder.open(Folder.READ_WRITE);
                try{
                    Message[] mensajes = folder.getMessages();

                    // Para cada mensaje de la bandeja de entrada, comprobamos si es uno de los seleccionados y si no está marcado.
                    for (Message message : mensajes) {
                        if (loIdsMensajesSeleccionados.contains(new JGUIxAvisosBuildMensaje().getMensaje(message).getIdMensaje())
                                && !message.getFlags().contains(Flags.Flag.FLAGGED)) {
                            message.setFlag(Flags.Flag.FLAGGED, true);
                        }

                    }
                }finally{
                    folder.close(true);
                }
            }

        }
    }
    
    private void conectar() throws Exception{
        if (moSesion == null ){
            try{
                Properties loProp = new Properties();
                int lPuerto = getPuerto();
                
                if(mcsServidorIMAP.equalsIgnoreCase( getTipoEntrante())){
                    if (getSeguridad() == mclSeguridadstarttls) {
                     
                        if(lPuerto == 0){
                            lPuerto=110;
                        }                        
                        loProp.setProperty("mail.imap.starttls.enable", "true");
                        loProp.setProperty("mail.imaps.ssl.trust", "*");
                        loProp.setProperty("mail.store.protocol", "imap");
                    } else if (getSeguridad() == mclSeguridadSSL) {
                        if(lPuerto == 0){
                            lPuerto=993;
                        }
                        loProp.put("mail.imap.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        loProp.put("mail.imap.socketFactory.fallback", false);
                        loProp.put("mail.store.protocol", "imaps");
                    } else {
                        if(lPuerto == 0){
                            lPuerto=143;
                        }                        
                        
                    }
                } else {
                    if (getSeguridad() == mclSeguridadstarttls) {
                     
                        if(lPuerto == 0){
                            lPuerto=110;
                        }                        
                        loProp.setProperty("mail.pop3.starttls.enable", "true");
                        loProp.setProperty("mail.pop3.ssl.trust", "*");
                        loProp.setProperty("mail.store.protocol", "imap");

                    } else if (getSeguridad() == mclSeguridadSSL) {
                        if(lPuerto == 0){
                            lPuerto=995;
                        }
                        loProp.setProperty("mail.pop3.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
                        loProp.setProperty("mail.pop3.socketFactory.fallback", "false");
                        loProp.setProperty("mail.pop3.port",  String.valueOf(lPuerto));
                        loProp.setProperty("mail.pop3.socketFactory.port", String.valueOf(lPuerto));
                    } else {
                        if(lPuerto == 0){
                            lPuerto=110;
                        }                        
                        
                    }
        
                }         
                moSesion = Session.getInstance(loProp, null);
                moStore = moSesion.getStore(getTipoEntrante().toLowerCase());
                moStore.connect(getServidor(), lPuerto, getUsuario(), getPassword());
            }catch (FolderClosedException ex) {
                Properties props = new Properties();
                moSesion = Session.getInstance(props, null);
                moStore = moSesion.getStore(getTipoEntrante().toLowerCase());
                moStore.connect(getServidor(), getPuerto(), getUsuario(), getPassword());
            }
        }
       
    }
    
    private void getSubcarpetas(Folder poCarpeta, IListaElementos poListaCarpetas) throws MessagingException{

        
            Folder[] loSubCarpetas = poCarpeta.list();
            for (int i = 0; i< loSubCarpetas.length; i++){
                poListaCarpetas.add(loSubCarpetas[i].getFullName());
                System.out.println(loSubCarpetas[i].getFullName());
                if (loSubCarpetas[i].list().length!=0)
                    getSubcarpetas(loSubCarpetas[i],poListaCarpetas);
            }

       
    }
    
     public IListaElementos getListaCarpetas() throws Exception{
        IListaElementos loCarpetas = new JListaElementos();
        conectar();
        
        if (moStore!=null){
            Folder[] listFolders = moStore.getDefaultFolder().list();
            for (int i = 0; i<listFolders.length; i++){
                System.out.println(listFolders[i].getFullName());
                loCarpetas.add(listFolders[i].getFullName());
                if ((listFolders[i].getType() & Folder.HOLDS_FOLDERS) ==Folder.HOLDS_FOLDERS)
                    getSubcarpetas (listFolders[i], loCarpetas);
            }
           
        }
        return loCarpetas;
    }
     
    private boolean estaEnRangoFechas(JDateEdu poFecha, JDateEdu poFechaDesde, JDateEdu poFechaHasta) {
        if (poFechaDesde == null && poFechaHasta == null) {
            return true;
        } else if (poFechaDesde != null && poFechaHasta != null) {
            if ((poFecha.compareTo(poFechaDesde) == JDateEdu.mclFechaMayor || poFecha.compareTo(poFechaDesde) == JDateEdu.mclFechaIgual)
                    && (poFecha.compareTo(poFechaHasta) == JDateEdu.mclFechaMenor || poFecha.compareTo(poFechaHasta) == JDateEdu.mclFechaIgual)) {
                return true;
            }
        } else if (poFechaDesde != null && poFechaHasta == null) {
            if (poFecha.compareTo(poFechaDesde) == JDateEdu.mclFechaMayor || poFecha.compareTo(poFechaDesde) == JDateEdu.mclFechaIgual) {
                return true;
            }
        } else if (poFechaDesde == null && poFechaHasta != null) {
            if (poFecha.compareTo(poFechaHasta) == JDateEdu.mclFechaMenor || poFecha.compareTo(poFechaHasta) == JDateEdu.mclFechaMayor) {
                return true;
            }
        }
        return false;
    }
    public List<Message> getCorreosMessajes() throws Exception{

        List<Message> loList = new ArrayList<Message>();

        Message [] mensajes = moFolder.getMessages();

        for(int i = 0 ; i < mensajes.length; i++){
            Message loMensaje = mensajes[i];
            JDateEdu loFechaMensaje = new JDateEdu(loMensaje.getSentDate());
            loFechaMensaje.setHora(0);
            loFechaMensaje.setMinuto(0);
            loFechaMensaje.setSegundo(0);

            if (!loMensaje.getFlags().contains(Flags.Flag.FLAGGED) && estaEnRangoFechas(loFechaMensaje, moFechaDesde, moFechaHasta)){
                try {
                    loList.add(loMensaje);
                }catch (Exception ex){
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                    JDepuracion.anadirTexto(getClass().getName(),"Asunto: "+ loMensaje.getSubject()+"\n");
                }
            }
        }

        return loList;
    }
    private IListaElementos<JMensaje> getCorreosMensajesDeMESSAGES(Message [] mensajes) throws MessagingException{
        IListaElementos<JMensaje> loCorreos = new JListaElementos<JMensaje>();
        if (mensajes != null){
            for (Message loMensaje : mensajes){
                try {
                    loCorreos.add(new JGUIxAvisosBuildMensaje().getMensaje(loMensaje));
                }catch (Exception ex){
                    JDepuracion.anadirTexto(getClass().getName(), ex);
                    JDepuracion.anadirTexto(getClass().getName(),"Asunto: "+ loMensaje.getSubject()+"\n");
                }
            }
        }      


        return loCorreos;
        
    }
    public IListaElementos<JMensaje> getCorreosMessajesDesdeUID(long plUID) throws Exception{
        Message [] mensajes;
        mensajes = moFolder.getMessages((int)plUID, moFolder.getMessageCount());
        IListaElementos<JMensaje> loCorreos = getCorreosMensajesDeMESSAGES(mensajes);
        return loCorreos;
    }
    
    public void openFolder() throws Exception{
        closeFolder();
        conectar();
        if (moStore != null){
            moFolder = (getCarpetaCorreo() != null ? moStore.getFolder(getCarpetaCorreo()) : moStore.getFolder("INBOX"));
            moFolder.open(Folder.READ_WRITE);
        }
    }
    public void closeFolder() {
        if(moFolder!=null){
            try {
                moFolder.close(true);
            } catch (MessagingException ex) {
                JDepuracion.anadirTexto(JGUIxAvisosCorreoLeer.class.getName(), ex);
            }
        }
        moFolder=null;
    }
    
    public IListaElementos<JMensaje> getCorreos() throws Exception{
        IListaElementos<JMensaje> loCorreos = new JListaElementos();
        openFolder();
        if(moFolder!=null){
            try{
                List<Message> mensajes = getCorreosMessajes();
                if (mensajes != null){
                    for (Message loMensaje : mensajes){
                        try {
                            loCorreos.add(new JGUIxAvisosBuildMensaje().getMensaje(loMensaje));
                        }catch (Exception ex){
                            JDepuracion.anadirTexto(getClass().getName(), ex);
                            JDepuracion.anadirTexto(getClass().getName(),"Asunto: "+ loMensaje.getSubject()+"\n");
                        }
                    }
                }                
            }finally{
                closeFolder();
            }
        }
        return loCorreos;
    }
    public synchronized void recibirYGuardar() throws Exception{
        if(!JCadenas.isVacio(this.getCarpetaCorreo())){
            JSelect loSelect = new JSelect(JTEEGUIXMENSAJESBD.msCTabla);
            loSelect.addCampoGroupYCampo(JTEEGUIXMENSAJESBD.msCTabla, JTEEGUIXMENSAJESBD.getFOLDERNombre());
            loSelect.addCampo(JSelectCampo.mclFuncionMax,JTEEGUIXMENSAJESBD.msCTabla, JTEEGUIXMENSAJESBD.getCODIGONombre());

            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTIgual
                    , JTEEGUIXMENSAJESBD.lPosiFOLDER
                    , JTEEGUIXMENSAJESBD.getIdentificadorFOLDER(moPadre, getCarpetaCorreo()));
            loFiltro.addCondicionAND(JListDatos.mclTDistinto, JTEEGUIXMENSAJESBD.lPosiIDMENSAJE, "");
            loFiltro.inicializar(new JTEEGUIXMENSAJESBD(null).getList());
            loSelect.getWhere().addCondicionAND(loFiltro);

            JListDatos loList = new JListDatos();
            loList.moServidor=moPadre.getServer();
            loList.getFields().addField(new JFieldDef(JTEEGUIXMENSAJESBD.getFOLDERNombre()));
            loList.getFields().addField(new JFieldDef(JTEEGUIXMENSAJESBD.getCODIGONombre()));

            loList.recuperarDatosNoCacheNormal(loSelect);
            
            this.inicializar();
            openFolder();
            try{
                List<JMensaje> loMensajes;
                if(loList.moveFirst()){
                    JTEEGUIXMENSAJESBD loMensTabla = JTEEGUIXMENSAJESBD.getTabla(loList.getFields(1).getString(), moPadre.getServer());

                    long lID = 0;
                    //se hace asi pq la busqueda directa por ID tarda mucho
                    String ls = loMensTabla.getIDMENSAJE().getString();
                    if(JCadenas.isVacio(ls)){
                        ls="asddsgteytg512213";
                    }
                    MessageIDTerm loIDTERM = new MessageIDTerm(ls);
                    //limite de fecha hacia atras
                    JDateEdu loDate = loMensTabla.getFECHA().getDateEdu();
                    loDate.add(loDate.mclDia, -2);
                    loDate.setHora(0);
                    loDate.setMinuto(0);
                    loDate.setSegundo(0);
//no va                    
//                    SearchTerm loSearTerm = new ReceivedDateTerm(ComparisonTerm.LE, loDate.getDate());
                    
                    Message totalMess[]=null;
                    int l=moFolder.getMessageCount();
                    int lMax=moFolder.getMessageCount();
                    while(l>1 && (totalMess==null || totalMess.length==0) && lID==0){
                        lMax=l;
                        l=l-50;
                        if(l<0){
                            l=1;
                        }
                        
                        Message[] loMessajesCorreo = moFolder.getMessages(l, lMax);
                        //buscamos id
                        totalMess = moFolder.search(loIDTERM,  loMessajesCorreo);
                        if(totalMess!=null && totalMess.length>0){
                            lID = totalMess[0].getMessageNumber()+1;
                        }
                        //si no id comprobamos fecha
                        if(lID<=0){
                            for (Message message : loMessajesCorreo) {
                                if (message.getSentDate().before(loDate.getDate())){
                                    lID = message.getMessageNumber();
                                    break;
                                } 
                            }
                        }
                    }
                    if(lID<=0){
                        lID=1;
                    }
                    loMensajes = this.getCorreosMessajesDesdeUID(lID);
                } else {
                    //si no hay correos se cojen los 20 ultimos      
                    int l=moFolder.getMessageCount()-20;
                    if(l<0){
                        l=1;
                    }
                    loMensajes = this.getCorreosMessajesDesdeUID(l);
//                    //se busca a partir de la fecha de ayer, no va
//                    JDateEdu loDate = new JDateEdu();
//                    loDate.add(loDate.mclDia, -1);
//                    loDate.setHora(0);
//                    loDate.setMinuto(0);
//                    loDate.setSegundo(0);
//                    SearchTerm loSearTerm = new ReceivedDateTerm(ComparisonTerm.GE, loDate.getDate());
//                    loDate.add(loDate.mclDia, 5);
//                    SearchTerm loSearTerm2 = new ReceivedDateTerm(ComparisonTerm.LE, loDate.getDate());
//                    Message totalMess[] = moFolder.search(new AndTerm(loSearTerm, loSearTerm2));
//                    loMensajes = this.getCorreosMensajesDeMESSAGES(totalMess);
                }

                for(JMensaje loMensajPropi : loMensajes){
                    JTEEGUIXMENSAJESBD loMensTabla = JTEEGUIXMENSAJESBD.getTablaPorIDMen(loMensajPropi.getIdMensaje(), moPadre.getServer());
                    if(!loMensTabla.moveFirst()){
                        loMensTabla.addNew();
                        loMensTabla.setMensaje(loMensajPropi);
                        loMensTabla.getFOLDER().setValue(
                                JTEEGUIXMENSAJESBD.getIdentificadorFOLDER(
                                        moPadre
                                        , this.getCarpetaCorreo()
                                ));
                        IResultado loResult = loMensTabla.guardar();
                        if(!loResult.getBien()){
                            throw new Exception(loResult.getMensaje());
                        }
                    }

                }
            }finally{
                closeFolder();
            }

        }
            
    }
    
    public void addMensajeACarpeta(JMensaje msg, String psCarpeta) throws Exception{
        
    }
    public void addMensajeACarpeta(Message msg, String psCarpeta) throws Exception{
        conectar();
        Folder folder = moStore.getFolder(psCarpeta);
        folder.open(Folder.READ_WRITE);  
        msg.setFlag(Flags.Flag.SEEN, true);  
        folder.appendMessages(new Message[] {msg});  
    }

    /**
     * @return the msServidor
     */
    public String getServidor() {
        return msServidor;
    }

    /**
     * @return the msUsuario
     */
    public String getUsuario() {
        return msUsuario;
    }

    /**
     * @return the msPassword
     */
    public String getPassword() {
        return msPassword;
    }

    /**
     * @return the msTipoEntrante
     */
    public String getTipoEntrante() {
        return msTipoEntrante;
    }

    /**
     * @return the msCarpetaCorreo
     */
    public String getCarpetaCorreo() {
        return msCarpetaCorreo;
    }

    
    
}
