package NewSpecificationConcept;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ResponseSpecificationTest {

    @Test
    public void reqSpecTest(){
        ResponseSpecification responseSpec = expect()
                .statusCode(200)
                .header("Content-Type", "application/json; charset=utf-8")
                .body("userId", equalTo(1));

        given()
                .baseUri("https://jsonplaceholder.typicode.com")
                .when()
                .get("/posts/1")
                .then()
                .spec(responseSpec);
    }
}
