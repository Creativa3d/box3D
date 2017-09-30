/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;

public class JCuardernoExportar {
    private JCuadernos moCuaderno;
    private String msFichero;

    public JCuardernoExportar(){
    }

    public void setFichero(String psFichero){
        msFichero=psFichero;
    }
    public void setCuaderno(JCuadernos poCuaderno){
        moCuaderno=poCuaderno;
    }

    public void exportar() throws  Throwable {
        switch(moCuaderno.getTipoCuaderno()){
            case JCuadernos.mclCuarderno19:
                JCuardernoExportar19 lo19 = new JCuardernoExportar19();
                lo19.setCuaderno(moCuaderno);
                lo19.setFichero(msFichero);
                lo19.exportar();
                break;
            case JCuadernos.mclCuarderno58:
                JCuardernoExportar58 lo58 = new JCuardernoExportar58();
                lo58.setCuaderno(moCuaderno);
                lo58.setFichero(msFichero);
                lo58.exportar();
                break;
            case JCuadernos.mclCuarderno34:
                JCuardernoExportar34 lo34 = new JCuardernoExportar34();
                lo34.setCuaderno(moCuaderno);
                lo34.setFichero(msFichero);
                lo34.exportar();
                break;
        }
    }
}
