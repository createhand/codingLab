package codingTest;

/**
 * <b>공통Enum</b>
 * @author 박서찬
 * @date 2018. 7. 24.
 * @version 0.1 : 최초작성
 * <hr>
 * <pre>
 * 공통적으로 사용되는 상수 및 Enum을 정의한다.
 * 
 * <b>History:</b>
 * ====================================================================
 *  버전  :    작성일    :  작성자  :  작성내역  
 * --------------------------------------------------------------------   
 *  0.1  2018. 7. 24   박서찬     최초작성
 * ====================================================================
 * </pre>
 */
public enum CommonEnum {
	YES(1, "Y")
	,NO(0, "N")
	,ParamTypeString(0, "0")
	,ParamTypeInt(1, "1")
	,ParamTypeList(2, "2")
	,HttpMethod(0, "GET")
	//0:GET, 1:POST, 2:PUT, 3:DELETE
	;
	
	private int intValue;
	private String stringValue;

	CommonEnum(int intValue, String stringValue) {
		this.intValue = intValue;
		this.stringValue = stringValue;
	}

	public int getInt() {
		return this.intValue;
	}
	
	public int getInt(String value) {
		for(CommonEnum enums : CommonEnum.values()) {
			if(enums.stringValue == value) return enums.intValue; 
		}
		return this.intValue;
	}

	public String getString() {
		return this.stringValue;
	}
	
	public String getString(int value) {
		for(CommonEnum enums : CommonEnum.values()) {
			if(enums.intValue == value) return enums.stringValue; 
		}
		return "";
	}

	public static class Const {

		/** 정상 응답코드 */
		public static final String NORMAL_RESP_CODE = "N0000000";
		
		/** 환율정보 자리수 */
		public static final int EXCHR_SIZE = 16;
		public static final int EXCHR_DP_SIZE = 10;
		public static final int EXCHR_PBLD_DT_SIZE = 8;
		public static final int EXCHR_PBLD_TN_SIZE = 5;
		public static final int EXCHR_PBLD_TM_SIZE = 6;

		public static class Cache {
			/** 메모리 */
			public static final String FROM_MEMORY = "memory";
			/** server */
			public static final String FROM_SERVER = "server";
			
			/** 응답메시지 */
			public static final String RSP_MSG = "RSP_MSG_LIST";

			/** 공통코드 */
			public static final String COM_CD = "COMM_CD_LIST";
			
			/** 환경설정정보 */
			public static final String ENV_SET = "ENV_SET_LIST";
			
			/** 언어팩 */
			public static final String LANG_PKG = "LANG_PKG_LIST";
			
			/** Local GLN interface 정보 */
			public final static String LOCAL_GLN_IN_IF = "LOCAL_GLN_IN_IF_LIST";
			
			/** Local GLN interface 정보 */
			public final static String LOCAL_GLN_EX_IF = "LOCAL_GLN_EX_IF_LIST";
		}
		
		public static class GlnHttpHeader {
			
			/** Header의 Global ID 필드명 */
			public static final String GLOBAL_ID_NAME = "GLOB_ID";
			
			/** Header의 api VERSION 필드명 */
			public static final String VERSION = "VERSION";
			
			/** Header의 TIMEZONE ID 필드명 */
			public static final String TIMEZONE_ID_NAME = "TIMEZONE_ID";
			
			/** Header의 TIMEZONE OFFSET 필드명 */
			public static final String TIMEZONE_OFFSET_NAME = "TIMEZONE_OFFSET";
			
			/** Header의 내부서버 인증 필드명 */
			public static final String GLN_AUTH_HEADER = "GLN_AUTH";
			
			/** Header의 내부서버 해시 필드명 */
			public static final String GLN_TS_HEADER = "GLN_TIMESTAMP";
			
			/** 회원인증토큰 */
			public static final String HEADER_AUTH_TOKEN = "X-Auth-Token";

			/** 인증유형 : SecurityRole 참조 */
			public static final String HEADER_AUTH_TYPE = "X-Auth-Type";
			
		}
		
		public static class GlnHttpBody {
			/** GLN data header */
			public static final String DATA_HEADER_NAME = "GLN_HEADER";

			/** GLN data body */
			public static final String DATA_BODY_NAME = "GLN_BODY";
		}
		
		public static class GlnAuthType {
			/** 인증유형:회원 */
			public static final String USER = "USER";
			
			/** 인증유형:Local GLN */
			public static final String GLN = "GLN";
			
			/** 인증유형:내부서버 */
			public static final String INTERNAL = "INTERNAL";
		}
	}
}