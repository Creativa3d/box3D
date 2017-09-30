package utilesLex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class JFormatoToken {

//Es el formato del patron
    private String msFormato;
//El nombre del patron
    private String msNombreToken;

//Propiedad que indica si se añade el toekn resultante de que coincida el patron
//a la collecion de tokens resultante
    private boolean mbAddSiCoincide;
    private Pattern moPatron;

//Inicializa el patron
    public void Inicializar(final String lsNombreToken, final String lsFormato, final boolean pbAddSiCoincide) {
        setFormato(lsFormato);
        setNombreToken(lsNombreToken);
        setAddSiCoincide(pbAddSiCoincide);
    }

//Analiza la cadena pasada por parametro

    public JToken oAnalizarCadena(final String lsCadena) {
        JToken loResult = null;
        Matcher loMat = moPatron.matcher(lsCadena);
        loMat.find();
        String lsSubCadena="";
        try{
            lsSubCadena = loMat.group();
        }catch(Exception e){
        }
        if(!lsSubCadena.equals("") &&
            lsSubCadena.equals(lsCadena.substring(0, lsSubCadena.length()))
            ){
            loResult = new JToken(
                    getNombreToken(),
                    lsSubCadena,
                    0,
                    isAddSiCoincide()
                    );
        }
//        JFormatoTokenParam loParam = new JFormatoTokenParam(0, 0, 0, true);
//
//        if (bCumpleFormato(lsCadena,getFormato(), loParam, false)) {
//            loResult = new JToken( getNombreToken(),lsCadena.substring(0, loParam.iCaracter), loParam.lPorcentaje, isAddSiCoincide());
//        }
        return loResult;
    }

    /**
     * @return the msFormato
     */
    public String getFormato() {
        return msFormato;
    }

    /**
     * @param msFormato the msFormato to set
     */
    public void setFormato(String psFormato) {
        msFormato = psFormato;
        moPatron = Pattern.compile(psFormato);
    }

    /**
     * @return the msNombreToken
     */
    public String getNombreToken() {
        return msNombreToken;
    }

    /**
     * @param msNombreToken the msNombreToken to set
     */
    public void setNombreToken(String msNombreToken) {
        this.msNombreToken = msNombreToken;
    }

    /**
     * @return the mbAnadirSiCoincide
     */
    public boolean isAddSiCoincide() {
        return mbAddSiCoincide;
    }

    /**
     * @param mbAnadirSiCoincide the mbAnadirSiCoincide to set
     */
    public void setAddSiCoincide(boolean mbAnadirSiCoincide) {
        this.mbAddSiCoincide = mbAnadirSiCoincide;
    }
}


