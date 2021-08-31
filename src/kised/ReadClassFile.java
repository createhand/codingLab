package kised;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;

public class ReadClassFile {
	public static String classFilePath = "D:\\KisedSDK3\\workspace\\codingutils\\target\\classes\\cordingTest";
	public static void main(String args[]) {
		try {
			File path = new File(classFilePath);
			URL url = path.toURI().toURL();
			URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
			Class cls = urlClassLoader.loadClass("cordingTest.AsisToBeColumnConverter");
			
			for(Method method : cls.getMethods()) {
				
				String methodName = method.getName();
				System.out.println("method:"+methodName);
				System.out.println(method.toGenericString());
				System.out.println(method.toString());
			}
			
//			for(Field field : cls.getDeclaredFields()) {
//				System.out.println("field:"+field.getName());
//			}
			urlClassLoader.close();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}