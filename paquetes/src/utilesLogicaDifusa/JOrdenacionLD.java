package utilesLogicaDifusa;

import ListDatos.IFilaDatos;
import ListDatos.JListDatos;
import utiles.JDateEdu;
import java.io.Serializable;
import utiles.JCadenas;

/**
 * Objeto ayuda a ordenar un JListDatos, sirve para comparar 2 conjunto de campos 
 */
public abstract class JOrdenacionLD implements Serializable {

    private static final long serialVersionUID = 33333311L;
    private int mlCampo;
    private int mlTipo;
    private boolean mbIgnorarCASE = true;
    private boolean mbPorPalabras = true;

    /**
     * Datos
     * @param palCampos Array de posiciones de los campos
     * @param palTipos Array de tipos de los campos
     * @param pbIgnorarCASE Independ. de mayus./minus.
     */
    public void setDatos(final int plCampo, final int plTipo, final boolean pbPorPalabras, final boolean pbIgnorarCASE) {
        mlCampo = plCampo;
        mlTipo = plTipo;
        mbPorPalabras = pbPorPalabras;
        mbIgnorarCASE = pbIgnorarCASE;
    }


    /**
     * compara el oibjeto 1 con el 2
     * @return resultado de la comparacion mclIgual, ...
     * @param o1 objeto 1
     * @param o2 objeto 2
     */
    public double compare(final Object o1, final Object o2) {
        return mlcompare((IFilaDatos) o1, (IFilaDatos) o2, mlCampo, mlTipo, mbIgnorarCASE);
    }

    private boolean mbBoolean(final String psValor) {
        boolean lbValor = false;
        if (psValor.compareTo(JListDatos.mcsTrue) == 0) {
            lbValor = true;
        } else {
            if (psValor.compareTo(Boolean.TRUE.toString()) == 0) {
                lbValor = true;
            }
        }
        return lbValor;
    }

    /**
     * compara 2 fila de datos, pero solo la lista de campos
     * @param o1 objeto 1
     * @param o2 objeto 2
     * @param palCampos lista de campos
     * @param palTipos tipos de los campos de la lista anterior
     * @return resultado de la comparación, mclIgual, mclMenor, mclMayor
     */
    public double mlcompare(final IFilaDatos o1, final IFilaDatos o2, final int plCampo, final int plTipo) {
        return mlcompare(o1, o2, plCampo, plTipo, true);
    }

