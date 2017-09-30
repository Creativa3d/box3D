/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generadorClases.modulosCodigo;


import generadorClases.*;
import utiles.*;
import utilesGUIx.configForm.JConexion;

public class JGeneradorGUIxAvisos implements IModuloProyecto {
    private JProyecto moProyecto;  //Conexion a la base de datos
    private JUtiles moUtiles;   //Clase de utilidades
    private final JConexionGeneradorClass moConex;

    public JGeneradorGUIxAvisos(JProyecto poProyec) {
        moUtiles = new JUtiles(poProyec);
        moProyecto = poProyec;
        moConex = poProyec.getConex();
    }

    public String getCodigo() {
        StringBuffer lsText = new StringBuffer(100);

        lsText.append("/*");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * To change this template, choose Tools | Templates");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * and open the template in the editor.");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("package " + moConex.getDirPadre() + ";");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import ListDatos.JFilaDatosDefecto;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utiles.JDateEdu;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.ActionEventCZ;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.ActionListenerCZ;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.aplicacion.avisos.JAviso;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.formsGenericos.edicion.IFormEdicion;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIx.msgbox.JMsgBox;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIxAvisos.avisos.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIxAvisos.calendario.*;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIxAvisos.tablasExtend.JTEEGUIXAVISOS;");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("import utilesGUIxAvisos.tablasExtend.JTEEGUIXEVENTOS;");lsText.append(JUtiles.msRetornoCarro);

        lsText.append("import utilesFXAvisos.forms.JPanelGUIXEVENTOS;");lsText.append(JUtiles.msRetornoCarro);
        
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("/**");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" *");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" * @author eduardo");lsText.append(JUtiles.msRetornoCarro);
        lsText.append(" */");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("public class JutilesFXAvisosGUIxAvisos implements IAvisosListener{");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public JutilesFXAvisosGUIxAvisos(){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void eventos(final JTEEGUIXEVENTOS poEvento) throws Throwable {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JDatosGeneralesP.getDatosGenerales().getAvisos().add(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                new JAviso(\"evento \" + poEvento.getNOMBRE().getString(), new ActionListenerCZ() {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            public void actionPerformed(ActionEventCZ e) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                try {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    JPanelGUIXEVENTOS loPanel = new JPanelGUIXEVENTOS();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    loPanel.setDatos(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            JDatosGeneralesP.getDatosGenerales().getAvisos1()");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            , poEvento, JDatosGeneralesP.getDatosGenerales().getEventos()");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            , null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    JDatosGeneralesP.getDatosGenerales().mostrarEdicion((IFormEdicion)loPanel, loPanel);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                } catch (Exception ex) {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    JDatosGeneralesP.getDatosGenerales().mensajeErrorYLog(JutilesFXAvisosGUIxAvisos.class.getName(), ex, null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        })");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        );");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void avisos(JTEEGUIXAVISOS poAviso) throws Throwable {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(poAviso.getPANTALLASN().getBoolean()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            eventos(poAviso.getEvento());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(!poAviso.getEMAIL().isVacio()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JGUIxAvisosDatosGenerales loEmailSMS = JDatosGenerales.getDatosCorreos(poAviso.moList.moServidor);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JTEEGUIXEVENTOS loEvent = poAviso.getEvento();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JMensaje loMensaje ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    = new JMensaje(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            new String[]{poAviso.getEMAIL().getString()}");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            , loEvent.getNOMBRE().getString()");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                            , loEvent.getTEXTO().getString(), null);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loEmailSMS.enviarEmail(loMensaje);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(!poAviso.getTELF().isVacio()){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JGUIxAvisosDatosGenerales loEmailSMS = JDatosGenerales.getDatosCorreos(poAviso.moList.moServidor);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JTEEGUIXEVENTOS loEventos = poAviso.getEvento();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            loEmailSMS.enviarSMS(");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    poAviso.getTELF().getString()");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    , poAviso.getSENDER().getString()");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    , loEventos.getNOMBRE().getString() + \" \" + loEventos.getTEXTO().getString()");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                    , new JDateEdu().toString());");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    @Override");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    public void mostrar(JTEEGUIXEVENTOS poEvento) throws Throwable {");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        JFilaDatosDefecto loFila = new JFilaDatosDefecto(JFilaDatosDefecto.moArrayDatos(poEvento.getGRUPO().getString()+\"-\", '-'));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        if(loFila.msCampo(0).equalsIgnoreCase(\"VEHICREFO\")){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            //quitamos la tabla");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JFilaDatosDefecto loFilaAux = new JFilaDatosDefecto();");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            for(int i = 1 ; i < loFila.mlNumeroCampos(); i++){");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("                loFilaAux.addCampo(loFila.msCampo(i));");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("            JDatosGeneralesP.getDatosGenerales().getGestionProyecto().mostrarEdicion(JDatosGeneralesP.getDatosGenerales().getServer(), JDatosGeneralesP.getDatosGenerales(), loFila.msCampo(0), loFilaAux);");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("        }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    }");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("    ");lsText.append(JUtiles.msRetornoCarro);
        lsText.append("}");lsText.append(JUtiles.msRetornoCarro);

        return lsText.toString();
    }

    public String getRutaRelativa() {
        return ".";
    }

    public String getNombre() {
        return "J"+moProyecto.getConex().getDirPadre()+"GUIxAvisos.java";
    }

    public boolean isGeneral() {
        return true;
    }

    public String getNombreModulo() {
        return "JGUIxAvisos.java";
    }
    public JModuloProyectoParametros getParametros() {
        JModuloProyectoParametros loParam = new JModuloProyectoParametros();
        loParam.mbGenerar=!moProyecto.getOpciones().isEdicionFX();
        return loParam;
    }
}
