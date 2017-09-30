package ListDatos;

import java.io.Serializable;
import java.util.Comparator;
import utiles.JDateEdu;

/**
 * Objeto ayuda a ordenar un JListDatos, sirve para comparar 2 conjunto de campos 
 */
public final class JOrdenacion implements Comparator<IFilaDatos>, Serializable {
    private static final long serialVersionUID = 33333311L;
    /**Constante igual*/
    public static final int mclIgual = JListDatos.mclTIgual;
    /**Constante mayor*/
    public static final int mclMayor = JListDatos.mclTMayor;
    /**Constante menor*/
    public static final int mclMenor = JListDatos.mclTMenor;

    private int[] malCampos;
    private int[] malTipos;
    private boolean mbAscendente = true;
    private boolean mbIgnorarCASE = true;

    /**
     * Constructor
     * @param palCampos Array de posiciones de los campos
     * @param palTipos Array de tipos de los campos
     * @param pbAscendente Si es ascendente
     * @param pbIgnorarCASE Independ. de mayus./minus.
     */
    public JOrdenacion(final int[] palCampos, final int[] palTipos, final boolean pbAscendente, final boolean pbIgnorarCASE) {
        this(palCampos, palTipos);
        mbAscendente = pbAscendente;
        mbIgnorarCASE = pbIgnorarCASE;
    }

    /**
     * Constructor
     * @param palCampos array de posiciones de los campos
     * @param palTipos array de tipos de los campos
     */
    public JOrdenacion(final int[] palCampos, final int[] palTipos) {
        super();
        malCampos = palCampos;
        malTipos = palTipos;
    }

    /**
     * compara el oibjeto 1 con el 2
     * @return resultado de la comparacion mclIgual, ...
     * @param o1 objeto 1
     * @param o2 objeto 2
     */
    public int compare(final IFilaDatos o1, final IFilaDatos o2) {
        return mlcompare(o1, o2, malCampos, malTipos, mbAscendente, mbIgnorarCASE);
    }

    private static boolean mbBoolean(final String psValor) {
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
     * @param pbAscendente si es ascendente o descendente
     * @return resultado de la comparacion, mclIgual, mclMenor, mclMayor
     */
    public static int mlcompare(final IFilaDatos o1, final IFilaDatos o2, final int[] palCampos, final int[] palTipos, final boolean pbAscendente) {
        return mlcompare(o1, o2, palCampos, palTipos, pbAscendente, true);
    }

    /**
     * compara 2 fila de datos, pero solo la lista de campos
     * @param o1 objeto 1
     * @param o2 objeto 2
     * @param palCampos lista de campos
     * @param palTipos tipos de los campos de la lista anterior
     * @param pbAscendente si es ascendente o descendente
     * @param pbIgnoreCASE si ignoramos mayusculas/minus.
     * @return resultado de la comparacion, mclIgual, mclMenor, mclMayor
     */
    public static int mlcompare(final IFilaDatos o1, final IFilaDatos o2, final int[] palCampos, final int[] palTipos, final boolean pbAscendente, final boolean pbIgnoreCASE) {
        IFilaDatos lo1 = o1;
        IFilaDatos lo2 = o2;
        int lCompara = mclIgual;
        //si la comparacion es mayor o menor parar la comparacion
        //si es igual continuar 
        for (int i = 0; i < palCampos.length && lCompara == mclIgual; i++) {

            String s1 = lo1.msCampo(palCampos[i]);
            String s2 = lo2.msCampo(palCampos[i]);

            if ((palTipos[i] == JListDatos.mclTipoNumero) ||
                    (palTipos[i] == JListDatos.mclTipoNumeroDoble) ||
                    (palTipos[i] == JListDatos.mclTipoMoneda3Decimales) ||
                    (palTipos[i] == JListDatos.mclTipoMoneda) ||
                    (palTipos[i] == JListDatos.mclTipoPorcentual3Decimales) ||
                    (palTipos[i] == JListDatos.mclTipoPorcentual)) {

                double l1 = 0;
                boolean lbNulo1 = false;
                double l2 = 0;
                boolean lbNulo2 = false;

                //si alguno no es numerico se controla
                try {
                    l1 = Double.valueOf(s1).doubleValue();
                } catch (Exception ex) {
                    lbNulo1 = true;
                }
                try {
                    l2 = Double.valueOf(s2).doubleValue();
                } catch (Exception ex) {
                    lbNulo2 = true;
                }

                //se realizan las comparaciones numericas
                if (lbNulo1 || lbNulo2) {

                    if (lbNulo1 && lbNulo2) {
                        lCompara = mclIgual;
                    } else {
                        if (lbNulo1) {
                            lCompara = mclMenor;
                        } else {
                            lCompara = mclMayor;
                        }
                    }
                } else {

                    if (l1 > l2) {
                        lCompara = mclMayor;
                    } else {
                        if (l1 < l2) {
                            lCompara = mclMenor;
                        } else {
                            lCompara = mclIgual;
                        }
                    }

                }

            } else if (palTipos[i] == JListDatos.mclTipoFecha) {

                boolean lbNulo1 = !(JDateEdu.isDate(s1));
                boolean lbNulo2 = !(JDateEdu.isDate(s2));

                if (lbNulo1 || lbNulo2) {

                    if (lbNulo1 && lbNulo2) {
                        lCompara = mclIgual;
                    } else {
                        if (lbNulo1) {
                            lCompara = mclMenor;
                        } else {
                            lCompara = mclMayor;
                        }
                    }
                } else {
                    lCompara = JDateEdu.compareTo(JDateEdu.CDate(s1), JDateEdu.CDate(s2));
                }
            } else if (palTipos[i] == JListDatos.mclTipoBoolean) {
                boolean lbS1 = mbBoolean(s1);
                boolean lbS2 = mbBoolean(s2);
                if (lbS1 == lbS2) {
                    lCompara = mclIgual;
                } else {
                    if (lbS1) {
                        lCompara = mclMayor;
                    } else {
                        lCompara = mclMenor;
                    }
                }

            } else {
                if (pbIgnoreCASE) {
                    lCompara = s1.toLowerCase().compareTo(s2.toLowerCase());
                } else {
                    lCompara = s1.compareTo(s2);
                }
            }
            //al usar ordenaciones de otros metodos comprobamos que devuelve lo que tiene que devolver
            if (lCompara > 0) {
                lCompara = mclMayor;
            } else {
                if (lCompara < 0) {
                    lCompara = mclMenor;
                } else {
                    lCompara = mclIgual;
                }
            }
//ya se hace en el for
//        if (lCompara != mclIgual) {
//            break;
//        }
        }
        if (!pbAscendente) {
            switch (lCompara) {
                case mclMayor:
                    lCompara = mclMenor;
                    break;
                case mclMenor:
                    lCompara = mclMayor;
                    break;
                default:
            }
        }
        lo1 = null;
        lo2 = null;
        return lCompara;

    }

}


