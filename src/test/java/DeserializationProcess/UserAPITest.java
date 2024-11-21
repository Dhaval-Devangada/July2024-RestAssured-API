package DeserializationProcess;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

/**
 * De-Serilazation of normal JSON RESPONSE
 */
public class UserAPITest {
    public String getRandomEmailId() {
        return "apiautomation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void createUserWith_Lombok() {

        RestAssured.baseURI = "https://gorest.co.in";

        //1. create a user : POST
        User user = new User.UserBuilder()
                .name("Kavya")
                .email(getRandomEmailId())
                .status("active")
                .gender("female")
                .build();

        Response response = given().log().all()
                .contentType(ContentType.JSON)
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .body(user) //body description - Specify an Object request content that will automatically be serialized to JSON or XML and sent with request //auto Serialization : POJO/Lombok to JSON
                .when().log().all()
                .post("/public/v2/users");

        response.prettyPrint();
        Integer userId = response.jsonPath().get("id");
        System.out.println("User id : " + userId);

        System.out.println("=======================");

        //2. Get a user using the userId : GET
        Response getResponse = given().log().all()
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .when().log().all()
                .get("/public/v2/users/" + userId);

        getResponse.prettyPrint();

        //De-Serialization: JSON:POJO
        // We don't need to write any de-serialization code
        //we  just need to use existing methods provided by "Object Mapper" and "Jackson" class
        // And then Jackson will automatically de-serilize // jackson will automatically connect to each and every user class variable
        // How will we provide the De-serialization
        //We need to create object of  ObjectMapper class which is coming from JACKSON DATABIND library

        ObjectMapper mapper = new ObjectMapper();
        try {
            User userRes=mapper.readValue(getResponse.getBody().asString(),User.class); //(User.class) means we are converting to the "User class object" so we can store it as a "User" class object as well
            //"readValue()" is returning the "User" class object
            //readValue() is method to deserialize json content from given JSON content String
            // whenever we are using any existing class as a method parameter in that cases we need to use compiled class and not the java class (Java class we need use when we are writing code)
            // we don't need to create two "User" class, the same "User" class we will be used for Serialization and for de-Serialization as well
            //We will be able to use getter/setter methods on the object which we have created using "ObjectMapper"
            // there are total two object in this class
            //1. When we have created user "create a user : POST"
            //2. At a time of de-Serialization (with the help of object mapper)
            System.out.println(userRes.getId() + " " + userRes.getName() + " " + userRes.getEmail() + " " + userRes.getStatus() + " " + userRes.getGender());
            Assert.assertEquals(userRes.getId(),userId);
            Assert.assertEquals(userRes.getEmail(),user.getEmail());
            Assert.assertEquals(userRes.getGender(),user.getGender());
            Assert.assertEquals(userRes.getStatus(),user.getStatus());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}

// when we will create user at that time we have "id" as "null" but in response we will have "id" which is created from "server"
// That means compare to "request" we have 1 extra key-value in the "response" which is "id"
/**
 * Request payload(total attributes: 4)
 *
 * {
 *     "name": "Kavya",
 *     "email": "apiautomation1728197345845@gmail.com",
 *     "gender": "female",
 *     "status": "active"
 * }
 *
 * but we have 5 attributes in the POJO so in the request body 5 will appear
 *
 *   {    "id": null
 *       "name": "Kavya",
 *       "email": "apiautomation1728197345845@gmail.com",
 *       "gender": "female",
 *       "status": "active"
 *   }
 *
 * Response payload(total attributes: 5)
 * {
 *     "id": 7450779,
 *     "name": "Kavya",
 *     "email": "apiautomation1728197345845@gmail.com",
 *     "gender": "female",
 *     "status": "active"
 * }
 *
 *Pojo: request and response
 *
 * public class User {
 *     private Integer id;
 *     private String name;
 *     private String email;
 *     private String gender;
 *     private String status;
 *
 * }
 *
 *
 * Here in the request body we have 4 attr with value and 5th(id with null) but in the response body we have 5 attribute but in the POJO class we have 5 attribute
 * So due to that 5th extra attribute(ID) in the "POJO" class we are having "id:null" in the request body
 * But we can't remove the "id" from pojo class, if we remove it then we will have error at that time of DeserializationProcess
 * Error is : java.lang.RuntimeException: com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException: Unrecognized field "id" (class DeserializationProcess.User), not marked as ignorable (4 known properties: "gender", "status", "name", "email"])
 *
 *
 * So can we create two pojo class one for REQUEST and one for RESPONSE like below?
 * one pojo for user request
 * public class UserRequestPojo {
 *
 *     private String name;
 *     private String email;
 *     private String gender;
 *     private String status;
 *
 * }
 *
 * one pojo for user response
 * public class UserResponsePojo {
 *     private Integer id;
 *     private String name;
 *     private String email;
 *     private String gender;
 *     private String status;
 *
 * }
 *
 * so in this case , tomorrow if we have 20/25 API one for USER, one for CUSTOMER, one for PRODUCT, one for ORDER then
 * everytime we will need to maintain two(2) pojo class and that will be quite lengthy thing
 *
 * so how to solve above problem (id:null)?
 * By applying one annotation in the POJO class and
 * Annonation is "@JsonInclude(JsonInclude.Include.NON_NULL)"
 * While doing the serializationProcess/DeserializationProcess if "id" is notrequird/null then please ignore that
 *
 * After applying the above annotation below will be the our request body
 *
 * {
 *     "name": "Kavya",
 *     "email": "apiautomation1728199459331@gmail.com",
 *     "gender": "female",
 *     "status": "active"
 * }
 *
 * We don't have "id:null" in above body
 *
 * so we can use both the soluation (requestPOJOclass & responsePOJO class) and  ADD Annonation "@JsonInclude(JsonInclude.Include.NON_NULL)" in POJO class
 * JsonInclude is coming from JACKSON library
 *
 * "@JsonInclude(JsonInclude.Include.NON_NULL)" will not get applied to all the "fields/variables" which are there in the POJO class
 * It will get applied to only those variables which we have not added during the creation of REQUEST BODY(so here we have not included the "ID" during the request creation so "JsonInclude/not null"
 * wil get applied to only ID)
 *
 *
 * Another use case
 * If in the request we have 4 attributes but in the response we will have 6 attributes then we will need to create POJO based on our RESPONSE and that single POJO will handle our
 * REQUEST and RESPONSE
 * REQUEST BODY
 {
 "name": "Kavya",
 "email": "apiautomation1728199459331@gmail.com",
 "gender": "female",
 "status": "active"
 }
 *
 * RESPONSE BODY
 {
 "id":1234
 "dept":"IT"
 "name": "Kavya",
 "email": "apiautomation1728199459331@gmail.com",
 "gender": "female",
 "status": "active"
 }
 *
 * 3rd use case
 *
 * REQUEST BODY
 * {
 *     "name": "Kavya",
 *     "email": "apiautomation1728199459331@gmail.com",
 *     "gender": "female",
 *     "status": "active"
 * }
 * RESPONSE BODY
 * {
 *     "id": "Kavya"
 * }
 *
 * Here also we need to create only one single POJO class but here we don't need Deserialization process because we have only 1 attribute
 *
 * 4th use case (with different keys)
 * REQUEST BODY
 * {
 *     "name": "Kavya",
 *     "email": "apiautomation1728199459331@gmail.com",
 *     "gender": "female",
 *     "status": "active"
 * }
 *
 * RESPONSE BODY
 * {
 *     "id": "Kavya"
 *     "dept": "IT",
 *     "position": "tester"
 * }
 *
 * Here we will need to create two POJO classes one for REQUEST and one for RESPONSE, because here the structure is totally different
 *
 * First we need to analyze the response and on the basis of that we need to create the POJO
 * What is the major advantages of Deserialization?
 * Deserialization popular because assertations will be easy
 * Whaterver dynamic data we are getting in the Response , we can get it using getters() and can compare it with the expected getters()
 * Whenever we want to use any attribute value in other function / api call we can get it easily and use it
 * Whenever we have large set of JSON objects and we are writing mattchers over there then it will be difficult to find specific information
 * Maintainance will be easy - If any extra attributes getting updated then we can immediately we can update the POJO
 *
 * -------------------
 * Serialization : pojo to json
 * De-Serialization : can be avoided
 *
 * so what other option for De-Serialization
 * 1. extract data + matcher
 * 2. JSONPath : To fetch the data from complex JSON (If we want to fetch any kind of combination from the RESPONSE then we can use JSON path)
 * Huge product array then we should go with JSON path
 *
 * ----------
 * De-Serialization can be used when we have fetch the JSON and convert that JSON into POJO class and then we can use getters and can write assertation with the getters / we can write the extra
 * logic sas well
 *
 * ----
 * For uploading photo if we don't / if we are not getting response in JSON then we don't need  Serialization/De-Serialization
 *
 */

