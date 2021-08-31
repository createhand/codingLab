/**
 * Copyright (c) 2016 KBCARD, Inc.
 * All right reserved..
 */
package codingTest;

import java.text.NumberFormat;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * <p>
 * 업무와 관련 된 Utility 클래스
 * </p>
 * @author 2016. 08. 01., TA
 */
public class FormatUtil {
	
	private static final Logger logger = LoggerFactory.getLogger(FormatUtil.class);

	/**
	 * 휴대폰번호 포맷팅
	 * @param account
	 * @return
	 */
	public static String mobileNoFormat(String account) {

		String formatedValue = null;

    	try {
    		formatedValue = StringUtils.trim(account);

        	if(formatedValue.length() == 11){
        		formatedValue = StringUtils.substring(formatedValue, 0, 3)+"-"+StringUtils.substring(formatedValue, 3, 7)+"-"+StringUtils.substring(formatedValue, 7);
            }
        	else {
       			formatedValue = StringUtils.substring(formatedValue, 0, 3)+"-"+StringUtils.substring(formatedValue, 3, 6)+"-"+StringUtils.substring(formatedValue, 6);
            }
		} catch (NumberFormatException ne) {
			formatedValue = account;
		}
    	
    	return formatedValue;
	}
	
	/**
	 * 휴대폰번호 포맷팅(마스킹)
	 * @param account
	 * @return
	 */
	public static String mobileNoFormatMasking(String account) {
		
		String formatedValue = null;
		
		try {
			formatedValue = StringUtils.trim(account);
			
			if(formatedValue.length() == 11){
				formatedValue = StringUtils.substring(formatedValue, 0, 3)+"-"+StringUtils.substring(formatedValue, 3, 5)+"**-*"+StringUtils.substring(formatedValue, 8);
			}
			else {
				formatedValue = StringUtils.substring(formatedValue, 0, 3)+"-"+StringUtils.substring(formatedValue, 3, 5)+"**-*"+StringUtils.substring(formatedValue, 7);
			}
		} catch (NumberFormatException ne) {
			formatedValue = account;
		}
		
		return formatedValue;
	}

	/**
	 * 날짜 포맷팅
	 * @param date
	 * @return
	 */
	public static String dateFormat(String date) {
		if (date == null) return "";
		StringBuilder sb = new StringBuilder();
		if (date.length() == 4) {
			// "MMdd"
			return sb.append(date.substring(0, 2)).append("월")
			.append(date.substring(2)).append("일")
			.toString();
		}
		else if (date.length() == 8) {
			// "yyyyMMdd"
			return sb.append(date.substring(0, 4)).append("년")
			.append(date.substring(4, 6)).append("월")
			.append(date.substring(6)).append("일")
			.toString();
		}
		else if (date.length() == 12) {
			// "yyyyMMddHHmm"
			return sb.append(date.substring(0, 4)).append("년")
			.append(date.substring(4, 6)).append("월")
			.append(date.substring(6, 8)).append("일 ")
			.append(date.substring(8, 10)).append("시")
			.append(date.substring(10)).append("분")
			.toString();
		}
		else if (date.length() == 14) {
			// "yyyyMMddHHmmss"
			return sb.append(date.substring(0, 4)).append("년")
			.append(date.substring(4, 6)).append("월")
			.append(date.substring(6, 8)).append("일 ")
			.append(date.substring(8, 10)).append("시")
			.append(date.substring(10, 12)).append("분")
			.append(date.substring(12)).append("초")
			.toString();
		}
		return date;
	}
	
