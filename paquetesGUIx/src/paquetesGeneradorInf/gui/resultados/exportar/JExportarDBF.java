/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetesGeneradorInf.gui.resultados.exportar;

import ListDatos.JListDatos;
import com.linuxense.javadbf.DBFField;
import com.linuxense.javadbf.DBFWriter;
import java.io.File;
import java.io.FileOutputStream;


public class JExportarDBF implements IExportar {

    public void exportar(JListDatos poList, File poFile) throws Throwable {



        DBFWriter writer = new DBFWriter();
        writer.setFields( getCampos(poList));

        if(poList.moveFirst()){
            do{
                writer.addRecord(getValores(poList));
            }while(poList.moveNext());
        }
        FileOutputStream fos = new FileOutputStream( poFile);
        try{
            writer.write( fos);
        }finally{
            fos.close();
        }

    }

    private DBFField[] getCampos(JListDatos poList){
        DBFField[] fields = new DBFField[poList.getFields().size()];
        for(int i = 0;i < poList.getFields().size();i++ ){

            DBFField field = new DBFField();
            int lLen = poList.getFields(i).getNombre().length();
            if(lLen>10){
                field.setName(poList.getFields(i).getNombre().substring(0, 10)); // give a name to the field
            }else{
                field.setName(poList.getFields(i).getNombre()); // give a name to the field
            }
            switch(poList.getFields(i).getTipo()){
                case JListDatos.mclTipoBoolean:
                    field.setDataType( DBFField.FIELD_TYPE_L); // and set its type
                    break;
                case JListDatos.mclTipoFecha:
                    field.setDataType( DBFField.FIELD_TYPE_D); // and set its type
                    break;
                case JListDatos.mclTipoNumero:
                    field.setDataType( DBFField.FIELD_TYPE_N); // and set its type
                    break;
                case JListDatos.mclTipoNumeroDoble:
                case JListDatos.mclTipoMoneda3Decimales:
                case JListDatos.mclTipoMoneda:
                case JListDatos.mclTipoPorcentual3Decimales:
                case JListDatos.mclTipoPorcentual:
                    field.setDataType( DBFField.FIELD_TYPE_F); // and set its type
                    break;
                default:
                    field.setDataType( DBFField.FIELD_TYPE_C); // and set its type
                    if(poList.getFields(i).getTamano()>0 &&  poList.getFields(i).getTamano() <= 255){
                        field.setFieldLength( poList.getFields(i).getTamano()); // and length of the field
                    }else{
                        field.setFieldLength( 255); // and length of the field
                    }
            }

            fields[i]=field;
        }
        return fields;

    }
    private Object[] getValores(JListDatos poList){
        Object[] fields = new Object[poList.getFields().size()];
        for(int i = 0;i < poList.getFields().size();i++ ){
            fields[i] = poList.getFields(i).getValueSQL();
        }
        return fields;
    }

    public String getNombre() {
        return "Formato DBF";
    }

    public String[] getOpcionesNombre() {
        return null;
    }
    public String[] getOpciones() {
        return null;
    }

    public void setOpciones(String[] pasOpciones) {
    }

    public String[] getExtensiones() {
        return new String[]{"dbf"};
    }


}
