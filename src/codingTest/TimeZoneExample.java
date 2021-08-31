package codingTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang3.StringUtils;

public class TimeZoneExample {
    public static void main(String[] args) {
    	convertTimezoneDateTime("", "04", "Asia/Seoul", "US/Hawaii", "");
//    	AddTimezoneDateTime("20180327", "210421", "Asia/Seoul", +9, "D");
//    	System.out.println(isValidTimeZone("US/Pacific"));
    }

    public static String convertTimezoneDateTime(String date, String time, String standTimeZoneId, String convTimeZoneId, String type) {
    	
		//날짜형식 리턴포맷
        String returnFormat = "yyyyMMddHHmmss";
        
    	//인자 예외처리
    	if(StringUtils.isBlank(date)) {
    		date = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
    	}    	
    	date = date.replaceAll("-", "");
    	date = date.replaceAll(":", "");
    	
    	if(StringUtils.isBlank(date)) {
    		time = DateFormatUtils.format(System.currentTimeMillis(), "HHmmss");
    	}    	
    	time = time.replaceAll("-", "");
    	time = time.replaceAll(":", "");
    	
    	if(time.length() == 4) {
    		time += "00";
    		returnFormat = "yyyyMMddHHmm";
    	} else if(time.length() == 2) {
    		time += "0000";
    		returnFormat = "yyyyMMddHH";
    	}
    	
    	if(StringUtils.isBlank(standTimeZoneId)) {
    		isValidTimeZone(standTimeZoneId);
    		standTimeZoneId = TimeZone.getDefault().getID();
    	}
    	
    	if(StringUtils.isBlank(convTimeZoneId)) {
    		isValidTimeZone(convTimeZoneId);
    		convTimeZoneId = TimeZone.getDefault().getID();
    	}
    	
    	//기준 타임존
    	TimeZone standTimeZone = TimeZone.getTimeZone(standTimeZoneId);
    	Calendar standTime = Calendar.getInstance(standTimeZone);
        DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        df.setTimeZone(standTimeZone);
        try {
			standTime.setTime(df.parse(date+time));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        System.out.println(standTime.getTimeZone().getID()+"(System) time  : " + DateFormatUtils.format(standTime.getTime(), "yyyy-MM-dd HH:mm:ss", standTimeZone));
        

        //변환 타임존
        TimeZone convTimeZone = TimeZone.getTimeZone(convTimeZoneId);
        Calendar convTime = new GregorianCalendar(convTimeZone);
        convTime.setTimeInMillis(standTime.getTimeInMillis());
        System.out.println(convTime.getTimeZone().getID()+" time  : " + DateFormatUtils.format(convTime.getTime(), "yyyy-MM-dd HH:mm:ss", convTimeZone));

        
        if(StringUtils.equals(type, "D")) {
        	returnFormat = returnFormat.substring(0, 8);
        } else if(StringUtils.equals(type, "T")) {
        	returnFormat = returnFormat.substring(8);
        }
        
        return DateFormatUtils.format(convTime.getTime(), returnFormat, convTimeZone);
    }
    
    public static boolean isValidTimeZone(String timeZoneId) {
    	String[] list = TimeZone.getAvailableIDs();
    	for(String value : list) {
    		if(value.equals(timeZoneId)) {
    			return true;
    		}
    	}
    	return false;
    }
    
}