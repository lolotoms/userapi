package com.example.UserApi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@SpringBootApplication
public class UserApiApplication {

	public static final String serverUrl = "http://localhost:9090";
	
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
			JSONObject jsonObject = new JSONObject(response);
			state = jsonObject.getString(code.toUpperCase());
			System.out.println("STATE : "+state);
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
			JSONArray jsonArray = new JSONArray(response);
			for (int n = 0; n < jsonArray.length(); n++) {
				JSONObject object = jsonArray.getJSONObject(n);
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
