package LombokWithJSONArray;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.util.Arrays;

import static io.restassured.RestAssured.given;

//We have lombok just to create the object but serilazation will be done by jackson data-bind library

public class CreateUserWithJSONArrayLombokPOJO {

    public String getRandomEmailId() {
        return "apiautomation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void createUserWithJSONArrayPOJO(){

        RestAssured.baseURI = "http://httpbin.org";

        //for data we can create the data provider as well
        User.UserData userData1 = new User.UserData(1,getRandomEmailId(),"Abhi","patel","https://reqres.in/img/faces/1-image.jpg");
        User.UserData userData2 = new User.UserData(2,getRandomEmailId(),"Dhaval","Devangada","https://reqres.in/img/faces/2-image.jpg");
        User.UserData userData3 = new User.UserData(3,getRandomEmailId(),"Vidya","Sharma","https://reqres.in/img/faces/3-image.jpg");
        User.UserData userData4 = new User.UserData(4,getRandomEmailId(),"Kalpesh","patel","https://reqres.in/img/faces/4-image.jpg");
        User.UserData userData5 = new User.UserData(5,getRandomEmailId(),"Vijay","Ram","https://reqres.in/img/faces/5-image.jpg");
        User.UserData userData6 = new User.UserData(6,getRandomEmailId(),"Bharat","patel","https://reqres.in/img/faces/6-image.jpg");

        User.Support support = new User.Support("https://reqres.in/#support-heading","To keep ReqRes free,contributions towards server costs are appreciated!");

        User user = new User(1,
                6,
                12
                ,2
                ,Arrays.asList(userData1,userData2,userData3,userData4,userData5,userData6)
                ,support);


        given().log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat()
                .statusCode(200);

    }

    @Test
    public void createUserWithJSONArrayPOJO_BuilderPattern(){

        RestAssured.baseURI = "http://httpbin.org";

        User.UserData userData1 = new User.UserData.UserDataBuilder()
                .id(1)
                        .email(getRandomEmailId())
                                .first_name("Kamla")
                                        .last_name("Sharma")
                                                .avatar("https://reqres.in/img/faces/60-image.jpg")
                                                        .build();
        User.UserData userData2 = new User.UserData.UserDataBuilder()
                .id(2)
                .email(getRandomEmailId())
                .first_name("Dhaval")
                .last_name("Devangada")
                .avatar("https://reqres.in/img/faces/2-image.jpg")
                .build();

        User.UserData userData3 = new User.UserData.UserDataBuilder()
                .id(3)
                .email(getRandomEmailId())
                .first_name("Vidya")
                .last_name("Sharma")
                .avatar("https://reqres.in/img/faces/3-image.jpg")
                .build();

        User.UserData userData4 = new User.UserData.UserDataBuilder()
                .id(4)
                .email(getRandomEmailId())
                .first_name("Kalpesh")
                .last_name("patel")
                .avatar("https://reqres.in/img/faces/4-image.jpg")
                .build();

        User.UserData userData5 = new User.UserData.UserDataBuilder()
                .id(5)
                .email(getRandomEmailId())
                .first_name("Vijay")
                .last_name("Ram")
                .avatar("https://reqres.in/img/faces/5-image.jpg")
                .build();

        User.UserData userData6 = new User.UserData.UserDataBuilder()
                .id(5)
                .email(getRandomEmailId())
                .first_name("Bharat")
                .last_name("patel")
                .avatar("https://reqres.in/img/faces/6-image.jpg")
                .build();

        User.Support support = new User.Support.SupportBuilder()
                .text("To keep ReqRes free,contributions towards server costs are appreciated!")
                        .url("https://reqres.in/#support-heading")
                                .build();

        User user = new User.UserBuilder()
                .page(1)
                        .per_page(6)
                                .total(12)
                                        .total_pages(2)
                                                .data(Arrays.asList(userData1,userData2,userData3,userData4,userData5,userData6))
                                                        .support(support)
                                                                .build();


        given().log().all()
                .contentType(ContentType.JSON)
                .body(user)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat()
                .statusCode(200);

    }
}
/**
 * {
 *     "page": 1,
 *     "per_page": 6,
 *     "total": 12,
 *     "total_pages": 2,
 *     "data": [
 *         {
 *             "id": 1,
 *             "email": "george.bluth@reqres.in",
 *             "first name": "George",
 *             "last_name": "Bluth",
 *             "avatar": "https://reqres.in/img/faces/1-imagt.jpg"
 *         },
 *         {
 *             "id": 2,
 *             "email": "janet.weaver@reqres.in",
 *             "first_name": "Janet",
 *             "last_name": "Weaver",
 *             "avatar": "https://reqres.in/img/faces/2-image. jpg"
 *         },
 *         {
 *             "id": 3,
 *             "email": "emma.wong@reqres.in",
 *             "first_name": "Emma",
 *             "last_name": "Wong",
 *             "avatar": "https://reqres.in/img/faces/3-image-jpg"
 *         },
 *         {
 *             "id": 4,
 *             "email": "eve.holt@reqres.in",
 *             "first_name": "Eve",
 *             "last_name": "Holt",
 *             "avatar": "https://reqres.in/img/faces/4-image.jpg"
 *         },
 *         {
 *             "id": 5,
 *             "email": "charles-morris@reqres.in",
 *             "first_name": "Charles",
 *             "last_name": "Morris",
 *             "avatar": "https://reqres. in/img/faces/5-image. jpg"
 *         },
 *         {
 *             "id": 6,
 *             "email": "tracey. ramos@reqres.in",
 *             "first_name": "Tracey",
 *             "last_name": "Ramos",
 *             "avatar": "https://reqres.in/img/faces/6-image.jpg"
 *         }
 *     ],
 *     "support": {
 *         "url": "https://reqres.in/#support-heading",
 *         "text": "To keep ReqRes free,contributions towards server costs are appreciated!"
 *     }
 * }
 */