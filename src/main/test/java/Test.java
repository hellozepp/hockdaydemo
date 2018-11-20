//import com.daojia.hockday.util.ConvertAudio;

import com.daojia.hockday.controller.FileUpLoadController;
import com.daojia.hockday.util.ConvertAudio;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * @Author: zhanglin
 * @Date: 2018/11/11
 * @Time: 11:01 PM
 */
public class Test {

    public static void main(String[] args) {
        String path = "/opt/projects/hockdaydemo/src/main/test/";
        File file1 = new File(path+"1k.pcm");
        File file = new File(path + "37d1a7b6-426a-4195-8e66-b44d2e7a4cf0.wav");
        ConvertAudio.audioToWav(file, file1);
         InputStream input = null;
        try {
            input = new FileInputStream(file1);

            byte[] byt = new byte[input.available()];
            input.read(byt);
            JSONObject jsonObject = FileUpLoadController.apiSpeech(byt);
            System.out.println(jsonObject.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
