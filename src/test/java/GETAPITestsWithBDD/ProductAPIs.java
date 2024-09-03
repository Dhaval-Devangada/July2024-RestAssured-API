package GETAPITestsWithBDD;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProductAPIs {

    //BDD style test: given --> when -->then
    // Drawback of builder pattern -> we can not write system.out.println in between
    // The role of Testng is just to write test, because assertion will be provided by the restAssured as well
    @Test
    public void getProductsTest_1() {
        //Whenever we are writing api code we have to  create one api client
        // so here we need one api client(Rest assured) which will help us to call the api's(GET,PUT...)
        //Rest assured has provided static methods/ In rest assured most of the methods are static in nature
        // Generally we call static method using (className.methodName) but if we don't want to do that then we need to use static import
        // If we have added the static then we only need to use method name

        RestAssured.baseURI = "https://fakestoreapi.com";

        given().log().all()
                .get("/products")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void getProductsTest_2() {

        //Hamcrest is already used by /there in the "RestAssured"
        //Hamcrest is popular library for writing the matchers > inbuilt expression are there we just need to use that
        // Testng: equalsTo, assertTrue, assertFalse > To achieve same thing in rest assured we have matchers >size--20
        //"$" means top node of the JSON

        RestAssured.baseURI = "https://fakestoreapi.com";

        given().log().all()
                .when().log().all()
                .get("/products")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("$.size()",equalTo(200));
    }


}
