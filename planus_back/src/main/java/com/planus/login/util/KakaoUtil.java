package com.planus.login.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Component
public class KakaoUtil {

    @Value("${spring.security.oauth2.client.registration.kakao.client-id}")
    private String appKey;

    @Value("${spring.security.oauth2.client.provider.kakao.token-uri}")
    private String tokenUri;

    @Value("${spring.security.oauth2.client.registration.kakao.redirect-uri}")
    private String redirectUri;

    @Value("${spring.security.oauth2.client.provider.kakao.user-info-uri}")
    private String userInfoUri;

    @Value("${kakao.adminkey}")
    private String adminKey;

    public String getAccessToken(String code) {
        try{
            URL url = new URL(tokenUri);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("grant_type=authorization_code");
            sb.append("&client_id="+appKey);
            sb.append("&redirect_uri="+redirectUri);
            sb.append("&code="+code);

            bw.write(sb.toString());
            bw.flush();

            if(conn.getResponseCode()==200){
                System.out.println("토큰받기 성공(200)!");
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                String result="";
                String temp="";
                while((temp=br.readLine())!=null){
                    result+=temp;
                }

                JsonElement access_token = JsonParser.parseString(result).getAsJsonObject().get("access_token");
                return access_token.toString();
            }else{
                System.out.println("토큰 받기에서 에러 발생, code="+conn.getResponseCode());
                InputStream errorStream = conn.getErrorStream();
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
                String error = "";
                while((error=br.readLine())!=null) System.out.println(error);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public Map<String, Object> getUserInfo(String accessToken){
        Map<String, Object> resultMap = new HashMap<>();
        try{
            URL url = new URL(userInfoUri);
            HttpURLConnection conn= (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Authorization", "Bearer "+accessToken);
            conn.setDoOutput(true);

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String result="";
            String temp="";
            while((temp=br.readLine())!=null){
                result+=temp;
            }
            JsonObject jsonResult = JsonParser.parseString(result).getAsJsonObject();
            JsonObject account = jsonResult.get("kakao_account").getAsJsonObject();

            long id = Long.parseLong(jsonResult.get("id").getAsString());
            String nickname = account.get("profile").getAsJsonObject()
                                     .get("nickname").getAsString();
            String email = account.get("email").getAsString();

            resultMap.put("id", id);
            resultMap.put("nickname", nickname);
            resultMap.put("email", email);

            return resultMap;
        }catch(Exception e){
            System.out.println("사용자 정보 받기에서 에러");
            return null;
        }
    }

    public void kakaoSignOut(long kakaoId) throws IOException {
        Map<String, Object> resultMap = new HashMap<>();
        String result="";
        URL url = new URL("https://kapi.kakao.com/v1/user/unlink");
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try{

            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            conn.setRequestProperty("Authorization", "KakaoAK " + adminKey);
            conn.setDoOutput(true);
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));
            StringBuilder sb = new StringBuilder();
            sb.append("target_id_type=user_id");
            sb.append("&target_id="+kakaoId);

            bw.write(sb.toString());
            bw.flush();
//            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
//            writer.write("target_id_type=user_id&target_id="+kakaoId);
//            writer.flush();;
//            writer.close();
//            conn.getOutputStream().close();

            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String temp="";
            while((temp=br.readLine())!=null){
                result+=temp;
            }
            JsonObject jsonResult = JsonParser.parseString(result).getAsJsonObject();
            String id = jsonResult.get("id").getAsString();
            System.out.println("id = " +id+" 탈퇴함");
            br.close();


        }catch(Exception e){
            System.out.println("탈퇴에서 에러 발생");
            e.printStackTrace();
            InputStream errorStream = conn.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            String error = "";
            while((error=br.readLine())!=null) System.out.println(error);

        }
    }

}
