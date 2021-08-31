package kised;

import java.sql.Clob;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;

import codingTest.Consts;
import oracle.jdbc.driver.OracleConnection;

public class ReadEffelLogRequest {
	
	public static Map<String, String> variables = new HashMap<String, String>();
	public static String[] screenXmlPath = {
			"D:\\KisedSDK3\\workspace\\pms-admin\\src\\main\\webapp\\static\\screen",
			"D:\\KisedSDK3\\workspace\\pms_front\\src\\main\\webapp\\static\\screen"
	};
	public static boolean prod = true;
	public static String dbUrl = Consts.TOBE_DB_URL;
	public static String dbUser = Consts.TOBE_DB_ID;
	public static String dbPassword = Consts.TOBE_DB_PWD;
	
	//SERVICE CODE
	/*
SELECT 
    '{'||'"'||SERVICE_CODE||'","'||DECODE(SERVICE_NAME, NULL, '서비스명', SERVICE_NAME)||'"}'  AS STR
FROM (
    SELECT 
        A.SERVICE_CODE,
        (SELECT TRIM(MENU_NAME) FROM EFFEL_MENU_INFO
            WHERE SCREEN_ID LIKE SUBSTR(A.SERVICE_CODE, 0, 7)||'%'
            AND ROWNUM = 1) SERVICE_NAME
    FROM PMS_ADM.EFFEL_LOG_SVCCALL A
    WHERE A.SERVICE_CODE LIKE 'BE%'
    GROUP BY A.SERVICE_CODE
)
ORDER BY 1
	 */
	
	//등록대상 SERVICE CODE
	//@@@@@@@@@@ 주의 @@@@@@@@@@ 실행시 기존 SERVICE CODE 삭제 후 등록 @@@@@@@@@@
	public static String[][] serviceCodes = {
			{"BEC0104S03", "BEC0103N01"},
	};
	
	
	//INSERT SERVICE
	public static String serviceInsertQuery = 
			"Insert into EFFEL_SERVICE_INFO "
			+ "	(SERVICE_CODE,SERVICE_NAME,SERVICE_ATTR,SERVICE_CHNL,SERVICE_USER,SERVICE_SCREEN,SERVICE_IP,AVAIL_HOL_YN,AVAIL_TIME_ST,AVAIL_TIME_ED,SERVICE_DATE_ST,SERVICE_DATE_ED,PREVENT_SMTM_YN,PREVENT_DUPL_YN,PREVENT_MIDDLE_YN,PREVENT_REUSED_YN,SERVICE_LOCK_YN,SERVICE_LOCK_RSN,SERVICE_LOCK_ST,SERVICE_LOCK_ED,SERVICE_USE_YN,NEED_LOGIN_YN,INC_SESSIONTIME_YN,LOG_LEVEL,EXTRA_INFO,REG_GID,REG_DTTM,REG_PGM,REG_USER,UPD_GID,UPD_DTTM,UPD_CNT,UPD_PGM,UPD_USER,POSTPROCESS_YN,POSTPROCESS_SERVICE) "
			+ " values "
			+ " (?,?,?,'test',null,?,'0.0.0.0/0','Y','0000','2400','20210104','99991231','Y','Y','N','N','N','.','999912310100','999912310100','Y','Y','Y','DEFAULT',null, ? ,'20210119161340.878','ADM0102M01',null,null,null,null,null,null,null,null) "
			;
	
	//INSERT INOUT
//	public static String serviceIOInsertQuery = 
//			"Insert into EFFEL_INOUT_INFO "
//			+ " (SERVICE_CODE,INOUT,IDX,FLD,FLDNM,COMMENTS,TYPE,LEN,UIFLD,UIMSG,EN_MSG,EXT,REG_GID,REG_DTTM,REG_PGM,REG_USER,UPD_GID,UPD_DTTM,UPD_CNT,UPD_PGM,UPD_USER) "
//			+ " values "
//			+ " (?,?,?,?,?,'test','*',null,null,null,null,null,?,'20210125170124.342','ADM0103M01',null,?,'20210125170124.342',0,'ADM0103M01',null) "
//			;
	
