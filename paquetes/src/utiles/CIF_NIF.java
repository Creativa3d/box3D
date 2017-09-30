/*
 * Created on 15 -feb-2005
 */
package utiles;

/**
 * <p>
 * Clase con una serie de metodos estaticos para trabajar con NIFs y CIFs.
 * </p>
 *
 * <p>
 * Permite determinar si un identificador es un NIF o un CIF (
 * <code>isNIF()</code>,<code>isCIF()</code>), si un NIF o un CIF son
 * correctos (<code>isNIFOK()</code>,<code>isCIFOK()</code>), verificar
 * un identificador independientemente de si es un NIF o un CIF (
 * <code>verifyCIF_NIF()</code>) y, dado un NIF, averiguar la letra de
 * control que le corresponderia (<code>obtainNIFLetter()</code>).
 * </p>
 *
 * <p>
 * La informacion proviene de http://www.q3.nu/trucomania/truco.cgi?337&esp para
 * los CIFs y de http://www.q3.nu/trucomania/truco.cgi?9&esp para NIF. Tambien
 * hay euristica e informacion pasada por Albert Garreta Serra.
 * </p>
 *
 * <p>
 * La informacion del NIF se complementa con el "REAL DECRETO 338/1990" del
 * 09/03/1990. <br>
 * La informacion del CIF se complementa con el "REAL DECRETO 2423/1975", la
 * "Orden de 3 de julio de 1998" y la "Orden de 9 de enero de 1989".
 * </p>
 *
 * <p>
 * Aunque ni el decreto ni las ordenes lo mencionan, parece ser que los CIFs de
 * Corporaciones Locals (P) y de Organismos Autonomos (Q) tienen como digito de
 * control una letra. <br>
 * Asi, solo se admiten como CIFs validos con letra final aquellos que empiezan
 * per 'P' o per 'Q'.
 * </p>
 *
 * <p>
 * Tambien es curioso que si bien en el decreto 2423/1975 aparece la letra 'N' en
 * la lista de "claves de formas juridicas y clases de entidades" para
 * "Entidades no residentes" y que este codigo se mantiene en la orden de
 * 03/07/98 que modifica el decreto, desaparece en la ordren de 09/01/99, que
 * tambien lo modifica. Por si acaso, la he dejado.
 * </p>
 *
 * <p>
 * El decreto 2423/1975 detalla mas elementos de la estructura del CIF que no
 * nos interesan (codigo de provincia, calculo del numero de empresa secuencial,
 * etc.), pero no especifica como se calcula el digito (o letra) de control.
 * Asi, pues, me he basado en los diversos algoritmos que corren por Internet y,
 * especialmente, en el de Albert Garreta Serra.
 * </p>
 *
 * <p>
 * A todos los efectos, un NIE se trata como un NIF.
 * </p>
 *
 * <p>
 * Hay que tener en cuenta que los NIFs y CIFs que tratamos no admiten caracters
 * de separacion.
 * </p>
 *
 * @author Francesc Roses Albiol
 */
public class CIF_NIF {
	/*
	 * LETRAS VaLIDAS DE CIF (Orden 03-07-98. BOE Numero 162 08/07/1998)
	 * =============================================================
	 *
	 * A - Sociedades Anonimas
	 * B - Sociedades de responsabilidad limitada
	 * C - Sociedades colectivas
	 * D - Sociedades comanditarias
	 * E - Comunidades de bienes
	 * F - Sociedades cooperativas
	 * G - Asociaciones y otros tipos no definidos
         * J -
         * U -
         * V -
	 * H - Comunidades de propietarios en regimen de propiedad horizontal
	 * N - Entidades no residentes
         * W -
	 * P - Corporaciones locales
	 * Q - Organismos autonomos estatales o no, y asimilatdos,
	 *     y congregaciones e instituciones religiosas
         * R -
	 * S - organs de la administracion del Estado y
	 *     Comunidades autonomas
	 *
	 *
	 * LETRAS VaLIDAS DE NIF (Real Decreto 338/1990)
	 * =============================================
	 * K - Espanoles menores de 14 anos y extranjeros menores de 18
	 * L - Espanoles mayores de 14 anos residiendo en el
	 *     extranjero y que se trasladan por tiempo inferior
	 *     a 6 meses a Espana
	 * M - Extranjeros mayores de 18 anos
	 *
	 * LETRES VaLIDAS DE NIE
	 * ======================
	 * X - Extranjeros, que en vez de D.N.I. tienen N.I.E.
         * Y -
         * Z - Aun no se usa
	 */
	/** Caracteres admisibles como letra de control de CIFs. */
	public static final String	CHARS_CIF	= "ABCDEFGHNPQSJUVWR";
	/** Caracteres admisibles como letra de control de NIFs. */
	public static final String	CHARS_NIF	= "TRWAGMYFPDXBNJZSQVHLCKET";
	/** Caracteres admisibles como letra inicial de NIFs. */
	public static final String	INIT_NIF	= "ZYXKLM";

