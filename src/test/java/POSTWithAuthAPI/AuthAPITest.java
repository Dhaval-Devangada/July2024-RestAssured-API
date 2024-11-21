package POSTWithAuthAPI;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.Assert;
import org.testng.annotations.Test;
import pojo.Credentials;

import java.io.File;

import static io.restassured.RestAssured.given;

public class AuthAPITest {

    @Test
    public void getAuthTokenTest_WithJSON(){

        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        String tokenId = given()
                .contentType(ContentType.JSON)
                .body("{\n" +
                        "    \"username\" : \"admin\",\n" +
                        "    \"password\" : \"password123\"\n" +
                        "}")
                .when()
                .post("/auth")
                .then()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("token");

        System.out.println("tokenId ==>" + tokenId);
        Assert.assertNotNull(tokenId);

    }

    // Below approach can be used when we have fix json
    @Test
    public void getAuthTokenTest_WithJSONFile(){

        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        String tokenId = given().log().all()
                .contentType(ContentType.JSON)
                .body(new File("./src/test/resources/jsons/auth.json"))
                .when().log().all()
                .post("/auth")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("token");

        System.out.println("tokenId ==>" + tokenId);
        Assert.assertNotNull(tokenId);

    }

    //BELOW APPROACH WE CAN USE WHEN ATTRIBUTES ARE dynamic/getting changed
    // pojo to json : java object serialization
    //json to pojo : de-serialization
    // jackson-databind/gson(provided by google & open source)/tasson
    @Test
    public void getAuthTokenTest_WithPOJOClass(){

        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        Credentials cred = new Credentials("admin","password123");

        String tokenId = given().log().all()
                .contentType(ContentType.JSON)
                .body(cred)      // need to convert pojo class to json: serialization: ObjectMapper(Jackson databind)
                                //java.lang.IllegalStateException: Cannot serialize object because no JSON serializer found in classpath. Please put Jackson (Databind), Gson, Johnzon, or Yasson in the classpath.
                .when().log().all()
                .post("/auth")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .extract()
                .path("token");

        System.out.println("tokenId ==>" + tokenId);
        Assert.assertNotNull(tokenId);

        //json ---> pojo: De-serialization

    }
}
