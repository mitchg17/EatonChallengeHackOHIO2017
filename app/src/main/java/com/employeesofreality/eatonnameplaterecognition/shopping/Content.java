package com.employeesofreality.eatonnameplaterecognition.shopping;

import com.employeesofreality.eatonnameplaterecognition.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class Content {

    /**
     * An array of sample items.
     */
    public static final List<Item> ITEMS = new ArrayList<Item>();

    /**
     * A map of sample items, by ID.
     */
    public static final Map<String, Item> ITEM_MAP = new HashMap<String, Item>();

    private static final int COUNT = 10;


    private static void addItem(Item item) {
        ITEMS.add(item);
        //ITEM_MAP.put(item.id, item);
    }

    private static Item createItem(HashMap<String,String> map) {
        return new Item(map);
    }

    private static String makeDetails(int position) {
        StringBuilder builder = new StringBuilder();
        builder.append("Details about Item: ").append(position);
        for (int i = 0; i < position; i++) {
            builder.append("\nMore details information here.");
        }
        return builder.toString();
    }

    /**
     * An item representing a piece of content.
     */
    public static class Item {
        static final String[] fields = {"Brand","Catalog #","Order #","Range", "Voltage", "Serial #","Unit #","Manufacturing Date","Manufacturing Location","Physical Location","Drawing #"};
        public final boolean isChecked;
        HashMap<String,String> values;

        public Item(HashMap<String,String> map) {
            isChecked = false;

            for(String temp : fields) {
                if(map.containsKey(temp)) {
                    values.put(temp,map.get(temp));
                }
                else
                {
                    values.put(temp,"");
                }
            }
        }

        @Override
        public String toString() {
            return "TODO";
        }
    }
}
