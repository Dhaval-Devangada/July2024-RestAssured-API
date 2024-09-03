package GETAPITestsWithBDD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ContactsAPIsTest {

    @Test
    public void getContactsAPITest() {

        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com/";

        given().log().all()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NmQ3NjM0M2VkZmQ5YzAwMTNmNzE0NDgiLCJpYXQiOjE3MjUzOTE2ODN9.eEFlA8sgJtvJZ-406hJgpC758TitPhG2LR7Ah1eCS-4")
                .when().log().all()
                .get("contacts")
                .then().log().all()
                .assertThat()
                .statusCode(200).and()
                .statusLine("HTTP/1.1 200 OK").and()
                .contentType(ContentType.JSON).and()//enum is collection of multiple constants
                .body("$.size", equalTo(0));

    }

    @Test
    public void getContactsAPITest_WithInvalidToken() {

        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com/";

        given().log().all()
                .header("Authorization", "Bearer 0-4")
                .when().log().all()
                .get("contacts")
                .then().log().all()
                .assertThat()
                .statusCode(401);

    }


}
