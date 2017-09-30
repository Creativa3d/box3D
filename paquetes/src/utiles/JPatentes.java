/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utiles;

import java.util.HashMap;

/**
 *
 * @author eduardo
 */
public class JPatentes {
    private static HashMap moHast = new HashMap();
    private static HashMap moHastModer = new HashMap();
    static {
        moHast.put("AA","001");
        moHast.put("EA","004");
        moHast.put("HA","007");
        moHast.put("EB","010");
        moHast.put("HB","013");
        moHast.put("EC","016");
        moHast.put("HC","019");
        moHast.put("FD","022");
        moHast.put("AE","025");
        moHast.put("EE","028");
        moHast.put("HE","031");
        moHast.put("CF","034");
        moHast.put("GF","037");
        moHast.put("BG","040");
        moHast.put("FG","043");
        moHast.put("BH","046");
        moHast.put("FH","049");
        moHast.put("AJ","052");
        moHast.put("EJ","055");
        moHast.put("HJ","058");
        moHast.put("EK","061");
        moHast.put("HK","064");
        moHast.put("CL","067");
        moHast.put("GL","070");
        moHast.put("BN","073");
        moHast.put("FN","076");
        moHast.put("AP","079");
        moHast.put("EP","082");
        moHast.put("HP","085");
        moHast.put("BA","002");
        moHast.put("FA","005");
        moHast.put("AB","008");
        moHast.put("FB","011");
        moHast.put("AC","014");
        moHast.put("FC","017");
        moHast.put("BD","020");
        moHast.put("GD","023");
        moHast.put("BE","026");
        moHast.put("FE","029");
        moHast.put("AF","032");
        moHast.put("EF","035");
        moHast.put("HF","038");
        moHast.put("CG","041");
        moHast.put("HG","044");
        moHast.put("CH","047");
        moHast.put("GH","050");
        moHast.put("BJ","053");
        moHast.put("FJ","056");
        moHast.put("BK","059");
        moHast.put("FK","062");
        moHast.put("AL","065");
        moHast.put("EL","068");
        moHast.put("HL","071");
        moHast.put("CN","074");
        moHast.put("GN","077");
        moHast.put("BP","080");
        moHast.put("FP","083");
        moHast.put("AR","086");
        moHast.put("CA","003");
        moHast.put("GA","006");
        moHast.put("CB","009");
        moHast.put("GB","012");
        moHast.put("BC","015");
        moHast.put("GC","018");
        moHast.put("ED","021");
        moHast.put("HD","024");
        moHast.put("CE","027");
        moHast.put("GE","030");
        moHast.put("BF","033");
        moHast.put("FF","036");
        moHast.put("AG","039");
        moHast.put("EG","042");
        moHast.put("AH","045");
        moHast.put("EH","048");
        moHast.put("HH","051");
        moHast.put("CJ","054");
        moHast.put("GJ","057");
        moHast.put("CK","060");
        moHast.put("GK","063");
        moHast.put("BL","066");
        moHast.put("FL","069");
        moHast.put("AN","072");
        moHast.put("EN","075");
        moHast.put("HN","078");
        moHast.put("CP","081");
        moHast.put("GP","084");
        moHast.put("BR","087");
        moHast.put("CR","088");
        moHast.put("GR","091");
        moHast.put("BS","094");
        moHast.put("FS","097");
        moHast.put("AT","100");
        moHast.put("ET","103");
        moHast.put("HT","106");
        moHast.put("CU","109");
        moHast.put("GU","112");
        moHast.put("BV","115");
        moHast.put("FV","118");
        moHast.put("AX","121");
        moHast.put("EX","124");
        moHast.put("HX","127");
        moHast.put("EY","130");
        moHast.put("HY","133");
        moHast.put("CZ","136");
        moHast.put("GZ","139");
        moHast.put("DD","142");
        moHast.put("DG","145");
        moHast.put("DJ","148");
        moHast.put("DN","151");
        moHast.put("DS","154");
        moHast.put("DV","157");
        moHast.put("DZ","160");
        moHast.put("KC","163");
        moHast.put("KF","166");
        moHast.put("KJ","169");
        moHast.put("KN","172");
        moHast.put("KS","175");
        moHast.put("ER","089");
        moHast.put("HR","092");
        moHast.put("CS","095");
        moHast.put("GS","098");
        moHast.put("BT","101");
        moHast.put("FT","104");
        moHast.put("AU","107");
        moHast.put("EU","110");
        moHast.put("HU","113");
        moHast.put("CV","116");
        moHast.put("GV","119");
        moHast.put("BX","122");
        moHast.put("FX","125");
        moHast.put("BY","128");
        moHast.put("FY","131");
        moHast.put("AZ","134");
        moHast.put("EZ","137");
        moHast.put("DA","140");
        moHast.put("DE","143");
        moHast.put("DH","146");
        moHast.put("DK","149");
        moHast.put("DP","152");
        moHast.put("DT","155");
        moHast.put("DX","158");
        moHast.put("KA","161");
        moHast.put("KD","164");
        moHast.put("KG","167");
        moHast.put("KK","170");
        moHast.put("KP","173");
        moHast.put("KT","176");
        moHast.put("FR","090");
        moHast.put("AS","093");
        moHast.put("ES","096");
        moHast.put("HS","099");
        moHast.put("CT","102");
        moHast.put("GT","105");
        moHast.put("BU","108");
        moHast.put("FU","111");
        moHast.put("AV","114");
        moHast.put("EV","117");
        moHast.put("HV","120");
        moHast.put("CX","123");
        moHast.put("GX","126");
        moHast.put("CY","129");
        moHast.put("GY","132");
        moHast.put("BZ","135");
        moHast.put("FZ","138");
        moHast.put("DB","141");
        moHast.put("DF","144");
        moHast.put("DI","147");
        moHast.put("DL","150");
        moHast.put("DR","153");
        moHast.put("DU","156");
        moHast.put("DY","159");
        moHast.put("KB","162");
        moHast.put("KE","165");
        moHast.put("KH","168");
        moHast.put("KL","171");
        moHast.put("KR","174");
        moHast.put("KU","177");
        moHast.put("KV","178");
        moHast.put("KZ","181");
        moHast.put("LC","184");
        moHast.put("LF","187");
        moHast.put("LJ","190");
        moHast.put("LN","193");
        moHast.put("LS","196");
        moHast.put("LV","199");
        moHast.put("LZ","202");
        moHast.put("NC","205");
        moHast.put("NF","208");
        moHast.put("NJ","211");
        moHast.put("NN","214");
        moHast.put("NS","217");
        moHast.put("NV","220");
        moHast.put("PA","223");
        moHast.put("PD","226");
        moHast.put("PG","229");
        moHast.put("PK","232");
        moHast.put("PP","235");
        moHast.put("PU","238");
        moHast.put("PY","241");
        moHast.put("RA","244");
        moHast.put("RD","247");
        moHast.put("RG","250");
        moHast.put("RK","253");
        moHast.put("RP","256");
        moHast.put("RT","259");
        moHast.put("RX","262");
        moHast.put("HZ","265");
        moHast.put("KX","179");
        moHast.put("LA","182");
        moHast.put("LD","185");
        moHast.put("LG","188");
        moHast.put("LK","191");
        moHast.put("LP","194");
        moHast.put("LT","197");
        moHast.put("LX","200");
        moHast.put("NA","203");
        moHast.put("ND","206");
        moHast.put("NG","209");
        moHast.put("NK","212");
        moHast.put("NP","215");
        moHast.put("NT","218");
        moHast.put("NY","221");
        moHast.put("PB","224");
        moHast.put("PE","227");
        moHast.put("PH","230");
        moHast.put("PL","233");
        moHast.put("PS","236");
        moHast.put("PV","239");
        moHast.put("PZ","242");
        moHast.put("RB","245");
        moHast.put("RE","248");
        moHast.put("RH","251");
        moHast.put("RL","254");
        moHast.put("RR","257");
        moHast.put("RU","260");
        moHast.put("RY","263");
        moHast.put("SA","266");
        moHast.put("KY","180");
        moHast.put("LB","183");
        moHast.put("LE","186");
        moHast.put("LH","189");
        moHast.put("LL","192");
        moHast.put("LR","195");
        moHast.put("LU","198");
        moHast.put("LY","201");
        moHast.put("NB","204");
        moHast.put("NE","207");
        moHast.put("NH","210");
        moHast.put("NL","213");
        moHast.put("NR","216");
        moHast.put("NU","219");
        moHast.put("NZ","222");
        moHast.put("PC","225");
        moHast.put("PF","228");
        moHast.put("PJ","231");
        moHast.put("PN","234");
        moHast.put("PT","237");
        moHast.put("PX","240");
        moHast.put("NX","243");
        moHast.put("RC","246");
        moHast.put("RF","249");
        moHast.put("RJ","252");
        moHast.put("RN","255");
        moHast.put("RS","258");
        moHast.put("RV","261");
        moHast.put("RZ","264");
        moHast.put("SB","267");
        moHast.put("SC","268");
        moHast.put("SF","271");
        moHast.put("SJ","274");
        moHast.put("SN","277");
        moHast.put("SS","280");
        moHast.put("SV","283");
        moHast.put("SZ","286");
        moHast.put("TC","289");
        moHast.put("TF","292");
        moHast.put("TJ","295");
        moHast.put("TN","298");
        moHast.put("TS","301");
        moHast.put("TV","304");
        moHast.put("TZ","307");
        moHast.put("UC","310");
        moHast.put("UF","313");
        moHast.put("UJ","316");
        moHast.put("UN","319");
        moHast.put("US","322");
        moHast.put("UV","325");
        moHast.put("UZ","328");
        moHast.put("VC","331");
        moHast.put("VF","334");
        moHast.put("VJ","337");
        moHast.put("VN","340");
        moHast.put("VS","343");
        moHast.put("VV","346");
        moHast.put("VZ","349");
        moHast.put("XC","352");
        moHast.put("XF","355");
        moHast.put("SD","269");
        moHast.put("SG","272");
        moHast.put("SK","275");
        moHast.put("SP","278");
        moHast.put("ST","281");
        moHast.put("SX","284");
        moHast.put("TA","287");
        moHast.put("TD","290");
        moHast.put("TG","293");
        moHast.put("TK","296");
        moHast.put("TP","299");
        moHast.put("TT","302");
        moHast.put("TX","305");
        moHast.put("UA","308");
        moHast.put("UD","311");
        moHast.put("UG","314");
        moHast.put("UK","317");
        moHast.put("UP","320");
        moHast.put("UT","323");
        moHast.put("UX","326");
        moHast.put("VA","329");
        moHast.put("VD","332");
        moHast.put("VG","335");
        moHast.put("VK","338");
        moHast.put("VP","341");
        moHast.put("VT","344");
        moHast.put("VX","347");
        moHast.put("XA","350");
        moHast.put("XD","353");
        moHast.put("XG","356");
        moHast.put("SE","270");
        moHast.put("SH","273");
        moHast.put("SL","276");
        moHast.put("SR","279");
        moHast.put("SU","282");
        moHast.put("SY","285");
        moHast.put("TB","288");
        moHast.put("TE","291");
        moHast.put("TH","294");
        moHast.put("TL","297");
        moHast.put("TR","300");
        moHast.put("TU","303");
        moHast.put("TY","306");
        moHast.put("UB","309");
        moHast.put("UE","312");
        moHast.put("UH","315");
        moHast.put("UL","318");
        moHast.put("UR","321");
        moHast.put("UU","324");
        moHast.put("UY","327");
        moHast.put("VB","330");
        moHast.put("VE","333");
        moHast.put("VH","336");
        moHast.put("VL","339");
        moHast.put("VR","342");
        moHast.put("VU","345");
        moHast.put("VY","348");
        moHast.put("XB","351");
        moHast.put("XE","354");
        moHast.put("XH","357");
        moHast.put("XJ","358");
        moHast.put("XM","361");
        moHast.put("XQ","364");
        moHast.put("XT","367");
        moHast.put("XX","370");
        moHast.put("YA","373");
        moHast.put("JB","376");
        moHast.put("JE","379");
        moHast.put("YE","382");
        moHast.put("YH","385");
        moHast.put("YL","388");
        moHast.put("YR","391");
        moHast.put("YU","394");
        moHast.put("YY","397");
        moHast.put("ZB","400");
        moHast.put("ZE","403");
        moHast.put("ZH","406");
        moHast.put("ZK","409");
        moHast.put("JG","412");
        moHast.put("ZP","415");
        moHast.put("ZT","418");
        moHast.put("ZX","421");
        moHast.put("WA","430");
        moHast.put("WD","433");
        moHast.put("WG","436");
        moHast.put("WK","439");
        moHast.put("WP","442");
        moHast.put("WT","445");
        moHast.put("JK","448");
        moHast.put("XK","359");
        moHast.put("XN","362");
        moHast.put("XR","365");
        moHast.put("XU","368");
        moHast.put("XY","371");
        moHast.put("YB","374");
        moHast.put("JC","377");
        moHast.put("YC","380");
        moHast.put("YF","383");
        moHast.put("YJ","386");
        moHast.put("YN","389");
        moHast.put("YS","392");
        moHast.put("YV","395");
        moHast.put("YZ","398");
        moHast.put("ZC","401");
        moHast.put("ZF","404");
        moHast.put("ZI","407");
        moHast.put("ZL","410");
        moHast.put("JH","413");
        moHast.put("ZR","416");
        moHast.put("ZU","419");
        moHast.put("ZY","422");
        moHast.put("WB","431");
        moHast.put("WE","434");
        moHast.put("WH","437");
        moHast.put("WL","440");
        moHast.put("WR","443");
        moHast.put("WU","446");
        moHast.put("WV","449");
        moHast.put("XL","360");
        moHast.put("XP","363");
        moHast.put("XS","366");
        moHast.put("XV","369");
        moHast.put("XZ","372");
        moHast.put("JA","375");
        moHast.put("JD","378");
        moHast.put("YD","381");
        moHast.put("YG","384");
        moHast.put("YK","387");
        moHast.put("YP","390");
        moHast.put("YT","393");
        moHast.put("YX","396");
        moHast.put("ZA","399");
        moHast.put("ZD","402");
        moHast.put("ZG","405");
        moHast.put("ZJ","408");
        moHast.put("JF","411");
        moHast.put("ZN","414");
        moHast.put("ZS","417");
        moHast.put("ZV","420");
        moHast.put("ZZ","423");
        moHast.put("WC","432");
        moHast.put("WF","435");
        moHast.put("WJ","438");
        moHast.put("WN","441");
        moHast.put("WS","444");
        moHast.put("JJ","447");
        moHast.put("WW","450");
        moHast.put("WX","451");
        moHast.put("ZW","454");
        moHast.put("UW","457");
        moHast.put("RW","460");
        moHast.put("LW","463");
        moHast.put("MY","466");
        moHast.put("MU","469");
        moHast.put("JL","424");
        moHast.put("JP","427");
        moHast.put("JT","472");
        moHast.put("JW","475");
        moHast.put("JZ","478");
        moHast.put("MC","481");
        moHast.put("MF","484");
        moHast.put("MJ","487");
        moHast.put("MN","490");
        moHast.put("IA","493");
        moHast.put("ID","496");
        moHast.put("IG","499");
        moHast.put("IK","502");
        moHast.put("IO","505");
        moHast.put("IS","508");
        moHast.put("IV","511");
        moHast.put("IY","514");
        moHast.put("QB","517");
        moHast.put("QE","520");
        moHast.put("QH","523");
        moHast.put("QL","526");
        moHast.put("QP","529");
        moHast.put("QT","532");
        moHast.put("WY","452");
        moHast.put("YW","455");
        moHast.put("TW","458");
        moHast.put("PW","461");
        moHast.put("KW","464");
        moHast.put("MX","467");
        moHast.put("MT","470");
        moHast.put("JN","425");
        moHast.put("JR","428");
        moHast.put("JU","473");
        moHast.put("JX","476");
        moHast.put("MA","479");
        moHast.put("MD","482");
        moHast.put("MG","485");
        moHast.put("MK","488");
        moHast.put("MP","491");
        moHast.put("IB","494");
        moHast.put("IE","497");
        moHast.put("IH","500");
        moHast.put("IL","503");
        moHast.put("IP","506");
        moHast.put("IT","509");
        moHast.put("IW","512");
        moHast.put("IZ","515");
        moHast.put("QC","518");
        moHast.put("QF","521");
        moHast.put("QJ","524");
        moHast.put("QN","527");
        moHast.put("QR","530");
        moHast.put("QU","533");
        moHast.put("WZ","453");
        moHast.put("XW","456");
        moHast.put("SW","459");
        moHast.put("NW","462");
        moHast.put("MZ","465");
        moHast.put("MV","468");
        moHast.put("MS","471");
        moHast.put("JO","426");
        moHast.put("JS","429");
        moHast.put("JV","474");
        moHast.put("JY","477");
        moHast.put("MB","480");
        moHast.put("ME","483");
        moHast.put("MH","486");
        moHast.put("ML","489");
        moHast.put("MR","492");
        moHast.put("IC","495");
        moHast.put("IF","498");
        moHast.put("IJ","501");
        moHast.put("IN","504");
        moHast.put("IR","507");
        moHast.put("IU","510");
        moHast.put("IX","513");
        moHast.put("QA","516");
        moHast.put("QD","519");
        moHast.put("QG","522");
        moHast.put("QK","525");
        moHast.put("QO","528");
        moHast.put("QS","531");
        moHast.put("QV","534");
        moHast.put("QW","535");
        moHast.put("QZ","538");
        moHast.put("OC","541");
        moHast.put("OF","544");
        moHast.put("OJ","547");
        moHast.put("ON","550");
        moHast.put("OR","553");
        moHast.put("OU","556");
        moHast.put("OX","559");
        moHast.put("AD","562");
        moHast.put("AM","565");
        moHast.put("BB","568");
        moHast.put("BO","571");
        moHast.put("CD","574");
        moHast.put("CO","577");
        moHast.put("DM","580");
        moHast.put("QX","536");
        moHast.put("OA","539");
        moHast.put("OD","542");
        moHast.put("OG","545");
        moHast.put("OK","548");
        moHast.put("OO","551");
        moHast.put("OS","554");
        moHast.put("OV","557");
        moHast.put("OY","560");
        moHast.put("AI","563");
        moHast.put("AW","566");
        moHast.put("BI","569");
        moHast.put("BW","572");
        moHast.put("CI","575");
        moHast.put("CW","578");
        moHast.put("DO","581");
        moHast.put("QY","537");
        moHast.put("OB","540");
        moHast.put("OE","543");
        moHast.put("OH","546");
        moHast.put("OL","549");
        moHast.put("OP","552");
        moHast.put("OT","555");
        moHast.put("OW","558");
        moHast.put("OZ","561");
        moHast.put("AK","564");
        moHast.put("AY","567");
        moHast.put("BM","570");
        moHast.put("CC","573");
        moHast.put("CM","576");
        moHast.put("DC","579");
        moHast.put("DW","582");
        
        moHastModer.put("B","1");
        moHastModer.put("C","2");
        moHastModer.put("D","3");
        moHastModer.put("F","4");
        moHastModer.put("G","5");
        moHastModer.put("H","6");
        moHastModer.put("J","7");
        moHastModer.put("K","8");
        moHastModer.put("L","9");
        moHastModer.put("P","0");
        moHastModer.put("R","2");
        moHastModer.put("S","3");
        moHastModer.put("T","4");
        moHastModer.put("V","5");
        moHastModer.put("W","6");
        moHastModer.put("X","7");
        moHastModer.put("Y","8");
        moHastModer.put("Z","9");

        

    }
    public static String getDigitoVerificador(String psRUT) {
        psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
        try{
            int letras=0;
            for(int i = 0 ; i < psRUT.length(); i++){
                if(!JConversiones.isNumeric(psRUT.substring(i, i+1))){
                    letras++;
                } else {

                }
            }
            switch (letras) {
                case 4:
                    return getDigitoVerificadorModer(psRUT);
                case 3:
                    return getDigitoVerificadorMoto(psRUT);
                default:
                    return getDigitoVerificadorAntig(psRUT);
            }
        }catch(Exception e){
            return null;
        }
    }
    
