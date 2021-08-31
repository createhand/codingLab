package codingTest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Properties;
import java.util.TimeZone;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;

public class TimeZoneTest {
	
	//서머타임 기간 : 2018.03.11 ~ 2018.11.03
	
	public static String sysTimezone = "Asia/Seoul";

	public static String date = "20180320";
	public static String time = "184024";
	
	/*
	 * US/Pacific
	 * US/Mountain
	 * US/Central
	 * US/Eastern
	 * US/Hawaii
	 * US/Aleutian
	 * US/Alaska
	 */
	
	private static Properties prop;
	
    public static void main(String[] argv) {
    	System.out.print(convertTimezoneDateTime(date, time, "Asia/Seoul", "US/Hawaii", "D")+":");
    	System.out.println(convertTimezoneDateTime(date, time, "Asia/Seoul", "US/Hawaii", "T"));
    	
    	System.out.print(convertTimezoneDateTime(date, time, "Asia/Seoul", "US/Aleutian", "D")+":");
    	System.out.println(convertTimezoneDateTime(date, time, "Asia/Seoul", "US/Aleutian", "T"));

    }
    
	public void setPrivateProperties(Properties privateProperties) {
		prop = privateProperties;
	}
	
    /**
	 * 타임존 날짜 데이터(DB) 리스트 변환(system->local)
	 * @param TAData 
	 * @return TAData
	 */	
	public static List<TAData> convertTimeZoneDataListFromDB(List<TAData> rows) {
		List<TAData> list = new ArrayList<TAData>();
		for(TAData row : rows) {
			list.add(convertTimeZoneDataInfoFromDB(row));
		}
		return list;
	}
	
    /**
	 * 타임존 날짜 데이터(DB) 변환(system->local)
	 * @param TAData 
	 * @return TAData
	 */	
	public static TAData convertTimeZoneDataInfoFromDB(TAData row) {
		
		if(row == null) return row;
		
		for(String colName : db_date_column_list) {
			String dtColName = colName+from_db_date_suffix;
			String tmColName = colName+from_db_time_suffix;
			if(StringUtils.isNotBlank(row.getString(dtColName))) {
				String convertedDttm = convertTimezoneDateTime(row.getString(dtColName), row.getString(tmColName), prop.getProperty("system.timezone"), getSessionTimeZoneId("USER_TZ"), "");
				row.set(dtColName, convertedDttm.substring(0, 8));
				row.set(tmColName, convertedDttm.substring(8));
			}
		}
		
		return row;
	}
	
	/**
	 * 타임존 날짜 데이터(parameter) 변환(local->system)
	 * @param TAData 
	 * @return TAData
	 */	
	public static TAData reverseTimeZoneDataInfoFromParam(TAData row) {
		
		if(row == null) return row;
		TAData convertData = (TAData)row.clone();
		
		for(String colName : param_date_column_list) {
			String dtColName = colName+from_param_date_suffix;
			String tmColName = colName+from_param_time_suffix;
			if(StringUtils.isNotBlank(convertData.getString(dtColName))) {
				String convertedDttm = convertTimezoneDateTime(convertData.getString(dtColName), convertData.getString(tmColName), getSessionTimeZoneId("USER_TZ"), prop.getProperty("system.timezone"), "");
				convertData.set(dtColName, convertedDttm.substring(0, 8));
				convertData.set(tmColName, convertedDttm.substring(8));
			}
		}
		
		//DB timezone offset
		convertData.set("standTimezoneId", getTimeZoneOffset(prop.getProperty("system.timezone")));
		convertData.set("userTimezoneId", getTimeZoneOffset(getSessionTimeZoneId("USER_TZ")));
		
		return convertData;
	}
	
