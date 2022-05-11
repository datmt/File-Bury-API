package com.datmt.hack.appwrite.api.service;

import kong.unirest.Unirest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CronDeleteTest {


    @Test
    public void testGetCollections() {
        int minutes = 200;
        long now = System.currentTimeMillis();

        long timeInPast = now - (long) minutes * 1000 * 60;

        String url = "http://dev-exl2.ddns.net:40003/v1/database/collections/62769ebbc91be4be9759/documents";;

        String apiKey = "896d7d69a70ebd5fc61403212dc8e60c6a5319de0855437cc366003e167125857bfac505f9bd25566deef6172add5b31b075651db6a19f242ca5c0438e5867ec2910cb46c416a64c4f3cfbb47db000698a8300ae56fb2ef0b138785b0232d3420fcea90541966aa8c22cba92f1a8d1637f3e941b90a357104014c7611ad7555c";
        //'[0]=timestamp.lesser(11212)'
        Unirest.get(url + "?queries[0]=timestamp.lesser("+timeInPast+")")
                .header("x-appwrite-key", apiKey)
                .header("x-appwrite-project", "62769eaceff5eede2899")
                //x-appwrite-project: 62769eaceff5eede2899
                .asJson();
    }
}