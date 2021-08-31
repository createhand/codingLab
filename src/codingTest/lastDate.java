package codingTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.TimeZone;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.commons.lang.time.DateFormatUtils;




public class lastDate {
	
	public static void main(String args[]) {
		
		Calendar cal = new GregorianCalendar();
		cal.add(Calendar.MONTH, -1);
		System.out.println(DateFormatUtils.format(cal.getTime(), "yyyyMM"));
		
		
		//String userKey = "sk" + "02222" + StringUtils.leftPad(String.valueOf(99999919), 8, "0");
		//sk0201503001638
		//sk0222299999919
		//fileWrite();
		//fileList();
		//subDirList("D:\\log_data\\apilog\\gw4");
		
		
		
		/*
		String s = "s";
		
		try {		
			
			System.out.println(s.substring(2,8));
			
		} catch(Exception e) {
			System.out.println(e);
			System.out.println(s);			
		}
		*/
		

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
	
	public static void fileList() {
		String filePath = "C:\\jsp\\";
		File jobFileDir = new File(filePath);
		File[] jobFileArray = jobFileDir.listFiles();
		try {
			for(int i=0;i<jobFileArray.length;i++) {
				
				if(jobFileArray[i].isFile()) {
					String fileName = filePath+jobFileArray[i].getName();
					File targetFile = new File(fileName);
					BufferedReader in = new BufferedReader(new FileReader(targetFile));				
					String s = in.readLine();
					s = s.replaceAll("<%", "");
					s = s.replaceAll("%>", "");
					s = s.replaceAll("-", "");
					System.out.println("<tr><td>"+jobFileArray[i].getName()+"</td><td>"+s+"</td></tr>");
				} else {
					
				}
//			      while ((s = in.readLine()) != null) {
//			        System.out.println(s);
//			      }
			      
				//System.out.println("isFile = "+jobFileArray[i].isFile()+" fileName = "+filePath+jobFileArray[i].getName());
				
			}
		} catch(Exception e) {
			
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
