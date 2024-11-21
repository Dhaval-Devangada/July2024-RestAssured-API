package FakeUserAPITests;

import io.restassured.RestAssured;
import org.testng.annotations.Test;


import static io.restassured.RestAssured.given;


public class CreateFakeUserTest {

    public String getRandomEmailId() {
        return "apiautomation" + System.currentTimeMillis() + "@gmail.com";
    }

    @Test
    public void createUserTest_LombokPojo() {

        RestAssured.baseURI = "https://fakestoreapi.com";

        //GeoLocation geoLocation = new User.Address.GeoLocation("-98.900","98.36");
        User.Address.GeoLocation geoLocation = new User.Address.GeoLocation("-98.900", "98.36");
        User.Address address = new User.Address("Bangalore", "new Road", 9090, "98563", geoLocation);
        User.Name name = new User.Name("Dhaval", "Devangada");
        User user = new User(getRandomEmailId(), "dhavalDevangada", "test@2024", "8866194955", address, name);


        given().log().all()
                .body(user) //serilazation is happning here with the help of user object
                .when().log().all()
                .post("/users")
                .then().log().all()
                .assertThat()
                .statusCode(200);
    }

    @Test
    public void createUserTest_LombokBuilder() {

        RestAssured.baseURI = "https://fakestoreapi.com";

        User.Address.GeoLocation geoLocation = new User.Address.GeoLocation.GeoLocationBuilder()
                .lat("-90.98")
                .longitude("89.36")
                .build();

        User.Address address = new User.Address.AddressBuilder()
                .city("Bangalore")
                .street("old Road")
                .number(8965)
                .zipcode("12398")
                .geoLocation(geoLocation)
                .build();

        User.Name name = new User.Name.NameBuilder()
                .firstname("vijay")
                .lastname("patel")
                .build();

        User user = new User.UserBuilder().
                email(getRandomEmailId())
                .username("vijayPatel")
                .password("test#45")
                .phone("8877554488")
                .name(name)
                .address(address)
                .build();


        given().log().all()
                .body(user) //serilazation is happning here with the help of user object
                .when().log().all()
                .post("/users")
                .then().log().all()
                .assertThat()
                .statusCode(200);

    }
}

//To create the object
//Start from the lowest inner class