    /**
     * compara 2 fila de datos, pero solo la lista de campos
     * @param o1 objeto 1
     * @param o2 objeto 2
     * @param palCampos lista de campos
     * @param palTipos tipos de los campos de la lista anterior
     * @param pbIgnoreCASE si ignoramos mayusculas/minus.
     * @return resultado de la comparación, mclIgual, mclMenor, mclMayor
     */
    public double mlcompare(final IFilaDatos o1, final IFilaDatos o2, final int plCampo, final int plTipo, final boolean pbIgnoreCASE) {
        IFilaDatos lo1 = o1;
        IFilaDatos lo2 = o2;
        double lCompara = 0;

        String s1 = lo1.msCampo(plCampo);
        String s2 = lo2.msCampo(plCampo);


        double l1 = 0;
        boolean lbNulo1 = false;
        double l2 = 0;
        boolean lbNulo2 = false;

        //fechas y numeros se tratan como numeros
        if ((plTipo == JListDatos.mclTipoNumero) ||
                (plTipo == JListDatos.mclTipoNumeroDoble)||
                (plTipo == JListDatos.mclTipoMoneda3Decimales)||
                (plTipo == JListDatos.mclTipoMoneda)||
                (plTipo == JListDatos.mclTipoPorcentual3Decimales)||
                (plTipo == JListDatos.mclTipoPorcentual) ||
                (plTipo == JListDatos.mclTipoFecha)) {

            if ((plTipo == JListDatos.mclTipoNumero) ||
                    (plTipo == JListDatos.mclTipoNumeroDoble) ||
                    (plTipo == JListDatos.mclTipoMoneda3Decimales) ||
                    (plTipo == JListDatos.mclTipoMoneda) ||
                    (plTipo == JListDatos.mclTipoPorcentual3Decimales) ||
                    (plTipo == JListDatos.mclTipoPorcentual)) {
                //si alguno no es numerico se controla
                try {
                    l1 = Double.valueOf(s1).doubleValue();
                } catch (NumberFormatException ex) {
                    lbNulo1 = true;
                }
                try {
                    l2 = Double.valueOf(s2).doubleValue();
                } catch (NumberFormatException ex) {
                    lbNulo2 = true;
                }
            } else if (plTipo == JListDatos.mclTipoFecha) {

                //si alguno no es numerico se controla
                try {
                    l1 = JDateEdu.CDate(s1).getFechaEnNumero();
                } catch (NumberFormatException ex) {
                    lbNulo1 = true;
                }
                try {
                    l2 = JDateEdu.CDate(s2).getFechaEnNumero();
                } catch (NumberFormatException ex) {
                    lbNulo2 = true;
                }
            }
            //se realizan las comparaciones numericas
            if (lbNulo1 || lbNulo2) {

                if (lbNulo1 && lbNulo2) {
                    lCompara = 100;
                } else {
                    lCompara = 0;
                }
            } else {
                //si son de distinto signo incrementamos la difernecia antes de hacerlos absolutos
                if (!((l1 <= 0 && l2 <= 0) || (l1 >= 0 && l2 >= 0))) {
                    l1 = Math.abs(l1) + Math.abs(l2);
                    l2 = Math.abs(l2);
                } else {
                    l1 = Math.abs(l1);
                    l2 = Math.abs(l2);
                }
                if (l1 < l2 || l1 > l2) {
                    lCompara = l2 / l1;
                    if(lCompara>1){
                        lCompara = 100 / lCompara;
                    } else {
                        lCompara = 100 * lCompara;
                    }
                } else {
                    lCompara = 100;
                }

            }

        } else if (plTipo == JListDatos.mclTipoBoolean) {
            boolean lbS1 = mbBoolean(s1);
            boolean lbS2 = mbBoolean(s2);
            if (lbS1 == lbS2) {
                lCompara = 100;
            } else {
                lCompara = 0;
            }

        } else {
            if(mbPorPalabras){
                String[] lasPalabras1 = null;
                String[] lasPalabras2 = null;
                if (pbIgnoreCASE) {
                    lasPalabras1 = JCadenas.getPalabras(s1.toLowerCase());
                    lasPalabras2 = JCadenas.getPalabras(s2.toLowerCase());
                } else {
                    lasPalabras1 = JCadenas.getPalabras(s1);
                    lasPalabras2 = JCadenas.getPalabras(s2);
                }
                double ldPorcentajeTotal = 0;
                for(int i = 0 ; i < lasPalabras2.length; i++ ){
                    double ldMax = 0;
                    for(int ii = 0 ; ii < lasPalabras1.length && ldMax < 99.99999; ii++ ){
                        double ldAux = getPorcentajeCadena(lasPalabras1[ii], lasPalabras2[i]);
                        if(ldAux>ldMax){
                            ldMax = ldAux;
                        }
                    }
                    ldPorcentajeTotal += ldMax;
                }
                if(lasPalabras2.length>0){
                    lCompara = ldPorcentajeTotal / lasPalabras2.length;
                } else {
                    lCompara = 0;
                }
            }else{
                if (pbIgnoreCASE) {
                    lCompara = getPorcentajeCadena(s1.toLowerCase(), s2.toLowerCase());
                } else {
                    lCompara = getPorcentajeCadena(s1, s2);
                }
            }
        }

        lo1 = null;
        lo2 = null;
        return lCompara;

    }


    public abstract double getPorcentajeCadena(String s1, String s2);





    /**Devuelve si el objeto es igual, no implementado*/
    public boolean equals(final Object obj) {
        throw new java.lang.InternalError("Method equals() not yet implemented.");
    }


}


