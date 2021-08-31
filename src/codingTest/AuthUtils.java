package codingTest;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.StringTokenizer;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.RandomStringUtils;

import codingTest.EncryptUtils.HMACSHA256;
import lombok.extern.slf4j.Slf4j;
import okhttp3.internal.http.HttpHeaders;

/**
 * <b>GLN 인증 유틸</b>
 * @author 박서찬
 * @date 2019. 1. 11.
 * @version 0.1 : 최초작성
 * <hr>
 * <pre>
 * GLN 인증 관련 Util
 * 
 * <b>History:</b>
 * ====================================================================
 *  버전  :    작성일    :  작성자  :  작성내역  
 * --------------------------------------------------------------------   
 *  0.1  2019. 1. 11   박서찬     최초작성
 * ====================================================================
 * </pre>
 */
@Slf4j
public class AuthUtils {
	
	/**
	 * <b>수신한 인증헤더(Authorization)분리</b>
	 * @param authorization
	 * <pre>
	 *  수신한 HTTP요청의 authorization 헤더
	 * </pre>
	 * @return StringTokenizer
	 * <pre>
	 *  authorization 헤더 분리 객체
	 * </pre> 
	 * <hr>
	 * <pre>
	 *  authorization 헤더의 값을 StringTokenizer로 분리하여 리턴한다.
	 * </pre>
	 */
	public static StringTokenizer getBasicAuthorization(String authorization) {
		
		StringTokenizer tokenizer = null;
		
		try {
			
			tokenizer = new StringTokenizer(authorization);
			
			if (tokenizer.countTokens() < 2) {
				System.out.println("Invalid authorization header.");
			}
			if (!tokenizer.nextToken().equalsIgnoreCase("Basic")) {
				System.out.println("Invalid authorization header.");
			}
			
			String decodeAuthorization = new String(Base64.getDecoder().decode(tokenizer.nextToken().getBytes(StandardCharsets.UTF_8)));
			return tokenizer = new StringTokenizer(decodeAuthorization, ":");
		
		} catch(Exception e) {
			e.printStackTrace();
		}
		return tokenizer;
	}
	
	/**
	 * <b></b>
	 * @param authorization
	 * <pre>
	 *  수신한 HTTP요청의 authorization 헤더
	 * </pre>
	 * @param timestamp
	 * <pre>
	 *  수신한 HTTP요청의 Timestamp 헤더
	 * </pre> 
	 * @param salt
	 * <pre>
	 *  수신한 HTTP요청에 대한 검증을 위한 salt
	 * </pre> 
	 * <hr>
	 * <pre>
	 *  authorization 헤더검증
	 * </pre>
	 */
	public static void checkAuthorization(String authorization, String timestamp, String salt) {
		StringTokenizer tokenizer = getBasicAuthorization(authorization);
		String authId = tokenizer.nextToken();
		String authSecret = tokenizer.nextToken();
		checkAuthorization(authId, authSecret, timestamp, salt);
	}
	
	/**
	 * <b></b>
	 * @param authId
	 * <pre>
	 *  수신한 HTTP요청의 authorization 헤더 중 authId(client ID)
	 * </pre>
	 * @param authSecret
	 * <pre>
	 *  수신한 HTTP요청의 authorization 헤더 중 authSecret(client secret)
	 * </pre> 
	 * @param timestamp
	 * <pre>
	 *  수신한 HTTP요청의 Timestamp 헤더
	 * </pre> 
	 * @param salt
	 * <pre>
	 *  수신한 HTTP요청에 대한 검증을 위한 salt
	 * </pre> 
	 * <hr>
	 * <pre>
	 *  authorization 헤더검증
	 * </pre>
	 */
	public static void checkAuthorization(String authId, String authSecret, String timestamp, String salt) {
		//authorization 헤더에 값이 없을때
		if(StringUtils.isBlank(authId) || StringUtils.isBlank(authSecret)) {
			System.out.println("Authorization is required.[authId or authSecret]");
		}
		
		log.debug("request from : "+authId);
		
		String hashValue = null;
		try {
			hashValue = HMACSHA256.generate(timestamp, salt);
		} catch (InvalidKeyException | NoSuchAlgorithmException | UnsupportedEncodingException | DecoderException e) {
			e.printStackTrace();
		}
		
		if(!StringUtils.equals(authSecret, hashValue)) {
			log.warn("Invalid authorization. ["+authSecret+"]");
			log.debug("salt. ["+salt+"]");
			log.debug("timestamp. ["+timestamp+"]");
			log.warn("hashValue. ["+hashValue+"]");
			System.out.println("Invalid authSecret value.");
		}
		
		log.debug("permitted request.");
	}
	
