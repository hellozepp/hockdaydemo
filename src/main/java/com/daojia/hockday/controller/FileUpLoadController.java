package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.speech.AipSpeech;
import com.daojia.hockday.entity.ArticleDetail;
import com.daojia.hockday.enums.ErrorEnum;
import com.daojia.hockday.util.ConvertAudio;
import com.daojia.hockday.util.DateUtil;
import com.daojia.hockday.util.ResultDto;
import org.apache.commons.lang3.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.*;
import java.util.Date;
import java.util.List;

/**
 * @author by Dawei on 2018/11/11.
 */
@RestController
public class FileUpLoadController {
    private static final String APP_ID = "14759966";
    private static final String API_KEY = "GXBETGK8ZMoc7QUOEBlGKrZW";
    private static final String SECRET_KEY = "GSyzff82Iq83qyjde3bkAVDaDXa3Zh9D";
    private static final Logger logger = LoggerFactory.getLogger(FileUpLoadController.class);

    @PostMapping(value = "/upload", produces = "multipart/form-data")
    public String upload(@RequestParam(value = "file", required = false) CommonsMultipartFile file) {
        ResultDto<List<ArticleDetail>> resultDto = new ResultDto<>();
        ModelAndView mo = new ModelAndView();
        if (file != null) {
            String originalFilename = file.getOriginalFilename();
            String newFileName = "";
            String distName = "";
            if (StringUtils.isBlank(originalFilename) || originalFilename.lastIndexOf(".") < 0) {
                logger.error("[upload]文件名或文件后缀不能为空!");
                resultDto.setCode(ErrorEnum.ERROR.getCode());
                resultDto.setCodeMsg("文件名或文件后缀不能为空!");
            } else {
                String endStr = originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
                String nameCut = originalFilename.substring(0, originalFilename.lastIndexOf(".")) + "_" + DateUtil.simple4(new Date());
                newFileName = nameCut + "." + endStr;
                distName = nameCut + ".wav";

                mo.addObject("filename", newFileName);
                mo.addObject("size", file.getSize() / (1024.0 * 1024) + "M");
                String systemRealPath = "static/file/";
                File path = new File(systemRealPath);
                if (!path.exists()) {
                    boolean mkdirs = path.mkdirs();
                    logger.info("path:" + path + ", result : mkdirs={}", mkdirs);
                }
                File upFile = new File(systemRealPath + newFileName);
                try {
                    file.transferTo(upFile);
                    JSONObject jsonObject = callAipSpeech(upFile, new File(systemRealPath + distName));
                    resultDto.setCode(ErrorEnum.ERROR.getCode());
                    if (jsonObject.has("error_msg")) {
                        resultDto.setCodeMsg((String) jsonObject.get("error_msg"));
                    }
                    if (jsonObject.has("err_msg")) {
                        resultDto.setCodeMsg((String) jsonObject.get("err_msg")); //success.
                    }
                    if (jsonObject.has("error_code")) {
                        resultDto.setCodeMsg((String) jsonObject.get("error_code"));
                    }
                    if (jsonObject.has("err_code")) {
                        resultDto.setCodeMsg((String) jsonObject.get("err_code")); //0
                    }
                    StringBuilder msg = new StringBuilder();
                    boolean first = true;
                    if (jsonObject.has("result")) {
                        JSONArray result = (JSONArray) jsonObject.get("result");
                        for (Object o : result) {
                            if (o != null && StringUtils.isNotBlank((String) o)) {
                                if (first) {
                                    first = false;
                                    msg = new StringBuilder((String) o);
                                    continue;
                                }
                                msg.append(",").append(o);
                            }
                        }
                        resultDto.setCodeMsg(msg.toString());
                        resultDto.setCode(ErrorEnum.SUCCESS.getCode());
                    }
                } catch (IOException e) {
                    logger.error("[upload] 创建文件流出错!e:{}", e);
                    resultDto.setCode(ErrorEnum.ERROR.getCode());
                    resultDto.setCodeMsg("创建文件流出错!");
                }
            }
        } else {
            logger.error("[upload]未上传语音文件!");
            resultDto.setCode(ErrorEnum.ERROR.getCode());
            resultDto.setCodeMsg("[upload] 未上传语音文件!");
        }
        return JSON.toJSONString(resultDto);
    }

    private JSONObject callAipSpeech(File source, File dist) {
        // 初始化一个AipSpeech
        AipSpeech client = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        // 可选：设置网络连接参数
        client.setConnectionTimeoutInMillis(2000);
        client.setSocketTimeoutInMillis(60000);

        // 可选：设置代理服务器地址, http和socket二选一，或者均不设置
        int proxy_port = 28085;
//        client.setHttpProxy("proxy_host", proxy_port);  // 设置http代理
//        client.setSocketProxy("proxy_host", proxy_port);  // 设置socket代理

        // 可选：设置log4j日志输出格式，若不设置，则使用默认配置
        // 也可以直接通过jvm启动参数设置此环境变量
//        System.setProperty("aip.log4j.conf", "log4j.properties");

        // 调用接口
        ConvertAudio.audioToWav(source, dist);
        try {
            InputStream input = new FileInputStream(dist);
            byte[] byt = new byte[input.available()];
            input.read(byt);
            JSONObject res = client.asr(byt, "wav", 8000, null);
            logger.info("[callAipSpeech] res:" + res.toString());
            return res;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONObject();
        } catch (Exception e) {
            return new JSONObject();
        }
    }

}
