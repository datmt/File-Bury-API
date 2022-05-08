package com.datmt.hack.appwrite.api.controller;

import com.datmt.hack.appwrite.api.model.CreateResponse;
import com.datmt.hack.appwrite.api.model.UnearthResponse;
import com.datmt.hack.appwrite.api.service.AppwriteService;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.net.URLDecoder;
import java.nio.charset.Charset;

@RestController
@RequestMapping("/v1")
public class Controller {

    @Autowired
    AppwriteService appwriteService;

    @Value("${appwrite.url}")
    String appwriteURL;

    @GetMapping("/hello")
    public String hello() {
        return "Great";
    }

    @PostMapping("/account/sessions/anonymous")
    public String createSession( HttpServletRequest request) throws IOException {
        return appwriteService.forwardRequest( request, appwriteURL + "/account/sessions/anonymous", "POST");
    }

    @GetMapping("/account")
    public String getAccount( HttpServletRequest request) throws IOException {
        return appwriteService.forwardRequest( request, appwriteURL + "/account", "GET");

    }

    @GetMapping("/account/sessions")
    public String getSessions( HttpServletRequest request) throws IOException {
        return appwriteService.forwardRequest( request, appwriteURL + "/account/sessions", "GET");

    }

    @PostMapping("/database/collections/{dbId}/documents")
    public CreateResponse bury(@PathVariable(name = "dbId") String dbId, HttpServletRequest request) throws IOException, UnirestException, NoSuchFieldException, IllegalAccessException {

        return appwriteService.createDocument(request, dbId);
    }


    @GetMapping("/database/collections/{dbId}/documents")

    public String dig(@PathVariable(name = "dbId") String dbId, HttpServletRequest request) throws IOException {
         if (URLDecoder.decode(request.getQueryString(), Charset.defaultCharset()).length() != 29) {
             //the length is always 29 because the number is always six nubmers
             throw new RuntimeException("Bad request");
         }


         return appwriteService.dig(request, dbId);
    }
}
