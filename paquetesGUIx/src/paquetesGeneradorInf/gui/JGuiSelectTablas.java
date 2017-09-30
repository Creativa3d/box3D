/*
 * JGuiTablasPanel.java
 *
 * Created on 27-may-2009, 10:00:48
 */
package paquetesGeneradorInf.gui;

import java.awt.event.ActionListener;
import paquetesGeneradorInf.gui.util.IPaint;
import ListDatos.JSelect;
import ListDatos.JSelectCampo;
import ListDatos.JSelectFrom;
import ListDatos.JSelectUnionTablas;
import ListDatos.estructuraBD.JRelacionesDef;
import ListDatos.estructuraBD.JTableDef;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import utiles.IListaElementos;
import java.util.Iterator;
import utiles.JDepuracion;
import utiles.JListaElementos;
import utilesGUIx.formsGenericos.JMostrarPantalla;
import utilesGUIx.formsGenericos.JMostrarPantallaParam;

public class JGuiSelectTablas extends javax.swing.JPanel implements ComponentListener, MouseListener, ActionListener {

    private JGuiConsulta moConsulta;
    private IListaElementos moRelaciones = new JListaElementos();

    /** Creates new form JGuiTablasPanel */
    public JGuiSelectTablas() {
        initComponents();
    }
    public void guardar() throws CloneNotSupportedException {
    }
    public void cambioTablas() throws Exception {
        JSelect loSelect = getSelect();
        if(loSelect.getFrom()!=null && loSelect.getFrom().getTablasUnion().size()>0){
            Iterator loEnum = loSelect.getFrom().getTablasUnion().iterator();
            while (loEnum.hasNext()) {
                JSelectUnionTablas loFromUnion = (JSelectUnionTablas) loEnum.next();
                addRelacionVisual(loFromUnion);
            }
            loEnum = null;
        }
        jMenuCheckTotales.setSelected(getSelect().isAgrupado());
    }

    void addRelacionSelect(JSelectUnionTablas poFromUnion) throws Exception {
        //si no estaba relacionado a ninguna tabla la tabla destino 
        //la borramos pq la tabla ya no estaria sola
        for(int i = 0 ; i < getConsulta().getSelect().getFrom().getTablasUnion().size(); i++){
            JSelectUnionTablas loSelectUnion = (JSelectUnionTablas) getConsulta().getSelect().getFrom().getTablasUnion().get(i);
            if(isMismaTabla(
                    loSelectUnion.getTabla2(), loSelectUnion.getTabla2Alias(),
                    poFromUnion.getTabla2(), poFromUnion.getTabla2Alias()) &&
               loSelectUnion.getCampos1()==null){
                getSelect().getFrom().getTablasUnion().remove(i);
            }
        }
        //añadimos visualmente
        addRelacionVisual(poFromUnion);
        repaint();

        //añadimos al JSelect
        getSelect().getFrom().addTabla(poFromUnion);
        //indicamos cambios en las tablas
        getConsulta().cambioTablasDETABLAS();
    }
    void addRelacionVisual(JSelectUnionTablas poFromUnion) throws Exception {
        JGuiTabla loTabla = addTablaGui(null, poFromUnion.getTablaPrefijoCampos1());
        JGuiTabla loTabla2 = addTablaGui(
                poFromUnion.getTabla2(), poFromUnion.getTabla2Alias());
        if(loTabla!=null && loTabla2!=null){
            JGuiRelacion loRelacion = new JGuiRelacion(this);
            loRelacion.setRelacion(poFromUnion, loTabla, loTabla2);
            moRelaciones.add(loRelacion);
        }
    }

