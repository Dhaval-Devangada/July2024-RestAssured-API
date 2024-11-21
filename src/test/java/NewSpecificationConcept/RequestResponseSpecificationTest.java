package NewSpecificationConcept;

import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;


public class RequestResponseSpecificationTest {
    RequestSpecification requestSpecification;
    ResponseSpecification responseSpecification;

    @BeforeTest
    public void setUp(){
        //define request spec(this is our initial requirement for the request)

        requestSpecification = given().log().all()
                .baseUri("https://jsonplaceholder.typicode.com")
                .header("Content-Type","application/json");

        //define response spec(expectation from the response)
        responseSpecification = expect()
                .statusCode(anyOf(equalTo(200),equalTo(201)))
                .header("Content-Type", "application/json; charset=utf-8")
                .header("Server","cloudflare")
                .time(lessThan(2000L));
    }

    //Request spec and responseSpec is done now we  just need to focus on our testcases
    @Test
    public void checkGetTest(){
        requestSpecification
                .when()
                .get("/posts/1")
                .then().log().all()
                .spec(responseSpecification)
                .body("id",equalTo(1));
    }

    @Test
    public void checkGetWithQueryParamTest(){
        requestSpecification
                .queryParam("userId",1)
                .when()
                .get("/posts/1")
                .then().log().all()
                .spec(responseSpecification)
                .body("id",equalTo(1));
    }

    @Test
    public void checkPostTest(){
        requestSpecification
                .body("{\n" +
                "    \"title\": \"foo\",\n" +
                "    \"body\": \"bar\",\n" +
                "    \"userId\": 1\n" +
                "}")
                .when()
                .post("/posts")
                .then().log().all()
                .spec(responseSpecification)
                .body("id",equalTo(101))
                .body("body",equalTo("bar"))
                .body("title",equalTo("foo"));
    }
}
//"post" will give you the response not "responseSpecification"
// "given()/when()/then()" will give you the responseSpecification & not the post call get call or delete call