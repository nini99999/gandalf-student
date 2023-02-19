package com.poshist.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpUtils {
    public static void main(String[] args){
        httpPostRequest("http://127.0.0.1:8000/school/soa/carViaInfo","{\"cardCode\":\"苏FX2341\",\"viaTime\":\"2019-09-01 11:34:06\",\"viaType\":\"0\",\"gateId\":\"f9980f66-3355-4419-b845-325d83963360\",\"gateInfo\":\"chukou\",\"viaResult\":\"0\"}");
    }
    public static String httpPostRequest(String requestUrl,String outputStr){
        return httpRequest(requestUrl,"POST",outputStr);
    }
    public static String httpRequest(String requestUrl,String requestMethod,String outputStr){
        StringBuffer buffer=null;
        try{
            URL url=new URL(requestUrl);
            HttpURLConnection conn=(HttpURLConnection)url.openConnection();
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setUseCaches(false);
            conn.setRequestMethod(requestMethod);
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("Content-Length", String.valueOf(outputStr.length()));
            // 设置文件字符集:
            conn.setRequestProperty("Charset", "UTF-8");
            conn.setRequestProperty("Accept-Encoding", "gzip, deflate");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.connect();

            //往服务器端写内容 也就是发起http请求需要带的参数
            if(null!=outputStr){
                OutputStream os=conn.getOutputStream();
                os.write(outputStr.getBytes("utf-8"));
                os.flush();
                os.close();

            }

            //读取服务器端返回的内容
            InputStream is=null;
            if (conn.getResponseCode() >= 400 ) {
                is = conn.getErrorStream();
             }
            else{
                  is = conn.getInputStream();
            }
            InputStreamReader isr=new InputStreamReader(is,"utf-8");
            BufferedReader br=new BufferedReader(isr);
            buffer=new StringBuffer();
            String line=null;
            while((line=br.readLine())!=null){
                buffer.append(line);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        return buffer.toString();
    }

}
