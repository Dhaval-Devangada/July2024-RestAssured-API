package POSTAPIWithCreateUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class CreateUserAPITest {

    @Test
    public void getAuthTokenTest_WithJSONFile(){

        RestAssured.baseURI= "https://gorest.co.in/";

        Integer userId = given().log().all()
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/jsons/user.json"))
                .when().log().all()
                .post("/public/v2/users")
                .then().log().all()
                .assertThat()
                .statusCode(201)
                .extract()
                .path("id");

        System.out.println("user id is ==>" + userId);
        Assert.assertNotNull(userId);

    }

}
