import com.daojia.hockday.util.EncryptUtil;

/**
 * @Author: zhanglin
 * @Date: 2018/11/24
 * @Time: 9:18 PM
 */
public class TestEncript {
    public static void main(String[] args) {

        String encrypt = EncryptUtil.encrypt("https://wx.qlogo.cn/mmopen/vi_32/BN7iaJ9g0xUIFp5XNXvsGjdoZxb8IdH0dtq2icWhia82FLSy2sGw2g7yHkQSFcv12ibwfg1Ke9pmibHnccSfrd6Yb2w/132");
        System.out.println(encrypt);
    }
}
