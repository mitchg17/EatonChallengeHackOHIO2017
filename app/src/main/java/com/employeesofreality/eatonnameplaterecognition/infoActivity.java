package com.employeesofreality.eatonnameplaterecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.employeesofreality.eatonnameplaterecognition.shopping.Content;

import java.util.HashMap;

public class infoActivity extends AppCompatActivity {

    Content.Item part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        if(intent.hasExtra("ITEM"))
        {
            Log.v("infoActivity", "you a bitch");
            part = (Content.Item)(intent.getSerializableExtra("ITEM"));
        }
        else
        {
            part = new Content.Item(new HashMap<String,String>());
        }
        /*
        if(bundledItem != null && !bundledItem.isEmpty())
        {
            part = (Content.Item)(bundledItem.get("ITEM"));
        }
        else
        {
            part = null;
        }
        */
    }

    public void buttonClick(View view) {
        int id = view.getId();

        if(id == R.id.save_button) {
            HashMap<String,String> map = new HashMap<String,String>();

            for(String temp : Content.Item.fields) {
                String strID = "@id/" + temp + "_field";
                int intID = super.getResources().getIdentifier(strID,"id",super.getPackageName());
                //System.out.print(temp);
                EditText edit = (EditText)findViewById(intID);
                String input = edit.getText().toString();
                //System.out.println(input);

                map.put(temp,input);
            }
            part.values.clear();
            part.values.putAll(map);
            Content.addAnItem(part);

            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.doc_button) {

        }
    }
}
