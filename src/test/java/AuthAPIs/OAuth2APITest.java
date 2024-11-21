package AuthAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class OAuth2APITest {

    private String accessToken;

    @BeforeMethod
    public void getAccessToken(){
        RestAssured.baseURI="https://test.api.amadeus.com/v1/security/oauth2/token";

        Response response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "oJxYGswZKjSAAXtAPnM1tqIb5LNuNjVk")
                .formParam("client_secret", "OcX46xKVweU7mH3X")
                .when()
                .post();

        Assert.assertEquals(response.getStatusCode(),200);
        response.prettyPrint();
        accessToken=response.jsonPath().getString("access_token");

    }

    @Test
    public void getFlightDetailsTest(){

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200");

        Assert.assertEquals(response.getStatusCode(),200);
        response.prettyPrint();
    }

    //Bearer is required when we are supplying with header otherwise with "auth().oauth()2 it is not required"
    @Test
    public void getFlightDetailsTest_2(){

        Response response = RestAssured.given()
                .auth().oauth2(accessToken)
                .when()
                .get("https://test.api.amadeus.com/v1/shopping/flight-destinations?origin=PAR&maxPrice=200");

        Assert.assertEquals(response.getStatusCode(),200);
        response.prettyPrint();
    }


}
