package CertificateHandling;

import com.jayway.jsonpath.ReadContext;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import org.testng.annotations.Test;

public class SSLCertificateTest {

    //1st approach - Ignore SSL cert
    @Test
    public void sslTest(){
        RestAssured.given().log().all()
                .relaxedHTTPSValidation()// by default HTTPS valdations/secured connection validations is ON, so we need to OFF it (Wee need to off cert validation)
                .when().log().all()
                .get("https://expired.badssl.com/")
                .then().log().all()
                .statusCode(200);
    }

    //2nd approach - With Config
    @Test
    public void sslTest_With_Config(){

        // RestAssured.config(): This initializes the configuration for Rest Assured.
        // I assign the SSL config directly to the RestAssured.config object. This ensures that the config is properly applied to all subsequent requests.
        RestAssured.config = RestAssured.config()
                        .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
        RestAssured.given().log().all()
                .when().log().all()
                .get("https://expired.badssl.com/")
                .then().log().all()
                .statusCode(200);
    }
}


// cert error
//javax.net.ssl.SSLHandshakeException: PKIX path validation failed: java.security.cert.CertPathValidatorException: validity check failed
// If we are not bothered about cert testing then we can just use "relaxedHTTPSValidation()" this method