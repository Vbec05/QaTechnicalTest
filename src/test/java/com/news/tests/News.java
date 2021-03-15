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


    @Test
    public void getNews() {

        given()
                .header(DataProperties.getApplicationIdHeader(), DataProperties.getApplicationID())
                .header(DataProperties.getApplicationKeyHeader(), DataProperties.getApplicationKey())
                .when()
                .get(getBaseUrl())
                .then().statusCode(200);


    }

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

    @Test
    public void getStoriesVolume() {

        Response response=  given()
                .header(DataProperties.getApplicationIdHeader(), DataProperties.getApplicationID())
                .header(DataProperties.getApplicationKeyHeader(), DataProperties.getApplicationKey())
                .queryParam("published_at.start", "NOW-1DAY")
                .queryParam("published_at.end", "NOW")
                .when()
                .get(getBaseUrl())
                .then().statusCode(400).extract().response();



        Response response1= given()
                .header(DataProperties.getApplicationIdHeader(), DataProperties.getApplicationID())
                .header(DataProperties.getApplicationKeyHeader(), DataProperties.getApplicationKey())
                .queryParam("published_at.start", "NOW-3DAY")
                .queryParam("published_at.end", "NOW-1DAY")
                .when()
                .get(getBaseUrl())
                .then().statusCode(200).extract().response();


    }
}