////simbolos especificos =
////? se hace 0 o una vez
////* se hace 0 - n veces lo anterior
////| se hace lo de la parte izq. o lo de la parte de derecha
////() para agrupar parte del formato
////[] solo se coge un caracter de los que hay dentro
///// lo siguiente pierde su funcionalidad
////^ lo siguiente se contempla como palabra clave(no sensitivo a mayus) (solo admite parentesis delante)
////REALIZACION DE UN AUTOMATA( no se puede pq haria falta una celula de memoria asi que ha currarselo)
//    private boolean bCumpleFormato(
//            final String lsCadena,
//            final String lsFormato,
//            final JFormatoTokenParam poParam, //por refenrecia
//            final boolean bSoloUnaPasada) {
//        int llMarcaInicial;//indica el caracter (o grupo si va entre parentesis) anterior
//        int llMarcaFinal;//indica el caracter que hay despues de la funcion(*,+,...) actual
//        boolean lbMarcaError;//indica que ha habido un error, pero que continua para ver si en el siguiente caracter
//        //es una funcion que suple el error
//        boolean bDesabilitarFunciones;//desabilita la funcion(*,?,..) del siguiente caracter
//        boolean bError;//indica si hay algun error incorregible
//        boolean bFin;//sirve para forzar finalizar normal
//        llMarcaInicial = 0;
//        llMarcaFinal = 0;
//        lbMarcaError = false;
//        bDesabilitarFunciones = false;
//        bError = false;
//        bFin = false;
//        while (poParam.iFormato < lsFormato.length() && !bError && !bFin) {
//            if (!bDesabilitarFunciones) {
//                switch (lsFormato.charAt(poParam.iFormato)) {
//                    case '*': //lo anterior se cumple 0 - n veces
//                        if (!lbMarcaError) {
//                            poParam.bError = bError;
//                            AsteriscooSumaSigue(
//                                    lsCadena,
//                                    lsFormato,
//                                    poParam,
//                                    llMarcaInicial);
//                            bError = poParam.bError;
//                        } else {
//                            poParam.iFormato = poParam.iFormato + 1;
//                            lbMarcaError = false;
//                        }
//                        break;
//                    case '?': //lo anterior se cumple 0 o 1 vez
//                        if (lbMarcaError) {
//                            lbMarcaError = false;
//                        }
//                        poParam.iFormato = poParam.iFormato + 1;
//                        break;
//                    case '+': // lo anterior se cumple 1 - n veces
//                        if (!lbMarcaError) {
//                            poParam.bError = bError;
//                            AsteriscooSumaSigue(
//                                    lsCadena,
//                                    lsFormato,
//                                    poParam,
//                                    llMarcaInicial);
//                            bError = poParam.bError;
//                        } else {
//                            bError = true;
//                        }
//                        break;
//                    case '(': //caracter de apertura de una agrupacion
//                        if (!lbMarcaError) {
//                            llMarcaInicial = poParam.iFormato;
//                            lbMarcaError = !bParenteris(
//                                    lsCadena,
//                                    lsFormato,
//                                    poParam,
//                                    llMarcaInicial);
//                        } else {
//                            bError = true;
//                        }
//                        break;
//                    case ')': //caracter de cierre de la agrupacion(Devuelve que el parentesis si no hay error anterior esta
//                        //cerrado correctamente)
//                        poParam.bEsBienParentesis = true && !lbMarcaError;
//                        bError = true;
//                        break;
//                    case '/': //caracter de anulacion de funcion de siguiente caracter
//                        bDesabilitarFunciones = true;
//                        poParam.iFormato = poParam.iFormato + 1;
//                        break;
//                    case '[': //caracter de apertura de opcion "O"
//                        if (!lbMarcaError) {
//                            llMarcaInicial = poParam.iFormato;
//                            lbMarcaError = !bCorchetes(
//                                    lsCadena,
//                                    lsFormato,
//                                    poParam,
//                                    llMarcaInicial);
//                        } else {
//                            lbMarcaError = true;
//                            bError = true;
//                        }
//                        break;
//                    case '|': //caracter de la parte izq. o la parte derecha
//                        if (lbMarcaError) {
//                            int lAuxFormat = poParam.iFormato;
//                            boolean lbAuxBienParen = poParam.bEsBienParentesis;
//                            poParam.iFormato = 1;
//                            poParam.bEsBienParentesis = true;
//                            lbMarcaError = !bCumpleFormato(
//                                    lsCadena,
//                                    lsFormato.substring(
//                                        poParam.iFormato + 1,
//                                        poParam.iFormato + 1 +
//                                            LongSiguiente(lsCadena, lsFormato, lAuxFormat, poParam.iCaracter)),
//                                    poParam,
//                                    false);
//                            poParam.iFormato = lAuxFormat;
//                            poParam.bEsBienParentesis = lbAuxBienParen;
//                            poParam.iFormato = poParam.iFormato + 1 + LongSiguiente(lsCadena, lsFormato, poParam.iFormato, poParam.iCaracter);
//                        } else {
//                            poParam.iFormato = poParam.iFormato + 1 + LongSiguiente(lsCadena, lsFormato, poParam.iFormato, poParam.iCaracter);
//                        }
//                        break;
//                    case '^': //caracter para las palabras clave indep. de mayus. y minus
//                        String lsMayor = "";
//                        int lIncremento;
//                        llMarcaInicial = poParam.iFormato;
//                        //cogemos la cadena del formato que es indep de mayus-minus
//                        if (lsFormato.charAt(poParam.iFormato+1) == '(') {
//                            lIncremento = lsFormato.indexOf(')', poParam.iFormato);
//                            try {
//                                lsMayor = lsFormato.substring(poParam.iFormato + 2, lIncremento).toUpperCase();
//                            } catch (Exception e) {
//                                lbMarcaError = true;
//                            }
//                            bError = lbMarcaError;
//                        } else {
//                            lIncremento = 2;
//                            lsMayor = String.valueOf(lsFormato.charAt(poParam.iFormato+1)).toUpperCase();
//                        }
//                        //comparamos el formato con la cadena
//                        if (lsMayor.equalsIgnoreCase(lsCadena.substring(poParam.iCaracter,poParam.iCaracter+ lsMayor.length()).toUpperCase())) {
//                            poParam.iCaracter = poParam.iCaracter + lsMayor.length();
//                            poParam.iFormato = poParam.iFormato + lIncremento;
//                        } else {
//                            //si ya habia un error entonces salir
//                            if (lbMarcaError) {
//                                bError = true;
//                            } else {
//                                if (llMarcaFinal != 0) {
//                                    poParam.iFormato = llMarcaFinal;
//                                    llMarcaFinal = 0;
//                                    lbMarcaError = false;
//                                } else {
//                                    poParam.iFormato = poParam.iFormato + lIncremento;
//                                    lbMarcaError = true;
//                                }
//                            }
//                        }
//                        break;
//                    default: //caracteres normales
//                        bDesabilitarFunciones = false;
//                        llMarcaInicial = poParam.iFormato;
//                        if (lsCadena.charAt(poParam.iCaracter) == lsFormato.charAt(poParam.iFormato)) {
//                            poParam.iCaracter = poParam.iCaracter + 1;
//                            poParam.iFormato = poParam.iFormato + 1;
//                        } else {
//                            //si ya habia un error entonces salir
//                            if (lbMarcaError) {
//                                bError = true;
//                            } else {
//                                if (llMarcaFinal != 0) {
//                                    poParam.iFormato = llMarcaFinal;
//                                    llMarcaFinal = 0;
//                                    lbMarcaError = false;
//                                } else {
//                                    poParam.iFormato = poParam.iFormato + 1;
//                                    lbMarcaError = true;
//                                }
//                            }
//                        }
//                }
//            } else {
//                bDesabilitarFunciones = false;
//                llMarcaInicial = poParam.iFormato;
//                if (lsCadena.charAt(poParam.iCaracter) == lsFormato.charAt(poParam.iFormato)) {
//                    poParam.iCaracter = poParam.iCaracter + 1;
//                    poParam.iFormato = poParam.iFormato + 1;
//                } else {
//                    //si ya habia un error entonces salir
//                    if (lbMarcaError) {
//                        bError = true;
//                    } else {
//                        if (llMarcaFinal != 0) {
//                            poParam.iFormato = llMarcaFinal;
//                            llMarcaFinal = 0;
//                            lbMarcaError = false;
//                        } else {
//                            poParam.iFormato = poParam.iFormato + 1;
//                            lbMarcaError = true;
//                        }
//                    }
//                }
//            }
//            bFin = bSoloUnaPasada && !bDesabilitarFunciones;
//        }
////si cumple el formato es que no hay nongun tipo de error al acabar
////y si no se ha forzado el fin si se a completado la long. del formato
////y se ha procesado algun caracter
//        return !bError &&
//               !lbMarcaError &&
//               (lsFormato.length() <= poParam.iFormato || bFin) &&
//               poParam.iCaracter > 0;
//    }
//
////Hace la correspondencia de fomrato con el patron * o +
////        final String lsCadena ,
////        final String lsFormato ,
////        final int lPorcentaje , //por refenrecia
////        final int iFormato , //por refenrecia
////        final int iCaracter , //por refenrecia
////        final int iMarcaInicial ,
////        final boolean bError //por refenrecia
//    private void AsteriscooSumaSigue(
//            final String lsCadena,
//            final String lsFormato,
//            final JFormatoTokenParam poParam,
//            final int iMarcaInicial) {
//        int iCaracAntig; //almacena el ult. caracter valido
//        int iMarcaIniAux;//almacena la marca de inicio del bucle
//        boolean lbAuxBienParam = poParam.bEsBienParentesis;
//        int lFormatoAux = poParam.iFormato;
//        iCaracAntig = poParam.iCaracter;
//        iMarcaIniAux = iMarcaInicial;
//        poParam.iFormato = iMarcaInicial;
//
////si antes ha habido un caracter normal de reconocimiento
//        if (poParam.iFormato > 0) {
//            //mientras se cumple el formato hacerlo
//            poParam.bEsBienParentesis = true;
//            while (bCumpleFormato(
//                    lsCadena,
//                    lsFormato.substring(1, poParam.iFormato - 1),
//                    poParam,
//                    false)) {
//                //alamacenamos el caracter hasta el cual todo va bien
//                iCaracAntig = poParam.iCaracter;
//                poParam.iFormato = iMarcaIniAux;
//                if (poParam.iCaracter > lsCadena.length()) {
//                    break;
//                }
//            }
//        } else {
//            poParam.bError = true;
//        }
////si ha fallado el bcumpleformto restauramos el ult. carac. que ha ida bien
//        poParam.iCaracter = iCaracAntig;
////pasamos el caracter de repeticion
//        poParam.iFormato = lFormatoAux + 1;
////restauramos pararentesis
//        poParam.bEsBienParentesis = lbAuxBienParam;
//    }
//
////Cuando se encuentra un parentesis se llama a esta funcion que resuelve el
////tema de los parentesis
////##ModelId=39E1B6FF02C8
//    private boolean bParenteris(
//            final String lsCadena,
//            final String lsFormato,
//            final JFormatoTokenParam poParam,
//            final int iMarcaInicial) {
//        boolean bBienParentesisAux = poParam.bEsBienParentesis;//variable para que se pase por referencia, despues del proce.
//        //te devuelve si el cierre del parentesis es correcto
//        int iCaracterAux;// para almacenar el 1º caracter valido antes de la abvertura del
//        //parentesis
//        boolean lbResult;
////almacenamos el caracter de la cadena por si lo de los parentesios falla restaurar el caracter
////inicial
//        iCaracterAux = poParam.iCaracter;
////para pasarnos el 1º parentesis
//        poParam.iFormato = poParam.iFormato + 1;
////por defecto mal , si no encuentra otro parentesis mal
//        poParam.bEsBienParentesis = false;
//        if (!bCumpleFormato(
//                lsCadena,
//                lsFormato,
//                poParam,
//                false)) {
//            lbResult = poParam.bEsBienParentesis;
//            //si no han ido bien los parentesis dejamos el caracter donde estaba antes de procesar los
//            //parentesis
//            if (!lbResult) {
//                poParam.iCaracter = iCaracterAux;
//            }
//        } else {
//            //es pq no ha encontrado el parentesis de cierre
//            lbResult = false;
//        }
//
////alamacemos el carac. actual
//        iCaracterAux = poParam.iCaracter;
////pasamos el resto de elementos hasta llegar al parentesis cerrado
//        int lParenCerr;
//        lParenCerr = 1;
//        while (lParenCerr > 0 && poParam.iFormato < lsFormato.length()) {
//            if (lsFormato.charAt(poParam.iFormato) == ')') {
//                lParenCerr = lParenCerr - 1;
//            }
//            if (lsFormato.charAt(poParam.iFormato) == '(') {
//                lParenCerr = lParenCerr + 1;
//            }
//            poParam.iFormato = poParam.iFormato + 1;
//        }
//        poParam.bEsBienParentesis = bBienParentesisAux;
////si existe el corchete de cierre y se ha cumplido algun formato -> true
////si no false
//        lbResult = lbResult && (lParenCerr == 0);
//        return lbResult;
//
//    }
//
////Cuando se encuentra un corchete se llama a esta funcion que resuelve el tema
////de los corchetes, un entre corchetes indica que se debe cumplir una opcion de las que
////hay dentro
//    private boolean bCorchetes(
//            final String lsCadena,
//            final String lsFormato,
//            final JFormatoTokenParam poParam,
//            final int iMarcaInicial) {
//        int iCaracterAux;
//        boolean lbEsBienParamAux = poParam.bEsBienParentesis;
//        boolean lbResult = false;
////nos saltamos el corchete
//        poParam.iFormato = poParam.iFormato + 1;
//        while (lsFormato.charAt(poParam.iFormato) != ']' && poParam.iFormato < lsFormato.length() && !lbResult) {
//            //se va comprovando si algun elemento de dentro del corchete es bueno, si es bueno
//            //la funcion por defecto es true
//            poParam.bEsBienParentesis=true;
//            lbResult = bCumpleFormato(lsCadena, lsFormato, poParam, true);
//        //no se pone pq la funcion anterior si hay error se incrementa en uno
////    iFormato = iFormato + 1
//        }
////alamacemos el carac. actual
//        iCaracterAux = poParam.iCaracter;
////pasamos el resto de elementos hasta llegar al corchete cerrado
////nota no hay problemas con corchetes dentro del corchete pq 1º esta el corhcete
////abierto y se volveria a llamar a este procedimeitno buscadon su
////corchete cerrado y sacandolo
//        int lCorchetesCerr;
//        lCorchetesCerr = 1;
//        while (lCorchetesCerr > 0 && poParam.iFormato <= lsFormato.length()) {
//            if (lsFormato.charAt(poParam.iFormato) == ']') {
//                lCorchetesCerr = lCorchetesCerr - 1;
//            }
//            if (lsFormato.charAt(poParam.iFormato) == '[') {
//                lCorchetesCerr = lCorchetesCerr + 1;
//            }
//            poParam.iFormato = poParam.iFormato + 1;
//        }
////si existe el corchete de cierre y se ha cumplido algun formato -> true
////si no false
//        poParam.bEsBienParentesis=lbEsBienParamAux;
//        return lbResult && (lCorchetesCerr == 0);
//    }
//
////Te indica la long. del siguiente patron, es decir, si no hay parentesis( o cochete) solo
////es un caracter, pero si hay parentesis abra que buscar el parentesis que lo
////cierra(a ese pq puede haber parentesis dentro de los parentesis)
//    private int LongSiguiente(final String lsCadena, final String lsFormato, final int piFormato, final int piCaracter) {
//        int iFormatoAux;
//        //nos pasamos el caracter |
//        JFormatoTokenParam loParam = new JFormatoTokenParam(0, piFormato+1, piCaracter, true);
//        iFormatoAux = loParam.iFormato;
//        bCumpleFormato(lsCadena, lsFormato, loParam, true);
//        return loParam.iFormato - iFormatoAux;
//    }
//
//class JFormatoTokenParam {
//
//    int lPorcentaje = 0;
//    int iFormato = 0;
//    int iCaracter = 0;
//    boolean bEsBienParentesis = true;
//    boolean bError;
//
//    JFormatoTokenParam() {
//    }
//
//    JFormatoTokenParam(int plPorcentaje, int piFormato, int piCaracter, boolean pbEsBienParentesis) {
//        lPorcentaje = plPorcentaje;
//        iFormato = piFormato;
//        iCaracter = piCaracter;
//        bEsBienParentesis = pbEsBienParentesis;
//    }
//}
