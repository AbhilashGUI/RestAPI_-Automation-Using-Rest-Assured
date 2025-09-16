package getting_started;

import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class Getting_Started {

    @Test
    public void simple_get_request() {

        given()
                .baseUri("https://restcountries.com/v3.1").
                when()
                .get("/all?fields=name,flags").
                then()
                .statusCode(200);
    }
        @Test
        public void validate_json_response() {

            given()
                    .baseUri("https://restcountries.com/v3.1").
                    when()
                    .get("/alpha/USA").
                    then()
                    .statusCode(200)
                    .body("name.common[0]", equalTo("United States"))
                    .body("name.official[0]", equalTo("United States of America"))
                    .body("currencies.USD.symbol[0]", equalTo("$"))
                    .body("currencies.USD.name[0]", equalTo("United States dollar"))
                    .body("languages.eng[0]", equalTo("English"));
        }

        @Test
        public void validate_xml_response () {
            given()
                    .baseUri("https://api.openweathermap.org/data/2.5")
                    .queryParam("q", "London,uk")
                    .queryParam("APPID", "5e76ce9abdf5914f0123cbd6303bafd7")
                    .queryParam("mode", "xml").
                    when()
                    .get("/weather").
                    then()
                    .statusCode(200)
                    .body(
                            "current.city.@name", equalTo("London"),
                            "current.city.country", equalTo("GB")
                    );
        }

        @Test
        public void extract_response_data () {
            Response res = given()
                    .baseUri("https://restcountries.com/v3.1").
                    when()
                    .get("/alpha/USA").
                    then()
                    .extract().response();
            System.out.println(res.asString());
        }

        @Test
        public void extract_single_value () {
            String temperature = given()
                    .baseUri("https://api.openweathermap.org/data/2.5")
                    .queryParam("q", "London,uk")
                    .queryParam("APPID", "5e76ce9abdf5914f0123cbd6303bafd7")
                    .queryParam("mode", "xml").
                    when()
                    .get("/weather").
                    then()
                    .statusCode(200)
                    .body(
                            "current.city.@name", equalTo("London"),
                            "current.city.country", equalTo("GB")
                    )
                    .extract().path("current.temperature.@value");

            System.out.println(temperature);
        }

        @Test
        public void verify_status_line () {
            given()
                    .baseUri("https://api.printful.com").
                    when()
                    .get("/variant/1").
                    then()
                    .statusCode(404)
                    .statusLine("HTTP/1.1 404 Not Found");
        }
    }


