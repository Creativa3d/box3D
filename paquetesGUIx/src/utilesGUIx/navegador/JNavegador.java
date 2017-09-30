/*
 * JNavegador.java
 *
 * Created on 11 de abril de 2005, 10:20
 */

package utilesGUIx.navegador;

import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

/**Componente navegador de registros*/
public class JNavegador extends javax.swing.JPanel implements ActionListener {

    private INavegador moNavegador;
    
    /** Creates new form JNavegador */
    public JNavegador() {
        super();
        initComponents();
        //añadimos los action listener a esta misma clase
        btnBorrar.addActionListener(this);
        btnEditar.addActionListener(this);
        btnNuevo.addActionListener(this);
        btnRefrescar.addActionListener(this);
        btnAceptar.addActionListener(this);
        btnCancelar.addActionListener(this);
        
        btnAnterior.addActionListener(this);
        btnPrimero.addActionListener(this);
        btnSiguiente.addActionListener(this);
        btnUltimo.addActionListener(this);

        btnBuscar.addActionListener(this);
        
        btnAceptar.setMnemonic(java.awt.event.KeyEvent.VK_G);
        btnCancelar.setMnemonic(java.awt.event.KeyEvent.VK_C);
        btnNuevo.setMnemonic(java.awt.event.KeyEvent.VK_N);
        btnBorrar.setMnemonic(java.awt.event.KeyEvent.VK_E);
        btnEditar.setMnemonic(java.awt.event.KeyEvent.VK_M);
        btnBuscar.setMnemonic(java.awt.event.KeyEvent.VK_B);

        btnAnterior.setMnemonic(java.awt.event.KeyEvent.VK_A);
        btnPrimero.setMnemonic(java.awt.event.KeyEvent.VK_P);
        btnSiguiente.setMnemonic(java.awt.event.KeyEvent.VK_S);
        btnUltimo.setMnemonic(java.awt.event.KeyEvent.VK_U);
        
    }
    
