package codingTest;

import java.util.Calendar;

public class test {
	
	public static void main(String args[]) {
		
		int tm = 621;
		long now = Long.parseLong(DateUtil.getCurrentDateTime());
		System.out.println((tm/60)*1000);
		System.out.println(tm%60);
		
		System.out.println(DateUtil.getCurrentTime(tm));
		System.out.println(DateUtil.addTimeByHour(Calendar.SECOND, "yyyyMMddHHmmss", tm));
		System.out.println(now);
		
		
		
	}

}
