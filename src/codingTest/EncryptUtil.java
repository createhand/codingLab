package codingTest;

import java.security.MessageDigest;

/**
 * 
 * @author hyeokseung.choi
 *
 * @create 2010. 12. 22.
 *
 */

public class EncryptUtil {


		public static String getSHA256(String src) {
			String SHA = "";
			try{			
				MessageDigest md = MessageDigest.getInstance("SHA-256");
				md.update(src.getBytes("EUC-KR"));

				byte[] mdResult = md.digest();

				StringBuffer sb = new StringBuffer();
				for(int i=0; i < mdResult.length; i++){
					sb.append(Integer.toString((mdResult[i]&0xff) + 0x100, 16).substring(1));
				}
				SHA = sb.toString();
				
			} catch(Exception e) {
				return src;			
			}	
			return SHA;
		}
		
		public static void main(String[] args) {
			System.out.println(getSHA256("상호저축은행#067*1*3*215302#김*수#1원#").toUpperCase());
		}
	
}
