package utilesLex;

public class JToken {
//El el resultado final de una deteccion de formato valido

//Indica el nombre del token que servira para el analisis sintactico
    private String msNombreToken;
//Indica el tro de la cadena de entrada que le corresponde
    private String msCadena;
//Indica el nivel de letras fijas que ha cogido
    private int mlPorcentajeFormatoFijo;
//Te dice si se añade a la lista de tokens resultante
    private boolean mbAddSiCoincide;

    public JToken() {
    }
    public JToken(final String sNombreToken, final String sCadena) {
        inicializar(sNombreToken, sCadena, 0, true);
    }
    public JToken(final String sNombreToken, final String sCadena, final int lPorcentajeFormatoFijo, final boolean bAnadirSiCoincide) {
        inicializar(sNombreToken, sCadena, lPorcentajeFormatoFijo, bAnadirSiCoincide);
    }
    public void inicializar(final String sNombreToken, final String sCadena, final int lPorcentajeFormatoFijo, final boolean pbAddSiCoincide) {
        msNombreToken = sNombreToken;
        msCadena = sCadena;
        mlPorcentajeFormatoFijo = lPorcentajeFormatoFijo;
        mbAddSiCoincide = pbAddSiCoincide;
    }

//Propiedad que devuelkve el nombre del token
    public String getNombreToken() {
        return msNombreToken;
    }

//Propiedad que devuelve el trozo de cadena que corresponde a este token
    public String getCadena() {
        return msCadena;
    }

//devuelve el porcentaje de formato fijo que tiene esta cadena
    public int getPorcentajeFormatoFijo() {
        return mlPorcentajeFormatoFijo;
    }

//Devuelve si se debe añadir a los tokens resultantes
    public boolean isAddSiCoincide() {
        return mbAddSiCoincide;
    }
}