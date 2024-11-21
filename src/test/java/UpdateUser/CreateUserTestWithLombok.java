package UpdateUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

// Lombok is helping us to minimize the code
// No need to create unnecessary getter()/setter()/constructors

public class CreateUserTestWithLombok {

    //1. create a user: POST : fetch the user id
    //2. GET
    //3. Update a user: PUT/user id (Append userid)

    public String getRandomEmailId() {
        return "apiautomation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void updateUserWith_Lombok() {
        RestAssured.baseURI = "https://gorest.co.in";

       // User user = new User("dhaval", getRandomEmailId(), "male", "active");

        UserLombok userLombok = new UserLombok("dhaval", getRandomEmailId(), "male", "active");

        //1. post: create a user:
        Response postResponse = given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .body(userLombok)
                .when().log().all()
                .post("/public/v2/users");

        postResponse.prettyPrint();

        Integer userId = postResponse.jsonPath().get("id");
        System.out.println("user id ===> " + userId);
    }


    // selenium also internally using lombok to create builder
    // Below we are creating the object in a fluentway / method chain way
    @Test
    public void updateUserWith_Lombok_With_Builder() { // popular method for creating object with the lombok
        RestAssured.baseURI = "https://gorest.co.in";

        //creating user class object using lombok builder pattern:
        UserLombok userLombokBuilder=new UserLombok.UserLombokBuilder()
                .name("Dhaval")
                .email(getRandomEmailId())
                .gender("male")
                .status("active")
                .build(); // so this "build()" is helping us to create the object of "userLombok" class

        //1. post: create a user:
        Response postResponse = given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .body(userLombokBuilder)
                .when().log().all()
                .post("/public/v2/users");

        postResponse.prettyPrint();

        Integer userId = postResponse.jsonPath().get("id");
        System.out.println("user id ===> " + userId);
    }
}
