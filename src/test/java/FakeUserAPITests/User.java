package FakeUserAPITests;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * What I will learn? - How to create Lombok from the JSON file
 * This user class will behave like lombok for us
 * Below class is a blueprint - Always create blue print first
 * Whenever we have curly braces {} we have to create a class
 * Whenever we have array [] then we need to create list
 *
 * // JSON Body without array
 * {
 * "email": "john@gmail.com",
 * "username": "johnd",
 * "password": "m38rmF$",
 * "name": {
 * "firstname": "john",
 * "lastname": "doe"
 * },
 * "address": {
 * "geolocation": {
 * "lat": "-37.3159",
 * "long": "81.1496"
 * },
 * "city": "kilcoole",
 * "street": "new road",
 * "number": 7682,
 * "zipcode": "12926-3874"
 * },
 * "phone": "1-570-236-7033"
 * }
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {  // we can consider this "User" as a root as well

    //root elements and not nested
    private String email;
    private String username;
    private String password;
    private String phone;
    private Address address;
    private Name name;

    //for nested element always create a class
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Name {
        private String firstname;
        private String lastname;
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Address {
        private String city;
        private String street;
        private int number;
        private String zipcode;
        private GeoLocation geoLocation;
        // we have the "geoLocation" under "Address" so for "Address" we need to create reference for "geoLocation" as well
        //Otherwise when we try to create constructor of "Address" at that time , how will we supply the value of "GeoLocation"


        @Data
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        public static class GeoLocation{
            private String lat;
            @JsonProperty("long")
            private String longitude;
        }
    }

}
