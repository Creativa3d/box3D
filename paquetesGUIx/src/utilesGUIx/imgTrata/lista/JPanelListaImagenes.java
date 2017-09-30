/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * JPanelListaImagenes.java
 *
 * Created on 11-feb-2011, 14:29:40
 */

package utilesGUIx.imgTrata.lista;

import java.awt.event.ComponentEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListSelectionModel;
import ListDatos.IFilaDatos;
import ListDatos.IListDatosEdicion;
import ListDatos.JListDatos;
import ListDatos.JListDatosBookMark;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentListener;
import javax.swing.JComponent;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import utiles.IListaElementos;
import utiles.JDepuracion;
import utiles.JListaElementos;


    // Variables declaration - do not modify
    // End of variables declaration
public class JPanelListaImagenes extends javax.swing.JPanel implements ActionListener, MouseListener, ListSelectionListener, IListDatosEdicion, ComponentListener {
    private static final long serialVersionUID = 1L;
    private static final String mcsAnterior = "Anterior";
    private static final String mcsSiguiente = "Siguiente";

    protected ListSelectionModel selectionModel;
    private JListDatos moModel;

    private final IListaElementos moActionListener;
    private IListaElementos moElementos;

    private int mlPagina = 0;
    private int mlNumeroPaginas = 1;
    private int mlElemenX;
    private int mlElemenY;
    private int mlElemenPagina;

    private final JBotonControl moBotonAnterior;
    private final JBotonControl moBotonSiguiente;
//    private int mlImagen;
//    private int mlDescrip;
    private IImagenFactory moImagen;
    private boolean mbPrimeraVez=true;

    /** Creates new form JPanelListaImagenes */
    public JPanelListaImagenes() {
        initComponents();
        addComponentListener(this);
        moActionListener = new JListaElementos();

        //boton anterior
        moBotonAnterior = new JBotonControl();
        moBotonAnterior.setImage("/utilesGUIx/images/atras.png");
        moBotonAnterior.setText(mcsAnterior);
        moBotonAnterior.setName(mcsAnterior);
        moBotonAnterior.addActionListener(this);

        //boton siguiente
        moBotonSiguiente = new JBotonControl();
        moBotonSiguiente.setImage("/utilesGUIx/images/alante.png");
        moBotonSiguiente.setText(mcsSiguiente);
        moBotonSiguiente.setName(mcsSiguiente);
        moBotonSiguiente.addActionListener(this);

        setSelectionModel(new DefaultListSelectionModel());
        setModel(
                new JListDatos(
                    null, "",
                    new String[]{"imagen","texto"},
                    new int[]{JListDatos.mclTipoCadena,JListDatos.mclTipoCadena},
                    new int[]{0}
                    ), new JImagenBasicaFactory(0, 1));
    }

    public synchronized void addMouseListener(MouseListener l) {
        super.addMouseListener(l);
    }

    public void setModel(JListDatos dataModel, IImagenFactory poImagen) {

        if (dataModel == null) {
            throw new IllegalArgumentException("Cannot set a null TableModel");
	}
        moElementos = new JListaElementos();
        moImagen = poImagen;
        if (this.moModel != dataModel) {
	    JListDatos old = this.moModel;
            if (old != null) {
                old.removeListenerEdicion(this);
	    }
            this.moModel = dataModel;
            dataModel.addListenerEdicion(this);

            edicionDatos(-1,-1,null);

	    firePropertyChange("model", old, dataModel);

        }
    }
    private void refrescarDimesiones(){
        try{
        setLayout(new GridLayout());
        JBotonControl loBoton = new JBotonControl();
            mlElemenX = (int)(getWidth() / (loBoton.getPreferredSize().getWidth()+4));
            mlElemenY = (int)(getHeight() / (loBoton.getPreferredSize().getHeight()+4));
            if(mlElemenX<2){
                mlElemenX=2;
            }
            if(mlElemenY<2){
                mlElemenY=2;
            }

        if(moModel.size()<mlElemenX * mlElemenY && moModel.size()>0 && getHeight()>0 && getWidth()>0){
            //por cada X (ldPropor) una Y
            double ldPropor = (double)getWidth() / (double)getHeight();

            boolean lbXMay = ldPropor > 1;
            double ldX=0;
            double ldY=0;
            //se suman las proporciones hasta q el resultado de la multi. sea > q model.size()
            while(moModel.size()>=(int)ldX*(int)ldY){
                ldX+=ldPropor;
                ldY++;
            }
            mlElemenX=(int)ldX;
            mlElemenY=(int)ldY;

            //por si las moscas, comprobamos q el size no sea mayor q la multiplica
            if(moModel.size()>=mlElemenX * mlElemenY){
                if(lbXMay){
                    mlElemenX++;
                }else{
                    mlElemenY++;
                }
            }
            //por si las moscas 2 veces, comprobamos q el size no sea mayor q la multiplica
            if(moModel.size()>=mlElemenX * mlElemenY){
                if(lbXMay){
                    mlElemenY++;
                }else{
                    mlElemenX++;
                }
            }

       }
        ((GridLayout)getLayout()).setColumns(mlElemenX);
        ((GridLayout)getLayout()).setRows(0);
        ((GridLayout)getLayout()).setHgap(2);
        ((GridLayout)getLayout()).setVgap(2);
        if(moModel.size()==1){
            ((GridLayout)getLayout()).setColumns(1);
            ((GridLayout)getLayout()).setRows(0);
        }
        if(moModel.size()>1 && moModel.size()<5){
            ((GridLayout)getLayout()).setColumns(2);
            ((GridLayout)getLayout()).setRows(0);
        }


        mlElemenPagina = mlElemenX * mlElemenY;

        mlNumeroPaginas = 1;
        int lElementos = mlElemenPagina-1;
        while(lElementos  < moModel.size()){
            lElementos += (mlElemenPagina-2);
            mlNumeroPaginas++;
        }
        }catch(Exception e){
            JDepuracion.anadirTexto(getClass().getName(), e);
        }
    }

