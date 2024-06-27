package io.github.xiechanglei.lan.base.digest.aes;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Base64;

/**
 * Aes加密工具类
 */
public class AESHelper {
    /**
     * Aes 加密
     *
     * @param content  明文
     * @param password 密码
     * @param iv       偏移量
     * @return 密文 base64 格式
     * @throws Exception 加密异常
     */
    public static String encode(String content, String password, String iv) throws Exception {
        byte[] encode = AESDigest.encode(content.getBytes(StandardCharsets.UTF_8),
                formatPass(password.getBytes(StandardCharsets.UTF_8)),
                formatIv(iv.getBytes(StandardCharsets.UTF_8)));
        return Base64.getEncoder().encodeToString(encode);
    }

    /**
     * @param content  密文  base64 格式
     * @param password 密码
     * @param iv       偏移量
     * @return 明文
     * @throws Exception 解密异常
     */
    public static String decode(String content, String password, String iv) throws Exception {
        byte[] decode = AESDigest.decode(Base64.getDecoder().decode(content),
                formatPass(password.getBytes(StandardCharsets.UTF_8)),
                formatIv(iv.getBytes(StandardCharsets.UTF_8)));
        return new String(decode, StandardCharsets.UTF_8);
    }

    /**
     * 密码的长度必须是16字节，24字节，或者是32字节
     */
    private static byte[] formatPass(byte[] bytes) {
        if (bytes.length == 16 || bytes.length == 24 || bytes.length == 32) {
            return bytes;
        }
        if (bytes.length < 16) {
            return padding(bytes, 16 - bytes.length);
        }
        if (bytes.length < 24) {
            return padding(bytes, 24 - bytes.length);
        }
        if (bytes.length < 32) {
            return padding(bytes, 32 - bytes.length);
        }
        return Arrays.copyOf(bytes, 32);
    }

    /**
     * iv 长度大于16，则裁剪，小于16则填充
     */
    private static byte[] formatIv(byte[] bytes) {
        int length = 16 - bytes.length;
        if (length == 0) {
            return bytes;
        }
        if (length < 0) {
            return Arrays.copyOf(bytes, 16);
        }
        return padding(bytes, length);

    }

    private static byte[] padding(byte[] bytes, int length) {
        byte[] res = new byte[bytes.length + length];
        System.arraycopy(bytes, 0, res, 0, bytes.length);
        return res;
    }
}
