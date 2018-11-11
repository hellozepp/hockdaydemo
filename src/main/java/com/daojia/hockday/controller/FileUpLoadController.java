package com.daojia.hockday.controller;

import com.alibaba.fastjson.JSON;
import com.daojia.hockday.util.ResultDto;
import java.io.File;
import java.io.IOException;
import java.util.UUID;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

/**
 * @author by Dawei on 2018/11/11.
 */
@RestController
public class FileUpLoadController {

    @PostMapping(value = "/upload")
    public String addPrizePhoto(@RequestParam(value = "prizeFile", required = false) CommonsMultipartFile prizeFile) {

        ResultDto resultDto = new ResultDto();
        if (prizeFile == null || prizeFile.isEmpty()) {
            return JSON.toJSONString(resultDto);
        }
        //文件原始名
        String originalFilename = prizeFile.getOriginalFilename();
        String newFileName = UUID.randomUUID().toString() + originalFilename;
        /*String systemRealPath = SystemHelper.getSystemRealPath();*/
        String systemRealPath = "http://ai.baidu.com/docs#/ASR-Online-Java-SDK/top/";
        File upFile = new File(systemRealPath, newFileName);
        try {
            //直接的文件复制
            prizeFile.transferTo(upFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return JSON.toJSONString(resultDto);
    }
}
