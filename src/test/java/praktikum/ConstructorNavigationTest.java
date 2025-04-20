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
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

import static org.hamcrest.core.IsEqual.equalTo;

public class ConstructorNavigationTest {
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        userAction = new UserAction(new ApiClient());
        createUniqueUser();
    }

    public void createUniqueUser() {
        email = faker.internet().emailAddress();
        password = faker.internet().password();
        name = faker.name().fullName();
        accessToken = userAction.registerNewUser(email, password, name);
    }

    @Test
    @Description("Constructor")
    @Story("Проверка перехода к разделам «Соусы», «Начинки», «Булки»")
    public void testNavigateToConstructorSections() throws InterruptedException {
        MainPage mainPage = new MainPage(driver);
        Thread.sleep(2000);
        mainPage.openMainPage();

        mainPage.clickLoginButton();

        LoginPage loginPage = new LoginPage(driver);
        loginPage.fillLoginForm(email, password);
        Thread.sleep(1000);

        // Соусы
        mainPage.clickSaucesSection();
        Thread.sleep(1000);
        String saucesHeader = mainPage.getCurrentSectionHeader();
        Assert.assertEquals("Sauces section header is not displayed as expected", "Соусы", saucesHeader);

        // Начинки
        mainPage.clickFillingsSection();
        Thread.sleep(1000);
        String fillingsHeader = mainPage.getCurrentSectionHeader();
        Assert.assertEquals("Fillings section header is not displayed as expected", "Начинки", fillingsHeader);

        // Булки
        mainPage.clickBunsSection();
        Thread.sleep(1000);
        String bunsHeader = mainPage.getCurrentSectionHeader();
        Assert.assertEquals("Buns section header is not displayed as expected", "Булки", bunsHeader);
    }


    @After
    public void tearDown() {
        // удаление пользователя и закрытие браузера
        if (accessToken != null) {
            Response deleteResponse = new ApiClient().deleteUser(accessToken);
            deleteResponse.then().statusCode(202);
            deleteResponse.then().body("message", equalTo("User successfully removed"));
        }

        DriverManager.quitDriver();
    }
}
