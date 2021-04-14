package com.tickets.common;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.BeforeTest;

import static io.restassured.RestAssured.given;

public class BeforeAfterTest extends EnvironmentProperties {

    @BeforeTest
    public static void setup() {
        RestAssured.baseURI = EnvironmentProperties.getBaseUrl();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

     //   getauthToken();

    }

 /*   public static void getauthToken() {

        Response response = given()
                .header("Content-type", "application/json")
                .header("Authorization", "dmFuamFiZWNpcm92aWM4NUBnbWFpbC5jb20vdG9rZW46Rzc0cUIxQkVuempYVHZXeDhCYVhoTHlsb3NObkVHbDdDcGdaczJvSA==")
                .when()
                .post(EnvironmentProperties.getBaseUrl() + EnvironmentProperties.getAuthenticateUrl())
                .then().statusCode(200).extract().response();


    }*/
}
