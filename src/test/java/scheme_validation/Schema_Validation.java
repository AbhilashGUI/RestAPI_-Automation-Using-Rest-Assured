package scheme_validation;

import org.testng.annotations.Test;

import java.io.File;
import static io.restassured.RestAssured.given;
import static io.restassured.internal.matcher.xml.XmlDtdMatcher.matchesDtd;
import static io.restassured.internal.matcher.xml.XmlXsdMatcher.matchesXsd;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;



public class Schema_Validation {

    /* JSON Schema Validation
     *
     * Base URI: http://data.fixer.io/api
     * End point: /latest
     *
     * Parameters
     * access_key: b406c5d0bd55d77d592af69a930f4feb
     * Symbols: USD
     */



        @Test
        public void json_schema_validation() {
            File file = new File("json_schema_1.json"); // schema file in project root or resources

            given()
                    .baseUri("http://data.fixer.io/api")
                    .queryParam("access_key", "eaaaa33d3571fef962d994f117f202dd")
                    .queryParam("Symbols", "INR")  // lowercase
                    .when()
                    .get("/latest")
                    .then()
                    .log().all()
                    .statusCode(200)
                    .body(matchesJsonSchema(file));  // correct matcher
        }




    /*
     * XML DTD Schema Validation
     *
     * Base URI: https://api.openweathermap.org/data/2.5
     * End-point: /weather
     *
     * Parameters
     * q: London,uk
     * APPID: 8f5d911d86200c6e4d30a9e8d3877fe1
     * mode: xml
     *
     */

    @Test
    public void xml_dtd_schema_validation() {

        File file = new File("xml_dtd_schema.dtd");

        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("APPID", "5e76ce9abdf5914f0123cbd6303bafd7")
                .queryParam("q", "London,uk")
                .queryParam("mode", "xml").
                when()
                .get("/weather").
                then()
                .body(matchesDtd(file))
                .log().all()
                .statusCode(200);
    }

    /*
     * XML XSD Schema Validation
     *
     * Base URI: https://api.openweathermap.org/data/2.5
     * End-point: /weather
     *
     * Parameters
     * q: London,uk
     * APPID: 8f5d911d86200c6e4d30a9e8d3877fe1
     * mode: xml
     *
     */

    @Test
    public void xml_xsd_schema_validation() {

        File file = new File("resources/xml_xsd_schema.xsd");

        given()
                .baseUri("https://api.openweathermap.org/data/2.5")
                .queryParam("APPID", "8f5d911d86200c6e4d30a9e8d3877fe1")
                .queryParam("q", "London,uk")
                .queryParam("mode", "xml").
                when()
                .get("/weather").
                then()
                .body(matchesXsd(file))
                .log().all()
                .statusCode(200);
    }





}
