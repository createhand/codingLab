package kised;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import codingTest.Consts;
import oracle.jdbc.driver.OracleConnection;

public class AsisToBeColumnConverter  {
	
	public static final boolean checkTableValid = false;
	public static final boolean displayColumnVariable = false;
	public static final boolean displayConditionVariable = true;
	public static final boolean displayTableName = false;
	public static final boolean displayColumnName = false;
	
	public static final boolean displayLoading = false;
	
	static Comparator<String> comparator = (s1, s2)->s2.compareTo(s1);
	public static Map<String, String> variables = new TreeMap<>(comparator);
	
    public static void main(String[] args) {
    	
		try {
			System.out.println("================ START ===============");
			
			if(displayTableName) {
				getTableColumnNames("");
			} else {
				for(String tableName : Consts.tableNames) {
					getTableColumnNames(tableName);
				}
			}
			
			//asis-tobe 변환 목록 출력
			int j = 1000;			
			for(Iterator<String> i = variables.keySet().iterator() ; i.hasNext() ;) {
				String key = i.next();
				String value = variables.get(key);
				
				if(displayTableName) {
					System.out.println("{\""+key+"\", \""+value+"\"},");
				}
				
				if(displayColumnVariable) {
					//mybatis binding
    				System.out.println("{\"#{"+key+"}\", \"#{"+value+"}\"},");
					//java default variable
//					j++;
//    				System.out.println("tempMap.put(\""+value+"\", \""+j+"\");");
				}
				if(displayConditionVariable) {
					//mybatis if
					System.out.println("{\""+key+"\", \""+value+"\"},");
				}
				
				if(displayColumnName) {
    				System.out.println("{\""+key+"\", \""+value+"\"},");
				}
				
				
			}
			
		} catch (SQLException e1) {
			System.out.println("SQLException:"+e1.getMessage());
			e1.printStackTrace();
		}
    }
    
    @SuppressWarnings("unused")
	public static Map<String, String> getTableColumnNames(String table) throws SQLException {
    	Map<String, String> columns = new HashMap<String, String>();
    	OracleConnection conn = (OracleConnection)DriverManager.getConnection(Consts.TOBE_DB_URL, Consts.TOBE_DB_ID, Consts.TOBE_DB_PWD);
    	conn.setAutoCommit(false);
    	PreparedStatement tablePstmt = null;
    	ResultSet tableResult = null;
    	
    	StringBuilder tableComment = new StringBuilder();
    	
    	if(displayTableName) {
    		tableComment.append("SELECT DISTINCT ASIS_ENG_TB, TOBE_ENG_TB FROM T_ASIS_TOBE_COL ");
    		tableComment.append("WHERE ASIS_ENG_TB IS NOT NULL ");
    		tableComment.append("AND TOBE_ENG_TB IS NOT NULL ");
    		tableComment.append("AND USE_YN = 'Y' ");
    		tableComment.append("ORDER BY ASIS_ENG_TB DESC");
    	} else {
    		tableComment.append("SELECT ASIS_ENG_TB, ASIS_ENG_COL, TOBE_ENG_TB, TOBE_ENG_COL  FROM T_ASIS_TOBE_COL ");
    		tableComment.append("WHERE ASIS_ENG_TB IS NOT NULL ");
    		tableComment.append("AND TOBE_ENG_TB IS NOT NULL ");
    		tableComment.append("AND USE_YN = 'Y' ");
    		tableComment.append("AND ASIS_ENG_TB = '"+table+"'");
    	}
    	
		tablePstmt = conn.prepareStatement(tableComment.toString());
		
    	try {
    		tableResult = tablePstmt.executeQuery();
    		
    		//get table comments
    		while(tableResult.next()) {
    			
    			String asisTableName = tableResult.getString("ASIS_ENG_TB");
    			String tobeTableName = tableResult.getString("TOBE_ENG_TB");
    			String asisColumnName = null;
    			String tobeColumnName = null;
    			
    			if(displayLoading) {
    				System.out.print(".");
    			}
    			
    			if(checkTableValid) {
    				
        			tobeColumnName = tableResult.getString("TOBE_ENG_COL");        			
        			String query = "SELECT "+tobeColumnName+" FROM "+tobeTableName;
        			PreparedStatement validPstmt = null;
        			
        			try {
	        			validPstmt = conn.prepareStatement(query);
	        	    	validPstmt.executeQuery();
        			} catch (Exception e) {
//        				System.out.println("SELECT * FROM "+tobeTableName+";");
//        				System.out.println("UPDATE T_ASIS_TOBE_COL SET TOBE_ENG_COL = '' WHERE TOBE_ENG_TB = '"+tobeTableName+"' AND TOBE_ENG_COL = '"+tobeColumnName+"';");
//        				System.out.println("\n\n");
        				System.out.println(tobeTableName+"."+tobeColumnName);
//						System.out.println(e.getMessage());
					} finally {
						validPstmt.close();
					}
    			}
    			
    			if(displayTableName) {
    				
    				if(asisTableName == null || tobeTableName == null) {
    					System.out.println("asis or tobe null ["+asisTableName+ " / " +tobeTableName+"]");
    				} else {
    					variables.put(asisTableName, tobeTableName);
    				}
    			}
    			
    			if(displayColumnVariable || displayConditionVariable) {
    				
    				asisColumnName = tableResult.getString("ASIS_ENG_COL");
        			tobeColumnName = tableResult.getString("TOBE_ENG_COL");
	    			
	    			if(asisColumnName == null || tobeColumnName == null) {
	    				System.out.println("asis or tobe null ["+asisColumnName+ " / " +tobeColumnName+"]");
	    			} else {
	    				String asisCamel = convertCamelCase(asisColumnName);
	    				String tobeCamel = convertCamelCase(tobeColumnName);
	    				variables.put(asisCamel, tobeCamel);
	    			}
    			}
    			
    			if(displayColumnName) {
    				
    				asisColumnName = tableResult.getString("ASIS_ENG_COL");
        			tobeColumnName = tableResult.getString("TOBE_ENG_COL");
        			
	    			if(asisColumnName == null || tobeColumnName == null) {
	    				System.out.println("asis or tobe null ["+asisColumnName+ " / " +tobeColumnName+"]");
	    			} else {
	    				variables.put(asisColumnName, tobeColumnName);
	    			}
    			}
    		}
    		
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println(e.getMessage());
		} finally {
			tablePstmt.close();
			tableResult.close();
			conn.close();
			
			
		}
    	return columns;
    }
    
    public static String convertCamelCase(String str) {
    	if(StringUtils.isBlank(str)) return str;
    	String[] words = str.toLowerCase().split("_");
		StringBuilder variableName = new StringBuilder(words[0]);
		for(int j=1;j<words.length;j++) {
			variableName.append(Character.toUpperCase(words[j].charAt(0)));
			variableName.append(words[j].substring(1));
		}
		return variableName.toString();
    }
}