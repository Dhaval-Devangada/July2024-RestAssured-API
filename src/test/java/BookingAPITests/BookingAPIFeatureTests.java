package BookingAPITests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class BookingAPIFeatureTests {

    String tokenId;
    @BeforeMethod
    public void getToken(){
        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

         tokenId = given()
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
    }

    //Any testng method should not be static/any method with annonation should not be static
    @Test
    public void getBookingIdsTest(){
        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        Response response = given().log().all()
                .when().log().all()
                .get("/booking")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .extract()
                .response();

        JsonPath jsonPath = response.jsonPath(); //json path is existing method there in the rest assured
        List<Integer> bookingids = jsonPath.getList("bookingid");
        System.out.println("total booking ids: " +bookingids.size());

        for(Integer id:bookingids){
            Assert.assertNotNull(id);
        }
    }

    @Test
    public void getBookingIdTest(){
        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        //create a new booking id: POST
        int newBookingId = createBooking();

        //get the same booking id: GET
        given().log().all()
                .pathParams("bookingId",newBookingId)
                .when().log().all()
                .get("/booking/{bookingId}")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("firstname",equalTo("API"))
                .and()
                .body("bookingdates.checkin",equalTo("2018-01-01"));

    }

    @Test
    public void createBookingTest(){
        Assert.assertNotNull(createBooking());
    }

    @Test
    public void updateBookingTest(){
        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        //create a new booking id: POST
        int newBookingId = createBooking();

        //get the same booking id: GET
//        given().log().all()
//                .pathParams("bookingId",newBookingId)
//                .when().log().all()
//                .get("/booking/{bookingId}")
//                .then().log().all()
//                .assertThat()
//                .statusCode(200);
        verifyBookingId(newBookingId);

        //update the same booking id: PUT
        given().log().all()
                .pathParams("bookingId",newBookingId)
                .contentType(ContentType.JSON)
                .header("Cookie","token=" + tokenId)
                .body("{\n"
                        + "    \"firstname\" : \"Naveen\",\n"
                        + "    \"lastname\" : \"Automation\",\n"
                        + "    \"totalprice\" : 111,\n"
                        + "    \"depositpaid\" : true,\n"
                        + "    \"bookingdates\" : {\n"
                        + "        \"checkin\" : \"2018-01-01\",\n"
                        + "        \"checkout\" : \"2019-01-01\"\n"
                        + "    },\n"
                        + "    \"additionalneeds\" : \"Dinner\"\n"
                        + "}")
                .when().log().all()
                .put("/booking/{bookingId}")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("firstname",equalTo("Naveen"))
                .and()
                .body("lastname",equalTo("Automation"))
                .and()
                .body("additionalneeds",equalTo("Dinner"));

    }

    @Test
    public void partialBookingTest(){
        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        //create a new booking id: POST
        int newBookingId = createBooking();

        //get the same booking id: GET
//        given().log().all()
//                .pathParams("bookingId",newBookingId)
//                .when().log().all()
//                .get("/booking/{bookingId}")
//                .then().log().all()
//                .assertThat()
//                .statusCode(200);
        verifyBookingId(newBookingId);

        //update the same booking id: PATCH
        given().log().all()
                .pathParams("bookingId",newBookingId)
                .contentType(ContentType.JSON)
                .header("Cookie","token=" + tokenId)
                .body("{\n" +
                        "    \"firstname\" : \"Dani\",\n" +
                        "    \"lastname\" : \"Wills\"\n" +
                        "}")
                .when().log().all()
                .patch("/booking/{bookingId}")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("firstname",equalTo("Dani"))
                .and()
                .body("lastname",equalTo("Wills"));
    }

    @Test
    public void deleteBookingTest(){
        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        //create a new booking id: POST
        int newBookingId = createBooking();

        //get the same booking id: GET
//        given().log().all()
//                .pathParams("bookingId",newBookingId)
//                .when().log().all()
//                .get("/booking/{bookingId}")
//                .then().log().all()
//                .assertThat()
//                .statusCode(200);

        verifyBookingId(newBookingId);

        //delete the same booking id: PATCH
        given().log().all()
                .pathParams("bookingId",newBookingId)
                .contentType(ContentType.JSON)
                .header("Cookie","token=" + tokenId)
                .when().log().all()
                .delete("/booking/{bookingId}")
                .then().log().all()
                .assertThat()
                .statusCode(201);

    }

    public void verifyBookingId(int newBookingId){

        //get the same booking id: GET
        given().log().all()
                .pathParams("bookingId",newBookingId)
                .when().log().all()
                .get("/booking/{bookingId}")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }


    public int createBooking(){
        RestAssured.baseURI= "https://restful-booker.herokuapp.com";

        int bookingId =given()
                .contentType(ContentType.JSON)
                .body("{\n"
                        + "    \"firstname\" : \"API\",\n"
                        + "    \"lastname\" : \"Automation\",\n"
                        + "    \"totalprice\" : 111,\n"
                        + "    \"depositpaid\" : true,\n"
                        + "    \"bookingdates\" : {\n"
                        + "        \"checkin\" : \"2018-01-01\",\n"
                        + "        \"checkout\" : \"2019-01-01\"\n"
                        + "    },\n"
                        + "    \"additionalneeds\" : \"Breakfast\"\n"
                        + "}")
                .when()
                .post("/booking")
                .then()
                .extract()
                .path("bookingid");
        System.out.println("booking id: " + bookingId);
        return bookingId;
    }
}

// if we want to write the generic method for "GET call" then we can write the method up to STATUS code/assert the status code
// and write separate testcase for checking the data / assearting the body part
// for BaseURI we can maintain the separate method as well and can call it
// Priority is and anti-pattern for writing the testcases , because how can we sure that user is going to use the application in the same priority. So in API automation we avoid the priority
// There are two types of JSON path
// JSONpath : which is inbuilt in rest assured
//jayway: external library : which has powerful json path -> we can write json query for fetching the data from complex to complex json