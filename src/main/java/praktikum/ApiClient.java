package praktikum;

import com.google.gson.Gson;
import io.restassured.response.Response;
import io.qameta.allure.Step;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class ApiClient {
    private Gson gson = new Gson();

    @Step("Регистрация пользователя")
    public Response createUser(String email, String password, String name) {
        Map<String, String> user = new HashMap<>();
        user.put("email", email);
        user.put("password", password);
        user.put("name", name);
        return given()
                .contentType("application/json")
                .body(gson.toJson(user))
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
