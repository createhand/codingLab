package kised;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import codingTest.Consts;
import oracle.jdbc.driver.OracleConnection;

public class QueryColumnToVariableConverter  {
	
	public static final boolean displayWebsquareTag = true;
	public static final boolean displayColumnName = false;
	
	public static final boolean displayVariableType = true;
	public static final boolean displayVariableName = true;
	public static final boolean displayVariableComment = true;
	public static final String DB_URL = Consts.TOBE_DB_URL;
	public static final String DB_ID = Consts.TOBE_DB_ID;
	public static final String DB_PWD = Consts.TOBE_DB_PWD;

	public static Map<String, String> totalComments = new HashMap<String, String>();
	
	public static void main(String[] args) {
		//1. variables 추출
//    	getVariabls();
		
		//2. vo, websqure 등 text 생성
    	printAutoText();
    	
    }
    
    public static void printAutoText() {
    	String[][] variables = {
    			//변수타입, 변수명, 주석, 컬럼명
    			 {null, null, null, null}
    			 ,{"weaWeeWuaUbrDao.weaWeeWuaUbrChk",null,null,null}
    			 ,{"String","tsksId","과제ID","TSKS_ID"}
    			 ,{"String","tsksNm","과제명","TSKS_NM"}
    			 ,{"String","tsksTypeClcd","과제유형구분코드","TSKS_TYPE_CLCD"}
    			 ,{"String","mngInstId","관리기관ID","MNG_INST_ID"}
    			 ,{"String","tsksPrgsClcd","과제진행구분코드","TSKS_PRGS_CLCD"}
    			 ,{"String","bizCtcd","사업분류코드","BIZ_CTCD"}
    			 ,{"String","bizClsfcNm","사업분류명","BIZ_CLSFC_NM"}
    			 ,{"String","bizYear","사업년도","BIZ_YEAR"}
    			 ,{"String","rulId","규정ID","RUL_ID"}
    			 ,{"String","useAprvYn","사용승인여부","USE_APRV_YN"}
    			 ,{"String","tactExcnCrflClcd","이체실행인증서구분코드","TACT_EXCN_CRFL_CLCD"}
    			 ,{"String","tactStepClcd","이체단계구분코드","TACT_STEP_CLCD"}
    			 ,{"String","vatSuptClcd","부가가치세지원구분코드","VAT_SUPT_CLCD"}
    			 ,{"String","orgnClSeC18D60","영리/비영리","ORGN_CL_SE_C18_D60"}
    			 ,{"String","tactExcnYn","이체실행여부","TACT_EXCN_YN"}
    			 ,{"String","execYn","null","EXEC_YN"}
    			 ,{"long","profEnfrAbleRate","증빙집행가능비율","PROF_ENFR_ABLE_RATE"}
    			 ,{"String","execValChkYn","null","EXEC_VAL_CHK_YN"}
    			 ,{"String","instId","기관ID","INST_ID"}
    			 ,{"String","bsmnRegNo","사업자등록번호","BSMN_REG_NO"}
    			 ,{"String","instInvlRoleClcd","기관참여역할구분코드","INST_INVL_ROLE_CLCD"}
    			 ,{"String","indsClsfcClcd","산업분류구분코드","INDS_CLSFC_CLCD"}
    			 ,{"String","instClsfcClcd","기관분류구분코드","INST_CLSFC_CLCD"}
    			 ,{"String","instStatClcd","기관상태구분코드","INST_STAT_CLCD"}
    			 ,{"String","tsksMnpwId","null","TSKS_MNPW_ID"}
    			 ,{"String","tsksMnpwNm","null","TSKS_MNPW_NM"}
    			 ,{"String","enfrAgrmId","집행협약ID","ENFR_AGRM_ID"}
    			 ,{"long","tsksAnnl","과제연차","TSKS_ANNL"}
    			 ,{"String","mnpwInstId","인력기관ID","MNPW_INST_ID"}
    			 ,{"String","mnpwInstClcd","인력기관구분코드","MNPW_INST_CLCD"}
    			 ,{"String","enfrStatClcd","집행상태구분코드","ENFR_STAT_CLCD"}
    			 ,{"String","tactAprvCrflClcd","이체승인인증서구분코드","TACT_APRV_CRFL_CLCD"}
    			 ,{"long","agreCnt","null","AGRE_CNT"}
    			 ,{"String","giveTactExcnYn","null","GIVE_TACT_EXCN_YN"}
    			 ,{"long","aplyCnt","null","APLY_CNT"}
    			 ,{"String","skpAbleYn","생략가능여부","SKP_ABLE_YN"}
    			 ,{"long","elsgCnt","null","ELSG_CNT"}
    			 ,{"long","taxSignCnt","null","TAX_SIGN_CNT"}
    			 ,{"long","agreCsntpsCnt","null","AGRE_CSNTPS_CNT"}
    			 ,{"long","giveAmt","지급금액","GIVE_AMT"}
    			 ,{"long","regCnt","null","REG_CNT"}
    			 ,{"long","confirm1Cnt","null","CONFIRM_1_CNT"}
    			 ,{"long","confirm2Cnt","null","CONFIRM_2_CNT"}
    			 ,{"String","acctCardYn","null","ACCT_CARD_YN"}
    			 ,{"String","spctRfltYn","전문반영여부","SPCT_RFLT_YN"}
    			 ,{"String","acctTrfrSeYn","null","ACCT_TRFR_SE_YN"}
    			 ,{"String","statChgResnClcd","상태변경사유구분코드","STAT_CHG_RESN_CLCD"}
    			 ,{"String","bexpCpstChngYmd","null","BEXP_CPST_CHNG_YMD"}
    			 ,{"String","bexpCpstChngYn","null","BEXP_CPST_CHNG_YN"}
    			 ,{"String","setlYn","null","SETL_YN"}
    			 ,{"String","cashRegAbleYn","현금등록가능여부","CASH_REG_ABLE_YN"}
    			 ,{"String","spotsRegAbleYn","현물등록가능여부","SPOTS_REG_ABLE_YN"}
    			 ,{"String","schdYn","null","SCHD_YN"}
    			 ,{"String","timeChkYn","null","TIME_CHK_YN"}
    			 ,{"String","execTimeChkMsg","null","EXEC_TIME_CHK_MSG"}

    	};

    	for(int i=0;i<variables.length;i++) {
	    	
    		if(variables[i][0] != null && variables[i][1] == null) {
    			System.out.println("\n");
				System.out.println("//"+variables[i][0]);
			}
	    	
    		if(variables[i][1] != null) {
		    	if(displayVariableComment) {
//					System.out.println("/** "+variables[i][2]+" */");
		    		System.out.println("//"+variables[i][2]);
				}    			
				if(displayVariableType) {
					System.out.print("private " + variables[i][0] + " ");
				}
				if(displayVariableName) {
					System.out.println(variables[i][1]+ ";");
				}
    		}
    	}
    	
    	if(displayWebsquareTag) {
    		
    		for(int i=0;i<variables.length;i++) {
    			if(variables[i][0] != null && variables[i][1] == null) {
    				System.out.println("\n\n============== w2:dataList["+variables[i][0]+"] ==============");
    			}
    			if(variables[i][1] != null) {
					System.out.println("<w2:column id=\""+variables[i][1]+"\" name=\""+variables[i][2]+"\" dataType=\"text\" />");
    			}
    		}
    		
    		for(int i=0;i<variables.length;i++) {
    			if(variables[i][0] != null && variables[i][1] == null) {
    				System.out.println("\n\n============== w2:dataMap["+variables[i][0]+"] ==============");
    			}
    			if(variables[i][1] != null) {
					System.out.println("<w2:key id=\""+variables[i][1]+"\" name=\""+variables[i][2]+"\" dataType=\"text\"></w2:key>");
    			}
    		}
//    		
//    		System.out.println("\n\n============== w2:header ==============");
//    		for(int i=0;i<variables.length;i++) {
//    			if(variables[i][1] != null) {
//					System.out.println("<w2:column blockSelect=\"false\" id=\"column"+i+"\" inputType=\"text\" style=\"height:20px;\" value=\""+variables[i][2]+"\" width=\"150\" />");
//    			}
//    		}
//    		
//    		System.out.println("\n\n============== w2:gBody ==============");
//			for(int i=0;i<variables.length;i++) {
//    			if(variables[i][1] != null) {
//					System.out.println("<w2:column blockSelect=\"false\" id=\""+variables[i][1]+"\" inputType=\"text\" style=\"height:20px;\" textAlign=\"center\" width=\"100\" />");
//    			}
//    		}
    	}
    	
    	if(displayColumnName) {
    		System.out.println("\n\n============== 컬럼명 ==============");
    		for(int i=0;i<variables.length;i++) {
    			if(variables[i][1] != null) {
		    			System.out.println("/** "+variables[i][2]+" */");
		    			System.out.println(variables[i][3]);
	    		}
    		}
    	}

    }
    
