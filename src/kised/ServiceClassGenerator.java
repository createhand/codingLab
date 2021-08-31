package kised;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.parser.txt.CharsetDetector;

public class ServiceClassGenerator {

	//###################### 확인 ######################
	final static String projectPath = "pms_bank"; 
	final static String changeName = "박서찬";
	//#################################################
	
	final static String basePath = "D:\\KisedSDK3\\workspace\\"+projectPath+"\\src\\main\\java\\pms\\service";
	final static String examplePath = basePath + "\\exm";
	final static String examplePackage = ".exm";
	final static String exampleServiceId = "EXM0101";
	final static String exampleDate = "YYYY-MM-DD";
	final static String exampleName = "김개발";
	
	
	public static void main(String args[]) {
		
		String serviceIds[] = {
				//분할납부등록(팝업)
//				"BEC0207",
				//환수금액정산현황(탭)
//				"BEC0209",
				//납부계획변경신청
//				"BEC0202",
				//납부계획변경신청 관리(팝업)
//				"BEC0203",
				//납부계획변경승인
//				"BEA0201",
				//납부계획변경 승인요청 상세 조회(팝업)
//				"BEA0202",
				//환수금대상자현황(탭)
//				"BEC0206",
				//지원금지급등록
//				"BED0202",
//				//지원금지급승인
//				"BED0203",
//				//지원금지급취소
//				"BED0204",
//				//지원금지급현황
//				"BED0205",
//				//지원금지급현황 과제별상세내역 조회(팝업)
//				"BED0206",
//				//과제별사용현황
//				"BEJ0201",
//				//과제별 비목별 사용내역 조회(팝업)
//				"BEJ0202",
//				//사업비 구성내역 조회(팝업)
//				"BEJ0203",
//				//일자별시재관리(탭) - 우리은행
//				"BEE0211",
//				//일자별시재관리(주관기관)(탭)-우리은행
//				"BEE0218",
//				//일자별시재관리(주관기관)(탭)-신한은행
//				"BEE0235",
//				//입금거래 목록 조회(팝업) - 우리은행
//				"BEE0212",
//				//입금거래 상세 조회(팝업) - 우리은행
//				"BEE0213",
//				//미확인 출금거래 목록 조회(팝업)
////				"BEE0217",
//				//일자별시재관리(입금)(탭) - 신한은행
//				"BEE0229",
//				//입금거래 목록 조회(팝업) - 신한은행
//				"BEE0230",
//				//입금거래 상세 조회(팝업) - 신한은행
//				"BEE0233",
//				//미확인 입금거래 목록 조회(팝업) - 우리은행
//				"BEE0214",
//				//출금거래 목록 조회(팝업) - 우리은행
//				"BEE0215",
//				//출금거래 상세 조회(팝업) - 우리은행
//				"BEE0216",
//				//출금거래 목록 조회(팝업) - 신한은행
//				"BEE0231",
//				//일자별시재관리(사업비)(탭) - 신한은행
//				"BEE0232",
//				//출금거래 목록 조회(팝업) - 신한은행
//				"BEE0234",
//				//카드등록
//				"BEF0103",
//				//계좌변경신청
//				"BEF0102",
//				//계좌변경신청
//				"BEF0102",
//				//납부관리
//				"BEC0101",
//				//납부현황(탭)
//				"BEC0102",
//				//납부변경신청(탭)
//				"BEC0103",
//				//납부변경신청 상세 조회(팝업)
//				"BEC0104",
//				//납부변경신청 등록(팝업)
//				"BEC0107",
//				//자동이체내역조회
//				"BEE0219",
//				//자동이체내역조회(신한)
//				"BEE0236",
//				//납부계획변경신청
//				"BEC0202",
//				//환수금대상자현황(탭)
//				"BEC0206",				
		};

		for(String serviceId : serviceIds) {
			//디렉토리 생성
			dirGenerate(serviceId);
			
			fileReadAndWrite(serviceId, examplePath);
		}
	}

