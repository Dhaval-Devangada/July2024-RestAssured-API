package RequestResponseSpecUsingBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
//Testng methods cannot be static
public class RequestResponseSpecTest {

    public static RequestSpecification oAuth2ReqSpec(){
        RequestSpecification requestOAuth2Spec = new RequestSpecBuilder()
                .setBaseUri("https://test.api.amadeus.com")
                .setContentType(ContentType.URLENC)
                .addFormParam("grant_type", "client_credentials")
                .addFormParam("client_id", "oJxYGswZKjSAAXtAPnM1tqIb5LNuNjVk")
                .addFormParam("client_secret", "OcX46xKVweU7mH3X")
                .build();

        return requestOAuth2Spec;
    }

    static String accessToken;
    @BeforeMethod
    public void getAccessToken(){
        Response response = given()
                .spec(oAuth2ReqSpec())
                .when()
                .post("/v1/security/oauth2/token");

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

    public static RequestSpecification user_req_spec() {
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .build();
        return requestSpec;
    }

    public static ResponseSpecification get_res_spec_200OK() {
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectHeader("Server", "cloudflare")
                .build();
        return responseSpec;
    }

    @Test
    public void getUserTest() {
        given()
                .spec(user_req_spec())
                .when()
                .get("/public/v2/users")
                .then()
                .assertThat()
                .spec(get_res_spec_200OK());


    }

}
