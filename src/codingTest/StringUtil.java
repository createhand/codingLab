/**
 * Copyright (c) 2016 KBCARD, Inc.
 * All right reserved..
 */
package codingTest;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

import org.apache.commons.lang.StringUtils;

/**
 * <pre>
 * 문자열 유틸 클래스
 * </pre>
 * @author 2016. 08. 01.
 */
public class StringUtil {
	
	/**
	 * <p>스트링으로 변환 가능한 객체의 null, 공백문자열을 체크 후 지정한 길이만큼 잘라서 리턴.</p>
	 * null, 공백문자열이 아닌 경우만 trim처리한 뒤 지정한 길이만큼 자르도록 되어 있음.
	 * <pre>
	 *  StringUtil.getSubstringAfterEmptyCheck(null, 3) = null;
	 *  StringUtil.getSubstringAfterEmptyCheck("      ", 3) = null;
	 *  StringUtil.getSubstringAfterEmptyCheck("abcde", 3) = "abc";
	 *  StringUtil.getSubstringAfterEmptyCheck("  abcde  ", 3) = "abc";
	 * </pre>
	 * @param target
	 * @param maxLength
	 * @return
	 */
	public static String getSubstringAfterEmptyCheck(Object target, int maxLength) {
		if (target == null ) {
			return null;
		} else { 
			String targetStr = String.valueOf(target).trim();
			
			if (StringUtils.isEmpty(targetStr)){
				return null;
			} else if (targetStr.length() > maxLength) {
				return targetStr.substring(0, maxLength);
			} else {
				return targetStr;
			}
		}
	}
	
	public static boolean isBlank(String str) {
		if(str == null) return true;
		if(str.equals("")) return true;
		return false;
	}
	
	final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();
	
	public static byte[] convertByteToByte(String str, int byteLen, String srcCharset, String dstCharset) {
		
		byte[] rtnBytes = new byte[byteLen];
		
		/* fill blank character */
		Arrays.fill( rtnBytes, (byte)0x20 );
		
		Charset srcCharsetObj = Charset.forName(srcCharset);
		Charset dstCharsetObj = Charset.forName(dstCharset);
		ByteBuffer inputBuffer = ByteBuffer.wrap(str.getBytes());
		
		// decode
		CharBuffer data = srcCharsetObj.decode(inputBuffer);
		
		// encode
		ByteBuffer outputBuffer = dstCharsetObj.encode(data);
		
		int len = outputBuffer.array().length;
		if(len > byteLen) len = byteLen;
		
		
		System.arraycopy(outputBuffer.array(), 0, rtnBytes, 0, len);
		
		return rtnBytes;
	}
	
	public static String bytesToHex(byte[] bytes) {
	    char[] hexChars = new char[bytes.length * 2];
	    for ( int j = 0; j < bytes.length; j++ ) {
	        int v = bytes[j] & 0xFF;
	        hexChars[j * 2] = hexArray[v >>> 4];
	        hexChars[j * 2 + 1] = hexArray[v & 0x0F];
	    }
	    return new String(hexChars);
	}
	
    public static String convertByteToString(byte[] byteArray, String dstEncd) throws Exception {
		String str = null;
		if(byteArray == null) return "";
		try {
			str = new String(byteArray, dstEncd);
			str = str.replaceAll(" ", "");
		} catch (UnsupportedEncodingException e) {
			throw new Exception("시스템 에러가 발생했습니다.", e);
		}
		return str;
	}
    
    public static String nullToBlank(String str) {
    	if(str == null) str = "";
    	return str;
    }
    
    public static String nvl(String strTarget, String strDest) {
		if (strTarget == null || "".equals(strTarget)) {
			return strDest;
		}
		return strTarget;
	}
	
}
