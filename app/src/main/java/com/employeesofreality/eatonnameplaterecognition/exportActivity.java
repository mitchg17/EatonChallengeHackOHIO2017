package com.employeesofreality.eatonnameplaterecognition;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.employeesofreality.eatonnameplaterecognition.shopping.Content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class exportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
    }

    public void buttonClicked(View view) throws IOException {
        int id = view.getId();

        if(id == R.id.export_button)
        {

            File output = new File(getFilesDir(), "order.xml");
            Log.v("FUCK THIS SHIT",output.getAbsolutePath());

            BufferedWriter out = null;
            try {
                out = new BufferedWriter(new FileWriter(output));
            } catch (IOException e) {
                e.printStackTrace();
            }

            if(out != null) {
                Log.v("","3333333333333333333333333333333333333333333333333333333333333");
                String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<html>\n<head>" +
                        "\n<title>Order</title>\n</head>\n<body>\n<h1>Order</h1><p>";

                try {
                    out.write(header,0,header.length());
                    Log.v("","1111111111111111111111111111111111111111111111111111111111111111111");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                /*
                for(Content.Item temp : Content.ITEMS) {
                    String element = processItem(temp);
                    try {
                        out.write(element,0,element.length());
                        Log.v("","22222222222222222222222222222222222222222222222222222222222");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                */
                String footer = "\n</p>\n</body>\n</html>";
                try {
                    out.write(footer,0,footer.length());
                    Log.v("","22222222222222222222222222222222222222222222222222222222222");
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            Intent intent =  new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        System.out.print("");
    }


    public static String processItem(Content.Item item) {
        String str = "<item ";

        for(String temp : Content.Item.fields) {
            if(!item.values.get(temp).equals("")) {
                str += temp + "=\""+item.values.get(temp)+"\"\n";
            }
        }

        str += "/>";
        return str;
    }
}
