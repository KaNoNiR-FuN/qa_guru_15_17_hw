import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class RegresInTests {

    @Test
    void listUserTotalTest(){
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("total", is(12));
    }

    @Test
    void userFirstNameByIdTest(){
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/users?page=2")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.find {it.id == 7}.first_name",is("Michael"));
    }

    @Test
    void findNamesInDataTest(){
        given()
                .log().uri()
                .when()
                .get("https://reqres.in/api/unknown")
                .then()
                .log().body()
                .statusCode(200)
                .body("data.name",hasItems("cerulean", "fuchsia rose", "true red", "aqua sky", "tigerlily", "blue turquoise"));
    }

    @Test
    void createUserTest(){
        String data = "{ \"name\": \"Dmitry\", \"job\": \"QA\" }";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .log().status()
                .body("name", is("Dmitry"))
                .body("job", is("QA"))
                .statusCode(201);
    }

    @Test
    void registerTest(){
        String data = "{ \"email\": \"eve.holt@reqres.in\", \"password\": \"pistol\" }";

        given()
                .log().all()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/register")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }
}
