package XMLAPIs;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import io.restassured.RestAssured;
import io.restassured.path.xml.XmlPath;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

/**
 * How to parse the XMl using XMLPath
 * <p>
 * <p>
 * Our ultimate target is we have to fetch the response body
 * From the response body we need to prepare query
 * Then we need to pass that query to XMLPATH
 */
public class XMLPathTest {
    // In below example we are fetching the value from the XML tag/XML node
    @Test
    public void circuitApiTestWith_XML() {

        RestAssured.baseURI = "http://ergast.com";

        Response response = given()
                .when()
                .get("/api/f1/2017/circuits.xml")
                .then()
                .extract().response();
        // response.prettyPrint();

        String responseBodyAsString = response.body().asString();
        System.out.println("Response as a String:" + responseBodyAsString);

        //Now we have to create object of XML path, just like we have created object of JSON path

        XmlPath xmlPath = new XmlPath(responseBodyAsString);

        //I want to collect all the circuit names
        // In xml there is no concept of array
        // IN xml we have "XML tag" and "XML attribute" as well
        List<String> circuitNames = xmlPath.getList("MRData.CircuitTable.Circuit.CircuitName");
        System.out.println("Total circuits: " + circuitNames.size());
        System.out.println("-----------------");
        for (String e : circuitNames) {
            System.out.println(e);
        }

        // Can we write conditional based
        // Give me the locality where circuit id == Americas
        System.out.println("-Give me the locality where circuit id == Americas=>op=>Austin");
        //Here we will need to write query
        // Query will start with two wildcards **
        // Then we have write grooy clouser (Clouser is not a function, clouser means just a block )
        // As clouser is block so we need to use {}
        //"findAll" is a clouser name
        // To iterate through each block(here CIRCUIT) we need to use Iterater
        // To find the attribute we need to use "@"
        String locality = xmlPath.getString("**.findAll{it.@circuitId=='americas'}.Location.Locality");
        System.out.println(locality);
        System.out.println("-----------------");

        String country = xmlPath.getString("**.findAll{it.@circuitId=='americas'}.Location.Country");
        System.out.println(country);

        //Fetch the Locality where circuitId = 'americas' or circuitId = 'baharain' ==>Austin, Sakir
        System.out.println("-----------------");
        List<String> moreLocalities = xmlPath.getList("**.findAll{it.@circuitId=='americas' || it.@circuitId=='bahrain'}.Location.Locality");
        System.out.println(moreLocalities);
    }

    // In below example we are getting value from XML ATTRIBUTE
    @Test
    public void xmlPathTest() {
        RestAssured.baseURI = "http://ergast.com";

        Response response = given()
                .when()
                .get("/api/f1/2017/circuits.xml")
                .then()
                .extract().response();

        String responseBodyAsString = response.body().asString();
        System.out.println(responseBodyAsString);

        XmlPath xmlPath = new XmlPath(responseBodyAsString);

        //Give me all the circuit ids(Which is attribute )
        List<String> circuitIds = xmlPath.getList("MRData.CircuitTable.Circuit.@circuitId");

        for (String ci : circuitIds) {
            System.out.println(ci);
        }

        System.out.println("-----------------");

        //For Baharin , give me it's lat and long value // fetch the lat and long values where circuitId = 'bahrain'
        // When we have condition at that time we should use findAll
        // At attribute level we can not fetch it combinely , we need to fetch it separately
        // To fetch attribute we need to use "@"
        String latValue = xmlPath.getString("**.findAll{it.@circuitId=='bahrain'}.Location.@lat");
        String longValue = xmlPath.getString("**.findAll{it.@circuitId=='bahrain'}.Location.@long");

        System.out.println("lat is: " + latValue + " and long value is" + longValue);

        //Give me the URL where circutId == americas//fetch url where circuitId == americas
        String americaUrl = xmlPath.getString("**.findAll{it.@circuitId=='americas'}.@url");
        System.out.println(americaUrl);

    }

    @Test
    public void goRestXMLPathTest() {
        RestAssured.baseURI = "http://gorest.co.in";

        Response response = given()
                .when()
                .get("/public/v2/users.xml")
                .then()
                .extract().response();

        String responseBodyAsString = response.body().asString();
        System.out.println(responseBodyAsString);

        XmlPath xmlPath = new XmlPath(responseBodyAsString);

        //collect all the names
        List<String> names = xmlPath.getList("objects.object.name");
        System.out.println(names);

        //collect all the types
        List<String> ids = xmlPath.getList("objects.object.id");
        System.out.println(ids);

        // make sure that all the id's type == integer
        List<String> type = xmlPath.getList("objects.object.id.@type");
        System.out.println(type);
    }

    @Test
    public void xmlTest_With_XMLResponse_Desrialization() {
        RestAssured.baseURI = "http://ergast.com";

        Response response = given()
                .when()
                .get("/api/f1/2017/circuits.xml")
                .then()
                .extract().response();

        // For XML, we need to create XMLMapper class to start the deserilazation process

        XmlMapper xmlMapper = new XmlMapper();

        try {
            MRDataPOJOForXML mrDataPOJOForXML = xmlMapper.readValue(response.asString(), MRDataPOJOForXML.class);
            System.out.println(mrDataPOJOForXML.getSeries());
            System.out.println(mrDataPOJOForXML.getCircuitTable().getSeason());

            //assertions
            Assert.assertNotNull(mrDataPOJOForXML);
            Assert.assertEquals(mrDataPOJOForXML.getSeries(),"f1");
            Assert.assertEquals(mrDataPOJOForXML.getCircuitTable().getSeason(),"2017");

            MRDataPOJOForXML.Circuit circuit = mrDataPOJOForXML.getCircuitTable().getCircuits().get(0);

            Assert.assertEquals(circuit.getCircuitName(),"Albert Park Grand Prix Circuit");
            Assert.assertEquals(circuit.getCircuitId(),"albert_park");
            Assert.assertEquals(circuit.getLocation().getLocality(),"Melbourne");
            Assert.assertEquals(circuit.getLocation().getCountry(),"Australia");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
