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

/**
 * @author lei shuiyu
 * @date 2018/11/24 22:13
 */
public class RequestUtil {

    public static String doPost(String content) {

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


    public static void main(String[] args) {
        //  System.out.println(doPost("https://aip.baidubce.com/rpc/2.0/nlp/v1/sentiment_classify?charset=UTF-8&access_token=24.e6a72dcd0a254d946e74977b11ae604b.2592000.1544673097.282335-14782508", "高兴"));
    }


}
