package codingTest;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class RequestAPI {
	
	public static void requestAPI() {
		OkHttpClient client = new OkHttpClient();

		MediaType mediaType = MediaType.parse("application/json");
		RequestBody body = RequestBody.create(mediaType, "{\n    \"GLN_HEADER\": {},\n    \"GLN_BODY\": {}\n}");
		Request request = new Request.Builder()
		  .url("https://api.glnpay.com:9000/api/v1/mainhub/core/common/initInfo")
		  .post(body)
		  .addHeader("Content-Type", "application/json")
		  .addHeader("User-Agent", "PostmanRuntime/7.11.0")
		  .addHeader("Accept", "*/*")
		  .addHeader("Cache-Control", "no-cache")
		  .addHeader("Postman-Token", "094074a6-2386-4914-a169-e4c7daef046f,353f6cba-d921-4866-b0db-eb55859833fd")
		  .addHeader("Host", "api.glnpay.com:9000")
		  .addHeader("cookie", "SESSION=M2NlYmZkMWYtMjk1Ni00NDUwLTkxYzAtZmJlNzQ5YjcyYjAx")
		  .addHeader("accept-encoding", "gzip, deflate")
		  .addHeader("content-length", "44")
		  .addHeader("Connection", "keep-alive")
		  .addHeader("cache-control", "no-cache")
		  .build();

		try {
			Response response = client.newCall(request).execute();
			System.out.println(response.body().string());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void main(String args[]) {
		try {
			for(int i=0;i<10000;i++) {
				requestAPI();
				requestAPI();
				requestAPI();
				requestAPI();
				requestAPI();
				requestAPI();
				requestAPI();
				requestAPI();
				requestAPI();
				requestAPI();
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
		
}