    /**
     * Establece la fuente de datos
     * @param poDatos fuente datos
     */
    public void setDatos(INavegador poDatos){
        moNavegador = poDatos;
        btnBorrar.setVisible(moNavegador.getBorrarSN());
        btnEditar.setVisible(moNavegador.getEditarSN());
        btnNuevo.setVisible(moNavegador.getNuevoSN());
        btnRefrescar.setVisible(moNavegador.getRefrescarSN());
        btnBuscar.setVisible(moNavegador.getBuscarSN());
        if(!moNavegador.getDentroFormEdicionSN()){
            btnAceptar.setVisible(false);
            btnCancelar.setVisible(false);
            jSeparator2.setVisible(false);
        }
        
    }
    /**Deshabilita aceptar y cancelar y habilita todo lo demas*/
    public void setModoNormal(){
        btnAceptar.setEnabled(false);
        btnCancelar.setEnabled(false);

        btnBorrar.setEnabled(true);
        btnEditar.setEnabled(true);
        btnNuevo.setEnabled(true);
        btnRefrescar.setEnabled(true);
        btnBuscar.setEnabled(true);
        

        btnAnterior.setEnabled(true);
        btnPrimero.setEnabled(true);
        btnSiguiente.setEnabled(true);
        btnUltimo.setEnabled(true);
    }
    /**Habilita aceptar y cancelar y desabilita todo lo demas*/
    public void setModoEdicion(){
        btnAceptar.setEnabled(true);
        btnCancelar.setEnabled(true);

        btnBorrar.setEnabled(false);
        btnEditar.setEnabled(false);
        btnNuevo.setEnabled(false);
        btnRefrescar.setEnabled(false);
        btnBuscar.setEnabled(false);

        btnAnterior.setEnabled(false);
        btnPrimero.setEnabled(false);
        btnSiguiente.setEnabled(false);
        btnUltimo.setEnabled(false);
    }
    /**Ejecuta las acciones de los botones*/
    public void actionPerformed(java.awt.event.ActionEvent e) {
        try{
            if(e.getSource() == btnAnterior){
                btnSiguiente.setEnabled(true);
                btnUltimo.setEnabled(true);
                if(!moNavegador.anterior()){
                    btnPrimero.setEnabled(false);
                    btnAnterior.setEnabled(false);
                }
            }
            if(e.getSource() == btnPrimero){
                btnSiguiente.setEnabled(true);
                btnUltimo.setEnabled(true);
                if(!moNavegador.primero()){
                    btnPrimero.setEnabled(false);
                    btnAnterior.setEnabled(false);
                }
            }
            if(e.getSource() == btnSiguiente){
                btnPrimero.setEnabled(true);
                btnAnterior.setEnabled(true);
                if(!moNavegador.siguiente()){
                    btnSiguiente.setEnabled(false);
                    btnUltimo.setEnabled(false);
                }
            }
            if(e.getSource() == btnUltimo){
                btnPrimero.setEnabled(true);
                btnAnterior.setEnabled(true);
                if(!moNavegador.ultimo()){
                    btnSiguiente.setEnabled(false);
                    btnUltimo.setEnabled(false);
                }
            }
            if(e.getSource() == btnBorrar){

                    if (JOptionPane.showConfirmDialog(this, "¿Estas seguro de borrar el registro actual?", "Confirmación", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION){
                        moNavegador.borrar();
                    }
                
            }
            if(e.getSource() == btnEditar){
                moNavegador.editar();
                if(moNavegador.getDentroFormEdicionSN()){
                    setModoEdicion();
                }
                
            }
            if(e.getSource() == btnNuevo){
                moNavegador.nuevo();
                if(moNavegador.getDentroFormEdicionSN()){
                    setModoEdicion();
                }
            }
            if(e.getSource() == btnAceptar){
                moNavegador.aceptar();
                if(moNavegador.getDentroFormEdicionSN()){
                    setModoNormal();
                }

            }
            if(e.getSource() == btnCancelar){
                moNavegador.cancelar();
                if(moNavegador.getDentroFormEdicionSN()){
                    setModoNormal();
                }
            }
            if(e.getSource() == btnRefrescar){
                moNavegador.refrescar();
                btnPrimero.setEnabled(true);
                btnAnterior.setEnabled(true);
                btnSiguiente.setEnabled(true);
                btnUltimo.setEnabled(true);
            }
            if(e.getSource() == btnBuscar){
                moNavegador.buscar();
                btnPrimero.setEnabled(true);
                btnAnterior.setEnabled(true);
                btnSiguiente.setEnabled(true);
                btnUltimo.setEnabled(true);
            }
            
        }catch(Throwable error){
            utilesGUIx.msgbox.JMsgBox.mensajeErrorYLog(this, error, getClass().getName());
        }
    }    
        
    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnPrimero = new utilesGUIx.JButtonCZ();
        btnAnterior = new utilesGUIx.JButtonCZ();
        btnSiguiente = new utilesGUIx.JButtonCZ();
        btnUltimo = new utilesGUIx.JButtonCZ();
        jSeparator1 = new javax.swing.JSeparator();
        btnAceptar = new utilesGUIx.JButtonCZ();
        btnCancelar = new utilesGUIx.JButtonCZ();
        jSeparator2 = new javax.swing.JSeparator();
        btnNuevo = new utilesGUIx.JButtonCZ();
        btnEditar = new utilesGUIx.JButtonCZ();
        btnBorrar = new utilesGUIx.JButtonCZ();
        btnRefrescar = new utilesGUIx.JButtonCZ();
        btnBuscar = new utilesGUIx.JButtonCZ();

        setLayout(new java.awt.GridLayout(1, 0));

        btnPrimero.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Rewind16.gif"))); // NOI18N
        btnPrimero.setToolTipText("Primero (Alf+P)");
        add(btnPrimero);

        btnAnterior.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Back16.gif"))); // NOI18N
        btnAnterior.setToolTipText("Anterior (Alf+A)");
        add(btnAnterior);

        btnSiguiente.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Forward16.gif"))); // NOI18N
        btnSiguiente.setToolTipText("Siguiente (Alf+S)");
        add(btnSiguiente);

        btnUltimo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/FastForward16.gif"))); // NOI18N
        btnUltimo.setToolTipText("Último (Alf+U)");
        add(btnUltimo);
        add(jSeparator1);

        btnAceptar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/accept.gif"))); // NOI18N
        btnAceptar.setToolTipText("Guardar (Alf+G)");
        add(btnAceptar);

        btnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/cancel.gif"))); // NOI18N
        btnCancelar.setToolTipText("Cancelar (Alf+C)");
        add(btnCancelar);
        add(jSeparator2);

        btnNuevo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Add16.gif"))); // NOI18N
        btnNuevo.setToolTipText("Nuevo (Alf+N)");
        add(btnNuevo);

        btnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Edit16.gif"))); // NOI18N
        btnEditar.setToolTipText("Modificar (Alf+M)");
        add(btnEditar);

        btnBorrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Delete16.gif"))); // NOI18N
        btnBorrar.setToolTipText("Eliminar (Alf+E)");
        add(btnBorrar);

        btnRefrescar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Refresh16.gif"))); // NOI18N
        btnRefrescar.setToolTipText("Refrescar");
        add(btnRefrescar);

        btnBuscar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/utilesGUIx/images/Find16.gif"))); // NOI18N
        btnBuscar.setToolTipText("Buscar (Alf+B)");
        add(btnBuscar);
    }// </editor-fold>//GEN-END:initComponents


    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public utilesGUIx.JButtonCZ btnAceptar;
    public utilesGUIx.JButtonCZ btnAnterior;
    public utilesGUIx.JButtonCZ btnBorrar;
    public utilesGUIx.JButtonCZ btnBuscar;
    public utilesGUIx.JButtonCZ btnCancelar;
    public utilesGUIx.JButtonCZ btnEditar;
    public utilesGUIx.JButtonCZ btnNuevo;
    public utilesGUIx.JButtonCZ btnPrimero;
    public utilesGUIx.JButtonCZ btnRefrescar;
    public utilesGUIx.JButtonCZ btnSiguiente;
    public utilesGUIx.JButtonCZ btnUltimo;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    // End of variables declaration//GEN-END:variables
    
}
