package NewSpecificationConcept;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class RequestSpecReuseTest {
    RequestSpecification requestSpecification;
    @BeforeTest
    public void setUp(){
         requestSpecification = given().log().all()
                .baseUri("https://gorest.co.in")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009");
    }

    @Test
    public void getUserTest(){
        requestSpecification
                .when().log().all()
                .get("public/v2/users")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void getAUserTest(){
        requestSpecification
                .when().log().all()
                .get("public/v2/users/7484342")
                .then().log().all()
                .statusCode(200);
    }

    @Test
    public void getWrongUserTest(){
        requestSpecification
                .when().log().all()
                .get("public/v2/users/1")
                .then().log().all()
                .statusCode(404);
    }
}
