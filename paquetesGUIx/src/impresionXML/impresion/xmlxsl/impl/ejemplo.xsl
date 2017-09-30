<?xml version="1.0" encoding="ISO-8859-1"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform">
	<xsl:template match="/">
		<!-- Creamos una variable que controle si existen datos de un determinado tipo CAUCIONALES / NO CAUCIONALES -->
		<xsl:variable name="HayCAUCIONALES">
			<xsl:for-each select="xml/FICHERO/MODELOS/MODELO[AUTOLIQUIDACION/DATOS_LIQUIDACION/SUSPENSIVO = 'S']">
				<xsl:value-of select="/."/>
			</xsl:for-each>
		</xsl:variable>
		<!--<xsl:text>VALOR CAUCIONALES=</xsl:text><xsl:value-of select="$HayCAUCIONALES"/>-->
		<xsl:variable name="NoHayCAUCIONALES">
			<xsl:for-each select="xml/FICHERO/MODELOS/MODELO[AUTOLIQUIDACION/DATOS_LIQUIDACION/SUSPENSIVO = 'N']">
				<xsl:value-of select="/."/>
			</xsl:for-each>
		</xsl:variable>
		<!--<xsl:text>VALOR NO CAUCIONALES=</xsl:text><xsl:value-of select="$NoHayCAUCIONALES"/>-->
		<!-- -->
		<!--	<xsl:template match="/"> -->
		<html>
			<!--desde-->
			<table border="2" width="95%" cellpadding="1" cellspacing="1" style="border-collapse: collapse" bordercolor="#111111">
				<tr>
					<td bgcolor="#CCCCCC" colspan="4" ALIGN="CENTER">
						<b>
							<font style="font-family: arial;">DATOS GENERALES</font>
						</b>
					</td>
				</tr>
				<tr>
					<td>
						<b>
							<font style="font-family: arial;">Ref. envío:</font>
						</b>
					</td>
					<td>
						<font style="font-family: arial;font-size:12;">
							<xsl:value-of select="xml/FICHERO/IDENTIFICACION/ID_COMUNICACION"/>
						</font>
					</td>
					<td>
						<b>
							<font style="font-family: arial;">F. Presentación:</font>
						</b>
					</td>
					<td>
						<font style="font-family: arial;font-size:12;">
							<xsl:value-of select="xml/FICHERO/MODELOS/MODELO/HECHO_IMPONIBLE/FECHA_PRESENTACION"/>
						</font>
					</td>
				</tr>
				<tr>
					<td bgcolor="#CCCCCC" colspan="4" ALIGN="LEFT">
						<b>
							<font style="font-family: arial;">Presentador</font>
						</b>
					</td>
				</tr>
				<tr>
					<td>
						<b>
							<font style="font-family: arial;">NIF:</font>
						</b>
					</td>
					<td>
						<font style="font-family: arial;font-size:12;">
							<xsl:value-of select="xml/FICHERO/MODELOS/MODELO/INTERVINIENTES/SUJETO_PRESENTADOR/NIF"/>
						</font>
					</td>
					<td>
						<b>
							<font style="font-family: arial;">Nombre:</font>
						</b>
					</td>
					<td>
						<font style="font-family: arial;font-size:12;">
							<xsl:value-of select="xml/FICHERO/MODELOS/MODELO/INTERVINIENTES/SUJETO_PRESENTADOR/NOMBRE_COMPLETO"/>
						</font>
					</td>
				</tr>
			</table>
			<br/>
			<xsl:if test="$HayCAUCIONALES!=''">
				<table border="2" width="95%" cellpadding="1" cellspacing="1" style="border-collapse: collapse" bordercolor="#111111">
					<tr>
						<td bgcolor="#CCCCCC" colspan="5" ALIGN="CENTER">
							<b>
								<font style="font-family: arial;">CAUCIONALES</font>
							</b>
						</td>
					</tr>
					<tr>
						<td>
							<b>
								<font style="font-family: arial;">RUE:</font>
							</b>
						</td>
						<td>
							<b>
								<font style="font-family: arial;">Nº Autoliq.:</font>
							</b>
						</td>
						<td>
							<b>
								<font style="font-family: arial;">Cif/Nif S.Pasivo:</font>
							</b>
						</td>
						<td>
							<b>
								<font style="font-family: arial;">F. Devengo:</font>
							</b>
						</td>
						<td>
							<b>
								<font style="font-family: arial;">Importe:</font>
							</b>
						</td>
					</tr>
					<xsl:for-each select="xml/FICHERO/MODELOS/MODELO">
						<xsl:sort select="HECHO_IMPONIBLE/NUMERO_RUE"/>
						<xsl:if test="AUTOLIQUIDACION/DATOS_LIQUIDACION/SUSPENSIVO = 'S' and RESULTADO/RETURNCODE > '-1'">
							<tr>
								<xsl:choose>
									<xsl:when test="RESULTADO/RETURNCODE = '-1' ">
										<!--<td colspan="2"><font style="font-family: arial;font-size:13;">ERRORES</font></td>-->
									</xsl:when>
									<xsl:otherwise>
										<td>
											<font style="font-family: arial;font-size:12;">
												<xsl:value-of select="HECHO_IMPONIBLE/NUMERO_RUE"/>
											</font>
										</td>
										<td>
											<font style="font-family: arial;font-size:12;">
												<xsl:value-of select="HECHO_IMPONIBLE/NUMERO_PRESENTACION"/>
											</font>
										</td>
									</xsl:otherwise>
								</xsl:choose>
								<td>
									<font style="font-family: arial;font-size:12;">
										<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/NIF"/>
									</font>
								</td>
								<td>
									<font style="font-family: arial;font-size:12;">
										<xsl:value-of select="HECHO_IMPONIBLE/FECHA_DEVENGO"/>
									</font>
								</td>
								<td>
									<font style="font-family: arial;font-size:12;">
										<xsl:value-of select="AUTOLIQUIDACION/CALCULO/IMPORTE_INGRESAR"/>
									</font>
								</td>
							</tr>
						</xsl:if>
					</xsl:for-each>
				</table>
			</xsl:if>
			<p/>
			<p/>
			<p/>
			<!--		<div style="page-break-before:always" /> -->
			<!--OTRO-->
			<xsl:if test="$NoHayCAUCIONALES!=''">
				<table border="2" width="95%" cellpadding="1" cellspacing="1" style="border-collapse: collapse" bordercolor="#111111">
					<tr>
						<td bgcolor="#CCCCCC" colspan="5" ALIGN="CENTER">
							<b>
								<font style="font-family: arial;">NO CAUCIONALES</font>
							</b>
						</td>
					</tr>
					<tr>
						<td>
							<b>
								<font style="font-family: arial;">RUE:</font>
							</b>
						</td>
						<td>
							<b>
								<font style="font-family: arial;">Nº Autoliq.:</font>
							</b>
						</td>
						<td>
							<b>
								<font style="font-family: arial;">Cif/Nif S.Pasivo:</font>
							</b>
						</td>
						<td>
							<b>
								<font style="font-family: arial;">F. Devengo:</font>
							</b>
						</td>
						<td>
							<b>
								<font style="font-family: arial;">Importe:</font>
							</b>
						</td>
					</tr>
					<xsl:for-each select="xml/FICHERO/MODELOS/MODELO">
						<xsl:sort select="HECHO_IMPONIBLE/NUMERO_RUE"/>
						<xsl:if test="AUTOLIQUIDACION/DATOS_LIQUIDACION/SUSPENSIVO = 'N' and RESULTADO/RETURNCODE >'-1' ">
							<tr>
								<xsl:choose>
									<xsl:when test="RESULTADO/RETURNCODE = '-1' ">
										<!--<td colspan="2"><font style="font-family: arial;font-size:13;">ERRORES</font></td>-->
									</xsl:when>
									<xsl:otherwise>
										<td>
											<font style="font-family: arial;font-size:12;">
												<xsl:value-of select="HECHO_IMPONIBLE/NUMERO_RUE"/>
											</font>
										</td>
										<td>
											<font style="font-family: arial;font-size:12;">
												<xsl:value-of select="HECHO_IMPONIBLE/NUMERO_PRESENTACION"/>
											</font>
										</td>
									</xsl:otherwise>
								</xsl:choose>
								<td>
									<font style="font-family: arial;font-size:12;">
										<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/NIF"/>
									</font>
								</td>
								<td>
									<font style="font-family: arial;font-size:12;">
										<xsl:value-of select="HECHO_IMPONIBLE/FECHA_DEVENGO"/>
									</font>
								</td>
								<td>
									<font style="font-family: arial;font-size:12;">
										<xsl:value-of select="AUTOLIQUIDACION/CALCULO/IMPORTE_INGRESAR"/>
									</font>
								</td>
							</tr>
						</xsl:if>
					</xsl:for-each>
				</table>
			</xsl:if>
			<p/>
			<p/>
			<p/>
			<div style="page-break-before:always"/>
			<!--aki-->
			<!--<xsl:for-each select="MODELOS/MODELO[RESULTADO/RETURNCODE != '-1']">-->
			<xsl:for-each select="xml/FICHERO/MODELOS/MODELO">
				<!--		<xsl:sort select="HECHO_IMPONIBLE/NUMERO_RUE"/> -->
				<xsl:if test="ID_MODELO != '1' and ID_MODELO != '01' and ID_MODELO != '001' and ID_MODELO != '0001'">
					<div style="page-break-before:always"/>
				</xsl:if>
				<table border="0" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber1">
					<tr>
						<td width="40%">
							<p align="left">
								<img src="https://atenea.ha.gva.es:1443/aduana/urls/imagenes/logo.bmp" alt="logo"/>
							</p>
						</td>
						<td width="60%">
							<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber2">
								<tr>
									<td width="75%">
										<h2 style="background:" align="center">
											<font face="arial">
											<!-- errores-->
											<xsl:choose>
												<xsl:when test="RESULTADO/RETURNCODE = '-1'"><span style="font-size: 14pt">COMUNICACIÓN DE ERRORES</span></xsl:when>
												<xsl:otherwise><span style="font-size: 14pt">JUSTIFICANTE DE PRESENTACIÓN DEL MODELO</span></xsl:otherwise>
											</xsl:choose>
											<!--span style="font-size: 14pt">JUSTIFICANTE DE PRESENTACIÓN DEL MODELO</span-->
											</font>
										</h2>
									</td>
									<td width="25%">
										<p align="center">
											<font face="arial" style="font-size: 20pt">620</font>
										</p>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
				<p/>
				<table border="0" width="100%">
					<tr>
						<td width="100%">
							<b>
								<font style="font-family: arial"/>
							</b>
						</td>
					</tr>
				</table>
				<p/>
				<table border="0" width="100%">
					<tr>
						<td width="100%">
							<b>
								<font style="font-family: arial">IDENTIFICACION DEL SUJETO PASIVO:</font>
							</b>
						</td>
					</tr>
				</table>
				<p/>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Cif/Nif:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/NIF"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Nombre o razón social:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/NOMBRE"/>
								<font/>
								<font style="font-family: arial"> </font>
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/PRIMER_APELLIDO"/>
								<font/>
								<font style="font-family: arial"> </font>
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/SEGUNDO_APELLIDO"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Dirección:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/TIPO_VIA"/>
								<font/>
								<font style="font-family: arial"> </font>
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/NOMBRE_VIA"/>
								<font/>
								<font style="font-family: arial"> </font>
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/NUMERO"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;"/>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/CODIGO_POSTAL"/>
								<font/>
								<font style="font-family: arial"> </font>
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/MUNICIPIO/NOMBRE"/>
								<font/>
								<font style="font-family: arial"> </font>
								<xsl:value-of select="INTERVINIENTES/SUJETO_PASIVO/PROVINCIA/NOMBRE"/>
							</font>
						</td>
					</tr>
				</table>
				<p/>
				<table border="0" width="100%">
					<tr>
						<td width="100%">
							<b>
								<font style="font-family: arial"/>
							</b>
						</td>
					</tr>
				</table>
				<p/>
				<!-- errores -->
				<xsl:choose>
					<xsl:when test="RESULTADO/RETURNCODE = '-1'">
					
					<table border="0" width="100%">
					<tr>
						<td width="100%">
							<b>
								<font style="font-family: arial">IDENTIFICACION DEL DOCUMENTO RECHAZADO:</font>
							</b>
						</td>
					</tr>
				</table>
				<p/>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Fecha Devengo:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="HECHO_IMPONIBLE/FECHA_DEVENGO"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Matricula/Bastidor:</font>
							</b>
						</td>
						<td width="70%">
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'V' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/VEHICULOS/MATRICULA"/>
									/
								<xsl:value-of select="CARACT_TECNICAS/VEHICULOS/FICHA_TECNICA/BASTIDOR"/>
								</font>
							</xsl:if>
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'M' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/MOTOCICLETAS/MATRICULA"/>
								</font>
							</xsl:if>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Marca:</font>
							</b>
						</td>
						<td width="70%">
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'V' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/VEHICULOS/MARCA"/>
								</font>
							</xsl:if>
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'E' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/EMBARCACIONES/MARCA"/>
								</font>
							</xsl:if>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Modelo:</font>
							</b>
						</td>
						<td width="70%">
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'V' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/VEHICULOS/MODELO"/>
								</font>
							</xsl:if>
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'E' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/EMBARCACIONES/MODELO"/>
								</font>
							</xsl:if>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Referencia envío:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="xml/FICHERO/IDENTIFICACION/ID_COMUNICACION"/> - <xsl:value-of select="ID_MODELO"/> / <xsl:value-of select="INTERVINIENTES/SUJETO_PRESENTADOR/ID_GESTOR"/>
							</font>
						</td>
					</tr>
				</table>
				<p/>
				<table border="0" width="100%">
					<tr>
						<td width="100%">
							<b>
								<font style="font-family: arial"/>
							</b>
						</td>
					</tr>
				</table>					
					
					</xsl:when>
					<xsl:otherwise>
					
				<table border="0" width="100%">
					<tr>
						<td width="100%">
							<b>
								<font style="font-family: arial">IDENTIFICACION DEL DOCUMENTO:</font>
							</b>
						</td>
					</tr>
				</table>
				<p/>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Rue:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="HECHO_IMPONIBLE/NUMERO_RUE"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Nº de Autoliquidación:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="HECHO_IMPONIBLE/NUMERO_PRESENTACION"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Concepto Liquidación:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="HECHO_IMPONIBLE/CONCEPTO"/>-<xsl:value-of select="HECHO_IMPONIBLE/DES_CONCEPTO"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Fecha Presentación:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="HECHO_IMPONIBLE/FECHA_PRESENTACION"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Fecha Devengo:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="HECHO_IMPONIBLE/FECHA_DEVENGO"/>
							</font>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Matricula/Bastidor:</font>
							</b>
						</td>
						<td width="70%">
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'V' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/VEHICULOS/MATRICULA"/>
									/
								<xsl:value-of select="CARACT_TECNICAS/VEHICULOS/FICHA_TECNICA/BASTIDOR"/>
								</font>
							</xsl:if>
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'M' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/MOTOCICLETAS/MATRICULA"/>
								</font>
							</xsl:if>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Marca:</font>
							</b>
						</td>
						<td width="70%">
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'V' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/VEHICULOS/MARCA"/>
								</font>
							</xsl:if>
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'E' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/EMBARCACIONES/MARCA"/>
								</font>
							</xsl:if>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Modelo:</font>
							</b>
						</td>
						<td width="70%">
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'V' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/VEHICULOS/MODELO"/>
								</font>
							</xsl:if>
							<xsl:if test="CARACT_TECNICAS/TIPO_VEHICULO = 'E' ">
								<font style="font-family: arial;">
									<xsl:value-of select="CARACT_TECNICAS/EMBARCACIONES/MODELO"/>
								</font>
							</xsl:if>
						</td>
					</tr>
				</table>
				<table border="0" width="100%">
					<tr>
						<td width="30%">
							<b>
								<font style="font-family: arial;">Referencia envío:</font>
							</b>
						</td>
						<td width="70%">
							<font style="font-family: arial;">
								<xsl:value-of select="/xml/FICHERO/IDENTIFICACION/ID_COMUNICACION"/> - <xsl:value-of select="ID_MODELO"/> / <xsl:value-of select="INTERVINIENTES/SUJETO_PRESENTADOR/ID_GESTOR"/>
							</font>
						</td>
					</tr>
				</table>
				<p/>
				<table border="0" width="100%">
					<tr>
						<td width="100%">
							<b>
								<font style="font-family: arial"/>
							</b>
						</td>
					</tr>
				</table>
				</xsl:otherwise>
				</xsl:choose>
				<p/>
				<xsl:if test="(PAGO/TIPO_CONFIRMACION = 'AD') and (RESULTADO/RETURNCODE != '-1')">
					<table border="0" width="100%">
						<tr>
							<td width="100%">
								<b>
									<font style="font-family: arial">IDENTIFICACION DEL PAGO:</font>
								</b>
							</td>
						</tr>
					</table>
					<p/>
					<table border="0" width="100%">
						<tr>
							<td width="30%">
								<b>
									<font style="font-family: arial;">NRC:</font>
								</b>
							</td>
							<td width="70%">
								<font style="font-family: arial;">
									<xsl:value-of select="PAGO/DATOS_PAGO/NRC"/>
								</font>
							</td>
						</tr>
					</table>
					<p/>
					<table border="0" width="100%">
						<tr>
							<td width="100%">
								<b>
									<font style="font-family: arial"/>
								</b>
							</td>
						</tr>
					</table>
					<p/>
				</xsl:if>
				<xsl:choose>
					<xsl:when test="RESULTADO/RETURNCODE = '-1' ">
						<b>
							<font style="font-family: arial">RELACION DE ERRORES:</font>
						</b>
						<p/>
						<xsl:for-each select="RESULTADO/ERRORES">
							<b>
								<font style="font-family: arial" color="#FF0000">
									<xsl:value-of select="CODERROR"/>
								</font>
							</b> - <font style="font-family: arial" color="#FF0000">
								<xsl:value-of select="TEXTERROR"/>
							</font>
							<br/>
						</xsl:for-each>
					</xsl:when>
					<xsl:otherwise>
						<table border="0" width="100%">
							<tr>
								<td width="100%">
									<b>
										<font style="font-family: arial">DILIGENCIA:</font>
									</b>
								</td>
							</tr>
						</table>
						<p/>
						<table border="0" width="100%" style="font-family: arial; font-size: 12pt">
							<tr>
								<td width="100%" style="text-align:justify">
									<xsl:if test="AUTOLIQUIDACION/DATOS_LIQUIDACION/EXENCIONES/EXENTO = 'S' ">
										<font style="font-family: arial">El presente documento se devuelve a la persona interesada por haber alegado que el acto o contrato que contiene está exento / no sujeto / prescrito al impuesto. Ha presentado copia para la comprobación de la exención / no sujeción / prescripción alegada, o práctica de la liquidación o liquidaciones que, en su caso, procedan.</font>
									</xsl:if>
									<xsl:if test="AUTOLIQUIDACION/DATOS_LIQUIDACION/SUSPENSIVO = 'S' ">
										<font style="font-family: arial">El presente documento se devuelve a la persona interesada por haber alegado que el acto o contrato que contiene está provisionalmente exento. Ha presentado copia para la comprobación de la exención provisional alegada, o práctica de la liquidación o liquidaciones que, en su caso, procedan.</font>
									</xsl:if>
									<xsl:if test="AUTOLIQUIDACION/DATOS_LIQUIDACION/SUSPENSIVO = 'N' and AUTOLIQUIDACION/DATOS_LIQUIDACION/EXENCIONES/EXENTO = 'N'">
										<font style="font-family: arial">Por autoliquidación del Impuesto correspondiente al presente documento, ha sido ingresada la cantidad de </font>
										<xsl:value-of select="PAGO/DATOS_PAGO/INGRESO_IMPORTE"/>
										<font style="font-family: arial"> Euros por adeudo en cuenta, con fecha </font>
										<xsl:value-of select="PAGO/DATOS_PAGO/FECHA_INGRESO"/>
										<font style="font-family: arial">. El interesado ha presentado copia del documento para comprobación de la autoliquidación y, en su caso, rectificación y práctica de la liquidación o liquidaciones complementarias que procedan.</font>
									</xsl:if>
								</td>
							</tr>
						</table>
						<p/>
						<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber3" height="38">
							<tr>
								<td width="100%" height="19">
									<p align="center">
										<b>
											<font face="Arial">CÓDIGO ELECTRÓNICO DE TRANSMISIÓN</font>
										</b>
									</p>
								</td>
							</tr>
							<tr>
								<td width="100%" height="18">
									<p align="center">
										<font style="font-family: arial">
											<xsl:value-of select="HECHO_IMPONIBLE/CET"/>
										</font>
									</p>
								</td>
							</tr>
						</table>
						<table border="0" width="100%">
							<tr>
								<td width="100%">
									<b>
										<font style="font-family: arial"/>
									</b>
								</td>
							</tr>
						</table>
						<p/>						
						<table border="1" cellpadding="0" cellspacing="0" style="border-collapse: collapse" bordercolor="#111111" width="100%" id="AutoNumber3" height="38">
							<tr>
								<td width="100%" height="19">
									<p align="center">
										<b>
											<font face="Arial">NÚMERO DE CONTROL</font>
										</b>
									</p>
								</td>
							</tr>
							<tr>
								<td width="100%" height="18">
									<p align="center">
										<font style="font-family: arial">
											<xsl:value-of select="HECHO_IMPONIBLE/NUMERO_CONTROL"/>
										</font>
									</p>
								</td>
							</tr>
						</table>
					</xsl:otherwise>
				</xsl:choose>
				<p/>
			</xsl:for-each>
		</html>
	</xsl:template>
</xsl:stylesheet>
