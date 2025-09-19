package MiscOperations;


import io.restassured.RestAssured;
import io.restassured.config.XmlConfig;
import io.restassured.response.Response;
import junit.framework.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class Misc {

    @BeforeClass
    public static void setup() {
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = 9999;
    }

    @Test
    public void specify_port() {

        when()
                .get("/rest/v3.1/alpha/USA")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void connect_whatspp() {
        when()
                .get("/Whatsapp")
                .then()
                .log().all()
                .statusCode(200);

    }

    @Test
    public void validate_response_time() {
        when()
                .get("/Whatsapp")
                .then()
                .time(lessThan(900L), TimeUnit.MILLISECONDS);

    }
    @Test
    public void validate_Xml_Namespace()
    {
        XmlConfig xmlc= XmlConfig.xmlConfig().declareNamespace("perctg","https://dezlearn.com/calculate-percentage");
        given()
                .config(RestAssured.config().xmlConfig(xmlc));
        when()
                .get("/student/963/score")
                .then()
                .log().all()
                .body("student.score[0]",equalTo("369"))
                .body("student.score[1]",equalTo("99.66"));
    }
 @Test
    public void validate_response_using_response_part()
    {
        Response res=when()
                .get("/get-article/bash")
                .then()
                .log().all()
                .extract().response();
        String href=res.path("href");
        String articleId=res.path("articleId");
        String articleUrl=res.path("articleUrl");

        Assert.assertTrue(articleUrl.equals(href+articleId));

    }
    @Test
    public void response_Aware_Matcher_Example()
    {
        when()
                .get("/get-article/bash")
                .then()
                .log().all()
                .body("articleUrl", response -> equalTo(response.path("href").toString()+response.path("articleId").toString()));

    }

}