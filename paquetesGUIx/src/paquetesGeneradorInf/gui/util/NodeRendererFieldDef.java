/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui.util;

import ListDatos.JListDatos;
import ListDatos.estructuraBD.JFieldDef;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import utilesGUI.tabla.IComponentParaTabla;
import utilesGUIx.JCheckBoxCZ;
import utilesGUIx.JTextFieldCZ;

public class NodeRendererFieldDef implements TreeCellRenderer {

    private JCheckBoxCZ moCheck = new JCheckBoxCZ();
    private JTextFieldCZ moCadena = new JTextFieldCZ();
    private JTextFieldCZ moFecha = new JTextFieldCZ();
    private JTextFieldCZ moNumero = new JTextFieldCZ();
    private JTextFieldCZ moMoneda3D = new JTextFieldCZ();
    private JTextFieldCZ moMoneda = new JTextFieldCZ();
    private JTextFieldCZ moPorcentual3D = new JTextFieldCZ();
    private JTextFieldCZ moPorcentual = new JTextFieldCZ();
    private DefaultTreeCellRenderer nonLeafRenderer = new DefaultTreeCellRenderer();
    Color selectionBorderColor, selectionForeground, selectionBackground,
            textForeground, textBackground;

    public NodeRendererFieldDef() {
        Font fontValue;
        moCadena.setTipoBD(JListDatos.mclTipoCadena);
        moFecha.setTipoBD(JListDatos.mclTipoFecha);
        moNumero.setTipoBD(JListDatos.mclTipoNumeroDoble);
        moMoneda3D.setTipoBD(JListDatos.mclTipoMoneda3Decimales);
        moMoneda.setTipoBD(JListDatos.mclTipoMoneda);
        moPorcentual3D.setTipoBD(JListDatos.mclTipoPorcentual3Decimales);
        moPorcentual.setTipoBD(JListDatos.mclTipoPorcentual);
        fontValue = UIManager.getFont("Tree.font");
        if (fontValue != null) {
            moCheck.setFont(fontValue);
            moCadena.setFont(fontValue);
            moFecha.setFont(fontValue);
            moNumero.setFont(fontValue);
            moMoneda3D.setFont(fontValue);
            moMoneda.setFont(fontValue);
            moPorcentual3D.setFont(fontValue);
            moPorcentual.setFont(fontValue);
        }
        moCadena.setBorder(null);
        moFecha.setBorder(null);
        moNumero.setBorder(null);
        moMoneda3D.setBorder(null);
        moMoneda.setBorder(null);
        moPorcentual3D.setBorder(null);
        moPorcentual.setBorder(null);
        Boolean booleanValue = (Boolean) UIManager.get("Tree.drawsFocusBorderAroundIcon");
        moCheck.setFocusPainted((booleanValue != null) && (booleanValue.booleanValue()));

        selectionBorderColor = UIManager.getColor("Tree.selectionBorderColor");
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");
        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
    }

    protected IComponentParaTabla getLeafRenderer(JFieldDef poCampo) {
        IComponentParaTabla loResult = null;
        switch(poCampo.getTipo()){
            case JListDatos.mclTipoBoolean:
                loResult = moCheck;
                break;
            case JListDatos.mclTipoCadena:
                loResult=moCadena;
                break;
            case JListDatos.mclTipoFecha:
                loResult=moFecha;
                break;
            case JListDatos.mclTipoNumero:
            case JListDatos.mclTipoNumeroDoble:
                loResult=moNumero;
                break;
            case JListDatos.mclTipoMoneda3Decimales:
                loResult=moMoneda3D;
                break;
            case JListDatos.mclTipoMoneda:
                loResult=moMoneda;
                break;
            case JListDatos.mclTipoPorcentual3Decimales:
                loResult=moPorcentual3D;
                break;
            case JListDatos.mclTipoPorcentual:
                loResult=moPorcentual;
                break;
        }
        return loResult;
    }


    public Component getTreeCellRendererComponent(JTree tree, Object value,
            boolean selected, boolean expanded, boolean leaf, int row,
            boolean hasFocus) {

        Component returnValue;
        JFieldDef loDatos=null;
        if ((value != null) && (value instanceof DefaultMutableTreeNode)) {
            Object userObject = ((DefaultMutableTreeNode) value).getUserObject();
            if (userObject instanceof JFieldDef) {
                loDatos = (JFieldDef) userObject;
            }
        }
        if (loDatos!=null) {
            IComponentParaTabla loComp = getLeafRenderer(loDatos);
            String lsCaption = loDatos.getCaption();
            String lsToolTip = loDatos.getCaption();
            if(lsCaption==null ||lsCaption.equals("") || lsCaption.equals(loDatos.getNombre())){
                lsCaption = loDatos.getNombre();
                lsToolTip = lsCaption;
            }else{
                lsToolTip = lsCaption + "[" + loDatos.getNombre() + "]";
            }
            //para el case check tenemos q presentar el nombre/caption
            if(loDatos.getTipo() == JListDatos.mclTipoBoolean){
                JCheckBoxCZ loCheck = (JCheckBoxCZ) loComp;
                loCheck.setText(lsCaption);
            }else{
                JTextFieldCZ loText = (JTextFieldCZ) loComp;
            }
            //tooltip
            tree.setToolTipText("<html>"+
                    (loDatos.getDescripcion()!=null?loDatos.getDescripcion() + "<br>"+"("+lsToolTip+")":lsToolTip) +
                    "</html>");
            //establecemos el valor
            try {
                loComp.setValueTabla(loDatos.getValue());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            //habilitamos el componente
            Component loCompF = (Component) loComp;
            loCompF.setEnabled(tree.isEnabled());

            //establecemos los colores
            if (selected) {
                loCompF.setForeground(selectionForeground);
                loCompF.setBackground(selectionBackground);
            } else {
                loCompF.setForeground(textForeground);
                loCompF.setBackground(textBackground);
            }

            returnValue = loCompF;
        } else {
            tree.setToolTipText("");
            returnValue = nonLeafRenderer.getTreeCellRendererComponent(tree,
                    value, selected, expanded, leaf, row, hasFocus);
        }
        return returnValue;
    }
}