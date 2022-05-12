package com.ruoyi.web.controller.tool; /**
 * Alipay.com Inc.
 * Copyright (c) 2004-2019 All Rights Reserved.
 */


import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.*;
import java.security.GeneralSecurityException;
import java.text.MessageFormat;
import java.util.*;

/**
 * @author Alipay.com Inc
 */
@Slf4j
public class AESSymmtricEncrypt {
    private static final String AES_ALGORITHM = "AES/ECB/PKCS5Padding";

    private static final String ENCRYPT_PADDING = "PKCS5Padding";

    /**
     * 支持的加密padding
     */
    private static final Set<String> SUPPORTED_ENCRYPT_PADDING = new HashSet<>(Arrays.asList(ENCRYPT_PADDING,
            "NoPadding"));

    public static String encrypt(String content, String token) {
        try {
            return encrypt(content, token, AES_ALGORITHM, "utf-8");
        } catch (Exception e) {
            log.error("AESSymmtricEncrypt 加密失败，失败原因：{}", e);
            return null;
        }
    }

    public static String decrypt(String content, String token) {
        try {
            return decrypt(content, token, AES_ALGORITHM, "utf-8");
        } catch (Exception e) {
            log.error("AESSymmtricEncrypt 解密失败，失败原因：{}", e);
            return null;
        }
    }


    public static String encrypt(String content, String key, String algorithm, String charset) throws UnsupportedEncodingException,
            GeneralSecurityException {
        // 密钥数据
        byte[] keyData = Base64.decodeBase64(key);
        List<String> r = Arrays.asList(StringUtils.split(algorithm, '/'));
        byte[] cipherBytes = symmtricCrypto(content.getBytes(), keyData, r.get(0), r.get(1),
                r.get(2), Cipher.ENCRYPT_MODE);
        return Base64.encodeBase64String(cipherBytes);
    }

    public static String decrypt(String content, String key, String algorithm, String charset) throws UnsupportedEncodingException,
            GeneralSecurityException {
        // 待加/解密的数据
        byte[] bytes = Base64.decodeBase64(content);
        // 密钥数据
        byte[] keyData = Base64.decodeBase64(key);
        List<String> r = Arrays.asList(StringUtils.split(algorithm, '/'));
        byte[] cipherBytes = symmtricCrypto(bytes, keyData, r.get(0), r.get(1), r.get(2),
                Cipher.DECRYPT_MODE);
        return new String(cipherBytes, charset);
    }

    /**
     * 对称加解密(/?/?模式)
     *
     * @param text        待加/解密的数据
     * @param keyData     密钥数据
     * @param algorithm   对称加密算法名称。行业SDK默认使用3DES算法，即“DESede”.
     *                    目前行业SDK接受的参数有: AES, Blowfish, DESede
     * @param workingMode 工作模式,目前行业SDK接受的参数有CBC和ECB.
     * @param padding     填充模式,目前行业SDK接受的参数有PKCS5Padding和NoPadding.
     * @param mode        加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
     * @return 密文(加密)/明文（解密）。
     * @throws GeneralSecurityException 当用户输入行业SDK不接受的参数时,会抛出异常
     *                                  当密钥数据的长度不符合算法要求时,会抛出异常
     *                                  在NoPadding填充模式下,当待加密的数据不是相应的算法的块大小的整数倍时,会抛出异常
     */
    public static byte[] symmtricCrypto(byte[] text, byte[] keyData, String algorithm,
                                        String workingMode, String padding,
                                        int mode) throws GeneralSecurityException {
        String fullAlg = algorithm + "/" + workingMode + "/" + padding;
        byte[] iv = null;
        if (StringUtils.equals(workingMode, "CBC")) {
            iv = initIv(fullAlg);
        }
        return doCrypto(text, keyData, iv, fullAlg, workingMode, padding, mode);
    }

