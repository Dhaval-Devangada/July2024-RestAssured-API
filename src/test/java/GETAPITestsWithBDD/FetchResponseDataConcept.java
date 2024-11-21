package GETAPITestsWithBDD;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class FetchResponseDataConcept {

    @Test
    public void getContactsAPITest_WithInvalidToken() {

        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

        given().log().all()
                .header("Authorization", "Bearer 0-4")
                .when().log().all()
                .get("/contacts")
                .then().log().all()
                .assertThat()
                .statusCode(401)
                .and()
                .assertThat()
                .body("error",equalTo("Please authenticate.")); //error is directly associated with root
    }

    @Test
    public void getContactsAPITest_WithInvalidToken_WithExtract() {

        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";

       String errorMessage = given().log().all()
                .header("Authorization", "Bearer 0-4")
                .when().log().all()
                .get("/contacts")
                .then().log().all()
                .extract() // Here to reach to error we don't need any child/parent element
                .path("error"); //Path is returning object but we can store in "String" datatype because "String" is a child of "Object"

        System.out.println(errorMessage);
        Assert.assertEquals(errorMessage,"Please authenticate.");
    }

    @Test
    public void getSingleUser_FetchSingleUserData(){
        RestAssured.baseURI = "https://gorest.co.in";

        Response response = given()
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .when()
                .get("/public/v2/users/7430403");

        System.out.println("status code: " + response.statusCode());
        System.out.println("status line: " + response.statusLine());
        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath(); //get a jsonpath view of the response body

        int userId = jsonPath.getInt("id");
        System.out.println("user id: " + userId);

        String userName = jsonPath.getString("name");
        System.out.println("user name: " + userName);

        String status = jsonPath.getString("status");
        System.out.println("status: " + status);

        //we can not fetch all the key-values --> we need to fetch one by one
    }

    @Test
    public void getSingleUser_FetchFullUserData(){
        RestAssured.baseURI = "https://gorest.co.in";

        Response response = given()
                .header("Authorization","Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .when()
                .get("/public/v2/users/");

        System.out.println("status code: " + response.statusCode());
        System.out.println("status line: " + response.statusLine());
        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath(); //get a jsonpath view of the response body

        List<Integer> ids = jsonPath.getList("id");
        System.out.println(ids);

        List<String> names = jsonPath.getList("name");
        System.out.println(names);

        for(Integer e : ids){
            System.out.println(e);
        }
    }

    @Test
    public void getProduct_FetchNestedData() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given()
                .when()
                .get("/products");

        System.out.println("status code: " + response.statusCode());
        System.out.println("status line: " + response.statusLine());
        response.prettyPrint();

        JsonPath jsonPath = response.jsonPath();

        List<Integer> ids = jsonPath.getList("id");
        System.out.println(ids);

        List<Object> prices = jsonPath.getList("price");
        System.out.println(prices);

        List<Object> rateList=jsonPath.getList("rating.rate");
        System.out.println(rateList);

        List<Integer> countList=jsonPath.getList("rating.count");
        System.out.println(countList);

        for(int i=0;i<ids.size();i++){
            int id = ids.get(i);
            Object price = prices.get(i);
            Object rate = rateList.get(i);
            int count = countList.get(i);

            System.out.println("ID: " + id + " price: " + price + " rate : " + rate + " count : " + count);
        }
            Assert.assertTrue(rateList.contains(4.7F));
    }

    @DataProvider
    public Object[][] getUserData(){
        return new Object[][]{
                {"7381453"}
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
                .statusCode(200)
                .and()
                //.body("title",equalTo("Ascit calcar virgo animi concido cultura eaque."));
                .body("title",hasItem("Ascit calcar virgo animi concido cultura eaque."));
    }
        //extract method is used to fetch something from the JSON body
        //equalTo : json object {}
        //hasItem: json array []
    // In the API testing we don't need manual testcases as API is not the UI application, avoid manual testcase design
    // we can achieve good coverage, 100% automation is possible is as well
    // API documentation /swagger itself is a manual testcases for us

}
