package com.maven.crawler;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

public class JD {
    public static void main(String[] args) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("https://cd.jd.com/qrcode?skuId=100001118722&location=3&isWeChatStock=2").build();
        Response execute = client.newCall(request).execute();
        System.out.println(execute.body().string());



    }
}