    void addTabla(String psTabla) throws Exception {
        String lsAlias = null;
        JGuiTabla loGuiTabla = getTablaGui(psTabla, null);
        int i = 1;
        while(loGuiTabla!=null){
            lsAlias = psTabla + "_" + String.valueOf(i);
            loGuiTabla = getTablaGui(psTabla, lsAlias);
            i++;
        }
        loGuiTabla = addTablaGui(psTabla, lsAlias);
        if(getSelect().getFrom()==null || getSelect().getFrom().getTablasUnion().size()==0){
            //caso especial si no hay tablas
            getSelect().setTabla(psTabla, lsAlias);
        } else {
            boolean lbContinuar = true;
            //para cada tabla de la pantalla
            for (i = 0; i < this.getComponentCount() && lbContinuar; i++) {
                if (this.getComponent(i).getClass().isAssignableFrom(JGuiTabla.class)) {
                    JGuiTabla loGuiTablaAux = (JGuiTabla) this.getComponent(i);
                    JTableDef loTabla = loGuiTablaAux.getTabla();
                    //no tenemos en cuenta las tablas q se llamen igual a la tabla
                    //q acabamos de añadir
                    if(!loTabla.getNombre().equals(psTabla)){
                        //recorremos las relaciones y vemos si esta relacionado
                        //con la tabla que acabamos de añadir
                        for(int ii = 0 ; ii < loTabla.getRelaciones().getListaRelaciones().size() && lbContinuar; ii++){
                            JRelacionesDef loRelacion = loTabla.getRelaciones().getRelacion(ii);
                            if(loRelacion.getTablaRelacionada().equals(psTabla)){
                                String[] lasCampos = new String[loRelacion.getListaCamposRelacion().size()];
                                String[] lasCamposR = new String[loRelacion.getListaCamposRelacion().size()];
                                for(int j = 0 ; j < lasCampos.length; j++ ){
                                    lasCampos[j]=loRelacion.getCampoPropio(j);
                                    lasCamposR[j]=loRelacion.getCampoRelacion(j);
                                }
                                JSelectUnionTablas loUnion = new JSelectUnionTablas(
                                        JSelectUnionTablas.mclLeft,
                                        loTabla.getNombre(),
                                        loGuiTabla.getNombreTablaFisico(),
                                        lasCampos,
                                        lasCamposR
                                        );
                                addRelacionSelect(loUnion);
                                lbContinuar = false;
                            }
                        }
                    }
                }
            }
            if(lbContinuar){
                getSelect().getFrom().addTabla(
                        new JSelectUnionTablas(
                                psTabla,
                                lsAlias
                                )
                        );
            }
        }
        repaint();
        getConsulta().cambioTablasDETABLAS();
    }

    void borrarRelacion(JSelectUnionTablas poFromUnion) {
        boolean lbContinuar = true;
        //borramos graficas
        for(int i = 0 ; i< moRelaciones.size() && lbContinuar; i++){
            JGuiRelacion loRelacion = (JGuiRelacion) moRelaciones.get(i);
            if(loRelacion.moUnion == poFromUnion){
                moRelaciones.remove(i);
                loRelacion.liberar();
                lbContinuar = false;
            }
        }
        //borramos del JSelect
        for(int i = 0 ; i < getConsulta().getSelect().getFrom().getTablasUnion().size(); i++){
            JSelectUnionTablas loSelectUnion = (JSelectUnionTablas) getConsulta().getSelect().getFrom().getTablasUnion().get(i);
            if(loSelectUnion == poFromUnion){
                getSelect().getFrom().getTablasUnion().remove(i);
//                poFromUnion.masCampos1=new String[0];
//                poFromUnion.masCampos2=new String[0];
//                poFromUnion.msTabla2="";
            }
        }
        repaint();
    }

    void borrarTabla(String psTabla, String psTablaAlias) throws Exception {
        //borramos todas las relaciones
        for(int i = 0 ; i < getSelect().getFrom().getTablasUnion().size() && getConsulta().getSelect().getFrom().getTablasUnion().size() > 0; i++){
            JSelectUnionTablas loSelectUnion = (JSelectUnionTablas) getConsulta().getSelect().getFrom().getTablasUnion().get(i);
            if(isMismaTabla(null, loSelectUnion.getTablaPrefijoCampos1(), psTabla, psTablaAlias) ||
               isMismaTabla(loSelectUnion.getTabla2(), loSelectUnion.getTabla2Alias(), psTabla, psTablaAlias)
                ){
                borrarRelacion(loSelectUnion);
                i=i-1;
            }
        }
        //borramos la tabla
        for (int i = 0; i < this.getComponentCount(); i++) {
            if (this.getComponent(i).getClass().isAssignableFrom(JGuiTabla.class)) {
                JGuiTabla loGuiTabla = (JGuiTabla) this.getComponent(i);
                if(isMismaTabla(loGuiTabla, psTabla, psTablaAlias)){
                    this.remove(loGuiTabla);
                }
            }
        }
        //parche, cuando queda solo una tabla, no se borra la relacion grafica
        //asociada a esa tabla las borramos todas y punto
        if(getSelect().getFrom().getTablasUnion().size()==1){
            while(moRelaciones.size()>0){
                JGuiRelacion loRelacion = (JGuiRelacion) moRelaciones.get(0);
                moRelaciones.remove(0);
                loRelacion.liberar();
            }
        }
        for(int i = 0 ; i < getSelect().getCampos().size(); i++){
            JSelectCampo loCampo = (JSelectCampo) getSelect().getCampos().get(i);
            if(isMismaTabla(psTabla, psTablaAlias, null, loCampo.getTabla())){
                getSelect().getCampos().remove(i);
                i--;
            }
        }
        repaint();
        getConsulta().cambioTablasDETABLAS();

    }

