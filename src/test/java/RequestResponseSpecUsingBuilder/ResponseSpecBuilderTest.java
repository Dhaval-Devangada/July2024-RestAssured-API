package RequestResponseSpecUsingBuilder;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ResponseSpecBuilderTest {
    public static ResponseSpecification get_res_spec_200OK(){
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(200)
                .expectHeader("Server", "cloudflare")
                .build();
        return responseSpec;
    }

    public static ResponseSpecification get_res_spec_401_AuthFail(){
        ResponseSpecification responseSpec = new ResponseSpecBuilder()
                .expectContentType(ContentType.JSON)
                .expectStatusCode(401)
                .expectHeader("Server", "cloudflare")
                .build();
        return responseSpec;
    }

    @Test
    public void getUsersTest(){
        RestAssured.baseURI="https://gorest.co.in";
        given()
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .when()
                .get("/public/v2/users")
                .then()
                .assertThat()
                .spec(get_res_spec_200OK());
    }

    @Test
    public void getUsers_WithInvalidToken_Test(){
        RestAssured.baseURI="https://gorest.co.in";
        given()
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009dhaval")
                .when()
                .get("/public/v2/users")
                .then()
                .assertThat()
                .spec(get_res_spec_401_AuthFail());
    }
}
//we can create different types of request and response specifications methods/utilities