	public static void main(String args[]) {
		
		if(prod) {
			//운영DB
			dbUrl = Consts.TOBE_PROD_DB_URL;
			dbUser = Consts.TOBE_PROD_DB_ID;
			dbPassword = Consts.TOBE_PROD_DB_PWD;
		}
		
		long startTime = System.currentTimeMillis();
		
		try {
//			for(String path : screenXmlPath) {
//				GetWebsquareDataCollection.readAndReplace(path);
//			}
			
			insertServiceData(serviceCodes);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		System.out.println((System.currentTimeMillis()-startTime)+" ms");
	}
	
	public static void insertServiceData(String[][] serviceCode) throws SQLException {
    	
		OracleConnection conn = null;
    	PreparedStatement screenPstmt = null;
    	ResultSet screenIdResult = null;
    	ObjectMapper mapper = new ObjectMapper();
    	
    	try {
    		
    		conn = (OracleConnection)DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        	//conn.setAutoCommit(false);
    		
    		for(int i=0;i<serviceCode.length;i++) {
    			
    			//Get screen ID
    			StringBuilder screenId = new StringBuilder();
	    		StringBuilder screenIdData = new StringBuilder();
//	    		screenIdData.append(
//	    				"SELECT SERVICE_CODE, SCREEN_ID FROM EFFEL_LOG_SVCCALL " + 
//	    				"WHERE SERVICE_CODE = ? " + 
//	    				"GROUP BY SERVICE_CODE, SCREEN_ID");
//	    		screenPstmt = conn.prepareStatement(screenIdData.toString());
//	    		screenPstmt.setString(1, serviceCode[i][0]);
//	    		screenIdResult = screenPstmt.executeQuery();
//	    		
//    			while(screenIdResult.next()) {
//    				if(StringUtils.isNotBlank(screenIdResult.getString("SCREEN_ID"))) {
//    					screenId.append(screenIdResult.getString("SCREEN_ID")+",");
//    				}
//    			}
//    			if(screenId.length() >= 8) {
//    				screenId.delete(screenId.length()-1, screenId.length());
//    			}
    			
    			//Get log
//	    		Map<String, JsonNode> tranData = getEffelLogRequestData(serviceCode[i][0]);
	    		
	    		//get table comments
//	    		JsonNode reqDat = tranData.get("req");
//	    		JsonNode resDat = tranData.get("res");
//	    		if(reqDat != null && resDat != null) {
	    			
//	    			deleteServiceIOData(serviceCode[i][0]);

//	    			String req = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqDat);
//	    			String res = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resDat);
	    			
	    			PreparedStatement servicePstmt = conn.prepareStatement(serviceInsertQuery.toString());
	    			//INSERT SERVICE
	    			//serviceCode, serviceName, serviceAttr, serviceScreen,GID('20210119PMSDEV1 161340590009R00000001')
	    			servicePstmt.setString(1, serviceCode[i][0]);
	    			servicePstmt.setString(2, "서비스명");
	    			servicePstmt.setString(3, serviceCode[i][0].substring(7, 8));
	    			servicePstmt.setString(4, screenId.toString());
	    			servicePstmt.setString(5, "20210512PMSDEV1 101340590009R00000001");
	    			try {
	    			int result = servicePstmt.executeUpdate();
	    			System.out.println("["+serviceCode[i][0]+"]SERVICE INSERT:"+result);
	    			} catch(Exception e) {
//	    				e.printStackTrace();
	    				System.out.println("이미 등록된 서비스["+serviceCode[i][0]+"]로 화면ID["+serviceCode[i][1]+"]만 UPDATE");
	    				updateServiceIOData(serviceCode[i][0], serviceCode[i][1]);
	    				continue;
	    			}

	    			
	    			System.out.println("==============="+serviceCode[i][0]+"===============");
	    			System.out.println("screenId:"+screenId.toString());
	    			System.out.println("====REQ====");
	    			//System.out.println(req);
	    			int inIdx = 0;
//	    			for(Iterator<String> j = reqDat.getFieldNames(); j.hasNext() ;) {
//	    				
//	    				PreparedStatement serviceIOPstmt = conn.prepareStatement(serviceIOInsertQuery.toString());
//	    				inIdx++;
//	    				String key = j.next();
//	    				String keyName = GetWebsquareDataCollection.variables.get(key);
//	    				if(StringUtils.isEmpty(keyName)) {
//	    					keyName = "목록";
//	    				}
//		    			//INSERT INOUT
//		    			//serviceCode, inout, idx, fld, fldNm, GID('20210119PMSDEV1 161340590009R00000001'), GID
//	    				
//	    				serviceIOPstmt.setString(1, serviceCode[i][0]);
//		    			serviceIOPstmt.setString(2, "IN");
//		    			serviceIOPstmt.setInt(3, inIdx);
//		    			serviceIOPstmt.setString(4, key);
//		    			serviceIOPstmt.setString(5, keyName);
//		    			serviceIOPstmt.setString(6, "20210119PMSDEV1 161340590009R00000001");
//		    			serviceIOPstmt.setString(7, "20210119PMSDEV1 161340590009R00000001");
//		    			result = serviceIOPstmt.executeUpdate();
//		    			
//		    			System.out.println("["+key+"]SERVICE IN INSERT:"+result);
//		    			
//		    			serviceIOPstmt.close();
//	    			}
	    			
	    			System.out.println("====RES====");
	    			//System.out.println(res);
//	    			int outIdx = 0;
//	    			if(resDat != null) {
//		    			for(Iterator<String> j = resDat.getFieldNames(); j.hasNext() ;) {
//	
//		    				PreparedStatement serviceIOPstmt = conn.prepareStatement(serviceIOInsertQuery.toString());
//		    				outIdx++;
//		    				String key = j.next();
//		    				String keyName = GetWebsquareDataCollection.variables.get(key);
//		    				if(StringUtils.isEmpty(keyName)) {
//		    					keyName = "목록";
//		    				}
//		    				
//		    				serviceIOPstmt.setString(1, serviceCode[i][0]);
//			    			serviceIOPstmt.setString(2, "OUT");
//			    			serviceIOPstmt.setInt(3, outIdx);
//			    			serviceIOPstmt.setString(4, key);
//			    			serviceIOPstmt.setString(5, keyName);
//			    			serviceIOPstmt.setString(6, "20210119PMSDEV1 161340590009R00000001");
//			    			serviceIOPstmt.setString(7, "20210119PMSDEV1 161340590009R00000001");
//			    			result = serviceIOPstmt.executeUpdate();
//			    			
//			    			System.out.println("["+key+"]SERVICE OUT INSERT:"+result);
//			    			
//			    			serviceIOPstmt.close();
//		    			}
//	    			}
//	    		}
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(screenPstmt != null) screenPstmt.close();
			if(screenIdResult != null) screenIdResult.close();
			conn.close();
		}
	}
	
	public static Map<String, JsonNode> getEffelLogRequestData(String serviceCode) throws SQLException {
    	
		OracleConnection conn = null;
    	PreparedStatement searchPstmt = null;
    	ResultSet searchResult = null;
    	ObjectMapper mapper = new ObjectMapper();
    	Map<String, JsonNode> result = new HashMap<String, JsonNode>();
    	
    	try {
    		
    		conn = (OracleConnection)DriverManager.getConnection(dbUrl, dbUser, dbPassword);
        	//conn.setAutoCommit(false);
    		
    		StringBuilder transData = new StringBuilder();
    		transData.append(
    				//"SELECT * FROM EFFEL_LOG_REQUEST " + 
    				"SELECT * FROM EFFEL_LOG_SVCCALL " +
    				"WHERE SERVICE_CODE = ? " + 
    				//"AND ROWNUM = 1 " + 
    				"ORDER BY REG_DTTM DESC");
    		searchPstmt = conn.prepareStatement(transData.toString());
    		searchPstmt.setString(1, serviceCode);
    		searchResult = searchPstmt.executeQuery();
    		
    		//get table comments
    		while(searchResult.next()) {
    			//EFFEL_LOG_REQUEST
//    			Clob reqBody = searchResult.getClob("REQ_BODY");
    			Clob reqBody = searchResult.getClob("REQ_DATA");
    			String strReqBody = reqBody.getSubString(1, (int) reqBody.length());
    			
    			//EFFEL_LOG_REQUEST
//    			JsonNode reqDat = mapper.readTree(strReqBody).get("REQ_DAT");
    			JsonNode reqDat = mapper.readTree(strReqBody);
    			result.put("req", reqDat);
//    			String req = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(reqDat);
    			
    			//EFFEL_LOG_REQUEST
//    			Clob resBody = searchResult.getClob("RES_BODY");
    			
    			Clob resBody = searchResult.getClob("RES_DATA");
    			String strResBody = resBody.getSubString(1, (int) resBody.length());
    			
    			//EFFEL_LOG_REQUEST
//    			JsonNode resDat = mapper.readTree(strResBody).get("RES_DAT");
    			JsonNode resDat = mapper.readTree(strResBody);
    			
    			//error log
    			if(resDat == null) {
    				continue;
    			}
    			
    			//EFFEL_LOG_REQUEST
//   				JsonNode resultDat = mapper.readTree(strResBody).get("RES_DAT").get("result");
    			JsonNode resultDat = resDat.get("result");
    			if(resultDat != null && resDat.size() > 0) {
    				result.put("res", resultDat);
    				break;
    			}
//    			String res = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(resDat);
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(searchPstmt != null) searchPstmt.close();
			if(searchResult != null) searchResult.close();
			conn.close();
		}
    	
    	return result;
	}
	
	public static void deleteServiceIOData(String serviceCode) throws SQLException {
    	
		OracleConnection conn = null;
    	PreparedStatement servicePstmt = null;
    	PreparedStatement ioPstmt = null;
    	
    	try {
    		
    		conn = (OracleConnection)DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    		
    		StringBuilder transData = new StringBuilder();
    		transData.append(
    				"DELETE FROM PMS_ADM.EFFEL_SERVICE_INFO " + 
    				"WHERE SERVICE_CODE = ? ");
    		servicePstmt = conn.prepareStatement(transData.toString());
    		servicePstmt.setString(1, serviceCode);
    		servicePstmt.executeUpdate();
    		
    		transData = new StringBuilder();
    		transData.append(
    				"DELETE FROM PMS_ADM.EFFEL_INOUT_INFO " + 
    				"WHERE SERVICE_CODE = ? ");
    		ioPstmt = conn.prepareStatement(transData.toString());
    		ioPstmt.setString(1, serviceCode);
    		ioPstmt.executeUpdate();
	    		    		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(servicePstmt != null) servicePstmt.close();
			if(ioPstmt != null) ioPstmt.close();
			conn.close();
		}
	}
	
	public static void updateServiceIOData(String serviceCode, String screenIds) throws SQLException {
    	
		OracleConnection conn = null;
    	PreparedStatement servicePstmt = null;
    	
    	try {
    		
    		conn = (OracleConnection)DriverManager.getConnection(dbUrl, dbUser, dbPassword);
    		
    		StringBuilder transData = new StringBuilder();
    		transData.append(
    				"UPDATE EFFEL_SERVICE_INFO SET SERVICE_SCREEN = ? WHERE SERVICE_CODE = ?");
    		servicePstmt = conn.prepareStatement(transData.toString());
    		servicePstmt.setString(1, screenIds);
    		servicePstmt.setString(2, serviceCode);
    		servicePstmt.executeUpdate();
    		conn.commit();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(servicePstmt != null) servicePstmt.close();
			conn.close();
		}
	}
	
}