/*
 * PintarTabla.java
 *
 * Created on 2 de abril de 2004, 13:11
 */

package utilesGUI.tabla;

import java.awt.*;
import java.awt.event.*;
import ListDatos.*;
import utiles.*;

class PintarTabla extends Thread {
    private final JTabla moTablaReal;
    private boolean mbFin = false;
    private boolean mbRefrescar = true;
    private boolean mbAutoRedraw = true;
    
    public PintarTabla(JTabla poTabla){
        super();
        moTablaReal = poTabla;
    }
    public void close(){
        mbFin=true;
    }
    public synchronized void setRefrescar(boolean pbRefrescar){
        mbRefrescar = pbRefrescar;
        notifyAll();
    }
    public synchronized void setAutoRedraw(boolean pbAutoRedraw){
        mbAutoRedraw = pbAutoRedraw;
        notifyAll();
    }
    public synchronized boolean getAutoRedraw(){
        return mbAutoRedraw;
    }
    public void run() {
        Image loImg=null;
        for(;!mbFin;){
            try{

            synchronized(this){
                while(!((mbRefrescar)&&(mbAutoRedraw))) {
                    wait();
                }
            }
            if(!mbFin){
                mbRefrescar = false;
                //creamos una imagen para dibujar en ella, se le suma 10 para que el ult. punto no se pintaria a mitad
                if((moTablaReal.getSize().width>0)&&(moTablaReal.getSize().height>0)){
                    loImg = moTablaReal.createImage( moTablaReal.getSize().width, moTablaReal.getSize().height);
                    Graphics g2 = loImg.getGraphics();
                    pintar(g2);
                    if(!mbFin){
                        moTablaReal.moImg = loImg;
                        moTablaReal.updateg();
                    }
                }
            }
    
            }catch(Exception e){
                JDepuracion.anadirTexto(getClass().getName(), e);
            }
        }
    }
    private void pintar(Graphics g){
        if(moTablaReal.moTabla==null){
            JListDatos loDatos = new JListDatos(null, "", new String[]{"",""}, 
                new int[]{JListDatos.mclTipoCadena, JListDatos.mclTipoCadena},new int[]{0}
            );
            moTablaReal.setTabla(new JTablaMotor (loDatos));
        }
        if(moTablaReal.mlFilaSelec>=moTablaReal.moTabla.getRowCount()){
            int lFila = moTablaReal.mlFilaSelec;
            moTablaReal.mlFilaSelec=moTablaReal.moTabla.getRowCount()-1;
            if(moTablaReal.mlFilaSelec<0) {
                moTablaReal.mlFilaSelec=0;
            }
            if (lFila!=moTablaReal.mlFilaSelec){
                moTablaReal.setFilaSelec(moTablaReal.mlFilaSelec);
            }
        }
        //variables de dibujo
        Dimension loDim = moTablaReal.getSize();

        moTablaReal.mlAltoCelda = moTablaReal.fntm.getHeight()+4;
        int lNumeroFilasQueCaben =(loDim.height / moTablaReal.mlAltoCelda)-1; //quitamos la cabezera 

        int lFilaVisible=0;
        int lColVisible=0;
        //quitamos de la dimension de dibujo para las barras si no caben todas las filas/colum
        if (moTablaReal.moTabla.getRowCount()>lNumeroFilasQueCaben) {
            loDim.width-=JBarraDesplazamiento.mclAncho;
            moTablaReal.moBarraV.setMax(moTablaReal.moTabla.getRowCount());
            moTablaReal.moBarraV.setSize(moTablaReal.getSize());
            moTablaReal.moBarraV.setColores(moTablaReal.moColorBarraExtremos ,moTablaReal.moColorBarraCentro ,moTablaReal.moColorBarraLineas);
            moTablaReal.moBarraV.setSeUsa(true);
            lFilaVisible = moTablaReal.moBarraV.mlIndex;
        }else moTablaReal.moBarraV.setSeUsa(false);

        //quitamos de la dimension de dibujo para las barras si no caben todas las filas/colum
        if ((sumaAnchos()+moTablaReal.mlAnchoIzq)>loDim.width) {
            loDim.height -=JBarraDesplazamiento.mclAncho;
            moTablaReal.moBarraH.setMax(moTablaReal.moTabla.getColumnCount());
            moTablaReal.moBarraH.setSize(moTablaReal.getSize());
            moTablaReal.moBarraH.setColores(moTablaReal.moColorBarraExtremos ,moTablaReal.moColorBarraCentro ,moTablaReal.moColorBarraLineas);
            moTablaReal.moBarraH.setSeUsa(true);
            lColVisible = moTablaReal.moBarraH.mlIndex;
        }else moTablaReal.moBarraH.setSeUsa(false);

        lNumeroFilasQueCaben =(loDim.height / moTablaReal.mlAltoCelda)-1; //quitamos la cabezera 
        int lNumeroFilasDeTabla = (moTablaReal.moTabla.getRowCount()-lFilaVisible);
        int lNumeroFilas = (
           (lNumeroFilasDeTabla < lNumeroFilasQueCaben) ? 
           lNumeroFilasDeTabla :lNumeroFilasQueCaben);
        int lAltoColumna = (lNumeroFilas+1 ) * moTablaReal.mlAltoCelda;

        //rectangulo lado izq.
        g.setColor( moTablaReal.moBackColorIzq );
        g.fillRect( 0, 0, moTablaReal.mlAnchoIzq, loDim.height );
        //rectangulo cabezera
        g.setColor( moTablaReal.moBackColorCab );
        g.fillRect( 0, 0, loDim.width, moTablaReal.mlAltoCelda );
        //rectangulo datos
        g.setColor( moTablaReal.moBackColor );
        g.fillRect( moTablaReal.mlAnchoIzq, moTablaReal.mlAltoCelda, loDim.width - moTablaReal.mlAnchoIzq, loDim.height );
        //dibujamos la posicion
        if ((moTablaReal.mlFilaSelec>=lFilaVisible)&&(moTablaReal.mlFilaSelec<=(lFilaVisible+lNumeroFilas))){
            g.setColor(moTablaReal.moForeColorIzq);
            g.fillOval(1, (moTablaReal.mlFilaSelec-lFilaVisible+1)*moTablaReal.mlAltoCelda, 
                moTablaReal.mlAnchoIzq-2, moTablaReal.mlAltoCelda-2 );
            g.setColor(moTablaReal.moBackColorSel);
            g.fillRect(moTablaReal.mlAnchoIzq, (moTablaReal.mlFilaSelec-lFilaVisible+1)*moTablaReal.mlAltoCelda, 
                loDim.width-moTablaReal.mlAnchoIzq,moTablaReal.mlAltoCelda);
        }

        //dibujamos las lineas por columna
        double ldAncho = moTablaReal.mlAnchoIzq;
        g.setColor(moTablaReal.moColorLinea);
        for(int i = lColVisible ; i<= moTablaReal.moTabla.getColumnCount();i++){
            g.drawLine(
                (int)(ldAncho), 0, 
                (int)(ldAncho), lAltoColumna);
            if(i<moTablaReal.madAnchos.length) {
                ldAncho += moTablaReal.madAnchos[moTablaReal.malPosiCols[i]];
            }
        }
        //dibujamos las lineas por fila
        for(int i = 1; i<(lNumeroFilas+2) ;i++){
            g.drawLine(
                0, i * moTablaReal.mlAltoCelda, 
                loDim.width, i * moTablaReal.mlAltoCelda);
        }
        //dibujar titulos
        g.setColor(moTablaReal.moForeColorCab);
        g.setFont(moTablaReal.fntCab);
        ldAncho = moTablaReal.mlAnchoIzq+1;

        for(int i = lColVisible; i< moTablaReal.moTabla.getColumnCount();i++){
            int lPosi = moTablaReal.malPosiCols[i];
            int lAncho = (int)moTablaReal.madAnchos[lPosi];
            String lsTexto = msAjustarTexto(moTablaReal.moTabla.getColumnName(lPosi),String.class,lAncho,moTablaReal.fntmCab,g);
            int lDespl = mlAjustarTexto(lsTexto, moTablaReal.mclAlinCentro, lAncho, moTablaReal.fntm,g);
            g.drawString(lsTexto , 
                ((int)ldAncho)+1+lDespl, -3+moTablaReal.mlAltoCelda);//moTablaReal.fntm.getHeight()
            ldAncho +=moTablaReal.madAnchos[lPosi];
        }
        //dibujar valores
        g.setFont(moTablaReal.fnt);
        boolean lbColorCambio =true;
        for(int y0 = lFilaVisible; y0<(lNumeroFilas+lFilaVisible); y0++){
            ldAncho = moTablaReal.mlAnchoIzq+1;
            int ly = (y0+1-lFilaVisible)*moTablaReal.mlAltoCelda-3+moTablaReal.mlAltoCelda;//ponemos "y" y saltamos la cab.moTablaReal.fntm.getHeight()
            //ponemos el color segun si es selec. o no
            if (y0==moTablaReal.mlFilaSelec) {
                g.setColor(moTablaReal.moForeColorSel);
                lbColorCambio = true;
            }
            else {
                if(lbColorCambio){
                    g.setColor(moTablaReal.moForeColor);
                    lbColorCambio = false;
                }
            }
            Component loComps[] = new Component[moTablaReal.moTabla.getColumnCount()];
            double ldAnchoCeldas = (loDim.width-(moTablaReal.moBarraV.getSeUsa()  ? JBarraDesplazamiento.mclAncho  : 0 ));
            moTablaReal.mlUltCol = -1;
            for(int x0 = lColVisible; (x0< moTablaReal.moTabla.getColumnCount()) && (ldAnchoCeldas>ldAncho);x0++){
                moTablaReal.mlUltCol = x0;
                //Calculo de la columna real
                int lCol = moTablaReal.malPosiCols[x0];
                //anchos y posicion x
                int lAncho = (int)moTablaReal.madAnchos[lCol];
                int lx = ((int)ldAncho)+1;
                if (ldAnchoCeldas<(lx+lAncho)){
                    lAncho = (int)ldAnchoCeldas-lx;
                }
                //ajustamos el texto, quitandole caracteres en caso de que sea necesario
                String lsTexto = msAjustarTexto(moTablaReal.moTabla.getValueAt(y0,lCol),moTablaReal.moTabla.getColumnClass(lCol), lAncho,moTablaReal.fntm,g);
                //Alineamos el texto, calcula el deslazamiento
                int lDespl = mlAjustarTexto(lsTexto, moTablaReal.malAlineaciones[lCol], lAncho, moTablaReal.fntm, g);
                //dibujamos el texto
                g.drawString(lsTexto, lx+lDespl, ly);
                //incrementamos el ancho dibujado, lo hacemos con double por ser mas preciso
                ldAncho +=moTablaReal.madAnchos[lCol];
                //si es la fila seleccionada dibujamos los componentes
                if (y0==moTablaReal.mlFilaSelec) {
                    if(moTablaReal.moTabla.isCellEditable(y0, lCol)){
                        Component loComp = null;
                        loComp = (Component)moTablaReal.maoEdicion[lCol];
                        if (loComp != null){
                            try{
                                ((IComponentParaTabla)loComp).setValueTabla(moTablaReal.moTabla.getValueAt(y0,lCol));
                            }catch(Exception e){
                                utilesGUI.msgbox.JDialogo.showDialog(new java.awt.Frame(),e.toString());
                            }
                            loComp.setBounds(lx,ly-moTablaReal.mlAltoCelda+3, lAncho, moTablaReal.mlAltoCelda);
                        }
                        loComps[lCol] = loComp;
                    }
                }
            }
            //vemos si la fila de componenetes es visible o no
            boolean lbVisible = ((moTablaReal.mlFilaSelec>=lFilaVisible)&&(moTablaReal.mlFilaSelec<=(lFilaVisible+lNumeroFilas)));
            for(int x0 = 0; (x0< moTablaReal.moTabla.getColumnCount());x0++){
                //Calculo de la columna real
                int lCol = moTablaReal.malPosiCols[x0];
                if (moTablaReal.maoEdicion[lCol] != null){
                    if((x0<lColVisible)||(x0>moTablaReal.mlUltCol)){
                        ((Component)moTablaReal.maoEdicion[lCol]).setVisible(false);
                    }else{
                        ((Component)moTablaReal.maoEdicion[lCol]).setVisible(lbVisible);
                    }
                }
            }
        }
        //dibujamos las barras
        moTablaReal.moBarraV.paint(g, moTablaReal);
        moTablaReal.moBarraH.paint(g, moTablaReal);

        if (moTablaReal.mbConFoco){
            g.setColor(moTablaReal.moColorLineaExtSel);
            g.drawRect(0,0, moTablaReal.getSize().width-1, moTablaReal.getSize().height-1);

        }
    }
    /**
     *suma anchos
     */
    private int sumaAnchos(){
        double ldAncho =0;
        for (int i = 0 ; i<moTablaReal.madAnchos.length;i++){
            ldAncho+=moTablaReal.madAnchos[i];
        }
        return (new Double(ldAncho)).intValue();
    }
    /**
     *devuelve el desplazamiento de x para pintar alineado
     */
    private int mlAjustarTexto(String psTexto, int plAlineacion, int plAncho, FontMetrics fntm, Graphics g){
        int lDesp = 0;
        switch(plAlineacion){
            case JTabla.mclAlinCentro:
                lDesp = ((plAncho-8)-(int)(fntm.stringWidth(psTexto)))/2;
                break;
            case JTabla.mclAlinDer:
                lDesp =  (plAncho-8)-(int)fntm.stringWidth(psTexto);
                break;
            default:
        }
        if(lDesp<0){
            lDesp=0;
        }
        return lDesp;
        
    }
    /**
     *Ajusta el texto al ancho pasado
     */
    private String msAjustarTexto(Object psTexto, Class poClase, int plAncho, FontMetrics fntm, Graphics g){
        String lsReturn = "";
        if (psTexto != null) {
            String  lsTexto;
            if (poClase == Boolean.class){
                if (((Boolean)psTexto).booleanValue()){
                    lsTexto = "Sí";
                }else{
                    lsTexto = "No";
                }
            }else{
                lsTexto = psTexto.toString();
            }

	    int lLen = lsTexto.length();	
            //se resta 8 al ancho por las lineas de los cuadros
            plAncho-=8;
            if((plAncho <= 0)||(lLen==0)) {
                lsReturn = "";
            }else {
                //optimizacion, se calcula el ancho de 2 letras y se calcula el numero de carct. inicial
                int lAncho2 = fntm.stringWidth("99");
                int lLenIni = (plAncho / lAncho2) * 2;
                if (lLen < lLenIni) {
                    lLenIni = lLen;
                }
                //vemos si tiene que ir anadiendo caracteres o decrementando
                if (fntm.stringWidth(lsTexto.substring(0,lLenIni))>plAncho) {
                    int lSalto = -1;
                    //afinamos con un bucle, quitando o añadiendo caracteres
                    while((fntm.stringWidth(lsTexto.substring(0,lLenIni))>plAncho)&&((lLenIni+=lSalto)>=0));
                }
                else {
                    int lSalto = 1;
                    //afinamos con un bucle, quitando o añadiendo caracteres
                    while((fntm.stringWidth(lsTexto.substring(0,lLenIni))<plAncho)&&((lLenIni+=lSalto)<=lLen));
                    if (lLen < lLenIni) {
                        lLenIni = lLen;
                    }
                }
                if(lLenIni<=0) {
                    lsReturn = "";
                }else {
                    lsReturn = lsTexto.substring(0,lLenIni);
                }
            }
        }
        return lsReturn;
    }    
}
