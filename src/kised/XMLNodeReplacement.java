package kised;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class XMLNodeReplacement {
	
	public static final boolean getEscapeVariables = true;
	public static long startTime = System.currentTimeMillis();
	public static Map<String, String> variables = new HashMap<String, String>();
	
	public static void main(String args[]) {
		
		
		
		//대체대상파일 경로
		String paths[] = {
				//"D:\\temp\\temp\\temp_convert"
//				"D:\\KisedSDK3\\workspace\\codingutils\\resources\\bank_convert"
				"D:\\KisedSDK3\\workspace\\pms_bank\\src\\main\\resources\\sqlmap\\mappers\\exm\\batch",
//				"D:\\KisedSDK3\\workspace\\pms-admin\\src\\main\\resources\\sqlmap\\mappers\\exm"
		};
		
		//node replace
		for(String path : paths) {
			readAndReplace(path);
		}

		//ibatis 변수 목록 출력
		if(getEscapeVariables) {
			System.out.println("============= START =============");
			for(Iterator<String> i = variables.keySet().iterator() ; i.hasNext() ;) {
				String key = i.next();
				String value = variables.get(key);
				System.out.println("{\"#"+value+ "#\", \"#{" +value+"}\"},");
			}
		}
	}

	public static void readAndReplace(String path) {
		
		//경로
		File dir = new File(path);
		if(!dir.exists()) {
    		dir.mkdirs();
    	}
		
		File[] files = dir.listFiles();
		for(File file : files) {
			
			try {
			
				String absolutePath = file.getAbsolutePath();
				
				if(file.isDirectory()) {
					readAndReplace(absolutePath);
				}
				
				if(file.getName().endsWith(".xml")) {
					
//					System.out.println((System.currentTimeMillis()-startTime)+" ms");
					
//					System.out.println("absolutePath:"+absolutePath);
					
					DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
					dbFactory.setValidating(false);
					dbFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
					DocumentBuilder  dBuilder = dbFactory.newDocumentBuilder();
					Document doc = dBuilder.parse(absolutePath);
					doc.getDocumentElement().normalize();
					
					//selectKey 제거
					//procedure -> select 대체
					String[] replaceNodes = {
							//replace
							"isNotEmpty", "isEmpty", "isEqual", "isNotEqual" 
							,"sqlMap", "iterate", "dynamic", "procedure"
							//remove
							,"typeAlias", "resultMap", "parameterMap", "selectKey"		
					};
					
					for(String replaceNode : replaceNodes) {
						while(true) {
							NodeList nodeList = doc.getElementsByTagName(replaceNode);
							int nodeListLen = nodeList.getLength();
							if(nodeListLen == 0) break;				
							
							Node node = nodeList.item(nodeListLen-1);						
							if(node == null) continue;
							
							convertNode(doc, node);
							
							writeDocument(doc, absolutePath);
						}
					}
					
					
					
					String[] targetNodes = {
						//update
						"select", "insert", "update", "delete", "include"
					};
					
					for(String targetNode : targetNodes) {
						
						NodeList nodeList = doc.getElementsByTagName(targetNode);
						int nodeListLen = nodeList.getLength();
						
						for(int j=0;j<nodeListLen;j++) {
							Node node = nodeList.item(j);						
							if(node == null) continue;
							
							convertNode(doc, node);
							Element nodeElement = ((Element)node);
							if(nodeElement.hasAttribute("id")) {
								//mapper
//								System.out.print("public List<Map<String, Object>> "); 
//								System.out.print(nodeElement.getAttribute("id"));
//								System.out.println("(Map<String, Object> in) throws SQLException;");
								
								//serviceImpl
//								System.out.println("@Override");
//								System.out.print("public List<Map<String, Object>> "); 
//								System.out.print(nodeElement.getAttribute("id"));
//								System.out.println("(Map<String, Object> in) throws SQLException {");
//								System.out.println("return mapper."+nodeElement.getAttribute("id")+"(in);");
//								System.out.println("}");
							}
							writeDocument(doc, absolutePath);
						}
					}					
					
				}
			
			} catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static NodeList getNodeList(Document doc, String nodeName) {
		return doc.getElementsByTagName("isNotEmpty");		
	}
	
	public static void convertNode(Document doc, Node node) {
		
		Element nodeElement = ((Element)node);
//		NamedNodeMap attributes = node.getAttributes();
		String nodeName = node.getNodeName();
		String nodeTextContent = node.getTextContent();
		
		String propertyValue = nodeElement.getAttribute("property");
		String compareValue = nodeElement.getAttribute("compareValue");
		String compareProperty = nodeElement.getAttribute("compareProperty");
//		if(attributes.getNamedItem("property") != null) {
//			propertyValue = attributes.getNamedItem("property").getNodeValue();
//		}
		
//		System.out.println("nodeName:"+nodeName);
//		System.out.println("nodeValue:"+node.getTextContent());
		
		System.out.print(".");
		
		
		if(getEscapeVariables) {
			getIbatisVariables(nodeTextContent, "#");
		}
		
		
		
		
		if(nodeName.equals("isNotEmpty")) {
			
			changePrepend(nodeElement);
			
			doc.renameNode(node, null, "if");
			nodeElement.setAttribute("test", propertyValue+" != '' and "+propertyValue+" != null");
			
		} else if(nodeName.equals("isEqual")) {
			
			changePrepend(nodeElement);
			
			doc.renameNode(node, null, "if");
//			String compareValue = nodeElement.getAttribute("compareValue");
//			if(attributes.getNamedItem("compareValue") != null) {
//				compareValue = attributes.getNamedItem("compareValue").getNodeValue();
//			}
			nodeElement.setAttribute("test", propertyValue+" == '"+compareValue+"'");
			if(StringUtils.isBlank(propertyValue)) {
				nodeElement.setAttribute("test", compareProperty+" == '"+compareValue+"'");	
			}
			
		} else if(nodeName.equals("isNotEqual")) {
			
			changePrepend(nodeElement);
			
			doc.renameNode(node, null, "if");
//			String compareValue = null;
//			if(attributes.getNamedItem("compareValue") != null) {
//				compareValue = attributes.getNamedItem("compareValue").getNodeValue();
//			}
			nodeElement.setAttribute("test", propertyValue+" != '"+compareValue+"'");
			
			
		} else if(nodeName.equals("isEmpty")) {
			
			changePrepend(nodeElement);
			
			doc.renameNode(node, null, "if");
			nodeElement.setAttribute("test", propertyValue+" == null or "+propertyValue+" == ''");
			
			
		} else if(nodeName.equals("sqlMap")) {
			
			doc.renameNode(node, null, "mapper");
			String namespace = nodeElement.getAttribute("namespace");
			nodeElement.setAttribute("namespace", "pms.service.exm.mapper.ASISMapper");
			
			if(namespace.equals("BankComm")) {
				nodeElement.setAttribute("namespace", "ASIS_BankComm");
			}
		} else if(nodeName.equals("iterate")) {
			
			changePrepend(nodeElement);
			
			doc.renameNode(node, null, "foreach");
			nodeElement.setAttribute("separator", nodeElement.getAttribute("conjunction"));
			nodeElement.setAttribute("collection", nodeElement.getAttribute("property"));
			nodeElement.setAttribute("item", "item");
			nodeElement.removeAttribute("conjunction");
			nodeElement.removeAttribute("property");
			nodeElement.setTextContent("#item#");
			//<iterate close=")" conjunction="," open="(" property="arrBuclCd">#arrBuclCd[]#</iterate>
		} else if(nodeName.equals("dynamic")) {
			doc.renameNode(node, null, "where");
			nodeElement.removeAttribute("prepend");
		} else if(nodeName.equals("procedure")) {
			doc.renameNode(node, null, "select");
		} else if(nodeName.equals("select")
				|| nodeName.equals("insert")
				|| nodeName.equals("update")
				|| nodeName.equals("delete")
				|| nodeName.equals("procedure")) {
			
			String chgId = nodeElement.getAttribute("id").replace(".", "_");
			nodeElement.setAttribute("id", chgId);
			//remove attributes
			nodeElement.removeAttribute("parameterClass");
			nodeElement.removeAttribute("resultClass");

			//set attributes
			nodeElement.setAttribute("parameterType", "java.util.Map");
			if(nodeName.equals("select")
					|| nodeName.equals("procedure")) {
				nodeElement.setAttribute("resultType", "java.util.Map");
			}
		} else if(nodeName.equals("include")) {
			String refid = nodeElement.getAttribute("refid");
			if(refid.indexOf(".") == -1) {
				if(!StringUtils.equals(refid, "strDmaRole")) {
					refid = "ASIS_BankComm." + refid;
					nodeElement.setAttribute("refid", refid);
				}
			}
			
		} else if(nodeName.equals("typeAlias") 
				|| nodeName.equals("resultMap")
				|| nodeName.equals("parameterMap")) {
//				|| nodeName.equals("selectKey")) {
//			doc.removeChild(node);
			node.getParentNode().removeChild(node);
		} else if(nodeName.equals("selectKey")) {
			node.getParentNode().removeChild(node);
		}
		
		//remove attributes
		nodeElement.removeAttribute("property");
		nodeElement.removeAttribute("compareValue");
		nodeElement.removeAttribute("compareProperty");
		nodeElement.removeAttribute("resultMap");
		nodeElement.removeAttribute("parameterMap");
		nodeElement.removeAttribute("remapResults");
		
	}
	
	public static void writeDocument(Document doc, String path) {
		try {
			DOMSource source = new DOMSource(doc);
			StreamResult f = new StreamResult(path);
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transf = transformerFactory.newTransformer();
			transf.setOutputProperty(OutputKeys.DOCTYPE_PUBLIC, "-//mybatis.org//DTD Mapper 3.0//EN");
			transf.setOutputProperty(OutputKeys.DOCTYPE_SYSTEM, "http://mybatis.org/dtd/mybatis-3-mapper.dtd");
			transf.transform(source, f);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void changePrepend(Element nodeElement) {
		try {
			if(nodeElement.hasAttribute("prepend")) {
				String prepend = nodeElement.getAttribute("prepend");
				String textContent = " " + prepend + " " + nodeElement.getTextContent();
				nodeElement.setTextContent(textContent);
			}
			nodeElement.removeAttribute("prepend");
			
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	//ibatis 변수 목록
	public static void getIbatisVariables(String text, String escape) {
		int idx = text.indexOf(escape);
		while(idx > -1) {
			int start = text.indexOf(escape) + 1;
			int end = text.substring(start).indexOf(escape) + start;
			if(start == -1 || end == -1) {
				break;
			}
			String temp = text.substring(start, end);
			text = text.substring(end+1);
			variables.put(temp, temp);
//			System.out.println("{\"#"+temp+ "#\", \"#{" +temp+"}\"},");
//			System.out.println("text:"+text);
			idx = text.indexOf(escape);
		}
		
	}
	
	public static boolean isMatch(String str) {
		//영문 또는 언더바(_) 체크
		if(Pattern.matches("^[A-Z0-9]*$", str)) {
			return true;
		}
		if(str.equals("_")) {
			return true;
		}
		return false;
	}
	
	
}