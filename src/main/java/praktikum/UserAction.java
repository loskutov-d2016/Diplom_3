package praktikum;

import io.qameta.allure.Step;
import io.restassured.response.Response;

public class UserAction {

    private final ApiClient apiClient;

    public UserAction(ApiClient apiClient) {
        this.apiClient = apiClient;
    }

    @Step("Регистрация нового пользователя")
    public String registerNewUser(String email, String password, String name) {
        Response registrationResponse = apiClient.createUser(email, password, name);
        registrationResponse.then().statusCode(200);
        return registrationResponse.jsonPath().getString("accessToken");
    }
}
