package com.employeesofreality.eatonnameplaterecognition;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.employeesofreality.eatonnameplaterecognition.shopping.Content;

public class infoActivity extends AppCompatActivity {

    Content.Item part;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        Intent intent = getIntent();
        Bundle bundledItem = intent.getExtras();

        if(bundledItem != null && !bundledItem.isEmpty())
        {
            part = (Content.Item)(bundledItem.get("ITEM"));
        }
        else
        {
            part = null;
        }
    }
}
