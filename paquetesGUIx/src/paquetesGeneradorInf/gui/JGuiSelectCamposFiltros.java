/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui;

import ListDatos.IListDatosFiltro;
import ListDatos.JListDatos;
import ListDatos.JListDatosFiltroConj;
import ListDatos.JListDatosFiltroElem;
import ListDatos.JSelect;
import ListDatos.estructuraBD.JFieldDef;
import ListDatos.estructuraBD.JTableDef;
import paquetesGeneradorInf.gui.util.JSelectMotorNeutro;
import utiles.IListaElementos;
import utiles.JListaElementos;
import utilesLex.JLex;
import utilesLex.JToken;


public class JGuiSelectCamposFiltros {
    private JSelectMotorNeutro moSelectMotor = new JSelectMotorNeutro(false);
    private JGuiSelectCamposTableModel moTableModelCampos;
    private JGuiSelectCampos moGuiCampos;
    private JLex moLex = new JLex();
    private String mcsIGUAL="=";
    private String mcsMAYOR=">";
    private String mcsAND="Y";
    private String mcsMENOR="<";
    private String mcsDISTINTO="!=";
    private String mcsMENORIGUAL="<=";
    private String mcsMAYORIGUAL=">=";
    private String mcsOR="O";
    private String mcsNOT="NO";
    private String mcsNULL="NULL";
    private String mcsPARENTESISI="(";
    private String mcsPARENTESISD=")";
    private String mcsDIVISOR="/";
    private String mcsPOR="*";
    private String mcsMAS="+";
    private String mcsMENOS="-";
    private String mcsLIKE="LIKE";
    private String mcsNUMERO="Numero";
    private String mcsCADENA="Cadena";
    private String mcsCADENADELIM="CadenaDelimitada";
    private String mcsFECHA="Fecha";
    private String mcsCAMPO="Campo";
    private String mcsESVACIO="ESVACIO";
    private String mcsTRUE=JListDatos.mcsTrue;
    private String mcsFALSE=JListDatos.mcsFalse;


    private int mlMaxTokenProcesado;

    JGuiSelectCamposFiltros(
            JGuiSelectCamposTableModel poTableModelCampos,
            JGuiSelectCampos poGuiCampos){
        moTableModelCampos = poTableModelCampos;
        moGuiCampos = poGuiCampos;

        //SIMBOLOS
        moLex.addPatron("=", mcsIGUAL);
        moLex.addPatron( "\\>", mcsMAYOR);
        moLex.addPatron( "\\<", mcsMENOR);
        moLex.addPatron( "(<>)|(!=)", mcsDISTINTO);
        moLex.addPatron( "(<=)|(=<)", mcsMENORIGUAL);
        moLex.addPatron( "(>=)|(=>)", mcsMAYORIGUAL);
        moLex.addPatron( "[Aa][Nn][Dd]|Y|y", mcsAND);
        moLex.addPatron( "[Oo][Rr]|O|o", mcsOR);
        moLex.addPatron( "[Nn][Oo][Tt]|[Nn][Oo]|\\!", mcsNOT);
        moLex.addPatron( "[Nn][Uu][Ll][Ll]|\"\"|''|[Vv][Aa][Vc][IÕiÌ][Oo]", mcsNULL);
        moLex.addPatron( "\\(", mcsPARENTESISI);
        moLex.addPatron( "\\)", mcsPARENTESISD);
        moLex.addPatron( "\\+", mcsMAS);
        moLex.addPatron( "\\*", mcsPOR);
        moLex.addPatron( "/", mcsDIVISOR);
        moLex.addPatron( "\\-", mcsMENOS);
        moLex.addPatron( "[Ss][IiÌÕ]|1|[Tt][Rr][Uu][Ee]", mcsTRUE);
        moLex.addPatron( "[Nn][Oo]|0|[Ff][Aa][Ll][Ss][Ee]", mcsFALSE);

        moLex.addPatron( "[Ll][Ii][Kk][Ee]|[Cc][Oo][Mm][Oo]", mcsLIKE);
        moLex.addPatron( "[eE][sS]\\s*[vV][Aa][Cc][IiÕÌ][Oo]", mcsESVACIO);
        
        //CONSTANTES
        moLex.addPatron("([0-9])+(\\.)?[0-9]*", mcsNUMERO);
        String lsCadenaFormato = "[A-Za-z0-9,;\\.:_\\+\\-\\*/·ÈÌÛ˙Ò¡…Õ”⁄—]";
        String lsCadenaFormatoDelimExtra = "[ A-Za-z0-9,;:_\\+\\-\\*/\\%\\.\\!\\#\\$\\(\\)\\=\\ø\\?\\{\\}·ÈÌÛ˙Ò¡…Õ”⁄—]";
        moLex.addPatron("("+lsCadenaFormato+")*", mcsCADENA);
        moLex.addPatron("\"("+lsCadenaFormatoDelimExtra+")*\"", mcsCADENADELIM);
        moLex.addPatron("\\'("+lsCadenaFormatoDelimExtra+")*\\'", mcsCADENADELIM);
        moLex.addPatron("\\#([0-9])+(\\/)([0-9])+(\\/)([0-9])+((\\s)([0-9])+(\\:)?[0-9]*(\\:)?[0-9]*)?\\#", mcsFECHA);
        //Campos
        moLex.addPatron("\\[([ A-Za-z0-9,;:_\\+\\-\\*/])*\\]", mcsCAMPO);

        //IGNORAR BLANCOS
        moLex.addPatron ("\\s", "BLANCO", false);
        //ingnorar retornos de carro y tab
        moLex.addPatron(String.valueOf((char)13), "Intro", false);
        moLex.addPatron(String.valueOf((char)10), "Intro2", false);
        moLex.addPatron(String.valueOf((char)9), "TAB", false);

    }

