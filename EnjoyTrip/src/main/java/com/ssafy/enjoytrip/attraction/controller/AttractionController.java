package com.ssafy.enjoytrip.attraction.controller;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

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

import com.ssafy.enjoytrip.attraction.model.AttractionFestival;
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
	public ResponseEntity<List<AttractionFestival>> list(@RequestBody Map<String, Object> map) {
		
		List<AttractionFestival> attractionFestival = new ArrayList<>();
		
		if (map.get("key").equals("all")) {
			attractionFestival.addAll(attractionService.listAttractionFood(Integer.parseInt((String) map.get("sidoCode"))));
			attractionFestival.addAll(attractionService.listAttractionFestival(Integer.parseInt((String) map.get("sidoCode"))));
			attractionFestival.addAll(attractionService.listAttractionFamily(Integer.parseInt((String) map.get("sidoCode"))));
		}
		else if (map.get("key").equals("food")) {
			attractionFestival.addAll(attractionService.listAttractionFood(Integer.parseInt((String) map.get("sidoCode"))));
		}
		else if (map.get("key").equals("festival")) {
			attractionFestival.addAll(attractionService.listAttractionFestival(Integer.parseInt((String) map.get("sidoCode"))));
		}
		else if (map.get("key").equals("family")) {
			attractionFestival.addAll(attractionService.listAttractionFamily(Integer.parseInt((String) map.get("sidoCode"))));
		}
		
//		attractionFestival.sort((o1, o2) -> o1.getTitle().compareTo(o2.getTitle()));
		attractionFestival.sort((o1, o2) -> o1.getContentId() - o2.getContentId());
		
		return ResponseEntity.ok(attractionFestival);
	}
	
	@GetMapping("/parsing/{action}")
	public ResponseEntity<String> parsing(@PathVariable String action) throws IOException, ParseException {
		String apiUrl = "https://apis.data.go.kr/B551011/KorService1/";
		String key = "CVlQ11z3HiNPvOFS0%2BRZ0sQpvOLpMXjb2exTdIFMO1bIYFluA%2FRXP4bwfGaFQkfRrYWaUxBK6FSZrkKS88eWhQ%3D%3D";
		String os = "&MobileOS=ETC";
		String app = "&MobileApp=AppTest";	
		String type = "&_type=json";
		String param = "serviceKey=" + key + os + app + type;
		
		if (action.equals("festival")) {
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
	
}