	/**
	 * 날짜 포맷팅
	 * @param date
	 * @return
	 */
	public static String dateFormatDot(String date) {
		if (date == null) return "";
		StringBuilder sb = new StringBuilder();
		if (date.length() == 4) {
			// "MMdd"
			return sb.append(date.substring(0, 2)).append(".")
			.append(date.substring(2))
			.toString();
		}
		else if (date.length() == 8) {
			// "yyyyMMdd"
			return sb.append(date.substring(0, 4)).append(".")
			.append(date.substring(4, 6)).append(".")
			.append(date.substring(6))
			.toString();
		}
		else if (date.length() == 12) {
			// "yyyyMMddHHmm"
			return sb.append(date.substring(0, 4)).append(".")
			.append(date.substring(4, 6)).append(".")
			.append(date.substring(6, 8)).append("  ")
			.append(date.substring(8, 10)).append(":")
			.append(date.substring(10))
			.toString();
		}
		else if (date.length() == 14) {
			// "yyyyMMddHHmmss"
			return sb.append(date.substring(0, 4)).append(".")
			.append(date.substring(4, 6)).append(".")
			.append(date.substring(6, 8)).append(" ")
			.append(date.substring(8, 10)).append(":")
			.append(date.substring(10, 12)).append(":")
			.append(date.substring(12))
			.toString();
		}
		return date;
	}
	
	/**
	 * 시간 포맷팅
	 * @param time
	 * @return
	 */
	public static String timeFormat(String time) {
		if (time == null) return "";
		StringBuilder sb = new StringBuilder();
		if (time.length() == 4) {
			// "HHmm"
			return sb.append(time.substring(0, 2)).append("시")
			.append(time.substring(2)).append("분")
			.toString();
		}
		else if (time.length() == 6) {
			// "HHmmss"
			return sb.append(time.substring(0, 2)).append("시")
			.append(time.substring(2, 4)).append("분")
			.append(time.substring(4)).append("초")
			.toString();
		}
		return time;
	}

	/**
	 * 시간 포맷팅
	 * @param time
	 * @return
	 */
	public static String timeFormatDot(String time) {
		if (time == null) return "";
		StringBuilder sb = new StringBuilder();
		if (time.length() == 4) {
			// "HHmm"
			return sb.append(time.substring(0, 2)).append(":")
			.append(time.substring(2))
			.toString();
		}
		else if (time.length() == 6) {
			// "HHmmss"
			return sb.append(time.substring(0, 2)).append(":")
			.append(time.substring(2, 4)).append(":")
			.append(time.substring(4))
			.toString();
		}
		return time;
	}

	private static NumberFormat nf = NumberFormat.getNumberInstance();
	private static NumberFormat pf = NumberFormat.getPercentInstance();

	/**
	 * 숫자 포맷팅
	 * @param num
	 * @param format
	 * @return
	 */
	public static String numberFormat(String num, String format) {
		if (!NumberUtils.isNumber(num)) {
			return num;
		}
		nf.setMaximumFractionDigits(2);
		if ("F".equals(format))
			return nf.format(NumberUtils.toFloat(num));
		else if ("P".equals(format))
			return pf.format(NumberUtils.toFloat(num));
		else
			return nf.format(NumberUtils.toLong(num));
	}

	/**
	 * 숫자 포맷팅
	 * @param num
	 * @return
	 */
	public static String numberFormat(long num) {
		return nf.format(num);
	}
	
	public static void main(String [] args) {
		System.out.println(FormatUtil.dateFormat("12345678"));
		System.out.println(FormatUtil.dateFormat("1234567890!@"));
		System.out.println(FormatUtil.dateFormat("1234567890!@#$"));
		System.out.println(FormatUtil.timeFormat("1234"));
		System.out.println(FormatUtil.timeFormat("123456"));
		
		System.out.println(FormatUtil.dateFormatDot("12345678"));
		System.out.println(FormatUtil.timeFormatDot("123456"));
		System.out.println(FormatUtil.dateFormatDot("1234"));
		System.out.println(FormatUtil.timeFormatDot("1234"));
		
		System.out.println(FormatUtil.numberFormat("123456", "N"));
		System.out.println(FormatUtil.numberFormat("123456.22222", "F"));
		System.out.println(FormatUtil.numberFormat("0.723213", "P"));
		System.out.println(FormatUtil.numberFormat(".02", "P"));
		
		System.out.println(FormatUtil.numberFormat(1234567));
		
		System.out.println(FormatUtil.mobileNoFormat("01053664784"));
		System.out.println(FormatUtil.mobileNoFormat("0191234567"));
	}
}
