package com.daojia.hockday.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * @author Dawei 2018/11/19
 */
public class M4aToWavUtil {

    private static final Logger logger = LoggerFactory.getLogger(M4aToWavUtil.class);

    public static void doTurnType(String sourcePath, String targetPath) {
        logger.info("sourcePath={}, targetPath={}", sourcePath, targetPath);
        String webRoot = "/usr/local/ffmpeg/bin";
        Runtime runtime = null;
        runtime = Runtime.getRuntime();
        long startTime = System.currentTimeMillis();

        try {
            //执行ffmpeg.exe 前面是ffmpeg.exe的地址， 中间是需要转换的文件地址，后面是转换后的文件地址
            // -i 是转化方式，意思是可编码节码，
            //配置了环境变量之后 可以不使用此命令
            String absolutePath = new File(webRoot).getAbsolutePath();
            logger.info("absolutePath is {}", absolutePath);
            //m4a转mav ffmpeg -i input.m4a -acodec pcm_s16le -ac 2 -ar 44100 output.wav
            Process process = runtime.exec(absolutePath + "/ffmpeg -i " + sourcePath + " -acodec pcm_s16le -ac 2 -ar 44100 " + targetPath);
            //释放进程
            process.getOutputStream().close();
            process.getInputStream().close();
            process.getErrorStream().close();
            process.waitFor();
            long endTime = System.currentTimeMillis();
            logger.info(sourcePath + " convert success, costs: " + (endTime - startTime) + "ms");
        } catch (Exception e) {
            logger.info("调用本地指令出现问题， e=", e);
        } finally {
            //释放内存资源
            runtime.freeMemory();
        }
    }
}