    /**
	 * 타임존 변환
	 * @param 변환날짜 date : 20180328 
	 * @param 변환시간 time : 152815
	 * @param 기준타임존 standTimeZoneId : US/Pacific
	 * @param 변환타임존 convTimeZoneId : Asia/Seoul
	 * @param 리턴유형 type : D, T, ''
	 * @return 리턴유형에 따라 D(날짜:yyyyMMdd), T(시간:HHmmss), ''(yyyyMMddHHmmss)
	 */
	public static String convertTimezoneDateTime(String date, String time, String standTimeZoneId, String convTimeZoneId, String type) {
    	
		//날짜형식 리턴포맷
        String returnFormat = "yyyyMMddHHmmss";
        
    	//인자 예외처리
    	if(StringUtils.isBlank(date)) {
    		date = DateFormatUtils.format(System.currentTimeMillis(), "yyyyMMdd");
    	}    	
    	date = date.replaceAll("-", "");
    	date = date.replaceAll(":", "");
    	
    	if(StringUtils.isBlank(time)) {
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
    		standTimeZoneId = prop.getProperty("system.timezone");
    	}
    	
    	if(StringUtils.isBlank(convTimeZoneId)) {
    		isValidTimeZone(convTimeZoneId);
    		convTimeZoneId = prop.getProperty("system.timezone");
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
//        System.out.println(standTime.getTimeZone().getID()+"(System) time  : " + DateFormatUtils.format(standTime.getTime(), "yyyy-MM-dd HH:mm:ss", standTimeZone));
        

        //변환 타임존
        TimeZone convTimeZone = TimeZone.getTimeZone(convTimeZoneId);
        Calendar convTime = new GregorianCalendar(convTimeZone);
        convTime.setTimeInMillis(standTime.getTimeInMillis());
//        System.out.println(convTime.getTimeZone().getID()+" time  : " + DateFormatUtils.format(convTime.getTime(), "yyyy-MM-dd HH:mm:ss", convTimeZone));

        
        if(StringUtils.equals(type, "D")) {
        	returnFormat = returnFormat.substring(0, 8);
        } else if(StringUtils.equals(type, "T")) {
        	returnFormat = returnFormat.substring(8);
        }
        
        return DateFormatUtils.format(convTime.getTime(), returnFormat, convTimeZone);
    }


    /**
	 * 시스템 타임의 타임존 변환
	 * @param 리턴유형 type : D, T, ''
	 * @return 리턴유형에 따라 D(날짜:yyyyMMdd), T(시간:HHmmss), ''(yyyyMMddHHmmss)
	 */
	public static String getConvertedSystemTimezoneDateTime(String type) {
    	
		Calendar cal = Calendar.getInstance();
		
		SimpleDateFormat timezoneSdf = new SimpleDateFormat("yyyyMMddHHmmss");
		timezoneSdf.format(cal.getTime()).substring(0, 8);
		timezoneSdf.format(cal.getTime()).substring(8);
		
		String convertedDttm = convertTimezoneDateTime(timezoneSdf.format(cal.getTime()).substring(0, 8)
				, timezoneSdf.format(cal.getTime()).substring(8)
				, prop.getProperty("system.timezone")
				, getSessionTimeZoneId("USER_TZ"), type);
        
        return convertedDttm;
    }
	
    /**
	 * 타임존ID 유효성체크
	 * @param timeZoneId 
	 * @return true, false
	 */
	public static boolean isValidTimeZone(String timeZoneId) {
		String[] list = TimeZone.getAvailableIDs();
		for(String value : list) {
			if(value.equals(timeZoneId)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 타임존 offset
	 * @param timezone id : 'US/Pacific', 'Asia/Seoul'
	 * @param timezone offset : '-07:00', '+09:00'
	 */
	public static String getTimeZoneOffset(String timeZoneId) {
		return getZoneIds(TimeZone.getTimeZone(timeZoneId).toZoneId().getId());
	}
    
    public static String getZoneIds(String zoneId) {

        LocalDateTime dt = LocalDateTime.now();
        ZoneId zone = ZoneId.of(zoneId);
        ZonedDateTime zdt = dt.atZone(zone);
        ZoneOffset zos = zdt.getOffset();
        
        //replace Z to +00:00
        String offset = zos.getId().replaceAll("Z", "+00:00");

//        System.out.println("zone:"+zone.toString()+" offset:"+offset);
        return offset;
    }
    
    /**
	 * 세션의 타임존 리턴
	 * @param reqType : session variable name
	 * @return timezone offset : '-07:00', '+09:00'
	 */
	public static String getSessionTimeZoneId(String reqNm) {
		return sysTimezone;
	}
	
	public static String from_db_date_suffix = "_DT";
	public static String from_db_time_suffix = "_TM";
	public static String db_date_column_list[] = {
			"ACCU",
			"AD_EN",
			"AD_ST",
			"AUTH",
			"CNCL",
			"CNFM",
			"CONF",
			"CREAT",
			"DSN_EXP",
			"EN",
			"EVT_EN",
			"EVT_ST",
			"EXPS_EN",
			"EXPS_ST",
			"EXP",
			"FRST_ACCU",
			"ISU",
			"ISU_EN",
			"ISU_ST",
			"LOGIN",
			"LST_ACCU",
			"LST_CNCL",
			"LST_ROUL",
			"MOD",
			"NOTI",
			"OPEN",
			"PROC",
			"PROC_EN",
			"PROC_ST",
			"PWD_MOD",
			"REG",
			"REL",
			"REQ",
			"RETURN",
			"SEND",
			"STAT_MOD",
			"ST",
			"SUBS",
			"TRSF",
			"UNSUBS",
			"USE"
	};
	
	public static String from_param_date_suffix = "Dt";
	public static String from_param_time_suffix = "Tm";
	public static String param_date_column_list[] = {
			"accu",
			"searchAdEn",
			"searchAdSt",
			"adEn",
			"adSt",
			"dsnExp",
			"searchEn",
			"searchSt",
			"search1St",
			"search1En",
			"search2St",
			"search2En",
			"searchAccuSt",
			"searchAccuEn",
			"searchDistSt",
			"searchDistEn",
			"searchBrandSt",
			"searchBrandEn",
			"searchRegSt",
			"searchRegEn",
			"searchTrsfSt",
			"searchTrsfEn",
			"en",
			"st",
			"evtEn",
			"evtSt",
			"expsEn",
			"expsSt",
			"isuEn",
			"isuSt",
			"open"
	};

}