    /**
     * 获取对称加解密输入流的方法。（/CBC/PKCS5Padding模式）
     *
     * @param file
     * @param keyData   密钥数据
     * @param algorithm 对称加密算法名称。行业SDK默认使用3DES算法，即“DESede”.
     *                  目前行业SDK接受的参数有: AES, Blowfish, DESede
     * @param mode      加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
     * @return 输入流
     * @throws IOException              文件读取错误的时候抛出该异常。
     * @throws GeneralSecurityException 加解密失败时抛出该异常。
     */
    public static InputStream getInputStream(File file, byte[] keyData, String algorithm,
                                             int mode) throws IOException,
            GeneralSecurityException {
        return getInputStream(file, keyData, algorithm, "CBC", mode);
    }

    /**
     * 获取对称加解密输入流的方法
     *
     * @param file
     * @param keyData     密钥数据
     * @param algorithm   对称加密算法名称。行业SDK默认使用3DES算法，即“DESede”.
     *                    目前行业SDK接受的参数有: AES, Blowfish, DESede
     * @param workingMode 工作模式,目前行业SDK接受的参数有CBC和ECB.
     *                    padding     填充模式,目前行业SDK接受的参数有PKCS5Padding和NoPadding.
     * @param mode        加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
     * @return 输入流
     * @throws IOException              文件读取错误的时候抛出该异常。
     * @throws GeneralSecurityException 加解密失败时抛出该异常。
     */
    public static InputStream getInputStream(File file, byte[] keyData, String algorithm,
                                             String workingMode,
                                             int mode) throws IOException {
        String fullAlg = algorithm + "/CBC/PKCS5Padding";
        FileInputStream fileInputStream = null;
        try {
            // 初始化输入流
            fileInputStream = new FileInputStream(file);
            // 初始化cipher
            byte[] iv = initIv(fullAlg);
            Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
            return new CipherInputStream(fileInputStream, cipher);
        } catch (Exception e) {
            log.error("获取对称加解密输入流的方法出错，原因：{}", e);
        } finally {
            if (fileInputStream != null) {
                fileInputStream.close();
            }

        }
        return null;
    }

    /**
     * 获取对称加解密输出流的方法。（/CBC/PKCS5Padding模式）
     *
     * @param file
     * @param keyData   密钥数据
     * @param algorithm 对称加密算法名称。行业SDK默认使用3DES算法，即“DESede”.
     *                  目前行业SDK接受的参数有: AES, Blowfish, DESede
     * @param mode      加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
     * @return 输出流
     * @throws IOException              文件读取错误的时候抛出该异常。
     * @throws GeneralSecurityException 加解密失败时抛出该异常。
     */
    public static OutputStream getOutputStream(File file, byte[] keyData, String algorithm,
                                               int mode) throws IOException {
        return getOutputStream(file, keyData, algorithm, "CBC", ENCRYPT_PADDING, mode);
    }

