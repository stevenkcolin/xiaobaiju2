package com.stevenkcolin.xiaobaiju.util;

import android.content.Context;
import android.util.Log;

import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Pengfei on 2015/12/10.
 */
public class FileUtil {
    public static void write(Context ctx, String filename, String text) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
            out = ctx.openFileOutput(filename, Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(text);
        } catch (IOException e) {
            Log.e("FileUtil", e.getMessage());
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                Log.e("FileUtil", e.getMessage());
            }
        }
    }

    public static List<String> read(Context ctx, String filename) {
        FileInputStream in = null;
        BufferedReader reader = null;
        List<String> result = new LinkedList<>();
        try {
            in = ctx.openFileInput(filename);
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while ((line = reader.readLine()) != null) {
                result.add(line);
            }
        } catch (IOException e) {
            Log.e("FileUtil", e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("FileUtil", e.getMessage());
                }
            }
        }
        return result;
    }

    public static byte[] downloand(String filePath) throws Exception {
        String url = GeneralConstant.SERVER_URL + GeneralConstant.ATTACHMENT_URI + GeneralConstant.ATTACHMENT_DOWNLOAD_URI + "?filepath=" + filePath;
        byte[] bin = HttpUtil.sendHttpRequestWithBin(url, "GET", null);
        return bin;
    }
}
