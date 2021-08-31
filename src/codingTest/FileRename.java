package codingTest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;

public class FileRename {
	
	public static void main(String args[]) {
//		fileRename();
		fileMoveToPackage();
	}
	
	public static void fileMoveToPackage() {
		try {
			//경로
			String filePath = "D:\\workspace\\aceibank\\src";
			File dir = new File(filePath);
			if(!dir.exists()) {
        		dir.mkdirs();
        	}
			
			File[] files = dir.listFiles();
			for(File file : files) {
				if(file.getName().indexOf(".java") > -1) {
					
					FileReader fr = new FileReader(file);
					BufferedReader br = new BufferedReader(new FileReader(file));
					String fileName = file.getName();
					
					String line = null;
					while((line = br.readLine()) != null) {
						if(line.indexOf("package") > -1) {
							
							String movePath = line.replaceAll(" ", "");
							movePath = movePath.replaceAll("package", "");
							movePath = movePath.replaceAll(";", "");
							movePath = movePath.replaceAll("\\.", "\\\\");
							
							String classPackage = movePath+"\\"+movePath;
							File packageDir = new File(classPackage);
							if(!packageDir.exists()) {
								packageDir.mkdirs();
							}
							
							String moveFilePath = filePath + "\\" + movePath;
							System.out.println("moveFilePath:"+moveFilePath);
							FileUtils.copyFileToDirectory(file, new File(moveFilePath), true);
							
							break;
						}
					}
					
					fr.close();
					br.close();
					
				}
			}
			
		
	    } catch (Exception e) {
	    	e.printStackTrace();
	        System.err.println(e); // 에러가 있다면 메시지 출력
	        System.exit(1);
	    }
	}
	
	public static void fileRename() {
		try {
			//경로
			String filePath = "D:\\workspace\\aceibank\\src";
			File dir = new File(filePath);
			if(!dir.exists()) {
        		dir.mkdirs();
        	}
			
			File[] files = dir.listFiles();
			for(File file : files) {
				if(file.getName().indexOf(".java") > -1) {
					String fileName = file.getName();
					int end = fileName.indexOf(".java");
					int start = fileName.indexOf(".java") - 13;
					String replaceFileName = fileName.replaceAll(fileName.substring(start, end), "");
					System.out.println("fileName:"+fileName);
					System.out.println("replace:"+replaceFileName+"\n\n");
					file.renameTo(new File(filePath + "\\" + replaceFileName));
				}
			}
			
		
	    } catch (Exception e) {
	        System.err.println(e); // 에러가 있다면 메시지 출력
	        System.exit(1);
	    }
	}
}
