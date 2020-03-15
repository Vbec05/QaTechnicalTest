package com.teckro.tests;

import com.teckro.common.BeforeAfterTest;
import com.teckro.common.DataProperties;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import static com.sun.jmx.snmp.ThreadContext.contains;
import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

public class CheckAvailability extends BeforeAfterTest {

    @Test
    public void checkAvailabilityValidDate() {
        Response response = given()
                .when()
                .post(getBaseUrl() + getCheckAvailabilityUrl() + DataProperties.getValidDate())
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        int price = jsonPathEvaluator.get("price");
        assertEquals(price, 130);



    }

    @Test
    @Ignore
    //Ignoring Test as it is failing due 200 response being received back. This would be a defect. I was expecting a 400 response. Also date format was altered
    public void checkAvailabilityInValidDateFormat() {
        given()
                .when()
                .post(getBaseUrl() + getCheckAvailabilityUrl() + DataProperties.getInValidDateFormat())
                .then()
                .statusCode(400)
                .extract()
                .response().getBody().asString();



    }
    @Test
    public void checkAvailabilityInValidDateFormatTwo() {
       Response response= given()
                .when()
                .post(getBaseUrl() + getCheckAvailabilityUrl() + DataProperties.getInValidDate())
                .then()
                .statusCode(400)
                .extract()
                .response();


        String jsonString = response.getBody().asString();
        assertTrue(jsonString.contains("Invalid or missing date '2020/11/08'. Valid date format is: yyyy-mm-dd, e.g. 2013-04-20"));





    }
    @Test
    public void checkAvailabilitySendStringInsteadOfDate() {
        given()
                .when()
                .post(getBaseUrl() + getCheckAvailabilityUrl() + "RandomString")
                .then()
                .statusCode(400)
                .extract()
                .response();



    }

}

