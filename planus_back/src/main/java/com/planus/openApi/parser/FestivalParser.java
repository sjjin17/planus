package com.planus.openApi.parser;

import com.planus.db.entity.Festival;
import com.planus.db.repository.AreaRepository;
import com.planus.db.repository.FestivalRepository;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

@Component
public class FestivalParser {
    private AreaRepository areaRepository;
    private FestivalRepository festivalRepository;

    @Autowired
    public FestivalParser(AreaRepository areaRepository, FestivalRepository festivalRepository) {
        this.areaRepository = areaRepository;
        this.festivalRepository = festivalRepository;
    }

//    @Scheduled(cron = "0 0 0/1 * * *") //한시간마다 실행
    @Scheduled(cron = "0 0 0 * * ?") //자정마다 실행 "초 분 시 일 월 주 (년)"
//    @Scheduled(cron = "0 25 5 * * ?")
    public void getFestival(){
        try{
//            URL url = new URL("http://apis.data.go.kr/B551011/KorService/searchFestival?serviceKey=uC7oHJwGi%2FOBB070p2RKZ%2Fz9sCPt8z59HFwDdx5bta5Jky5pSo0pSF8etCED%2BF%2Fyc6cSGJR%2BLZ0qWBcZqdsufQ%3D%3D&numOfRows=10000&MobileOS=ETC&MobileApp=Planus&_type=json&eventStartDate=00000000");
//            HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
//            urlConnection.setRequestMethod("GET");
//            urlConnection.setRequestProperty("Content-type","application/json");
//
//            BufferedReader bf = new BufferedReader(new InputStreamReader(url.openStream(),"UTF-8"));
//            result.append(bf.readLine());

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://apis.data.go.kr/B551011/KorService/searchFestival?serviceKey=uC7oHJwGi%2FOBB070p2RKZ%2Fz9sCPt8z59HFwDdx5bta5Jky5pSo0pSF8etCED%2BF%2Fyc6cSGJR%2BLZ0qWBcZqdsufQ%3D%3D&numOfRows=10000&MobileOS=ETC&MobileApp=Planus&_type=json&eventStartDate=00000000"))
                    .build();

            HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) ((JSONObject) ((JSONObject) ((JSONObject) jsonParser.parse(response.body().toString())).get("response")).get("body")).get("items");
            JSONArray jsonArray = (JSONArray) jsonObject.get("item");

            festivalRepository.deleteAllInBatch();

            for (int i=0; i<jsonArray.size(); i++){
                JSONObject object = (JSONObject) jsonArray.get(i);
                String title = (String) object.get("title");
                String address = (String) object.get("addr1");
                String imageUrl = (String) object.get("firstimage");
                String startDate = (String) object.get("eventstartdate");
                String endDate = (String) object.get("eventenddate");

                if (!title.equals("")&&!address.equals("")&&address.length()>2&&!imageUrl.equals("")&&!startDate.equals("")&&!endDate.equals("")){
                    if (address.substring(2,4).equals("특별")||address.substring(2,4).equals("광역")){
                        String doName = address.substring(0,2);

                        if(!object.get("addr2").equals("")){
                            address += " "+(String) object.get("addr2");
                        }

                        long areaId = 0;
                        switch (doName){
                            case "서울":
                                areaId = 1;
                                break;
                            case "인천":
                                areaId = 2;
                                break;
                            case "대전":
                                areaId = 52;
                                break;
                            case "세종":
                                areaId = 53;
                                break;
                            case "부산":
                                areaId = 80;
                                break;
                            case "대구":
                                areaId = 81;
                                break;
                            case "울산":
                                areaId = 82;
                                break;
                            case "광주":
                                areaId = 124;
                                break;
                            case "제주":
                                areaId = 161;
                                break;
                        }

                        Festival festival = Festival.builder()
                                .title(title)
                                .address(address)
                                .imageUrl(imageUrl)
                                .startDate(LocalDate.parse(startDate.substring(0,4)+"-"+startDate.substring(4,6)+"-"+startDate.substring(6)))
                                .endDate(LocalDate.parse(endDate.substring(0,4)+"-"+endDate.substring(4,6)+"-"+endDate.substring(6)))
                                .area(areaRepository.findByAreaId(areaId))
                                .build();

                        festivalRepository.save(festival);
                    }else{
                        String doName = address.substring(0,2);

                        String[] array = address.split("도 ");

                        if (array.length>1){
                            String siName = array[1].substring(0,2);

                            if(!object.get("addr2").equals("")){
                                address += " "+(String) object.get("addr2");
                            }

                            Festival festival = Festival.builder()
                                    .title(title)
                                    .address(address)
                                    .imageUrl(imageUrl)
                                    .startDate(LocalDate.parse(startDate.substring(0,4)+"-"+startDate.substring(4,6)+"-"+startDate.substring(6)))
                                    .endDate(LocalDate.parse(endDate.substring(0,4)+"-"+endDate.substring(4,6)+"-"+endDate.substring(6)))
                                    .area(areaRepository.findByDoNameStartsWithAndSiNameStartsWith(doName,siName))
                                    .build();

                            festivalRepository.save(festival);
                        }
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