	/**
	 * <p>
	 * Indica si una tira tiene la estructura prevista para
	 * un NIF.
	 * </p>
	 *
	 * <p>
	 * En ningun caso indica si el NIF es correcto o no.
	 * Para saberlo, hay que usar <code>isNIFOK()</code>.
	 * </p>
	 *
	 * <p>
	 * Entiende que los Extranjeros son NIFs. Tambien trata
	 * la posibilidad de que se reduzca en uno (1) el numero
	 * de digitos para los extranjeros. Suele pasar porque
	 * los campos de entrada para NIF no los tienen en cuenta.
	 * Lo que acostumbran a hacer es eliminar el primer cero.
	 * Asi, si empieza por 'X' y la longitud es 9,
	 * consideramos que es un NIF.
	 * </p>
	 *
	 * @param nif
	 * @return boolean
	 */
	public static boolean isNIF(String nif) {
		if (nif == null) return false;
		if (nif.length() < 9) return false;

		nif = nif.toUpperCase();
		char DC1 = nif.charAt(0);
		char DC2 = nif.charAt(nif.length() - 1);

		// Possible extranjero o menor o residente en el extranjero...
		if (nif.length() == 10) {
			if ((INIT_NIF.indexOf(DC1) != -1) && (CHARS_NIF.indexOf(DC2) != -1)) {
				return true;
			} else
				return false;
		}
		if (nif.length() == 9) {
			// Extranjero o menor o residente en el extranjero sin cero de relleno
			if (((INIT_NIF.indexOf(DC1) != -1)) && (CHARS_NIF.indexOf(DC2) != -1))
				return true;
			// NIF normal tipo DNI
			else
				return (Character.isDigit(DC1) && CHARS_NIF.indexOf(DC2) != -1);
		}

		// No tiene ni 9 ni 10 caracteres
		return false;
	} // isNIF()

	/**
	 * <p>
	 * Indica si una tira tiene la estructura prevista para un
	 * CIF.
	 * </p>
	 *
	 * <p>
	 * En ningun caso indica si el CIF es correcto o no.
	 * Para saberlo, hay que usar <code>isCIFOK()</code>.
	 * </p>
	 *
	 * <p>
	 * Seguimos el siguiente criterio:
	 * <ul>
	 * <li>La longitud tiene que ser 9 y</li>
	 * <li>el primer caracter tiene que ser alfabetico y
	 * formar parte de la tira <code>CHARS_CIF</code>.
	 * </ul>
	 * </p>
	 *
	 * @param cif
	 * @return boolean
	 */
	public static boolean isCIF(String cif) {
		if (cif == null) return false;

		if (cif.length() != 9) return false;
		return (CHARS_CIF.indexOf(cif.substring(0, 1).toUpperCase()) != -1);
	} // isCIF()

        public static boolean isNIE(String nif){
            return (nif.startsWith("X")  || nif.startsWith("Y")
                                || nif.startsWith("Z"));
        }
	/**
	 * <p>
	 * Devuelve <code>true</code> si el NIF pasado
	 * como parametro es correcto.
	 * </p>
	 *
	 * @param nif - String
	 * @return boolean
	 */
	public static boolean isNIFOK(String nif) {
		if (nif == null) return false;

		if (!isNIF(nif)) return false;

		nif = nif.toUpperCase();

		boolean estrangers = (nif.startsWith("X") || nif.startsWith("K")
				|| nif.startsWith("L") || nif.startsWith("M") || nif.startsWith("Y")
                                || nif.startsWith("Z"));
		if (estrangers) {
                        String peso = "";
                        if (nif.startsWith("Y"))
                            peso = "1";
                        else {
                            if (nif.startsWith("Z"))
                                peso = "2";
                            else
                                peso = "0";
                        }
			nif = nif.substring(1);
			// A veces, en el caso de extranjeros, se dejan un
			// cero de relleno al principio... Asi, el NIF
			// 'X02542762C' (de 10 caracteres) puede aparecer como
			// 'X2542762C' (de 9). En este caso hay que anadir el '0'.
			if (nif.length() == 8) nif = peso + nif;
		}

		// Tiene que tener longitud 9
		if (nif.length() != 9) return false;
		// Tiene que acabar con una letra
		if (!Character.isLetter(nif.charAt(8))) return false;
		// Tiene que ser una letra de la lista CHARS_NIF
		if (CHARS_NIF.indexOf(nif.charAt(8)) == -1) return false;

		// Solo se admiten digitos
		try {
			Integer.valueOf(nif.substring(0, 8)).intValue();
		} catch (NumberFormatException e) {
			return false;
		}

		// Validamos el rango para NIFs solo si no son extranjeros
		if (!estrangers) {
			int inif = Integer.valueOf(nif.substring(0, 8)).intValue();
			if (inif > 99999999) { return false; }
		}

		// Calculamos la letra y comparamos
		int lPos = (Integer.valueOf(nif.substring(0, 8)).intValue() % 23);
		return (nif.charAt(8) == CHARS_NIF.charAt(lPos));
	} // isNIFOK()

