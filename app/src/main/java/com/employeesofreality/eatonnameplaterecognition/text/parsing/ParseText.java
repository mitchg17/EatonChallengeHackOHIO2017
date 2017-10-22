package com.employeesofreality.eatonnameplaterecognition.text.parsing;

import com.employeesofreality.eatonnameplaterecognition.ui.camera.OcrGraphic;
import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mitchell on 10/21/2017.
 */

public class ParseText {

    private static String[] keyWords = {"Brand", "Catalog Number", "Style Number", "Order",
            "General Order", "GO#", "PO#", "Range", "Voltage", "Serial Number", "Serial no.",
            "Serial#", "Serial", "Unit Number", "Unit no.", "Unit#", "Unit", "Manufacturing Date", "Mfg Date", "Drawing Ref"};


    public static Map<String, String> parseArray(ArrayList<OcrGraphic> mixedUp) {
        Map<String, String> pairs = new HashMap<String, String>();
        for(int i = 0; i < mixedUp.size(); i++) {
            OcrGraphic ocrg = mixedUp.get(i);
            TextBlock tb = ocrg.getTextBlock();
            float brandChance = ((float)(mixedUp.get(i).getTextBlock().getCornerPoints()[0].y - mixedUp.get(0).getTextBlock().getCornerPoints()[0].y)/
                    ((float)mixedUp.get(mixedUp.size()-1).getTextBlock().getCornerPoints()[2].y));
            parseTextBlock(tb, pairs, brandChance);
        }
        return pairs;
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
        //TODO: Get each word and check it against keyWords.
        String[] words = text.split(" ");
        for(String word : words) {
            if(word.matches("([(A-z0-9)]){10}-([0-9]){3}")) {
                mymap.put("Order Number", word);
            }
        }

        for(int i = 0; i < keyWords.length; i++) {
            String word = keyWords[i];
            if(text.contains(word)) {

            }
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
