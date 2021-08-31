package codingTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class cordingTest {
	
	public interface SbHeros {
		public int calcPlus(int a, int b);	
	}
	
	public static void requestAPI() {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n    \"GLN_HEADER\": {},\n    \"GLN_BODY\": {}\n}");
		Request request = new Request.Builder()
		  .url("https://api.glnpay.com:9000/api/v1/mainhub/core/common/initInfo")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("User-Agent", "PostmanRuntime/7.11.0")
		  .addHeader("Accept", "*/*")
		  .addHeader("Cache-Control", "no-cache")
		  .addHeader("Postman-Token", "094074a6-2386-4914-a169-e4c7daef046f,353f6cba-d921-4866-b0db-eb55859833fd")
		  .addHeader("Host", "api.glnpay.com:9000")
		  .addHeader("cookie", "SESSION=M2NlYmZkMWYtMjk1Ni00NDUwLTkxYzAtZmJlNzQ5YjcyYjAx")
		  .addHeader("accept-encoding", "gzip, deflate")
		  .addHeader("content-length", "44")
		  .addHeader("Connection", "keep-alive")
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		
		System.out.println(RandomUtils.nextInt(100000000));
		
//		try {
//			for(int i=0;i<10000;i++) {
//				requestAPI();
//				requestAPI();
//				requestAPI();
//				requestAPI();
//				requestAPI();
//				requestAPI();
//				requestAPI();
//				requestAPI();
//				requestAPI();
//				requestAPI();
//			}
//		} catch(Exception e) {
//			e.printStackTrace();
//		}
	}
	
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
	
	public static Calendar getCalendar(String day, String format) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(getDate(day, format));
		return cal;
	}
	
	public static String getSettingVal(String val, int cnt) {		
		return StringUtils.leftPad("", cnt, val);
	}
	
	public static String encrypt(String pwd, String keyModls, String keyExpnt) {
		
		PublicKey pubKey;
		String encryptPwd = "";
		
		try {
			pubKey = RSAUtil.generatePublicKey(keyModls, keyExpnt);
			encryptPwd = RSAUtil.rsaEncrypt(pwd.getBytes(), pubKey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return encryptPwd;
	}
	
	public static String decrypt(String pwd, String keyModls, String keyExpnt) {
		
		PrivateKey priKey;
		String decryptPwd = "";
		
		try {
			priKey = RSAUtil.generatePrivateKey(keyModls, keyExpnt);
			byte[] decryptByte = RSAUtil.rsaDecrypt(pwd, priKey);
			decryptPwd = new String(decryptByte);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return decryptPwd;
	}
	
	   
	   
	public static void fileConvert(String source) {
		
		File file = new File(source);
		
		try{
			
			BufferedReader in = new BufferedReader(new FileReader(file));
			String s;
			while ((s = in.readLine()) != null) {
				
				s = s.replace(" ", "");
				s = s.trim();
				
				if("1-1계좌입금(가상계좌)[0200/1100]취소[0400/1100]".equals(s)||
						"1-2잔액조회[0200/2100]".equals(s)||
						"1-3수취조회[0200/4100]".equals(s)||
						"1-4가상계좌신규/해지관리".equals(s)||
						"1-5가상계좌신규/해지관리(프라임전용)".equals(s)||
						"1-6가상계좌신규/해지관리(우리은행)".equals(s)||
						"1-7집계/정산전문".equals(s)||
						"1-8은행,기관관리".equals(s)||
						"2-1지급이체[0200/1100]".equals(s)||
						"2-1-1별단지급이체(출금)[0200/1101]".equals(s)||
						"2-2입금불능[0200/1700]".equals(s)||
						"2-3잔액조회[0200/2100]".equals(s)||
						"2-4즉시출금[0200/3800]".equals(s)||
						"2-5자동이체이용기관등록[0200/3801]".equals(s)||
						"2-6자동이체출금등록[0200/3810]".equals(s)||
						"2-7수취조회[0200/4100]".equals(s)||
						"2-8휴대폰인증1차[0200/4600]".equals(s)||
						"2-9휴대폰인증2차[0200/4601]".equals(s)||
						"2-10카드인증[0200/4700]".equals(s)||
						"2-11실명조회[0200/4800]".equals(s)||
						"2-12예금거래명세통지결번[0200/5700]".equals(s)||
						"2-13예금거래명세통지[0200/5900]".equals(s)||
						"2-14어음거래명세통지결번[0200/5701]".equals(s)||
						"2-15어음거래명세통지[0200/5901]".equals(s)||
						"2-16이체처리결과조회[0200/6013]".equals(s)||
						"2-17수표조회[0200/6020]".equals(s)||
						"2-18집계/정산전문".equals(s)||
						"2-19집계/정산전문(은행집계)".equals(s)||
						"2-20은행,기관관리".equals(s)) {
					
					System.out.println(s);
				}
				
				try {
					int a = Integer.parseInt(s);
					System.out.println(a);
				} catch(Exception e) {
					//System.out.println(e.getMessage());
				}
				
				
				/*
				String fileName = "c:\\";
		        
		        File targetFile = new File(fileName);
				targetFile.createNewFile();
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile.getPath()), "ASCII"));
				out.write("");
				out.close();	
				*/
				
		        
		    }
		}catch(IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	public static String decompose(String s) {
	    return java.text.Normalizer.normalize(s, java.text.Normalizer.Form.NFD).replaceAll("\\p{InCombiningDiacriticalMarks}+","");
	}
	
	public static void subDirList(String source){
		File dir = new File(source); 
		File[] fileList = dir.listFiles(); 
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
					System.out.println("\t fileName = " + file.getName());
					String s;
					try {
						BufferedReader in = new BufferedReader(new FileReader(file));
						
						while ((s = in.readLine()) != null) {
					        if(s.indexOf("프로모션") > 0) {
					        	//if(s.indexOf("[null]") < 0) {
					        		System.out.println(s);
					        	//}
							}
					    }
					
					} catch(Exception e) {
						System.out.println(e.toString());
					}
					
					
				}else if(file.isDirectory()){
					System.out.println("<tr><td colspan='2'>Directory = " + file.getName()+"</td></tr>");
					subDirList(file.getCanonicalPath().toString()); 
				}
			}
		}catch(IOException e){
			
		}
	}
	
	public static void fileRename(String source) {
		File dir = new File(source); 
		File[] fileList = dir.listFiles(); 
		try{
			for(int i = 0 ; i < fileList.length ; i++){
				File file = fileList[i]; 
				if(file.isFile()){
					System.out.println("fileName = " + file.getName());
					String s = file.getName();
					try {
						
						int ext = s.indexOf("jsp");
						int lastPath = file.getPath().lastIndexOf("\\");
						String newPath = file.getPath().substring(0, lastPath);
						System.out.println("\next = " + ext);
						System.out.println("\tpath = " + newPath);
						
						if(ext > 0) {
							String fileName = s.substring(0, ext);
//							System.out.println("\nrename");
//							File a = new File("C:\\test\\aaa.html");
//							a.createNewFile();							
//							System.out.println("\ngetPath = " + a.getPath());
							file.renameTo(new File(newPath+ "\\" + fileName + "html"));
						}
						
					} catch(Exception e) {
						System.out.println(e.toString());
					}
					
					
				}else if(file.isDirectory()){					
					fileRename(file.getCanonicalPath().toString()); 
				}
			}
		}catch(IOException e){
			System.out.println();
		}
	}
	
	public static void codeGenerator() {
		
		RandomStringUtils ran = new RandomStringUtils();
		String code = null;
		boolean dup = true;
		int cnt = 0;
		
		while(dup) {
			cnt++;
			code = ran.randomAlphanumeric(6).toUpperCase();
			System.out.println(code);
			if(cnt == 100) {
				dup = false;
			}			
		}
		
	}
	
	public static void lottery() {
		
		ArrayList<int[]> prvList = new ArrayList<int[]>();
		int prvNumber1[] = {3, 7, 14, 16, 31, 40};
		int prvNumber2[] = {7, 37, 38, 39, 40, 44};
		int prvNumber3[] = {16, 21, 26, 31, 36, 43};
		int prvNumber4[] = {5, 6, 26, 27, 38, 39};
		
		prvList.add(prvNumber1);
		prvList.add(prvNumber2);
		prvList.add(prvNumber3);
		prvList.add(prvNumber4);
		
		RandomUtils ran = new RandomUtils();
		boolean congratulation = false;
		boolean limit = false;
		int lmitCnt = 1000000000;
		int cnt = 0;
		
		while(!congratulation && !limit) {
			cnt++;
			int ranNo[] = new int[6];
			ranNo[0] = getNoDupNo(ran, ranNo);
			ranNo[1] = getNoDupNo(ran, ranNo);
			ranNo[2] = getNoDupNo(ran, ranNo);
			ranNo[3] = getNoDupNo(ran, ranNo);
			ranNo[4] = getNoDupNo(ran, ranNo);
			ranNo[5] = getNoDupNo(ran, ranNo);
			
			
			if(cnt == 50000) {
				//{3, 7, 14, 16, 31, 40};
				//{16, 21, 26, 31, 36, 43};
				ranNo[0] = 16;
				ranNo[1] = 21;
				ranNo[2] = 26;
				ranNo[3] = 31;
				ranNo[4] = 36;
				ranNo[5] = 43;				
			}
			
			for(int i = 0; i<prvList.size();i++) {
				
				int winNo[] = prvList.get(0);
				Arrays.sort(ranNo);
				
				if(Arrays.equals(winNo, ranNo)) {
					System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					System.out.println("$$$$$$$$$$$$$$$$$$$$ �뱀꺼! : " + cnt + "踰�㎏ $$$$$$$$$$$$$$$$$$$$");
					System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$");
					System.out.print("[");
					System.out.print(ranNo[0]);
					System.out.print(", "+ranNo[1]);
					System.out.print(", "+ranNo[2]);
					System.out.print(", "+ranNo[3]);
					System.out.print(", "+ranNo[4]);
					System.out.print(", "+ranNo[5]);
					System.out.println("]");
					
					congratulation = true;
					break;
					
				} else {
//					System.out.print("count : " + cnt+" [");
//					System.out.print(ranNo[0]);
//					System.out.print(", "+ranNo[1]);
//					System.out.print(", "+ranNo[2]);
//					System.out.print(", "+ranNo[3]);
//					System.out.print(", "+ranNo[4]);
//					System.out.print(", "+ranNo[5]);
//					System.out.println("]");
				}
				
				if(cnt > lmitCnt) {
					System.out.println("@@@@@@@@@@ "+lmitCnt+"留���� 珥�낵! @@@@@@@@@@");
					limit = true;
					break;
				}
								
			}
		}
	}
	
	public static int getNoDupNo(RandomUtils ran, int arrNo[]) {
		boolean sameYn = true;
		int ranNo = 0;
		
		while(sameYn) {
			ranNo = getRandomNo(ran, 46);
			
			if(arrNo[0] == ranNo) {
				continue;
			}
			if(arrNo[1] == ranNo) {
				continue;
			}
			if(arrNo[2] == ranNo) {
				continue;
			}
			if(arrNo[3] == ranNo) {
				continue;
			}
			if(arrNo[4] == ranNo) {
				continue;
			}
			if(arrNo[5] == ranNo) {
				continue;
			} else {
				sameYn = false;
				//System.out.println("sameYn = "+ sameYn + "no same number = "+ranNo);
			}
		}
		return ranNo;
	}
	
	public static int getRandomNo(RandomUtils ran, int range) {
		int ranNo = 0;
		boolean isZero = true;		
		while(isZero) {
			ranNo = ran.nextInt(range);
			if(ranNo != 0) isZero = false;
		}
		return ranNo;
	}
	
    public static String getMD5(String input) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            byte[] messageDigest = md.digest(input.getBytes());
            BigInteger number = new BigInteger(1, messageDigest);
            String hashtext = number.toString(16);
            // Now we need to zero pad it if you actually want the full 32 chars.
            while (hashtext.length() < 32) {
                hashtext = "0" + hashtext;
            }
            return hashtext;
        }
        catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
	
	public static void calcRound() {
		System.out.println(Math.round(100.46));
	}
	
	public boolean findTextLine() {
		boolean findYn = false;
		
		return findYn;		
	}
	
	public static void fileWrite() {
	try {
		
		String fileName = "c:\\AWTS_test";
		int cnt = 100;
		
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		SimpleDateFormat sdfymd = new SimpleDateFormat("yyyyMMdd");
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("Asia/Seoul"));
		String toDate = sdfymd.format(cal.getTime());
		RandomUtils ran = new RandomUtils();
		String mchtList[] = {"3017642","3015715","3026504","2014150","3045103","3056953","3019345","2007989","3044919","3058094","2011182","3013406","2000482","2009351","2012324","3019346","2000426","2007934","3003769","2014679","2008995","3007585","3041193","3002248","2004086","2006726","2011885","3006737","2007992","2017208","2022004","2003216","2007570","2000127","3007861","2000659","2009573","2013397","3015199","2013398","3012005","3013736","3014014","3022525","2011889","3049403","3046154","2020395","2014440","3053565","3051338","3011421","2019179","2022754","3051799","3021651","3042510","3057333","3042073","3017062","3007686","2016915","3014961","3016455","3050241","3023554","3041967","2020565","3011880","2010076","3017840","2000560","2004160","2021516","3016628","3005264","3023819","3049282","2022977","3008105","2005904","3003745","3022998","2017436","2020298","3028199","3049906","3040742","2006545","2013256","3057202","2014673","3048604","2011893","2017784","2020541","2004204","3041849","3012470","3004889"};
		
		File targetFile = new File(fileName);
		targetFile.createNewFile();
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile.getPath()), "ASCII"));
		
