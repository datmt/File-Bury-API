package com.datmt.hack.appwrite.api.service;

import com.datmt.hack.appwrite.api.model.Records;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.context.annotation.ApplicationScope;

import java.util.List;

@ApplicationScope
public class CronDelete {
    @Value("${appwrite.url}")
    String appwriteURL;
    @Value("${appwrite.collection_id}")
    String collectionID;
    @Value("${appwrite.project_id}")
    String projectId;

    @Value("${appwrite.bucket_id}")
    String bucketId;

    @Value("${appwrite.api_key}")
    String apiKey;

//    @Scheduled(cron = "* */1 * * * *")
//    public void scheduleTaskUsingCronExpression() {
//
//
//    }


//x-appwrite-key
    private void deleteText() {

    }

    private void deleteFile(String fileId) {

    }

    private void deleteRecord(String record) {

    }
//
//    private List<Records> getRecords(int minutes) {
//
//        long now = System.currentTimeMillis();
//
//        long timeInPast = now - (long) minutes * 1000 * 60;
//
//        String url = appwriteURL + "database/collections/" + collectionID + "/documents";;
//
//        //'[0]=timestamp.lesser(11212)'
//        return Unirest.get(url + "?queries[0]=timestamp.lesser("+timeInPast+")")
//                .header("x-appwrite-key", apiKey)
//                .asJson();
//    }
}
