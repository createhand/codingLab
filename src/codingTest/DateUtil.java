package codingTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.lang.time.DateFormatUtils;

/**
 * <p>
 * 날짜/시간관련 Utility 클래스
 * </p>
 * @author 2015. 10. 20., TA
 */
public class DateUtil {

	/**
	 * <pre>
	 * Calendar 인스턴스를 넘긴다. 
	 * </pre>
	 * @return Calendar
	 */
	public static Calendar getCalendar() {
		return Calendar.getInstance();
	}

	/**
	 * <pre>
	 * Calendar객체를 반환한다.
	 * 입력된 String타입 날짜를 원하는타입으로 Calendar를 반환한다.
	 * String>Calendar
	 * ex)
	 *  포맷 예제
	 * "yyyy.MM.dd G 'at' HH:mm:ss z" => 2001.07.04 AD at 12:08:56 PDT  
	 *	"EEE, MMM d, ''yy"  => Wed, Jul 4, '01  
	 *	"h:mm a"   => 12:08 PM  
	 *	"hh 'o''clock' a, zzzz"   => 12 o'clock PM, Pacific Daylight Time  
	 * "K:mm a, z"  => 0:08 PM, PDT  
	 *	"yyyyy.MMMMM.dd GGG hh:mm aaa"   => 02001.July.04 AD 12:08 PM  
	 *	"EEE, d MMM yyyy HH:mm:ss Z"  =>  Wed, 4 Jul 2001 12:08:56 -0700  
	 *	"yyMMddHHmmssZ"  => 010704120856-0700  
	 * </pre>
	 * @param day 입력한 날짜 문자열
	 * @param format 변결할 포맷
	 * @return 결과 Calendar
	 */
	public static Calendar getCalendar(String day, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(day, format));
		return cal;
	}

	/**
	 * <pre>
	 * 유효한 날짜 형식인지 체크하고 입력 된 날짜 형식으로 변환함.
	 * String>Date
	 * ex)
	 * 포맷 예제
	 * "yyyy.MM.dd G 'at' HH:mm:ss z" => 2001.07.04 AD at 12:08:56 PDT  
	 *	"EEE, MMM d, ''yy"  => Wed, Jul 4, '01  
	 *	"h:mm a"   => 12:08 PM  
	 *	"hh 'o''clock' a, zzzz"   => 12 o'clock PM, Pacific Daylight Time  
	 * "K:mm a, z"  => 0:08 PM, PDT  
	 *	"yyyyy.MMMMM.dd GGG hh:mm aaa"   => 02001.July.04 AD 12:08 PM  
	 *	"EEE, d MMM yyyy HH:mm:ss Z"  =>  Wed, 4 Jul 2001 12:08:56 -0700  
	 *	"yyMMddHHmmssZ"  => 010704120856-0700  
	 * </pre>
	 * @param str 날짜 문자열
	 * @param dateFormat 입련된 날짜형식
	 * @return date 결과 Date 
	 */
	public static Date getDate(String str, String dateFormat) {
		Date date = null;

		try {
			if (str == null || str.length() != dateFormat.length()) {
				return date;
			}
			//Date validation
			SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
			sdf.setLenient(false);

			try {
				date = sdf.parse(str);
			} catch (ParseException pe) {
				date = new Date();
			}
			
		} catch (Exception e) {
			date = new Date();
		}

		return date;
	}

	/**
	 * <pre>
	 * Date 인스턴스를 넘긴다. 
	 * </pre>
	 * @return Date 
	 */
	public static Date getDate() {
		return new Date();
	}

	/**
	 * <pre>
	 * 입력된 Date Type을  원하는 DateFormat으로  반환한다.
	 * Date>String
	 * ex)
	 * 
	 * 포멧예제 
	 * "yyyy.MM.dd G 'at' HH:mm:ss z" => 2001.07.04 AD at 12:08:56 PDT  
	 *	"EEE, MMM d, ''yy"  => Wed, Jul 4, '01  
	 *	"h:mm a"   => 12:08 PM  
	 *	"hh 'o''clock' a, zzzz"   => 12 o'clock PM, Pacific Daylight Time  
	 * "K:mm a, z"  => 0:08 PM, PDT  
	 *	"yyyyy.MMMMM.dd GGG hh:mm aaa"   => 02001.July.04 AD 12:08 PM  
	 *	"EEE, d MMM yyyy HH:mm:ss Z"  =>  Wed, 4 Jul 2001 12:08:56 -0700  
	 *	"yyMMddHHmmssZ"  => 010704120856-0700  
	 * </pre>
	 * @param date Date
	 * @param format 출력할 Date 형식
	 * @return 변환한 날짜형식
	 */
	public static String getDate2String(Date date, String format) {
		if (date != null) {
			DateFormat df = new SimpleDateFormat(format, Locale.ENGLISH);
			return df.format(date);
		}

		return null;
	}
	public static String getDate2String(Date date, String format, Locale locale ) {
		if (date != null) {
			DateFormat df = new SimpleDateFormat(format, locale);
			return df.format(date);
		}

		return null;
	}
	
	/**
	 * <pre>
	 * 오늘 일자를 입력된 Date Type을  원하는 DateFormat으로 반환하는 기능을 제공한다.
	 * Date>String
	 * ex)
	 * 
	 * 포멧예제 
	 * "yyyy.MM.dd G 'at' HH:mm:ss z" => 2001.07.04 AD at 12:08:56 PDT  
	 *	"EEE, MMM d, ''yy"  => Wed, Jul 4, '01  
	 *	"h:mm a"   => 12:08 PM  
	 *	"hh 'o''clock' a, zzzz"   => 12 o'clock PM, Pacific Daylight Time  
	 * "K:mm a, z"  => 0:08 PM, PDT  
	 *	"yyyyy.MMMMM.dd GGG hh:mm aaa"   => 02001.July.04 AD 12:08 PM  
	 *	"EEE, d MMM yyyy HH:mm:ss Z"  =>  Wed, 4 Jul 2001 12:08:56 -0700  
	 *	"yyMMddHHmmssZ"  => 010704120856-0700  
	 * </pre>
	 * @param format 출력할 Date 형식
	 * @return String 변환한 날짜형식
	 */
	public static String getToday(String format, Locale locale) {
		return getDate2String(getDate(), format, locale);
	}
	
	/**
	 * <p>
	 * 포맷을 지정하여 현재날짜시간을 리턴 
	 * </p>
	 * @param format
	 * @return
	 */
	public static String getToday(String format) {
		return getDate2String(getDate(), format);
	}
	
	/**
	 * <p>
	 * 현재 날짜(년월일)와 시간(시분초)을 리턴 
	 * </p>
	 * @return
	 */
	public static String getToday(){
		return getDate2String(getDate(), Consts.DEFAULT_DATE_FORMAT);
	}

	/**
	 * <p>
	 * 현재 날짜(년월일)를 리턴 
	 * </p>
	 * @return
	 */
	public static String getYearMmDd(){
		return getDate2String(getDate(), Consts.DEFAULT_YEAR_FORMAT);
	}
	
	/**
	 * <p>
	 * 현재 시간(시분초)을 리턴 
	 * </p>
	 * @return
	 */
	public static String getTime(){
		return getDate2String(getDate(), Consts.DEFAULT_TIME_FORMAT);
	}
	
	/**
	 * 입력 일 전 일자를 반환한다.(6자리 : yyyyMMdd)
	 * @return String
	 */
	public static String getBefDay(int day, String format){
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, -day);
		return DateFormatUtils.format(cal.getTime(), format);
	}
	
	/**
	 * 입력 일 후 일자를 반환한다.(6자리 : yyyyMMdd)
	 * @return String
	 */
	public static String getAftDay(int day, String format){
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.DAY_OF_MONTH, day);
		return DateFormatUtils.format(cal.getTime(), format);
	}
	
	
	/**
	 * <p>date  를 기준으로 addFormat 지정만큼 더하거나 뺀다.</p>
	 * 
	 * <pre>
	 * addFormat은 [+|-][d|w|m|y][숫자]로 구성된다.
	 *   + : 더하기
	 *   - : 빼기
	 *   d : 일
	 *   w : 주
	 *   m : 월
	 *   y : 년
	 *   숫자 : 더하거나 뺄 숫자
	 *   
	 *   EX) +d5 <-- 5일 더한 일
	 *       -w1 <-- 1주 뺀 일
	 *       
	 * </pre>
	 * 
	 * @param date  기준일자.
	 * @param addFormat 더하거나 뺄 일자형식.
	 * @return 기준일에서 더하거나 뺀 날자.
	 */
	public static final Date add(Date date, String addFormat) {
		if (addFormat.length() < 3)
			throw new IllegalArgumentException("addFormat 형식이 올바르지 않습니다. addFormat=" + addFormat);

		char sign = addFormat.charAt(0);
		char unit = addFormat.charAt(1);
		String sNumber = addFormat.substring(2);

		int number = 0;
		try {
			number = Integer.parseInt(sNumber);
		} catch (Exception e) {
			throw new IllegalArgumentException("addFormat 형식이 올바르지 않습니다. addFormat=" + addFormat);
		}
		if (number == 0) // 0이면 date  제공.
			return date;

		int calendarField = -1;
		if (unit == 'd') {
			calendarField = Calendar.DAY_OF_MONTH;
		} else if (unit == 'w') {
			calendarField = Calendar.WEEK_OF_MONTH;
		} else if (unit == 'm') {
			calendarField = Calendar.MONTH;
		} else if (unit == 'y') {
			calendarField = Calendar.YEAR;
		} else
			throw new IllegalArgumentException("addFormat 형식이 올바르지 않습니다. addFormat=" + addFormat);

		Calendar cal = new GregorianCalendar();

		if (date != null)
			cal.setTime(date);

		if (sign == '-')
			cal.add(calendarField, number * -1);
		else
			cal.add(calendarField, number);

		return cal.getTime();
	}
	
	/**
	 * date 를 format 형식의 문자열로 제공한다.
	 * @param date 
	 * @param format
	 * @return
	 */
	public static final String format(Date date, String format) {
		if( date == null) {
			return "";
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 입력 달 전 년월을 반환한다.(6자리 : yyyyMM)
	 * @return String(yyyyMM)
	 */
	public static String getBefMonth(int month){
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MONTH, -month);
		return DateFormatUtils.format(cal.getTime(), "yyyyMM");
	}
	
	/**
	 * 입력 달 다음달의 년월을 반환한다.(6자리 : yyyyMM)
	 * @return String(yyyyMM)
	 */
	public static String getAftMonth(String stDt){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(stDt.substring(0,4));
		int month = Integer.parseInt(stDt.substring(4,6));
		
		cal.set(year, month+1, 0);
		
		String beforeYear = dateFormat.format(cal.getTime()).substring(0,4); 
		String beforeMonth = dateFormat.format(cal.getTime()).substring(4,6); 
		String retStr = beforeYear + beforeMonth;
		
		return retStr;
	}
	
	/**
	 * 입력 달 전달의 년월을 반환한다.(6자리 : yyyyMM)
	 * @return String(yyyyMM)
	 */
	public static String getBefMonth(String stDt){
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMM");
		Calendar cal = Calendar.getInstance();
		int year = Integer.parseInt(stDt.substring(0,4));
		int month = Integer.parseInt(stDt.substring(4,6));
		
		cal.set(year, month-1, 0);
		
		String beforeYear = dateFormat.format(cal.getTime()).substring(0,4); 
		String beforeMonth = dateFormat.format(cal.getTime()).substring(4,6); 
		String retStr = beforeYear + beforeMonth;
		
		return retStr;
	}
	
	/**
	 * 입력 달의 마지막일자를 반환한다.
	 * @return String(yyyyMMdd)
	 */
	public static String getLastDay(String stDt){
		int year = Integer.parseInt(stDt.substring(1, 4));
		int month = Integer.parseInt(stDt.substring(4, 6));
		Calendar cal = new GregorianCalendar();
		cal.set(year, month-1, 1);
		String lastDay = stDt + Integer.toString(cal.getActualMaximum(Calendar.DATE));
		
		return lastDay;
	}
	
	/**
	 * <pre>
	 * 만 나이를 계산  
	 * </pre>
	 * @param birthday 생일
	 * @return 만 나이
	 */
	public static int getAge(Date birthday) {
		
		Calendar now = Calendar.getInstance();
		Calendar dob = Calendar.getInstance();
		dob.setTime(birthday);
		if (dob.after(now)) {
		  throw new IllegalArgumentException("Can't be born in the future");
		}
		int year1 = now.get(Calendar.YEAR);
		int year2 = dob.get(Calendar.YEAR);
		int age = year1 - year2;
		int month1 = now.get(Calendar.MONTH);
		int month2 = dob.get(Calendar.MONTH);
		
		if (month2 > month1) {
		  age--;
		} else if (month1 == month2) {
		  int day1 = now.get(Calendar.DAY_OF_MONTH);
		  int day2 = dob.get(Calendar.DAY_OF_MONTH);
		  if (day2 > day1) {
		    age--;
		  }
		}
		
		return age;
	}
	
	/**
	 * <pre>
	 * 특정날짜의 요일을 구한다.
	 * </pre>
	 * @param date 날짜
	 * @param format 날짜 포맷
	 * @return 요일
	 * 1 : 일요일
	 * 2 : 월요일
	 * 3 : 화요일
	 * 4 : 수요일
	 * 5 : 목요일
	 * 6 : 금요일
	 * 7 : 토요일
	 */
	public static int getDateDay(String date) {
	     
	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd") ;
	    
	    Date nDate = null;
		try {
			nDate = dateFormat.parse(date);
		} catch (ParseException e) {
			throw new IllegalArgumentException("date 형식이 올바르지 않습니다. date=" + date);
		}
	     
	    Calendar cal = Calendar.getInstance() ;
	    cal.setTime(nDate);
	     
	    int dayNum = cal.get(Calendar.DAY_OF_WEEK) ;
	    
	    /* 문자열로 리턴할때는 아래 로직 사용.
	    String day = "" ;
	    switch(dayNum){
	        case 1:
	            day = "일";
	            break ;
	        case 2:
	            day = "월";
	            break ;
	        case 3:
	            day = "화";
	            break ;
	        case 4:
	            day = "수";
	            break ;
	        case 5:
	            day = "목";
	            break ;
	        case 6:
	            day = "금";
	            break ;
	        case 7:
	            day = "토";
	            break ;
	             
	    }
	    */ 
	    return dayNum ;
	}
	
	public static String getDayOfWeek(int dayNum) {
	    String day = "" ;
	    switch(dayNum){
	        case 1:
	            day = "일";
	            break ;
	        case 2:
	            day = "월";
	            break ;
	        case 3:
	            day = "화";
	            break ;
	        case 4:
	            day = "수";
	            break ;
	        case 5:
	            day = "목";
	            break ;
	        case 6:
	            day = "금";
	            break ;
	        case 7:
	            day = "토";
	            break ;
	    }
	    return day;
	}
	
	/**
	 * <pre>
	 * 특정요일의 금주 날짜를 구한다.
	 * </pre>
	 * @param day 요일
	 * @return String(yyyyMMdd)
	 */
	public static String getWeekDate(int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, day);		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
	    return format.format(cal.getTime());
	}
	
	/**
	 * <pre>
	 * 특정요일의 다음 주 날짜를 구한다.
	 * </pre>
	 * @param day 요일
	 * @return String(yyyyMMdd)
	 */
	public static String getNextWeekDate(int day) {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.DAY_OF_WEEK, day);		
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		cal.add(Calendar.DATE, 7);
	    return format.format(cal.getTime());
	}

	/**
	 * <p>문자열 날자를 Date 형으로 제공한다.</p>
	 * 
	 * @param date  
	 * @return 날자형식 반환.
	 * @since 1.0
	 */
	public static final Date toDate(String date) {
		date = (date + "000000000000").substring(0, 14);
		String YYYYMMDD = date.substring(0,8);
		String hhmmss = date.substring(8);
		
		return toDate(YYYYMMDD, hhmmss);
	}
	
	/**
	 * <p>문자열 날자를 Date 형으로 제공한다.</p>
	 * 
	 * @param ymdf
	 * @param hhmmss
	 * @return
	 * @since 1.0
	 */
	public static final Date toDate(String ymdf, String hhmmss) {
		int[] iYmd = splitDate(ymdf);
		int[] iHms = splitTime(hhmmss);

		Calendar cal = new GregorianCalendar(iYmd[0], iYmd[1] - 1, iYmd[2], iHms[0], iHms[1], iHms[2]);
		return cal.getTime();
	}
	
	/**
	 * date  를 년,월,일 정수배열로 변환 제공한다.
	 * date  는 'YYYYMMdd' 또는 구분자(-)를 갖는 'YYYY-MM-dd'이다. 
	 * @param date 
	 * @return
	 */
	public static final int[] splitDate(String date) {
		date = date.replaceAll("-", "");
		int[] iYmd = new int[3];
		iYmd[0] = Integer.parseInt(date.substring(0, 4));
		iYmd[1] = Integer.parseInt(date.substring(4, 6));
		iYmd[2] = Integer.parseInt(date.substring(6));
		return iYmd;
	}
	
	/**
	 * time 을 시,분,초 정수배열로 변환 제공한다.
	 * time 는 'HHMMSS' 또는 구분자(:)를 갖는 'hh:mm:ss'이다. 
	 * @param time
	 * @return
	 */
	public static final int[] splitTime(String time) {
		time = time.replaceAll(":", "");
		int[] iHms = new int[3];
		iHms[0] = Integer.parseInt(time.substring(0, 2));
		iHms[1] = Integer.parseInt(time.substring(2, 4));
		iHms[2] = Integer.parseInt(time.substring(4));
		return iHms;
	}
	
	/**
	 * <p>두 날자의  초 차이를 계산한다.</p>
	 * 
	 * @param begin 비교할 시작일자
	 * @param end 비교할 끝일자
	 * @return
	 * @since 1.0
	 */
	public static long diffOfDateSecond(Date begin, Date end) {
		long diff = begin.getTime() - end.getTime();
		long diffDays = diff / (1000);
		return diffDays;
	}
	
	/**
	 * @Method Name	: formatDateTime
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 요청 format으로 시간 반환한다.
	 * @param		: String
	 * @return		: String
	 */
	public static String formatDateTime(String format) {
		return DateFormatUtils.format(System.currentTimeMillis(), format);
	}
	
	/**
	 * @Method Name	: formatDateTime
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 요청 date, format으로 시간 반환한다.
	 * @param		: String
	 * @return		: String
	 */
	public static String formatDateTime(String date, String format) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.set(Integer.parseInt(date.substring(0,4)), Integer.parseInt(date.toString().substring(4,6))-1, Integer.parseInt(date.toString().substring(6,8)));
		
		return DateFormatUtils.format(calandar.getTime(), format);
	}
	
	/**
	 * @Method Name	: getCurrentDateTime17
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 현재 일자와 시간을 반환한다. (17자리 : yyyyMMddHHmmssSSS)
	 * @param		: 
	 * @return		: String
	 */
	public static String getCurrentDateTime17() {
		return DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS");
	}

	/**
	 * @Method Name	: getCurrentDateTime16
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 현재 일자와 시간을 반환한다. (16자리 : yyyyMMddHHmmssSS)
	 * @param		: 
	 * @return		: String
	 */
	public static String getCurrentDateTime16() {
		String dateTime = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmssSSS");
		return dateTime.substring(0, 16);
	}

	/**
	 * @Method Name	: getCurrentDateTime
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 현재 일자와 시간을 반환한다. (14자리 : yyyyMMddHHmmss)
	 * @param		: 
	 * @return		: String
	 */
	public static String getCurrentDateTime() {
		return DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMddHHmmss");
	}

	/**
	 * @Method Name	: getCurrentDateTime12
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 현재 일자와 시간을 반환한다. (12자리 : yyMMddHHmmss)
	 * @param		: 
	 * @return		: String
	 */
	public static String getCurrentDateTime12() {
		return DateFormatUtils.format(System.currentTimeMillis(), "yyMMddHHmmss");
	}

	/**
	 * @Method Name	: getCurrentDate
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 현재일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param		: 
	 * @return		: String
	 */
	public static String getCurrentDate() {
		return DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd"); 
	}
	
	/**
	 * 현재일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param		: addDay(-, +) miliseconds
	 * @return		: String
	 */
	public static String getCurrentDate(long addDay) {
		addDay = addDay*(6000*60*24);
		return DateFormatUtils.format(System.currentTimeMillis()+addDay, "yyyyMMdd"); 
	}

	/**
	 * @Method Name	: getCurrentDate6
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 현재일자를 반환한다. (6자리 : yyMMdd)
	 * @param		: 
	 * @return		: String
	 */
	public static String getCurrentDate6() {
		return DateFormatUtils.format(System.currentTimeMillis(), "yyMMdd"); 
	}

	/**
	 * @Method Name	: getCurrentTime
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 현재시간을 반환한다. (6자리 : HHmmss)
	 * @param		: 
	 * @return		: String
	 */
	public static String getCurrentTime() {
		return DateFormatUtils.format(System.currentTimeMillis(), "HHmmss");
	}
	
	/**
	 * @Method Name	: getCurrentTime
	 * @Date		: 2016. 08. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 현재시간을 반환한다. (6자리 : HHmmss)
	 * @param		: addTime(-, +) miliseconds
	 * @return		: String
	 */
	public static String getCurrentTime(long addTime) {
		return DateFormatUtils.format(System.currentTimeMillis()+addTime, "HHmmss");
	}

    /**
     * @Method Name	: getCalYear
     * @Date		: 2015. 11. 01.
     * @author		: scpark, tanetworks
     * @Method 설명	: 월,일에 현제 년도를 추가 반환한다. (8자리 : yyyyMMdd)
     * @param		: String
     * @return		: String
     */
    public static String getCalYear( String mmdd ) {
    	String systemDate = getCurrentDate();
    	return systemDate.substring(0, 4) + mmdd;
    }
    
    /**
	 * 오늘을 기준으로 파라메터로 입력한 값 이후의 일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param day 합산할 일
	 * @return String
	 */
	public static String addTimeByHour(int type, String format, int time) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.add(type, time);
		return DateFormatUtils.format(calandar.getTime(), format);
	}
	
	/**
	 * 오늘을 기준으로 파라메터로 입력한 값 이후의 일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param day 합산할 일
	 * @return String
	 */
	public static String addDateByDay(int day) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.add(Calendar.DAY_OF_YEAR, day);
		return DateFormatUtils.format(calandar.getTime(), "yyyyMMdd");
	}
	
	/**
	 * 오늘을 기준으로 파라메터로 입력한 값 이후의 일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param month 합산할 월
	 * @return String
	 */
	public static String addDateByMonth(int month) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.add(Calendar.MONTH, month);
		return DateFormatUtils.format(calandar.getTime(), "yyyyMMdd");
	}

	/**
	 * 오늘을 기준으로 파라메터로 입력한 값 이후의 일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param year 합산할 년
	 * @return String
	 */
	public static String addDateByYear(int year) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.add(Calendar.YEAR, year);
		return DateFormatUtils.format(calandar.getTime(), "yyyyMMdd");
	}
    
	/**
	 * @Method Name	: getDate
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 오늘을 기준으로 입력한 값 이후의 일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param		: int
	 * @return		: String
	 */
	public static String getDate(int day) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.add(Calendar.DAY_OF_YEAR, day);
		return DateFormatUtils.format(calandar.getTime(), "yyyyMMdd");
	}
	
	/**
	 * @Method Name	: addDay
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 입력한 일자를 기준으로 입력한 값 이후의 일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param		: String, int
	 * @return		: String
	 */
	public static String addDay(String date, int day) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.set(Integer.parseInt(date.substring(0,4)), Integer.parseInt(date.toString().substring(4,6))-1, Integer.parseInt(date.toString().substring(6,8)));
		calandar.add(Calendar.DAY_OF_YEAR, day);
		return DateFormatUtils.format(calandar.getTime(), "yyyyMMdd");
	}
	
	/**
	 * @Method Name	: getCalDateMonth
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 입력기준일로 부터 월을 계산하여 계산된 일자를 반환한다. (8자리 : yyyyMMdd)
	 * @param		: String, int
	 * @return		: String
	 */
	public static String getCalDateMonth(String date, int month) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.set(Integer.parseInt(date.substring(0,4)), Integer.parseInt(date.toString().substring(4,6))-1, Integer.parseInt(date.toString().substring(6,8)));
		calandar.add(Calendar.MONTH, month);
		return DateFormatUtils.format(calandar.getTime(), "yyyyMMdd");
	}
	
	/**
	 * @Method Name	: getDayDiff
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 입력한 두 날짜의 차이를 반환한다. (8자리 : yyyyMMdd)
	 * @param		: String
	 * @return		: int
	 */
	public static int getDayDiff(String stDate, String enDate) {
		GregorianCalendar stCalandar = new GregorianCalendar();
		GregorianCalendar enCalandar = new GregorianCalendar();
		stCalandar.set(Integer.parseInt(stDate.substring(0,4)), Integer.parseInt(stDate.toString().substring(4,6))-1, Integer.parseInt(stDate.toString().substring(6,8)));
		enCalandar.set(Integer.parseInt(enDate.substring(0,4)), Integer.parseInt(enDate.toString().substring(4,6))-1, Integer.parseInt(enDate.toString().substring(6,8)));
		
		long diffSec = (enCalandar.getTimeInMillis() - stCalandar.getTimeInMillis())/1000;
		int difDay = (int)diffSec/(60*60*24);
		
		return difDay;
	}
	
	/**
	 * @Method Name	: getLastDateOfMonth
	 * @Date		: 2015. 3. 6.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: 입력한 날짜 또는 입력한 월의 마지막날짜를 반환한다. (8자리 : yyyyMMdd)
	 * @param		: String(yyyyMM)
	 * @return		: String
	 */
	public static String getLastDateOfMonth(String date) {		
		Calendar cal = Calendar.getInstance();
		try {
			if(date.length() != 6) { 
				throw new IllegalArgumentException("입력한 형식이 올바르지 않습니다. date=" + date);
			}
			cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6))-1);
		    cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		    cal.set(Calendar.DAY_OF_MONTH, 1);
		    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));		    
		} catch (Exception e) {
			throw new IllegalArgumentException("입력한 형식이 올바르지 않습니다. date=" + date);
		}
		return DateFormatUtils.format(cal.getTime(),"yyyyMMdd");
	}
	
	/**
	 * @Method Name	: checkDate
	 * @Date		: 2015. 11. 01.
	 * @author		: scpark, tanetworks
	 * @Method 설명	: Date 형식 유효성 검사
	 * @param		: String
	 * @return		: boolean
	 */
	public static boolean checkDate(String params, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        Date chkDate = new Date();

        dateFormat.applyPattern(format);
        dateFormat.setLenient(false);      // 엄밀하게 검사한다는 옵션 (반드시 있어야 한다)
        
        try {
            chkDate = dateFormat.parse(params);
        } catch (ParseException e) {
            return false;
        }
		return true;
	}
	
	/**
	 * <p>date  를 기준으로 addFormat의 시 또는 분 만큼 더하거나 뺀다.</p>
	 * 
	 * <pre>
	 * addFormat은 [+|-][h|m][숫자]로 구성된다.
	 *   + : 더하기
	 *   - : 빼기
	 *   h : 시
	 *   m : 분
	 *   숫자 : 더하거나 뺄 숫자
	 *   
	 *   EX) +h5 <-- 5시간 더한 일
	 *       -m10 <-- 10분 뺀 일
	 *       
	 * </pre>
	 * 
	 * @param date  기준일자.
	 * @param addFormat 더하거나 뺄 일자형식.
	 * @return 기준일에서 더하거나 뺀 날자.
	 */
	public static String mixDay(Date currentDay, String addFormat) {
		
		if (addFormat.length() < 3)
			throw new IllegalArgumentException("addFormat 형식이 올바르지 않습니다. addFormat=" + addFormat);

		char sign = addFormat.charAt(0);
		char unit = addFormat.charAt(1);
		String sNumber = addFormat.substring(2);

		int number = 0;
		
		try {
			number = Integer.parseInt(sNumber);
		} catch (Exception e) {
			throw new IllegalArgumentException("addFormat 형식이 올바르지 않습니다. addFormat=" + addFormat);
		}
		if (number == 0) // 0이면 date  제공.
			return null;
		
		
		int calendarField = -1;
		if (unit == 'h') {
			calendarField = Calendar.HOUR;
		} else if (unit == 'm') {
			calendarField = Calendar.MINUTE;
		} else
			throw new IllegalArgumentException("addFormat 형식이 올바르지 않습니다. addFormat=" + addFormat);
		
		
		String minusDay = "";
		
		// 포맷변경 ( 년월일 시분초)
		SimpleDateFormat sdformat = new SimpleDateFormat("yyyyMMddHHmmss"); 
  
		Calendar cal = Calendar.getInstance();
		
		cal.setTime(currentDay);

		if (sign == '-')
			cal.add(calendarField, number * -1);
		else
			cal.add(calendarField, number);
  
		minusDay = sdformat.format(cal.getTime());  
		
		return minusDay;
	}
	
	/**
	 * 오늘을 기준으로 파라메터로 입력한 값 이후의 일자(milisecond)를 반환한다.
	 * @param type
	 * year : Calendar.YEAR
	 * month : Calendar.MONTH
	 * day : Calendar.DAY_OF_YEAR
	 * hour : Calendar.HOUR_OF_DAY
	 * minute : Calendar.MINUTE
	 * second : Calendar.SECOND
	 * @param time
	 * @return String
	 */
	public static long getAfterTime(int type, int time) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.add(type, time);
		return calandar.getTimeInMillis();
	}
	
	/**
	 * 오늘을 기준으로 파라메터로 입력한 값 이후의 일자(format string)를 반환한다.
	 * @param type
	 * year : Calendar.YEAR
	 * month : Calendar.MONTH
	 * day : Calendar.DAY_OF_YEAR
	 * hour : Calendar.HOUR_OF_DAY
	 * minute : Calendar.MINUTE
	 * second : Calendar.SECOND
	 * @param time
	 * @param format
	 * @return String
	 */
	public static String getAfterTime(int type, int time, String format) {
		GregorianCalendar calandar = new GregorianCalendar();
		calandar.add(type, time);
		return DateFormatUtils.format(calandar.getTime(), format);
	}

}
