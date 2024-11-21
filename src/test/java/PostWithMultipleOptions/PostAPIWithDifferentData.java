package PostWithMultipleOptions;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;

public class PostAPIWithDifferentData {

    @Test
    public void bodyWithSimpleText() {

        RestAssured.baseURI = "https://postman-echo.com";

        String bodyPayload = "Hi this is Dhaval";

        given().log().all()
                .contentType(ContentType.TEXT)
                .body(bodyPayload)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void bodyWithJavaScript() {

        RestAssured.baseURI = "https://postman-echo.com";

        String bodyPayload = "<script>\n" +
                "var x, y, z;\n" +
                "x = 5;\n" +
                "y = 6;\n" +
                "z = x + y;\n" +
                "\n" +
                "document.getElementById(\"demo\").innerHTML =\n" +
                "\"The value of z is \" + z + \".\";\n" +
                "</script>";

        given().log().all()
                .contentType("application/javascript")
                .body(bodyPayload)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void bodyWithHTMLTest() {

        RestAssured.baseURI = "https://postman-echo.com";

        String bodyPayload = "<html>\n" +
                "<body>\n" +
                "    <h2>My First Web Page</h2>\n" +
                "    <p>My First Paragraph.</p>\n" +
                "    <p id=\"demo\"></p>\n" +
                "</body>\n" +
                "</html>";

        given().log().all()
                .contentType(ContentType.HTML)
                .body(bodyPayload)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    //WIP
    @Test
    public void bodyWithXMLTest() {

        RestAssured.baseURI = "https://postman-echo.com";

        String bodyPayload = " <dependency>\n" +
                "      <groupId>io.rest-assured</groupId>\n" +
                "      <artifactId>rest-assured</artifactId>\n" +
                "      <version>5.5.0</version>\n" +
                "    </dependency>";

        given().log().all()
                .contentType("application/xml;charset=utf-8")
                .body(bodyPayload)
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    //Multiple data
    //nakuari.com // resume,avatar
    @Test
    public void bodyWithMultiPartTest() {

        RestAssured.baseURI = "https://postman-echo.com";

        given().log().all()
                .contentType(ContentType.MULTIPART)
                .multiPart("fileName",new File("C:\\Users\\devangadad\\Downloads\\price.csv"))
                .multiPart("name","Bugzilla tool")
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void bodyWithPDFFileTest() {

        RestAssured.baseURI = "https://postman-echo.com";

        given().log().all()
                .contentType("application/pdf")
                .body(new File("C:\\Users\\devangadad\\Downloads\\Postman Interview Questions.pdf"))
                .when().log().all()
                .post("/post")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

}