    /**guarda el filtro de la pantalla al JSelect*/
    public void guardarFiltro(JSelect poSelect) throws Exception{
        for(int ii = moTableModelCampos.lPosiCriterios;
                ii < moTableModelCampos.moList.size();
                ii++){
            JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
            for(int i = 0 ; i < moTableModelCampos.moList.getFields().size(); i++){
                JListDatosFiltroConj loAux = getFiltro(ii, i);
                if(loAux!=null && loAux.mbAlgunaCond()){
                    loFiltro.addCondicion(
                            ((Integer)loAux.getConjUniones().get(0)).intValue(),
                            loAux);
                }
            }
            if(loFiltro != null && loFiltro.mbAlgunaCond()){
                poSelect.getWhere().addCondicion(JListDatosFiltroConj.mclOR, loFiltro);
            }
        }
    }

    private JListDatosFiltroConj getFiltro(int plFila, int plColumn) throws Exception {
        if(moTableModelCampos.getValueAt(plFila,plColumn+1)!=null){
            JFieldDef loCampo=null;
            JTableDef loTabla=null;
            try{
                loCampo = moGuiCampos.getCampoDeColumna(plColumn);
                loTabla = moGuiCampos.getTablaDeFormat(plColumn);
            }catch(Exception e){

            }
            if(loCampo!=null){
                IListaElementos loListaToken = moLex.procesarCadena(moTableModelCampos.getValueAt(plFila,plColumn+1).toString());
                mlMaxTokenProcesado=-1;
                return getFiltro(loListaToken, 0, loCampo, loTabla);
            }else{
                return null;
            }
        }else{
            return null;
        }
    }
    private JListDatosFiltroConj getFiltro(IListaElementos poListaToken, int i, JFieldDef poCampo, JTableDef poTabla){
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        int lUnion = JListDatosFiltroConj.mclAND;
        int lComparador = JListDatos.mclTIgual;
        boolean lbFin = false;
        for( ;i < poListaToken.size() && !lbFin; i++){
            JToken loToken = (JToken) poListaToken.get(i);
            if(loToken.getNombreToken().equals(mcsPARENTESISI)){
                JListDatosFiltroConj loAux = getFiltro(poListaToken, i+1, poCampo, poTabla);
                if(loAux!=null && loAux.mbAlgunaCond()){
                    loFiltro.addCondicion(lUnion, loAux);
                }
                i=mlMaxTokenProcesado;
            }
            if(loToken.getNombreToken().equals(mcsPARENTESISD)){
                lbFin=true;
            }
            if(loToken.getNombreToken().equals(mcsESVACIO)){
                loFiltro.addCondicion(
                        lUnion,
                        new JListDatosFiltroElem(
                            JListDatos.mclTIgual,
                            poTabla.getNombre(),
                            new String[]{poCampo.getNombre()},
                            new String[]{""},
                            new int[]{poCampo.getTipo()}
                        )
                        );
            }
            if(isUnionLogica(loToken)){
                lUnion = getUnionLogica(loToken);
            }
            if(isComparadorLogico(loToken)){
                lComparador = getComparadorLogico(loToken);
            }
            if(isConstante(loToken)){
                loFiltro.addCondicion(
                        lUnion,
                        new JListDatosFiltroElem(
                            lComparador,
                            poTabla.getNombre(),
                            new String[]{poCampo.getNombre()},
                            new String[]{getConstante(loToken)},
                            new int[]{poCampo.getTipo()}
                        )
                        );
                lUnion=JListDatosFiltroConj.mclAND;
                lComparador=JListDatos.mclTIgual;
            }
            if(i>mlMaxTokenProcesado){
                mlMaxTokenProcesado=i;
            }
        }
        return loFiltro;
    }

