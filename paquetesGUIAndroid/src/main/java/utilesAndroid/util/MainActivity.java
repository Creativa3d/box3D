package utilesAndroid.util;

import ListDatos.ECampoError;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import utiles.JDateEdu;
import utilesGUIx.ColorCZ;
import utilesGUIx.JTableCZ;
import utilesGUIx.JTableModel;
import utilesGUIx.formsGenericos.colores.JPanelGenericoColores;
import utilesGUIx.msgbox.JMsgBox;

public class MainActivity extends Activity
{
    private JListDatos moList;
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.main);
            LinearLayout loLin = (LinearLayout) findViewById(R.id.tablacuerpo);
            final JTableCZ jTable1 = new JTableCZ(this);
            createList();
            jTable1.setModel(new JTableModel(moList));
            HorizontalScrollView  loSH = new HorizontalScrollView (this);
            loSH.addView(jTable1, LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT);
            loLin.addView(loSH, new TableLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT, 1));
            
            
            JPanelGenericoColores loColores = new JPanelGenericoColores(moList);
            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
            loFiltro.addCondicionAND(JListDatos.mclTIgual, 3, JListDatos.mcsTrue);
            loColores.addCondicion(loFiltro, new ColorCZ(Color.RED), new ColorCZ(Color.WHITE));
            loFiltro.inicializar(moList);
            jTable1.setTableCZColores(loColores);
            
            jTable1.refrescar();
            
            Button loB = (Button) findViewById(R.id.add);
            loB.setOnClickListener(new View.OnClickListener() {

                public void onClick(View arg0) {
                    try {
                        add5();
                        
                        jTable1.refrescarDatos();
                    } catch (Exception ex) {
                        JMsgBox.mensajeError(MainActivity.this, ex);
                    }
                }
            });
            
        } catch (Exception ex) {
            JMsgBox.mensajeError(this, ex);
        }
        
    }
    private void createList() throws ECampoError{
        moList = new JListDatos(null, "", new String[]{"camp   xcdf sf df d df do 1","campo    fff   2","campo3","campo4"}, new int[]{0,JListDatos.mclTipoNumeroDoble,JListDatos.mclTipoFecha, JListDatos.mclTipoBoolean}, new int[]{0});

        add5();
    }
    private void add5() throws ECampoError{
        JDateEdu loDate = new JDateEdu();
        for(int i = 0 ; i < 5; i++){
            moList.addNew();
            moList.getFields(0).setValue(String.valueOf(i)+".1");
            moList.getFields(1).setValue(String.valueOf(i)+".2");
            loDate.add(loDate.mclDia, 1);
            moList.getFields(2).setValue(loDate.toString());
            moList.getFields(3).setValue(i % 2 == 0);
            moList.update(false);
        }
    }
}