    private int getPosicionInicialElementos(){
        int lNumeroPaginas = 1;
        int lElementos=0;

        if(mlPagina > (lNumeroPaginas-1)){
            lElementos = mlElemenPagina-1;
            lNumeroPaginas++;
            while((lElementos < moElementos.size()) &&
                  (mlPagina > (lNumeroPaginas-1))
                 ){
                lElementos += (mlElemenPagina-2);
                lNumeroPaginas++;

            }
        }
        return lElementos;
    }

    public JListDatos getModel() {
        return moModel;
    }

    private int buscarIndiceModel(final JElementoImagen poElem){
        int lResult = -1;
        for(int i = getPosicionInicialElementos(); i < moModel.size() && i < getMax() && lResult==-1 && poElem.getFila()!=null;i++){
            IFilaDatos loFila = (IFilaDatos) moModel.get(i);
            if(poElem.getFila() == loFila || poElem.getFila().toString().equals(loFila.toString())){
                lResult=i;
            }
        }

        return lResult;
    }
    public IImagen getImagen(final IFilaDatos poFila){
        JElementoImagen loIm = buscar(poFila);
        if(loIm!=null){
            return loIm.getImagen();
        }else{
            return null;
        }
    }
    public JElementoImagen buscar(final IFilaDatos poFila){
        JElementoImagen loBoton = null;
        for(int i = 0 ; i < moElementos.size() && loBoton==null; i++){
            JElementoImagen loAux = (JElementoImagen) moElementos.get(i);
            if(loAux.getFila() == poFila || 
                    (poFila!=null && loAux.getFila().toString().equals(poFila.toString()))){
                loBoton=loAux;
            }
        }
        return loBoton;

    }
    private JElementoImagen buscarOcrearImagen(final IFilaDatos poFila){
        JElementoImagen loBoton = buscar(poFila);
        if(loBoton==null){
            loBoton = new JElementoImagen();
            loBoton.setFila(poFila, moImagen.getImagenNueva());
            if(poFila != null){
                loBoton.addMouseListener(this);
            }
            moElementos.add(loBoton);
        }
        return loBoton;
    }
    private void addBoton(final JComponent  poBoton){
        add(poBoton);
    }
    private int getMax(){
        int lPosiMax = getPosicionInicialElementos() + mlElemenPagina;
        //añadimos boton anterior
        if(mlPagina > 0){
            lPosiMax--;
        }
        if(mlPagina < (mlNumeroPaginas-1)){
            lPosiMax--;
        }
        return lPosiMax;
    }
    /**Refrescamos la imagens de la pagina actual*/
    public synchronized void refrescarImagenes(boolean pbRefrescarTodasImg){
        refrescarDimesiones();

        //borramos los componenets previos
        removeAll();
        validate();
        //calculamos posicion minima y maxima
        int lPosiMin = getPosicionInicialElementos();
        int lPosiMax = getMax();
        //añadimos boton anterior
        if(mlPagina > 0){
            addBoton(moBotonAnterior);
        }
        //añadimos los datos modelo
        int i;
        for(i = lPosiMin; i < moModel.size() && i < lPosiMax;i++){
            if(pbRefrescarTodasImg){
                JElementoImagen loIm = buscarOcrearImagen((IFilaDatos) moModel.get(i));
                loIm.setFila((IFilaDatos) moModel.get(i), moImagen.getImagenNueva());
                addBoton(loIm);
            } else {
                 addBoton(buscarOcrearImagen((IFilaDatos) moModel.get(i)));
            }
        }

        //añadimos boton siguiente
        if(mlPagina < (mlNumeroPaginas-1)){
            for(;i < lPosiMax;i++ ){
                addBoton(buscarOcrearImagen(null));
            }
            addBoton(moBotonSiguiente);
        }
        validate();
        repaint();
    }
    /**Siguiente pagina de imagenes*/
    public void nextPagina(){
        if(mlPagina<mlNumeroPaginas){
            mlPagina++;
        }
        refrescarImagenes(false);
    }
    /**pagina previa de imagens*/
    public void previaPagina(){
        if(mlPagina>0){
            mlPagina--;
        }
        refrescarImagenes(false);
    }
    /**n action listenre*/
    public void addActionListener(final ActionListener poElem){
        moActionListener.add(poElem);
    }
    private void llamarListeners(final String psNombre){
        ActionEvent loEvent = new ActionEvent(this,0,psNombre);
        for(int i = 0; i < moActionListener.size(); i++){
            ActionListener l =( ActionListener)moActionListener.get(i);
            l.actionPerformed(loEvent);
        }
    }
    private void llamarListenerMouse(MouseEvent e, int plEvento) {
        MouseEvent loEvent = new MouseEvent(
                this, e.getID(), e.getWhen(),
                e.getModifiers(),
                ((Component)e.getSource()).getX()+e.getX(), ((Component)e.getSource()).getY()+e.getY(),
                e.getClickCount(),
                e.isPopupTrigger());
        MouseListener[] laoM= getMouseListeners();
        for(int i = 0; i <laoM.length; i++){
            switch(plEvento){
                case 0:
                    laoM[i].mouseClicked(loEvent);
                    break;
                case 1:
                    laoM[i].mousePressed(loEvent);
                    break;
                case 2:
                    laoM[i].mouseReleased(loEvent);
                    break;
                case 3:
                    laoM[i].mouseEntered(loEvent);
                    break;
                case 4:
                    laoM[i].mouseExited(loEvent);
                    break;
            }
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setLayout(new java.awt.GridLayout());
    }// </editor-fold>//GEN-END:initComponents

/////////////////////////////////////////////////////////////////////////////////////
//seleccion
/////////////////////////////////////////////////////////////////////////////////////


    public void setSelectionModel(ListSelectionModel newModel) {
        if (newModel == null) {
            throw new IllegalArgumentException("Cannot set a null SelectionModel");
        }

        ListSelectionModel oldModel = selectionModel;

        if (newModel != oldModel) {
            if (oldModel != null) {
                oldModel.removeListSelectionListener(this);
            }

            selectionModel = newModel;
            newModel.addListSelectionListener(this);

	    firePropertyChange("selectionModel", oldModel, newModel);
            repaint();
        }
    }

    public ListSelectionModel getSelectionModel() {
        return selectionModel;
    }

    public void setSelectionMode(int selectionMode) {
        clearSelection();
        selectionModel.setSelectionMode(selectionMode);

    }

    public int getRowCount() {
        return getModel().size();
    }
    private int boundRow(int row) throws IllegalArgumentException {
	if (row < 0 || row >= getRowCount()) {
	    throw new IllegalArgumentException("Row index out of range");
	}
	return row;
    }

    public void setRowSelectionInterval(int index0, int index1) {
        selectionModel.setSelectionInterval(boundRow(index0), boundRow(index1));

    }

    public int[] getSelectedRows() {
	int iMin = selectionModel.getMinSelectionIndex();
	int iMax = selectionModel.getMaxSelectionIndex();

	if ((iMin == -1) || (iMax == -1)) {
	    return new int[0];
	}

	int[] rvTmp = new int[1+ (iMax - iMin)];
	int n = 0;
	for(int i = iMin; i <= iMax; i++) {
	    if (selectionModel.isSelectedIndex(i)) {
		rvTmp[n++] = i;
	    }
	}
	int[] rv = new int[n];
	System.arraycopy(rvTmp, 0, rv, 0, n);
	return rv;
    }


    public int getSelectedRow() {
        return selectionModel.getMinSelectionIndex();
    }

    public void selectAll() {
        setRowSelectionInterval(0, getRowCount()-1);
    }

    public void clearSelection() {
        selectionModel.clearSelection();
    }
    private void clearSelectionAndLeadAnchor() {
        selectionModel.setValueIsAdjusting(true);

        clearSelection();

        selectionModel.setAnchorSelectionIndex(-1);
        selectionModel.setLeadSelectionIndex(-1);

        selectionModel.setValueIsAdjusting(false);

    }
/////////////////////////////////////////////////////////////////////////////////////
//evetnos
/////////////////////////////////////////////////////////////////////////////////////


    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == moBotonAnterior){
            previaPagina();
        }else{
            if(e.getSource() == moBotonSiguiente){
                nextPagina();
            }else{
                llamarListeners(e.getActionCommand());
            }
        }

    }
    private int limit(int i, int a, int b) {
	return Math.min(b, Math.max(i, a));
    }
    public void valueChanged(ListSelectionEvent e) {

	// The getCellRect() calls will fail unless there is at least one column.
	if (getRowCount() <= 0) {
	    return;
	}
        int firstIndex = limit(e.getFirstIndex(), 0, getRowCount()-1);
        int lastIndex = limit(e.getLastIndex(), 0, getRowCount()-1);

        //seleccionar todas las imagenes q procedan y deseleccionar el resto
        for(int i = firstIndex; i <= lastIndex && i < moModel.size(); i++){
            JElementoImagen loEl = buscar((IFilaDatos) moModel.get(i));
            if(loEl!=null && loEl.isSelected() != getSelectionModel().isSelectedIndex(i)){
                loEl.setSelected(getSelectionModel().isSelectedIndex(i));
            }
        }
    }
    private void tableRowsInserted(int plIndex) {
        int start = plIndex;
        int end = plIndex;
        if (start < 0) {
            start = 0;
	}
	if (end < 0) {
	    end = getRowCount()-1;
	}

        // Adjust the selection to account for the new rows.
	int length = end - start + 1;
	selectionModel.insertIndexInterval(start, length, true);

    }
    private void tableRowsDeleted(int plIndex) {
        int start = plIndex;
        int end = plIndex;
        if (start < 0) {
            start = 0;
	}
	if (end < 0) {
	    end = getRowCount()-1;
	}

	selectionModel.removeIndexInterval(start, end);
    }

    public void edicionDatos(int plModo, int plIndex, IFilaDatos poDatos) {
        if (plModo == -1) {
            // The whole thing changed
            clearSelectionAndLeadAnchor();

        }else{
            if (poDatos.getTipoModif() == JListDatos.mclNuevo) {
                tableRowsInserted(plIndex);
            }else if (poDatos.getTipoModif() == JListDatos.mclBorrar) {
                tableRowsDeleted(plIndex);
            }else{

            }
        }
        refrescarImagenes(false);

    }

    public void edicionDatosAntes(int plModo, int plIndex) throws Exception {

    }

    public void mouseClicked(MouseEvent e) {
        if(JElementoImagen.class.isAssignableFrom(e.getSource().getClass())){
            JElementoImagen loEl = (JElementoImagen) e.getSource();
            int lPosi = buscarIndiceModel(loEl);
            if(lPosi>=0){
                int onmask = e.CTRL_DOWN_MASK;
                if ((e.getModifiersEx() & (onmask)) == onmask) {
                    
                } else {
                    getSelectionModel().clearSelection();
                }             
                if(getSelectionModel().isSelectedIndex(lPosi) && e.getClickCount() == 1){
                    getSelectionModel().removeSelectionInterval(lPosi, lPosi);
                }else{
                    getSelectionModel().addSelectionInterval(lPosi, lPosi);
                }
            }
        }
        llamarListenerMouse(e, 0);
    }

    public void mousePressed(MouseEvent e) {
        llamarListenerMouse(e, 1);
    }

    public void mouseReleased(MouseEvent e) {
        llamarListenerMouse(e, 2);
    }

    public void mouseEntered(MouseEvent e) {
        llamarListenerMouse(e, 3);
    }

    public void mouseExited(MouseEvent e) {
        llamarListenerMouse(e, 4);
    }

    public void componentResized(ComponentEvent e) {
        componentShown(e);
    }

    public void componentMoved(ComponentEvent e) {
//        componentShown(e);
    }

    public void componentShown(ComponentEvent e) {
//        if(mbPrimeraVez){
//            mbPrimeraVez=false;
            refrescarDimesiones();
            refrescarImagenes(false);
//        }
    }

    public void componentHidden(ComponentEvent e) {
//        componentShown(e);
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables

}