    private int getUnionLogica(JToken loToken) {
        int lUnion=JListDatosFiltroConj.mclAND;
        if(loToken.getNombreToken().equals(mcsAND)){
            lUnion = JListDatosFiltroConj.mclAND;
        } else if(loToken.getNombreToken().equals(mcsOR)){
            lUnion = JListDatosFiltroConj.mclOR;
        }
        return lUnion;

    }
    private String getConstante(JToken poToken) {
        String lsResult = "";
        if(poToken.getNombreToken().equals(mcsCADENA)||
           poToken.getNombreToken().equals(mcsNUMERO)){
            lsResult = poToken.getCadena();
        }
        if(poToken.getNombreToken().equals(mcsCADENADELIM) ||
           poToken.getNombreToken().equals(mcsFECHA)){
            lsResult = poToken.getCadena().substring(1, poToken.getCadena().length()-1);
        }
        if(poToken.getNombreToken().equals(mcsNULL)){
            lsResult = "";
        }
        if(poToken.getNombreToken().equals(mcsTRUE)){
            lsResult = JListDatos.mcsTrue;
        }
        if(poToken.getNombreToken().equals(mcsFALSE)){
            lsResult = JListDatos.mcsFalse;
        }
        return lsResult;
    }
    private boolean isConstante(JToken poToken){
        return poToken.getNombreToken().equals(mcsCADENA) ||
               poToken.getNombreToken().equals(mcsCADENADELIM) ||
               poToken.getNombreToken().equals(mcsFECHA) ||
               poToken.getNombreToken().equals(mcsNUMERO) ||
               poToken.getNombreToken().equals(mcsTRUE) ||
               poToken.getNombreToken().equals(mcsFALSE) ||
               poToken.getNombreToken().equals(mcsNULL);
    }
    private boolean isComparadorLogico(JToken poToken){
        return poToken.getNombreToken().equals(mcsDISTINTO) ||
               poToken.getNombreToken().equals(mcsIGUAL) ||
               poToken.getNombreToken().equals(mcsMAYOR) ||
               poToken.getNombreToken().equals(mcsMAYORIGUAL) ||
               poToken.getNombreToken().equals(mcsMENOR) ||
               poToken.getNombreToken().equals(mcsMENORIGUAL) ||
               poToken.getNombreToken().equals(mcsLIKE);
    }
    private int getComparadorLogico(JToken poToken) {
        int lResult = JListDatos.mclTIgual;
        if(poToken.getNombreToken().equals(mcsDISTINTO)){
            lResult = JListDatos.mclTDistinto;
        }
        if(poToken.getNombreToken().equals(mcsIGUAL)){
            lResult = JListDatos.mclTIgual;
        }
        if(poToken.getNombreToken().equals(mcsMAYOR)){
            lResult = JListDatos.mclTMayor;
        }
        if(poToken.getNombreToken().equals(mcsMAYORIGUAL)){
            lResult = JListDatos.mclTMayorIgual;
        }
        if(poToken.getNombreToken().equals(mcsMENOR)){
            lResult = JListDatos.mclTMenor;
        }
        if(poToken.getNombreToken().equals(mcsMENORIGUAL)){
            lResult = JListDatos.mclTMenorIgual;
        }
        if(poToken.getNombreToken().equals(mcsLIKE)){
            lResult = JListDatos.mclTLike;
        }
        return lResult;
    }
    private boolean isUnionLogica(JToken poToken){
        return poToken.getNombreToken().equals(mcsAND) ||
               poToken.getNombreToken().equals(mcsOR);
    }

