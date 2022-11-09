package com.planus.openApi.controller;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class OpenApiController {

    private static final Logger logger = LoggerFactory.getLogger(OpenApiController.class);
    @Value("${google.map.key}")
    private String googleMapKey;

    @Value("${naver.map.key}")
    private String NaverMapKey;

    @Value("${naver.map.id}")
    private String NaverMapId;

    @PostMapping("/google")
    public ResponseEntity getGoogle(@RequestBody String url){
        logger.info(url);
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("Accept-Language","ko");
        
        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url+googleMapKey).build();

        ResponseEntity<?> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity,Object.class);
        logger.info(resultMap.toString());
        return resultMap;
    }
    @PostMapping("/naver")
    public ResponseEntity getNaver(@RequestBody String url){

        logger.info(url);

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.add("X-NCP-APIGW-API-KEY-ID",NaverMapId);
        headers.add("X-NCP-APIGW-API-KEY",NaverMapKey);

        HttpEntity<?> entity = new HttpEntity<>(headers);

        UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();

        ResponseEntity<?> resultMap = restTemplate.exchange(uri.toString(), HttpMethod.GET,entity,Object.class);

        logger.info(resultMap.toString());
        return resultMap;
    }
}
