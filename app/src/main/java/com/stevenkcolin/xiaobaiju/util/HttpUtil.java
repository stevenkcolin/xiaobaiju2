package com.stevenkcolin.xiaobaiju.util;

import android.util.Log;

import com.stevenkcolin.xiaobaiju.common.HttpCallbackListener;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.exception.ServerException;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Pengfei on 2015/12/9.
 */
public class HttpUtil {
    public static void sendHttpRequestWithCallback(final String address, final String method, final String body, final HttpCallbackListener listener) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod(method);
                    if (body != null) {
                        connection.setRequestProperty("content-type", "application/json");
                        DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                        out.writeBytes(body);
                    }
                    connection.setConnectTimeout(GeneralConstant.CONN_TIMEOUT);
                    connection.setReadTimeout(GeneralConstant.READ_TIMEOUT);
                    InputStream in = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    Object result = HttpResultUtil.checkResult(response.toString());
                    if (listener != null) {
                        listener.onFinish(result);
                    }
                } catch (Exception e) {
                    Log.e("HttpUtil", e.getMessage());
                    e.printStackTrace();
                    if (listener != null) {
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }


    public static Object sendHttpRequest(String address, String method, String body) throws ServerException, Exception {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(address);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            if (body != null) {
                connection.setRequestProperty("content-type", "application/json");
                DataOutputStream out = new DataOutputStream(connection.getOutputStream());
                out.writeBytes(body);
            }
            connection.setConnectTimeout(GeneralConstant.CONN_TIMEOUT);
            connection.setReadTimeout(GeneralConstant.READ_TIMEOUT);
            InputStream in = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            Object result = HttpResultUtil.checkResult(response.toString());
            return result;
        } catch (Exception e) {
            Log.e("HttpUtil", e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
    }
}
