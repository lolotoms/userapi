package com.example.UserApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.json.HTTP;

@RestController
@SpringBootApplication
public class UserApiApplication {

	//public static final String serverUrl = "http://localhost:9090";
	public static final String serverUrl = "https://9090-dot-11253447-dot-devshell.appspot.com";

	public static void main(String[] args) {
		SpringApplication.run(UserApiApplication.class, args);
	}
	
	public static String RequestProcessedData(String url){
		RestTemplate request = new RestTemplate();
		String result = request.getForObject(url, String.class);
		System.out.println(url);
		return (result);
	}
	
	@GetMapping("/")
	public static String Hello() {
		return "I'M THE CONVERTOR";
	}
	
	@GetMapping("/codeToState")
	public static String CodetoState(@RequestParam("code") String code) {
		String state = null;
		try {
			String response = RequestProcessedData(serverUrl+"/readDataForCode");
			
			System.out.println("response : "+HTTP.toJSONObject(serverUrl+"/readDataForCode"));
			JSONObject jsonObject = new JSONObject(response);
			
			System.out.println("jsonObject : "+jsonObject);
			state = jsonObject.getString(code.toUpperCase());
			
			System.out.println("codeToState " + code + " : " + jsonObject);
			
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
			String response = RequestProcessedData(serverUrl+"/readDataForState");
			
			System.out.println("response : "+HTTP.toJSONObject(serverUrl+"/readDataForState"));
			
			JSONArray jsonArray = new JSONArray(response);
			for (int n = 0; n < jsonArray.length(); n++) {
				JSONObject object = jsonArray.getJSONObject(n);
				System.out.println("jsonObject : "+ object);
				
				String name = object.getString("name");
				if(state.equalsIgnoreCase(name)) {
					value = object.getString("abbreviation");
					break;
				}
				System.out.println("stateToCode " + state + " : " + object);
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
