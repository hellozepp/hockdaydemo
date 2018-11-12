import com.daojia.hockday.util.ConvertAudio;

import java.io.File;

/**
 * @Author: zhanglin
 * @Date: 2018/11/11
 * @Time: 11:01 PM
 */
public class Test {

    public static void main(String[] args) {
        String path = "/opt/projects/hockdaydemo/src/main/test/java/";
        File file = new File(path + "test.wav");
        ConvertAudio.audioToWav(new File(path + "York.m4a"), file);
    }
}
