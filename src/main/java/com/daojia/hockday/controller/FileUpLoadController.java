package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.baidu.aip.speech.AipSpeech;
import com.daojia.hockday.enums.ErrorEnum;
import com.daojia.hockday.util.M4aToWavUtil;
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

import java.io.*;
import java.util.UUID;

/**
 * @author by Dawei on 2018/11/11.
 */
@RestController
public class FileUpLoadController {
    private static final String APP_ID = "14759966";
    private static final String API_KEY = "GXBETGK8ZMoc7QUOEBlGKrZW";
    private static final String SECRET_KEY = "GSyzff82Iq83qyjde3bkAVDaDXa3Zh9D";


    // 初始化一个AipSpeech
    private AipSpeech aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);

    // 可选：设置网络连接参数
    {
        aipSpeech.setConnectionTimeoutInMillis(2000);
        aipSpeech.setSocketTimeoutInMillis(60000);
    }

    private static final Logger logger = LoggerFactory.getLogger(FileUpLoadController.class);

    @PostMapping(value = "/upload", produces = "multipart/form-data")
    public String upload(@RequestParam(value = "file", required = false) CommonsMultipartFile file) {
        logger.info(" Upload file ........");
        ResultDto<String> resultDto = new ResultDto<>();

        if (file == null) {
            logger.info("[upload]未上传语音文件!");
            resultDto.setParamError();
            resultDto.setCodeMsg("[upload] 未上传语音文件!");
        }
        if (file != null) {
            String originalFileName = file.getOriginalFilename();
            logger.info("originalFileName is {}", originalFileName);

            String distName;
            if (StringUtils.isBlank(originalFileName) || !originalFileName.contains(".")) {
                logger.error("[upload]文件名或文件后缀不能为空!");
                resultDto.setCode(ErrorEnum.ERROR.getCode());
                resultDto.setCodeMsg("文件名或文件后缀不能为空!");
            } else {
                distName = UUID.randomUUID().toString() + ".wav";
                String systemRealPath = "/opt/web/hockday-demo/static/file/";
                File path = new File(systemRealPath);
                if (!path.exists()) {
                    boolean mkdirs = path.mkdirs();
                    logger.info("path:" + path + ", result : mkdirs={}", mkdirs);
                }
                File upFile = new File(systemRealPath + originalFileName);
                try {
                    file.transferTo(upFile);
                    logger.info("转储语音文件成功---");
                } catch (IOException e) {
                    logger.error("转储语音文件异常------ e=", e);
                }
                try {
                    //进行格式转换
                    M4aToWavUtil.doTurnType(systemRealPath + originalFileName, systemRealPath + distName);
                    //调用转化接口
                    JSONObject jsonObject = callBaiduApi(new File(systemRealPath + distName));
                    logger.info("调取结果， jsonObject={}", jsonObject.toString());
                    resultDto.setCode(ErrorEnum.ERROR.getCode());
                    String resultMag = null;
                    if (jsonObject.has("error_msg")) {
                        resultMag = (String) jsonObject.get("error_msg");
                    }
                    if (jsonObject.has("err_msg")) {
                        resultMag = (String) jsonObject.get("err_msg");
                    }
                    if (jsonObject.has("error_code")) {
                        resultMag = (String) jsonObject.get("error_code");
                    }
                    if (jsonObject.has("err_code")) {
                        resultMag = (String) jsonObject.get("err_code");
                    }
                    if (resultMag != null) {
                        resultDto.setCodeMsg(resultMag);
                    }
                    StringBuilder msgResult = new StringBuilder();
                    if (jsonObject.has("result")) {
                        JSONArray result = (JSONArray) jsonObject.get("result");
                        for (Object o : result) {
                            if (o != null && StringUtils.isNotBlank((String) o)) {
                                msgResult.append(o).append(",");
                            }
                        }
                        if (StringUtils.isNotBlank(msgResult)) {
                            resultDto.setData(msgResult.substring(0, msgResult.length() - 1));
                            resultDto.setCode(ErrorEnum.SUCCESS.getCode());
                        }
                    }
                } catch (Exception e) {
                    logger.info("格式转化 或语音接口 方法失败 e=", e);
                }
            }
        }
        return JSON.toJSONString(resultDto);
    }

    private JSONObject callAipSpeech(File source, File dist) {
        // 调用接口
        //ConvertAudiso.audioToWav(source, dist);
        try {
            InputStream input = new FileInputStream(dist);
            byte[] byt = new byte[input.available()];
            input.read(byt);
            JSONObject res = aipSpeech.asr(byt, "wav", 16000, null);
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


    private JSONObject callBaiduApi(File targetFile) {
        if (aipSpeech == null) {
            aipSpeech = new AipSpeech(APP_ID, API_KEY, SECRET_KEY);
        }
        JSONObject res = null;
        InputStream input = null;
        try {
            input = new FileInputStream(targetFile);
            byte[] byt = new byte[input.available()];
            input.read(byt);
            res = aipSpeech.asr(byt, "wav", 16000, null);
            logger.info("[callAipSpeech] res:" + res.toString());
        } catch (Exception e) {
            logger.error("api failed，e=", e);
        } finally {
            try {
                if (input != null) {
                    input.close();
                }
            } catch (IOException e) {
                logger.info("关闭文件流异常 e=", e);
            }
        }

        return res;
    }

}
