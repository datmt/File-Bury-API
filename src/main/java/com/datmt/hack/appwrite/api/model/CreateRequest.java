package com.datmt.hack.appwrite.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class CreateRequest {
    //{documentId: "unique()", data: {code: 762786, text: "adsfasdfasdfsadfsdf"}}
    private String documentId;
    private int code;
    private String text;

    @JsonProperty("data")
    public void setLng(Map<String, String> coordinates) {
        this.code = (Integer.parseInt(coordinates.get("code")));
    }
}
