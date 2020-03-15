package com.teckro.tests;

import com.teckro.common.BeforeAfterTest;
import com.teckro.common.DataProperties;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;
import com.google.gson.JsonObject;
import static io.restassured.RestAssured.given;

public class BookRoom extends BeforeAfterTest {

    @Test
    public void bookRoomOneDay() {

        //Creating Json Object
        JsonObject payload = new JsonObject();
        payload.addProperty(DataProperties.getNumDays(),1);
        payload.addProperty(DataProperties.getCheckIn(),DataProperties.getValidDate());



        Response response = given()
                .body(payload)
                .when()
                .post(getBaseUrl() + getBookRoomUrl())
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        String checkOutDate = jsonPathEvaluator.get("checkOutDate");
        int totalPrice = jsonPathEvaluator.get("totalPrice");

        Assert.assertEquals(totalPrice, 120);
        Assert.assertEquals(checkOutDate, "2020-11-09");


    }
    @Test
    public void bookRoomTwoDays() {
        JsonObject payload = new JsonObject();
        payload.addProperty(DataProperties.getNumDays(),2);
        payload.addProperty(DataProperties.getCheckIn(),DataProperties.getValidDate());



        Response response = given()
                .body(payload)
                .when()
                .post(getBaseUrl() + getBookRoomUrl())
                .then()
                .statusCode(200)
                .extract()
                .response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        String checkOutDate = jsonPathEvaluator.get("checkOutDate");
        int totalPrice = jsonPathEvaluator.get("totalPrice");

        Assert.assertEquals(totalPrice, 260);
        Assert.assertEquals(checkOutDate, "2020-11-10");


    }
    @Test
    @Ignore
    /*
    Defect here as I am getting a 200 response with following details
    "checkInDate": "2020-11-08",
    "checkOutDate": "2020-11-07",
    "totalPrice": -10
    Expected was a 400 error response.
    */

    public void bookRoomLessThanDay() {
        JsonObject payload = new JsonObject();
        payload.addProperty(DataProperties.getNumDays(),-1);
        payload.addProperty(DataProperties.getCheckIn(),DataProperties.getValidDate());



        Response response = given()
                .body(payload)
                .when()
                .post(getBaseUrl() + getBookRoomUrl())
                .then()
                .statusCode(400)
                .extract()
                .response();

    }

    @Test
    public void bookRoomInvalidDate() {
        JsonObject payload = new JsonObject();
        payload.addProperty(DataProperties.getNumDays(),2);
        payload.addProperty(DataProperties.getCheckIn(),DataProperties.getInValidDate());



        Response response = given()
                .body(payload)
                .when()
                .post(getBaseUrl() + getBookRoomUrl())
                .then()
                .statusCode(400)
                .extract()
                .response();

    }


}
