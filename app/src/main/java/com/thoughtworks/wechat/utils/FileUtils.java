package com.thoughtworks.wechat.utils;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String readAssetTextFile(Context context, String fileName) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        InputStream inputStream = context.getResources().getAssets().open(fileName);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;

        while ((line = reader.readLine()) != null) {
            stringBuffer.append(line);
            stringBuffer.append('\n');
        }
        return stringBuffer.toString();
    }
}
