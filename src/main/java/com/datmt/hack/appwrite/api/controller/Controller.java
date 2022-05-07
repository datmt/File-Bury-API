package com.datmt.hack.appwrite.api.controller;

import com.datmt.hack.appwrite.api.model.CreateResponse;
import com.datmt.hack.appwrite.api.model.UnearthDetails;
import com.datmt.hack.appwrite.api.service.AppwriteService;
import kong.unirest.UnirestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1")
public class Controller {

    @Autowired
    AppwriteService appwriteService;

    @GetMapping("/hello")
    public String hello() {
        return "Great";
    }

    @PostMapping("/account/sessions/anonymous")
    public String createSession(){
        return "nice";
    }

    @PostMapping("/database/collections/{dbId}/documents")
    public CreateResponse bury(@PathVariable(name = "dbId") String dbId, HttpServletRequest request) throws IOException, UnirestException {

        return appwriteService.createDocument(request, dbId);



    }


    @GetMapping("/database/collections/{dbId}/documents")
    public ResponseEntity<UnearthDetails> dig( @PathVariable String dbId, @RequestParam String queries) {
        return ResponseEntity.ok().body(null);
    }
}
