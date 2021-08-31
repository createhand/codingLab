package codingTest;

import lombok.Data;

/**
 * <b>공통 MAP</b>
 * @author 박서찬
 * @date 2018. 7. 24.
 * @version 0.1 : 최초작성
 * <hr>
 * <pre>
 * 프레임워크 공통 Map 클래스
 * key, value형태의 LinkedHashMap을 활용해 customizing된 메소드를 제공한다.
 *
 * <b>History:</b>
 * ====================================================================
 *  버전  :    작성일    :  작성자  :  작성내역
 * --------------------------------------------------------------------
 *  0.1  2018. 7. 24   박서찬     최초작성
 * ====================================================================
 * </pre>
 */
@Data
public class GlnDataHeader {

	/**
	 * 이름: 요청기관 코드
	 * Mandatory 여부: Y
	 * length: 10
	 * Description: - GLN Int'l Code
	 * 				- Local GLN Code
	 * 				- Local GLN Affiliate Code
	 */
	private String REQ_ORG_CODE;

	/**
	 * 이름: 요청 기관의 전문전송 일자 (해당 국가 타임존 적용)
	 * Mandatory 여부: Y
	 * length: 8
	 * Description: 요청 기관의 전문전송 일자 (해당 국가 타임존 적용)
	 */
	private String REQ_ORG_TX_DATE;

	/**
	 * 이름: 요청 기관의 전문전송 시간 (해당 국가 타임존 적용)
	 * Mandatory 여부: Y
	 * length: 6
	 * Description: 요청 기관의 전문전송 시간 (해당 국가 타임존 적용)
	 */
	private String REQ_ORG_TX_TIME;

	/**
	 * 이름: 요청 기관의 거래추척번호
	 * Mandatory 여부: Y
	 * length: 50
	 * Description: 해당거래가 마무리 되기 전에는 동일하게 유지
	 */
	private String REQ_ORG_TX_NO;


	/**
	 * 이름: GLN Int'l에서 채번한 거래 추적번호
	 * Mandatory 여부: Y
	 * length: 50
	 * Description:
	 */
	private String GLN_TX_NO;

	/**
	 * 이름: GLN Int'l에서 채번한 거래 추적번호
	 * Mandatory 여부: N
	 * length: 50
	 * Description: 해당거래를 최종적으로 처리하는 경우 기관에서 채번하는 거래 추적번호
	 */
	private String RES_ORG_TX_NO;

	/**
	 * 이름: 응답코드
	 * Mandatory 여부: N
	 * length: 9
	 * Description: 정상: "N0000000"
	 * 				오류: "S","B"
	 */
	private String RES_CODE;

	/**
	 * 이름:
	 * Mandatory 여부: N
	 * length: 50
	 * Description: 최초 요청하는 Local GLN이 필요시 사용하며, 응답시 해당값을 그대로 리턴한다.
	 */
	private String REQ_ORG_AREA;

}
