package com.news.tests;

import com.news.common.BeforeAfterTest;


import com.news.common.DataProperties;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;


public class News extends BeforeAfterTest {

// Just a simple Get Request to validate 200 response
    @Test
    public void getNews() {

        given()
                .header(DataProperties.getApplicationIdHeader(), DataProperties.getApplicationID())
                .header(DataProperties.getApplicationKeyHeader(), DataProperties.getApplicationKey())
                .when()
                .get(getBaseUrl())
                .then().statusCode(200);


    }

    // A request to get fresh stories which have been published in the last 12 hours
    @Test
    public void getFreshStories() {

        given()
                .header(DataProperties.getApplicationIdHeader(), DataProperties.getApplicationID())
                .header(DataProperties.getApplicationKeyHeader(), DataProperties.getApplicationKey())
                .queryParam("published_at.start", "NOW-1DAY")
                .queryParam("published_at.end", "NOW")
                .when()
                .get(getBaseUrl())
                .then().statusCode(200);


    }


// Request to Verify that there is a set Rate Limit of 60 and the time period.
    @Test
    public void getRateLimit() {

       given()
                .header(DataProperties.getApplicationIdHeader(), DataProperties.getApplicationID())
                .header(DataProperties.getApplicationKeyHeader(), DataProperties.getApplicationKey())
                .when()
                .get(getBaseUrl())
                .then().statusCode(200)
                .header("x-ratelimit-hit-limit","60")
                .header("x-ratelimit-hit-period","minute");

    }

    // A request to do a Keyword search and validate the Keyword is contained in the response.
    @Test
    public void getKeyWords() {

        Response response = given()
                .header(DataProperties.getApplicationIdHeader(), DataProperties.getApplicationID())
                .header(DataProperties.getApplicationKeyHeader(), DataProperties.getApplicationKey())
                .queryParam("body", "Zurich")
                .when()
                .get(getBaseUrl())
                .then().statusCode(200).extract().response();

        String jsonString = response.getBody().asString();
        assertEquals(jsonString.contains("Zurich"), true);




    }

}
