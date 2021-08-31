/**
 * <b>클래스명</b>
 * @author seochan
 * @date 2018. 12. 4.
 * @version 0.1 : 최초작성
 * <hr>
 * <pre>
 * description..
 * 
 * <b>History:</b>
 * ====================================================================
 *  버전  :    작성일    :  작성자  :  작성내역  
 * --------------------------------------------------------------------   
 *  0.1  2018. 12. 4.   seochan     최초작성
 * ====================================================================
 * </pre>
 */
package codingTest;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONException;
import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;
import net.sf.json.JsonConfig;

public class TADataUtil {
    
    // 파라미터 Value가 String 일 경우 
	public static TAData getParameterMap(HttpServletRequest request) {
		
		// 파라미터 이름 
		Enumeration<String> paramNames = request.getParameterNames(); 

		// 저장할 맵 
		TAData paramMap = new TAData(); 

		// 맵에 저장 
		while(paramNames.hasMoreElements()) { 
			String name = paramNames.nextElement().toString(); 
			String value = request.getParameter(name); 
			
			paramMap.set(name, value); 
		} 

		// 결과반환 
		return paramMap; 
	}
	
	// 파라미터 Value가 String[] 일 경우
	public static TAData getParameterMapArray(HttpServletRequest request) {
		
		// 파라미터 이름 
		Enumeration<String> paramNames = request.getParameterNames(); 

		// 저장할 맵 
		TAData paramMap = new TAData(); 

		// 맵에 저장 
		while(paramNames.hasMoreElements()) { 
			String name = paramNames.nextElement().toString(); 
			String[] value = request.getParameterValues(name); 
			paramMap.set(name, value); 
		} 

		// 결과반환 
		return paramMap; 
	}
	
	//GLN_HEADER, GLN_BODY를 포함한 GLN data format형태의 TAData를 리턴한다.
	public static TAData getGlnDataFormatToTAData(GlnDataHeader glnDataHeader, TAData dataBody) throws JSONException {
		TAData glnDataFormat = new TAData();
		glnDataFormat.set(CommonEnum.Const.GlnHttpBody.DATA_HEADER_NAME, glnDataHeaderToTAData(glnDataHeader));
		glnDataFormat.set(CommonEnum.Const.GlnHttpBody.DATA_BODY_NAME, dataBody);
		return glnDataFormat;
	}
	
	//GLN_HEADER, GLN_BODY를 포함한 GLN data format형태의 Map를 리턴한다.
	public static Map<String, Object> getGlnDataFormatToMap(GlnDataHeader glnDataHeader, TAData dataBody) throws JSONException {
		Map<String, Object> glnDataFormat = new HashMap<String, Object>();
		glnDataFormat.put(CommonEnum.Const.GlnHttpBody.DATA_HEADER_NAME, glnDataHeaderToMap(glnDataHeader));
		glnDataFormat.put(CommonEnum.Const.GlnHttpBody.DATA_BODY_NAME, dataBody.toMap());
		return glnDataFormat;
	}
	
	//GLN_HEADER, GLN_BODY를 포함한 GLN data format형태의 Map를 리턴한다.
	public static Map<String, Object> getGlnDataFormatToMap(GlnDataHeader glnDataHeader, JSONObject dataBody) throws JSONException {
		Map<String, Object> glnDataFormat = new HashMap<String, Object>();
		glnDataFormat.put(CommonEnum.Const.GlnHttpBody.DATA_HEADER_NAME, glnDataHeaderToMap(glnDataHeader));
		glnDataFormat.put(CommonEnum.Const.GlnHttpBody.DATA_BODY_NAME, dataBody);
		return glnDataFormat;
	}
	
