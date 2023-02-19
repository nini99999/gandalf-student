package com.poshist.common.utils;

import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.security.Key;
import java.security.Security;

public class AESUtils {
    /**
     * 指定加密算法为AES
     */
    private static final String ALGORITHM = "AES/ECB/PKCS7Padding";
    private static String KEY = "key";
@Test
    public  void test() throws Exception {


        String cryptograph1 = encrypt("{\"cardCode\":\"粤B55555\",\"viaTime\":\"2019-08-12 17:46:46\",\"viaType\":\"1\",\"gateId\":\"2e964544-ec67-4f06-a21a-961c53952ce6\",\"gateInfo\":\"rukou\",\"viaResult\":\"0\"}");// 生成的密文
        System.out.println("加密后的结果为:" + cryptograph1);

        String target = decrypt(cryptograph1);// 解密密文
        System.out.println("解密后的字符串为：" + target);
        System.out.println();
    }

    /**
     * 加密方法
     *
     * @param source 源数据
     * @return
     * @throws Exception
     */
    public static String encrypt(String source) throws Exception {

        Key key = getKey();
       byte[] a= key.getEncoded();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        /** 得到Cipher对象来实现对源数据的RSA加密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] b = source.getBytes();
        /** 执行加密操作 */
        byte[] b1 = cipher.doFinal(b);
        BASE64Encoder encoder = new BASE64Encoder();
        return encoder.encode(b1);
    }

    /**
     * 解密算法
     *
     * @param cryptograph 密文
     * @return
     * @throws Exception
     */
    public static String decrypt(String cryptograph) throws Exception {

        Key key = getKey();
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());

        /** 得到Cipher对象对已用公钥加密的数据进行RSA解密 */
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        BASE64Decoder decoder = new BASE64Decoder();
        byte[] b1 = decoder.decodeBuffer(cryptograph);

        /** 执行解密操作 */
        byte[] b = cipher.doFinal(b1);
        return new String(b);
    }

    private static Key getKey() throws Exception {
        ClassPathResource resource = new ClassPathResource(KEY);
        InputStream inputStream = resource.getInputStream();
        byte[] key = IOUtils.toByteArray(inputStream);
        SecretKeySpec sKeySpec = new SecretKeySpec(key, ALGORITHM);
        return sKeySpec;
    }
}
