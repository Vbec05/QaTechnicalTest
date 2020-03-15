package com.teckro.common;

import io.restassured.RestAssured;
import org.testng.annotations.BeforeTest;

public class BeforeAfterTest extends EnvironmentProperties{

    @BeforeTest
    public static void setup() {
        RestAssured.baseURI = EnvironmentProperties.getBaseUrl();

        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();




    }



}