	/**
	 * <p>
	 * Devuelve <code>true</code> si el CIF que pasamos
	 * es correcto.
	 * </p>
	 *
	 * <p>
	 * Hay que tener presente que los CIFs de extranjeros
	 * se verifican como NIF y los de ayuntamientos, como CIF.
	 * </p>
	 *
	 * @param cif
	 * @return boolean
	 */
	public static boolean isCIFOK(String cif) {
		if (cif == null) return false;

		// Tiene que tener 9 caracteres
		if (cif.length() != 9) return false;

		String DC1 = cif.substring(0, 1).toUpperCase();
		String DC2 = cif.substring(8).toUpperCase();
		String MID = cif.substring(1, 8);

		// El primer caracter indica el tipo de sociedad y
		// ha de ser uno de los previstos.
		if (CHARS_CIF.indexOf(DC1) == -1) return false;

		// Si el primer caracter es 'P' o 'Q', el ultimo
		// debe ser un caracter
		if (DC1.equals("P") || DC1.equals("Q")) {
			if (!Character.isLetter(DC2.charAt(0))) return false;
		}

		// Sumamos los digitos pares
		int suma = Integer.valueOf(MID.substring(1, 2)).intValue()
				+ Integer.valueOf(MID.substring(3, 4)).intValue()
				+ Integer.valueOf(MID.substring(5, 6)).intValue();

		// Calculos sobre los impares y suma
		for (int i = 0; i < 4; i++) {
			int c = Integer.valueOf(MID.substring(i * 2, (i * 2) + 1)).intValue() * 2;
			suma += (c % 10 + c / 10);
		}

		// Obtenemos el digito de control
		int control = 10 - (suma % 10);

//		if (DC1.equals("P") || DC1.equals("Q")) { return (cif.charAt(8) == (control + 64)); }
//		if (control == 10) control = 0;
//
//		return (DC2.equals(control + ""));

                if (Character.isDigit(DC2.charAt(0))){
                    if (control == 10) control = 0;
                    return (DC2.equals(String.valueOf(control)));
                }
                else {
                    return (DC2.charAt(0) == control + 64);
                }
	} // isCIFOK()

	/**
	 * <p>
	 * Verifica cualquier CIF o NIF. Decide si es CIF o NIF
	 * y aplica el algoritmo pertinente.
	 * </p>
	 *
	 * @param cn
	 *          String: CIF o NIF a verificar.
	 * @return boolean
	 */
	public static boolean verifyCIF_NIF(String cn) {
		if (cn == null) return false;

		if (isNIF(cn)) return isNIFOK(cn);
		if (isCIF(cn)) return isCIFOK(cn);
		return false;
	} // verifyCIF_NIF()

	/**
	 * <p>
	 * Calcula y devuelve la letra de control correspondiente
	 * a las cifras del NIF pasado como parametro.
	 * </p>
	 *
	 * <p>
	 * Si la longitud de la tira es incorrecta o bien, la
	 * tira no representa un entero, devuelve <code>null</code>.
	 * </p>
	 *
	 * <p>
	 * Podemos pasar un NIF que empiece por una letra valida.
	 * Si la letra no es valida, devuelve <code>null</code>.
	 * </p>
	 *
	 * @param nif
	 * @return String: la letra del NIF o <code>null</code>.
	 *
	 * @see CIF_NIF#obtainNIFLetter(int)
	 */
	public static String obtainNIFLetter(String nif) {
		if (nif == null) return null;

		char firstChar = nif.toUpperCase().charAt(0);
		if (Character.isLetter(firstChar)) {
			if (INIT_NIF.indexOf(firstChar) != -1) {
                                String peso = "";
                                if (nif.startsWith("Y"))
                                    peso = "1";
                                else {
                                    if (nif.startsWith("Z"))
                                        peso = "2";
                                    else
                                        peso = "0";
                                }
				nif = nif.substring(1);
				if (nif.length() == 7) // Falta cero inicial
					nif = peso + nif;
			} else {
				return null;
			}
		}
		if (nif.length() != 8) return null;
		try {
			return obtainNIFLetter(Integer.valueOf(nif).intValue());
		} catch (Throwable e) {
			return null;
		}
	} // obtainNIFLetter(String)

	/**
	 * <p>
	 * Calcula y devuelve la letra de control correspondiente
	 * a las cifras del NIF pasado como parametro.
	 * </p>
	 *
	 * @param nif
	 * @return String
	 *
	 * @see CIF_NIF#obtainNIFLetter(String)
	 */
	public static String obtainNIFLetter(int nif) {
		int lPos = (nif % 23);
		return CHARS_NIF.substring(lPos, lPos + 1);
	} // obtainNIFLetter(int)
    public static void main(String[] args) {
        verifyCIF_NIF("B83498246");
    }
}
