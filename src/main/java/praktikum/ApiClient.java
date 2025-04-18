package praktikum;

import io.restassured.response.Response;
import io.qameta.allure.Step;
import static io.restassured.RestAssured.given;

public class ApiClient {

    @Step("Регистрация пользователя")
    public Response createUser(String email, String password, String name) {
        return given()
                .contentType("application/json")
                .body(String.format("{\"email\": \"%s\", \"password\": \"%s\", \"name\": \"%s\"}", email, password, name))
                .baseUri("https://stellarburgers.nomoreparties.site")
                .when()
                .post("/api/auth/register");
    }

    @Step("Удаление пользователя")
    public Response deleteUser(String accessToken) {
        return given()
                .contentType("application/json")
                .header("Authorization", accessToken)
                .baseUri("https://stellarburgers.nomoreparties.site")
                .when()
                .delete("/api/auth/user");
    }

}