    JSelectUnionTablas getFromUnion(String psAlias1, String psAlias2) {
        JSelectUnionTablas loResult = null;
        for(int i = 0 ; i < getConsulta().getSelect().getFrom().getTablasUnion().size() && loResult==null; i++){
            JSelectUnionTablas loSelectUnion = (JSelectUnionTablas) getConsulta().getSelect().getFrom().getTablasUnion().get(i);
            if((isMismaTabla(null, loSelectUnion.getTablaPrefijoCampos1(), null, psAlias1) &&
                isMismaTabla(loSelectUnion.getTabla2(), loSelectUnion.getTabla2Alias(), null, psAlias2)
                ) ||
               (isMismaTabla(null, loSelectUnion.getTablaPrefijoCampos1(), null, psAlias2) &&
                isMismaTabla(loSelectUnion.getTabla2(), loSelectUnion.getTabla2Alias(), null, psAlias1)
                )
              ){
                loResult=loSelectUnion;
            }
        }
        return loResult;
    }


    boolean isTablaSecundaria(String psTabla, String psAlias){
        boolean lbResult = true;
        if(psAlias==null || psAlias.equals("")){
            psAlias = psTabla;
        }
        for(int i = 0 ; i < getSelect().getFrom().getTablasUnion().size() && lbResult; i++){
            JSelectUnionTablas loUnion = (JSelectUnionTablas) getSelect().getFrom().getTablasUnion().get(i);
            if(((loUnion.getCampos1()!=null && loUnion.getCampos1().length>0) || i == 0) &&
               (loUnion.getTabla2Alias()!=null  && psAlias.equalsIgnoreCase(loUnion.getTabla2Alias()) ||
                loUnion.getTabla2()!=null  && psAlias.equalsIgnoreCase(loUnion.getTabla2())
               )   ){
                lbResult=false;
            }
        }
        return lbResult;
    }

    void mostrarRelacion(JSelectUnionTablas poFromUnion, String psNombreTabla1Fisica) throws Exception {
        mostrarRelacion(poFromUnion, psNombreTabla1Fisica, false);
    }
    void mostrarRelacion(JSelectUnionTablas poFromUnion, String psNombreTabla1Fisica, boolean pbADD) throws Exception {
        JGuiRelacionADD loGui = new JGuiRelacionADD(this);
//        //si es añadir y es una tabla q ya existe entonces se renombra
//        if(pbADD){
//            int lTotal = 0;
//            for (int i = 0; i < this.getComponentCount() ; i++) {
//                if (this.getComponent(i).getClass().isAssignableFrom(JGuiTabla.class)) {
//                    JGuiTabla loGuiTabla = (JGuiTabla) this.getComponent(i);
//                    if (loGuiTabla.getTabla().getNombre().equals(poFromUnion.msTabla2)) {
//                        lTotal++;
//                    }
//                }
//            }
//            if(lTotal>0){
//                if(poFromUnion.msTabla2Alias==null || poFromUnion.msTabla2Alias.equalsIgnoreCase(poFromUnion.msTabla2)){
//                    poFromUnion.msTabla2Alias=poFromUnion.msTabla2 + "_" + String.valueOf(lTotal);
//                }
//            }
//        }
        loGui.setUnion(poFromUnion, psNombreTabla1Fisica, pbADD);

        getConsulta().getDatos().getMostrarPantalla().mostrarForm(
                new JMostrarPantallaParam(
                    loGui, 500, 300, JMostrarPantalla.mclEdicionFrame, "Añadir relación")
                );
        loGui.setVisible(true);
    }



    private JTableDef getTabla(String psTabla) throws Exception {
        JTableDef loResult = null;
        if (psTabla != null && !psTabla.equals("")) {
            loResult = moConsulta.getDatos().getServer().getTableDefs().get(psTabla);
        }
        return loResult;
    }

