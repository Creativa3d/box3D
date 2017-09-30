/*
* clase generada con metaGenerador
*
* Creado el 6/7/2011
*/

package generadorClases.modulosCodigo;

import ListDatos.estructuraBD.JFieldDefs;
import ListDatos.estructuraBD.JRelacionesDef;
import ListDatos.estructuraBD.JTableDef;
import generadorClases.*;

public class JGeneradorFormFlex implements IModuloProyecto {
    private JConexionGeneradorClass moConex;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private JProyecto moProyecto;

    public JGeneradorFormFlex(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moConex = poProyec.getConex();
        moProyecto = poProyec;
    }

    @Override
    public String getCodigo() {
        StringBuilder lsText = new StringBuilder(100);
        JTableDef loTabla;
        JFieldDefs loCampos;

        loTabla = moConex.getTablaBD(moConex.getTablaActual());
        loCampos = moConex.getCamposBD(moConex.getTablaActual());

        lsText.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("<mx:Form xmlns:fx=\"http://ns.adobe.com/mxml/2009\" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		 xmlns:s=\"library://ns.adobe.com/flex/spark\" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		 xmlns:mx=\"library://ns.adobe.com/flex/mx\" width=\"704\" height=\"423\" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		 chromeColor=\"#168DBF\" paddingLeft=\"0\" paddingRight=\"0\" paddingTop=\"0\" paddingBottom=\"0\" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		 xmlns:supportClasses=\"com.esri.ags.skins.supportClasses.*\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		 creationComplete=\"inicializar()\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		 implements=\"IReceptorDatos, IMensaje, ICubrir\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		 styleName=\"formularios.css\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<fx:Script>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<![CDATA[");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import APaquetesMapa.ALocalizarEnMapa;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                  ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.collections.ArrayCollection;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.controls.Alert;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.controls.dataGridClasses.DataGridColumn;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.core.Application;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.core.FlexGlobals;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.events.IndexChangedEvent;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.events.ListEvent;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.managers.PopUpManager;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.rpc.events.ResultEvent;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import mx.utils.ObjectProxy;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import org.osmf.utils.URL;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			import spark.events.IndexChangeEvent;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Variables locales");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public var moCtrlForms: AcontrolFormularios;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var moServer: servidorDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var codigo : String; //Codigo externo, recogido por el gis");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var posX:int;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var posY:int;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var tengoFoco:Boolean;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public var moParam:paramLocalizacion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//			private var numPestanasCompletadas:int; //Numero de pestañas con datos rellenados");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			[Bindable] private var arrayPestanas:ArrayCollection; //Array de pestañas");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			[Bindable] private var datosCombo:ArrayCollection; //Array con los datos del combo");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var arrayColumnasGrid:ArrayCollection; //Array para las columnas de los grid");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var moCaptions:ArrayCollection;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Posiciones Pestañas");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private const lPosiGENERAL:int = 0;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private const lPosiDOCUMENTOS:int = 1;");lsText.append(JUtiles.msRetornoCarro);

