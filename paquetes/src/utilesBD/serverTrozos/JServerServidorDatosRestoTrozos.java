/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utilesBD.serverTrozos;

import utilesBD.serverTrozos.IServerServidorDatosTrozos;
import ListDatos.IResultado;
import ListDatos.ISelectEjecutarSelect;
import ListDatos.IServerEjecutar;
import ListDatos.IServerServidorDatos;
import ListDatos.JActualizar;
import ListDatos.JListDatos;
import ListDatos.JSelect;
import ListDatos.JServidorDatosAbtrac;
import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JTableDefs;

public class JServerServidorDatosRestoTrozos extends JServidorDatosAbtrac implements IServerServidorDatosTrozos{
    private int mlMin;
    private int mlMax;
    private final IServerServidorDatos moBD;
    /** Creates a new instance of ServidorDatos */
    public JServerServidorDatosRestoTrozos(IServerServidorDatos poBD) {
        super();
        moBD = poBD;

    }

    public void setIntervaloDatos(int plMin, int plMax){
        mlMin = plMin;
        mlMax = plMax;
    }

    public IResultado borrar(String psSelect, String psTabla, JFieldDefs poCampos) {
        return moBD.borrar(psSelect, psTabla, poCampos);
    }

    public IResultado modificar(String psSelect, String psTabla, JFieldDefs poCampos) {
        return moBD.modificar(psSelect, psTabla, poCampos);
    }

    public IResultado anadir(String psSelect, String psTabla, JFieldDefs poCampos) {
        return moBD.anadir(psSelect, psTabla, poCampos);                
    }

    public IResultado modificarEstructura(ISelectEjecutarSelect poEstruc) {
        return moBD.modificarEstructura(poEstruc);
    }

    public IResultado actualizar(String psSelect, JActualizar poActualizar) {
        return moBD.actualizar(psSelect, poActualizar);
    }

    public IResultado ejecutarServer(IServerEjecutar poEjecutar) {
        return moBD.ejecutarServer(poEjecutar);
    }

    public JTableDefs getTableDefs() throws Exception {
        return moBD.getTableDefs();
    }

    @Override
    protected void recuperarInformacion(JListDatos v, JSelect poSelect, String psTabla) throws Exception {
        JListDatos v2 = new JListDatos(v, false);
        moBD.recuperarDatos(v2, poSelect, psTabla, false, false, JListDatos.mclSelectNormal);
            if(mlMin<mlMax){
                v.ensureCapacity(mlMax - mlMin + 1);
            }
            int lFila = 0;
            boolean lbTroceo = mlMin<mlMax;
           if(v2.moveFirst()){
               do{
                    if(!lbTroceo || ( lFila>=mlMin && lFila<mlMax) ){
                        v.add(v2.moFila());
                    }                   
                   
                   lFila++;
               }while(v2.moveNext() && (!lbTroceo || (lFila<mlMax)));
           }
    }

}
