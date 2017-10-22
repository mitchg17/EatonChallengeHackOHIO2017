package com.employeesofreality.eatonnameplaterecognition.text.parsing;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by Mitchell on 10/21/2017.
 */

public class Tokenizer {
    private ArrayList<String> tokens;
    private static Set<Character> skips;

    static{
       skips.add(' ');
        skips.add(',');
        skips.add(':');
        skips.add('\n');
        skips.add('\t');
    }

    public Tokenizer(String text){
        int i = 0;
        while(i < text.length()){
            boolean skip = skips.contains(text.charAt(i));
            String token = "";
            while(i != text.length() && skips.contains(text.charAt(i)) == skip){
                token += text.charAt(i);
                i++;
            }
            if(!skips.contains(token.charAt(0))){
                tokens.add(token);
            }
        }
    }

    public ArrayList<String> getTokens(){
        return tokens;
    }



}
