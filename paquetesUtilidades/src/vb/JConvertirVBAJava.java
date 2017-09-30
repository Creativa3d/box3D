/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package vb;


public class JConvertirVBAJava {

    public String getConversion(String psTextoVB){
        String lsResult = psTextoVB;

        lsResult = lsResult.replaceAll("End Sub", "}");
        lsResult = lsResult.replaceAll("End Function", "}");
        lsResult = lsResult.replaceAll("End Property", "}");
        lsResult = lsResult.replaceAll("End If", "}");
        lsResult = lsResult.replaceAll("Next i", "}");
        lsResult = lsResult.replaceAll("Public Sub", "public void ");
        lsResult = lsResult.replaceAll("Private Sub", "private void ");
        lsResult = lsResult.replaceAll("Private Function", "private   ");
        lsResult = lsResult.replaceAll("Public Function", "public   ");
        lsResult = lsResult.replaceAll("Public Property Get ", "public ");
        lsResult = lsResult.replaceAll(" & ", " + ");
        lsResult = lsResult.replaceAll("If ", "if(");
        lsResult = lsResult.replaceAll(" Then", "){");
        lsResult = lsResult.replaceAll(" And ", " && ");
        lsResult = lsResult.replaceAll(" Or ", " || ");
        lsResult = lsResult.replaceAll("True", "true");
        lsResult = lsResult.replaceAll("False", "false");
        lsResult = lsResult.replaceAll("Loop While ", "}while(");
        lsResult = lsResult.replaceAll("Else", "}else{");
        lsResult = lsResult.replaceAll(" <> ", " != ");
        lsResult = lsResult.replaceAll("Do While", "while(");
        lsResult = lsResult.replaceAll("Loop While", "while(");
        lsResult = lsResult.replaceAll("Select Case", "switch(");
        lsResult = lsResult.replaceAll("End Select", "}");
        lsResult = lsResult.replaceAll("CDbl", "JConversiones.cdbl");
        lsResult = lsResult.replaceAll("IsNumeric", "JConversiones.isNumeric");



//        lsResult = lsResult.replaceAll("", "");
//        lsResult = lsResult.replaceAll("", "");
//        lsResult = lsResult.replaceAll("", "");
//        lsResult = lsResult.replaceAll("", "");
        lsResult = lsResult.replaceAll("", "");
        lsResult = lsResult.replaceAll("Private", "private");
        lsResult = lsResult.replaceAll("Public", "public");
        lsResult = lsResult.replaceAll("ByVal ", "");
        lsResult = lsResult.replaceAll("Nothing", "null");
        lsResult = lsResult.replaceAll("Not ", "!");
        lsResult = lsResult.replaceAll("'", "//");
        lsResult = lsResult.replaceAll("Set ", "");
        lsResult = lsResult.replaceAll(" Is ", " == ");
        lsResult = lsResult.replaceAll("Dim ", "");
        lsResult = lsResult.replaceAll("Now", "new JDateEdu()");
        lsResult = lsResult.replaceAll("Err.Raise ", "throw new Exception(");
        lsResult = lsResult.replaceAll("oFunciones.MoveFirst", "moList.moveFirst()");
        lsResult = lsResult.replaceAll("oFunciones.MoveNext", "moList.moveNext()");
        lsResult = lsResult.replaceAll(" As Double", " As double");
        lsResult = lsResult.replaceAll(" As Long", " As int");
        lsResult = lsResult.replaceAll(" As Boolean", " As boolean");
        lsResult = lsResult.replaceAll("Optional ", "");
        lsResult = lsResult.replaceAll("For ", "for(");
        lsResult = lsResult.replaceAll("Loop", "}");
        lsResult = lsResult.replaceAll(" Do", " do{ ");


        StringBuffer lsAux= new StringBuffer();
        boolean lbLineaAlgo=false;
        boolean lbUltCaracLlave=false;

        //puntos y comas
        for(int i = 0 ; i < lsResult.length(); i++){
            //en retorno de carro,si hay algo en la linea le a?adimos ;
            if(lsResult.charAt(i)=='\n'){
                if(lbLineaAlgo && !lbUltCaracLlave){
                    lsAux.append(';');
                }
                lbLineaAlgo=false;
            }

            if(lsResult.charAt(i)!=' ' && lsResult.charAt(i)!='\n'){
                lbLineaAlgo=true;
            }
            if(lsResult.charAt(i)=='}'||lsResult.charAt(i)=='{'){
                lbUltCaracLlave=true;
            }else{
                if(lsResult.charAt(i)!=' '){
                    lbUltCaracLlave=false;
                }
            }

            lsAux.append(lsResult.charAt(i));
        }
        lsResult= lsAux.toString(); 
        //damos la vuelta a la declaracion de variables
        lsResult=getDarVuelta(lsResult, true);
        lsResult=getDarVuelta(lsResult, false);

        
        //
        //retoques finales
        //
        //salto linea de vb
        lsResult = lsResult.replaceAll("_;", "");

        return lsResult;


    }
    private String getDarVuelta(String psResult, boolean pbNew){
        String lsAS = " As " + (pbNew ? "New ":"");
        int i=psResult.indexOf(lsAS);
        while(i>=0){
            String lsPalabraAlante = getPalabra(psResult, i+lsAS.length(), true);
            String lsPalabraAtras = getPalabra(psResult, i-1, false);
            if(!lsPalabraAtras.equals("")){
                psResult=psResult.replaceAll(
                    lsPalabraAtras + lsAS + lsPalabraAlante,
                    lsPalabraAlante + " " + lsPalabraAtras + (pbNew ? " = new " + lsPalabraAlante+"()": ""));
            }
            i=psResult.indexOf(lsAS, i+2);
        }
        return psResult;

    }
    private boolean isCaracSepar(char pcChar){
        return pcChar=='\n'||
               pcChar==' '||
               pcChar==','||
               pcChar==';'||
               pcChar=='('||
               pcChar==')'||
               pcChar=='\n'||
               pcChar=='\n'||
               pcChar=='\n'||
               pcChar=='\n'||
               pcChar=='\n'||
               pcChar=='\n'||
               pcChar=='\n'
                ;
    }
    private String getPalabra(String psResult,int i, boolean pbAlante){
        StringBuffer lsPalabra=new StringBuffer();
        boolean lbCont=true;
        boolean lbContO=false;
        char lcCarac=' ';
        while(lbCont){
            lbCont=i<psResult.length();
            lbCont&=(i>=0);
            if(lbCont){
                lcCarac=psResult.charAt(i);
                if(lcCarac != ' '){
                    lbContO = true;
                }
                if(isCaracSepar(lcCarac) && lbContO){
                    lbCont=false;
                }
            }
            if(lbCont){
                lsPalabra.append(lcCarac);
            }
            if(lbCont){
                if(pbAlante){
                    i++;
                }else{
                    i--;
                }
            }
        }


        if(pbAlante){
            return lsPalabra.toString();
        }else{
            return lsPalabra.reverse().toString();
        }
    }
}
