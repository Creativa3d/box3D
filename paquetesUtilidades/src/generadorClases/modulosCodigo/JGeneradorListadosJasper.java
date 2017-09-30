/*
* clase generada con metaGenerador
*
* Creado el 29/4/2009
*/

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.JFieldDefs;
import generadorClases.*;

public class JGeneradorListadosJasper implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JProyecto moProyecto;

    public JGeneradorListadosJasper(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual() );;

        lsText.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<jasperReport xmlns=\"http://jasperreports.sourceforge.net/jasperreports\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:schemaLocation=\"http://jasperreports.sourceforge.net/jasperreports http://jasperreports.sourceforge.net/xsd/jasperreport.xsd\" name=\"lstACU\" pageWidth=\"595\" pageHeight=\"842\" columnWidth=\"535\" leftMargin=\"30\" rightMargin=\"30\" topMargin=\"20\" bottomMargin=\"20\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<property name=\"ireport.scriptlethandling\" value=\"0\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<property name=\"ireport.encoding\" value=\"UTF-8\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<import value=\"net.sf.jasperreports.engine.*\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<import value=\"java.util.*\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<import value=\"net.sf.jasperreports.engine.data.*\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<parameter name=\"pathWeb\" class=\"java.lang.String\" isForPrompting=\"false\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<defaultValueExpression><![CDATA[\"\"]]></defaultValueExpression>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</parameter>");lsText.append(JUtiles.msRetornoCarro);

        //Anadimos los parametros
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("	<parameter name=\"" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "\" class=\"java.lang.String\"/>");lsText.append(JUtiles.msRetornoCarro);
        }

        //Anadimos los campos
        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("	<field name=\"" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "\" class=\"java.lang.String\"/>");lsText.append(JUtiles.msRetornoCarro);
        }

        //Abrir grupos
        if(loCampos.malCamposPrincipales()!=null){
            int cont = -40;
            for(int i = loCampos.malCamposPrincipales().length; i < loCampos.count(); i++ ){
                if(i == loCampos.count()-1) {
                    cont += 41;
                    lsText.append("	<group name=\"" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "\">");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		<groupExpression><![CDATA[$F{" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "}]]></groupExpression>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		<groupHeader>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("			<band height=\"17\" isSplitAllowed=\"false\">");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("				<textField>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("					<reportElement mode=\"Opaque\" x=\"" + cont + "\" y=\"1\" width=\"40\" height=\"15\" forecolor=\"#FFFFFF\" backcolor=\"#999999\"/>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("					<textElement/>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("					<textFieldExpression class=\"java.lang.String\"><![CDATA[$F{"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +"}]]></textFieldExpression>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("				</textField>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("			</band>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		</groupHeader>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		<groupFooter>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("			<band/>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		</groupFooter>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("	</group>");lsText.append(JUtiles.msRetornoCarro);
                } else {
                    lsText.append("	<group name=\"" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "\">");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		<groupExpression><![CDATA[$F{" + moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) + "}]]></groupExpression>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		<groupHeader>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("			<band height=\"0\" isSplitAllowed=\"false\">");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("			</band>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		</groupHeader>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		<groupFooter>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("			<band/>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("		</groupFooter>");lsText.append(JUtiles.msRetornoCarro);
                    lsText.append("	</group>");lsText.append(JUtiles.msRetornoCarro);
                }
            }
        }

        lsText.append("	<background>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<band/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</background>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<title>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<band height=\"78\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			<image scaleImage=\"RetainShape\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<reportElement key=\"LOGO\" x=\"1\" y=\"1\" width=\"49\" height=\"46\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<imageExpression class=\"java.lang.String\"><![CDATA[$P{pathWeb} + \"/listados/escudo.jpg\"]]></imageExpression>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</image>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			<staticText>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<reportElement key=\"TITU1\" x=\"55\" y=\"8\" width=\"329\" height=\"13\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<textElement verticalAlignment=\"Middle\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<font size=\"8\" isBold=\"true\" pdfFontName=\"Helvetica-Bold\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</textElement>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<text><![CDATA[titulo]]></text>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</staticText>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			<staticText>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<reportElement key=\"TITU2\" x=\"55\" y=\"21\" width=\"329\" height=\"13\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<textElement verticalAlignment=\"Middle\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<font size=\"6\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</textElement>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<text><![CDATA[Departamento del Ciclo Hídrico]]></text>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</staticText>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			<staticText>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<reportElement key=\"TITU3\" x=\"0\" y=\"50\" width=\"84\" height=\"27\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<textElement textAlignment=\"Left\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<font size=\"18\" isBold=\"false\" pdfFontName=\"Helvetica\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</textElement>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<text><![CDATA[Listado de ]]></text>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</staticText>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			<textField>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<reportElement x=\"87\" y=\"50\" width=\"446\" height=\"27\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<textElement>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<font size=\"18\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</textElement>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<textFieldExpression class=\"java.lang.String\"><![CDATA[$P{NOMTABLA}]]></textFieldExpression>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</textField>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		</band>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</title>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<pageHeader>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<band/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</pageHeader>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<columnHeader>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<band/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</columnHeader>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<detail>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<band height=\"13\">");lsText.append(JUtiles.msRetornoCarro);

        //Anadimos el detalle
        int cont = -40;
        for(int i = 0; i < loCampos.count(); i++ ){
            cont += 41;
            lsText.append("			<textField isStretchWithOverflow=\"true\" isBlankWhenNull=\"true\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("				<reportElement key=\"T"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +"\" x=\""+cont+"\" y=\"2\" width=\"40\" height=\"10\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("				<box leftPadding=\"2\" rightPadding=\"2\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("				<textElement textAlignment=\"Left\" verticalAlignment=\"Top\">");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("					<font size=\"6\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("				</textElement>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("				<textFieldExpression class=\"java.lang.String\"><![CDATA[$F{"+ moUtiles.msSustituirRaros(loCampos.get(i).getNombre()) +"}]]></textFieldExpression>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("			</textField>");lsText.append(JUtiles.msRetornoCarro);
        }

        lsText.append("		</band>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</detail>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<columnFooter>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<band/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</columnFooter>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<pageFooter>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<band height=\"15\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			<staticText>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<reportElement key=\"SNUM_PAG\" x=\"480\" y=\"0\" width=\"24\" height=\"14\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<textElement verticalAlignment=\"Bottom\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<font fontName=\"Tahoma\" size=\"8\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</textElement>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<text><![CDATA[Pag:]]></text>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</staticText>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			<textField isBlankWhenNull=\"false\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<reportElement key=\"NUM_PAG\" x=\"503\" y=\"0\" width=\"30\" height=\"14\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<textElement verticalAlignment=\"Bottom\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<font size=\"8\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</textElement>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<textFieldExpression class=\"java.lang.Integer\"><![CDATA[$V{PAGE_NUMBER}]]></textFieldExpression>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</textField>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		</band>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</pageFooter>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<summary>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<band/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</summary>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("</jasperReport>");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }

    public String getRutaRelativa() {
        return "listados";
    }

    public String getNombre() {
        return "JLST"+ moUtiles.msSustituirRaros(moConex.getTablaActual())+".jrxml";
    }

    public boolean isGeneral() {
        return false;
    }

    public String getNombreModulo() {
        return "JLSTListados";
    }

    public JModuloProyectoParametros getParametros() {
        return new JModuloProyectoParametros();
    }
}
