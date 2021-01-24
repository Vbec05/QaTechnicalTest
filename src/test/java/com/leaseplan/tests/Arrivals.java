package com.leaseplan.tests;

import com.leaseplan.common.BeforeAfterTest;
import com.leaseplan.common.DataProperties;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.serenitybdd.junit.runners.SerenityRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.testng.Assert;

import static io.restassured.RestAssured.given;

@RunWith(SerenityRunner.class)
public class Arrivals extends BeforeAfterTest {

    private Response response;
    private ValidatableResponse json;
    private RequestSpecification request;

    @org.junit.Test
    @Given("I want to find arrivals in Berlin")
    public void allArrivalsBerlin() {

        request = given()
                .queryParam("date", DataProperties.getValidDate());


    }

    @When("When the date is valid")
    public void sendValidDate() {

        response = request.when()
                .get(getBaseUrl() + getArrivalsUrl());


    }

    @Then("The status code should be 200")
    public void getStatusCode() {
        json = response.then().statusCode(200);


    }

    @And("Response Contains")
    public void responseContains() {


        JsonPath jsonPathEvaluator = response.jsonPath();
        String name = jsonPathEvaluator.get("name").toString();
        String stopId = jsonPathEvaluator.get("stopId").toString();
        Assert.assertTrue(name.contains("IC 2251"));
        Assert.assertTrue(stopId.contains("8010255"));


    }


    @Test
    @Given("I want to find arrivals in Berlin")
    public void allArrivalsBerlinInvalidDate() {

        request = given()
                .queryParam("date", DataProperties.getInValidDate());
    }

    @When("When the date is invalid")
    public void sendInValidDate() {

        response = request.when()
                .get(getBaseUrl() + getArrivalsUrl());

    }

    @Then("The status code should be 400")
    public void getErrorStatusCode() {
        json = response.then().statusCode(400);


    }


}