    public static void getVariabls() {
    	try {
    		getTableComments(Consts.toBeTableNames);
			convertColumnNameToVariable(QueryColumnToVariablesConsts.query);
		} catch (SQLException e1) {
			System.out.println("SQLException:"+e1.getMessage());
			e1.printStackTrace();
		}
    }
    
    public static void convertColumnNameToVariable(String[][] query) throws SQLException {
    	OracleConnection conn = (OracleConnection)DriverManager.getConnection(DB_URL, DB_ID, DB_PWD);
    	conn.setAutoCommit(false);
    	PreparedStatement pstmt = null;
    	ResultSet result = null;
    	String nowSqlId = null;
    	String nowSql = null;
    	
    	try {
    		
    		for(int k=0;k<query.length;k++) {
    		
    			nowSqlId = query[k][0];
    			nowSql = query[k][1];
    					
	    		if(StringUtils.isBlank(nowSql)) {
	    			System.out.println("쿼리를 입력해주세요.");
	    			return;
	    		}
	    		StringBuilder sb = new StringBuilder(nowSql);
	    		
	    		pstmt = conn.prepareStatement(sb.toString());
	    		result = pstmt.executeQuery();
	    		
	    		System.out.println(",{\""+nowSqlId+"\",null,null,null}");
	    		
	    		int count = result.getMetaData().getColumnCount();
	    		for(int i=1;i<=count;i++) {
	    			String columnName = result.getMetaData().getColumnName(i);
	    			String columnNameCamel = AsisToBeColumnConverter.convertCamelCase(columnName);
	    			String columnComment = totalComments.get(columnName);
	    			
	    			if(StringUtils.isBlank(columnComment)) {
	    				//T_ASIS_TOBE_COL에서 조회
	    				StringBuilder mappingComment = new StringBuilder();
	    				mappingComment.append("SELECT TOBE_KOR_COL FROM T_ASIS_TOBE_COL ");
	    				mappingComment.append("WHERE TOBE_ENG_COL = '"+columnName+"' ");
	    				mappingComment.append("OR ASIS_ENG_COL = '"+columnName+"'");
	    				
	    				PreparedStatement mappingPstmt = conn.prepareStatement(mappingComment.toString());
	    		    	ResultSet mappingResult = mappingPstmt.executeQuery();
	    		    	
	    		    	if(mappingResult.next()) {
	    		    		columnComment = mappingResult.getString("TOBE_KOR_COL");
	    		    	} else {
	    		    		//기존 상수에서 조회
	    		    		for(int j=0;j<QueryColumnToVariablesConsts.variables.length;j++) {
	    		    			String constVariables = QueryColumnToVariablesConsts.variables[j][3];
	    		    			if(StringUtils.equals(columnName, constVariables)) {
	    		    				columnComment = QueryColumnToVariablesConsts.variables[j][2];
	    		    			}
	    		    		}
	    		    	}
	    			}
	    			
	    			String type = result.getMetaData().getColumnTypeName(i);
	    			
	    			switch(type) {
	    				case "VARCHAR2":
	    					type = "String";
	    					break;
	    				case "NUMBER":
	    					type = "long";
	    					break;
	    				default :
	    					type = "String";
							break;
	    			}
	    			
	    			System.out.println(",{\""+type+"\",\""+columnNameCamel+"\",\""+columnComment+"\",\""+columnName+"\"}");
	    		}
	    		System.out.println("\n\n");
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(nowSqlId);
			System.err.println(nowSql);
		} finally {
			pstmt.close();
			conn.close();
			result.close();
		}
    }
    
    public static void getTableComments(String[] table) throws SQLException {
	    	OracleConnection conn = (OracleConnection)DriverManager.getConnection(Consts.TOBE_DB_URL, Consts.TOBE_DB_ID, Consts.TOBE_DB_PWD);
	    	conn.setAutoCommit(false);
	    	PreparedStatement tablePstmt = null;
	    	PreparedStatement columnPstmt = null;
	    	ResultSet tableResult = null;
	    	ResultSet columnResult = null;
			
	    	try {
	    		
	    		for(int i=0;i<table.length;i++) {
	    			
	    			Map<String, String> comments = new HashMap<String, String>();
	    			
		    		//DATA DICTIONARY에서 조회
		    		StringBuilder tableComment = new StringBuilder();
		    		tableComment.append("SELECT * FROM ALL_TAB_COMMENTS WHERE TABLE_NAME = '"+table[i]+"'");
		    		tablePstmt = conn.prepareStatement(tableComment.toString());
		    		
		    		StringBuilder columnComment = new StringBuilder();
		    		columnComment.append("SELECT * FROM ALL_COL_COMMENTS WHERE TABLE_NAME = '"+table[i]+"'");
		    		columnPstmt = conn.prepareStatement(columnComment.toString());
		    		tableResult = tablePstmt.executeQuery();
		    		
		    		//get table comments
		    		if(tableResult.next()) {
			    		//set table comments
			    		comments.put(table[i], tableResult.getString("COMMENTS"));
		    		}
		    		columnResult = columnPstmt.executeQuery();
		    		while(columnResult.next()) {
		    			//set table comments
		    			if(StringUtils.isNotBlank(columnResult.getString("COMMENTS"))) {
		    				comments.put(columnResult.getString("COLUMN_NAME"), columnResult.getString("COMMENTS"));
		    			}
		    		}
		    		
		    		totalComments.putAll(comments);
//		    		System.out.println("/** 테이블명 : "+table[i]+" "+totalComments.get(table[i])+" */");
	    		}
	    		
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				tablePstmt.close();
				tableResult.close();
				columnPstmt.close();
				columnResult.close();
				conn.close();
			}
    	
    }
}