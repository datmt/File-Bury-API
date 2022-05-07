package com.datmt.hack.appwrite.api.service;


import com.datmt.hack.appwrite.api.model.CreateRequest;
import com.datmt.hack.appwrite.api.model.CreateResponse;
import com.datmt.hack.appwrite.api.model.UnearthResponse;
import com.google.gson.Gson;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.nio.charset.Charset;
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


    private String getDBQueryUrl(String dbId) {
        return appwriteURL + "database/collections/" + dbId + "/documents";
    }

    public String dig(HttpServletRequest request, String dbId) throws IOException {
        Map<String, String> headers = getRequestHeaders(request);

        Request.Builder builder = new Request.Builder()
                .url(getDBQueryUrl(dbId) + "?" + request.getQueryString());

        headers.entrySet().forEach(t -> {
            builder.addHeader(t.getKey(), t.getValue());
        });

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(builder.build());
        Response response = call.execute();

        return response.peekBody(Long.MAX_VALUE).string();

//        Object o = Unirest
//                .get(getDBQueryUrl(dbId) + "?" + request.getQueryString())
//                .headers(headers)
//                .asObject(Object.class);
////http://localhost:8080/v1/database/collections/62754731c4635af92550/documents?queries%5B0%5D=code.equal%28376004%29
////http://localhost:8818/v1/database/collections/62754731c4635af92550/documents?queries%5B0%5D=code.equal%28376004%29
////http://localhost:8818/v1/database/collections/62754731c4635af92550/documents?queries%5B0%5D=code.equal%28747797%29
//
//        return null;

    }

    public CreateResponse createDocument(HttpServletRequest request, String dbId) throws UnirestException, IOException, NoSuchFieldException, IllegalAccessException {
        String payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Map<String, String> headers = getRequestHeaders(request);
        //http://localhost:8818/v1/database/collections/62754731c4635af92550/documents
        //http://localhost:8818/v1/collections/62754731c4635af92550/documents
        int code = new Random().nextInt(999999 - 100000) + 100000;


        payload = payload.replace("__CODE_PLACEHOLDER__", String.valueOf(code));
        Object o = Unirest
                .post(getDBQueryUrl(dbId))

                .headers(headers)
                .body(payload)
                .asObject(Object.class);

        Field f = o.getClass().getSuperclass().getDeclaredField("statusCode");
        f.setAccessible(true);
        Integer statusCode = (Integer) f.get(o);

        if (statusCode == 201) {
            return new CreateResponse(String.valueOf(code));
        } else {
            return new CreateResponse(String.valueOf(-1));
        }
    }

    private Map<String, String> getRequestHeaders(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();

        var requestHeaders = request.getHeaderNames();
        while (requestHeaders.hasMoreElements()) {
            String name1 = requestHeaders.nextElement();
            if (
                    name1.toLowerCase(Locale.ROOT).contains("content-length") ||
                            name1.toLowerCase(Locale.ROOT).contains("accept-encoding")

            )
                continue;

            headers.put(name1, request.getHeader(name1));
        }
        return headers;
    }


}
