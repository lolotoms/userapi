package com.example.UserApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

import org.json.HTTP;

@RestController
@SpringBootApplication
public class UserApiApplication {

	//public static final String serverUrl = "http://localhost:9090";
	public static final String serverUrl = "https://9090-dot-11253447-dot-devshell.appspot.com";

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}
	
	public static JSONObject RequestProcessedData(String url){
		RestTemplate request = new RestTemplate();
	
		
		ResponseEntity<String> resultEntity = request.getForEntity(url, String.class);
		JSONObject json = new JSONObject(resultEntity.getBody());
		System.out.println("resultObject "+ url + " : " + json);
		System.out.println("resultEntity "+ url + " : " + json.toString());

		return (json);
	}
	
	@GetMapping("/")
	public static String Hello() {
		return "I'M THE CONVERTOR";
	}
	
	@GetMapping("/codeToState")
	public static String CodetoState(@RequestParam("code") String code) {
		String state = null;
		try {
			JSONObject jsonObject = RequestProcessedData(serverUrl+"/readDataForCode");		
			state = jsonObject.getString(code);
			
		} catch (Exception e) {
			System.out.println("[ERROR] : [CUSTOM_LOG] : "+e);
		}
		if(state == null) {
			state = "No Match Found";
		}
		return state;
	}
	
	@GetMapping("/stateToCode")
	public static String StateToCode(@RequestParam("state") String state){
		String value = "";
		try {
			JSONObject response = RequestProcessedData(serverUrl+"/readDataForState");
			
			JSONArray jsonArray = new JSONArray(response);
			
			for (int n = 0; n < jsonArray.length(); n++) {
				JSONObject object = jsonArray.getJSONObject(n);
				System.out.println("jsonObject : " + object);
				
				String name = object.getString("name");
				
				if(state.equalsIgnoreCase(name)) {
					value = object.getString("abbreviation");
					break;
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("[ERROR] : [CUSTOM_LOG] : "+e);
		}
		if (value == null) {
			value = "No match found";
		}
		return value;
	}

}
