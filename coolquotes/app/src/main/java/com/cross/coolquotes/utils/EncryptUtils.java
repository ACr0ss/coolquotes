package com.cross.coolquotes.utils;

import android.util.Base64;

import com.cross.coolquotes.R;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class EncryptUtils {

    private static final byte[] keyValue = new byte[]{'r', 'e', 'a', 'l', 'l', 'y', 'c', 'o', 'o', 'l', 'q', 'u', 'o', 't', 'e', 's'};
    private final static String HEX = "0123456789ABCDEF";

    private static byte[] getRawKey() throws Exception {
        SecretKey key = new SecretKeySpec(keyValue, "AES");
        return key.getEncoded();
    }

    private static void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b >> 4) & 0x0f)).append(HEX.charAt(b & 0x0f));
    }

    public static String encryptString(String toEncrypt) throws Exception {
        byte[] rawKey = getRawKey();
        byte[] result = encrypt(rawKey, toEncrypt.getBytes());
        return toHex(result);
    }

    private static byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
        SecretKey secretKeySpec = new SecretKeySpec(raw, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return cipher.doFinal(clear);
    }

    public static String decryptString(String toDecrypt) throws Exception {
        byte[] enc = toByte(toDecrypt);
        byte[] result = decrypt(enc);
        return new String(result);
    }

    private static byte[] decrypt(byte[] encrypted) throws Exception {
        SecretKey secretKeySpec = new SecretKeySpec(keyValue, "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return cipher.doFinal(encrypted);
    }

    public static byte[] toByte(String hexString) {
        int len = hexString.length() / 2;
        byte[] result = new byte[len];

        for (int i = 0; i < len; i++) {
            result[i] = Integer.valueOf(hexString.substring(2 * i, 2 * i + 2),16).byteValue();
        }

        return result;
    }

    public static String toHex(byte[] buf) {
        if (buf == null){
            return "";
        }

        StringBuffer result = new StringBuffer(2 * buf.length);
        for (int i = 0; i < buf.length; i++) {
            appendHex(result, buf[i]);
        }

        return result.toString();
    }

    public static String base64Encoder(String toEncrypt) throws Exception {
        return Base64.encodeToString(toEncrypt.getBytes("UTF-8"), Base64.DEFAULT);
    }

    public static String base64Decoder(String toDecrypt) throws Exception {
        return new String(Base64.decode(toDecrypt, Base64.DEFAULT), "UTF-8");
    }

}
