package com.kosa.gallerygather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.gallerygather.dto.ResourceApiResponse;
import com.kosa.gallerygather.entity.Exhibition;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.regex.Pattern;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ExhibitionListUpdateService {

    private String resourceOriginUrl = "http://api.kcisa.kr/openapi/API_CCA_145/request";
    private String key = "46cd54e4-19c0-4a21-a4fb-e96cf2eabcd6";
    private ObjectMapper mapper = new ObjectMapper();

    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");

    public void updateExhibition() throws Exception {
        StringBuilder urlBuilder = new StringBuilder("http://api.kcisa.kr/openapi/API_CCA_145/request"); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key); /*서비스키*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode("3", "UTF-8")); /*세션당 요청레코드수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode("1", "UTF-8")); /*페이지수*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        System.out.println("Response code: " + conn.getResponseCode());

        BufferedReader rd;
        if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
            rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        } else {
            rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
        }
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = rd.readLine()) != null) {

            sb.append(line);

        }
        rd.close();
        conn.disconnect();
        try {
            JsonNode rootNode = mapper.readTree(sb.toString());
            JsonNode itemsNode = rootNode.path("response").path("body").path("items").path("item");
            List<ResourceApiResponse.Item> items = mapper.convertValue(itemsNode, new TypeReference<>() {
            });

            for (ResourceApiResponse.Item item : items) {
                System.out.println(item);
                String[] split = item.getPeriod().split("~");
                LocalDate startDate = LocalDate.parse(split[0]);
                LocalDate endDate = LocalDate.parse(split[0]);

                // 나중에
                Exhibition.builder()
                        .startDate(startDate)
                        .endDate(endDate)
                        .author(item.getAuthor())
                        .charge(changeToCharge(item.getCharge()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private int changeToCharge(String charge) {
        if (charge == null || isNotNumber(charge)) {
            return 0;
        }
        return Integer.parseInt(charge);
    }

    private boolean isNotNumber(String charge) {
        return INTEGER_PATTERN.matcher(charge).matches();
    }
}
