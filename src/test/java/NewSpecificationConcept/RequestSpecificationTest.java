package NewSpecificationConcept;

import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.*;

/**
 * Specification means we can create our own request, and we can use it whenever its required
 */
public class RequestSpecificationTest {

    @Test
    public void reqSpecTest(){
        RequestSpecification requestSpecification = given().log().all()
                .baseUri("https://jsonplaceholder.typicode.com");

        requestSpecification
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200);

        requestSpecification
                .when()
                .body("{\n" +
                        "    \"title\": \"foo\",\n" +
                        "    \"body\": \"bar\",\n" +
                        "    \"userId\": 1\n" +
                        "}")
                .when()
                .post("/posts")
                .then()
                .statusCode(201);
    }

    @Test
    public void getUserTest(){
        RequestSpecification requestSpecification = given().log().all()
                .baseUri("https://gorest.co.in")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009");

        requestSpecification
                .when()
                .get("public/v2/users")
                .then()
                .statusCode(200);

        requestSpecification
                .when()
                .get("public/v2/users/7482885")
                .then()
                .statusCode(200);
    }
    @Test
    public void getUserTest_QueryParam(){
        RequestSpecification requestSpecification = given().log().all()
                .baseUri("https://gorest.co.in")
                .header("Content-Type","application/json")
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                        .queryParam("name","Naveen");

        requestSpecification
                .when().log().all()
                .get("public/v2/users")
                .then().log().all()
                .statusCode(200);

    }
}
