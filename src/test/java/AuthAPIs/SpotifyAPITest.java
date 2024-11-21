package AuthAPIs;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class SpotifyAPITest {
//our target in api documentation is that through which API they generation token
    private String accessToken;

    @BeforeMethod
    public void getAccessToken(){
        RestAssured.baseURI="https://accounts.spotify.com";

        Response response = RestAssured.given()
                .contentType(ContentType.URLENC)
                .formParam("grant_type", "client_credentials")
                .formParam("client_id", "69d53552e3ba4b6c98382bf0fac4655d")
                .formParam("client_secret", "32732f98d2f141b39f85af55d3538049")
                .when()
                .post("/api/token");

        Assert.assertEquals(response.getStatusCode(),200);
        response.prettyPrint();
        accessToken=response.jsonPath().getString("access_token");

    }
    @Test
    public void getAlbumTest(){

        Response response = RestAssured.given()
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("https://api.spotify.com/v1/albums/4aawyAB9vmqN3uQ7FjRGTy");

        Assert.assertEquals(response.getStatusCode(),200);
        response.prettyPrint();
    }

}
