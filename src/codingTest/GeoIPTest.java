package codingTest;

import java.io.File;
import java.io.IOException;
import java.net.InetAddress;

import org.junit.Test;

import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;

public class GeoIPTest {
	public static void main(String[] args) throws IOException {
		String  dbLocation = "";
//		File database = new File(dbLocation)
//		DatabaseReader dbReader = new DatabaseReader.Builder(database).build();	
	}
	
	@Test
	public void givenIP_whenFetchingCity_thenReturnsCityData() 
	  throws IOException, GeoIp2Exception {
	    String ip = "128.11.22.33";
	    String dbLocation = "D:\\sources\\geoip\\GeoLite2-City.mmdb";
	         
	    File database = new File(dbLocation);
	    DatabaseReader dbReader = new DatabaseReader.Builder(database)
	      .build();
	         
	    InetAddress ipAddress = InetAddress.getByName(ip);
	    CityResponse response = dbReader.city(ipAddress);
	    CountryResponse cResponse = dbReader.country(ipAddress);
	         
	    String countryName = response.getCountry().getName();
	    String countryCode = response.getCountry().getIsoCode();
	    String cityName = response.getCity().getName();
	    String postal = response.getPostal().getCode();
	    String state = response.getLeastSpecificSubdivision().getName();
	    
	    System.out.println("countryName:"+countryName);
	}

}