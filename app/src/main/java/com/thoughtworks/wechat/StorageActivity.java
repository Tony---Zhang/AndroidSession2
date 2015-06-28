package com.thoughtworks.wechat;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.charset.Charset;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class StorageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage);
        ButterKnife.inject(this);
    }

    @OnClick(R.id.storage_read_file)
    void readFile() {
        String filename = "myfile";
        try {
            byte[] buf = new byte[16];
            FileInputStream inputStream = openFileInput(filename);
            while (inputStream.read(buf) != -1) {
                Toast.makeText(StorageActivity.this, new String(buf, Charset.forName("UTF-8")), Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.storage_write_file)
    void writeFile() {
        String filename = "myfile";
        String string = "Hello world!";
        FileOutputStream outputStream;

        try {
            outputStream = openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(string.getBytes());
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.storage_read_prefs)
    void readPrefs() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        String name = sharedPref.getString(Constants.PREFS_USER_NAME, "");
        String uToken = sharedPref.getString(Constants.PREFS_USER_TOKEN, "");

        new AlertDialog.Builder(this)
                .setTitle("Name: " + name)
                .setMessage("U Token: " + uToken)
                .setNegativeButton(R.string.btn_cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create().show();

    }

    @OnClick(R.id.storage_write_prefs)
    void writePrefs() {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(Constants.PREFS_USER_NAME, "ZS");
        editor.putString(Constants.PREFS_USER_TOKEN, "handsome");
        editor.apply();

        Toast.makeText(StorageActivity.this, "Saved prefs successful!", Toast.LENGTH_SHORT).show();
    }

}
