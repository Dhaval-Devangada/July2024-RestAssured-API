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
 * De-Serilazation of normal JSON ARRAY RESPONSE
 */
public class UserAPITestWithJSONArrayResponse {

    @Test
    public void createUserWith_Lombok() {

        RestAssured.baseURI = "https://gorest.co.in";

        //1. Get all user using  : GET
        Response getResponse = given().log().all()
                .header("Authorization", "Bearer 882013dc26e18a388eeea1b7ad9cc59f06988e88ecc53f1fa6f0d3ac3e058009")
                .when().log().all()
                .get("/public/v2/users");
        // In the response we have JSON array

        getResponse.prettyPrint();

        ObjectMapper mapper = new ObjectMapper();
        try {
           // User userRes=mapper.readValue(getResponse.getBody().asString(),User.class); //(User.class) means we are converting to the "User class object" so we can store it as a "User" class object as well
            // In the response we have JSON array
            //asString() will give full JSON array
            // Error : java.lang.RuntimeException: com.fasterxml.jackson.databind.exc.MismatchedInputException: Cannot deserialize value of type `DeserializationProcess.User` from Array value (token `JsonToken.START_ARRAY`)
            //With single JSON it is easy to map so we won't get any error during deserilization but with JSON array we can't map easily so we will get above error 
            // To resolve that add [] in the deserilization code 
            User[] userRes=mapper.readValue(getResponse.getBody().asString(),User[].class);
            // In above line we are saying that go to each and every JSON and map it with "User.class"
            //here single dimension array is getting created and we are storing user objects/the whole user object in that array
            // To  print each and every data

            for(User user:userRes){
                System.out.println("ID: "+ user.getId());
                System.out.println("Name: "+ user.getName());
                System.out.println("Email: "+ user.getEmail());
                System.out.println("Status: "+ user.getStatus());
                System.out.println("Gender: "+ user.getGender());

                System.out.println("=========================");

            }

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
