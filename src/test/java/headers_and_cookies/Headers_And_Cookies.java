package headers_and_cookies;

import io.restassured.http.Cookie;
import io.restassured.http.Headers;
import org.testng.annotations.Test;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class Headers_And_Cookies {
    /*
     * What are Headers?
     *
     * Headers contain important information in the form of meta-data associated with:
     *
     * 		1. Request Body
     * 		2. Response Body
     * 		3. Caching of Response
     * 		4. Authentication
     * 		5. Cookies
     */


    /*
     * Adding Request Headers
     *
     * http://data.fixer.io/api/latest
     *
     * 1. If-None-Match: ee8d42ca86290f687b5a42ee5b8ecc07
     * 2. If-Modified-Since: 	Thu, 18 Sep 2025 15:44:05 GMT
     *
     */


    @Test
    public void sending_response_headers() {
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "eaaaa33d3571fef962d994f117f202dd")
                .queryParam("Symbols", "INR")
                .header("etag", "ee8d42ca86290f687b5a42ee5b8ecc07")
                .header("last-modified", "Thu, 18 Sep 2025 15:44:05 GMT").
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void sending_headers_object() {
        HashMap<String, Object> headers = new HashMap<String, Object>();
        headers.put("etag", "ee8d42ca86290f687b5a42ee5b8ecc07");
        headers.put("last-modified", "Thu, 18 Sep 2025 15:44:05 GMT");
        headers.put("Accept-Encoding", "gzip, deflate, br");
        headers.put("Connection", "keep-alive");

        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "eaaaa33d3571fef962d994f117f202dd")
                .queryParam("Symbols", "INR")
                .headers(headers).
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void sending_request_cookies() {
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "eaaaa33d3571fef962d994f117f202dd")
                .queryParam("Symbols", "INR")
                .cookie("user","Test","Test1","Test2").
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);
    }
  //Importance of cookie builder is that we have an additional methods
    @Test
    public void sending_cookies_usinhg_builder() {
        Cookie cookie = new Cookie.Builder("usertype","int").setSecured(true).setComment("test cookie").build();
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "eaaaa33d3571fef962d994f117f202dd")
                .queryParam("Symbols", "USD")
                .cookie(cookie).
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200);
    }

    @Test
    public void validate_response_header() {
        given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "eaaaa33d3571fef962d994f117f202dd")
                .queryParam("Symbols", "INR").
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200)
        .header("transfer-encoding", "chunked");
    }

    @Test
    public void extract_response_header() {
        Headers headers = given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "eaaaa33d3571fef962d994f117f202dd")
                .queryParam("Symbols", "INR").
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200)
                .extract().headers();

        System.out.println(headers.getValue("date"));
        System.out.println(headers.getValue("content-type"));
        System.out.println(headers.getValue("Transfer-Encoding"));
    }

    @Test
    public void extract_response_cookies() {
        Map<String,String> cookies = given()
                .baseUri("http://data.fixer.io/api")
                .queryParam("access_key", "eaaaa33d3571fef962d994f117f202dd")
                .queryParam("Symbols", "INR").
                when()
                .get("/latest").
                then()
                .log().all()
                .statusCode(200)
                .extract().cookies();

        System.out.println(cookies.get("chunked"));

    }
}

