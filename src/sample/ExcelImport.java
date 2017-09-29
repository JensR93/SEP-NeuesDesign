package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import sample.DAO.auswahlklasse;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.ResourceBundle;

/**
 * Created by Manuel Hüttermann on 15.08.2017.
 */
public class ExcelImport implements Initializable{


    private static Spieler aktuellerSpieler = new Spieler();
    private static Spieler UpdateSpieler = new Spieler();
    private static ObservableList obs_vorh=FXCollections.observableArrayList();

    public static ObservableList getObs_vorh() {
        return obs_vorh;
    }

    public static void setObs_vorh(ObservableList obs_vorh) {
        ExcelImport.obs_vorh = obs_vorh;
    }
    static boolean freilos=true;



//endregion

    public static boolean importExcelData(String excelDatei) throws Exception {


        obs_vorh.clear();
        aktuellerSpieler=new Spieler();
        UpdateSpieler=new Spieler();


        if(auswahlklasse.getDict_doppelte_spieler().size()>0)
        {
            System.out.println("getDict_doppelte_spieler nicht geleert!");
        }
        if(auswahlklasse.getSpielererfolgreich().size()>0)
        {
            System.out.println("getSpielererfolgreich nicht geleert!");

        }
        if(auswahlklasse.getSpielerupdate().size()>0)
        {
            System.out.println("getSpielerupdate nicht geleert!");
        }
        try {
            FileInputStream iStream = new FileInputStream(excelDatei);
            HSSFWorkbook workbook = new HSSFWorkbook(iStream);

            HSSFSheet worksheet = workbook.getSheet("Einzel");
            readWorkSheetSingle(worksheet);

            worksheet = workbook.getSheet("Doppel");
            readWorkSheetDoubleMixed(worksheet);

            worksheet = workbook.getSheet("Mixed");
            readWorkSheetDoubleMixed(worksheet);
            ExcelImport exc= new ExcelImport();
            try {
                exc.pressBtn_Popup();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


    private static void readWorkSheetSingle(HSSFSheet worksheet) {
        int actualRow = 1;

        while ((worksheet.getRow(actualRow) != null)) {
            //get row
            try {
                HSSFRow row = worksheet.getRow(actualRow);
                Spieler sp = readRow(row);
                if(sp!=null) {

                    if(auswahlklasse.getSpielererfolgreich().get(aktuellerSpieler)!=null)
                    {
                        int [] rpunkte = auswahlklasse.getSpielererfolgreich().get(sp).getrPunkte();
                        if(sp.getrPunkte()[0]>0)
                        {
                            rpunkte[0]=sp.getrPunkte()[0];
                        }
                        if(sp.getrPunkte()[1]>0)
                        {
                            rpunkte[1]=sp.getrPunkte()[1];
                        }
                        if(sp.getrPunkte()[2]>0)
                        {
                            rpunkte[2]=sp.getrPunkte()[2];
                        }
                    }
                    //ExcelImport.getObs_vorh().add(sp);
                    else if(auswahlklasse.getDict_doppelte_spieler().get(aktuellerSpieler)==null)
                    {
                        // auswahlklasse.InfoBenachrichtigung("Spieler nicht vorhanden",sp.toString()+" wurde hinzugefügt.");
                        sp.getSpielerDAO().create(sp);
                        auswahlklasse.getSpieler().put(sp.getSpielerID(),sp);
                        auswahlklasse.getObs_spieler().add(sp);
                        auswahlklasse.getSpielererfolgreich().put(sp.toString(),sp);
                        System.out.println("Neuer Spieler erstellt");
                    }
                    else
                    {
                        System.out.println("DUPLIKATE");
                    }
                    if(obs_vorh!=null)
                    {
                        obs_vorh.clear();
                    }
                    //auswahlklasse.getDict_doppelte_spieler().remove(sp.getExtSpielerID());
                    System.out.println("Spieler erfolgreich (ohne Duplikat) erstellt");


                }
                actualRow++;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }



    private static void readWorkSheetDoubleMixed(HSSFSheet worksheet) {
        try {
            int actualRow = 1;

            while ((worksheet.getRow(actualRow) != null)) {
                //get first row
                HSSFRow row = worksheet.getRow(actualRow);
                // readRow(row);
                Spieler sp = readRow(row);
                if(sp!=null) {
                    // ExcelImport.getVorhandeneSpieler().add(sp);,
                    if(auswahlklasse.getSpielererfolgreich().get(sp)!=null)
                    {
                        int [] rpunkte = auswahlklasse.getSpielererfolgreich().get(sp).getrPunkte();
                        if(sp.getrPunkte()[0]>0)
                        {
                            rpunkte[0]=sp.getrPunkte()[0];
                        }
                        if(sp.getrPunkte()[1]>0)
                        {
                            rpunkte[1]=sp.getrPunkte()[1];
                        }
                        if(sp.getrPunkte()[2]>0)
                        {
                            rpunkte[2]=sp.getrPunkte()[2];
                        }
                    }
                    //ExcelImport.getObs_vorh().add(sp);
                    else if(auswahlklasse.getDict_doppelte_spieler().get(sp)==null)
                    {
                        System.out.println("Spieler nicht vorhanden"+sp.getVName());
                        //auswahlklasse.InfoBenachrichtigung("Spieler nicht vorhanden",sp.toString()+" wurde hinzugefügt.");
                        sp.getSpielerDAO().create(sp);
                        auswahlklasse.getSpieler().put(sp.getSpielerID(),sp);
                        auswahlklasse.getObs_spieler().add(sp);
                        auswahlklasse.getSpielererfolgreich().put(sp.toString(),sp);
                    }
                    obs_vorh.clear();
                    //dict_doppelte_spieler.remove(sp.getExtSpielerID());
                    System.out.println("Spieler erfolgreich (ohne Duplikat) erstellt");
                }
                actualRow++;

                //

                //get second row
                row = worksheet.getRow(actualRow);
                if (row != null) {
                    Spieler tempSpieler = readRow(row);
                    if (tempSpieler !=null){
                        auswahlklasse.getObs_spieler().add(tempSpieler);
                        auswahlklasse.addSpieler(tempSpieler);
                        //System.out.println(tempSpieler.getVName()+" "+tempSpieler.getNName()+" gespeichert"+ " geschlecht:"+tempSpieler.getGeschlecht()+" extID:"+tempSpieler.getExtSpielerID()+"verein: "+tempSpieler.getVerein()+" gdatum:"+tempSpieler.getGDatum());
                        if(auswahlklasse.getSpielererfolgreich().get(aktuellerSpieler)!=null)
                        {
                            int [] rpunkte = auswahlklasse.getSpielererfolgreich().get(sp).getrPunkte();
                            if(sp.getrPunkte()[0]>0)
                            {
                                rpunkte[0]=sp.getrPunkte()[0];
                            }
                            if(sp.getrPunkte()[1]>0)
                            {
                                rpunkte[1]=sp.getrPunkte()[1];
                            }
                            if(sp.getrPunkte()[2]>0)
                            {
                                rpunkte[2]=sp.getrPunkte()[2];
                            }
                        }
                        //ExcelImport.getObs_vorh().add(sp);
                        else if(auswahlklasse.getDict_doppelte_spieler().get(tempSpieler)==null)
                        {
                            System.out.println("Spieler nicht vorhanden"+tempSpieler.getVName());
                            //auswahlklasse.InfoBenachrichtigung("Spieler nicht vorhanden",tempSpieler.toString()+" wurde hinzugefügt.");
                            tempSpieler.getSpielerDAO().create(tempSpieler);
                            auswahlklasse.getSpieler().put(tempSpieler.getSpielerID(),tempSpieler);
                            auswahlklasse.getObs_spieler().add(sp);
                            auswahlklasse.getSpielererfolgreich().put(tempSpieler.toString(),tempSpieler);
                        }
                        obs_vorh.clear();
                        //dict_doppelte_spieler.remove(sp.getExtSpielerID());
                        System.out.println("Spieler erfolgreich (ohne Duplikat) erstellt");
                    }
                }
                actualRow++;
                if(worksheet.getRow(actualRow)==null)
                {
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    private static Spieler readRow(HSSFRow row) throws Exception {
        // dict_doppelte_spieler=new Hashtable<>();
        int cellNumber = 0;
        HSSFCell cell = row.getCell(cellNumber);


        String vName = "";
        String nName;
        LocalDate gDatum = LocalDate.now();
        boolean geschlecht = false;
        String extSpielerID = "";
        int rPunkte[] = new int[3];
        Verein verein = null;



        HSSFCell nachnameZelle = row.getCell(1);
        if (nachnameZelle != null ) {
            if(nachnameZelle.toString().length()>1&&(nachnameZelle.getStringCellValue() != ""&&nachnameZelle.getStringCellValue().toUpperCase()!="FREILOS"&&nachnameZelle.getStringCellValue().toUpperCase()!="FREIMELDUNG")) {
                if(!nachnameZelle.getStringCellValue().substring(0,1).equalsIgnoreCase("#"))
                {
                    freilos=false;
                    nName = nachnameZelle.getStringCellValue();
                    if (nName.equals("")||nName.toUpperCase().equals("FREIMELDUNG"))
                    {
                        System.out.println("FREIMELDUNG");
                        return null;
                    }

                    HSSFCell vornameZelle = row.getCell(0);
                    if (vornameZelle != null) {
                        try {
                            vName = vornameZelle.getStringCellValue();
                        } catch (Exception e) {
                            try {
                                vName = String.valueOf(vornameZelle.getNumericCellValue());
                            } catch (Exception e1) {

                            }
                        }
                    }
                    HSSFCell geschlechtZelle = row.getCell(2);
                    if (geschlechtZelle != null) {
                        try {
                            geschlecht = (geschlechtZelle.getStringCellValue().toUpperCase().contains("M"));
                        } catch (Exception e) {

                        }
                    }

                    HSSFCell extSpieleridZelle = row.getCell(11);
                    if (extSpieleridZelle != null&&extSpieleridZelle.toString()!="") {
                        try {
                            extSpielerID = extSpieleridZelle.getStringCellValue();
                        } catch (Exception e) {
                            try {
                                extSpielerID = String.valueOf(extSpieleridZelle.getNumericCellValue());
                            } catch (Exception e1) {
                                if(!freilos) {
                                    if(!freilos) {
                                        auswahlklasse.WarnungBenachrichtigung("Formatierungsfehler", vName + " " + nName + " (" + extSpielerID + ")" + ": Ext. SpielerID nicht richtig formatiert");
                                        System.out.println("Ext. Spieler ID konnte nicht eingelesen werden");}
                                }

                            }
                        }
                    }
                    HSSFCell gdatumZelle = row.getCell(12);
                    if (gdatumZelle != null&&gdatumZelle.toString()!="") {
                        if(gdatumZelle.getCellTypeEnum()==CellType.NUMERIC){
                            gDatum = gdatumZelle.getDateCellValue().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                        }
                        else {
                            if (!freilos) {
                                auswahlklasse.WarnungBenachrichtigung("Formatierungsfehler", vName + " " + nName + " (" + extSpielerID + ")" + ": Datumszelle nicht richtig formatiert");
                                //throw new Exception("Datumszelle nicht richtig formatiert");}

                            }
                        }
                    }
                    //RanglistenpunkteZellen
                    HSSFCell einzelRPunkteZelle = row.getCell(8);
                    if (einzelRPunkteZelle != null) {
                        try {
                            rPunkte[0] = (int) einzelRPunkteZelle.getNumericCellValue();
                        } catch (Exception e) {
                            if(!freilos) {
                                auswahlklasse.WarnungBenachrichtigung("Formatierungsfehler",vName+" "+nName+" ("+extSpielerID+")"+": Ranglistenpunkte konnten nicht eingelesen werden");}
                        }
                    }
                    HSSFCell doppelRPunkteZelle = row.getCell(9);
                    if (doppelRPunkteZelle != null) {
                        try {
                            rPunkte[1] = (int) doppelRPunkteZelle.getNumericCellValue();
                        } catch (Exception e) {
                            if(!freilos) {
                                auswahlklasse.WarnungBenachrichtigung("Formatierungsfehler", vName + " " + nName + " (" + extSpielerID + ")" + ": Ranglistenpunkte konnten nicht eingelesen werden");
                            }
                        }
                    }
                    HSSFCell mixedRPunkteZelle = row.getCell(10);
                    if (mixedRPunkteZelle != null) {
                        try {
                            rPunkte[2] = (int) mixedRPunkteZelle.getNumericCellValue();
                        } catch (Exception e) {
                            if(!freilos) {
                                auswahlklasse.WarnungBenachrichtigung("Formatierungsfehler", vName + " " + nName + " (" + extSpielerID + ")" + ": Ranglistenpunkte konnten nicht eingelesen werden");
                            }
                        }
                    }


                    String vereinsname ="";
                    String verband="";
                    String extVereinsID="";
                    //Vereinszellen
                    HSSFCell vereinsnameZelle = row.getCell(3);
                    if (vereinsnameZelle != null) {
                        try {
                            vereinsname = vereinsnameZelle.getStringCellValue();
                        } catch (Exception e) {
                            try {
                                vereinsname = String.valueOf(vereinsnameZelle.getNumericCellValue());
                            } catch (Exception e1) {
                                if(!freilos) {

                                    auswahlklasse.WarnungBenachrichtigung("Formatierungsfehler",vName+" "+nName+" ("+extSpielerID+")"+": Vereinsname konnte nicht eingelesen werden");}
                            }
                        }
                    }
                    HSSFCell vereinverbandZelle = row.getCell(4);
                    if (vereinverbandZelle != null) {
                        try {
                            verband = vereinverbandZelle.getStringCellValue();
                        } catch (Exception e) {
                            try {
                                verband = String.valueOf(vereinverbandZelle.getNumericCellValue());
                            } catch (Exception e1) {
                                if(!freilos)
                                {auswahlklasse.WarnungBenachrichtigung("Formatierungsfehler",vName+" "+nName+" ("+extSpielerID+")"+": Verband konnte nicht eingelesen werden");}
                            }
                        }

                    }
                    HSSFCell extVereinsidZelle = row.getCell(13);
                    if (extVereinsidZelle != null) {
                        if (extVereinsidZelle.getCellTypeEnum() == CellType.NUMERIC) {
                            Integer extID = (int)extVereinsidZelle.getNumericCellValue();
                            extVereinsID = extID.toString();
                        } else if (extVereinsidZelle.getCellTypeEnum() == CellType.STRING){
                            extVereinsID = extVereinsidZelle.getStringCellValue();
                        }

                    }

                    Enumeration e = auswahlklasse.getVereine().keys();
                    while(e.hasMoreElements()) {
                        String id= (String) e.nextElement();
                        if (verein == null) {
                            {
                                Verein tempverein = auswahlklasse.getVereine().get(id);
                                if(tempverein.getName().equals(vereinsname)||tempverein.getExtVereinsID().equals(extVereinsID)){
                                    verein = tempverein;
                                }
                                /*if (auswahlklasse.getVereine().get(vereinsname) == null) {
                                    verein = new Verein(extVereinsID, vereinsname, verband);

                                    auswahlklasse.addVerein(verein);
                                    System.out.println("neu");
                                    auswahlklasse.getVereine().put(verein.getVereinsID(), verein);

                                    //auswahlklasse.InfoBenachrichtigung("Neuer Verein",vereinsname+"-"+verband+"("+extVereinsID+")");
                                    obs_vereine_erfolgreich.add(verein);
                                } else {
                                    System.out.println("wähle verein");
                                    verein = auswahlklasse.getVereine().get(id);
                                }*/
                            }
                        }
                    }
                    if (verein==null){
                        verein = new Verein(extVereinsID, vereinsname, verband);
                        auswahlklasse.addVerein(verein);
                        System.out.println("neu");
                        auswahlklasse.getObs_vereine_erfolgreich().add(verein);
                    }

                    Spieler neuerSpieler = new Spieler(vName,nName,gDatum,geschlecht,rPunkte,verein,extSpielerID,4);
                    for(Enumeration ee = auswahlklasse.getSpieler().elements();ee.hasMoreElements();)
                    {
                        Spieler sp = (Spieler) ee.nextElement();
                        if(sp!=null && neuerSpieler!=null) {
                            boolean b =false;
                            if(sp.getExtSpielerID()!=null&&neuerSpieler.getExtSpielerID()!=null)
                            {


                                if ((sp.getNName().equalsIgnoreCase(neuerSpieler.getNName()) && sp.getVName().equalsIgnoreCase(neuerSpieler.getVName()))||
                                        neuerSpieler.getExtSpielerID()!=""&&sp.getExtSpielerID().equalsIgnoreCase(neuerSpieler.getExtSpielerID())) {

                                    b=true;
                                }}
                            if(b&&obs_vorh!=null&&auswahlklasse.getSpielererfolgreich().get(sp.toString())==null&&auswahlklasse.getSpielerupdate().get(sp.toString())==null) {
                                //vorhandeneSpieler.add(sp);

                                if(!obs_vorh.contains(sp)) {
                                    obs_vorh.add(sp);
                                }
                                auswahlklasse.getDict_doppelte_spieler().put(neuerSpieler, FXCollections.observableArrayList(obs_vorh));
                                aktuellerSpieler=neuerSpieler;

                                //system.out.println(neuerSpieler.getExtSpielerID()+"........"+dict_doppelte_spieler.get(neuerSpieler.getExtSpielerID()));
                                //exc.pressBtn_Popup();
                            }
                        }

                    }

                    return neuerSpieler;
                }
                freilos=true;
            }}
        return null;
    }


    public static Spieler getAktuellerSpieler() {
        return aktuellerSpieler;
    }

    public static void setAktuellerSpieler(Spieler aktuellerSpieler) {
        ExcelImport.aktuellerSpieler = aktuellerSpieler;
    }

    public static Spieler getUpdateSpieler() {
        return UpdateSpieler;
    }

    public static void setUpdateSpieler(Spieler updateSpieler) {
        UpdateSpieler = updateSpieler;
    }

    public  void pressBtn_Popup() throws Exception {
        //System.out.println("test");
        boolean b = false;

        if(!b&&auswahlklasse.getDict_doppelte_spieler().size()>0)  {

            auswahlklasse.getDashboardController().setNodeSpielervorhanden();
        }
        else
        {
            auswahlklasse.getDashboardController().meldeformularImport();
        }
    }




    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // ExcelImport.getVorhandeneSpieler().clear();



    }


}