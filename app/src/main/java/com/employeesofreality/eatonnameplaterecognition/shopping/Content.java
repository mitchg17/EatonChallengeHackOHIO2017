package com.employeesofreality.eatonnameplaterecognition.shopping;

import com.employeesofreality.eatonnameplaterecognition.R;

import java.io.Serializable;
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
    public static List<Item> ITEMS = new ArrayList<Item>();

    /**
     * A map of sample items, by ID.
     */
    public static final Map<String, Item> ITEM_MAP = new HashMap<String, Item>();

    private static final int COUNT = 10;

    public static void removeAnItem(Item item) {
        ITEMS.remove(item);
        ITEM_MAP.remove(Integer.toString(item.hashCode()));
    }


    public static void addAnItem(Item item) {
        ITEMS.add(item);
        ITEM_MAP.put(Integer.toString(item.hashCode()), item);
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
    public static class Item implements Serializable{
        public static final String[] fields = {"Brand","CatalogNumber","OrderNumber","Range", "Voltage", "SerialNumber","UnitNumber","ManufacturingDate","ManufacturingLocation","PhysicalLocation","DrawingNumber"};
        public final boolean isChecked;
        public HashMap<String,String> values;

        public String id;
        public String content;

        public Item(HashMap<String,String> map) {
            isChecked = false;
            values = new HashMap<String,String>();

            for(String temp : fields) {
                if(map.containsKey(temp)) {
                    values.put(temp,map.get(temp));
                }
                else
                {
                    values.put(temp,"");
                }
            }
            id = values.get("Brand");
            content = values.get("OrderNumber");
        }



        @Override
        public String toString() {
            return "TODO";
        }
    }
}
