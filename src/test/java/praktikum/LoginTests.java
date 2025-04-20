package praktikum;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;

import static org.hamcrest.core.IsEqual.equalTo;

public class LoginTests {
    private static WebDriver driver;
    private final Faker faker = new Faker();
    private String email;
    private String password;
    private String name;
    private String accessToken;
    private UserAction userAction;

    @Before
    public void setUp() {
        driver = DriverManager.getDriver();
        createUniqueUser();
    }

    public void createUniqueUser() {
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().fullName();

        userAction = new UserAction(new ApiClient());

        // Создание юзера + получение токена для удаления после теста
        accessToken = userAction.registerNewUser(email, password, name);
    }

    @Test
    @Description("Login")
    @Story("вход по кнопке «Войти в аккаунт» на главной")
    public void testLoginViaLoginButton() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        Thread.sleep(1000);

        // Проверяю наличие кнопки "Оформить заказ" после аутентификации
        String welcomeText = mainPage.getWelcomeText();
        Assert.assertEquals(welcomeText, "Оформить заказ");
    }

    @Test
    @Description("Login")
    @Story("вход через кнопку «Личный кабинет»")
    public void testLoginViaPersonalAccountButton() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickPersonalAccountButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        Thread.sleep(1000);

        // Проверяю наличие кнопки "Оформить заказ" после аутентификации
        String welcomeText = mainPage.getWelcomeText();
        Assert.assertEquals(welcomeText, "Оформить заказ");
    }

    @Test
    @Description("Login")
    @Story("вход через кнопку в форме регистрации»")
    public void testLoginViaRegisterButton() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickPersonalCabinetButton();
        mainPage.registerButton();
        mainPage.clickLoginWithRegistrationButton();
        Thread.sleep(1000);

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        Thread.sleep(1000);

        // Проверяю наличие кнопки "Оформить заказ" после аутентификации
        String welcomeText = mainPage.getWelcomeText();
        Assert.assertEquals(welcomeText, "Оформить заказ");
    }

    @Test
    @Description("Login")
    @Story("вход через кнопку в форме восстановления пароля.")
    public void testLoginViaPasswordRecoveryButton() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickPersonalCabinetButton();
        mainPage.clickPasswordRecoveryButton();
        Thread.sleep(1000);
        mainPage.clickLoginWithRegistrationButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        Thread.sleep(1000);

        // Проверяю наличие кнопки "Оформить заказ" после аутентификации
        String welcomeText = mainPage.getWelcomeText();
        Assert.assertEquals(welcomeText, "Оформить заказ");
    }

    @After
    public void tearDown() {
        // Удаление пользователя после тестов +закрытие браузера после теста
        if (accessToken != null) {
            Response deleteResponse = new ApiClient().deleteUser(accessToken);
            deleteResponse.then().statusCode(202);
            deleteResponse.then().body("message", equalTo("User successfully removed"));
        }

        DriverManager.quitDriver();
    }
}