	public static void fileReadAndWrite(String serviceId, String path) {
		
		String lowerServiceCategory = serviceId.substring(0, 3).toLowerCase();
		String serviceCategory = serviceId.substring(0, 3).toLowerCase();
		String serviceCategoryPath = basePath + "\\" + serviceCategory;
		
		//경로
		File dir = new File(path);
//		if(!dir.exists()) {
//    		dir.mkdirs();
//    	}
		
		File[] files = dir.listFiles();
		for(File file : files) {
			
				//서브디렉토리 조회
				if(file.isDirectory()) {
//					System.out.println(file.getAbsolutePath());
					fileReadAndWrite(serviceId, file.getAbsolutePath());
					continue;
				}
				
				if(file.getName().equals("ASISMapper.java")) {
					continue;
				}
				
				String sourceAbsolutePath = file.getAbsolutePath();
				String parentDirName = file.getParentFile().getName();
				String targetFileName = file.getName().replaceAll(exampleServiceId, serviceId);
				String targetPath = null;
				
				switch (parentDirName) {
				case "exm":
					targetPath = serviceCategoryPath;
					break;
				case "db":
					targetPath = serviceCategoryPath + "\\vo\\" + parentDirName;
					break;
				case "svc":
					targetPath = serviceCategoryPath + "\\vo\\" + parentDirName;
					break;
				default:
					targetPath = serviceCategoryPath + "\\" + parentDirName;
					break;
				}
				
				String targetAbsolutePath = targetPath + "\\" + targetFileName;
				
				System.out.println("============================");
				System.out.println("서비스ID:"+serviceId);
				System.out.println("클래스파일:"+targetFileName+" 생성");
				System.out.println(targetAbsolutePath);
				System.out.println("============================");
				
				
				String[][] fileContentsPatterns = {
						//package
						{examplePackage, "."+lowerServiceCategory},
						//serviceId
						{exampleServiceId, serviceId},
						//serviceId lower case
						{exampleServiceId.toLowerCase(), serviceId.toLowerCase()},
						//date
						{exampleDate, LocalDate.now().format(DateTimeFormatter.ofPattern("YYYY-MM-dd"))},
						//name
						{exampleName, changeName},
				};
				
				//copy & replace
				fileContentsReplace(sourceAbsolutePath, targetAbsolutePath, fileContentsPatterns);
				
		}
	}
	
	//디렉토리(패키지) 생성
	public static void dirGenerate(String serviceId) {
		String serviceCategory = serviceId.substring(0, 3).toLowerCase();
		String serviceCategoryPath = basePath + "\\" + serviceCategory;
		String[] servicePath = {
				//base dir
				serviceCategoryPath,
				//impl dir
				serviceCategoryPath + "\\impl",
				//mapper dir
				serviceCategoryPath + "\\mapper",
				//vo dir
				serviceCategoryPath + "\\vo",
				//vo db dir
				serviceCategoryPath + "\\vo\\db",
				//vo svc dir
				serviceCategoryPath + "\\vo\\svc"
		};
		
		for(String path : servicePath) {
			File dir = new File(path);
			if(!dir.exists()) {
				dir.mkdirs();
			}
		}
		
	}
	
	public static void fileContentsReplace(String sourceAbsolutePath, String targetAbsolutePath, String[][] fileContentsPatterns) {
		
		try {
			// Read source file
			FileInputStream fis = new FileInputStream(sourceAbsolutePath);
			BufferedInputStream bis = new BufferedInputStream(fis);
			Reader rd = new InputStreamReader(bis);
			BufferedReader bufferedReader = new BufferedReader(rd);
			
			CharsetDetector detector = new CharsetDetector();
			detector.setText(bis);
			
			StringBuffer fileLines = new StringBuffer();
		    String line = bufferedReader.readLine();
		    
		    // Replace fileContentsPatterns
		    while(line != null) {
				for(int i=0;i<fileContentsPatterns.length;i++) {
					String pattern = fileContentsPatterns[i][0];
					String replace = fileContentsPatterns[i][1];
//						    System.out.println("line:"+line);
					if(line.indexOf(pattern) > -1) {
			    		line = StringUtils.replace(line, pattern, replace);
		    		}
				}	
				fileLines.append(line + System.lineSeparator());
				line = bufferedReader.readLine();
		    }
			
		    // Write target file
		    FileOutputStream fos = new FileOutputStream(targetAbsolutePath);
		    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
		    String encodedString = new String(fileLines.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
		    
		    osw.write(encodedString);
		    osw.flush();
		    osw.close();
		
		} catch(Exception e) {
			e.printStackTrace();
		}		
		
	}
	
}