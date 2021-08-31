package codingTest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.Mac;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * <b>암호화유틸</b>
 * @author 박서찬
 * @date 2018. 7. 24.
 * @version 0.1 : 최초작성
 * <hr>
 * <pre>
 * 해쉬(SHA256, HMAC) 및 암호화(AES128, AES256), 인코딩(Base64) 등의 유틸제공
 * 
 * <b>History:</b>
 * ====================================================================
 *  버전  :    작성일    :  작성자  :  작성내역  
 * --------------------------------------------------------------------   
 *  0.1  2018. 7. 24   박서찬     최초작성
 * ====================================================================
 * </pre>
 */
public class EncryptUtils {

	public static String getHashingSHA256(String source, String salt) {
		String result = "";

		try {
			byte[] a = source.getBytes();
			byte[] b = salt.getBytes();

			byte[] bytes = new byte[a.length + b.length];

			System.arraycopy(a, 0, bytes, 0, a.length);
			System.arraycopy(b, 0, bytes, a.length, b.length);

			MessageDigest md = MessageDigest.getInstance("SHA-256");
			md.update(bytes);

			byte[] byteData = md.digest();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < byteData.length; ++i) {
				sb.append(Integer.toString((byteData[i] & 0xFF) + 256, 16).substring(1));
			}

			result = sb.toString();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		return result;
	}

	public static class HexaString {
		public static String encode(byte[] in)	{
			return Hex.encodeHexString(in);
		}
		public static byte[] decode(String in) throws DecoderException	{
			return Hex.decodeHex(in);
		}
	}
	
	public static class B64	{
		public static byte[] encode(byte[] in)	{
			return Base64.encodeBase64(in);
		}

		public static byte[] decode(byte[] in)	{
			try	{
				return Base64.decodeBase64(in);
			} catch (Exception e)	{
				return null;
			}
		}
	}

	public static class AES128	{
		final private	static String ALGORITHM = "AES";
		final private	static String OPMODE = "AES/CBC/PKCS5PADDING";
		
		public static byte[] encrypt(byte[] in, String key)
		throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, DecoderException
		{
			SecretKeySpec keySpec = new SecretKeySpec(HexaString.decode(key), ALGORITHM);
			Cipher	cipher = Cipher.getInstance(OPMODE);
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);
			
			return cipher.doFinal(in);
		}
		public static byte[] decrypt(byte[] in, String key)
		throws NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, NoSuchPaddingException, InvalidAlgorithmParameterException, BadPaddingException, DecoderException
		{
			SecretKeySpec keySpec = new SecretKeySpec(HexaString.decode(key), ALGORITHM);
			Cipher	cipher = Cipher.getInstance(OPMODE);
			cipher.init(Cipher.DECRYPT_MODE, keySpec);
			
			return cipher.doFinal(in);
		}
	}
	
	public static class AES256 {
	    
	    final private	static String ALGORITHM = "AES";
		final private	static String OPMODE = "AES/CBC/PKCS5PADDING";
	    
	    // 암호화
	    public static String encrypt(String str, String key) throws java.io.UnsupportedEncodingException, 
	                                                    NoSuchAlgorithmException, 
	                                                    NoSuchPaddingException, 
	                                                    InvalidKeyException, 
	                                                    InvalidAlgorithmParameterException, 
	                                                    IllegalBlockSizeException, 
	                                                    BadPaddingException {
	    	String iv = key.substring(0, 16);
	        
	        byte[] keyBytes = new byte[16];
	        byte[] b = key.getBytes("UTF-8");
	        int len = b.length;
	        if (len > keyBytes.length) {
	            len = keyBytes.length;
	        }
	        System.arraycopy(b, 0, keyBytes, 0, len);
	        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);
	        
	        Cipher c = Cipher.getInstance(OPMODE);
	        c.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes()));
	 
	        byte[] encrypted = c.doFinal(str.getBytes("UTF-8"));
	        String enStr = new String(B64.encode(encrypted));
	 
	        return enStr;
	    }
	 
	    //복호화
	    public static String decrypt(String str, String key) throws java.io.UnsupportedEncodingException,
	                                                        NoSuchAlgorithmException,
	                                                        NoSuchPaddingException, 
	                                                        InvalidKeyException, 
	                                                        InvalidAlgorithmParameterException,
	                                                        IllegalBlockSizeException, 
	                                                        BadPaddingException {
	    	String iv = key.substring(0, 16);

	        byte[] keyBytes = new byte[16];
	        byte[] b = key.getBytes("UTF-8");
	        int len = b.length;
	        if (len > keyBytes.length) {
	            len = keyBytes.length;
	        }
	        System.arraycopy(b, 0, keyBytes, 0, len);
	        SecretKeySpec keySpec = new SecretKeySpec(keyBytes, ALGORITHM);

	        Cipher c = Cipher.getInstance(OPMODE);
	        c.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv.getBytes("UTF-8")));

	        byte[] byteStr = B64.decode(str.getBytes());

	        return new String(c.doFinal(byteStr),"UTF-8");
	    }	 
	}

	public static class HMACSHA256	{
		final private static String ALGORITHM = "HmacSHA256";
		
		public static String generate(String in, String apikey)	
		throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, DecoderException
		{
			byte[]	key = HexaString.decode(apikey);
			SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
			Mac		mac = Mac.getInstance(ALGORITHM);
			mac.init(keySpec);

			byte[] out = mac.doFinal(in.getBytes("UTF-8"));
			return HexaString.encode(out);
		}
	}
	
	public static class HMACSHA512	{
		final private static String ALGORITHM = "HmacSHA512";
		
		public static String generate(String in, String apikey)	
		throws NoSuchAlgorithmException, InvalidKeyException, UnsupportedEncodingException, DecoderException
		{
			byte[]	key = HexaString.decode(apikey);
			SecretKeySpec keySpec = new SecretKeySpec(key, ALGORITHM);
			Mac		mac = Mac.getInstance(ALGORITHM);
			mac.init(keySpec);

			byte[] out = mac.doFinal(in.getBytes("UTF-8"));
			return HexaString.encode(out);
		}
	}
	
	public static void main(String args[]) throws InvalidKeyException, NoSuchAlgorithmException, UnsupportedEncodingException, DecoderException {
		//문자길이 짝수만 가능
		String hmacKey = "9a2a2519a27a4066a5720510fe3e4cb5c4";
		System.out.println("hmac256:"+HMACSHA256.generate("1537338648758", hmacKey));
		System.out.println("hmac512:"+HMACSHA512.generate("1537338648758", hmacKey));
		String userPwd = "123456";
		String salt = "hashSalt";
		//example SHA256
		String hashValue = getHashingSHA256(userPwd, salt);
		System.out.println(hashValue);
		
		try {
			//example AES256
			String key = "1234567890123456789";
			String encVal = AES256.encrypt("931226838113", key);
			System.out.println(encVal);
			String decVal = AES256.decrypt(encVal, key);
			System.out.println(decVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			String localGln = "KOEXKR";
			salt = "7137366B446573456C637045303837635057466D564F33785162513550373044";
			System.out.println("Local GLN:"+localGln);
			System.out.println("salt:"+salt);
			//"Qcrf9aCFKpwXrLzwHC3bHGIpcNdKbO63";
			String encVal = HMACSHA256.generate("1537269765170", salt);
			System.out.println("HMAC256:"+encVal);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
