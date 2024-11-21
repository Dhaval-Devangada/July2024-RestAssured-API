package XMLAPIs;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import lombok.Data;

import java.util.List;

// We can create POJO for xml and for HTML as well. POJO is not just JSON
//xmlns(nameSpace means given xml is ceryfide with given url)
//Whenever we create any certifid xml we need to give name space
// In xml we have two types of data , XML tag/node and attribute
// so attribute , we need to explicitly need to mentioned that given variable is attribute in the xml "@JacksonXmlProperty(isAttribute = true)"
// for tag/node, we sneed to mentioned "(localName = "Circuit")"
// We can ignore first two things in XML Response (Version and Stylesheet), We don't need to mentioned it in the POJO class
// Can practice with gorest/users.xml

@Data
@JacksonXmlRootElement(localName = "MRData", namespace = "http://ergast.com/mrd/1.5")
public class MRDataPOJOForXML {

    @JacksonXmlProperty(isAttribute = true)
    private String series;

    @JacksonXmlProperty(isAttribute = true)
    private String url;

    @JacksonXmlProperty(isAttribute = true)
    private int limit;

    @JacksonXmlProperty(isAttribute = true)
    private int offset;

    @JacksonXmlProperty(isAttribute = true)
    private int total;

    @JacksonXmlProperty(localName = "CircuitTable")
    private CircuitTable circuitTable;

    @Data
    public static class CircuitTable {

        @JacksonXmlProperty(isAttribute = true)
        private String season;

        @JacksonXmlElementWrapper(useWrapping = false)
        @JacksonXmlProperty(localName = "Circuit")
        private List<Circuit> circuits;
    }

    @Data
    public static class Circuit {

        @JacksonXmlProperty(isAttribute = true)
        private String circuitId;

        @JacksonXmlProperty(isAttribute = true)
        private String url;

        @JacksonXmlProperty(localName = "CircuitName")
        private String circuitName;

        @JacksonXmlProperty(localName = "Location")
        private Location location;
    }

    @Data
    public static class Location {

        @JacksonXmlProperty(isAttribute = true, localName = "lat")
        private double latitude;

        @JacksonXmlProperty(isAttribute = true, localName = "long")
        private double longitude;

        @JacksonXmlProperty(localName = "Locality")
        private String locality;

        @JacksonXmlProperty(localName = "Country")
        private String country;
    }
}