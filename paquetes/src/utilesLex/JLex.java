package utilesLex;

import utiles.IListaElementos;
import utiles.JListaElementos;

public class JLex {
//http://www.altavista.com/cgi-bin/query?q=lex+yacc&pg=q&qe
//http://www.uman.com/lexyacc.shtml
//http://www.monmouth.com/~wstreett/lex-yacc/lex-yacc.html
//http://epaperpress.com/y_man.html
//http://www.gy.com/spec/ityy.htm

//Es donde se almacenan los patrones
    private IListaElementos moCollecPatrones = new JListaElementos();



    /**
     * Añadimos los patrones
     * A partir de 2 token cumplidos
     * la cadena mas larga es el token preferente
     * a igualdad de long. de cadena el primero
     * q se añadio es el preferente
     */
    public void addPatron(final String sFormato, final String sNombreToken) {
        JFormatoToken loFormToken = new JFormatoToken();
        loFormToken.Inicializar(sNombreToken, sFormato, true);
        moCollecPatrones.add(loFormToken);
    }
    /**
     * Añadimos los patrones
     * A partir de 2 token cumplidos
     * la cadena mas larga es el token preferente
     * a igualdad de long. de cadena el primero
     * q se añadio es el preferente
     */
    public void addPatron(final String sFormato, final String sNombreToken, final boolean bAnadirSiCoincide) {
        JFormatoToken loFormToken = new JFormatoToken();
        loFormToken.Inicializar(sNombreToken, sFormato, bAnadirSiCoincide);
        moCollecPatrones.add(loFormToken);
    }

    /**
     * Es el procedimiento que llama el usu. para procesar la cadena de entrada
     * A partir de 2 token cumplidos
     * la cadena mas larga es el token preferente
     * a igualdad de long. de cadena el primero
     * q se añadio es el preferente
     */
    public IListaElementos procesarCadena(String lsCadena) throws ExceptionLex {
        String lsTemp;
        JFormatoToken loFormToken;
        JToken loToken;
        IListaElementos loCollecParcial;
        IListaElementos loCollecResul = new JListaElementos();
        //nos sirve para saber si en una pasada a todos los formato de token no ha cambiado la cadena
        //si es asi -> no se reconoce el formato
        loCollecParcial = new JListaElementos();
        //para que la primera vez el bucle pase bien
        loCollecParcial.add(new JToken());
        //mientras no se ha reconocido algun formato y no se ha procesado toda la cadena
        while (!lsCadena.equals("") && loCollecParcial.size() > 0) {
            //Pasamos todos los formatos de token y los validos los pasamos a la
            //coleccion resultado parcial
            loCollecParcial = new JListaElementos();
            for (int i = 0; i < moCollecPatrones.size(); i++) {
                loFormToken = (JFormatoToken) moCollecPatrones.get(i);
                //procesamos el formato token y devuelve un token que cumple el formato
                //junto con el porcentaje de formato fijo(es un token mejor si es mas alto este porcentaje)
                loToken = loFormToken.oAnalizarCadena(lsCadena);
                if (loToken != null) {
                    //añadimos el token a la colecion parcial
                    loCollecParcial.add(loToken);
                }
            }
            //vemos cual es el token que mejor se adapta
            loToken = oTokenMejor(loCollecParcial);
            //si el token es valido le quitamos de la cadena su trozo procesado
            //y se lo añadimos a la colecion de resultados
            if (loToken != null) {
                lsCadena = lsCadena.substring(loToken.getCadena().length());//right
                if (loToken.isAddSiCoincide()) {
                    loCollecResul.add(loToken);
                }
            }
        }
        if (!lsCadena.equals("")) {
            throw new ExceptionLex(loCollecResul);
        }
        //si la cadena es = "" formato procesado
        return loCollecResul;
    }

//Procedimeinto que te da el token mejor dentro de una collection de tokens
    private JToken oTokenMejor(final IListaElementos loCollecParcial) {
        JToken loToken;
        JToken loTokenResult = null;
        if (loCollecParcial.size() > 0) {
            loTokenResult = (JToken) loCollecParcial.get(0);
            for (int i = 1; i < loCollecParcial.size(); i++) {
                loToken = (JToken) loCollecParcial.get(i);
                //te coge el token de mayor porcentaje de fijo, si son de igual porcentaje fijo
                //te coge el que posea la cadena mas larga si son iguales las cadenas
                //te coje el 1º que ha encontrado
                if (loToken.getPorcentajeFormatoFijo() > loTokenResult.getPorcentajeFormatoFijo()) {
                    loTokenResult = loToken;
                } else {
                    if (loToken.getPorcentajeFormatoFijo() == loTokenResult.getPorcentajeFormatoFijo() &&
                        loToken.getCadena().length() > loTokenResult.getCadena().length()) {
                        loTokenResult = loToken;
                    }
                }
            }
        }
        return loTokenResult;
    }
}