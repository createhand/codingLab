package codingTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cordingLabs {
	
	public static void main(String[] args) {
		List<String> busiNoList = new ArrayList<String>();
		for(int i=0;i<5340;i++) {
			busiNoList.add("cnt"+i);
			if(busiNoList.size() >= 100 ) {
				System.out.println(String.join(",", busiNoList));
				System.out.println("ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
				busiNoList = new ArrayList<String>();
			}
		}
		if(busiNoList.size() > 0 ) {
			System.out.println(String.join(",", busiNoList));
			System.out.println("LASTㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ");
		}
	}
	
	public static long getLong(Object obj) {
		if (obj == null)
			return 0;
		if (obj instanceof String) {
			return Long.parseLong((String) obj);
		} else if (obj instanceof Integer) {
			return ((Integer)obj).longValue();
		} else if (obj instanceof Long) {
			return (long)obj;
		}
		return 0;
	}
	
}