//		BufferedWriter out = new BufferedWriter(new FileWriter(fileName));
		String header = "H"+toDate+"                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   ";		
		String footer = "T000000013500000000000000000000000000                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       ";
		out.write(header);
		for(int i=0;i<cnt;i++) {
			int mchtSeq = ran.nextInt(99);
			long ltrxAmt = ran.nextInt(9999);
			String trxAmt = StringUtils.leftPad(String.valueOf(i+1), 4, "0");
			
			String mchtNo = mchtList[mchtSeq];
			//cal.add(Calendar.SECOND, 1);
	        toDate = sdf.format(cal.getTime());
	        String body = "D00000000001010000617115936    "+toDate+"TRAFFICI                                                                                            0000001610                                                                          "+mchtNo+"           ICIC31BB5605                                                                                        BITXBITX                                                                                            +0000000000+000000"+trxAmt+"0000023840                                                                                                    201506130015090000003960                                                                000000000020150614               ";
			out.write(body);
		}
		
		out.write(footer);
		out.close();
		
	    } catch (IOException e) {
	        System.err.println(e);
	        System.exit(1);
	    }
	}
	
	public static long calcNumber() {
		
		double ratio = Double.parseDouble("6") / 100;
		System.out.println(ratio);
		
		long tempUseAmt = 400;
		tempUseAmt += (long)Math.abs(tempUseAmt * ratio);
		return tempUseAmt;
	}
	
	public static String getLastDateOfMonth(String date) {
		if(date.length() > 6) return "";
		Calendar cal = Calendar.getInstance();
		try {
			cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6))-1);
		    cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
		    cal.set(Calendar.DAY_OF_MONTH, 1);
		    cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));		    
		} catch (Exception e) {
			throw new IllegalArgumentException("�����������щ�瑜댁� ������. date=" + date);
		}
		return DateFormatUtils.format(cal.getTime(),"yyyyMMdd");
	}
	
	
	
	
	public static void fillZero(int yournumber) {
		System.out.println(String.format("%05d", yournumber));
	}
	
	public static String getFileRow(String arr[]) {
		
		return ""+arr[0].getBytes();
	}
	
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
	
	
}