    /**muestra el filtro del JSelect en la pantalla*/
    public void mostrarFiltro(JSelect poSelect) throws Exception{
        //hay q normalizar el Where
        //sabemos q los and son prioritarios y solo queremos un nivel de JFiltroConj
        //1.1. cuando haya un or multiplicamos por el or y creamos 2 filtro conj separados por or
        //debe ser recursivo, es un arbol
        JListDatosFiltroConj loFiltro = new JListDatosFiltroConj();
        loFiltro.addCondicion(JListDatosFiltroConj.mclAND, poSelect.getWhere());
        loFiltro = calcularFiltroConj(loFiltro);
        //1.2. cada unidad de filtroconj va en una fila diferente
        for(int i = 0; loFiltro != null && i < loFiltro.getConjFiltros().size(); i++){
            //1.3. para cada filtroconj anterior cojemos las cond. de campos iguales y las ponemos debajo del campo correspondiente
            JListDatosFiltroConj loAux = getFiltroConj(loFiltro.getConjFiltros().get(i));
            IListaElementos loListaCampoCond = new JListaElementos();
            rellenarListaCampoCondic(loAux, loListaCampoCond);
            for(int ii = 0; ii < loListaCampoCond.size(); ii++){
                JGuiSelectCampoFiltroCampo loCampo = (JGuiSelectCampoFiltroCampo) loListaCampoCond.get(ii);
                mostrarCond(loCampo, i);
            }
        }
        
    }

    //rellenamos la lista con las condiciones agrupadas por campo, habra un
    //JGuiSelectCampoFiltroCampo por cada campo distinto
    private void rellenarListaCampoCondic(JListDatosFiltroConj poFiltro, IListaElementos poLista) {
        JGuiSelectCampoFiltroCampo loCampo = getSoloUnCampo(poFiltro, new JGuiSelectCampoFiltroCampo("",""));
        if(loCampo.msCampo!=null){
            loCampo.moFiltro = poFiltro;
            addListaCampoCondicDeCampo(poLista, loCampo);
        }else{
            for(int i = 0; i < poFiltro.getConjFiltros().size(); i++){
                JListDatosFiltroConj loFiltro = getFiltroConj(poFiltro.getConjFiltros().get(i));
                rellenarListaCampoCondic(loFiltro, poLista);
            }
        }

    }
    private JGuiSelectCampoFiltroCampo addListaCampoCondicDeCampo(IListaElementos poLista, JGuiSelectCampoFiltroCampo poCampo){
        JGuiSelectCampoFiltroCampo loResult = null;
        for(int i = 0 ; i < poLista.size() && loResult == null; i++){
            JGuiSelectCampoFiltroCampo loCampo = (JGuiSelectCampoFiltroCampo) poLista.get(i);
            if(loCampo.isIgual(poCampo)){
                loResult = loCampo;
            }
        }
        if(loResult!=null){
            loResult.moFiltro.addCondicion(
                    JListDatosFiltroConj.mclAND,
                    poCampo.moFiltro);
        }else{
            poLista.add(poCampo);
            loResult = poCampo;
        }
        return loResult;

    }
    private void mostrarCond(JGuiSelectCampoFiltroCampo poCampo, int plFilaRelativa) throws Exception {
        int lColumnaCampo = -1;
        int lColumnaPrimeraVacia = -1;
        //buscamos si el campo existe y la 1∫ columna vacia
        for(int i = 0 ;
                (i < moTableModelCampos.moList.getFields().size()) &&
                (lColumnaCampo < 0  || lColumnaPrimeraVacia < 0) &&
                (lColumnaCampo<0);
            i++ ){
            moTableModelCampos.moList.setIndex(moTableModelCampos.lPosiCampos);
            if(!moTableModelCampos.moList.getFields(i).isVacio()){
                String lsCampo = moGuiCampos.getCampoDeFormatNombre(moTableModelCampos.moList.getFields(i).getString());
                moTableModelCampos.moList.setIndex(moTableModelCampos.lPosiTablas);
                String lsTabla = moTableModelCampos.moList.getFields(i).getString();
                String lsTablaParam = moGuiCampos.getTablaFormat(poCampo.msTabla, "");
                if(lsCampo.equalsIgnoreCase(poCampo.msCampo) && lsTabla.equalsIgnoreCase(lsTablaParam)){
                    lColumnaCampo = i;

                }
            }else{
                if(lColumnaPrimeraVacia<0){
                    lColumnaPrimeraVacia = i;
                }
            }
        }
        //si no existe lo aÒadimos
        if(lColumnaCampo<0){
            lColumnaCampo=lColumnaPrimeraVacia;
            //ponemos el campo
            moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiCampos);
            moTableModelCampos.moList.getFields(lColumnaPrimeraVacia).setValue(
                    moGuiCampos.getCampoFormat(poCampo.msTabla, poCampo.msCampo, ""));
            moTableModelCampos.moList.update(false);

            //ponemos la tabla
            moTableModelCampos.moList.setIndex(JGuiSelectCamposTableModel.lPosiTablas);
            moTableModelCampos.moList.getFields(lColumnaPrimeraVacia).setValue(
                    moGuiCampos.getTablaFormat(poCampo.msTabla, ""));
            moTableModelCampos.moList.update(false);
        }

