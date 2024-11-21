package GETAPITestsWithoutBDD;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;
public class ProductAPIsTest {

    //Without BDD
    @Test
    public  void getProductsTest_1(){

        RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
        RequestSpecification request =RestAssured.given();

        request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2NmQ3NjM0M2VkZmQ5YzAwMTNmNzE0NDgiLCJpYXQiOjE3Mjc1OTUxNjB9.yJG78xh_KEeixUzdWjS8fqLUrUEmGcrY-9rNj8RJ4xU");
        Response response=request.get("/contacts");

        int statusCode = response.statusCode();
        System.out.println("status code is:" + statusCode );
        Assert.assertEquals(statusCode,200);

        String statusLine = response.statusLine();
        System.out.println("status code is:" + statusLine );
        Assert.assertEquals(statusLine,"HTTP/1.1 200 OK");

        String body = response.body().prettyPrint();
        System.out.println(body);
    }
}
