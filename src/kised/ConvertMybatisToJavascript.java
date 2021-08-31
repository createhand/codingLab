package kised;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.parser.txt.CharsetDetector;

public class ConvertMybatisToJavascript {
	
	public static boolean frontBackCheck = true;
	public static boolean useRegex = true;
	public static String targetFileExt = ".xml";
	public static Charset targetFileCharset = StandardCharsets.UTF_8;
	public static String targetSuffix = "";
	
	public static void main(String args[]) {
		
		//대체대상파일 경로
		String paths[] = {
//				"D:\\KisedSDK3\\workspace\\pms_bank\\src\\main\\resources\\sqlmap\\mappers\\bea\\BEG0000_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_bank\\src\\main\\resources\\sqlmap\\mappers\\BankComm_SQL.xml",
//				"D:\\KisedSDK3\\workspace\\pms_bank\\src\\main\\resources\\sqlmap\\mappers\\exm\\batch",
				//"D:\\KisedSDK3\\workspace\\codingutils\\src\\cordingTest\\Consts.java",
				/*
				"D:\\KisedSDK3\\workspace\\pms_bank\\src\\main\\resources\\sqlmap\\mappers",
				"D:\\KisedSDK3\\workspace\\pms_com\\src\\main\\resources\\sqlmap\\mappers\\com\\COM0104_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_com\\src\\main\\resources\\sqlmap\\mappers\\com\\COM0105_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_com\\src\\main\\resources\\sqlmap\\mappers\\com\\COM0106_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_com\\src\\main\\resources\\sqlmap\\mappers\\com\\COM0107_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_com\\src\\main\\resources\\sqlmap\\mappers\\com\\COM0108_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_com\\src\\main\\resources\\sqlmap\\mappers\\com\\COM0110_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_front\\src\\main\\resources\\sqlmap\\mappers\\bmo\\BMO0101_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_front\\src\\main\\resources\\sqlmap\\mappers\\bmo\\BMO0301_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_front\\src\\main\\resources\\sqlmap\\mappers\\bmo\\BMO0306_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_front\\src\\main\\resources\\sqlmap\\mappers\\bmo\\BMO0417_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_front\\src\\main\\resources\\sqlmap\\mappers\\bmo\\BMO0418_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_front\\src\\main\\resources\\sqlmap\\mappers\\bmo\\BMO0502_SQL.xml",
				"D:\\KisedSDK3\\workspace\\pms_front\\src\\main\\resources\\sqlmap\\mappers\\bmo\\BMO0602_SQL.xml",
				*/
				//"D:\\temp\\temp\\temp_convert"
//				"D:\\KisedSDK3\\workspace\\pms_bank\\src\\main\\resources\\sqlmap\\mappers",
//				"D:\\KisedSDK3\\workspace\\pms-admin\\src\\main\\resources\\sqlmap\\mappers\\exm",
				//asis java
//				"D:\\KisedSDK3\\workspace\\ctp_admin\\src\\main\\java\\ctp",
//				"D:\\KisedSDK3\\workspace\\ctp_bank\\src\\main\\java\\ctp",
//				"D:\\KisedSDK3\\workspace\\ctp_front\\src\\main\\java\\ctp",
//				"D:\\KisedSDK3\\workspace\\ctp_common\\src\\main\\java\\ctp",
				//asis front view(jsp)
//				"D:\\KisedSDK3\\workspace\\ctp_front\\src\\main\\webapp\\WEB-INF\\jsp\\ctp"
				//asis admin view(xml, js)
//				"D:\\KisedSDK3\\workspace\\ctp_admin\\src\\main\\webapp\\xFrame\\screen",
		};

		//ASIS-TOBE 테이블, 컬럼 변환
		frontBackCheck = true;
		useRegex = true;
		for(String path : paths) {
			fileReadAndWrite(path, AsisTobeTablesConsts.tobeTobeTablesPattern, useRegex);
		}
		
		
//		/*** frontBackCheck = false ***/
//		//ASIS-TOBE 쿼리 변수명 변환
//		//1. #var# -> #{var}
//		//2. #{var} -> #{var}
//		frontBackCheck = false;
//		useRegex = false;
//		for(String path : paths) {
//			fileReadAndWrite(path, AsisTobeIbatisToMybatisVariablesConsts.asisTobeVariablesPattern, useRegex);
//		}
//		
//		/*** frontBackCheck = false ***/
//		frontBackCheck = true;
//		useRegex = false;
//		//ASIS-TOBE 조건절(if) 쿼리 변수명 변환(var -> var)
//		for(String path : paths) {
//			fileReadAndWrite(path, AsisTobeVariablesConsts.asisTobeVariablesPattern, useRegex);
//		}
	}

