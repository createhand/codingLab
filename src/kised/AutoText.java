package kised;

import org.apache.commons.lang.StringUtils;

public class AutoText {

	public static void main(String[] args) {
		
		for(int i=1;i<=2000;i++) {
			String svcCd = StringUtils.leftPad(""+i, 4, '0');
			System.out.println("\"BEX"+svcCd+"\",");
//			System.out.println("=CONCAT(Sheet1!D"+(i*7+1)+",\" \")\r\n" +
//					"=CONCAT(Sheet1!D"+(i*7+1)+",\" \", 복사참조!B1)\r\n" + 
//					"=CONCAT(Sheet1!D"+(i*7+1)+",\" \", 복사참조!B2)\r\n" + 
//					"=CONCAT(Sheet1!D"+(i*7+1)+",\" \", 복사참조!B3)\r\n" +
//					"=CONCAT(Sheet1!D"+(i*7+1)+",\" \", 복사참조!B4)\r\n" +
//					"=CONCAT(Sheet1!D"+(i*7+1)+",\" \", 복사참조!B5)\r\n" +
//					"=CONCAT(Sheet1!D"+(i*7+1)+",\" \", 복사참조!B6)");
			
//			System.out.println("=CONCAT(K"+(i*7+1)+",A"+(i*7+1)+",L"+(i*7+1)+", \"01\")\r\n" + 
//					"=CONCAT(K"+(i*7+1)+",A"+(i*7+1)+",L2,복사참조!D1)\r\n" + 
//					"=CONCAT(K"+(i*7+1)+",A"+(i*7+1)+",L2,복사참조!D2)\r\n" + 
//					"=CONCAT(K"+(i*7+1)+",A"+(i*7+1)+",L2,복사참조!D3)\r\n" + 
//					"=CONCAT(K"+(i*7+1)+",A"+(i*7+1)+",L2,복사참조!D4)\r\n" + 
//					"=CONCAT(K"+(i*7+1)+",A"+(i*7+1)+",L2,복사참조!D5)\r\n" + 
//					"=CONCAT(K"+(i*7+1)+",A"+(i*7+1)+",L2,복사참조!D6)");
			
		}
		
	}
}
