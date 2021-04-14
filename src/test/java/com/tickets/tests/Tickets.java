package com.tickets.tests;

import com.google.gson.JsonObject;
import com.tickets.common.BeforeAfterTest;


import com.tickets.common.DataProperties;
import com.tickets.common.EnvironmentProperties;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import net.minidev.json.JSONArray;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.junit.runner.Request;
import org.testng.Assert;
import org.testng.annotations.Test;

import javax.xml.crypto.Data;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.patch;
import static org.testng.Assert.assertEquals;


public class Tickets extends BeforeAfterTest {

// Just a simple Get Request to validate 200 response and get User



    @Test
    public void getAuth() {

       Response response= given().auth().preemptive().basic(DataProperties.getUserName(), DataProperties.getPassword())
                .when()
                .get(getBaseUrl()+EnvironmentProperties.getAuthenticateUrl())
                .then().statusCode(200).extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
       final String name = jsonPathEvaluator.get("users[0].name");


        Assert.assertEquals(name, "Vanja Becirovic");


    }

    // A request to create a new ticket and add a comment, validating the comment is saved under the body
    @Test
    public void createTicket() {


        JsonObject payload = new JsonObject();
        JsonObject payload2 = new JsonObject();
        JsonObject payload3 = new JsonObject();

        payload.add("ticket", payload2);

        payload2.addProperty("subject", " sunbject1");
        payload2.add("comment", payload3);

         payload3.addProperty("body","This is my comment");


    Response response =    given().auth().preemptive().basic(DataProperties.getUserName(), DataProperties.getPassword())
               .header("Content-type","application/JSON")
                .body(payload.toString())
                .when()
                .post(getBaseUrl()+EnvironmentProperties.getTicketsEndPoint())
                .then().statusCode(201).extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        final String body = jsonPathEvaluator.get("audit.events[0].body");


        Assert.assertEquals(body, "This is my comment");


    }


// Request to Verify I can get all tickets
    @Test
    public void getTicketList() {

       given().auth().preemptive().basic(DataProperties.getUserName(), DataProperties.getPassword())
                .when()
                .get(getBaseUrl()+EnvironmentProperties.getTicketsEndPoint())
                .then().statusCode(200);

    }


    @Test
    public void deleteTicket() {


// Create a ticket and get the ID
        JsonObject payload = new JsonObject();
        JsonObject payload2 = new JsonObject();
        JsonObject payload3 = new JsonObject();

        payload.add("ticket", payload2);

        payload2.addProperty("subject", " sunbject1");
        payload2.add("comment", payload3);

        payload3.addProperty("body","This is my comment");


        Response response =    given().auth().preemptive().basic(DataProperties.getUserName(), DataProperties.getPassword())
                .header("Content-type","application/JSON")
                .body(payload.toString())
                .when()
                .post(getBaseUrl()+EnvironmentProperties.getTicketsEndPoint())
                .then().statusCode(201).extract().response();

        JsonPath jsonPathEvaluator = response.jsonPath();
        final int id = jsonPathEvaluator.get("ticket.id");

//Delete ticket using the id above

       given().auth().preemptive().basic(DataProperties.getUserName(), DataProperties.getPassword())
                .when()
                .delete(getBaseUrl()+ "api/v2/tickets/"+id)
                .then().statusCode(204);

       //Try to get the ticket again and it should get a 404
        given().auth().preemptive().basic(DataProperties.getUserName(), DataProperties.getPassword())
                .when()
                .get(getBaseUrl()+ "api/v2/tickets/"+id)
                .then().statusCode(404);


    }




    }


