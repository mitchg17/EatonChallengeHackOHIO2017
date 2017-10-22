package com.employeesofreality.eatonnameplaterecognition.text.parsing;

import android.util.Log;

import com.employeesofreality.eatonnameplaterecognition.shopping.Content;
import com.employeesofreality.eatonnameplaterecognition.ui.camera.OcrGraphic;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Mitchell on 10/21/2017.
 */

public class ParseText {

    private static HashSet<String> keyWords = new HashSet<String>(Arrays.asList("BRAND", "CATALOG", "CAT", "CAT.", "STYLE","STY","TYL",
            "GENERAL", "GO#", "GOH","GO" ,"GOR", "PO#", "POH", "RANGE","ANG","ANGE", "VOLTAGE","VO","OLT", "VOLTS", "SERIAL","SER",
            "SERIAL#", "UNIT", "UNIT#", "MANUFACTURING", "MFG", "DRAWING","SERIALH","S/N","SM","SIN"));

    private static String[] fat = {"Brand", "Catalog Number", "Style Number", "Order",
            "General Order", "GO#", "PO#", "Range", "Voltage", "Serial Number", "Serial no.",
            "Serial#", "Serial", "Unit Number", "Unit no.", "Unit#", "Unit", "Manufacturing Date", "Mfg Date", "Drawing Ref"};


    public static HashMap<String, String> parseArray(ArrayList<OcrGraphic> sortedTextBlocks) {
        HashMap<String, String> values = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < sortedTextBlocks.size(); i++){
            sb.append(sortedTextBlocks.get(i).getTextBlock().getValue() + " ");
        }
        String fullText = sb.toString();

        String address = parseAddress(fullText);
        values.put("PhysicalLocation", address);

        Tokenizer tokenizer = new Tokenizer(fullText);

//        for(int i = 0; i < sortedTextBlocks.size(); i++) {
//            OcrGraphic ocrg = sortedTextBlocks.get(i);
//            TextBlock tb = ocrg.getTextBlock();
//            float brandChance = ((float)(sortedTextBlocks.get(i).getTextBlock().getCornerPoints()[0].y - sortedTextBlocks.get(0).getTextBlock().getCornerPoints()[0].y)/
//                    ((float)sortedTextBlocks.get(sortedTextBlocks.size()-1).getTextBlock().getCornerPoints()[2].y));
//            parseTextBlock(tb, values, brandChance);
//        }

        HashSet<String> s1 = new HashSet<>(Arrays.asList("CATALOG", "CAT", "CAT.", "STYLE","STY","TYL"));
        HashSet<String> s2 = new HashSet<>(Arrays.asList("GENERAL", "GO#", "GOH","GO" ,"GOR", "PO#", "POH"));
        HashSet<String> s3 = new HashSet<>(Arrays.asList("RANGE","ANG","ANGE"));
        HashSet<String> s4 = new HashSet<>(Arrays.asList("VOLTAGE","VO","OLT", "VOLTS"));
        HashSet<String> s5 = new HashSet<>(Arrays.asList("SERIAL","SER", "SERIAL#","SERIALH","S/N","SM","SIN"));
        HashSet<String> s6 = new HashSet<>(Arrays.asList("UNIT", "UNIT#"));
        HashSet<String> s7 = new HashSet<>(Arrays.asList("MANUFACTURING", "MFG"));
        HashSet<String> s8 = new HashSet<>(Arrays.asList("DRAWING"));

        HashMap<String,HashSet<String>> categories = new HashMap<String,HashSet<String>>();
        categories.put("CatalogNumber",s1);
        categories.put("OrderNumber",s2);
        categories.put("Range",s3);
        categories.put("Voltage",s4);
        categories.put("SerialNumber",s5);
        categories.put("UnitNumber",s6);
        categories.put("DrawingNumber",s8);

        HashSet<String> subsids = new HashSet<>();
        subsids.add("UNIVAR");
        subsids.add("UNIPUMP");
        subsids.add("AUTOVAR");

        values.put("Brand", "EATON");