    public static String getDigitoVerificadorMoto(String psRUT) {
            psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
            String nd="";
            for(int i = 0 ; i < 3; i++){
                nd+=moHastModer.get(String.valueOf(psRUT.charAt(i)));
            }
            if(psRUT.substring(3).length()==2){
                nd+="0"+psRUT.substring(3);
            } else {
                nd+=psRUT.substring(3);
            }
            if(nd.length()==6){
                return JRUT.getDigitoVerificadorGenerico(nd);
            }else{
                return null;
            }
    }
    
    public static String getDigitoVerificadorAntig(String psRUT) {
            psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
            if(psRUT.length()==5){
                psRUT=psRUT.substring(0, 2)+"0"+psRUT.substring(2);
            }
            if(psRUT.length()!=6){
                return null;
            }
            psRUT = moHast.get(psRUT.substring(0, 2)) + psRUT.substring(2);

            return JRUT.getDigitoVerificadorGenerico(psRUT);
    }
    
    public static String getDigitoVerificadorModer(String psRUT) {
        psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
        if(psRUT.length()!=6){
            return null;
        }
        String lsRUT = "";
        for(int i = 0 ; i < psRUT.length(); i++){
            if(!JConversiones.isNumeric(psRUT.substring(i, i+1))){
                lsRUT += moHastModer.get(psRUT.substring(i, i+1));
            } else {
                lsRUT += psRUT.substring(i, i+1);
            }
        }
        psRUT = lsRUT;
        
        return JRUT.getDigitoVerificadorGenerico(psRUT);
        
    }    
    public static boolean isPatenteOK(String psRUT, String psDV){
        if(JCadenas.isVacio(psRUT)){
            return false;
        }
        psRUT=psRUT.replace(".", "").replace("-", "").replace(" ", "");
        return getDigitoVerificador(psRUT).equalsIgnoreCase(psDV);
    }
    
    public static String getPatenteFormateado(String psTexto, String psDV){
        if(JCadenas.isVacio(psTexto)){
            return "";
        }
        return psTexto + "-" + psDV;
    }
    
    
}
