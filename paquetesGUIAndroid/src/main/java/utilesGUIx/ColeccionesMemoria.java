package utilesGUIx;

import java.util.HashMap;

import utiles.IListaElementos;
import utiles.JListaElementos;
import android.view.View;
import android.widget.TableRow;

public 
class ColeccionesMemoria {

    private HashMap moListaVistas = new HashMap();

    public ColeccionesMemoria() {
    }

    private IListaElementos getLista(Class poClassView) {
        IListaElementos loLista = (IListaElementos) moListaVistas.get(poClassView);
        if (loLista == null) {
            loLista = new JListaElementos();
        }
        return loLista;
    }

    public synchronized View getViewCuerpo(Class poClassView) {
        View loView = null;
        IListaElementos loLista = null;
        loLista = getLista(poClassView);

        if (loLista.size() > 0) {
            loView = (View) loLista.get(loLista.size() - 1);
            loLista.remove(loLista.size() - 1);
        }
        return loView;
    }

    public synchronized void addViewCuerpo(View poView) {
        IListaElementos loLista = getLista(poView.getClass());
        loLista.add(poView);
    }

    public synchronized TableRow getRowCuerpo() {
        TableRow loRow = null;
        IListaElementos loLista = null;

        loLista = getLista(TableRow.class);

        if (loLista.size() > 0) {
            loRow = (TableRow) loLista.get(loLista.size() - 1);
            loLista.remove(loLista.size() - 1);
        }

        return loRow;

    }

    public synchronized void addRowCuerpo(TableRow row) {
        addViewCuerpo(row);
    }
}
