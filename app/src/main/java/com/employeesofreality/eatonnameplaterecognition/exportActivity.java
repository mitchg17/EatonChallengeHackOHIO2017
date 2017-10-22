package com.employeesofreality.eatonnameplaterecognition;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.employeesofreality.eatonnameplaterecognition.shopping.Content;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class exportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_export);
    }

    public void buttonClicked(View view) throws IOException {
        int id = view.getId();

        if(id == R.id.export_button) {
            // Check for the camera permission before accessing the camera.  If the
            // permission is not granted yet, request permission.
            int rc = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (rc == PackageManager.PERMISSION_GRANTED) {
                EditText fileNameEditText = (EditText) findViewById(R.id.export_file_name);
                File output = new File(Environment.getExternalStoragePublicDirectory("/ExportedNameplates"), fileNameEditText.getText().toString());
                BufferedWriter out = null;

                try {
                    out = new BufferedWriter(new FileWriter(output));
                } catch (IOException e) {
                    e.printStackTrace();
                }

                if (out != null) {
                    String header = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n<html>\n<head>" +
                            "\n<title>" + fileNameEditText.getText().toString() + "</title>\n</head>\n<body>\n<h1>" + fileNameEditText.getText().toString() + "</h1><p>";
                    try {
                        out.write(header, 0, header.length());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                for(Content.Item temp : Content.ITEMS) {
                    String element = processItem(temp);
                    try {
                        out.write(element,0,element.length());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                    String footer = "\n</p>\n</body>\n</html>";
                    try {
                        out.write(footer, 0, footer.length());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    try {
                        out.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }


                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            } else {
                requestWritePermission();
            }
        }
    }

    private void requestWritePermission() {
        Log.w("exportActivity", "Camera permission is not granted. Requesting permission");

        final String[] permissions = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};

        if (!ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(this, permissions, 2);
            return;
        }

        final Activity thisActivity = this;

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(thisActivity, permissions, 2);
            }
        };
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