    /**
     * 获取对称加解密输出流的方法
     *
     * @param file
     * @param keyData     密钥数据
     * @param algorithm   对称加密算法名称。行业SDK默认使用3DES算法，即“DESede”.
     *                    目前行业SDK接受的参数有: AES, Blowfish, DESede
     * @param workingMode 工作模式,目前行业SDK接受的参数有CBC和ECB.
     * @param padding     填充模式,目前行业SDK接受的参数有PKCS5Padding和NoPadding.
     * @param mode        加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
     * @return 输出流
     * @throws IOException              文件读取错误的时候抛出该异常。
     * @throws GeneralSecurityException 加解密失败时抛出该异常。
     */
    public static OutputStream getOutputStream(File file, byte[] keyData, String algorithm,
                                               String workingMode, String padding,
                                               int mode) throws IOException {
        String fullAlg = algorithm + "/CBC/PKCS5Padding";
        // 初始化输出流
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(file);
            // 初始化cipher
            byte[] iv = initIv(fullAlg);
            Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
            return new CipherOutputStream(fileOutputStream, cipher);
        } catch (Exception e) {
            log.error("获取对称加解密输出流的方法出错，原因：{}", e);
        } finally {
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        }
        return null;
    }

    /**
     * 实现加解密的方法
     *
     * @param text    待加/解密的数据
     * @param keyData 密钥数据
     * @param iv      初始向量
     * @param fullAlg 对称加密算法全名。eg.DESede/CBC/PKCS5Padding
     * @param padding 填充模式,目前行业SDK接受的参数有PKCS5Padding和NoPadding.
     * @param mode    加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
     * @return 密文(加密)/明文（解密）。
     * @throws GeneralSecurityException 当用户输入行业SDK不接受的参数时,会抛出异常
     *                                  当密钥数据的长度不符合算法要求时,会抛出异常
     *                                  在NoPadding填充模式下,当待加密的数据不是相应的算法的块大小的整数倍时,会抛出异常
     */
    public static byte[] doCrypto(byte[] text, byte[] keyData, byte[] iv, String fullAlg,
                                  String workingMode, String padding,
                                  int mode) throws GeneralSecurityException {
        if (!StringUtils.equals(workingMode, "CBC") && !StringUtils.equals(workingMode, "ECB")) {
            throw new GeneralSecurityException("错误的工作模式,目前行业SDK只支持CBC和ECB两种工作模式");
        }

        if (!SUPPORTED_ENCRYPT_PADDING.contains(padding)) {
            throw new GeneralSecurityException(
                    MessageFormat.format("错误的填充模式,目前只支持{0}工作模式", SUPPORTED_ENCRYPT_PADDING));
        }

        if (mode != Cipher.ENCRYPT_MODE && mode != Cipher.DECRYPT_MODE) {
            throw new GeneralSecurityException(
                    "错误的加解密标识,目前行业SDK只支持Cipher.ENCRYPT_MODE和Cipher.DECRYPT_MODE");
        }

        Cipher cipher = getCipher(keyData, iv, fullAlg, workingMode, mode);
        return cipher.doFinal(text);
    }

    /**
     * 根据参数初始化cipher的方法
     *
     * @param keyData     密钥数据
     * @param fullAlg     用来初始化Cipher对象的算法全称(已经加上工作模式和填充模式的)
     * @param workingMode 工作模式,目前行业SDK接受的参数有CBC和ECB.
     * @param mode        加解密标识：加密——Cipher.ENCRYPT_MODE；解密——Cipher.DECRYPT_MODE。
     * @return cipher
     * @throws GeneralSecurityException
     */
    private static Cipher getCipher(byte[] keyData, byte[] iv, String fullAlg, String workingMode,
                                    int mode) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(fullAlg);
        SecretKey secretKey = new SecretKeySpec(keyData, StringUtils.substringBefore(fullAlg, "/"));

        if (StringUtils.equals(workingMode, "CBC")) {
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(mode, secretKey, ivSpec);
        } else {
            cipher.init(mode, secretKey);
        }
        return cipher;
    }

    /**
     * 初始向量的方法
     *
     * @param fullAlg
     * @return
     * @throws GeneralSecurityException
     */
    private static byte[] initIv(String fullAlg) throws GeneralSecurityException {
        Cipher cipher = Cipher.getInstance(fullAlg);
        int blockSize = cipher.getBlockSize();
        byte[] iv = new byte[blockSize];
        for (int i = 0; i < blockSize; ++i) {
            iv[i] = 0;
        }
        return iv;
    }


    public static void main(String[] args) {
        String idCard = "";
        String name = "";
        String auth = AESSymmtricEncrypt.encrypt("skm_auth_wxdwrmyy","c2ttMjAyMDEwd3h3eWhkaw==");
        Map<String,String> map = new HashMap<>();
        map.put("userid",idCard);    // 身份证
        map.put("name",name);     // 姓名
        map.put("organizeLocation","无锡市第五人民医院");     // 位置
        map.put("applyType","人行闸机通道");     // 应用类型
        String body = AESSymmtricEncrypt.encrypt(JSON.toJSONString(map),"c2ttMjAyMDF3eHd5Ym9keQ==");

        System.out.println(auth);
        System.out.println(body);
    }

}