        int num = 0;
        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("			private const lPosi"+lsNomTabla+":int = "+new Integer(num+2).toString()+
                        ";");lsText.append(JUtiles.msRetornoCarro);
                num++;
            }
        }

        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Numero de pestañas");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private const numPestanas:int = " +
                new Integer(num + 2).toString() + "; //Numero de pestañas");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var datosGENERAL:ArrayCollection;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var datosDOCUMENTOS:ArrayCollection;");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("			private var datos"+lsNomTabla+":ArrayCollection;");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private var minimizado:Boolean;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public var moPadre:IWidgets;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function establecerParametros():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                         moParam = formularios.establecerParametros(true,moPadre,codigo,formularios.mcsC_"+loTabla.getNombre().toString()+",moPadre.getConfigData().proxyUrl,moParam.moRecol);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                 }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function inicializar():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                         minimizado = false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				//Rellenamos el array de pestañas y establecemos label");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				rellenarArrayPestanas();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				establecerLabelPestanas();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				//Inicializamos el motor de datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				if(!moServer) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					moServer = new servidorDatos(FlexGlobals.topLevelApplication.urlBase,IReceptorDatos(this));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				//Añadimos los eventos al mapa");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				FlexGlobals.topLevelApplication.addEventListener(MouseEvent.MOUSE_MOVE,movimientoMapa);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				//Inicializamos variables combo");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				datosCombo = new ArrayCollection();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("//				numPestanasCompletadas = 0;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				//Recuperamos todos los datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				for(var i:int = 0;i<numPestanas;i++) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					recuperarDatos(i);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				//Recuperamos las columnas de los grid");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				for(i = 0;i<numPestanas;i++) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					recuperarColGrid(i);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function maximizarObjeto():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                         if(minimizado) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                                 efectos.maximizarObjeto(this);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                                 minimizado = false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                         }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                 }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function clickLeft():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				if (cmb.selectedIndex > 0) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					cmb.selectedIndex--;	");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					selecionarMenu();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}	");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function clickRight():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				cmb.selectedIndex++;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				selecionarMenu();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Rellenamos el array de las pestañas con los captions");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function rellenarArrayPestanas():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				arrayPestanas = new ArrayCollection();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				arrayPestanas.addItem(formularios.mcsT_GENERAL);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				arrayPestanas.addItem(formularios.mcsT_DOCUMENTOS);");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("				arrayPestanas.addItem(formularios.mcsT_"+lsNomTabla+");");lsText.append(JUtiles.msRetornoCarro);
            }
        }

        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Establecer label pestañas");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function establecerLabelPestanas():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				nc0.label = arrayPestanas[lPosiGENERAL];");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				nc1.label = arrayPestanas[lPosiDOCUMENTOS];");lsText.append(JUtiles.msRetornoCarro);

        num = 0;
        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("				nc"+new Integer(num+2).toString()+".label = arrayPestanas[lPosi"+lsNomTabla+"];");lsText.append(JUtiles.msRetornoCarro);
                num++;
            }
        }

        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("			//Recuperamos las columnas de los grid");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function recuperarColGrid(valor:int):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				var tabla:String = \"\";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				switch(valor) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case lPosiGENERAL:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case lPosiDOCUMENTOS:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						tabla = formularios.mcsDOCUMENTOS;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("					case lPosi"+lsNomTabla+":");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						tabla = formularios.mcs"+lsNomTabla+";");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
            }
        }

        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				if(valor != lPosiGENERAL) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					//RECUPERAR DATOS");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					moServer.inicializar(formularios.mcsC_COLUMNAS_GRID);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					moServer.addCondicion(moServer.mcsTAND,\"TABLA\",tabla,moServer.mcsIGUAL);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					moServer.recuperarDatos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("			//Recuperar datos por pestañas");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function recuperarDatos(valor:int):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				switch(valor) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case lPosiGENERAL:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						//GENERAL");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						moServer.inicializar(formularios.mcs"+loTabla.getNombre()+");");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						moServer.addCondicion(moServer.mcsTAND,\"COD\",codigo.toString(),moServer.mcsIGUAL);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						moServer.recuperarDatos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case lPosiDOCUMENTOS:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						//DOCUMENTOS");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						moServer.inicializar(formularios.mcsDOCUMENTOS);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						moServer.addCondicion(moServer.mcsTAND,\"CODTABLA\",codigo.toString(),moServer.mcsIGUAL);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						moServer.addCondicion(moServer.mcsTAND,\"NOMBRETABLA\",\""+loTabla.getNombre()+"\",moServer.mcsIGUAL);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						moServer.recuperarDatos();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("					case lPosi"+lsNomTabla+":");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						//"+lsNomTabla);lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						moServer.inicializar(formularios.mcs"+lsNomTabla+");");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						moServer.addCondicion(moServer.mcsTAND,\"COD_"+loTabla.getNombre()+"\",codigo.toString(),moServer.mcsIGUAL);");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						moServer.recuperarDatos();");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
            }
        }

        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Busca index en combo");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function existeIndex(i:String, datos:ArrayCollection):Boolean {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				for each (var item:String in datos) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					if(item == i) return(true);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				return(false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("			//Añadir las columnas a los grid");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function addColsGrids(poDatos:ArrayCollection): void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				if(!arrayColumnasGrid) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					arrayColumnasGrid = new ArrayCollection();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				arrayColumnasGrid.addItem(poDatos);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Buscar Col en el array de columnas");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function getColTabla(psNomTabla:String):ArrayCollection {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				var valor:ArrayCollection = null;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				for each (var item:ArrayCollection in arrayColumnasGrid) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					if(item[0].TABLA == psNomTabla) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						valor = item;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				return valor;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function hayDatos(pbHay:Boolean):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function addNumLlamadasADatos():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function deleteNumLlamadasADatos():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        
        
        
        lsText.append("			//Recepcion de datos");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function recibirDatos(poDatos:ArrayCollection,poLabels:ArrayCollection,psNombreTabla:String):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				switch(psNombreTabla) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case formularios.mcsC_COLUMNAS_GRID:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						addColsGrids(poDatos);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						break;		");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case formularios.mcs"+loTabla.getNombre()+":");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						datosGENERAL = poDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						if(!existeIndex(arrayPestanas[lPosiGENERAL],datosCombo)) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("							datosCombo.addItem(arrayPestanas[lPosiGENERAL]);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                                         datosCompletados();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						break;		");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case formularios.mcsDOCUMENTOS:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						datosDOCUMENTOS = poDatos;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						if(!existeIndex(arrayPestanas[lPosiDOCUMENTOS],datosCombo)) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("							datosCombo.addItem(arrayPestanas[lPosiDOCUMENTOS]);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						break;		");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("					case formularios.mcs"+lsNomTabla+":");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						datos"+lsNomTabla+" = poDatos;");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						datosCombo.addItem(arrayPestanas[lPosi"+lsNomTabla+"]);");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
            }
        }

        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function datosCompletados():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				cmb.dataProvider = datosCombo;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				cmb.selectedIndex = 0;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				rellenarDatos(lPosiGENERAL);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function rellenarPestana(e:IndexChangedEvent):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				rellenarDatos(e.currentTarget.selectedIndex);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function rellenarDatos(valor:int):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				switch(valor) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case lPosiGENERAL:");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                                         JDatosGenerales.establecerLabels(this,moCaptions);");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("						if(datosGENERAL[0]."+loCampos.get(i).getNombre()
                                                                                 +" != null) {");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("							TXT"+loCampos.get(i).getNombre()+".text = datosGENERAL[0]."
                                                                                 +loCampos.get(i).getNombre()+";");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("						}");lsText.append(JUtiles.msRetornoCarro);
        }

        lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function setCodigo(cod:String):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				codigo = cod;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function cerrar():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				moCtrlForms.cerrarForm();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				FlexGlobals.topLevelApplication.removeEventListener(MouseEvent.MOUSE_MOVE,movimientoMapa);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function enviarIncidencia():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				moCtrlForms.setFormulario(\"INCIDENCIAS\",codigo,moParam,moPadre,formularios.mcsC_TUBERIAS);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				moCtrlForms.abrirFrom(false);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			protected function inicioMovimiento(event:MouseEvent):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				posX = event.localX;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				posY = event.localY;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				tengoFoco = true;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			protected function finMovimiento(event:MouseEvent):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				tengoFoco = false;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			protected function movimientoMapa(event:MouseEvent):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				if(event.buttonDown && tengoFoco) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					this.x = event.stageX - posX;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					this.y = event.stageY - posY;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			protected function movimientoLocal (event:MouseEvent):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				if(event.buttonDown && tengoFoco) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					this.x = event.stageX - posX;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					this.y = event.stageY - posY;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function str_replace(mainStr:String, str1:String, str2:String):String {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				var temp:Array = mainStr.split(str1);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				var tempStr:String = temp.join(str2);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				return tempStr;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function selecionarMenu(): void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				var i:int = 0;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				for each (var obj:NavigatorContent in viewstack1.getChildren()) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					if(obj.label == cmb.selectedItem) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						viewstack1.selectedIndex = i;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					i++;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			//Establecer los datos de los grid");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function establecerDatosGrid(e:Event): void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				var grid:DataGrid = DataGrid(e.currentTarget);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				var gridDestino:DataGrid; ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				var arrColums:ArrayCollection; //Array de las columnas de la tabla COLUMNASGRID.");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				switch(grid.id) { ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					case \"gDOCUMENTOS\":");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						gDOCUMENTOS.dataProvider = datosDOCUMENTOS;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						arrColums = getColTabla(formularios.mcsDOCUMENTOS);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						gridDestino = gDOCUMENTOS;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("					case \"g"+lsNomTabla+"\":");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						g"+lsNomTabla+".dataProvider = datos"+lsNomTabla+";");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						arrColums = getColTabla(formularios.mcs"+lsNomTabla+");");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						gridDestino = g"+lsNomTabla+";");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				if(arrColums) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				        formularios.setGridColums(arrColums,gridDestino);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}			");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			private function verDatos(item:String):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				switch(item) {");lsText.append(JUtiles.msRetornoCarro);

        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("					case formularios.mcs"+lsNomTabla+":");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						if(g"+lsNomTabla+".selectedIndex != -1) {");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("							moCtrlForms.setFormulario(formularios.mcs"+lsNomTabla+",g"+lsNomTabla+".selectedItem.COD,moParam,moPadre);");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("							moCtrlForms.abrirFrom(false);		");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						}else {");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("							Alert.show(formularios.mcsT_ELEMENTO_NO_SEL);");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						}");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
            }
        }

        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("			protected function selectGrid(event:MouseEvent):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				switch(DataGrid(event.currentTarget).id) {");lsText.append(JUtiles.msRetornoCarro);
        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("					case \"g"+lsNomTabla+"\":");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						verDatos(formularios.mcs"+lsNomTabla+");");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("						break;");lsText.append(JUtiles.msRetornoCarro);
            }
        }
        lsText.append("				}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function SetMensaje(msg:String):void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			        mensaje.text = msg;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function deleteMensaje():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			        mensaje.text = \"\";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function cubrir():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			        c.height = this.height;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			public function descubrir():void {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			        c.height = 0;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		]]>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</fx:Script>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<fx:Declarations>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</fx:Declarations>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	<s:Group height=\"387\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:layout>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			<s:BasicLayout/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		</s:layout>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<mx:Image source=\"assets/images/baseForm.png\" height=\"423\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  mouseDown=\"inicioMovimiento(event)\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  mouseUp=\"finMovimiento(event)\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  mouseMove=\"movimientoLocal(event)\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  click=\"maximizarObjeto()\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  />");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:Button x=\"591\" y=\"359\" label=\"Cerrar\" click=\"cerrar()\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:Button x=\"422\" y=\"359\" label=\"Comunicación de errores\" click=\"enviarIncidencia()\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<mx:ViewStack  y=\"60\" id=\"viewstack1\" width=\"658\" height=\"280\" backgroundAlpha=\"0.9\" x=\"10\" change=\"rellenarPestana(event)\">");lsText.append(JUtiles.msRetornoCarro);

        //PestaNa General

        lsText.append("			<s:NavigatorContent id=\"nc0\" width=\"100%\" height=\"100%\" backgroundAlpha=\"0.9\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<s:layout>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<supportClasses:AttachmentLayout/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</s:layout>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<s:Group x=\"10\" width=\"647\" height=\"289\">");lsText.append(JUtiles.msRetornoCarro);

        int posx = 5;
        int posy = 5;

        for(int i = 0; i < loCampos.count(); i++ ){
            lsText.append("					<s:Label x=\""+ posx +"\" y=\""+ posy +"\" text=\""+loCampos.get(i).getNombre()+"\" height=\"22\" verticalAlign=\"middle\" width=\"100\" textAlign=\"right\" fontWeight=\"bold\"/>");lsText.append(JUtiles.msRetornoCarro);
            lsText.append("					<s:TextInput id=\"TXT"+loCampos.get(i).getNombre()+"\" x=\""+ new Integer(posx + 120).toString() +"\" y=\""+ posy +"\" width=\"100\" height=\"22\"/>");lsText.append(JUtiles.msRetornoCarro);
            posy = posy + 25;
            if(posy > 235) {
                posy = 5;
                posx = posx + 235;
            }
        }

        lsText.append("				</s:Group>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</s:NavigatorContent>");lsText.append(JUtiles.msRetornoCarro);
        
        
        //PestaNa documentos

        lsText.append("			<s:NavigatorContent id=\"nc1\" width=\"100%\" height=\"100%\" backgroundAlpha=\"0.9\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<s:layout>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<supportClasses:AttachmentLayout/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</s:layout>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				<s:HGroup>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					<mx:DataGrid id=\"gDOCUMENTOS\" width=\"640\" height=\"258\" creationComplete=\"establecerDatosGrid(event)\" ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("								 doubleClickEnabled=\"true\" doubleClick=\"JDatosGenerales.abrePagina(gDOCUMENTOS)\">");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("					</mx:DataGrid>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("				</s:HGroup>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("			</s:NavigatorContent>");lsText.append(JUtiles.msRetornoCarro);

        //Resto de pestañas

        num = 0;
        for(int i = 0; i < loTabla.getRelaciones().count();i++) {
            if(loTabla.getRelaciones().getRelacion(i).getTipoRelacion() == JRelacionesDef.mclRelacionExport) {
                String lsNomTabla = moUtiles.msSustituirRaros(loTabla.getRelaciones().getRelacion(i).getTablaRelacionada());
                lsText.append("			<s:NavigatorContent id=\"nc"+new Integer(num+2).toString()+"\" width=\"100%\" height=\"100%\" backgroundAlpha=\"0.9\">");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("				<s:layout>");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("					<supportClasses:AttachmentLayout/>");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("				</s:layout>");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("				<s:HGroup>");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("					<mx:DataGrid id=\"g"+lsNomTabla+"\" width=\"640\" height=\"258\" creationComplete=\"establecerDatosGrid(event)\" ");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("								 doubleClickEnabled=\"true\" doubleClick=\"selectGrid(event)\">");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("					</mx:DataGrid>");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("				</s:HGroup>");lsText.append(JUtiles.msRetornoCarro);
                lsText.append("			</s:NavigatorContent>");lsText.append(JUtiles.msRetornoCarro);
                num++;
            }
        }

        lsText.append("		</mx:ViewStack>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<mx:Image width=\"20\" height=\"22\" x=\"650\" y=\"5\" toolTip=\"Cerrar\" click=\"cerrar()\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  source=\"assets/images/w_close.png\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<mx:Image width=\"20\" height=\"22\" x=\"630\" y=\"5\" toolTip=\"Minimizar\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  click=\"JDatosGenerales.minimizar(this,minimizado);minimizado=true\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  source=\"assets/images/w_min.png\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:ComboBox id=\"cmb\" x=\"10\" y=\"29\" width=\"293\" change=\"selecionarMenu()\" fontWeight=\"bold\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:Button id=\"btnLeft\" x=\"311\" y=\"30\" label=\"&lt;\" fontSize=\"18\" fontWeight=\"bold\" textDecoration=\"none\" width=\"38\" click=\"clickLeft()\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:Button id=\"btnRight\" x=\"352\" y=\"30\" label=\"&gt;\" fontSize=\"18\" fontWeight=\"bold\" textDecoration=\"none\" width=\"38\" click=\"clickRight()\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:Button x=\"293\" y=\"359\" id=\"localizar\" label=\"Localizar en mapa\"");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		                  click=\"establecerParametros();ALocalizarEnMapa.locMapa(this,moParam,minimizado);minimizado = true\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:Label x=\"16\" y=\"7\" text=\"{formularios.mcsT_"+loTabla.getNombre().toString()+"}\" height=\"15\" styleName=\"titulo\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:Label id=\"mensaje\" x=\"10\" y=\"361\" width=\"275\" verticalAlign=\"middle\" textAlign=\"center\" fontWeight=\"bold\"/>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("		<s:BorderContainer id=\"c\" width=\"100%\" height=\"0\" alpha=\"0.2\" click=\"maximizarObjeto()\" />");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("	</s:Group>");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("</mx:Form>");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }

    @Override
    public String getRutaRelativa() {
        return "formsFlex";
    }

    @Override
    public String getNombre() {
        return "JForm"+ moUtiles.msSustituirRaros(moConex.getTablaActual())+".mxml";
    }

    @Override
    public boolean isGeneral() {
        return false;
    }

    @Override
    public String getNombreModulo() {
        return "JForm.mxml";
    }

    @Override
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        JFieldDefs loCampos = moConex.getCamposBD(moConex.getTablaActual() );;
        if(loCampos.size()<moProyecto.getOpciones().getCamposMinimosTodosModulos()){
            loParam.mbGenerar=false;
        }
        return loParam;
    }
}