        ArrayList<String>  tokens = tokenizer.getTokens();
        for (int i = 0; i < tokens.size(); i++){
            for (String sub: subsids){
                if(tokens.get(i).toUpperCase().contains(sub)){
                    if(values.containsKey("Brand") && !values.get("Brand").equalsIgnoreCase(sub)){
                        values.remove("Brand");
                    }
                    if(!values.containsKey("Brand")){
                        values.put("Brand", sub);
                    }
                }
            }

            for(String cat: categories.keySet()){
                for(String keyW : categories.get(cat)) {
                    if (tokens.get(i).toUpperCase().endsWith(keyW)) {
                        for (String str : categories.keySet()) {
                            HashSet<String> temp = categories.get(str);
                            if (temp.contains(keyW)) {
                                if (tokens.get(i + 1).equalsIgnoreCase("number") || tokens.get(i + 1).equalsIgnoreCase("no") || tokens.get(i + 1).equalsIgnoreCase("no.")) {
                                    i++;
                                    if (str.equalsIgnoreCase("OrderNumber")) {
                                        i++;
                                    }
                                }
                                i++;
                                values.put(str, tokens.get(i));
                            }
                        }
                    }
                }
           /*if(keyWords.contains(tokens.get(i).toUpperCase())){
                switch (tokens.get(i).toUpperCase()){
                    case "GENERAL":
                    case "GO":
                    case "GOR":
                    case "GO#":
                    case "GOH":
                    case "PO#":
                    case "POH":
                        if(tokens.get(i+1).equalsIgnoreCase("order")){
                            i++;
                        }
                        if(tokens.get(i+1).equalsIgnoreCase("number")){
                            i++;
                        }
                        i++;
                        //if(tokens.get(i).matches("([(A-z0-9)]){10}-([0-9]){3}")) {
                        //}
                        values.put("OrderNumber", tokens.get(i));
                        break;
                    case "CATALOG":
                    case "CAT":
                    case "CAT.":
                    case "STYLE":
                    case "STY":
                    case "TYL":
                        if(tokens.get(i+1).equalsIgnoreCase("number") || tokens.get(i+1).equalsIgnoreCase("no") || tokens.get(i+1).equalsIgnoreCase("no.")){
                            i++;
                        }
                        i++;
                        values.put("CatalogNumber", tokens.get(i));
                        break;
                    default:
                        break;
                    case "ANG":
                    case "ANGE":
                    case "RANGE":
                        i++;
                        values.put("Range", tokens.get(i));
                        break;
                    case "VO":
                    case "OLT":
                    case "VOLTAGE":
                    case "VOLTS":
                        i++;
                        values.put("Voltage", tokens.get(i));
                        break;
                    case "SERIAL":
                    case "SERIALH":
                    case "SER":
                    case "S/N":
                    case "SM":
                    case "SIN":
                    case "SERIAL#":
                        if(tokens.get(i+1).equalsIgnoreCase("number") || tokens.get(i+1).equalsIgnoreCase("no") || tokens.get(i+1).equalsIgnoreCase("no.")){
                            i++;
                        }
                        i++;
                        values.put("SerialNumber", tokens.get(i));
                        break;
                }
                */
            }
        }
        return values;
    }

    private static String parseAddress(String text) {
        Matcher matcher = Pattern.compile("(\\d+\\s[A-z]+\\s[A-z]+[,]+\\s+[A-z]+\\s+[A-z]+\\s+\\d+)").matcher(text);
        boolean found = matcher.find();
        if(found) {
            return matcher.group(1);
        }
        return "";
    }

    private static Map<String, String> parseTextBlock(TextBlock mixedUp, Map<String, String> mymap, float brandChance) {
        String text = mixedUp.getValue();
        boolean isBrand = false;
        if(brandChance < 0.3) {
            if(text.contains("\'") || text.contains("\"")) {
                isBrand = true;
            }
        }
        if(mymap.containsKey("Brand")) {
            isBrand = false;
        }

        if(isBrand) {
            text = text.replace("\'","");
            text = text.replace("\"","");
            mymap.put("Brand", text);
        }
        return mymap;
    }

    public static Map<String, String> parseSingleString(String myStr) {

        Map<String, String> pair = new HashMap<String, String>();

        return pair;
    }
}
