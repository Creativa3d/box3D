/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package utiles.cuadernos.q58y19;

import utiles.IListaElementos;
import utiles.JCuentaBancaria;
import utiles.JDateEdu;
import utiles.JListaElementos;



public class JCuadernos {
    public static final int mclCuarderno19=0;
    public static final int mclCuarderno58=1;
    public static final int mclCuarderno34=2;
    
    private JCuadernoPresentador moPresentador ;
    private JCuadernoTotal moTotal = new JCuadernoTotal();
    private IListaElementos moListaOrdenantes = new JListaElementos();
    private int mlTipoCuaderno=mclCuarderno58;

    public JCuadernos(){
        JDateEdu loDate = new JDateEdu();
        moPresentador = new JCuadernoPresentador(loDate);

    }

    public void setFechaSoporte(JDateEdu poDate){
        moPresentador.setFechaConcepcion(poDate);

        for(int i = 0 ; i < moListaOrdenantes.size(); i++){
            JCuadernoOrdenante loCliente = (JCuadernoOrdenante) moListaOrdenantes.get(i);
            loCliente.setFechaConcepcion(poDate);
        }
    }

    public void setTipoCuaderno(int plTipo){
        mlTipoCuaderno=plTipo;
        moPresentador.setTipoCuaderno(mlTipoCuaderno);
        moTotal.setTipoCuaderno(mlTipoCuaderno);

        for(int i = 0 ; i < moListaOrdenantes.size(); i++){
            JCuadernoOrdenante loCliente = (JCuadernoOrdenante) moListaOrdenantes.get(i);
            loCliente.setTipoCuaderno(mlTipoCuaderno);
        }

    }

    public JCuadernoPresentador getPresentador(){
        return moPresentador;
    }

    public JCuadernoTotal getTotalJUNTO(){
        moTotal.setNumeroOrdenantesDiferentes(moListaOrdenantes.size());
        moTotal.setSuma(0);
        moTotal.setNumeroRegistros(2);
        moTotal.setNumeroIndividuales(0);
        for(int i = 0 ; i<moListaOrdenantes.size(); i++){
             JCuadernoTotalOrdenante loOrd = ((JCuadernoOrdenante) moListaOrdenantes.get(i)).getTotalDomicilioJUNTO2();
             moTotal.setSuma(moTotal.getSuma() + loOrd.getSuma());
             moTotal.setNumeroIndividuales(moTotal.getNumeroIndividuales() + loOrd.getNumeroIndividuales());
             moTotal.setNumeroRegistros(moTotal.getNumeroRegistros() + loOrd.getNumeroRegistros());
        }
        return moTotal;
    }
    public JCuadernoTotal getTotalSeparado(){
        moTotal.setNumeroOrdenantesDiferentes(moListaOrdenantes.size());
        moTotal.setSuma(0);
        moTotal.setNumeroRegistros(2);
        moTotal.setNumeroIndividuales(0);
        for(int i = 0 ; i<moListaOrdenantes.size(); i++){
             JCuadernoTotalOrdenante loOrd = ((JCuadernoOrdenante) moListaOrdenantes.get(i)).getTotalDomicilioSeparado();
             moTotal.setSuma(moTotal.getSuma() + loOrd.getSuma());
             moTotal.setNumeroIndividuales(moTotal.getNumeroIndividuales() + loOrd.getNumeroIndividuales());
             moTotal.setNumeroRegistros(moTotal.getNumeroRegistros() + loOrd.getNumeroRegistros());
        }
        return moTotal;
    }

    public JCuadernoOrdenante addOrdenante(){
        JCuadernoOrdenante loCliente = new JCuadernoOrdenante(moPresentador.getFechaConcepcion());
        moListaOrdenantes.add(loCliente);
        return loCliente;
    }
    public IListaElementos getOrdenantes(){
        return moListaOrdenantes;
    }

    int getTipoCuaderno() {
        return mlTipoCuaderno;
    }
    public static JCuentaBancaria validarCCC(String psCCC, String psNIF, String psEntidad, String psOtro) throws Exception{
        if(JCuentaBancaria.validarCuenta(psCCC)){
            return new JCuentaBancaria(psCCC);
        }else{
            throw new Exception("CCC "+psEntidad+" incorrecto de NIF " + psNIF + " "  + psOtro);
        }
        
    }
}
