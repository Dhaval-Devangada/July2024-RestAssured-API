package AuthAPIs;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class OAuth1APITest {

    //with Oauth1.0: we need to add extra depedency in our pom.xml
    //name: scribejava-core(Internal authentication is happning via scribejava-code library and scribejava-core is not availabe in latest version of rest assured)
   // add old version
    @Test
    public void flickerAPITest(){
        RestAssured.baseURI="https://www.flickr.com";

        Response response = RestAssured.given()
                .auth()
                .oauth("1f2c07cc981d68cda4d92da52cf39327", "42c6c5b5250c3ccd", "72157720886040194-09484295d2063651", "189f47df19b30677")
                .queryParam("nojsoncallback", 1)
                .queryParam("format", "json")
                .queryParam("method", "flickr.test.login")
                .when()
                .get("/services/rest")
                .then()
                .assertThat()
                .statusCode(200).extract().response();

        response.prettyPrint();

    }
}

// In oAuth 1.0 we will get below issue
// java.lang.NoClassDefFoundError: com/github/scribejava/core/model/Token
//https://github.com/rest-assured/rest-assured/issues/880