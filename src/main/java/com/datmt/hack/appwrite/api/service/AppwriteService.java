package com.datmt.hack.appwrite.api.service;


import com.datmt.hack.appwrite.api.model.CreateRequest;
import com.datmt.hack.appwrite.api.model.CreateResponse;
import com.google.gson.Gson;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;

@Service
public class AppwriteService {

    @Value("${appwrite.url}")
    String appwriteURL;
    @Value("${appwrite.collection_id}")
    String collectionID;
    @Value("${appwrite.project_id}")
    String projectId;

    public CreateResponse createDocument(HttpServletRequest request, String dbId) throws UnirestException, IOException {
        String payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Map<String, String> headers = new HashMap<>();

        var requestHeaders = request.getHeaderNames();
        while (requestHeaders.hasMoreElements()) {
            String name1 = requestHeaders.nextElement();
            if (!name1.toLowerCase(Locale.ROOT).contains("content-length"))
                headers.put(name1, request.getHeader(name1));
        }
        //http://localhost:8818/v1/database/collections/62754731c4635af92550/documents
        //http://localhost:8818/v1/collections/62754731c4635af92550/documents
        int code = new Random().nextInt(999999 - 100000) + 100000;

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.postForObject(appwriteURL + "database/collections/" + dbId + "/documents", payload, Map.class);
        return Unirest
                .post(appwriteURL + "database/collections/" + dbId + "/documents")

                .headers(headers)
                .body(payload)
                .asObject(CreateResponse.class)
                .getBody();

    }
}