    private JGuiTabla getTablaGui(String psTabla, String psTablaAlias){
        JGuiTabla loResult = null;
        for (int i = 0; i < this.getComponentCount() && loResult == null; i++) {
            if (this.getComponent(i).getClass().isAssignableFrom(JGuiTabla.class)) {
                JGuiTabla loGuiTabla = (JGuiTabla) this.getComponent(i);
                if(isMismaTabla(loGuiTabla, psTabla, psTablaAlias)){
                    loResult = loGuiTabla;
                }
            }
        }
        return loResult;
    }
    private JGuiTabla addTablaGui(String psTabla, String psTablaAlias) throws Exception {
        JGuiTabla loResult = null;
        if(psTabla!=null || psTablaAlias!=null){
            if (loResult == null) {
                loResult = getTablaGui(psTabla, psTablaAlias);
            }
            if (loResult == null) {
                JTableDef loTabla = getTabla(psTabla);
                if (loTabla != null) {
                    JGuiTabla loGuiTabla = new JGuiTabla(this);
                    loGuiTabla.setTabla(loTabla, psTablaAlias);
                    this.add(loGuiTabla);
                    loGuiTabla.addComponentListener(this);
                    loResult = loGuiTabla;
                    int lTablas = -1;
                    for (int i = 0; i < this.getComponentCount(); i++) {
                        if (this.getComponent(i).getClass().isAssignableFrom(JGuiTabla.class)) {
                            lTablas++;
                        }
                    }
                    if (lTablas < 0) {
                        lTablas = 0;
                    }
                    loResult.setBounds(
                            lTablas * ((int) loResult.getPreferredSize().getWidth() + 20),
                            10,
                            (int) loResult.getPreferredSize().getWidth(),
                            (int) loResult.getPreferredSize().getHeight());
                }
            }
        }
        return loResult;
    }
    private boolean isMismaTabla(String psTablaO, String psTablaAliasO,String psTabla, String psTablaAlias){
        boolean lbResult = false;
        boolean lbContinuar = true;
        String lsTablaGui = psTablaO;
        String lsTablaAliasGui = psTablaAliasO;
        if(psTabla!=null && psTablaAlias!=null){
            if(psTabla.equals(psTablaAlias) || psTablaAlias.equals("")){
                psTablaAlias = null;
            }
        }
        if(lsTablaGui!=null && lsTablaAliasGui!=null){
            if(lsTablaGui.equals(lsTablaAliasGui) || lsTablaAliasGui.equals("")){
                lsTablaAliasGui = null;
            }
        }
        //si la tabla es nula entonces se compara por la tabla alias
        //o la tabla
        if (lbContinuar  &&
                (psTabla == null && psTablaAlias != null && lsTablaGui != null  &&
                  (lsTablaAliasGui == null &&
                    psTablaAlias.equalsIgnoreCase(lsTablaGui) ||
                   lsTablaAliasGui != null &&
                    psTablaAlias.equalsIgnoreCase(lsTablaAliasGui))
                )
            )   {
            lbResult=true;
            lbContinuar = false;
        }
        //si los alias son identicos es la misma
        if (lbContinuar &&
            (lsTablaAliasGui != null && psTablaAlias != null &&
                    psTablaAlias.equalsIgnoreCase(lsTablaAliasGui))
            )   {
            lbResult=true;
            lbContinuar = false;
        }
        //si los alias son no son identicos NO
        if (lbContinuar &&
            (lsTablaAliasGui != null && psTablaAlias != null &&
                    !psTablaAlias.equalsIgnoreCase(lsTablaAliasGui))
            )   {
            lbResult=false;
            lbContinuar = false;
        }
        //si solo un alias es distinto de nulo NO son identicos
        if (lbContinuar  &&
                ((lsTablaAliasGui != null && psTablaAlias == null) ||
                (lsTablaAliasGui == null && psTablaAlias != null)
                )
            )   {
            lbResult=false;
            lbContinuar = false;
        }
        //si los alias son nulos entonces si las tablas son identicas SI
        if (lbContinuar  &&
                (psTabla != null && lsTablaGui != null  &&
                 psTabla.equalsIgnoreCase(lsTablaGui))
            )   {
            lbResult=true;
            lbContinuar = false;
        }
        return lbResult;
    }
    private boolean isMismaTabla(JGuiTabla poGuiTabla,String psTabla, String psTablaAlias){
        return isMismaTabla(
                poGuiTabla.getTabla().getNombre(), poGuiTabla.getTablaAlias(),
                psTabla, psTablaAlias);
    }

    JSelect getSelect(){
        return moConsulta.getSelect();
    }

    JGuiConsulta getConsulta() {
        return moConsulta;
    }

