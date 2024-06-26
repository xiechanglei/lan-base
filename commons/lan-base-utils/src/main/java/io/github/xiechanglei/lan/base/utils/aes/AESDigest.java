package io.github.xiechanglei.lan.base.utils.aes;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

//AES 加密算法
public class AESDigest {
    /**
     * 加密
     */
    public static byte[] encode(byte[] byteContent, byte[] password, byte[] iv) throws Exception {
        return aes(byteContent, password, iv, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     */
    public static byte[] decode(byte[] content, byte[] password, byte[] iv) throws Exception {
        return aes(content, password, iv, Cipher.DECRYPT_MODE);
    }

    private static byte[] aes(byte[] byteContent, byte[] password, byte[] iv, int encryptMode) throws Exception {
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        IvParameterSpec zeroIv = new IvParameterSpec(iv);
        SecretKeySpec key = new SecretKeySpec(password, "AES");
        cipher.init(encryptMode, key, zeroIv);
        return cipher.doFinal(byteContent);
    }
}
