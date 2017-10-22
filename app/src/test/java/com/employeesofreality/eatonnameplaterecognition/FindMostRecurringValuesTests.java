package com.employeesofreality.eatonnameplaterecognition;

import org.junit.Test;

import java.util.HashMap;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

/**
 * Created by james on 10/21/17.
 */

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class FindMostRecurringValuesTests {
    @Test
    public void testOneKeyOneAndTwoCounts() throws Exception {
        HashSet<HashMap<String, String>> textSet = new HashSet<>();

        HashMap<String, String> set1 = new HashMap<String, String>();
        set1.put("Brand", "green");

        HashMap<String, String> set2 = new HashMap<String, String>();
        set2.put("Brand", "blue");

        HashMap<String, String> set3 = new HashMap<String, String>();
        set3.put("Brand", "blue");

        textSet.add(set1);
        textSet.add(set2);
        textSet.add(set3);

        HashMap<String, String> output = new HashMap<>();

        HashMap<String, String> expectedOutput = new HashMap<>();
        expectedOutput.put("Brand", "blue");

        OcrCaptureFragment.findMostRecurringValues(textSet, output);

        assertEquals(expectedOutput, output);
    }
}