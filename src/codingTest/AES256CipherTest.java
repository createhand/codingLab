package codingTest;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;

public class AES256CipherTest {
	

	public static void encDesTest() throws InvalidKeyException, UnsupportedEncodingException,
			NoSuchAlgorithmException, NoSuchPaddingException,
			InvalidAlgorithmParameterException, IllegalBlockSizeException,
			BadPaddingException {
		String id = "849384928928392";
		String custrnmNo = "111111";
		String custNm = "테스트";
		
		AES256Cipher a256 = AES256Cipher.getInstance();

		String enId = a256.AES_Encode(id);
		String enYyyymmdd = a256.AES_Encode(custrnmNo);
		String enCustNm = a256.AES_Encode(custNm);

		String desId = a256.AES_Decode(enId);
		String desYyyymmdd = a256.AES_Decode(enYyyymmdd);
		String desCustNm = a256.AES_Decode(enCustNm);
		System.out.println(enId);
		System.out.println(enYyyymmdd);
		System.out.println(enCustNm);
		System.out.println(desId);
		System.out.println(desYyyymmdd);
		System.out.println(desCustNm);
		
	}
	
	public static void main(String args[]) throws InvalidKeyException, UnsupportedEncodingException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidAlgorithmParameterException, IllegalBlockSizeException, BadPaddingException {
		encDesTest();
	}
	
	
}