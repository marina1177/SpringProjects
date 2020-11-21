package parser.services;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@Data
public class JsonLoader {

  @Value("${url}")
  private String url;

  @Value("${spring.fieldName}")
  private String fieldName;

  public JSONArray load() {

    try {
      URL obj = new URL(url);
      HttpURLConnection con = (HttpURLConnection) obj.openConnection();

      log.info("\nSending 'GET' request to URL : {}", url);
      log.info("Response Code : {}", con.getResponseCode());

      BufferedReader in = new BufferedReader(
          new InputStreamReader(con.getInputStream()));

      JSONParser parser = new JSONParser();

      JSONObject newJsonObject = new JSONObject();
      newJsonObject.put(fieldName, parser.parse(in));
      in.close();

      JSONArray resultArray = new JSONArray();
      resultArray.add(newJsonObject);
      return resultArray;

    } catch (Exception e) {
      log.warn("Fail to connect");
    }
    return new JSONArray();
  }
}
