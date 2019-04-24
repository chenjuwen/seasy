package com.seasy.core.util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.ResourceLoader;

/**
 * RSA算法加密工具类
 */
public class RSAencrypt {
    private static RSAPublicKey publickKey = null;
    private static RSAPrivateKey privateKey = null;

    private static final String ALGO = "RSA";
    private static final String CHARTSET = "UTF-8";
    private static final String RSA_PUBLIC = "rsa_public.dat";
    private static final String RSA_PRIVATE = "rsa_private.dat";
    
	private static ResourceLoader resourceLoader = new DefaultResourceLoader();

    /**
     * 加密字符串
     */
    public static String encrypt(String data) {
        FileInputStream fileInputStream = null;
        BigInteger c = null;
        try {
            if(StringUtils.isEmpty(data)){
                return "";
            }
            
            if(publickKey == null){
                // 从文件中读取公钥
                fileInputStream = new FileInputStream(resourceLoader.getResource(RSA_PUBLIC).getFile());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                publickKey = (RSAPublicKey) objectInputStream.readObject();
            }
            
            // RSA算法是使用整数进行加密的，在RSA公钥中包含有两个整数信息：e和n。对于明文数字m,计算密文的公式是m的e次方再与n求模。
            BigInteger e = publickKey.getPublicExponent();
            BigInteger n = publickKey.getModulus();

            // 获取明文的大整数
            byte ptext[] = data.getBytes(CHARTSET);
            BigInteger m = new BigInteger(ptext);

            // 加密明文
            c = m.modPow(e, n);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }

        return (c != null) ? c.toString() : null;
    }

    /**
     * 解密字符串
     */
    public static String decrypt(String data) {
        FileInputStream fileInputStream = null;
        BigInteger c = null;
        try {
            if(StringUtils.isEmpty(data)){
                return "";
            }
            
            c = new BigInteger(data);

            if(privateKey == null){
                // 获取私钥
                fileInputStream = new FileInputStream(resourceLoader.getResource(RSA_PRIVATE).getFile());
                ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
                privateKey = (RSAPrivateKey) objectInputStream.readObject();
            }
            
            // 获取私钥的参数d,n
            BigInteger d = privateKey.getPrivateExponent();
            BigInteger n = privateKey.getModulus();

            // 解密明文
            BigInteger m = c.modPow(d, n);

            // 计算明文对应的字符串并输出
            byte[] mt = m.toByteArray();
            String str = new String(mt, CHARTSET);

            return str;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            IOUtils.closeQuietly(fileInputStream);
        }
    }
    
    /**
     * 生成密钥对
     */
    public static void createRSAKey() {
        FileOutputStream f1 = null;
        ObjectOutputStream b1 = null;
        FileOutputStream f2 = null;
        ObjectOutputStream b2 = null;
        try {
            // 创建密钥对生成器，指定加密和解密算法为RSA
            KeyPairGenerator kpg = KeyPairGenerator.getInstance(ALGO);
            
            // 指定密钥的长度，初始化密钥对生成器
            kpg.initialize(512);
            
            // 生成密钥对
            KeyPair kp = kpg.genKeyPair();

            // 获取公钥
            PublicKey pbkey = kp.getPublic();
            
            // 获取私钥
            PrivateKey prkey = kp.getPrivate();

            // 保存公钥到文件
            f1 = new FileOutputStream(RSA_PUBLIC);
            b1 = new ObjectOutputStream(f1);
            b1.writeObject(pbkey);

            // 保存私钥到文件
            f2 = new FileOutputStream(RSA_PRIVATE);
            b2 = new ObjectOutputStream(f2);
            b2.writeObject(prkey);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(b1);
            IOUtils.closeQuietly(f1);
            IOUtils.closeQuietly(b2);
            IOUtils.closeQuietly(f2);
        }
    }

    public static void main(String[] args) {
    	try{
    		boolean isCreate = false;
    		
    		if(isCreate){
    			createRSAKey();
    			System.out.println("create ok");
    		}else{
        		String data = "test rsa encrypt";
        		
        		String encryptStr = encrypt(data);
        		System.out.println(encryptStr);
        		
        		String decryptStr = decrypt(encryptStr);
        		System.out.println(decryptStr);
        		
        		System.out.println(data.equals(decryptStr));
    		}
    		
    	}catch(Exception ex){
    		ex.printStackTrace();
    	}
    }
    
}
