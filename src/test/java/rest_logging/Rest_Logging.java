package rest_logging;


import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class Rest_Logging {
    @Test
    public void log_all_details() {

        given()
                .baseUri("https://restcountries.com/v3.1").
                when()
                .get("/alpha/US").
                then()
                .log().all();


    }

    @Test
    public void log_body_or_header() {
        given()
                .baseUri("https://restcountries.com/v3.1")
                .when()
                .get("/alpha/US").
                then()
                .log().headers();
                //.log().body();
    }

    @Test
    public void log_cookies_or_status() {
        given()
                .baseUri("https://restcountries.com/v3.1")
                .when()
                .get("/alpha/US").
                then()
                .log().status();
        //.log().cookies();
    }

    @Test
    public void log_if_error() {
        given()
                .baseUri("https://restcountries.com/v3.1").
                when()
                .get("/alpha/UT").
                then()
                //.log().ifStatusCodeIsEqualTo(404)
                .log().ifError()
                .statusCode(404);
    }

    @Test
    public void log_if_validation_fails() {
        given()
                .baseUri("https://restcountries.com/v3.1").
                when()
                .get("/alpha/UT").
                then()
                .log().ifValidationFails()
                .statusCode(404);
    }

}