    void setConsulta(JGuiConsulta poConsulta) throws Exception {
        moConsulta = poConsulta;
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPopupMenu1 = new javax.swing.JPopupMenu();
        jMenuMostrarTabla = new javax.swing.JMenuItem();
        jMenuCheckTotales = new javax.swing.JCheckBoxMenuItem();

        jMenuMostrarTabla.setText(" Mostrar tabla");
        jMenuMostrarTabla.addActionListener(this);
        jPopupMenu1.add(jMenuMostrarTabla);

        jMenuCheckTotales.setSelected(true);
        jMenuCheckTotales.setText("Totales");
        jMenuCheckTotales.addActionListener(this);
        jPopupMenu1.add(jMenuCheckTotales);

        setMinimumSize(new java.awt.Dimension(2000, 2000));
        setPreferredSize(new java.awt.Dimension(2000, 2000));
        addMouseListener(this);
        setLayout(null);
    }

    // Code for dispatching events from components to event handlers.

    public void actionPerformed(java.awt.event.ActionEvent evt) {
        if (evt.getSource() == jMenuMostrarTabla) {
            JGuiSelectTablas.this.jMenuMostrarTablaActionPerformed(evt);
        }
        else if (evt.getSource() == jMenuCheckTotales) {
            JGuiSelectTablas.this.jMenuCheckTotalesActionPerformed(evt);
        }
    }

    public void mouseClicked(java.awt.event.MouseEvent evt) {
    }

    public void mouseEntered(java.awt.event.MouseEvent evt) {
    }

    public void mouseExited(java.awt.event.MouseEvent evt) {
    }

    public void mousePressed(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == JGuiSelectTablas.this) {
            JGuiSelectTablas.this.formMousePressed(evt);
        }
    }

    public void mouseReleased(java.awt.event.MouseEvent evt) {
        if (evt.getSource() == JGuiSelectTablas.this) {
            JGuiSelectTablas.this.formMouseReleased(evt);
        }
    }// </editor-fold>//GEN-END:initComponents

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        maybeShowPopup(evt);
    }//GEN-LAST:event_formMousePressed

    private void formMouseReleased(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseReleased
        maybeShowPopup(evt);

    }//GEN-LAST:event_formMouseReleased

    private void jMenuMostrarTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuMostrarTablaActionPerformed
        try{
            JGuiSelectTablasMostrar loMostrar = new JGuiSelectTablasMostrar();
            loMostrar.setDatos(this, getConsulta().getDatos());
            getConsulta().getDatos().getMostrarPantalla().mostrarFormPrinci(loMostrar, 400, 400, JMostrarPantalla.mclEdicionFrame);

        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, ex);
        }
        
    }//GEN-LAST:event_jMenuMostrarTablaActionPerformed

    private void jMenuCheckTotalesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuCheckTotalesActionPerformed
        try{
            getConsulta().setAgrupado(jMenuCheckTotales.isSelected());
        } catch (Exception ex) {
            JDepuracion.anadirTexto(getClass().getName(), ex);
            utilesGUIx.msgbox.JMsgBox.mensajeError(this, ex);
        }

    }//GEN-LAST:event_jMenuCheckTotalesActionPerformed

    private void maybeShowPopup(MouseEvent e) {
        if (e.isPopupTrigger()) {
            jPopupMenu1.show(e.getComponent(),
                       e.getX(), e.getY());
        }
    }

    public void paint(Graphics g) {
        //pintamos el componente normal
        super.paint(g);
        //pintamos las relaciones
        for (int i = 0; i < moRelaciones.size(); i++) {
            IPaint loPaint = (IPaint) moRelaciones.get(i);
            loPaint.paint(g);
        }
        //repintamos los componentes hijos para q se queden
        //simpre por encima de las rayas de las relaciones
        super.paintChildren(g);
    }

    public void componentResized(ComponentEvent e) {
    }

    public void componentMoved(ComponentEvent e) {
        int lWidth = 0;
        int lHeight = 0;
        for (int i = 0; i < this.getComponentCount(); i++) {
            Component loComp = this.getComponent(i);
            int lWidthAux = loComp.getWidth() + loComp.getX();
            int lHeightAux = loComp.getHeight() + loComp.getY();
            if (lWidth < lWidthAux) {
                lWidth = lWidthAux;
            }
            if (lHeight < lHeightAux) {
                lHeight = lHeightAux;
            }
//            if (this.getComponent(i).getClass().isAssignableFrom(JGuiTabla.class)) {
//                this.setComponentZOrder(loComp, 0);
//            }
        }
        if(e!=null){
            this.setComponentZOrder(e.getComponent(), 0);
        }
        setBounds(0, 0, lWidth, lHeight);
    }

    public void componentShown(ComponentEvent e) {
    }

    public void componentHidden(ComponentEvent e) {
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBoxMenuItem jMenuCheckTotales;
    private javax.swing.JMenuItem jMenuMostrarTabla;
    private javax.swing.JPopupMenu jPopupMenu1;
    // End of variables declaration//GEN-END:variables

}