	public static void fileReadAndWrite(String path, String[][] patterns, boolean useRegex) {
		
		//경로
		File dir = new File(path);
		File[] files = null;
		
		if(dir.isFile()) {
			files = new File[1];
			files[0] = dir;
		} else if(dir.isDirectory()) {
			if(!dir.exists()) {
	    		dir.mkdirs();
	    	}
			files = dir.listFiles();
		}
		
		for(File file : files) {
			
			try {
			
				String absolutePath = file.getAbsolutePath();
				
				if(file.isDirectory()) {
					fileReadAndWrite(absolutePath, patterns, useRegex);
				}
				
				if(file.getName().endsWith(targetFileExt)) {
					
					// Read the content from file
					FileInputStream fis = new FileInputStream(absolutePath);
					BufferedInputStream bis = new BufferedInputStream(fis);
					//get file encoding
					CharsetDetector detector = new CharsetDetector();
					detector.setText(bis);
					
					Reader rd = new InputStreamReader(bis, Charset.forName(detector.detect().getName()));
					BufferedReader bufferedReader = new BufferedReader(rd);
					
					System.out.println("file : "+absolutePath);
					StringBuffer fileLines = new StringBuffer();
					
					try {
					    String line = bufferedReader.readLine();
					    boolean read = false;
					    while(line != null) {
//					    	if(line.indexOf("weaWeeWesUssDao_weaWeeWesUssTotBexpList\"") > -1) {
//					    	if(line.indexOf("strSbjtOrgn") > -1) {
					    	if(line.indexOf("strSbjtBexpCpst") > -1) {
					    		read = true;
					    	}
//					    	if(line.indexOf("</select>") > -1) {
					    	if(line.indexOf("</sql>") > -1) {
					    		read = false;
					    	}
					    	if(read) {
					    		line = line.replace("<![CDATA[", "");
					    		line = line.replace("]]>", "");
					    		
					    		if(line.indexOf("<if test") > -1) {
					    			//line = line.replace("<if test=\"", "if(\"{dataset.");
					    			line = line.replace("<if test=\"", "");
					    			line = line.trim();
					    			line = "if(\"{dataset." + line.substring(0, line.indexOf(" ")) + "}\" != '') {";
					    			//if("{dataset.sbjtId}" != '') {
//					    			<if test=\"tsksId != '' and tsksId != null \">
//					    					if("{dataset.tsksId}" != '')
					    		} else if(line.indexOf("</if>") > -1) {
					    			line = "}";
					    		} else {
					    			line = "sql += \"\\r\\n"+line+"\";";
					    		}
					    		if(line.indexOf("#{") > -1) {
					    			line = line.replace("#{", "\" + \"{dataset.");
					    		}
					    		if(line.indexOf("${") > -1) {
					    			line = line.replace("${", "\" + \"{dataset.");
					    		}
					    		fileLines.append(line + System.lineSeparator());
					    	}
					    	line = bufferedReader.readLine();
					    }
					    System.out.println(fileLines.toString());
					} catch (Exception e) {
					    e.printStackTrace();
					}
					
//					if(fileChanged) {
//					    FileOutputStream fos = new FileOutputStream(absolutePath+targetSuffix);
//					    OutputStreamWriter osw = new OutputStreamWriter(fos, targetFileCharset);
//					    String encodedString = new String(fileLines.toString().getBytes(targetFileCharset), targetFileCharset);
//					    
//					    osw.write(encodedString);
//					    osw.flush();
//					    osw.close();
//					}
				}
			
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static boolean isMatch(String str) {
		//영문 또는 언더바(_) 체크
		if(Pattern.matches("^[a-zA-Z0-9]*$", str)) {
			return true;
		}
		if(str.equals("<")
				|| str.equals("/")
				|| str.equals("_")) {
			return true;
		}
		return false;
	}
	
	public static String changeWord(String line, String find, String replace) {
		Pattern pattern = Pattern.compile(find);
		Matcher matcher = pattern.matcher(line);
		
		if(matcher.find()) {
//			System.out.println("대상:"+find+" -> "+replace);
//			System.out.println("원문:"+line);
			line = matcher.replaceAll(replace);
			System.out.println("정규식 치환:"+line);
    		System.out.println("\n\n");
		}
		return line;
	}
	
	
}