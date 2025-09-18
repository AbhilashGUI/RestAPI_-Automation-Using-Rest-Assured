package request_parameters;

import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class Request_Parameters {
    @Test
    public void handling_query_parameters() {

        given()
                .baseUri("https://restcountries.com/v3.1")
                .queryParam("fullText", true)
                .when()
                .get("/name/india")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void handling_multiple_parameters() {

        HashMap<String,Object> params = new HashMap<String,Object>();
        params.put("access_key", "eaaaa33d3571fef962d994f117f202dd");
        params.put("Symbols", "INR");

        given()
                    .baseUri("http://data.fixer.io/api")
                .queryParams(params)
                .when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void handling_multi_value_parameters() {
        given()
                .baseUri("https://restcountries.com/v3.1")
                .queryParam("codes", "col,no,ee,in").
                when()
                .get("/alpha").
                then()
                .log().all()
                .statusCode(200);

    }



    @Test
    public void handling_path_parameter() {
        given()
                .baseUri("https://restcountries.com/v3.1")
                .pathParam("currency", "usd").
                when()
                .get("/currency/{currency}").
                then()
                .log().all()
                .statusCode(200);
    }

    //For Form Parameters: https://postman-echo.com/post
    //multipart/form-data
    //application/x-www-form-urlencoded
    //application/json

    @Test
    public void handling_form_data() {

        given()
                .baseUri("https://postman-echo.com")
                .contentType("application/x-www-form-urlencoded;charset=UTF-8")
                .formParam("First Name", "Abhilash")
                .formParam("Last Name", "Vemula").
                when()
                .post("/post").
                then()
                .log().all()
                .statusCode(200);

    }


}
