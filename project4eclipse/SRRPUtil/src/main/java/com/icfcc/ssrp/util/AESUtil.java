package com.icfcc.ssrp.util;



import javax.crypto.KeyGenerator;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;


public class AESUtil {
	
	
	public static byte[] encrypt(String content, String password) {  
        try {             
                KeyGenerator kgen = KeyGenerator.getInstance("AES");  
                kgen.init(128, new SecureRandom(password.getBytes()));  
                SecretKey secretKey = kgen.generateKey();  
                byte[] enCodeFormat = secretKey.getEncoded();  
                SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");  
                Cipher cipher = Cipher.getInstance("AES");// 创建密码 
                byte[] byteContent = content.getBytes("utf-8");  
				cipher.init(Cipher.ENCRYPT_MODE, key);
				// 初始 
                byte[] result = cipher.doFinal(byteContent);  
                return result; // 加密   
        } catch (NoSuchAlgorithmException e) {  
                e.printStackTrace();  
        } catch (NoSuchPaddingException e) {  
                e.printStackTrace();  
        } catch (InvalidKeyException e) {  
                e.printStackTrace();  
        } catch (UnsupportedEncodingException e) {  
                e.printStackTrace();  
        } catch (IllegalBlockSizeException e) {  
                e.printStackTrace();  
        } catch (BadPaddingException e) {  
                e.printStackTrace();  
        }  
        return null;  
}  



public static byte[] decrypt(byte[] content, String password) {  
    try {  
             KeyGenerator kgen = KeyGenerator.getInstance("AES");  
             kgen.init(128, new SecureRandom(password.getBytes()));  
             SecretKey secretKey = kgen.generateKey();  
             byte[] enCodeFormat = secretKey.getEncoded();  
             SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");              
             Cipher cipher = Cipher.getInstance("AES");// 创建密码  
            cipher.init(Cipher.DECRYPT_MODE, key);// 初始  
            byte[] result = cipher.doFinal(content);  
            return result; // 加密   
    } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
    } catch (NoSuchPaddingException e) {  
            e.printStackTrace();  
    } catch (InvalidKeyException e) {  
            e.printStackTrace();  
    } catch (IllegalBlockSizeException e) {  
            e.printStackTrace();  
    } catch (BadPaddingException e) {  
            e.printStackTrace();  
    }  
    return null;  
}

/**将二进制转换16进制 
 * @param buf 
 * @return 
 */  
public static String parseByte2HexStr(byte buf[]) {  
        StringBuffer sb = new StringBuffer();  
        for (int i = 0; i < buf.length; i++) {  
                String hex = Integer.toHexString(buf[i] & 0xFF);  
                if (hex.length() == 1) {  
                        hex = '0' + hex;  
                }  
                sb.append(hex.toUpperCase());  
        }  
        return sb.toString();  
}  

/**16进制转换为二进制 
 * @param hexStr 
 * @return 
 */  
public static byte[] parseHexStr2Byte(String hexStr) {  
        if (hexStr.length() < 1)  
                return null;  
        byte[] result = new byte[hexStr.length()/2];  
        for (int i = 0;i< hexStr.length()/2; i++) {  
                int high = Integer.parseInt(hexStr.substring(i*2, i*2+1), 16);  
                int low = Integer.parseInt(hexStr.substring(i*2+1, i*2+2), 16);  
                result[i] = (byte) (high * 16 + low);  
        }  
        return result;  
}  

public static String encryptStr(String content, String password) {  
	byte[] encryptResult = encrypt(content, password);  
	return parseByte2HexStr(encryptResult);  
}

public static String decryptStr(String content, String password) { 
	byte[] decryptFrom = parseHexStr2Byte(content);  
	byte[] decryptResult = decrypt(decryptFrom,password);
	return new String(decryptResult);
}
public static void main(String[] ags){
	String content = "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";
	String con="76150668-X";
	String password = "123456qwerty1111";
	
//	System.out.println("加密前：" + content);  
//	String str1= encryptSecs(con,password);
	String str2 = decryptSecs(content,password);
	System.out.println(str2);
//	System.out.println(str2);
//	byte[] encryptResult = encrypt(content, password);  
//	String encryptResultStr = parseByte2HexStr(encryptResult);  
//	System.out.println("加密后：" + encryptResultStr);  
//	//解密   
//	byte[] decryptFrom = parseHexStr2Byte(encryptResultStr);  
//	byte[] decryptResult = decrypt(decryptFrom,password);  
//	System.out.println("解密后：" + new String(decryptResult));  
}

private static final String AESTYPE = "AES/ECB/PKCS5Padding";

public static String encryptSecs(String content, String key){
	if(null==(content)){
		return "";
	}
	byte[] encrypt = null;
	try{
		Key rekey = generateKey(key);
		Cipher cipher = Cipher.getInstance(AESTYPE);
		cipher.init(Cipher.ENCRYPT_MODE, rekey);
		encrypt = cipher.doFinal(content.getBytes("GBK"));
	}catch (Exception e) {
		e.printStackTrace();
	}
	return new String(Base64.encodeBase64(encrypt));
}
public static String desEncrypt(String data, String key) throws Exception {
	String s = "";
	if(null==(data)){
		return "";
	}
	byte[] decrypt = null;
	try{
		Key rekey = generateKey(key);
		Cipher cipher = Cipher.getInstance(AESTYPE);
		cipher.init(Cipher.DECRYPT_MODE, rekey);
		decrypt = cipher.doFinal(Base64.decodeBase64(data.getBytes("UTF-8")));
		s = new String(decrypt,"UTF-8");
	}catch (Exception e) {
		e.printStackTrace();
	}
	return s;
}
public static String decryptSecs(String content, String key){
	String s = "";
	if(null==(content)){
		return "";
	}
	byte[] decrypt = null;
	try{
		Key rekey = generateKey(key);
		Cipher cipher = Cipher.getInstance(AESTYPE);
		cipher.init(Cipher.DECRYPT_MODE, rekey);
		decrypt = cipher.doFinal(Base64.decodeBase64(content.getBytes("GBK")));
		s = new String(decrypt,"GBK");
	}catch (Exception e) {
		e.printStackTrace();
	}
	return s;
}

private static Key generateKey(String key) throws Exception{
		try{
			SecretKeySpec keySpec = new SecretKeySpec(key.getBytes("GBK"),"AES");
			return keySpec;
		}catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
