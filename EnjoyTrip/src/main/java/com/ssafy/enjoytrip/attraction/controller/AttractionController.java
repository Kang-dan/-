package com.ssafy.enjoytrip.attraction.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.enjoytrip.attraction.model.Attraction;
import com.ssafy.enjoytrip.attraction.model.AttractionFestival;
import com.ssafy.enjoytrip.attraction.model.AttractionFood;
import com.ssafy.enjoytrip.attraction.model.service.AttractionService;

@CrossOrigin("*")
@RestController
@RequestMapping("/attraction")
public class AttractionController {
	
	private final AttractionService attractionService;
	public AttractionController(AttractionService attractionService) {
		this.attractionService = attractionService;
	}
	
	@PostMapping
	public ResponseEntity<List<Attraction>> list(@RequestBody Attraction attraction) {
		return ResponseEntity.ok(attractionService.listAttraction(attraction));
	}
	
	@GetMapping("/parsing/{action}")
	public ResponseEntity<String> parsing(@PathVariable String action) throws IOException, ParseException {
		String apiUrl = "https://apis.data.go.kr/B551011/KorService1/";
		String key = "CVlQ11z3HiNPvOFS0%2BRZ0sQpvOLpMXjb2exTdIFMO1bIYFluA%2FRXP4bwfGaFQkfRrYWaUxBK6FSZrkKS88eWhQ%3D%3D";
		String os = "&MobileOS=ETC";
		String app = "&MobileApp=AppTest";
		String type = "&_type=json";
		String param = "serviceKey=" + key + os + app + type;
		
		if (action.equals("food")) {
			apiUrl += "detailIntro1";
			detailIntro(apiUrl, param);
		}
		else if (action.equals("festival")) {
			apiUrl += "searchFestival1";
			searchFestival(apiUrl, param);
		}
		
		return ResponseEntity.ok("성공");
	}
	
	public void searchFestival(String apiUrl, String param) throws IOException, ParseException {
		
		JSONParser parser = new JSONParser();
		int numOfRows = 200;
		int eventStartDate = 20231123;
		
		URL url = new URL(apiUrl + "?" + param + "&eventStartDate=" + eventStartDate + "&numOfRows=" + numOfRows);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		Object obj = parser.parse(new InputStreamReader(connection.getInputStream()));			
		JSONObject jsonMain = (JSONObject)obj;			
		jsonMain = (JSONObject) jsonMain.get("response");
		jsonMain = (JSONObject) jsonMain.get("body");
		
		int totalCount = Integer.parseInt(jsonMain.get("totalCount").toString());
		
		jsonMain = (JSONObject) jsonMain.get("items");
		JSONArray jsonArr = (JSONArray) jsonMain.get("item");
		
		for (int i = 0; i < totalCount; i++) {
			jsonMain = (JSONObject) jsonArr.get(i);			
			AttractionFestival attractionFestival = new AttractionFestival();
			attractionFestival.setAddr1((String) jsonMain.get("addr1"));
			attractionFestival.setAddr2((String) jsonMain.get("addr2"));
			attractionFestival.setContentId(Integer.parseInt(jsonMain.get("contentid").toString()));
			attractionFestival.setContentTypeId(Integer.parseInt(jsonMain.get("contenttypeid").toString()));
			attractionFestival.setEventEndDate((String) jsonMain.get("eventenddate"));
			attractionFestival.setEventStartDate((String) jsonMain.get("eventstartdate"));
			attractionFestival.setFirstImage((String) jsonMain.get("firstimage"));
			attractionFestival.setSidoCode((String) jsonMain.get("areacode"));
			attractionFestival.setGugunCode((String) jsonMain.get("sigungucode"));
			attractionFestival.setLatitude((String) jsonMain.get("mapy"));
			attractionFestival.setLongitude((String) jsonMain.get("mapx"));
			attractionFestival.setMlevel((String) jsonMain.get("mlevel"));
			attractionFestival.setTel((String) jsonMain.get("tel"));
			attractionFestival.setTitle((String) jsonMain.get("title"));
			
			try {
				attractionService.insertAttractionFestival(attractionFestival);
			} catch (Exception e) {
				System.out.println("SQL에러");
			}
		}
		
		System.out.println(jsonMain);

	}
	
	// 음식 메뉴 받아오기
	public void detailIntro(String apiUrl, String param) throws IOException, ParseException {		
		Attraction attraction = new Attraction();		
		attraction.setContentTypeId(39);
		
		// 데이터 하루 1000개 제한. 하루마다 도시 몇개씩 정해서 저장하기.
		int[] sidoCode = {1, 2, 3, 4, 5, 6, 7, 8, 31, 32, 33, 34, 35, 36, 37, 38, 39};
		for (int i : sidoCode) {
			attraction.setSidoCode(i);
			
			List<Attraction> attractionList = attractionService.listAttraction(attraction);
			
			JSONParser parser = new JSONParser();
			String contentTypeId = "&contentTypeId=39";
			param += contentTypeId;
			
			for (Attraction attractionFood : attractionList) {
				String contentId = attractionFood.getContentId() + "";
				URL url = new URL(apiUrl + "?" + param + "&contentId=" + contentId);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				Object obj = parser.parse(new InputStreamReader(connection.getInputStream()));			
				JSONObject jsonMain = (JSONObject)obj;			
				jsonMain = (JSONObject) jsonMain.get("response");
				jsonMain = (JSONObject) jsonMain.get("body");
				
				if (jsonMain.get("totalCount").toString().equals("0")) {
					AttractionFood food = new AttractionFood();
					food.setContentId((int) attractionFood.getContentId());
					food.setFirstMenu("");
					food.setOpenTimeFood("");
					food.setTreatMenu("");
					
					attractionService.insertAttractionFood(food);
					
				}
				else {				
					jsonMain = (JSONObject) jsonMain.get("items");
					JSONArray jsonArr = (JSONArray) jsonMain.get("item");
					jsonMain = (JSONObject) jsonArr.get(0);
					
					AttractionFood food = new AttractionFood();
					food.setContentId((int) attractionFood.getContentId());
					food.setFirstMenu((String) jsonMain.get("firstmenu"));
					food.setOpenTimeFood((String) jsonMain.get("opentimefood"));
					food.setTreatMenu((String) jsonMain.get("treatmenu"));
					
					attractionService.insertAttractionFood(food);
					
				}			
				
			}
			
		}		

	}
	
//	@GetMapping
//	public ResponseEntity<?> listAttraction(@RequestParam AttractionParameter attractionParameter) {
//		AttractionList attractionList = attractionService.listAttraction(attractionParameter);
//		HttpHeaders header = new HttpHeaders();
//		header.setContentType(new MediaType("application", "json", Charset.forName("UTF-8")));
//		return ResponseEntity.ok().headers(header).body(attractionList);
//	}

	
}