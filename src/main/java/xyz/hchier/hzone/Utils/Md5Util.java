package xyz.hchier.hzone.Utils;

import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * @author by Hchier
 * @Date 2022/6/23 14:34
 */

public class Md5Util {
    public static String encode(String str){
        return DigestUtils.md5DigestAsHex(str.getBytes(StandardCharsets.UTF_8));
    }
}
