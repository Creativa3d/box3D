package utilesGUIx;

import java.lang.reflect.InvocationTargetException;

import utiles.JDepuracion;
import utilesGUI.tabla.ITabla;
import ListDatos.JFilaDatosDefecto;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TableRow;

public class JTableCZAdapter  extends BaseAdapter {
	private final JTableCZ moTablaCZ;
	private final ITabla moModel;

	public JTableCZAdapter(JTableCZ poTabla, ITabla values) {
	    super();
	    this.moTablaCZ = poTabla;
	    this.moModel = values;
	  }
	@Override
	public int getCount() {
		return moModel.getRowCount();
	}

	@Override
	public Object getItem(int position) {
		return moModel.getValueAt(position, 0);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int lFila, View convertView, ViewGroup parent) {
        TableRow row=null;
        try {
	        if(convertView==null){
	        	row = moTablaCZ.getRowCuerpo();
				addViewsAFila(row, moModel.getColumnCount());
	        }else{
	            row = (TableRow) convertView;
	        }
			
			row.setTag(String.valueOf(lFila) + JFilaDatosDefecto.mcsSeparacion1);
			for (int lCol = 0; lCol < moModel.getColumnCount(); lCol++) {
                moTablaCZ.setValorCelda(lFila, lCol, row.getChildAt(lCol));
            }			
		} catch (Throwable e) {
			JDepuracion.anadirTexto(getClass().getName(), e);
		}
		
		return row;
	}
	private void addViewsAFila(TableRow poRow, int plColumns) throws IllegalArgumentException, SecurityException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        for (; plColumns > poRow.getChildCount();) {
            View loV = moTablaCZ.getViewCuerpo(moModel.getColumnClass(poRow.getChildCount()));
            poRow.addView(loV, moTablaCZ.getLayoutParamCuerpo()[poRow.getChildCount()]);
        }
    }	
}
