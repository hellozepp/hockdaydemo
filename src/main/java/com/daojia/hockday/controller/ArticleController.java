package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.speech.AipSpeech;
import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.service.ArticleService;
import com.daojia.hockday.util.ResultDto;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;

/**
 * @author by Dawei on 2018/11/11.
 */
@RestController
public class ArticleController {


    @Resource
    private ArticleService articleService;

    /**
     * 获取 发布的列表
     *
     * @param type 列表类型 1:最热 2：最新
     */
    @GetMapping(value = "/get/article/list")
    public String getArticleList(Integer type) {
        ResultDto<List<ArticleDetail>> resultDto = new ResultDto<>();
        /* 最热*/
        if (type == 1) {

        } else if (type == 2) {  //最近

        }

        return JSON.toJSONString(resultDto);
    }

    @RequestMapping(value = "/test", method = RequestMethod.POST, produces = "multipart/form-data")
    public String getArticleList(Integer type, String upload, @RequestParam(required = false) CommonsMultipartFile prizeFile, HttpServletRequest req, HttpServletResponse resp, MultipartFile file) {
        ResultDto<List<ArticleDetail>> resultDto = new ResultDto<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(req.getInputStream()));
            final StringBuffer buffer = new StringBuffer();

            String lines;
            while ((lines = reader.readLine()) != null) {
                buffer.append(lines);
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (prizeFile == null) {
            return "";
        }
        ModelAndView mo = new ModelAndView();
        mo.addObject("filename", file.getOriginalFilename());
        mo.addObject("size", file.getSize() / (1024.0 * 1024) + "M");
        String originalFilename = prizeFile.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString() + originalFilename;

        /*String systemRealPath = SystemHelper.getSystemRealPath();*/
        String systemRealPath = "http://10.253.105.108:8080/static/img/";

        String allPath = "http://10.253.105.108:8080/static/img/" + newFileName;
        File upFile = new File(systemRealPath, newFileName);
        if (!upFile.exists()) {
            boolean mkdirs = upFile.mkdirs();
//            logger.info(" result : mkdirs={}", mkdirs);
        }
        try {
            //直接的文件复制
            prizeFile.transferTo(upFile);
        } catch (IOException e) {
            e.printStackTrace();
            allPath = "";
        }

        /* 最热*/
//        System.out.println(data);
        callBaiduyvyin();
        return JSON.toJSONString(resultDto);
    }

    public static final String APP_ID = "14759966";
    public static final String API_KEY = "GXBETGK8ZMoc7QUOEBlGKrZW";
    public static final String SECRET_KEY = "GSyzff82Iq83qyjde3bkAVDaDXa3Zh9D";
    private static int proxy_port = 28085;

    public void callBaiduyvyin() {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
        System.setProperty("aip.log4j.conf", "/opt/projects/iotest/src2/baidu/log4j.properties");

        // 调用接口
        JSONObject res = client.asr("test.pcm", "pcm", 16000, null);
        System.out.println(res.toString(2));

    }

    /**
     * 获取 发布的列表
     *
     * @param articleDetail 列表类型 1:最热 2：最新
     */
    @PutMapping(value = "/put/article")
    public String getIndex(ArticleDetail articleDetail) {

        Integer integer = articleService.addArticleDetail(articleDetail);

        return JSON.toJSONString(integer);
    }


}
