/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package utilesGUIx.formsGenericos;

import utilesAndroid.util.R;
import utilesGUIx.ITableCZ;
import utilesGUIx.JTableCZ;
import utilesGUIx.msgbox.JMsgBox;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;

/**
 *
 * @author eduardo
 */
public class JPanelGenerico extends JPanelGenericoAbstract{
    private Button moButtonADD;
    private JTableCZ jTable1;
    private EditText txtSearch;
    private Button moButtonMENU;

    public JPanelGenerico(Context poCont) {
        super(poCont);
        View loView = ((LayoutInflater)poCont.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(
                R.layout.jpanelgenerico, this);  
        try {

            jTable1 = new JTableCZ(this.getContext());
            moButtonADD = (Button) findViewById(R.id.add);
            moButtonMENU = (Button) findViewById(R.id.menu);
            txtSearch = (EditText) findViewById(R.id.txtSearch);
            HorizontalScrollView  loSH = (HorizontalScrollView )findViewById(R.id.tablacuerpo);
            loSH.addView(jTable1
                    , HorizontalScrollView.LayoutParams.FILL_PARENT
                    , HorizontalScrollView.LayoutParams.FILL_PARENT);
            
            inicializar();
            
            moButtonMENU.setOnClickListener(new OnClickListener() {
                public void onClick(View arg0) {
                    menushow();
                }
            });            
        } catch (Exception ex) {
            JMsgBox.mensajeError(this.getContext(), ex);
        }
                
    }

    @Override
    public void setActividad(JActividadDefecto jActividadDefecto) {
    	super.setActividad(jActividadDefecto);
    	moActividad.registerForContextMenu(jTable1);
    }
    
    @Override
    public EditText getPanelGeneralFiltroTodosCamp1() {
        return txtSearch;
    }

    @Override
    public Button getBtnNuevo() {
        return moButtonADD;
    }

    @Override
    public ITableCZ getTabla() {
        return jTable1;
    }

    @Override
    public void setTotal(String psValor) {
    }

    @Override
    public void setVisiblePanelConfigyFiltroRap(boolean pbVisible) {
        txtSearch.setVisibility(pbVisible ? Button.VISIBLE : Button.INVISIBLE);
    }

    public JTablaConfig getTablaConfig() {
        return null;
    }

    public Object getPanelInformacion() {
        return null;
    }
    
}
