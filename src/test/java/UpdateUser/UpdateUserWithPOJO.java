package UpdateUser;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class UpdateUserWithPOJO {

    //1. create a user: POST : fetch the user id
    //2. GET
    //3. Update a user: PUT/user id (Append userid)

    public String getRandomEmailId(){
        return "apiautomation" + System.currentTimeMillis()+"@gmail.com";
    }

    @Test
    public void updateUserWith_POJO(){
        RestAssured.baseURI="https://gorest.co.in";

        User user = new User("dhaval",getRandomEmailId(),"male","active");

        //1. post: create a user:
        Response postResponse = given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .body(user)
                .when().log().all()
                .post("/public/v2/users");

        postResponse.prettyPrint();

        Integer userId = postResponse.jsonPath().get("id");
        System.out.println("user id ===> "+userId);

        System.out.println("======================================");

        //2 . update : PUT : update a user
       user.setName("Dhaval Devangada");
       user.setStatus("inactive");
        given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .body(user)
                .when().log().all()
                .put("/public/v2/users/" + userId)
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("id",equalTo(userId))
                .and()
                .body("name",equalTo(user.getName()))
                .and()
                .body("status",equalTo(user.getStatus()));

    }
}

//jackson-databind will automatically will convert from object to json
// or jackson will automatically do the serilazation
