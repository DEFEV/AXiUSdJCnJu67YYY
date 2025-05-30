package cl.bermann.tc.utils;

import java.io.IOException;
import java.net.URL;

import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Utils {

	public static String getDireccion(String lat, String lon) {
		StringBuilder jo = new StringBuilder();
//		String urlFrom = "http://192.168.101.146/nominatim/reverse.php?format=json&lat=-27.35107&lon=-70.64724";
		String urlFrom = "http://192.168.101.146/nominatim/reverse.php?format=json&lat="+lat + "&lon=" + lon ;
		JsonNode json = null;
		try {
			json = new ObjectMapper().readTree(new URL(urlFrom));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		jo.append(json.get("display_name").textValue());
	
		return jo.toString();
	}
	
	public static void main(String[] args) {
		System.out.println(getDireccion ("a","b" ));
	}
}