	/**
	 * <b></b>
	 * @param authId
	 * <pre>
	 *  HTTP요청을 보내기 위한 authorization 헤더 중 authId(client ID)
	 * </pre>
	 * @param salt
	 * <pre>
	 *  HTTP요청을 보내기 위한 salt
	 * </pre> 
	 * @param headers
	 * <pre>
	 *  HTTP요청을 보낼때 사용할 HTTP header 객체
	 * </pre> 
	 * <hr>
	 * <pre>
	 *  내부서버(mainhub, member API, gateway 등)로 HTTP 요청을 보내기 위한 authorization 헤더 세팅
	 * </pre>
	 */
	public static void setInternalAuthorization(String authId, String salt, TAData headers) {
		try {
			String timestamp = String.valueOf(System.currentTimeMillis());
			String authorization = authId + ":" + HMACSHA256.generate(timestamp, salt);
			authorization = Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8));
			headers.set(CommonEnum.Const.GlnHttpHeader.GLN_AUTH_HEADER, "Basic "+authorization);
			headers.set(CommonEnum.Const.GlnHttpHeader.GLN_TS_HEADER, timestamp);
		} catch(Exception e) {
			log.error("HMAC 처리 중 오류", e);
		}
	}
	
	/**
	 * <b></b>
	 * @param authId
	 * <pre>
	 *  HTTP요청을 보내기 위한 authorization 헤더 중 authId(client ID)
	 * </pre>
	 * @param salt
	 * <pre>
	 *  HTTP요청을 보내기 위한 salt
	 * </pre> 
	 * @param headers
	 * <pre>
	 *  HTTP요청을 보낼때 사용할 HTTP header 객체
	 * </pre> 
	 * <hr>
	 * <pre>
	 *  외부 Local GLN API Gateway로 HTTP 요청을 보내기 위한 authorization 헤더 세팅
	 * </pre>
	 */
	public static void setExternalAuthorization(String authId, String salt, TAData headers) {
		try {
			String timestamp = String.valueOf(System.currentTimeMillis());
			String authorization = authId + ":" + HMACSHA256.generate(timestamp, salt);
			authorization = Base64.getEncoder().encodeToString(authorization.getBytes(StandardCharsets.UTF_8));
			headers.set("Authorization", "Basic "+authorization);
			headers.set("Timestamp", timestamp);
		} catch(Exception e) {
			log.error("HMAC 처리 중 오류", e);
		}
	}
	
	//optional
	/**
	 * <b></b>
	 * @param timestamp
	 * <pre>
	 *  수신한 HTTP요청의 Timestamp 헤더
	 * </pre>
	 * @param period
	 * <pre>
	 *  수신한 HTTP요청의 Timestamp의 유효시간
	 * </pre> 
	 * <hr>
	 * <pre>
	 *  수신한 timestamp 값이 period를 초과했는지 체크
	 * </pre>
	 */
	public static void checkTimestamp(String timestamp, long period) {
		//-----------------------------------------------------
		// Timestamp 중복사용 방지
		//-----------------------------------------------------
		if(StringUtils.isBlank(timestamp)) {
			log.warn("required http header [Timestamp]!");
			System.out.println("SCOM0005");
		}
		
		//현재 시간(system시간기준)보다 일정시간(period)이 이상 경과된 timestamp 체크
		//현재 시간(system시간기준)보다 요청시간(timestamp)가 큰 경우 체크
		if(System.currentTimeMillis() - Long.parseLong(timestamp) > period 
				|| System.currentTimeMillis() < Long.parseLong(timestamp)) {
			System.out.println("SCOM0011");
		}
	}
	
	//optional
	/**
	 * <b></b>
	 * @param permitIpList, requestIp
	 * <pre>
	 *  수신한 HTTP요청에 대한 허용 IP 목록
	 * </pre>
	 * @param requestIp
	 * <pre>
	 *  수신한 HTTP요청의 IP
	 * </pre> 
	 * <hr>
	 * <pre>
	 *  수신한 HTTP요청에 대한 IP 체크
	 * </pre>
	 */
	public static void checkRequestIP(String permitIpList, String requestIp) {
		if(permitIpList.indexOf(requestIp) < 0) {
			log.warn("unregistered ip. [" + requestIp + "]");
			System.out.println("SCOM0005");
		}
	}
	
	/**
	 * <b></b>
	 * <pre>
	 *  salt 생성
	 * </pre>
	 * @param salt
	 * <pre>
	 *  영문숫자 랜덤 32자리를 통한 salt 값 생성
	 * </pre> 
	 * <hr>
	 */
	public static String generateSalt() {
		String newSalt = RandomStringUtils.randomAlphanumeric(32);
		return EncryptUtils.HexaString.encode(newSalt.getBytes());
	}
	
	
	
	public static void main(String[] args) {
		
		//#####################################
		// set authorization
		//#####################################
		//global cache(redis)에서 authId로 조회한 salt
		String salt = "51637266396143464B707758724C7A774843336248474970634E644B624F3633";
		String id = "KOEXKR";
		TAData reqHeader = new TAData();
		setExternalAuthorization(id, salt, reqHeader);
		log.debug("Authorization:"+reqHeader.getString("Authorization"));
		log.debug("Timestamp:"+reqHeader.getString("Timestamp"));
		
		//#####################################
		// check authorization
		//#####################################
		//authorization(http header name:Authorization)
//		String authorization = reqHeader.getString("Authorization");
		String authorization = "Basic U0lDT1RIOkNBOUNGRENFODVBODNFMjFGQjgzQTBFNkJBRjU3MEU5RkE3NkYxODdGQTdDMkE5NTg4NUQwNUZFRTNENTVERTM=";
		String timestamp = "1557393018405";
		StringTokenizer tokenizer = getBasicAuthorization(authorization);
		String authId = tokenizer.nextToken();
		String authSecret = tokenizer.nextToken();
//		checkAuthorization(authorization, timestamp, salt);
		//or
		checkAuthorization(authId, authSecret, timestamp, salt);
		
		
		
		//-----------------------------------------------------
		// 4. API GW에서 내부서버(mainhub, member API 등)로 송신시 인증값
		//-----------------------------------------------------
		TAData headers = new TAData();
		
		//내부서버간
		authId = CommonEnum.Const.GlnAuthType.INTERNAL;
		
		String newSalt = "784F626F48374D57514C50655970746E6C786B784E35786D4B4E574549734A54";
		
		//property 또는 global cache(redis)에서 조회한 salt
//		salt = "9a2a2519a27a4066a5720510fe3e4cb5c4";
		
		//generate
		setInternalAuthorization(authId, newSalt, headers);
		
		//verify
		checkAuthorization(
				headers.getString(CommonEnum.Const.GlnHttpHeader.GLN_AUTH_HEADER), 
				headers.getString(CommonEnum.Const.GlnHttpHeader.GLN_TS_HEADER),
				newSalt);
		
		log.debug("GLN_AUTH:"+headers.getString(CommonEnum.Const.GlnHttpHeader.GLN_AUTH_HEADER));
		log.debug("GLN_TIMESTAMP:"+headers.getString(CommonEnum.Const.GlnHttpHeader.GLN_TS_HEADER));
		
	}
	
}
