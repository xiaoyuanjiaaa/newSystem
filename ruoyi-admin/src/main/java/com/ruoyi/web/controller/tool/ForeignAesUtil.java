package com.ruoyi.web.controller.tool;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.HexUtil;
import com.alibaba.fastjson.JSON;
import com.ruoyi.common.utils.AESUtils;
import com.ruoyi.common.utils.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.util.Arrays;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

/**
 * @ProjectName: xcloud
 * @Package: com.bo.xcloud.common.crypto.bcrypt
 * @ClassName: AesUtil
 * @Author: it-bo
 * @Date: 2019/6/5 15:01
 * @Description: AES加密工具类
 * @Version: 1.0
 */
@Component
public class ForeignAesUtil {
    /**
     * @author ngh
     * AES128 算法
     * <p>
     * CBC 模式
     * <p>
     * PKCS7Padding 填充模式
     * <p>
     * CBC模式需要添加一个参数iv
     * <p>
     * 介于java 不支持PKCS7Padding，只支持PKCS5Padding 但是PKCS7Padding 和 PKCS5Padding 没有什么区别
     * 要实现在java端用PKCS7Padding填充，需要用到bouncycastle组件来实现
     */
    private Key key;
    private Cipher cipher;
//
    byte[] iv = "0103022405070878".getBytes();
    byte[] keyBytes = "fskm@0716".getBytes();

//    byte[] iv = "8a13d196e8344cddb81e8e67f25f7450".getBytes();
//    byte[] keyBytes = "20210723001".getBytes();
    public void init(byte[] keyBytes) {
        // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
        int base = 16;
        if (keyBytes.length % base != 0) {
            int groups = keyBytes.length / base + (keyBytes.length % base != 0 ? 1 : 0);
            byte[] temp = new byte[groups * base];
            Arrays.fill(temp, (byte) 0);
            System.arraycopy(keyBytes, 0, temp, 0, keyBytes.length);
            keyBytes = temp;
        }
        // 初始化
        Security.addProvider(new BouncyCastleProvider());
        // 转化成JAVA的密钥格式
        key = new SecretKeySpec(keyBytes, "AES");
        try {
            // 初始化cipher
            cipher = Cipher.getInstance("AES/CBC/PKCS7Padding", "BC");
        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (NoSuchProviderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }


    public String encrypt(String content) {
        return Base64.encode(encrypt(content.getBytes(), keyBytes));
    }

    public String encrypt(String content, String keyBytes) {
        return Base64.encode(encrypt(content.getBytes(), keyBytes.getBytes()));
    }

    public String encryptByHex(String content) {
        return HexUtil.encodeHexStr(encrypt(content.getBytes(), keyBytes));
    }

    public String encryptByHex(String content, String keyBytes) {
        return HexUtil.encodeHexStr(encrypt(content.getBytes(), keyBytes.getBytes()));
    }

    /**
     * 加密方法
     *
     * @param content  要加密的字符串
     * @param keyBytes 加密密钥
     * @return
     */
    public byte[] encrypt(byte[] content, byte[] keyBytes) {
        byte[] encryptedText = null;
        keyBytes = new String(keyBytes).getBytes();
        init(keyBytes);
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(content);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return encryptedText;
    }

    public String decrypt(String encryptedData) {
        return new String(decrypt(Base64.decode(encryptedData), keyBytes));
    }

    public String decrypt(String encryptedData, String keyData) {
        return new String(decrypt(Base64.decode(encryptedData), keyData.getBytes()));
    }

    public String decryptByHex(String encryptedData) {
        return new String(decrypt(HexUtil.decodeHex(encryptedData), keyBytes));
    }

    public String decryptByHex(String encryptedData, String keyData) {
        return new String(decrypt(HexUtil.decodeHex(encryptedData), keyData.getBytes()));
    }

    /**
     * 解密方法
     *
     * @param encryptedData 要解密的字符串
     * @param keyBytes      解密密钥
     * @return
     */
    public byte[] decrypt(byte[] encryptedData, byte[] keyBytes) {
        byte[] encryptedText = null;
        init(keyBytes);
        try {
            cipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            encryptedText = cipher.doFinal(encryptedData);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedText;
    }

    public static void main(String[] args) throws UnsupportedEncodingException, GeneralSecurityException {
        ForeignAesUtil foreignAesUtil = new ForeignAesUtil();
        String keySec = "20210723001";
        String userName = "狄乐乐";
//        String card = "320722199212310556";
        userName = AESSymmtricEncrypt.encrypt(userName,keySec,"AES","utf-8");
        System.out.println(userName);

        String card = "320722199212310556";
        card = AESSymmtricEncrypt.encrypt(card,keySec,"AES","utf-8");
        System.out.println(card);
        long now = System.currentTimeMillis()/1000;
        System.out.println(now);

        String md5_param = "";
        String secret = "8a13d196e8344cddb81e8e67f25f7450";
        TreeMap<String,String> map = new TreeMap<>();
        map.put("cardCode",card);
        map.put("organizeLocation","滨湖区落霞苑 385");
        map.put("super_app_key","20210723001");
        map.put("super_time",String.valueOf(now));
        map.put("userName",userName);
        for (String key :map.keySet()){
            md5_param += map.get(key);
        }
        String signStr ;
        if (com.ruoyi.common.utils.StringUtils.isBlank(secret)){
            signStr = DigestUtils.md5Hex(md5_param);
        }else {
            signStr = DigestUtils.md5Hex(md5_param+secret);
        }

        if (signStr.length()<25){
            signStr = signStr.substring(5,signStr.length());
        }else {
            signStr = signStr.substring(5,25);
        }

        System.out.println(signStr);
    }





}
