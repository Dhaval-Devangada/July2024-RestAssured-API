package RequestResponseSpecUsingBuilder;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecBuilderTest {

    //RequestSpecBuilder() will help us to create the request specification
    //RestAssured guys have already created builder for the request specification
    public static RequestSpecification user_req_spec(){
        RequestSpecification requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://gorest.co.in")
                .setContentType(ContentType.JSON)
                .addHeader("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .build();
        return requestSpec;
    }

    @Test
    public void getUser_WithReq_Spec(){
        given()
                .spec(user_req_spec())
                .when()
                .get("/public/v2/users")
                .then()
                .statusCode(200);
    }

    @Test
    public void getUser_WithReq_Spec_QueryParam(){
        given().log().all()
                .queryParam("name","naveen")
                .queryParam("status","active")
                .spec(user_req_spec())
                .when().log().all()
                .get("/public/v2/users")
                .then()
                .statusCode(200);
    }
}
