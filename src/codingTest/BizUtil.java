/**
 * Copyright (c) 2016 KBCARD, Inc.
 * All right reserved..
 */
package codingTest;

import java.text.DecimalFormat;
import java.util.Date;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 업무와 관련 된 Utility 클래스
 * </p>
 * @author 2016. 08. 01., TA
 */
public class BizUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(BizUtil.class);

	/**
	* 만나이 계산
	* @param birth : 생년월일 (예: "19800101")
	* @return int 만나이
	*/
	public static int calculateManAge(String birth){

		if(birth.length() < 8){ // 8자리 보다 작으면 
			return 0;
		}
	
		String today = ""; 		//오늘 날짜
		int manAge = 0; 		//만 나이
		
		today = DateUtil.getToday("yyyyMMdd");
	
		//today yyyyMMdd
		int todayYear = Integer.parseInt( today.substring(0, 4) );
		int todayMonth = Integer.parseInt( today.substring(4, 6) );
		int todayDay = Integer.parseInt( today.substring(6, 8) );
	
		int ssnYear = Integer.parseInt( birth.substring(0, 4) );
		int ssnMonth = Integer.parseInt( birth.substring(4, 6) );
		int ssnDay = Integer.parseInt( birth.substring(6, 8) );
	
		manAge = todayYear - ssnYear;
	
		if( todayMonth < ssnMonth ){ //생년월일 "월"이 지났는지 체크
			manAge--;
		}else if( todayMonth == ssnMonth ){ //생년월일 "일"이 지났는지 체크
			if( todayDay < ssnDay ){
				manAge--; //생일 안지났으면 (만나이 - 1)
			}
		}
	
		return manAge;
	}
	

	/**
	* 휴대폰 형식 체크
	* @param moblNo
	* @return 
	*/
	public static boolean isValidMoblFormat(String moblNo) {
		
		
		if(moblNo.length() != 10 && moblNo.length() != 11) {
			logger.error("휴대폰번호 형식 오류 입니다.(자리수 안맞음)");
			return false;
		}
		
		if(!StringUtils.equals(StringUtils.substring(moblNo, 0, 3), "010") &&
				!StringUtils.equals(StringUtils.substring(moblNo, 0, 3), "011") &&
				!StringUtils.equals(StringUtils.substring(moblNo, 0, 3), "016") &&
				!StringUtils.equals(StringUtils.substring(moblNo, 0, 3), "017") &&
				!StringUtils.equals(StringUtils.substring(moblNo, 0, 3), "018") &&
				!StringUtils.equals(StringUtils.substring(moblNo, 0, 3), "019")) {
			logger.error("휴대폰번호 형식 오류 입니다.(통신번호 안맞음)");
			return false;
		}
		
		if(moblNo.indexOf("010") == 0) {
			if(moblNo.length() == 10) {
				logger.error("휴대폰번호 형식 오류 입니다.(010인데 자리수 안맞음)");
				return false;
			}
		}
		
		return true;
	}
	
	/**
	* 휴대폰 번호 잘라내기
	* @param moblNo
	* @return 
	*/
	public static String[] sliceMoblNo(String moblNo) {
		String sMoblNo[] = new String[3];
		if(moblNo.length() == 10) {
			sMoblNo[0] = StringUtils.substring(moblNo, 0, 3);
			sMoblNo[1] = StringUtils.substring(moblNo, 3, 6);
			sMoblNo[2] = StringUtils.substring(moblNo, 6, 10);
		}
		
		if(moblNo.length() == 11) {
			sMoblNo[0] = StringUtils.substring(moblNo, 0, 3);
			sMoblNo[1] = StringUtils.substring(moblNo, 3, 7);
			sMoblNo[2] = StringUtils.substring(moblNo, 7, 11);
		}
		
		return sMoblNo;
	}
	
	/**
	* 휴대폰 번호 포맷팅
	* @param moblNo
	* @return 
	*/
	public static String formattingMoblNo(String moblNo) {		
		String sMoblNo[] = sliceMoblNo(moblNo);		
		if(sMoblNo.length !=3) return moblNo;		
		String fmtMoblNo = sMoblNo[0]+"-"+sMoblNo[1]+"-"+sMoblNo[2];		
		return fmtMoblNo;
	}
	
	/**
	* 사업자번호 유효성 체크
	* @param bizNo
	* @return 
	*/
	public static boolean isValidBizNoFormat(String bizNo) {
		
		if(StringUtils.isEmpty(bizNo)) return false;
		
		bizNo = bizNo.trim();
		if(bizNo.length() != 10) {
			return false;
		}
		
		int suma = 0;
		int sumb = 0;
		int sumc = 0;
		int sumd = 0;
		int sume = 0;
		int sumf = 0;
		int osub= 0;
		int s = 0;
		int lastval = 0;
		
		for(int i = 0; i < 8; i++) {
			if(i%3 == 1) s = 3;
			else if(i%3 == 2) s = 7;
			else if(i%3 == 0) s = 1;
			
			suma = suma + (Integer.parseInt(StringUtils.substring(bizNo, i, i+1))*s);
			sumf = sumf + Integer.parseInt(StringUtils.substring(bizNo, i, i+1));
		}
		if(sumf == 0) return false;
		
		osub = Integer.parseInt(StringUtils.substring(bizNo, 9));
		sumb = (Integer.parseInt(StringUtils.substring(bizNo, 8, 9))*5) % 10;
		sumc = (int)(Integer.parseInt(StringUtils.substring(bizNo, 8, 9))*5) / 10;
		lastval = (10 - ((suma + sumb + sumc) % 10)) % 10;
		
		if(osub != lastval) return false;
		
		return true;
	
	}

	
	/**
	* 이메일 유효성 체크
	* @param email
	* @return 
	*/
	public static boolean isValidEmailFormat(String email) {
		
		if(email.length() == 0) {
	        return false;
	    }
	 
	    String pttn = "^\\D.+@.+\\.[a-z]+";
	    Pattern p = Pattern.compile(pttn);
	    Matcher m = p.matcher(email);
	 
	    if(m.matches()) {
	        return true;
	    }
	 
	    return false;
	}
	
	/**
	* 이메일 유효성 체크
	* @param email
	* @return 
	*/
	public static String getCommaNum(String amt) {
		
		double dbAmt = Double.parseDouble(amt);
		DecimalFormat df = new DecimalFormat("#,###");
		
		return String.valueOf(df.format(dbAmt));
		
	}
	
	/**
	* 입력한 값을 입력한 자리수만큼 리턴
	* @param val, cnt
	* @return 
	*/
	public static String getSettingVal(String val, int cnt) {		
		return StringUtils.leftPad("", cnt, val);
	}

	public static String getUniqueNo(int digit) {
		String s = "";
		Random r = new Random();
		r.setSeed(new Date().getTime());
		
		for (int i = 1; i <= digit; i++) {
		    s = s + Math.abs(r.nextInt()%10);
		}
		return s;
	}
	
	/**
	* 비밀번호 유효성 체크
	* @param pwd
	* @return false(정상)
	*/
	public static boolean pwdValidCheck(String pwd) {
		/*--------------------------------------------------------------------*/
		/* 중복된 3자 이상의 문자 또는 숫자 사용불가
		/* 일련숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용불가
		/*---------------------------------------------------------------------*/
		// 중복된 3자 이상의 문자 또는 숫자 사용불가
		int p = pwd.length();
		byte[] b = pwd.getBytes();
		for (int i = 0; i < ((p * 2) / 3); i++) {
			int b1 = b[i + 1];
			int b2 = b[i + 2];

			if ((b[i] == b1) && (b[i] == b2)) {
				logger.debug("중복 3자리 숫자 존재함.");
				return true;
			} else {
				continue;
			}
		}
		
		// 일련숫자 또는 알파벳 순서대로 3자이상 사용하는 비밀번호는 사용불가
		byte[] g = pwd.getBytes();
		int pp = pwd.length();

		// 연속된 3개의 문자 사용불가 (오름차순)
		for (int i = 0; i < ((pp * 2) / 3); i++) {
			int b1 = g[i] + 1;
			int b2 = g[i + 1];
			int b3 = g[i + 1] + 1;
			int b4 = g[i + 2];

			if ((b1 == b2) && (b3 == b4)) {
				logger.debug("연속 3자리 오름차순 숫자 존재함.");
				return true;
			} else {
				continue;
			}
		}
		// 연속된 3개의 문자 사용불가 (내림차순)
		for (int i = 0; i < ((pp * 2) / 3); i++) {
			int b1 = g[i + 1] + 1;
			int b2 = g[i + 2] + 2;

			if ((g[i] == b1) && (g[i] == b2)) {
				logger.debug("연속 3자리 내림차순 숫자 존재함.");
				return true;
			} else {
				continue;
			}
		}
		
		return false;
	}
	
	/**
	* 비밀번호 유효성 체크 - 비교대상 문자열이 포함되었는지 체크한다.
	* @param pwd
	* @param compVal
	* @return false(정상)
	*/
	public static boolean pwdInfoCheck(String pwd, String compVal) {
		// 비밀번호가 특정 문자열을 포함하고 있는 경우 
		if(compVal.indexOf(pwd) > -1) {
			return true;
		} 
		
		return false;
	}
	
	/**
	* 영문숫자 랜덤생성
	* @param length
	* @return String
	*/
	public static String getUniqueString(int length) {
		
		String SALTCHARS = "ABC1D2E3F4G5H6I7J8K9L0MNOPQRSTUVWXYZ";		
		StringBuilder salt = new StringBuilder();
		Random rnd = new Random();
		rnd.setSeed(new Date().getTime());
		
		while(salt.length() < length) {
			int index = (int)(rnd.nextFloat() * SALTCHARS.length());
			salt.append(SALTCHARS.charAt(index));
		}
		
		String saltStr = salt.toString();
		
		return saltStr;
	}
	
	/**
	* byte array 변경
	* @param source
	* @param replace
	* @param startPoint
	* @return replaced
	*/	
	public static byte[] replaceByteArray(byte[] source, byte[] replace, int startPoint) {
		
		int endPoint = startPoint + replace.length;
	
		byte[] replacedBytes = new byte[source.length - (endPoint - startPoint) + replace.length];
		
		System.arraycopy(source, 0, replacedBytes, 0, startPoint);
		System.arraycopy(replace, 0, replacedBytes, startPoint, replace.length);
		System.arraycopy(source, endPoint, replacedBytes, startPoint + replace.length, source.length - endPoint);
		
		return replacedBytes;
	}
}
