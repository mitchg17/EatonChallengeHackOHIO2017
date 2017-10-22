package com.employeesofreality.eatonnameplaterecognition.text.parsing;

import android.util.Log;

import com.employeesofreality.eatonnameplaterecognition.ui.camera.OcrGraphic;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Mitchell on 10/21/2017.
 */

public class ParseText {

    private static HashSet<String> keyWords = new HashSet<String>(Arrays.asList("BRAND", "CATALOG", "CAT", "CAT.", "STYLE",
            "GENERAL", "GO#", "GOH","GO", "PO#", "POH", "RANGE","ANG","ANGE", "VOLTAGE","VO","OLT", "SERIAL","SER",
            "SERIAL#", "UNIT", "UNIT#", "MANUFACTURING", "MFG", "DRAWING","SERIALH","S/N","SM"));

    private static String[] fat = {"Brand", "Catalog Number", "Style Number", "Order",
            "General Order", "GO#", "PO#", "Range", "Voltage", "Serial Number", "Serial no.",
            "Serial#", "Serial", "Unit Number", "Unit no.", "Unit#", "Unit", "Manufacturing Date", "Mfg Date", "Drawing Ref"};


    public static HashMap<String, String> parseArray(ArrayList<OcrGraphic> mixedUp) {
        HashMap<String, String> values = new HashMap<String, String>();
        StringBuilder sb = new StringBuilder();
        for(int i = 0;i < mixedUp.size(); i++){
            sb.append(mixedUp.get(i).getTextBlock().getValue() + " ");
        }
        String fullText = sb.toString();
        Tokenizer tokenizer = new Tokenizer(fullText);
        for(int i = 0; i < mixedUp.size(); i++) {
            OcrGraphic ocrg = mixedUp.get(i);
            TextBlock tb = ocrg.getTextBlock();
            float brandChance = ((float)(mixedUp.get(i).getTextBlock().getCornerPoints()[0].y - mixedUp.get(0).getTextBlock().getCornerPoints()[0].y)/
                    ((float)mixedUp.get(mixedUp.size()-1).getTextBlock().getCornerPoints()[2].y));
            parseTextBlock(tb, values, brandChance);
        }

        ArrayList<String>  tokens = tokenizer.getTokens();
        for (int i = 0; i < tokens.size(); i++){
            if(keyWords.contains(tokens.get(i).toUpperCase())){
                switch (tokens.get(i).toUpperCase()){
                    case "GENERAL":
                        if(tokens.get(i+1).equalsIgnoreCase("order")){
                            i++;
                        }
                        if(tokens.get(i+1).equalsIgnoreCase("number")){
                            i++;
                        }
                    case "GO":
                    case "GO#":
                    case "GOH":
                    case "PO#":
                    case "POH":
                        i++;
                        if(tokens.get(i).matches("([(A-z0-9)]){10}-([0-9]){3}")) {
                            values.put("OrderNumber", tokens.get(i));
                        }
                        break;
                    case "CATALOG":
                    case "CAT":
                    case "CAT.":
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
                        values.put("Range", tokens.get(++i));
                        break;
                    case "VO":
                    case "OLT":
                    case "VOLTAGE":
                    case "VOLTS":
                        values.put("Voltage", tokens.get(++i));
                        break;
                    case "SERIAL":
                        if(tokens.get(i+1).equalsIgnoreCase("number") || tokens.get(i+1).equalsIgnoreCase("no") || tokens.get(i+1).equalsIgnoreCase("no.")){
                            i++;
                        }
                    case "SERIALH":
                    case "SER":
                    case "S/N":
                    case "SM":
                    case "SERIAL#":
                        values.put("Serial #", tokens.get(++i));
                        break;
                }
            }
        }
        return values;
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
            mymap.put("Brand", text);
        }
        return mymap;
    }

    public static Map<String, String> parseSingleString(String myStr) {

        Map<String, String> pair = new HashMap<String, String>();

        return pair;
    }
}
