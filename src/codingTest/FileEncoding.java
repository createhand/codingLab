package codingTest;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;

import org.apache.commons.lang3.StringUtils;
import org.apache.tika.parser.txt.CharsetDetector;

public class FileEncoding {
	
	public static void main(String args[]) {
		String paths[] = {
				"D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\admin",
//				"D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\java\\com\\hmf\\framework\\core\\pt\\ibs\\admin\\service\\",
//				"D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\loan\\saidol",
//				"D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\loan\\dreamloan",
//				"D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\loan\\hanaloan",
//				"D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\loan\\hploan",
//				"D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\loan\\sunshine",
//				"D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\loan\\themanyloan"
		};
//		changeFileEncoding("D:\\workspace\\git\\hmf-svr-core-pt-ibs\\src\\main\\webapp\\WEB-INF\\views\\admin");
		for(String path : paths) {
			fileReadAndWrite(path);
		}
	}

	public static void fileReadAndWrite(String path) {
		String[][] patterns = {
//				{"<%@ include file=\"/include/aceiCommon.jsp\"%>","<%//@ include file=\"/include/aceiCommon.jsp\"%>"},
//				{"<%@ taglib prefix=\"u\" uri=\"/WEB-INF/tld/utaglib.tld\" %>", ""},
//				{"<%@ taglib prefix=\"u\"      uri=\"/WEB-INF/tld/utaglib.tld\" %>",""},
//				{"<%@ taglib prefix=\"tabWeb\" uri=\"/WEB-INF/tld/ACEI_WEBTag.tld\" %>",""},
//				{"<%@ taglib prefix=\"keyPad\" uri=\"/WEB-INF/tld/KeyPad.tld\" %>",""},
//				{"<%=aceiRequest.getPageCode()%>","<%//=aceiRequest.getPageCode()%>"},
//				{"<!DOCTYPE html PUBLIC \"-//W3C//DTD XHTML 1.0 Transitional//EN\" \"http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd\">", ""},
//				{"<html xmlns=\"http://www.w3.org/1999/xhtml\" xml:lang=\"ko\" lang=\"ko\">", "<html lang=\"ko\">"},
//				{"systemNavigation", "webSiteNavigation"},
//				{"gotoSystemPageNaviAN", "webSiteNavigation"},
//				{"<%@ include file=\"/include/help.jsp\"%>", ""},
//				{"<%@ include file=\"/error/acei_error.jsp\"%>", "<%//@ include file=\"/error/acei_error.jsp\"%>"},
//				{"<%@ page language=\"java\" contentType=\"text/html; charset=utf-8\" %>", "<%@ page language=\"java\" contentType=\"text/html;charset=utf-8\" %>"},
//				{"aceiSession.getLoginAttribute(\"CUST_NAME\")", "user.getCustName()"},
//				{"aceiRequest", "request"},
//				{"aceiSession", "user"},
//				{"<u:variable id=", "String"},
//				{"\"               select=\"/응답부/", " = dataBody.get(\""},
//				{"\"          select=\"/응답부/", " = dataBody.get(\""},
//				{"\"               select=\"/공통부/", " = dataBody.get(\""},
//				{"\"             select=\"/응답부/", " = dataBody.get(\""},
//				{"\"       select=\"/응답부/", " = dataBody.get(\""},
//				{"\"         select=\"/응답부/", " = dataBody.get(\""},
//				{"\"           select=\"/응답부/", " = dataBody.get(\""},
//				{"\"            select=\"/응답부/", " = dataBody.get(\""},
//				{"\"        select=\"/응답부/", " = dataBody.get(\""},
//				{"<%@ include file=\"/include/aceiCommonNotLayout.jsp\"%>", ""},
//				{"<%@ include file=\"/include/aceiCommonSecurity.jsp\"%>", ""},
//				{"<%@ include file=\"/include/aceiCommonJs.jsp\"%>", ""},
//				{"<%@ include file=\"/header.jsp\"%>", ""},
//				{"var xmlDoc = getDocument(\"Dummy_Service\");", ""},
//				{"setServiceID(xmlDoc, \"Dummy_Service\");", ""},
//				{"setString(xmlDoc, \"pageCode\",\"<%=mPageCode%>\");", ""},
//				{"aceiSignData", "signSubmitForm"},
//				{"setString(xmlDoc,", "addFormAttribute(obj,"},
//				{"xmlDoc = getDocument(\"Dummy_Service\");", ""},
//				{"var xmlDoc = getDocument(\"Common_Host_Service01\");", ""},
//				{"setServiceID(xmlDoc, \"Common_Host_Service01\");", ""},
//				{"var xmlDoc = getDocument(\"dummy\");", ""},
//				{"var resultDoc = getDocument( result );", ""},
//				{"var retDoc      = getDocument(resultCData);", ""},
//				{"var resultCData = getCData(data, \"ACEI_RESULT\");", ""},
//				{"var retDoc	  = getDocument(resultCData);", ""},		
//				{"xmlDoc = getDocument(\"CertOnlyCheckService\");", ""},
//				{"setServiceID(xmlDoc, \"CertOnlyCheckService\");", "//setServiceID(xmlDoc, \"CertOnlyCheckService\");"},
//				{"doSubmit_popup(\"\", \"\", \"\", xmlDoc, \"직장명검색\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y);", "submitPopup(\"pop/COM25P101\", \"직장명검색\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y, popupForm);"},
//				{"doSubmit_popup(\"\", \"\", \"\", xmlDoc, \"직위검색\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y);", "submitPopup(\"pop/COM25P102\", \"직위검색\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y, popupForm);"},
//				{"doSubmit_popup(\"\", \"\", \"\", xmlDoc, \"표준분류코드\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y);", "submitPopup(\"pop/COM25P104\", \"표준분류코드\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y, popupForm);"},
//				{"doSubmit_popup(formObj, \"\", \"\", xmlDoc, \"핵심설명서\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y);", "		submitPopup(\"loan/../LIM010107_01\", \"핵심설명서\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y, popupForm);"},
//				{"doSubmit_popup(formObj, \"\", \"\", xmlDoc, \"대출상품설명서\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y);", "submitPopup(\"loan/../lim/LIM010107_02\", \"대출상품설명서\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y, popupForm);"},
//				{"doSubmit_popup(\"\", \"\", \"\", xmlDoc, \"고객정보취급방침\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y);", "submitPopup(\"loan/../custpop1\", \"고객정보취급방침\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y, popupForm);"},
//				{"doSubmit_popup(\"\", \"\", \"\", xmlDoc, \"고객권리안내문\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y);", "submitPopup(\"loan/../custpop2\", \"고객권리안내문\", \"toolbar=no,status=no,scrollbars=yes\", pop_x, pop_y, popupForm);"},
//				{"CUST_NAME", "BD_P_NAME"},
//				{"HAND_PHONENO_1", "BD_P_PH21"},
//				{"HAND_PHONENO_2", "BD_P_PH22"},
//				{"HAND_PHONENO_3", "BD_P_PH23"},
//				{"MOB_GUBUN", "BD_P_PH2_COM"},
				
				//admin
//				{"/js/ace.js", "/js/admin/admin.js"},
//				{"/js/admin_design.js", "/js/admin/admin_design.js"},
//				{"/js/formchk.js", "/js/admin/formchk.js"},
//				{"/new/css/admin.css", "/css/admin.css"},
//				{"<%@include file=\"/admin/checkSession.jsp\"	%>", "<%@ include file=\"../include/adminCommon.jsp\" %>"},
//				{"<%@ include file=\"../checkSession.jsp\"  %>", "<%@ include file=\"../include/adminCommon.jsp\" %>"},
//				{"<%@include file=\"/admin/header.jsp\" %>", "<%@include file=\"../header.jsp\" %>"},
//				{"<%@include file=\"/admin/include/", "<%@include file=\"../include/"},
//				{"/new/new_img/", "/new_img/"},
//				{"<%@ page import = \"com.acebank.bbs.*\"%>", ""},
//				{"<%@ page import = \"com.acebank.interest.*\"%>", ""},
//				{"/new/js/hana.js", "/js/hana.js"},
//				{"<script type=\"text/javascript\" src=\"/new/js/jquery.js\"></script>", "<script type=\"text/javascript\" src=\"/js/jquery-3.5.0.min.js\"></script>\r\n" +"<script type=\"text/javascript\" src=\"/js/jquery-ui.min.js\"></script>"},
//				{"/new/js/jquery.ui.datepicker.js", "/js/jquery.ui.datepicker.js"},
//				{"<%@ include file=\"../include/adminCommon\" %>", "<%@ include file=\"../include/adminCommon.jsp\" %>"},
//				{"<%@include file=\"../header\" %>", "<%@include file=\"../header.jsp\" %>"},
//				{"<%@include file=\"../include/deposit_left\" %>", "<%@include file=\"../include/deposit_left.jsp\" %>"},
//				{"DAOManager dm = new DAOManager();", ""},
//				{"dm.", "adminService."}
				{"new BBSConnectionResource", "getDataSource.getConnection"},
				{"BBSConnectionResource", "Connection"},
				{"BDFPreparedStatement", "PreparedStatement"}
			};
		
		//경로
		File dir = new File(path);
		if(!dir.exists()) {
    		dir.mkdirs();
    	}
		
		File[] files = dir.listFiles();
		for(File file : files) {
			
			try {
			
				String absolutePath = file.getAbsolutePath();
				
				if(file.isDirectory()) {
					fileReadAndWrite(absolutePath);
				}
				
				if(file.getName().indexOf(".java") > -1) {
//				if(file.getName().indexOf(".jsp") > -1) {
//				if(file.getName().indexOf(".bak") > -1) {
					
					// Read the content from file
					FileInputStream fis = new FileInputStream(absolutePath);
					BufferedInputStream bis = new BufferedInputStream(fis);
					Reader rd = new InputStreamReader(bis);
					BufferedReader bufferedReader = new BufferedReader(rd);
					
					CharsetDetector detector = new CharsetDetector();
					detector.setText(bis);
					
					System.out.println("absolutePath : "+absolutePath);
					
					StringBuffer fileLines = new StringBuffer();
						
					try {
					    String line = bufferedReader.readLine();
					    
					    while(line != null) {
							for(int i=0;i<patterns.length;i++) {
								String pattern = patterns[i][0];
								String replace = patterns[i][1];
//								    System.out.println("line:"+line);
								if(line.indexOf(pattern) > -1) {
						    		line = StringUtils.replace(line, pattern, replace);
					    		}
							}	
							fileLines.append(line + System.lineSeparator());
							line = bufferedReader.readLine();
					    }
					} catch (Exception e) {
					    e.printStackTrace();
					}
					
				    FileOutputStream fos = new FileOutputStream(absolutePath);
				    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
				    String encodedString = new String(fileLines.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
				    
				    osw.write(encodedString);
				    osw.flush();
				    osw.close();
				}
			
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	
	
	public static void changeFileEncoding(String path) {
		
		String[][] patterns = {
				{"EUC-KR","utf-8"},
				{"euc-kr","utf-8"}
			};
		
		//경로
		File dir = new File(path);
		if(!dir.exists()) {
    		dir.mkdirs();
    	}
		
		File[] files = dir.listFiles();
		for(File file : files) {
			
			try {
			
				String absolutePath = file.getAbsolutePath();
				
				if(file.isDirectory()) {
					changeFileEncoding(absolutePath);
				}
				
				if(file.getName().indexOf(".jsp") > -1) {
					
					// Read the content from file
					FileInputStream fis = new FileInputStream(absolutePath);
					BufferedInputStream bis = new BufferedInputStream(fis);
					Reader rd = new InputStreamReader(bis, "euc-kr");
//					InputStreamReader isr = new InputStreamReader(fis);
					BufferedReader bufferedReader = new BufferedReader(rd);
					
					CharsetDetector detector = new CharsetDetector();
					detector.setText(bis);
					String fileEncoding = detector.detect().getName();
					
					System.out.println("absolutePath : "+absolutePath);
					System.out.println("encoding : "+fileEncoding);

					StringBuffer fileLines = new StringBuffer();
						
					try {
					    String line = bufferedReader.readLine();
					    
					    while(line != null) {
							for(int i=0;i<patterns.length;i++) {
								String pattern = patterns[i][0];
								String replace = patterns[i][1];
//							    System.out.println("line:"+line);
								if(line.indexOf(pattern) > -1) {
						    		line = StringUtils.replace(line, pattern, replace);
					    		}
							}	
							fileLines.append(line + System.lineSeparator());
							line = bufferedReader.readLine();
					    }
					} catch (Exception e) {
					    e.printStackTrace();
					}
					
					
				    if(!StringUtils.equals(fileEncoding, StandardCharsets.UTF_8.name())) {
				    	
					    FileOutputStream fos = new FileOutputStream(absolutePath);
					    OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
					    String encodedString = new String(fileLines.toString().getBytes(StandardCharsets.UTF_8), StandardCharsets.UTF_8);
					    
					    osw.write(encodedString);
					    osw.flush();
					    osw.close();
					    
					    System.out.println(file.getName()+" changed!");
				    }
				    
				}
			
			} catch(Exception e) {
				e.printStackTrace();
			}
			
		}
	}
		
	
}