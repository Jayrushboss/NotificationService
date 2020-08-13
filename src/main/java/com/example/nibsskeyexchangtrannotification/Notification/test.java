package com.example.nibsskeyexchangtrannotification.Notification;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Hex;
import org.apache.tomcat.util.codec.binary.Base64;

import javax.crypto.*;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * @author JoshuaO
 */
public class test {

    public static String Decrypt(String data, String tmskey) throws NoSuchPaddingException, NoSuchAlgorithmException, BadPaddingException, IllegalBlockSizeException, UnsupportedEncodingException, InvalidKeyException, DecoderException {
        byte [] key = Hex.decodeHex(tmskey.toCharArray());

        System.out.println(Arrays.toString(key));
        Cipher cipher = Cipher.getInstance("AES");
//        System.out.println(tmskey.toCharArray()[0]);

        SecretKey originalKey = new SecretKeySpec(key, 0, key.length, "AES");

        cipher.init(Cipher.DECRYPT_MODE, originalKey);
        return new String(cipher.doFinal(Base64.decodeBase64(data)), "UTF-8");

    }

    public static void main(String...args) throws NoSuchPaddingException, DecoderException, InvalidKeyException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException {
        String data = "Wrkr+UftIIhWLof1RUUHp7mz3K0mQ6rYmhro2IKVASIeLyTRZxV5KnShCuGIcYGTdqZs/t33jYGTM0XAMFPCeRFBl9SRhqc/Nu+Gtsslbp2kWzmEpn5EJ0gyXiSYq56XZIMbR+hQreDtSavY98oxQ54gnq1hXr4IJAErekrMweSsa3Hapona0MvQiwi8yfH9jN2qmtcZ+CQ1Pt89HtILXrlPRqRQbfWjEHMUIzZPx0/ypliNtEtfBoQUbW88ILi6zxYc0Kk4whT/wfM+L+vsi1wBBORZnXb/9K3nWXt93DoH2EwNOgoFSMfLT18c+Eq2Hd1SNAXFAhNbSQY4i/Ktgg==";
        String key = "3f17c995dd0d3ef409fcabe2b3f54fb6";
        String value = Decrypt(data, key);
        System.out.println(value);
    }

}

