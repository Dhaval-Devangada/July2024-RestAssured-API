package GETAPITestsWithBDD;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class GETAPIWithQueryParamsAndPathParams {

    @Test
    public void getUserWith_QueryParams(){

        RestAssured.baseURI = "https://gorest.co.in";

        given().log().all()
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .queryParam("name","trivedi")
                .queryParam("status","active")
                .when().log().all()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @Test
    public void getUserWith_QueryParams_withHashMap(){

        RestAssured.baseURI = "https://gorest.co.in";

        //Parent interface of the Hashmap is Map interface
//        Map<String,String> queryMap = new HashMap<>();
//
//        queryMap.put("name","trivedi");
//        queryMap.put("status","active");
//        queryMap.put("gender","male");

        //after JDK 9 one method got added in map is Map.of method
        //Below is immutable hashmap, once it is created we can not update anything
        Map<String,String> queryMap = Map.of(
                "name","trivedi",
                "status","active"
        );


        given().log().all()
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .queryParams(queryMap)
                .when().log().all()
                .get("/public/v2/users")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .contentType(ContentType.JSON);
    }

    @DataProvider
    public Object[][] getUserData(){
        return new Object[][]{
            {"7381453"},
                {"7381533"},
                {"7381534"}
        };
    }

    @Test(dataProvider = "getUserData")
    public void getUserAPI_WithPathParams(String userid){
        RestAssured.baseURI = "https://gorest.co.in";

        given()
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .pathParams("userid",userid)
                .when().log().all()
                .get("/public/v2/users/{userid}/posts")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }


}
