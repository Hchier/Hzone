package cc.hchier;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.util.Random;

/**
 * @author by Hchier
 * @Date 2023/2/12 20:27
 */
public class Utils {
    /**
     * 生成len位长的验证码
     *
     * @return {@link String}
     */
    public static String authCode(int len) {
        Random random = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < len; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    /**
     * md5编码
     *
     * @param str str
     * @return {@link String}
     */
    public static String md5Encode(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }
}
