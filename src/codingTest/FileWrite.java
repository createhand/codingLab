package codingTest;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class FileWrite {
	
	public static void main(String args[]) {
		String path = "D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\com_test";
		fileWrite();
	}
	
	public static void fileWrite() {
		
		try {
			//timezone ID
			ZoneId zoneId = ZoneId.of("GMT");
			//date format
			String DATE_FORMAT = "dd.MM.yyyy";
			String DATETIME_FORMAT = "ddMMyyyy-hh:mm:ss";
			//formatter
			DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern(DATE_FORMAT);
			DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(DATETIME_FORMAT);
			
			//경로
			String filePath = "D:\\GLN_prj\\upload\\";
			File dir = new File(filePath);
			if(!dir.exists()) {
        		dir.mkdirs();
        	}
			
			//파일
			String fileName = "test.txt";
			File targetFile = new File(filePath+fileName);
			//overwrite
			targetFile.createNewFile();
			//거래건수
			int cnt = 500;
			
			//byte stream object
			BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFile.getPath()), "UTF-8"));
			
			//date object
			LocalDate toDate = LocalDate.now(zoneId);
			
			String header = "Header"+toDate.format(dateFormatter)+"\n";		
			String footer = "footer";
			out.write(header);
			
			for(int i=0;i<cnt;i++) {
				//datetime object
		        LocalDateTime localDateTime = LocalDateTime.now(zoneId);
		        String body = "D00000000001010000617115936"+localDateTime.format(dateTimeFormatter)+"";
		        body += "\n";
				out.write(body);
			}
			
			out.write(footer);
			out.close();
		
	    } catch (IOException e) {
	        System.err.println(e); // 에러가 있다면 메시지 출력
	        System.exit(1);
	    }
	}
	
}
