package com.datmt.hack.appwrite.api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CreateResponse {

    private String code;

    /*

    {code: 762786, text: "adsfasdfasdfsadfsdf", $collection: "62754731c4635af92550",…}
$collection: "62754731c4635af92550"
$id: "627567107d49611a152e"
$read: ["user:62754da576b3aa0763cc"]
$write: ["user:62754da576b3aa0763cc"]
code: 762786
text: "adsfasdfasdfsadfsdf"
     */
}
