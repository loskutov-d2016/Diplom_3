package praktikum;

import com.github.javafaker.Faker;
import io.qameta.allure.Description;
import io.qameta.allure.Story;
import io.restassured.response.Response;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import static org.hamcrest.core.IsEqual.equalTo;


public class PersonalAccountTest {
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
        userAction = new UserAction(new ApiClient());
        createUniqueUser();
    }

    public void createUniqueUser() {
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().fullName();

        // Создание юзера + получение токена для удаления после теста
        accessToken = userAction.registerNewUser(email, password, name);
    }

    @Test
    @Description("Account")
    @Story("Проверь переход по клику на «Личный кабинет»")
    public void testNavigateToPersonalAccount() throws InterruptedException {
        // Open the main page
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);

        mainPage.clickPersonalAccountButton();
        Thread.sleep(1000);
        // проверяем наличие ссылки "Профиль" в лк. Можно добавить другие проверки по тз нужен только переход в лк
        String linkProfile = driver.findElement(By.xpath("//*[text()='Профиль']")).getText();
        Assert.assertEquals(linkProfile, "Профиль");
    }

    @Test
    @Description("Account")
    @Story("Проверь переход по клику на «Конструктор")
    public void testNavigateToConstructor() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);

        mainPage.clickPersonalAccountButton();
        mainPage.clickConstructorButton();

        // Проверяю наличие кнопки "Оформить заказ" после аутентификации
        String verificationText = driver.findElement(By.xpath("//button[text()='Оформить заказ']")).getText();
        Assert.assertEquals(verificationText, "Оформить заказ");
    }

    @Test
    @Description("Account")
    @Story("Проверь переход по клику на  Stellar Burgers")
    public void testNavigateToStellarBurgers() throws InterruptedException {
        // Open the main page
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);

        mainPage.clickPersonalAccountButton();
        mainPage.clickStellarBurgersLogo();
        // Проверяю наличие кнопки "Оформить заказ"
        String verificationText = driver.findElement(By.xpath("//button[text()='Оформить заказ']")).getText();
        Assert.assertEquals(verificationText, "Оформить заказ");
    }

    @Test
    @Description("Account")
    @Story("Проверь выход по кнопке «Выйти» в личном кабинете.")
    public void testLogoutFromPersonalAccount() throws InterruptedException {
        // Open the main page
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);

        mainPage.clickPersonalAccountButton();
        Thread.sleep(1000);
        mainPage.clickLogoutButton();
        Thread.sleep(1000);
        // Проверяю наличие кнопки "Войти"
        String loginButton = driver.findElement(By.xpath("//button[text()='Войти']")).getText();
        Assert.assertEquals(loginButton, "Войти");
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
