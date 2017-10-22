package com.employeesofreality.eatonnameplaterecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

            }
        }
        else if(id == R.id.doc_button) {

        }
    }
}
