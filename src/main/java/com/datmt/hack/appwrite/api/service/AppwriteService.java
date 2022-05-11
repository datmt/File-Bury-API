package com.datmt.hack.appwrite.api.service;


import com.datmt.hack.appwrite.api.model.CreateResponse;
import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
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


    }

    public CreateResponse createDocument(HttpServletRequest request, String dbId) throws UnirestException, IOException, NoSuchFieldException, IllegalAccessException {
        String payload = request.getReader().lines().collect(Collectors.joining(System.lineSeparator()));

        Map<String, String> headers = getRequestHeaders(request);
        //http://localhost:8818/v1/database/collections/62754731c4635af92550/documents
        //http://localhost:8818/v1/collections/62754731c4635af92550/documents
        int code = new Random().nextInt(999999 - 100000) + 100000;


        payload = payload.replace("__CODE_PLACEHOLDER__", String.valueOf(code))
                .replace("__TS_PLACEHOLDER__", String.valueOf(System.currentTimeMillis()));

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

    public String forwardRequest(HttpServletRequest request, String url, String httpMethod) throws IOException {
        Map<String, String> headers = getRequestHeaders(request);

        Request.Builder builder = new Request.Builder()
                .url(url);

        headers.entrySet().forEach(t -> {
            builder.addHeader(t.getKey(), t.getValue());
        });


        if (httpMethod.equalsIgnoreCase("POST")) {
            StringBuilder buffer = new StringBuilder();
            BufferedReader reader = request.getReader();
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append(System.lineSeparator());
            }
            String data = buffer.toString();

            builder.post(RequestBody.create(MediaType.parse("application/json"),data));

        }

        headers.forEach(builder::addHeader);

        OkHttpClient client = new OkHttpClient();
        Call call = client.newCall(builder.build());
        try (Response response = call.execute()) {

            return response.peekBody(Long.MAX_VALUE).string();
        }
    }


}
