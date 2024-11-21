package GETAPITestsWithBDD;

import io.restassured.RestAssured;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class ProductAPIs {

    //BDD style test : given --> when --> then
    @Test
    public void getProductsTest_1() {
        RestAssured.baseURI = "https://fakestoreapi.com";

        // Below pattern is builder pattern or method chaining pattern
        given().log().all()
                .get("/products")  // END Point should start with forward slash
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }
    @Test
    public void getProductsTest_2() {

        RestAssured.baseURI = "https://fakestoreapi.com";

        given().log().all()
                .when().log().all()
                .get("/products")
                .then().log().all()
                .assertThat()
                .statusCode(200)
                .and()
                .body("$.size()", equalTo(20));
    }

}

//Rest Assured -
//Open source
//JAVA DSL - Domain specific language
//JDK : http client - it is complex > So for ease RestAssured guys have created wrapper on top of "http client" which is known as RestAssured library but internally it is using "http client" only
// Advantages - User friendly (given(),when(),then()) /readable
//RestAssured is internally using "httpClient" to call the REST API(GRT call, PUT call)
//So Rest assured does not have its own client its is using JAVA JDK client
// Other libraries which has built wrapper in top of "httpClient" clients are - OKHttp client , Jersy Client , Unirest
//In API automation we don't need to worry about driver and parallel execution
// .log.all() is designed only for given(), when() and then()


   /* Role: RestAssured is a class that provides various static configuration methods, such as setting the base URI, base path, port, authentication, etc.
    It acts as the primary entry point for configuring and setting up the environment for your API tests.These configurations affect all the subsequent requests made using Rest Assured.*/

  /* given() is a static method provided by the RestAssured class (actually from io.rest assured.RestAssured), and it's the starting point of the BDD (Behavior-Driven Development) style syntax in Rest Assured.
    It is used to set up the request specification, such as headers, query parameters, body content, and more*/

//BDD style test: given --> when -->then
//cucumber is not BDD -> BDD means to use  given(), when() and then()
// BDD is a approach -> approach can be used by different tools in the market and cucumber is one of them
// Due to BDD approach rest assured is super popular
// Drawback of builder pattern -> we can not write system.out.println in between
// The role of Testng is just to write test, because assertion will be provided by the restAssured as well

//Hamcrest is already used by /there id in the "RestAssured"
//Hamcrest is popular library for writing the matchers > inbuilt expression are there we just need to use that
// Testng: equalsTo, assertTrue, assertFalse > To achieve same thing in rest assured we have matchers >size--20
//"$" means top node of the JSON
//"$.size()" will gives us the array size of the response

//Whenever we are writing api code we have to  create one api client
// so here we need one api client(Rest assured) which will help us to call the api's(GET,PUT...)
//Rest assured has provided static methods/ In rest assured most of the methods are static in nature
// Generally we call static method using (className.methodName) but if we don't want to do that then we need to use static import
// If we have added the static then we only need to use method name
// We always need to add the STATIC import manually suggestions won't appear for static import
// There is one configuration as well which is by default disabled
//get() call is overloaded and giving us the response
