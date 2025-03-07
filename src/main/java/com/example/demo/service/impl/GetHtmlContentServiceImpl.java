package com.example.demo.service.impl;
import com.example.demo.service.GetHtmlContentService;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@Service
public class GetHtmlContentServiceImpl implements GetHtmlContentService {
    @Override
    public String getHtmlContent(String url) {
        URL url1 = null;
        try {
            url1 = new URL(url);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        StringBuffer contentBuffer = new StringBuffer();

        int responseCode = -1;
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url1.openConnection();
            /*
             * 此处的urlConnection对象实际上是根据URL的
             *  请求协议(此处是http)生成的URLConnection类
             *  的子类HttpURLConnection,故此处最好将其转化
             *  为HttpURLConnection类型的对象,以便用到
             *  HttpURLConnection更多的API
             * */
            con.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/88.0.4324.190 Safari/537.36");
            con.setConnectTimeout(60000);
            con.setReadTimeout(60000);
            // 获得网页返回信息码
            responseCode = con.getResponseCode();
            if (responseCode == -1) {
                System.out.println(url.toString() + " : connection is failure...");
                con.disconnect();
                return null;
            }
            if (responseCode >= 400) // 请求失败
            {
                System.out.println("请求失败:get response code: " + responseCode);
                con.disconnect();
                return null;
            }

            InputStream inStr = con.getInputStream();
            InputStreamReader istreamReader = new InputStreamReader(inStr, "utf-8");
            BufferedReader buffStr = new BufferedReader(istreamReader);

            String str = null;
            while ((str = buffStr.readLine()) != null)
                contentBuffer.append(str);
            inStr.close();
        } catch (IOException e) {
            e.printStackTrace();
            contentBuffer = null;
            System.out.println("error: " + url.toString());
        } finally {
            con.disconnect();
        }
        return contentBuffer.toString();
    }
}

