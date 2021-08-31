package codingTest;

import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Security;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class RSAUtil {
	/**
	 * KeyPair 생성
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public static KeyPair generateRSAKeyPair() throws NoSuchAlgorithmException {
		KeyPairGenerator kpg  = KeyPairGenerator.getInstance("RSA");
		kpg.initialize(1024);
		return kpg.genKeyPair();
	}
	
	/**
	 * RSA 암호문 생성
	 * @param plain
	 * @param pubk
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchProviderException 
	 */
	public static String rsaEncrypt(byte[] plain, PublicKey pubk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher c = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        c.init(Cipher.ENCRYPT_MODE, pubk);
        
        byte[] cipher = c.doFinal(plain);
        return byteArrayToHex(cipher);
	}
		
	/**
	 * RSA 암호문 평문으로 변경
	 * @param cipher
	 * @param privk
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws NoSuchProviderException 
	 */
	public static byte[] rsaDecrypt(String cipher, PrivateKey privk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, NoSuchProviderException {
		Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
        Cipher c = Cipher.getInstance("RSA/None/PKCS1Padding", "BC");
        c.init(Cipher.DECRYPT_MODE, privk);
        byte[] dec = hexToByteArray(cipher);
        byte[] plain = c.doFinal(dec);
        return plain;
	}
	
	/**
	 * modulus, exponent로 공개키 생성
	 * @param Modulus
	 * @param Exponent
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PublicKey generatePublicKey(String Modulus, String Exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
		java.math.BigInteger pbModulus = new java.math.BigInteger(Modulus, 32);
		java.math.BigInteger pbExponent = new java.math.BigInteger(Exponent, 32);
		RSAPublicKeySpec pubks = new RSAPublicKeySpec(pbModulus, pbExponent);
		return KeyFactory.getInstance("RSA").generatePublic(pubks);
	}
	
	/**
	 * modulus, exponent로 개인키 생성
	 * @param Modulus
	 * @param Exponent
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws InvalidKeySpecException
	 */
	public static PrivateKey generatePrivateKey(String Modulus, String Exponent) throws NoSuchAlgorithmException, InvalidKeySpecException {
		java.math.BigInteger pbModulus = new java.math.BigInteger(Modulus, 32);
		java.math.BigInteger pbExponent = new java.math.BigInteger(Exponent, 32);
		RSAPrivateKeySpec privks = new RSAPrivateKeySpec(pbModulus, pbExponent);
		return KeyFactory.getInstance("RSA").generatePrivate(privks);
	}
	
	/**
	 * 전자서명값 생성
	 * @param plain
	 * @param privk
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws SignatureException
	 */
	public static byte[] rsaSign(byte[] plain, PrivateKey privk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException {
		Signature sig = Signature.getInstance("MD5WithRSA");
		sig.initSign(privk);
		sig.update(plain);
		byte[] signatureBytes = sig.sign();
		return signatureBytes;
	}
	
	/**
	 * 전자서명값 검증
	 * @param plain
	 * @param sign
	 * @param pubk
	 * @return
	 * @throws NoSuchAlgorithmException
	 * @throws NoSuchPaddingException
	 * @throws InvalidKeyException
	 * @throws IllegalBlockSizeException
	 * @throws BadPaddingException
	 * @throws SignatureException
	 */
	public static boolean rsaVerify(byte[] plain, byte[] sign, PublicKey pubk) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException, SignatureException {
		Signature sig = Signature.getInstance("MD5WithRSA");
		sig.initVerify(pubk);
		sig.update(plain);
		return sig.verify(sign); 
	}
	
	/**
	 * Hexa String을 Byte Array로 변환
	 * @param hex
	 * @return
	 */
	public static byte[] hexToByteArray(String hex) {
		if (hex == null || hex.length() == 0) {
			return null;
		}
		byte[] ba = new byte[hex.length() / 2];
		for (int i = 0; i < ba.length; i++) {
			ba[i] = (byte) Integer.parseInt(hex.substring(2 * i, 2 * i + 2), 16);
		}
		return ba;
	}
	
	/**
	 * Byte Array를 Hexa String으로 변환
	 * @param ba
	 * @return
	 */
	// byte[] to hex
	public static String byteArrayToHex(byte[] ba) {
	    if (ba == null || ba.length == 0) {
	        return null;
	    }
	 
	    StringBuffer sb = new StringBuffer(ba.length * 2);
	    String hexNumber;
	    for (int x = 0; x < ba.length; x++) {
	        hexNumber = "0" + Integer.toHexString(0xff & ba[x]);
	 
	        sb.append(hexNumber.substring(hexNumber.length() - 2));
	    }
	    return sb.toString();
	}
}
