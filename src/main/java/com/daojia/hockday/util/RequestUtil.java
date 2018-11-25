package com.daojia.hockday.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import java.net.URLDecoder;

/**
 * @author lei shuiyu
 * @date 2018/11/24 22:13
 */
public class RequestUtil {

    public static String getsentiment(String content) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        JSONObject re = new JSONObject();
        if (content == null) {
            return "中性";
        }
        re.put("text", content);
        String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?charset=UTF-8&access_token=24.e6a72dcd0a254d946e74977b11ae604b.2592000.1544673097.282335-14782508";
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(re.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = (JSONObject) JSON.parse(result);
                System.out.println(response.getString("items"));
                JSONArray array = JSON.parseArray(response.getString("items"));
                JSONObject object = (JSONObject) JSON.parse(array.getString(0));
                System.out.println(object.getString("sentiment"));
                Integer va = Integer.valueOf(object.getString("sentiment"));
                if (va != null) {
                    if (va == 1) {
                        return "中性";
                    } else if (va == 0) {
                        return "消极";
                    } else {
                        return "积极";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "中性";
    }


    public static String getMailTopic(String content) {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        JSONObject re = new JSONObject();
        if (content == null) {
            return "中性";
        }
        re.put("title", content);
        re.put("content", content);
        String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/topic?charset=UTF-8&access_token=24.e6a72dcd0a254d946e74977b11ae604b.2592000.1544673097.282335-14782508";
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(re.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                String keyWord = URLDecoder.decode(result, "utf8");

                System.out.println(keyWord);
                response = (JSONObject) JSON.parse(result);
                System.out.println();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "中性";

    }


    public static String getkeyTopic(String content) {

        CloseableHttpClient httpclient = HttpClientBuilder.create().build();
        JSONObject re = new JSONObject();
        if (content == null) {
            return "中性";
        }
        re.put("titile", content);
        re.put("content", content);
        String url = "https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?charset=UTF-8&access_token=24.e6a72dcd0a254d946e74977b11ae604b.2592000.1544673097.282335-14782508";
        HttpPost post = new HttpPost(url);
        JSONObject response = null;
        try {
            StringEntity s = new StringEntity(re.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");//发送json数据需要设置contentType
            post.setEntity(s);
            HttpResponse res = httpclient.execute(post);
            if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                String result = EntityUtils.toString(res.getEntity());// 返回json格式：
                response = (JSONObject) JSON.parse(result);
                System.out.println(response.getString("items"));
                JSONArray array = JSON.parseArray(response.getString("items"));
                JSONObject object = (JSONObject) JSON.parse(array.getString(0));
                System.out.println(object.getString("sentiment"));
                Integer va = Integer.valueOf(object.getString("sentiment"));
                if (va != null) {
                    if (va == 1) {
                        return "中性";
                    } else if (va == 0) {
                        return "消极";
                    } else {
                        return "积极";
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return "中性";
    }


    public static String getNlp(String value) {
        StringBuilder builder = new StringBuilder();
        builder.append(getsentiment(value));
        builder.append(getkeyTopic(value));
        builder.append(getMailTopic(value));
        return builder.toString();
    }


    public static void main(String[] args) {
        //RequestUtil.getNlp("");
        RequestUtil.getMailTopic("客户的二岁半的男宝宝到了中午带他困午睡觉宝宝就是不困");
    }


}
