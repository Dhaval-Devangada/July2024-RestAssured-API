package JaywayJsonPath;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.ReadContext;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class JsonPathTest {

    @Test
    public void getProductAPITest_JsonPath() {

        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given()
                .when()
                .get("/products");

        String jsonResponse = response.asString();
        System.out.println(jsonResponse);

        ReadContext readContext = JsonPath.parse(jsonResponse);

        List<Number> prices = readContext.read("$.[?(@.price>50)].price"); // Give me all the products where price is > 50
        System.out.println(prices);

        List<Integer> ids = readContext.read("$.[?(@.price>50)].id"); // Give me all the product id where price is > 50
        System.out.println(ids);

        List<String> titles = readContext.read("$.[?(@.price>50)].title"); // Give me all the product title where price is > 50
        System.out.println(titles);

        List<Double> rates = readContext.read("$.[?(@.price>50)].rating.rate"); // Give me all the product rate where price is > 50
        System.out.println(rates);

        List<Integer> counts = readContext.read("$.[?(@.price>50)].rating.count"); // Give me all the product count where price is > 50
        System.out.println(counts);


        for (int i=0;i<ids.size();i++){
            System.out.println(ids.get(i) + " " + prices.get(i));
        }

        // $[?(@.rating.rate<3)].id Given me all the product ids where rating rate is less than 3
    }

    @Test
    public void productApiTest_ConditionalExamples_WithTwoAttributes(){
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given()
                .when()
                .get("/products");

        String jsonResponse = response.asString();
        System.out.println(jsonResponse);

        ReadContext readContext = JsonPath.parse(jsonResponse);

        //single attribute
        // $[?(@.rating.rate<3)].id

        // with two attributes
        List<Map<String,Object>> jeweleryList = readContext.read("$[?(@.category ==  'jewelery')].['title','price']"); // Give me the "title" & "prices" where category is equal to "jewelery"
        System.out.println(jeweleryList);
        System.out.println(jeweleryList.size());

        for (Map<String,Object> product: jeweleryList){
            String title=(String)product.get("title");
            Number price=(Number)product.get("price");

            System.out.println("title : "+ title);
            System.out.println("price : "+ price);
            System.out.println("-------------------");
        }
    }

    @Test
    public void productApiTest_ConditionalExamples_WithThreeAttributes(){
        RestAssured.baseURI = "https://fakestoreapi.com";

        Response response = given()
                .when()
                .get("/products");

        String jsonResponse = response.asString();
        System.out.println(jsonResponse);

        ReadContext readContext = JsonPath.parse(jsonResponse);

        //single attribute
        // $[?(@.rating.rate<3)].id

        // with two attributes
        List<Map<String,Object>> jeweleryList = readContext.read("$[?(@.category ==  'jewelery')].['title','price','id']"); // Give me the "title","prices"&"id"  where category is equal to "jewelery"
        System.out.println(jeweleryList);
        System.out.println(jeweleryList.size());

        for (Map<String,Object> product: jeweleryList){
            String title=(String)product.get("title");
            Number price=(Number)product.get("price");
            Integer id=(Integer)product.get("id");

            System.out.println("title : "+ title);
            System.out.println("price : "+ price);
            System.out.println("id : "+ id);
            System.out.println("-------------------");
        }
    }

    // Drawback of jway --> you can not combine direct element with indirect element
    //$[?(@.category ==  'jewelery')].['id','rating.rate']  (This will give you "id" but will not give you "rating.rate" as "rate" is nested)
    // And if we want to fetch rating then our query be like/we need to write separate query=> $[?(@.category ==  'jewelery')].rating.rate
    // We can not fetch nested with two parameters
    // We can fetch non-nested with comma separated and number of attr  ==>$[?(@.category ==  'jewelery')].['title','price','id']

    // Can we have two conditions=> yes using && operator
    // when we have multiple condition at that time we need to write condition inside () and around both the conditions we need to have () as well refer below example
    // Means we need to capture both the conditions, while we have multiple conditions
    //$[?((@.category ==  'jewelery') &&(@.price<20))].id
    //$[?((@.category ==  'jewelery') &&(@.price<20))].['id','title']
    //$[?(@.rating.rate == '3.3')&&(@.price>20)].id

    //$[?((@.category ==  'jewelery') &&(@.price>20))].['id','title']
    // We can combine conditions without nested one elements with nested elements as well => $[?((@.rating.rate == '3.3')&&(@.price>20))]
    //$[?((@.rating.rate == '3.3')&&(@.price>20))].title


    //practice => https://fakestoreapi.com/users (GET call)
    // get all the firstnames -> $..name.firstname  / $[*].name.firstname
    // Give me the user where username==johnD => $[?(@.username=='johnd')]
    //Give me the city of user where user's username==johnD=>$[?(@.username=='johnd')].address.city

    //single attribute: List<?>
    //multiple attributes: List<Map<String,Object>>
    //$[?(@.category ==  'jewelery')].['title','price','id'] (These comma separted values will automatically become KEYS )

    //RestAssured is also having it's own JSON path but it's simple and not that powerful as JAYWAY library

}