	//GLN_HEADER json 객체를 GlnDataHeader 객체로 변환한다.
	public static GlnDataHeader jsonToGlnDataHeader(JSONObject json) throws JSONException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(GlnDataHeader.class);
		return (GlnDataHeader)JSONSerializer.toJava(json, jsonConfig);
	}
	
	//GlnDataHeader 객체를 TAData 객체로 변환한다.
	public static TAData glnDataHeaderToTAData(GlnDataHeader glnDataHeader) throws JSONException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(GlnDataHeader.class);
		return jsonToTAData(JSONObject.fromObject(glnDataHeader, jsonConfig));
	}
	
	//GlnDataHeader 객체를 Map 객체로 변환한다.
	public static Map<String, Object> glnDataHeaderToMap(GlnDataHeader glnDataHeader) throws JSONException {
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setRootClass(GlnDataHeader.class);
		return jsonToTAData(JSONObject.fromObject(glnDataHeader, jsonConfig)).toMap();
	}
	
	public static TAData jsonToTAData(JSONObject json) throws JSONException {
		TAData retMap = new TAData();

	    if(json != null) {
	        retMap = toMap(json);
	    }
	    return retMap;
	}

	public static TAData toMap(JSONObject object) throws JSONException {
		TAData map = new TAData();

	    @SuppressWarnings("unchecked")
		Iterator<String> keysItr = object.keys();
	    while(keysItr.hasNext()) {
	        String key = keysItr.next();
	        Object value = object.get(key);
	        
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }
	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }

//	        if(value instanceof String) {
//	        	try {
//					map.set(key, URLDecoder.decode((String) value, "UTF-8"));
//				} catch (Exception e) {
//					map.set(key, value);
//				}
//	        }
//	        else {
//	        	map.set(key, value);
//	        }
	        
	        map.set(key, value);
	        
	    }
	    return map;
	}

	public static List<Object> toList(JSONArray array) throws JSONException {
	    List<Object> list = new ArrayList<Object>();
	    for(int i = 0; i < array.size(); i++) {
	        Object value = array.get(i);
	        if(value instanceof JSONArray) {
	            value = toList((JSONArray) value);
	        }

	        else if(value instanceof JSONObject) {
	            value = toMap((JSONObject) value);
	        }
	        list.add(value);
	    }
	    return list;
	}

	@SuppressWarnings("unchecked")
	public static JSONObject toJson(TAData root) {
		JSONObject rootJson = new JSONObject();
		for(Iterator<String> i = root.keySet().iterator() ; i.hasNext() ;) {
			String key = i.next();
			Object value = root.get(key);

//			if(value instanceof String) {
//				rootJson.put(key, value);
//			}
//			if(value instanceof Integer) {
//				rootJson.put(key, value);
//			}
//			else if(value instanceof TAData) {
//				rootJson.put(key, toJson((TAData) value));
//			}
//			else if(value instanceof List || value instanceof ArrayList) {
//				rootJson.put(key, toJsonList((List<TAData>) value));
//			}
//			else {
//				System.out.println("this value is null : ["+key+"]["+value+":"+value.getClass()+"]");
//			}
			
			if(value instanceof TAData) {
				rootJson.put(key, toJson((TAData) value));
			}
			else if(value instanceof List || value instanceof ArrayList) {
				rootJson.put(key, toJsonList((List<TAData>) value));
			}
			else if(value instanceof Timestamp) {
				rootJson.put(key, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value));
			}
			else {
				rootJson.put(key, value == null ? "" : value);
			}
		}

		return rootJson;
	}

	public static JSONArray toJsonList(List<TAData> list) {
		JSONArray array = new JSONArray();
		for(Object row : list) {
			JSONObject json = null;
			if(row instanceof TAData) {
				json = toJson((TAData)row);
				array.add(json);
			} else {
				array.add(row);
			}
		}

		return array;
	}
	
	public static List<Map<String, Object>> toMapList(List<TAData> list) {
		if(list == null) return null;
		List<Map<String, Object>> array = new ArrayList<Map<String, Object>>();
		for(TAData row : list) {
			array.add(row.toMap());
		}
		return array;
	}
	
	public static List<TAData> toTADataList(List<Map<String, Object>> list) {
		if(list == null) return null;
		List<TAData> array = new ArrayList<TAData>();
		for(Map<String, Object> row : list) {
			array.add(new TAData(row));
		}
		return array;
	}
	
}
