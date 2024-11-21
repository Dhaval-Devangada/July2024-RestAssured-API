package GETAPITestsWithBDD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ContactsAPIsTest {

    @BeforeMethod
    public void setUp(){
        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
    }

    @Test
    public void getContactsAPITest() {

        //RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        given().log().all()
                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NmQ3NjM0M2VkZmQ5YzAwMTNmNzE0NDgiLCJpYXQiOjE3Mjc1OTUxNjB9.yJG78xh_KEeixUzdWjS8fqLUrUEmGcrY-9rNj8RJ4xU")
                .when().log().all()
                .get("/contacts")
                .then().log().all()
                .assertThat()
                .statusCode(200).and()
                .statusLine("HTTP/1.1 200 OK").and()
                .contentType(ContentType.JSON).and()//enum is collection of multiple constants
                .body("$.size()", equalTo(1));
    }

    @Test
    public void getContactsAPITest_WithInvalidToken() {

        //RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        given().log().all()
                .header("Authorization", "Bearer 0-4")
                .when().log().all()
                .get("/contacts")
                .then().log().all()
                .assertThat()
                .statusCode(401);
    }
}

// Always use "and()" to separate the assertion so that it is more readable
// Enum is collection of multiple constants
// Feature file - Behaviour of the application
// Product manager has no idea about backend - Auth token , JWT token , base URL,
//Product manager has idea that how user is using the web or mobile application
// If we have api and ui together then don't mentioned anything technical in the feature file