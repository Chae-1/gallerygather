package com.kosa.gallerygather.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.annotation.Nullable;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ResourceApiResponse implements Serializable {

    private Header header;
    private Body body;

    @Getter
    @Setter
    public static class Header {
        private String resultCode;
        private String resultMsg;
    }

    @Getter
    @Setter
    public static class Body {
        private Items items;
        private String numOfRows;
        private String pageNo;
        private String totalCount;

    }

    @Getter
    @Setter
    public static class Items {
        private List<Item> items;
    }

    @Getter
    @Setter
    @ToString
    public static class Item {
        @JsonProperty("TITLE")
        @Nullable
        private String title;

        @JsonProperty("CNTC_INSTT_NM")
        @Nullable

        private String cntcInsttNm;

        @JsonProperty("COLLECTED_DATE")
        @Nullable

        private String collectedDate;

        @JsonProperty("ISSUED_DATE")
        @Nullable

        private String issuedDate;

        @JsonProperty("DESCRIPTION")
        @Nullable

        private String description;

        @JsonProperty("IMAGE_OBJECT")
        @Nullable

        private String imageObject;

        @JsonProperty("LOCAL_ID")
        @Nullable

        private String localId;

        @JsonProperty("URL")
        @Nullable

        private String url;

        @JsonProperty("VIEW_COUNT")
        @Nullable

        private String viewCount;

        @JsonProperty("SUB_DESCRIPTION")
        @Nullable

        private String subDescription;

        @JsonProperty("SPATIAL_COVERAGE")

        @Nullable
        private String spatialCoverage;

        @JsonProperty("EVENT_SITE")

        @Nullable
        private String eventSite;

        @JsonProperty("GENRE")

        @Nullable
        private String genre;

        @JsonProperty("DURATION")

        @Nullable
        private String duration;

        @JsonProperty("NUMBER_PAGES")
        @Nullable
        private String numberPages;

        @JsonProperty("TABLE_OF_CONTENTS")
        @Nullable
        private String tableOfContents;

        @JsonProperty("AUTHOR")
        @Nullable
        private String author;

        @JsonProperty("CONTACT_POINT")
        @Nullable
        private String contactPoint;

        @JsonProperty("ACTOR")
        @Nullable
        private String actor;

        @JsonProperty("CONTRIBUTOR")
        @Nullable
        private String contributor;

        @JsonProperty("AUDIENCE")
        @Nullable
        private String audience;

        @JsonProperty("CHARGE")
        @Nullable
        private String charge;

        @JsonProperty("PERIOD")
        @Nullable
        private String period;

        @JsonProperty("EVENT_PERIOD")
        @Nullable
        private String eventPeriod;
    }


    public List<Item> getItems() {
        return body.getItems().getItems();
    }
}