        //aÒadimos la condicion
        moTableModelCampos.moList.setIndex(plFilaRelativa + moTableModelCampos.lPosiCriterios);
        moTableModelCampos.moList.getFields(lColumnaCampo).setValue(
                (
                    (((Integer)poCampo.moFiltro.getConjUniones().get(0)).intValue() == JListDatosFiltroConj.mclOR 
                    && plFilaRelativa!=0
                    )
                    ? " O ": "" ) +
                poCampo.moFiltro.msSQL(moSelectMotor)
                );
        moTableModelCampos.moList.update(false);
    }

    private JListDatosFiltroConj calcularFiltroConj(final IListDatosFiltro poFiltro){
        JListDatosFiltroConj loFiltro = getFiltroConj(poFiltro);
        if(!loFiltro.mbAlgunaCond()){
            return null;
        }
        if(isSoloUnCampoOTodoAND(loFiltro) ){
            return loFiltro;
        }else{
            JListDatosFiltroConj loResult = new JListDatosFiltroConj();
            //1∫ condicion siempre es asi
            JListDatosFiltroConj loAux = new JListDatosFiltroConj();
            loAux.addCondicion(JListDatosFiltroConj.mclAND, getFiltroConj(loFiltro.getConjFiltros().get(0)));
            for(int i = 1; i < loFiltro.getConjUniones().size(); i++){
                int lUnion = ((Integer)loFiltro.getConjUniones().get(i)).intValue();
                JListDatosFiltroConj loCond = getFiltroConj(loFiltro.getConjFiltros().get(i));
                if(loCond.mbAlgunaCond()){
                    if(lUnion == JListDatosFiltroConj.mclAND){
                        if(isSoloUnCampoOTodoAND(loCond)){
                            //para cada condicion and se aÒade a aux si es una "unidad"
                            loAux.addCondicion(JListDatosFiltroConj.mclAND, loCond);
                        }else{
                            //si la cond. es mas de una "unidad", este proceso te devuelve un
                            //conj de filtros separados por or
                            JListDatosFiltroConj loAux2 = calcularFiltroConj(loCond);
                            loAux = multiplicacionCondiciones(loAux, loAux2);
                            loResult = unionCondiciones(loResult, loAux);
                            loAux = new JListDatosFiltroConj();
                        }
                    } else{
                        //se aÒade a result como or
                        loResult.addCondicion(JListDatosFiltroConj.mclOR, loAux);
                        loAux = new JListDatosFiltroConj();
                        if(isSoloUnCampoOTodoAND(loCond)){
                            //si la cond. es or y es una "unidad" se crea un nuevo aux, se le aÒade la cond
                            loAux.addCondicion(JListDatosFiltroConj.mclAND,(IListDatosFiltro) loFiltro.getConjFiltros().get(i));
                        }else{
                            //si la cond. es mas de una "unidad", este proceso te devuelve un
                            //conj de filtros spearados por or
                            JListDatosFiltroConj loAux2 = calcularFiltroConj(loCond);
                            //te devuelve el resulto de la union de los or
                            loResult = unionCondiciones(loResult, loAux2);
                        }
                    }
                }
            }
            if(loAux.mbAlgunaCond()){
                loResult.addCondicion(JListDatosFiltroConj.mclOR, loAux);
            }
            return loResult;
        }
    }
    private JListDatosFiltroConj getFiltroConj(Object poFiltro){
        JListDatosFiltroConj loCond;
        if(JListDatosFiltroConj.class.isAssignableFrom(poFiltro.getClass())){
            loCond =  (JListDatosFiltroConj) poFiltro;
        }else{
            loCond =  ((JListDatosFiltroElem) poFiltro).getFiltroConj();
        }
        return loCond;
    }


    private JListDatosFiltroConj unionCondiciones(final JListDatosFiltroConj poResult, final JListDatosFiltroConj poAux2) {
        for(int i = 0 ; i < poAux2.getConjFiltros().size(); i++ ){
            poResult.addCondicion(JListDatosFiltroConj.mclOR,(IListDatosFiltro) poAux2.getConjFiltros().get(i));
        }
        return poResult;
    }
    private JListDatosFiltroConj multiplicacionCondiciones(JListDatosFiltroConj poResult, JListDatosFiltroConj poAux2) {
        JListDatosFiltroConj loResult = new JListDatosFiltroConj();
        for(int i = 0 ; i < poResult.getConjFiltros().size(); i++ ){
            for(int ii = 0 ; ii < poAux2.getConjFiltros().size(); ii++ ){
                JListDatosFiltroConj loAux = new JListDatosFiltroConj();
                loAux.addCondicion(loAux.mclAND,(IListDatosFiltro) ((IListDatosFiltro) poResult.getConjFiltros().get(i)).clone());
                loAux.addCondicion(loAux.mclAND,(IListDatosFiltro) ((IListDatosFiltro) poAux2.getConjFiltros().get(ii)).clone());
                poResult.addCondicion(
                        JListDatosFiltroConj.mclOR,
                        loAux);
            }
        }
        return loResult;
    }

    private boolean isSoloUnCampoOTodoAND(IListDatosFiltro poFiltro){
        boolean lbResult = true;
        lbResult =
                getSoloUnCampo(poFiltro, new JGuiSelectCampoFiltroCampo("", "")).msCampo !=null ||
                isTodoAND(poFiltro);

        return lbResult;

    }
    //devuelve si la condicion lo tiene todo AND
    private boolean isTodoAND(IListDatosFiltro poFiltro) {
        boolean lbResult = true;
        if(JListDatosFiltroConj.class.isAssignableFrom(poFiltro.getClass())){
            JListDatosFiltroConj loFiltro = (JListDatosFiltroConj) poFiltro;
            for(int i = 1; i < loFiltro.getConjUniones().size() && lbResult; i++){
                int lUnion = ((Integer)loFiltro.getConjUniones().get(i)).intValue();
                lbResult = lUnion == JListDatosFiltroConj.mclAND;
            }
        }else{
            lbResult = isTodoAND(((JListDatosFiltroElem)poFiltro).getFiltroConj());
        }

        return lbResult;

    }
    //devuelve el nombre del campo si solo hay un campo implicado en la condicion
    //devuelve null si hay mas de un campo implicado en la condicion
    private JGuiSelectCampoFiltroCampo getSoloUnCampo(IListDatosFiltro poFiltro, JGuiSelectCampoFiltroCampo poResult){
        if(JListDatosFiltroConj.class.isAssignableFrom(poFiltro.getClass())){
            JListDatosFiltroConj loFiltro = (JListDatosFiltroConj) poFiltro;
            for(int i = 0; i < loFiltro.getConjFiltros().size() && poResult!= null; i++){
                IListDatosFiltro loAux = (IListDatosFiltro) loFiltro.getConjFiltros().get(i);
                if(JListDatosFiltroConj.class.isAssignableFrom(loAux.getClass())){
                    getSoloUnCampo((JListDatosFiltroConj) loAux, poResult);
                }else{
                    getSoloUnCampo((JListDatosFiltroElem) loAux, poResult);
                }
            }
        }else{
            JListDatosFiltroElem loElem = (JListDatosFiltroElem) poFiltro;
            if(poResult.msCampo!=null && poResult.msCampo.equals("")){
                poResult.msCampo = loElem.getCampo(0);
                poResult.msTabla = loElem.getTabla();
            }
            if(loElem.isMismoCampo()){
                if(poResult.msCampo !=null &&
                   !poResult.isIgual(loElem.getTabla(), loElem.getCampo(0))
                  ){
                    poResult.msCampo = null;
                }
            }else{
                poResult.msCampo = null;
            }
        }
        return poResult;
    }


}
class JGuiSelectCampoFiltroCampo {
    public String msTabla;
    public String msCampo;
    public JListDatosFiltroConj moFiltro;
    JGuiSelectCampoFiltroCampo(){

    }
    JGuiSelectCampoFiltroCampo(String psTabla, String psCampo){
        msTabla=psTabla;
        msCampo=psCampo;
    }

    public boolean isIgual(JGuiSelectCampoFiltroCampo poCampo){
        return isIgual(poCampo.msTabla, poCampo.msCampo);
    }
    public boolean isIgual(String psTabla, String psCampo){
        return psTabla.equalsIgnoreCase(msTabla) &&
                psCampo.equalsIgnoreCase(msCampo);
    }
}