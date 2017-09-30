package utilesGUIx;

import ListDatos.IFilaDatos;
import ListDatos.JFilaDatosDefecto;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Iterator;
import javax.swing.DefaultListModel;

import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListCellRenderer;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.UIManager;
import utiles.IListaElementos;
import utiles.JListaElementos;

public class JCheckList extends JList {

    /**
     * Separación de la clave con la descripción
     */
    public String msSeparacionDescri = " - ";
    private DefaultListModel<JCheckListItem> moDefaultModel = new DefaultListModel<JCheckListItem>();

    public JCheckList(final Object[] listData){
        this();
        for(int i = 0 ; i < listData.length;i++){
            addLinea(listData[i].toString(), listData[i].toString());
        }
    }
            
    /**
     * Constructs a <code>JList</code> with an empty, read-only, model.
     */
    public JCheckList() {
        super();

        setModel(moDefaultModel);

        setCellRenderer(new CheckListRenderer());
        setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

      // Add a mouse listener to handle changing selection
        addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent event) {
                JList list = (JList) event.getSource();

            // Get index of item clicked
                int index = list.locationToIndex(event.getPoint());
                JCheckListItem item = (JCheckListItem) list.getModel().getElementAt(index);

            // Toggle selected state
                item.setSelected(!item.isSelected());

            // Repaint cell
                list.repaint(list.getCellBounds(index, index));
            }
        });
    }

    /**
     * Rellena la lista del componente
     *
     * @param poDatos datos
     * @param lPosiDescri posición de la descripción
     * @param lPosiCods posiciones de los códigos
     */
    public void RellenarCombo(IListaElementos poDatos, int lPosiDescri, int[] lPosiCods) {
        RellenarCombo(poDatos, new int[]{lPosiDescri}, lPosiCods);
    }

    /**
     * Rellena la lista del componente
     *
     * @param lPosiDescris posiciones de las descripciones
     * @param poDatos datos
     * @param lPosiCods posiciones de los códigos
     */
    public void RellenarCombo(IListaElementos poDatos, int[] lPosiDescris, int[] lPosiCods) {
        StringBuilder lsClave;
        String lsClaveS;
        String lsUltValor = "";
        String lsDescriS = "";
        IFilaDatos loFilaDatos;

        borrarTodo();

        Iterator enum1 = poDatos.iterator();
        lsClave = new StringBuilder(25);
        for (; enum1.hasNext();) {
            loFilaDatos = (IFilaDatos) enum1.next();
            lsClave.setLength(0);
            for (int i = 0; i < lPosiCods.length; i++) {
                lsClave.append(loFilaDatos.msCampo(lPosiCods[i]));
                lsClave.append(JFilaDatosDefecto.mcsSeparacion1);
            }
            lsClaveS = lsClave.toString();
            lsClave.setLength(0);
            for (int i = 0; i < lPosiDescris.length; i++) {
                if (i != 0) {
                    lsClave.append(msSeparacionDescri);
                }
                lsClave.append(loFilaDatos.msCampo(lPosiDescris[i]));
            }
            lsDescriS = lsClave.toString();
            if ((lsUltValor.compareTo(lsClaveS) != 0) && (lsDescriS.compareTo("") != 0)) {
                moDefaultModel.addElement(new JCheckListItem(lsClaveS, lsDescriS));
                lsUltValor = lsClaveS;
            }
        }
    }

    
    /**
     * Añade una linea
     *
     * @param psDescri descripción
     * @param psClave clave(separada por JFilaDatos.mcsSeparador y termina en
     * JFilaDatos.mcsSeparador)
     */
    public void addLinea(String psDescri, String psClave) {
        moDefaultModel.addElement(new JCheckListItem(psClave, psDescri));
    }

    /**
     * Selecciona una fila por la clave
     *
     * @param psClave clave, si son varios campos los valores deben estar
     * separados por JFilaDatos.mcsSepracion1 y debe terminar siempre en
     * JFilaDatos.mcsSepracion1
     * @return si ha seleccionado la clave en el componente
     */
    public boolean mbSeleccionarClave(String psClave) {
        boolean lbEncontrado = false;
        for (int i = 0; (i < getModel().getSize()) && (!lbEncontrado); i++) {
            if (msDevolverValorActualCombo(i).compareTo(psClave) == 0) {
                moDefaultModel.getElementAt(i).setSelected(true);
                lbEncontrado = true;
            }
            if (lbEncontrado) {
                break;
            }
        }
        return lbEncontrado;
    }
    
    public void deseleccionarTodo(){
        for(int i = 0 ; i < moDefaultModel.getSize();i++){
            if(moDefaultModel.get(i).isSelected()){
                moDefaultModel.get(i).setSelected(false);
            }
        }        
    }

    /**
     * Borra una línea
     */
    public void borrarLinea() {
        moDefaultModel.remove(getSelectedIndex());
    }

    /**
     * Devuelve la clave del elemento actual (separada por
     * JFilaDatos.mcsSeparador y termina en JFilaDatos.mcsSeparador)
     *
     * @return valor
     */
    public String msDevolverValorActualCombo() {
        return msDevolverValorActualCombo(getSelectedIndex());
    }

    /**
     * Devuelve el obj. FilaDatos del elemento actual
     *
     * @return fila
     */
    public IFilaDatos getFilaActual() {
        return new JFilaDatosDefecto(msDevolverValorActualCombo());
    }

    /**
     * Devuelve el obj. FilaDatos del índice pasado por parámetro
     *
     * @return fila de datos
     * @param i índice de la fila
     */
    public IFilaDatos getFila(int i) {
        return new JFilaDatosDefecto(msDevolverValorActualCombo(i));
    }

    /**
     * Borramos la lista de datos
     */
    public void borrarTodo() {
        moDefaultModel=new DefaultListModel<JCheckListItem>();
        setModel(moDefaultModel);
        
    }

    public IListaElementos<String> getSeleccionadosClaves(){
        JListaElementos<String> loResult = new JListaElementos<String>();
        for(int i = 0 ; i < moDefaultModel.getSize();i++){
            if(moDefaultModel.get(i).isSelected()){
                loResult.add(moDefaultModel.get(i).getClave());
            }
        }
        return loResult;
    }

    public IListaElementos<JCheckListItem> getSeleccionadosItem(){
        JListaElementos<JCheckListItem> loResult = new JListaElementos<JCheckListItem>();
        for(int i = 0 ; i < moDefaultModel.getSize();i++){
            if(moDefaultModel.get(i).isSelected()){
                loResult.add(moDefaultModel.get(i));
            }
        }
        return loResult;
    }    
    /**
     * Devuelve la clave del desplegable
     *
     * @return clave
     * @param plIndex índice de la lista
     */
    public String msDevolverValorActualCombo(int plIndex) {
        String lsValor = JFilaDatosDefecto.mcsSeparacion1;
        if (plIndex >= 0) {
            String lsCond = moDefaultModel.get(plIndex).getClave();
            if (lsCond.compareTo("") != 0) {
                lsValor = lsCond;
            }
        }
        return lsValor;
    }

    /**
     * Devuelve la descricipción actual
     *
     * @return descripción
     */
    public String msDevolverDescri() {
        return getSelectedValue().toString();
    }
    
    public static void main(String args[]) {
        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      // Create a list containing CheckListItem's
        JCheckList list = new JCheckList();
        list.addLinea("Pepe 1", "1");
        list.addLinea("Pepe 2", "2");
        list.addLinea("Pepe 3", "3");
        list.addLinea("Pepe 4", "4");

        frame.getContentPane().add(new JScrollPane(list));
        frame.pack();
        frame.setVisible(true);
    }
}

// Handles rendering cells in the list using a check box
class CheckListRenderer extends JCheckBox
        implements ListCellRenderer {

    public CheckListRenderer() {
      setBackground(UIManager.getColor("List.textBackground"));
      setForeground(UIManager.getColor("List.textForeground"));
    }

    public Component getListCellRendererComponent(
            JList list, Object value, int index,
            boolean isSelected, boolean hasFocus) {
        if(value instanceof JCheckListItem){
            setEnabled(list.isEnabled());
            setSelected(((JCheckListItem) value).isSelected());
            setFont(list.getFont());
            setBackground(list.getBackground());
            setForeground(list.getForeground());
            setText(value.toString());
        }
        return this;
    }
}
