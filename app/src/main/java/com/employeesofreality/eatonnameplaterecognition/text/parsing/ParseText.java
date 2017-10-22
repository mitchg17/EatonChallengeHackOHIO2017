package com.employeesofreality.eatonnameplaterecognition.text.parsing;

import com.google.android.gms.vision.text.TextBlock;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by Mitchell on 10/21/2017.
 */

public class ParseText {

    public static String[] parseSet(HashSet<TextBlock> mixedUp) {
        for(TextBlock tb : mixedUp) {

        }

        String[] parts = new String[2];

        return parts;
    }

    public static String[] parseTextBlock(TextBlock mixedUp) {
        String mixedString = mixedUp.getValue();

        String[] parts = new String[2];

        return parts;
    }

    public static String[] parseSingleString(String myStr) {
        String[] pair = new String[2];
        return pair;
    }
}
