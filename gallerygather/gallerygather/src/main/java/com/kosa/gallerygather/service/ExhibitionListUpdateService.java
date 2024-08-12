package com.kosa.gallerygather.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kosa.gallerygather.dto.ResourceApiResponse;
import com.kosa.gallerygather.entity.Exhibition;
import com.kosa.gallerygather.repository.ExhibitionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Pattern;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;
import java.time.LocalDate;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class ExhibitionListUpdateService {

    private final String resourceOriginUrl = "http://api.kcisa.kr/openapi/API_CCA_145/request";
    private final String key = "46cd54e4-19c0-4a21-a4fb-e96cf2eabcd6";
    private final ObjectMapper mapper = new ObjectMapper();
    private final ExhibitionRepository repository;

    private static final Pattern INTEGER_PATTERN = Pattern.compile("-?\\d+");

//    @PostConstruct
    public void init() {
        for (int pageNum = 0; pageNum < 22; pageNum++) {
            try {
                callExhibitionUpdateRequest(10, pageNum);
            } catch (Exception e) {
                System.out.println("호출 실패");
            }
        }
    }

    // 반드시 리팩터링이 필요
    public void callExhibitionUpdateRequest(int recordPerSession, int pageNum) throws Exception {
        StringBuilder urlBuilder = new StringBuilder(resourceOriginUrl); /*URL*/
        urlBuilder.append("?" + URLEncoder.encode("serviceKey", "UTF-8") + "=" + key); /*서비스키*/
        urlBuilder.append("&" + URLEncoder.encode("numOfRows", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(recordPerSession), "UTF-8")); /*세션당 요청레코드수*/
        urlBuilder.append("&" + URLEncoder.encode("pageNo", "UTF-8") + "=" + URLEncoder.encode(String.valueOf(pageNum), "UTF-8")); /*페이지수*/

        URL url = new URL(urlBuilder.toString());
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        conn.setRequestMethod("GET");
        conn.setRequestProperty("Content-type", "application/json; charset=utf-8");
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
            try {
                for (ResourceApiResponse.Item item : items) {
                    System.out.println(item);
                    String[] split = item.getPeriod().split("~");
                    LocalDate startDate = LocalDate.parse(split[0].trim());
                    LocalDate endDate = LocalDate.parse(split[0].trim());

                    repository.save(
                            Exhibition.builder()
                                    .title(item.getTitle())
                                    .description(item.getDescription())
                                    .startDate(startDate)
                                    .endDate(endDate)
                                    .author(item.getAuthor())
                                    .imgUrl(item.getImageObject())
                                    .place(item.getEventSite())
                                    .charge(changeToCharge(item.getCharge()))
                                    .siteUrl(item.getUrl())
                                    .likeCount(0)
                                    .readCount(0)
                                    .reviewCount(0)
                                    .avgRating(0D).build());
                }
            } catch (Exception ex1) {
                ex1.printStackTrace();
                System.out.println("해당 레코드 저장 실패");
            }
        } catch (Exception e) {
            e.
                    printStackTrace();
            System.out.println("해당 컬럼 실패");
        }
    }

    private String changeToCharge(String charge) {
        if (charge == null) {
            return "알수 없음";
        }
        if (charge.equals("0")) {
            return "무료";
        }
        return charge;
    }
}
